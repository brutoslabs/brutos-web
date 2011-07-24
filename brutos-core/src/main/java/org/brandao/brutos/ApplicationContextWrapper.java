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

import java.util.Properties;

/**
 * 
 * 
 * @author Afonso Brandao
 */
public class ApplicationContextWrapper 
        extends AbstractApplicationContext{

    protected ApplicationContext applicationContext;

    /**
     * Define a aplica��o.
     *
     * @param app Aplica��o.
     */
    public ApplicationContextWrapper( ApplicationContext app ){
        this.applicationContext = app;
    }

    public void configure( Properties config ){
        this.applicationContext.configure(config);
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

    public void configure() {
         this.applicationContext.configure();
    }
}
