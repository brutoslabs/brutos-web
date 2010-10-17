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

import org.brandao.brutos.old.programatic.IOCManager;
import org.brandao.brutos.programatic.ControllerManager;
import org.brandao.brutos.programatic.InterceptorManager;
import org.brandao.brutos.old.programatic.WebFrameManager;

/**
 *
 * @author Afonso Brandao
 */
public class ApplicationContextWrapper extends ApplicationContext{

    protected ApplicationContext applicationContext;
    
    public ApplicationContextWrapper( ApplicationContext mapping ){
        this.applicationContext = mapping;
    }

    public void destroy() {
        applicationContext.destroy();
    }

    protected void loadIOCManager(IOCManager iocManager) {
        applicationContext.loadIOCManager(iocManager);
    }

    protected void loadWebFrameManager(WebFrameManager webFrameManager) {
        applicationContext.loadWebFrameManager(webFrameManager);
    }

    protected void loadInterceptorManager(InterceptorManager interceptorManager) {
        applicationContext.loadInterceptorManager(interceptorManager);
    }

    protected void loadController(ControllerManager controllerManager) {
        applicationContext.loadController(controllerManager);
    }

}
