/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */

package org.brandao.brutos;

import java.beans.PropertyEditor;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import org.brandao.brutos.codegenerator.CodeGeneratorProvider;
import org.brandao.brutos.ioc.CustomEditorConfigurer;
import org.brandao.brutos.ioc.EditorConfigurer;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.old.programatic.InterceptorManager;
import org.brandao.brutos.scope.CustomScopeConfigurer;
import org.brandao.brutos.scope.IOCScope;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.validator.ValidatorProvider;
import org.brandao.brutos.view.ViewProvider;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.AbstractWebApplicationContext;
import org.brandao.brutos.web.scope.ApplicationScope;
import org.brandao.brutos.web.scope.FlashScope;
import org.brandao.brutos.web.scope.OldRequestScope;
import org.brandao.brutos.web.scope.ParamScope;
import org.brandao.brutos.web.scope.SessionScope;

/**
 * @deprecated 
 * @author Afonso Brandao
 */
public class BrutosContext extends AbstractWebApplicationContext
    implements ConfigurableWebApplicationContext {

    private Configuration configuration;
    private IOCManager iocManager;
    private WebFrameManager webFrameManager;
    private ViewProvider viewProvider;
    private InterceptorManager interceptorManager;
    private List<AbstractApplicationContext> services = new ArrayList<AbstractApplicationContext>();
    private LoggerProvider loggerProvider;
    private Logger logger;
    private Invoker invoker;
    private ValidatorProvider validatorProvider;

    public BrutosContext(){
    }

    public synchronized void start( ServletContextEvent sce ){

        if( sce.getServletContext().getAttribute( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE ) != null ){
            throw new IllegalStateException(
                            "Cannot initialize context because there is already a root application context present - " +
                            "check whether you have multiple ContextLoader definitions in your web.xml!");
        }

        sce.getServletContext().setAttribute( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE, this );
        loadContext( sce );
    }

    private void loadContext( ServletContextEvent sce ){
        long time = System.currentTimeMillis();
        try{
            loadParameters( sce );
            loadLogger( sce.getServletContext() );
            logger.info( "Initializing Brutos root WebApplicationContext" );
            loadInvoker( sce.getServletContext() );
            loadServices( configuration, sce );
            setConfiguration( configuration );
            resolveController( sce.getServletContext() );
            methodResolver( sce.getServletContext() );
            registerCustomEditors();
            registerDefaultScopes( sce );
        }
        catch( Throwable e ){
            sce.getServletContext().setAttribute( BrutosConstants.EXCEPTION, e );
        }
        finally{
            if( logger != null )
                logger.info( String.format( "Initialization processed in %d ms", (System.currentTimeMillis()-time) ) );
        }
    }

    public IOCProvider getIocProvider(){
        return iocManager.getProvider();
    }
    
    private void registerDefaultScopes( ServletContextEvent sce ){
        logger.info( "Register scopes..." );
        getScopes().register( "application" , new ApplicationScope( sce.getServletContext() ) );
        getScopes().register( "flash" , new FlashScope() );
        getScopes().register( "ioc" , new IOCScope( this ) );
        //Scopes.register( "request" , new RequestScope() );
        getScopes().register( "session" , new SessionScope() );
        getScopes().register( ScopeType.PARAM.toString(), new ParamScope() );
        getScopes().register( ScopeType.REQUEST.toString(), new OldRequestScope() );
        if( iocManager.getProvider().containsBeanDefinition("customScopes") ){
            CustomScopeConfigurer customScopes =
                    (CustomScopeConfigurer)iocManager.getInstance("customScopes");
            Map scopes = customScopes.getCustomScopes();
            Set i = scopes.keySet();
            for( Object key: i )
                getScopes().register( (String)key,(Scope)scopes.get(key) );
        }

        logger.info( "Scopes:" );
            Map scopes = getScopes().getScopes();
            Set i = scopes.keySet();
            for( Object key: i )
                logger.info( String.valueOf(key) );

    }

    private void registerCustomEditors(){
        if( iocManager.getProvider().containsBeanDefinition("customEditors") ){
            CustomEditorConfigurer customEditors =
                    (CustomEditorConfigurer)iocManager.getInstance("customEditors");
            Map editors = customEditors.getCustomEditors();
            Set i = editors.keySet();
            for( Object key: i )
                EditorConfigurer.registerPropertyEditor( (String)key,(PropertyEditor)editors.get(key) );
        }
    }

    private void loadInvoker( ServletContext sc ){
        this.invoker = new Invoker1_3();

        sc.setAttribute( BrutosConstants.INVOKER,this.getInvoker());
    }

    private void loadLogger( ServletContext sc ){
        this.loggerProvider = LoggerProvider.getProvider(configuration);

        sc.setAttribute( BrutosConstants.LOGGER,
                                            this.loggerProvider );

        this.logger = this.loggerProvider.getLogger( BrutosContext.class.getName() );

    }

    private void loadManager( Configuration config, ServletContextEvent sce ){
        this.iocManager =  new IOCManager(
                            IOCProvider.getProvider(
                                config )
                            );

        this.iocManager.getProvider().configure(config);

        this.interceptorManager =  new InterceptorManager( iocManager );

        this.webFrameManager = new WebFrameManager( interceptorManager, iocManager );

        this.viewProvider       = ViewProvider.getProvider( configuration );

        this.validatorProvider = ValidatorProvider.getValidatorProvider(configuration);

        ServletContext sc = sce.getServletContext();

        sc.setAttribute( BrutosConstants.IOC_MANAGER,
                                            this.iocManager );
        sc.setAttribute( BrutosConstants.IOC_PROVIDER,
                                            this.iocManager.getProvider() );
        sc.setAttribute( BrutosConstants.WEBFRAME_MANAGER,
                                            this.webFrameManager );
        sc.setAttribute( BrutosConstants.INTERCEPTOR_MANAGER,
                                            this.interceptorManager );
        sc.setAttribute( BrutosConstants.VIEW_PROVIDER,
                                            this.viewProvider );
        sc.setAttribute( BrutosConstants.VALIDATOR_PROVIDER,
                                            this.viewProvider );

    }

    private void loadServices() throws IOException{
        Enumeration<URL> urlServices = Thread.currentThread().getContextClassLoader().getResources("META-INF/brutos.service");
        List<String> serviceNames = new ArrayList<String>();

        //default
        serviceNames.add( "org.brandao.brutos.DefaultApplicationContext" );

        while( urlServices.hasMoreElements() ){
           String serviceClass = getServiceClassName( urlServices.nextElement() );

           if( serviceClass != null ){
               String[] ss = serviceClass.replaceAll( " ", "" ).split( "\\," );
               for( String s: ss  ){
                   if( !serviceNames.contains( s ) )
                        serviceNames.add( s );
               }
           }
        }

        serviceNames.addAll( getCustomServices() );
        logger.info( String.format( "Contexts: %s", serviceNames.toString()) );
        loadServices(serviceNames);

    }

    private List<String> getCustomServices(){
        List<String> serv = new ArrayList();
        String stringService =
            configuration.getProperty( "org.brandao.brutos.applicationcontext" );

        if( stringService != null ){
           String[] ss = stringService.replaceAll( " ", "" ).split( "\\," );
           for( String s: ss  ){
               if( !serv.contains( s ) )
                    serv.add( s );
           }
        }
        return serv;
    }
    private String getServiceClassName( URL url ) throws IOException{
        InputStream in = null;
        try{
            in = url.openStream();
            Properties prop = new Properties();
            prop.load(in);
            return prop.getProperty( "org.brandao.brutos.applicationcontext" );
        }
        catch( Exception e ){
            return null;
        }
        finally{
            if( in != null )
                in.close();
        }
    }

    private void loadServices( Configuration config, ServletContextEvent sce ) throws IOException{
        logger.info( "Loading services" );
        loadServices();
        loadService( config, sce );
    }

    private void loadService( Configuration config, ServletContextEvent sce ){
        for( AbstractApplicationContext provider: services ){
            logger.info( String.format("Starting %s", provider.getClass().getName() ) );
            provider.configure(config);
        }

        logger.info( "Starting Brutos Application Context" );
        loadManager( config, sce );

        //load interceptors
        logger.info( "Loading interceptos" );
        for( AbstractApplicationContext provider: services ){
            provider.loadInterceptorManager( interceptorManager );
        }

        //load controllers
        logger.info( "Loading controllers" );
        for( AbstractApplicationContext provider: services ){
            provider.loadWebFrameManager( webFrameManager );
        }

        //load ioc/di
        logger.info( "Loading IOC" );
        for( AbstractApplicationContext provider: services ){
            provider.loadIOCManager( iocManager );
        }
    }

    private void resolveController( ServletContext context ){

        ControllerResolver instance = super.getNewControllerResolver();
        super.setControllerResolver(instance);
        context
            .setAttribute(
                BrutosConstants.CONTROLLER_RESOLVER , instance );
    }

    private void methodResolver( ServletContext context ){
        try{
            MethodResolver instance = (MethodResolver) Class.forName(
                    configuration.getProperty(
                    "org.brandao.brutos.controller.method_resolver",
                    "org.brandao.brutos.DefaultMethodResolver"
                ),
                    true,
                    Thread.currentThread().getContextClassLoader()

            ).newInstance();

            context
                .setAttribute(
                    BrutosConstants.METHOD_RESOLVER , instance );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    private void loadParameters( ServletContextEvent sce ){
        setConfiguration(new Configuration());

        ServletContext context = sce.getServletContext();
        Enumeration initParameters = context.getInitParameterNames();

        while( initParameters.hasMoreElements() ){
            String name = (String) initParameters.nextElement();
            getConfiguration().setProperty( name, context.getInitParameter( name ) );
        }
    }

    public synchronized void stop( ServletContextEvent sce ){

        if( services != null ){
            for( AbstractApplicationContext provider: services ){
                try{
                    provider.destroy();
                }
                catch( Exception e ){}
            }
        }

        ServletContext sc = sce.getServletContext();
        sc.removeAttribute( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE );
        sc.removeAttribute( BrutosConstants.IOC_MANAGER );
        sc.removeAttribute( BrutosConstants.IOC_PROVIDER );
        sc.removeAttribute( BrutosConstants.WEBFRAME_MANAGER );
        sc.removeAttribute( BrutosConstants.INTERCEPTOR_MANAGER );
        sc.removeAttribute( BrutosConstants.LOGGER );
        sc.removeAttribute( BrutosConstants.METHOD_RESOLVER );
        sc.removeAttribute( BrutosConstants.CONTROLLER_RESOLVER );
        sc.removeAttribute( BrutosConstants.INVOKER );

        if( this.iocManager != null && this.iocManager.getProvider() != null )
            this.iocManager.getProvider().destroy();

        if( this.loggerProvider != null )
            this.loggerProvider.destroy();

        this.configuration      = null;
        this.interceptorManager = null;
        this.iocManager         = null;
        this.logger             = null;
        this.loggerProvider     = null;
        this.services           = null;
        this.viewProvider       = null;
        this.webFrameManager    = null;
        this.invoker            = null;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public IOCManager getIocManager() {
        return iocManager;
    }

    public void setIocManager(IOCManager iocManager) {
        this.iocManager = iocManager;
    }

    public WebFrameManager getWebFrameManager() {
        return webFrameManager;
    }

    public void setWebFrameManager(WebFrameManager webFrameManager) {
        this.webFrameManager = webFrameManager;
    }

    public ViewProvider getViewProvider() {
        return viewProvider;
    }

    public void setViewProvider(ViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }

    public static BrutosContext getCurrentInstance(){
        BrutosContext brutosCore =
            (BrutosContext)ContextLoaderListener
                .currentContext
                    .getAttribute(
                        BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE );

        if( brutosCore == null ){
            throw new IllegalStateException(
                    "Unable to initialize the servlet was not configured for the application context root - " +
                    "make sure you have defined in your web.xml ContextLoader!"
            );
        }

        Throwable ex =
            (Throwable)ContextLoaderListener
                .currentContext
                    .getAttribute( BrutosConstants.EXCEPTION );

        if( ex != null )
            throw new BrutosException( ex );

        return brutosCore;
    }

    public ServletContext getContext(){
        return ContextLoaderListener.currentContext;
    }

    public HttpServletRequest getRequest(){
        return (HttpServletRequest) ContextLoaderListener.currentRequest.get();
    }

    public Controller getController(){
        BrutosContext brutosInstance = BrutosContext.getCurrentInstance();
        return (Controller) brutosInstance.getRequest()
                .getAttribute( BrutosConstants.CONTROLLER );
    }

    public MethodResolver getMethodResolver(){
        BrutosContext brutosInstance = BrutosContext.getCurrentInstance();
        return (MethodResolver) brutosInstance
                .getContext()
                    .getAttribute( BrutosConstants.METHOD_RESOLVER );
    }

    public InterceptorManager getInterceptorManager(){
        BrutosContext brutosInstance = BrutosContext.getCurrentInstance();
        return (InterceptorManager) brutosInstance
                .getContext()
                    .getAttribute( BrutosConstants.INTERCEPTOR_MANAGER );
    }

    public ControllerResolver getResolveController() {
        BrutosContext brutosInstance = BrutosContext.getCurrentInstance();
        return (ControllerResolver) brutosInstance
                .getContext()
                    .getAttribute( BrutosConstants.CONTROLLER_RESOLVER );
    }

    private void loadServices( List<String> data ){

        for( String provider: data ){
            try{
                logger.info( String.format( "Getting instance: %s", provider ) );
                services.add(
                        (AbstractApplicationContext)Class.
                        forName(
                            provider,
                            true,
                            Thread.currentThread().getContextClassLoader()
                                            ).newInstance()
                );
            }
            catch( Exception e ){
                logger.warn( "Failed to get instance!", e);
            }
        }
    }

    public LoggerProvider getLoggerProvider() {
        if( loggerProvider == null )
            throw new BrutosException( "Logger provider was not configured" );
        else
            return loggerProvider;
    }

    protected List<AbstractApplicationContext> getServices(){
        return this.services;
    }

    public ValidatorProvider getValidatorProvider() {
        return validatorProvider;
    }

    public void setValidatorProvider(ValidatorProvider validatorProvider) {
        this.validatorProvider = validatorProvider;
    }

    public Invoker getInvoker(){
        return this.invoker;
    }

    public CodeGeneratorProvider getCodeGeneratorProvider() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setCodeGeneratorProvider(CodeGeneratorProvider codeGeneratorProvider) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setServletContext(ServletContext servletContext) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public MvcRequestFactory getRequestFactory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public MvcResponseFactory getResponseFactory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setInterceptorManager(org.brandao.brutos.InterceptorManager interceptorManager) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setConfiguration(Properties config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setIocProvider(IOCProvider iocProvider) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ControllerManager getControllerManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ControllerResolver getControllerResolver() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ActionResolver getActionResolver() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
