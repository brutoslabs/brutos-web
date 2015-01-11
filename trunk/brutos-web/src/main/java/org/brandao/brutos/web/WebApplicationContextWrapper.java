/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.web;

import java.util.Properties;
import javax.servlet.ServletContext;
import org.brandao.brutos.ActionResolver;
import org.brandao.brutos.CodeGenerator;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ControllerManager;
import org.brandao.brutos.ControllerResolver;
import org.brandao.brutos.InterceptorManager;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.MvcRequestFactory;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.MvcResponseFactory;
import org.brandao.brutos.ObjectFactory;
import org.brandao.brutos.RenderView;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.ValidatorFactory;

/**
 *
 * @author Brandao
 */
public class WebApplicationContextWrapper 
        extends AbstractWebApplicationContext{

    protected ConfigurableWebApplicationContext applicationContext;

    public WebApplicationContextWrapper(ConfigurableWebApplicationContext app){
        this.applicationContext = app;
    }

    public void destroy(){
        this.applicationContext.destroy();
    }

    public Properties getConfiguration(){
        return this.applicationContext.getConfiguration();
    }

    public MvcResponse getMvcResponse() {
        return this.applicationContext.getMvcResponse();
    }

    public MvcRequest getMvcRequest() {
        return this.applicationContext.getMvcRequest();
    }

    public Scopes getScopes() {
        return this.applicationContext.getScopes();
    }

    public ServletContext getContext(){
        return this.applicationContext.getContext();
    }

    public void setServletContext(ServletContext servletContext){
        
        if( applicationContext instanceof ConfigurableWebApplicationContext )
            ((ConfigurableWebApplicationContext)this.applicationContext).
                    setServletContext(servletContext);

    }

    public MvcRequestFactory getRequestFactory(){
        return applicationContext.getRequestFactory();
    }

    public MvcResponseFactory getResponseFactory(){
        return applicationContext.getResponseFactory();
    }

    public void setInterceptorManager(InterceptorManager interceptorManager){
        applicationContext.setInterceptorManager(interceptorManager);
    }

    public void setRenderView(RenderView renderView){
        applicationContext.setRenderView(renderView);
    }
    
    public RenderView getRenderView(){
        return applicationContext.getRenderView();
    }

    public ValidatorFactory getValidatorFactory(){
        return applicationContext.getValidatorFactory();
    }

    public Invoker getInvoker(){
        return applicationContext.getInvoker();
    }

    public void setInvoker(Invoker value){
        applicationContext.setInvoker(value);
    }

    public void setConfiguration( Properties config ){
        applicationContext.setConfiguration(config);
    }

    public void setObjectFactory(ObjectFactory objectFactory){
        applicationContext.setObjectFactory(objectFactory);
    }


    public InterceptorManager getInterceptorManager(){
        return applicationContext.getInterceptorManager();
    }

    public ControllerManager getControllerManager(){
        return applicationContext.getControllerManager();
    }

    public ObjectFactory getObjectFactory(){
        return applicationContext.getObjectFactory();
    }

    public ControllerResolver getControllerResolver(){
        return applicationContext.getControllerResolver();
    }

    public ActionResolver getActionResolver(){
        return applicationContext.getActionResolver();
    }

    public CodeGenerator getCodeGeneratorProvider(){
        return applicationContext.getCodeGenerator();
    }

    public void setCodeGenerator(CodeGenerator codeGenerator){
        applicationContext.setCodeGenerator(codeGenerator);
    }

    protected void loadDefinitions(ComponentRegistry registry) {
        throw new UnsupportedOperationException();
    }

    public void flush(){
        this.applicationContext.flush();
    }
}
