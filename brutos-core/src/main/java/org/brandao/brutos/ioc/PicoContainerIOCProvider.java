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

package org.brandao.brutos.ioc;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSessionEvent;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.ioc.picocontainer.ComplexComponentFactory;
import org.brandao.brutos.ioc.picocontainer.ObjectComponentFactory;
import org.brandao.brutos.ioc.picocontainer.PicoContainerContextLoaderListener;
import org.brandao.brutos.ioc.picocontainer.PicoContainerScopes;
import org.brandao.brutos.ioc.picocontainer.Scope;
import org.brandao.brutos.mapping.ioc.ComplexObjectInject;
import org.brandao.brutos.mapping.ioc.Injectable;
import org.brandao.brutos.old.programatic.Bean;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoBuilder;

/**
 *
 * @author Afonso Brandao
 */
public class PicoContainerIOCProvider extends IOCProvider{

    private MutablePicoContainer applicationContainer;
    private PicoContainerContextLoaderListener context;
    private ServletContextEvent sce;

    public PicoContainerIOCProvider() {
        this.applicationContainer = (new PicoBuilder())
                                    .withConstructorInjection()
                                    .withMethodInjection()
                                    .build();
        this.context = new PicoContainerContextLoaderListener();
    }

    
    public void configure( Configuration properties, ServletContextEvent sce ){
        this.sce = sce;
        context.contextInitialized(sce);
    }

    public void addBeanDefinition( Bean bean ){
        super.addBeanDefinition( bean );
        Injectable b = bean.getInjectable();
        if( b instanceof ComplexObjectInject )
            applicationContainer.addAdapter( new ComplexComponentFactory( (ComplexObjectInject)b ) );
        else
            applicationContainer.addAdapter( new ObjectComponentFactory( b ) );
    }

    public Bean removeBeanDefinition( Bean bean ){
        applicationContainer.removeComponent( bean.getInjectable().getName() );
        return super.removeBeanDefinition(bean);
    }

    public void destroy(){
        context.contextDestroyed(sce);
    }
    
    public Object getInstance(String name ) {
        return applicationContainer.getComponent( name );
    }

    public void requestDestroyed(ServletRequestEvent sre) {
        //sre.getServletRequest().removeAttribute( REQUEST_ID );
        context.requestDestroyed(sre);
    }

    public void requestInitialized(ServletRequestEvent sre) {
        context.requestInitialized(sre);
    }

    public void sessionCreated(HttpSessionEvent se) {
        context.sessionCreated(se);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        context.sessionDestroyed(se);
    }

    public Object getBean(String name) {
        return applicationContainer.getComponent( name );
    }
    
}