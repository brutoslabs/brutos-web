

package org.brandao.brutos.web;

import java.io.Serializable;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.brandao.brutos.*;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.web.io.ServletContextResource;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.Interceptor;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.scope.IOCScope;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.DefaultTypeFactory;
import org.brandao.brutos.type.TypeFactory;
import org.brandao.brutos.web.http.JSPRenderView;
import org.brandao.brutos.web.http.DefaultUploadListenerFactory;
import org.brandao.brutos.web.scope.ApplicationScope;
import org.brandao.brutos.web.scope.FlashScope;
import org.brandao.brutos.web.scope.ParamScope;
import org.brandao.brutos.web.scope.RequestScope;
import org.brandao.brutos.web.scope.SessionScope;
import org.brandao.brutos.web.type.JSONType;


public abstract class AbstractWebApplicationContext
        extends AbstractApplicationContext
        implements ConfigurableWebApplicationContext {

    protected ServletContext servletContext;
    
    protected String[] locations;
    
    protected Resource[] resources;
    
    public AbstractWebApplicationContext(){
    }

    public AbstractWebApplicationContext( ApplicationContext parent ) {
        super(parent);
    }
    
    public void setLocations(String[] locations) {
        this.locations = locations;
        if(this.locations != null){
            for(int i=0;i<this.locations.length;i++)
                this.locations[i] = StringUtil.adjust(this.locations[i]);
        }
    }

    public void setResources(Resource[] resources) {
        this.resources = resources;
    }

    public String[] getLocations() {
        return this.locations;
    }

    public Resource[] getResources() {
        return this.resources;
    }

    protected void initUploadListener(){
        try{
            Properties config = this.getConfiguration();
            String uploadListenerFactoryName =
                config.getProperty( BrutosConstants.UPLOAD_LISTENER_CLASS,
                    DefaultUploadListenerFactory.class.getName() );

            Class<?> ulfClass = Class.forName(
                uploadListenerFactoryName,
                true,
                Thread.currentThread().getContextClassLoader() );

            Scope contextScope = getScopes()
                    .get( WebScopeType.APPLICATION );

            contextScope.put(
                BrutosConstants.UPLOAD_LISTENER_FACTORY,
                ClassUtil.getInstance(ulfClass) );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    protected void initRequestParser(){
        try{
            Properties config = this.getConfiguration();
            
            String requestParserName =
                config.getProperty( BrutosConstants.REQUEST_PARSER_CLASS,
                    BrutosConstants.DEFAULT_REQUEST_PARSER );

            Class<?> rpClass = Class.forName(
                requestParserName,
                true,
                Thread.currentThread().getContextClassLoader() );

            Scope contextScope = getScopes()
                    .get( WebScopeType.APPLICATION );

            contextScope.put(
                BrutosConstants.HTTP_REQUEST_PARSER,
                ClassUtil.getInstance(rpClass) );
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    protected void initTypes(){
        super.initTypes();
        this.typeManager.remove(Serializable.class);
        this.typeManager.register(new DefaultTypeFactory(JSONType.class, Serializable.class));
    }
    
    protected void initScopes(){
        super.initScopes();
        getScopes().register( WebScopeType.APPLICATION.toString(),
                new ApplicationScope( getContext() ) );
        getScopes().register( WebScopeType.FLASH.toString(),
                new FlashScope() );
        getScopes().register( WebScopeType.IOC.toString(),
                new IOCScope() );
        getScopes().register( WebScopeType.REQUEST.toString(),
                new RequestScope() );
        getScopes().register( WebScopeType.SESSION.toString(),
                new SessionScope() );
        getScopes().register( WebScopeType.PARAM.toString(),
                new ParamScope() );
    }
    
    protected void overrideConfig(){
        
        String tmp;
        Properties config = this.getConfiguration();
        
        tmp = config
                .getProperty(BrutosConstants.CONTROLLER_RESOLVER_CLASS, 
                              WebControllerResolver.class.getName() );

        config.put(BrutosConstants.CONTROLLER_RESOLVER_CLASS,
                    tmp );

        tmp = config
                .getProperty(BrutosConstants.RESPONSE_FACTORY,
                              WebMvcResponseFactory.class.getName() );

        config.put(BrutosConstants.RESPONSE_FACTORY,
                    tmp );

        tmp = config
                .getProperty(BrutosConstants.REQUEST_FACTORY,
                              WebMvcRequestFactory.class.getName() );

        config.put(BrutosConstants.REQUEST_FACTORY,
                    tmp );

        tmp = config
                .getProperty(BrutosConstants.ACTION_RESOLVER,
                              WebActionResolver.class.getName() );

        config.put(BrutosConstants.ACTION_RESOLVER,
                    tmp );

        tmp = config
                .getProperty(BrutosConstants.RENDER_VIEW_CLASS,
                              JSPRenderView.class.getName() );

        config.put(BrutosConstants.RENDER_VIEW_CLASS,
                    tmp );

        tmp = config
                .getProperty(BrutosConstants.VIEW_RESOLVER_PREFIX,
                              "/WEB-INF/views/" );
        
        config.put(BrutosConstants.VIEW_RESOLVER_PREFIX,
                    tmp );

        tmp = config
                .getProperty(BrutosConstants.VIEW_RESOLVER_SUFFIX,
                              ".jsp" );
        
        config.put(BrutosConstants.VIEW_RESOLVER_SUFFIX,
                    tmp );

        tmp = config
                .getProperty(BrutosConstants.VIEW_RESOLVER_INDEX,
                              "index" );
        
        config.put(BrutosConstants.VIEW_RESOLVER_INDEX,
                    tmp );

        tmp = config
                .getProperty(BrutosConstants.VIEW_RESOLVER_SEPARATOR,
                              "/" );
        
        config.put(BrutosConstants.VIEW_RESOLVER_SEPARATOR,
                    tmp );

        tmp = config
                .getProperty( BrutosConstants.INVOKER_CLASS,
                              WebInvoker.class.getName() );
        
        config.put( BrutosConstants.INVOKER_CLASS,
                    tmp );
        
        tmp = config
                .getProperty( BrutosConstants.ACTION_TYPE,
                              ActionType.HIERARCHY.name() );
        
        config.put( BrutosConstants.ACTION_TYPE,
                    tmp );

        tmp = config
                .getProperty( BrutosConstants.CONTROLLER_MANAGER_CLASS,
                              WebControllerManager.class.getName() );
        
        config.put( BrutosConstants.CONTROLLER_MANAGER_CLASS,
                    tmp );

    }

    public ServletContext getContext(){
        return this.servletContext;
    }

    public Controller getController(){
        
        return (Controller) getScopes()
                .get(WebScopeType.REQUEST)
                    .get( BrutosConstants.CONTROLLER );
    }

    protected Resource getContextResource( String path ){
        return new ServletContextResource(this.servletContext, path);
    }

    public void destroy() {
        this.servletContext = null;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ControllerBuilder registerController( Class<?> classtype ){
        return this.controllerManager.addController(classtype);
    }

    public ControllerBuilder registerController( String id, Class<?> classType ){
        return this.controllerManager.addController(id, classType);
    }
    
    public ControllerBuilder registerController( String id, String view, 
            boolean resolvedView, Class<?> classType ){
        return this.controllerManager.addController(id, view, resolvedView,
                classType);
    }
    
    public ControllerBuilder registerController( String id, String view, 
            boolean resolvedView, String name, Class<?> classType, String actionId ){
        return this.controllerManager.addController( id, view, resolvedView,
           name, classType, actionId );
    }

    public ControllerBuilder registerController( String id, String view,  
            boolean resolvedView, DispatcherType dispatcherType,
            String name, Class<?> classType, String actionId ){
        return this.controllerManager.addController( id, view, resolvedView,
                dispatcherType, name, classType, actionId );
    }
    
    public ControllerBuilder registerController( String id, String view,  
            boolean resolvedView, DispatcherType dispatcherType,
            String name, Class<?> classType, String actionId, ActionType actionType ){
        return this.controllerManager.addController( id, view, 
                resolvedView, dispatcherType, name, classType, actionId, actionType );
    }
    
    public ControllerBuilder registerController(String id, String view, 
            DispatcherType dispatcherType, boolean resolvedView, String name, 
            Class<?> classType, String actionId, ActionType actionType) {
        return this.controllerManager.addController(id, view, dispatcherType, 
                resolvedView, name, classType, actionId, actionType);
    }
    
    public Controller getRegisteredController(Class<?> clazz){
        return super.controllerManager.getController(clazz);
    }
    
    public Controller getRegisteredController(String name){
        return super.controllerManager.getController(name);
    }

    public InterceptorStackBuilder registerInterceptorStack( String name, boolean isDefault ){
        return this.interceptorManager.addInterceptorStack(name, isDefault);
    }
    
    public InterceptorBuilder registerInterceptor( String name, Class<?> interceptor, boolean isDefault ){
        return this.interceptorManager.addInterceptor(name, interceptor, isDefault);
    }
    
    public Interceptor getRegisteredInterceptor(Class<?> clazz){
        return this.interceptorManager.getInterceptor(clazz);
    }
    
    public Interceptor getRegisteredInterceptor(String name){
        return this.interceptorManager.getInterceptor(name);
    }

    public void registerScope(String name, Scope scope){
        this.scopes.register(name, scope);
    }
    
    public Scope getRegistredScope(String name){
        return this.scopes.get(name);
    }
    
    public Scope getRegistredScope(ScopeType scopeType){
        return this.scopes.get(scopeType);
    }
    
    public void registerType(TypeFactory factory){
        this.typeManager.register(factory);
    }
    
    public void registerType(Class<?> classType, Class<?> type){
        this.typeManager.register(new DefaultTypeFactory(type, classType));
    }
    
    public TypeFactory getRegistredType(Class<?> classType){
        return this.typeManager.getTypeFactory(classType);
    }
    
    public void registerProperty(String name, String value){
        super.getConfiguration().setProperty(name, value);
    }
    
    public String getProperty(String name){
        return super.getConfiguration().getProperty(name);
    }

    public Properties getProperties(){
        return super.getConfiguration();
    }
    
    public void flush(){

        this.initLogger();
        
        this.overrideConfig();
        
        this.initInstances();
        
        this.initScopes();
        
        this.initTypes();
        
        this.invoker.flush();
        
        this.loadDefinitions(this);

        this.initComponents();

        this.initUploadListener();
        
        this.initRequestParser();
    }
    
}
