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

import org.brandao.brutos.programatic.InterceptorManager;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import org.brandao.brutos.old.programatic.*;
import org.brandao.brutos.programatic.ControllerManager;

/**
 *
 * @author Afonso Brandao
 */
public abstract class ApplicationContext {
    
    private IOCManager iocManager;
    
    private WebFrameManager webFrameManager;
    
    private InterceptorManager interceptorManager;
    
    public ApplicationContext() {
    }

    /**
     * @deprecated 
     * @param config
     * @param sce
     */
    public void configure( Configuration config, ServletContextEvent sce ){
    }

    public void configure( Properties config ){
    }
    
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

    public InterceptorManager getInterceptorManager() {
        return interceptorManager;
    }

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

    protected abstract void loadInterceptorManager( InterceptorManager interceptorManager );
    
    protected abstract void loadController( ControllerManager controllerManager );
    
}
