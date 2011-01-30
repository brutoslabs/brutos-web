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

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.servlet.ServletContextEvent;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.old.programatic.*;
import org.brandao.brutos.scope.CustomScopeConfigurer;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.scope.Scopes;
import org.brandao.brutos.validator.ValidatorProvider;
import org.brandao.brutos.view.ViewProvider;

/**
 * Classe central que permite a configuração de um aplicativo. Com essa classe
 * é possível:
 * <ul>
 * <li>configurar interceptadores;</li>
 * <li>incluir novos controladores;</li>
 * <li>manipular os controladores;</li>
 * <li>manipular a resolução de controladores;</li>
 * <li>manipular a execução de ações;</li>
 * <li>determinar novas regras de validação</li>
 * </ul>
 * 
 * @author Afonso Brandao
 */
public abstract class ApplicationContext {

    private static Logger logger = LoggerProvider
        .getCurrentLoggerProvider()
            .getLogger(ApplicationContext.class.getName());

    private IOCManager iocManager;
    private IOCProvider iocProvider;
    private WebFrameManager webFrameManager;
    private InterceptorManager interceptorManager;
    private ControllerManager controllerManager;
    private ViewProvider viewProvider;
    private ValidatorProvider validatorProvider;
    private Invoker invoker;
    private Properties configuration;
    private LoggerProvider loggerProvider;
    private ControllerResolver controllerResolver;
    private ActionResolver actionResolver;
    private MvcResponseFactory responseFactory;
    private MvcRequestFactory requestFactory;

    public ApplicationContext() {
        this.configuration = new Configuration();
    }

    /**
     * @deprecated 
     * @param config
     * @param sce
     */
    public void configure( Configuration config, ServletContextEvent sce ){
    }

    /**
     * Inicia o processo de configuração da aplicação.
     */
    public void configure(){
        configure( this.configuration );
    }

    /**
     * Inicia o processo de configuração da aplicação.
     *
     * @param config Configuração.
     */
    public void configure( Properties config ){
        
        this.configuration = config;
        this.iocManager = new IOCManager();
        this.setIocProvider(IOCProvider.getProvider(config));
        this.getIocProvider().configure(config);
        this.interceptorManager = new InterceptorManager();
        this.webFrameManager = new WebFrameManager( this.interceptorManager, this.iocManager );
        this.controllerResolver = getNewControllerResolver();
        this.actionResolver = getNewMethodResolver();
        this.responseFactory = getMvcResponseFactory();
        this.responseFactory = getMvcResponseFactory();
        this.validatorProvider = ValidatorProvider.getValidatorProvider(this.getConfiguration());
        this.controllerManager = new ControllerManager(this.interceptorManager, this.getValidatorProvider());
        this.invoker = new Invoker(  this.getControllerResolver(), getIocProvider(), controllerManager, this.getActionResolver(), this );
        this.viewProvider = ViewProvider.getProvider(this.getConfiguration());

        if( iocProvider.containsBeanDefinition("customScopes") ){
            CustomScopeConfigurer customScopes =
                    (CustomScopeConfigurer)iocProvider.getInstance("customScopes");
            Map scopes = customScopes.getCustomScopes();
            Set i = scopes.keySet();
            for( Object key: i )
                Scopes.register( (String)key,(Scope)scopes.get(key) );
        }

        this.loadIOCManager(iocManager);
        this.loadInterceptorManager(interceptorManager);
        this.loadController(getControllerManager());
        this.loadWebFrameManager(webFrameManager);
    }

    /**
     * Define o responsável por resolver os controladores.
     * @param controllerResolver Responsável por resolver os controladores
     */
    protected void setControllerResolver( ControllerResolver controllerResolver ){
        this.controllerResolver = controllerResolver;
    }
    
    /**
     * Obtém o responsável por resolver os controladores.
     * @return Responsável por resolver os controladores
     */
    protected ControllerResolver getNewControllerResolver(){
        try{
            ControllerResolver instance = (ControllerResolver) Class.forName(
                    configuration.getProperty(
                    "org.brandao.brutos.controller.class",
                    DefaultControllerResolver.class.getName()
                ),
                    true,
                    Thread.currentThread().getContextClassLoader()

            ).newInstance();

            return instance;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    /**
     * Obtém a fábrica de respostas.
     * @return Fábrica de respostas.
     */
    protected MvcResponseFactory getMvcResponseFactory(){
        try{
            MvcResponseFactory instance = (MvcResponseFactory) Class.forName(
                    configuration.getProperty(
                    "org.brandao.brutos.controller.response_factory",
                    "org.brandao.brutos.DefaultMvcResponseFactory"
                ),
                    true,
                    Thread.currentThread().getContextClassLoader()

            ).newInstance();

            return instance;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    /**
     * Obtém a fábrica de requisições.
     * @return Fábrica de requisições.
     */
    protected MvcRequestFactory getMvcRequestFactory(){
        try{
            MvcRequestFactory instance = (MvcRequestFactory) Class.forName(
                    configuration.getProperty(
                    "org.brandao.brutos.controller.request_factory",
                    "org.brandao.brutos.DefaultMvcRequestFactory"
                ),
                    true,
                    Thread.currentThread().getContextClassLoader()

            ).newInstance();

            return instance;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    /**
     * Obtém o responsável por resolver as ações.
     * @return Responsável por resolver as ações.
     */
    protected ActionResolver getNewMethodResolver(){
        try{
            ActionResolver instance = (ActionResolver) Class.forName(
                    configuration.getProperty(
                    "org.brandao.brutos.controller.method_resolver",
                    DefaultActionResolver.class.getName()
                ),
                    true,
                    Thread.currentThread().getContextClassLoader()

            ).newInstance();
            return instance;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }

    /**
     * Método invocado quando a aplicação é finalizada.
     */
    public abstract void destroy();

    /**
     * @deprecated 
     * @return
     */
    public IOCManager getIocManager() {
        return iocManager;
    }

    /**
     * @deprecated 
     * @param iocManager
     */
    public void setIocManager(IOCManager iocManager) {
        this.iocManager = iocManager;
    }

    /**
     * @deprecated 
     * @return
     */
    public WebFrameManager getWebFrameManager() {
        return webFrameManager;
    }

    /**
     * @deprecated 
     * @param webFrameManager
     */
    public void setWebFrameManager(WebFrameManager webFrameManager) {
        this.webFrameManager = webFrameManager;
    }

    /**
     * Obtém o gestor de interceptadores.
     * @return Gestor de interceptadores.
     */
    public InterceptorManager getInterceptorManager() {
        return interceptorManager;
    }

    /**
     * Define o gestor de interceptadores.
     * @param interceptorManager Gestor de interceptadores.
     */
    public void setInterceptorManager(InterceptorManager interceptorManager) {
        this.interceptorManager = interceptorManager;
    }

    /**
     * @deprecated 
     * @param iocManager
     */
    protected abstract void loadIOCManager( IOCManager iocManager );

    /**
     * @deprecated 
     * @param webFrameManager
     */
    protected abstract void loadWebFrameManager( WebFrameManager webFrameManager );

    /**
     * Configura os interceptadores.
     * @param interceptorManager Gestor de interceptadores.
     */
    protected abstract void loadInterceptorManager( InterceptorManager interceptorManager );
    
    /**
     * Configura os controladores.
     * @param interceptorManager Gestor dos controladores.
     */
    protected abstract void loadController( ControllerManager controllerManager );

    /**
     * Obtém a aplicação corrente.
     * @return Aplicação.
     */
    public static ApplicationContext getCurrentApplicationContext(){
        Scope requestScope = Scopes.get(ScopeType.REQUEST);

        if( requestScope == null )
            throw new BrutosException( "scope not configured: " + ScopeType.REQUEST.toString() );

        ApplicationContext app = (ApplicationContext)
                        requestScope.get( BrutosConstants.ROOT_APPLICATION_CONTEXT_ATTRIBUTE );

        return app;
    }

    /**
     * Obtém um determinado controlador.
     * @param controllerClass Classe do controlador
     * @return Controlador.
     */
    public Object getController( Class controllerClass ){
        return null;
    }

    /**
     * Obtém o gestor de controladores.
     * @return Gestor de controladores.
     */
    public ControllerManager getControllerManager() {
        return controllerManager;
    }

    /**
     * Obtém o provedor da visão.
     * @return Provedor da visão.
     */
    public ViewProvider getViewProvider() {
        return viewProvider;
    }

    /**
     * Obtém o provedor das regras de validação.
     * @return Provedor das regras de validação
     */
    public ValidatorProvider getValidatorProvider() {
        return validatorProvider;
    }

    /**
     * Obtém o responsável por executar as ações.
     * @return Responsável por executar as ações.
     */
    public Invoker getInvoker() {
        return invoker;
    }

    /**
     * Define as configurações da aplicação.
     * @param config Configuração.
     */
    protected void setConfiguration( Properties config ){
        this.configuration = config;
    }

    /**
     * Obtém a configuração da aplicação.
     * @return Configuração da aplicação.
     */
    public Properties getConfiguration() {
        return configuration;
    }

    /**
     * Obtém o provedor de log.
     * @return Provedor de log.
     */
    public LoggerProvider getLoggerProvider() {
        return loggerProvider;
    }

    /**
     * Obtém o provedor do container IOC.
     * @return Provedor do container IOC.
     */
    public IOCProvider getIocProvider() {
        return iocProvider;
    }

    /**
     * Define o provedor do container IOC.
     * @param iocProvider Provedor do container IOC.
     */
    public void setIocProvider(IOCProvider iocProvider) {
        this.iocProvider = iocProvider;
    }

    /**
     * Obtém o responsável por resolver os controladores.
     * @return Responsável por resolver os controladores.
     */
    public ControllerResolver getControllerResolver() {
        return controllerResolver;
    }

    /**
     * Obtém o responsável por resolver as ações.
     * @return Responsável por resolver as ações.
     */
    public ActionResolver getActionResolver() {
        return actionResolver;
    }

    /**
     * Obtém o objeto responsável por enviar a resposta ao cliente.
     * @return Resposta.
     */
    public MvcResponse getMvcResponse() {
        return this.responseFactory.getCurrentResponse();
    }

    /**
     * Obtém o objeto responsável por receber a requisição do cliente.
     * @return Requisição.
     */
    public MvcRequest getMvcRequest() {
        return this.requestFactory.getCurrentRequest();
    }

}
