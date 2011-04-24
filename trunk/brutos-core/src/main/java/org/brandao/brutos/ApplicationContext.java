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
import org.brandao.brutos.codegenerator.CodeGeneratorProvider;
import org.brandao.brutos.io.DefaultResourceLoader;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Form;
import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.old.programatic.WebFrameManager;
import org.brandao.brutos.proxy.ProxyFactory;
import org.brandao.brutos.scope.CustomScopeConfigurer;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.validator.ValidatorProvider;
import org.brandao.brutos.view.ViewProvider;

/**
 * Classe central que permite a configura��o de um aplicativo. Com essa classe
 * � poss�vel:
 * <ul>
 * <li>configurar interceptadores;</li>
 * <li>incluir novos controladores;</li>
 * <li>manipular os controladores;</li>
 * <li>manipular a resolução de controladores;</li>
 * <li>manipular a execução de ações;</li>
 * <li>determinar novas regras de validação.</li>
 * </ul>
 * 
 * @author Afonso Brandao
 */
public abstract class ApplicationContext extends DefaultResourceLoader{

    private Logger logger = LoggerProvider
        .getCurrentLoggerProvider()
            .getLogger(ApplicationContext.class.getName());

    protected IOCManager iocManager;
    protected IOCProvider iocProvider;
    protected WebFrameManager webFrameManager;
    protected InterceptorManager interceptorManager;
    protected ControllerManager controllerManager;
    protected ViewProvider viewProvider;
    protected ValidatorProvider validatorProvider;
    protected Invoker invoker;
    protected Properties configuration;
    protected LoggerProvider loggerProvider;
    protected ControllerResolver controllerResolver;
    protected ActionResolver actionResolver;
    protected MvcResponseFactory responseFactory;
    protected MvcRequestFactory requestFactory;
    private Scopes scopes;
    protected CodeGeneratorProvider codeGeneratorProvider;
    
    private ApplicationContext parent;

    public ApplicationContext() {
        this(null);
    }

    public ApplicationContext( ApplicationContext parent ) {
        this.parent = parent;
        this.configuration = parent == null?
            new Configuration() :
            parent.configuration;
        this.scopes = new Scopes();
    }

    /**
     * Inicia o processo de configuração da aplicaçao.
     */
    public void configure(){
        configure( this.configuration );
    }

    /**
     * Inicia o processo de configura��o da aplica��o.
     *
     * @param config Configura��o.
     */
    public void configure( Properties config ){
        
        if( parent != null )
            loadParentConfig( parent );
        else
            loadLocalConfig(config);
    }

    private void loadParentConfig( ApplicationContext parent ){
        this.configuration = parent.getConfiguration();
        this.iocManager = parent.iocManager;
        this.iocProvider = parent.iocProvider;
        this.interceptorManager = parent.interceptorManager;
        this.webFrameManager = parent.webFrameManager;
        this.controllerResolver = parent.controllerResolver;
        this.actionResolver = parent.actionResolver;
        this.requestFactory = parent.requestFactory;
        this.responseFactory = parent.responseFactory;
        this.validatorProvider = parent.validatorProvider;
        this.controllerManager = parent.controllerManager;
        this.invoker = parent.invoker;
        this.viewProvider = parent.viewProvider;
        this.codeGeneratorProvider = parent.codeGeneratorProvider;
    }
    
    private void loadLocalConfig(Properties config){
        this.configuration = config;
        this.iocManager = new IOCManager();
        this.iocProvider = IOCProvider.getProvider(config);
        this.iocProvider.configure(config);
        this.interceptorManager = new InterceptorManager();
        this.webFrameManager = new WebFrameManager( this.interceptorManager, this.iocManager );
        this.controllerResolver = getNewControllerResolver();
        this.actionResolver = getNewMethodResolver();
        this.requestFactory = getMvcRequestFactory();
        this.responseFactory = getMvcResponseFactory();
        this.validatorProvider = ValidatorProvider.getValidatorProvider(this.getConfiguration());
        this.controllerManager = new ControllerManager(this.interceptorManager, validatorProvider);

        this.invoker =
                new Invoker(
                    controllerResolver,
                    iocProvider,
                    controllerManager,
                    actionResolver,
                    this,
                    viewProvider );
        
        this.viewProvider = ViewProvider.getProvider(this.getConfiguration());
        this.codeGeneratorProvider = CodeGeneratorProvider.getProvider(this.getConfiguration());
        
        if( iocProvider.containsBeanDefinition("customScopes") ){
            CustomScopeConfigurer customScopesConfigurer =
                    (CustomScopeConfigurer)iocProvider.getBean("customScopes");
            Map customScopes = customScopesConfigurer.getCustomScopes();
            Set i = customScopes.keySet();
            for( Object key: i )
                scopes.register( (String)key,(Scope)customScopes.get(key) );
        }

        this.loadIOCManager(iocManager);
        this.loadInterceptorManager(interceptorManager);
        this.loadController(controllerManager);
        this.loadWebFrameManager(webFrameManager);
    }

    /**
     * Define o respons�vel por resolver os controladores.
     * @param controllerResolver Respons�vel por resolver os controladores
     */
    protected void setControllerResolver( ControllerResolver controllerResolver ){
        this.controllerResolver = controllerResolver;
    }
    
    /**
     * Obt�m o respons�vel por resolver os controladores.
     * @return Respons�vel por resolver os controladores
     */
    protected ControllerResolver getNewControllerResolver(){
        try{
            ControllerResolver instance = (ControllerResolver) Class.forName(
                    configuration.getProperty(
                    "org.brandao.brutos.controller.class",
                    null
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
     * Obt�m a f�brica de respostas.
     * @return F�brica de respostas.
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
     * Obt�m a f�brica de requisi��es.
     * @return F�brica de requisi��es.
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
     * Obt�m o respons�vel por resolver as a��es.
     * @return Respons�vel por resolver as a��es.
     */
    protected ActionResolver getNewMethodResolver(){
        try{
            ActionResolver instance = (ActionResolver) Class.forName(
                    configuration.getProperty(
                    "org.brandao.brutos.controller.action_resolver",
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
     * M�todo invocado quando a aplica��o � finalizada.
     */
    public void destroy(){
    }

    /**
     * @deprecated 
     * @param iocManager
     */
    protected void loadIOCManager( IOCManager iocManager ){
    }

    /**
     * @deprecated 
     * @param webFrameManager
     */
    protected void loadWebFrameManager( WebFrameManager webFrameManager ){
    }

    /**
     * Configura os interceptadores.
     * @param interceptorManager Gestor de interceptadores.
     */
    protected void loadInterceptorManager( InterceptorManager interceptorManager ){
    }
    
    /**
     * Configura os controladores.
     * @param controllerManager Gestor dos controladores.
     */
    protected void loadController( ControllerManager controllerManager ){
    }

    /**
     * Obt�m um determinado controlador.
     * @param controllerClass Classe do controlador
     * @return Controlador.
     */
    public Object getController( Class controllerClass ){
        Form controller = this.controllerManager.getController(controllerClass);
        Object resource = this.iocProvider.getBean(controller.getId());
        ProxyFactory proxyFactory =
                this.codeGeneratorProvider.getProxyFactory(controllerClass);

        Object proxy =
                proxyFactory.getNewProxy(resource, controller, this, invoker);
        return proxy;
    }

    /**
     * Obt�m a configura��o da aplica��o.
     * @return Configura��o da aplica��o.
     */
    public Properties getConfiguration(){
        return configuration;
    }

    /**
     * Obt�m o provedor de log.
     * @return Provedor de log.
     */
    public LoggerProvider getLoggerProvider(){
        return loggerProvider;
    }

    /**
     * Obt�m o objeto respons�vel por enviar a resposta ao cliente.
     * @return Resposta.
     */
    public MvcResponse getMvcResponse() {
        return this.responseFactory.getCurrentResponse();
    }

    /**
     * Obt�m o objeto respons�vel por receber a requisi��o do cliente.
     * @return Requisi��o.
     */
    public MvcRequest getMvcRequest() {
        return this.requestFactory.getCurrentRequest();
    }

    public Scopes getScopes() {
        return scopes;
    }

}
