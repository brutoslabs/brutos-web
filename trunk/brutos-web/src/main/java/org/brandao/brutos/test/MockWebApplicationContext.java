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
package org.brandao.brutos.test;


import org.brandao.brutos.web.AbstractWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.WebApplicationContext;
import org.brandao.brutos.web.WebApplicationContextWrapper;



/**
 *
 * @author Afonso Brandao
 */
public class MockWebApplicationContext extends WebApplicationContextWrapper{

    private static ConfigurableWebApplicationContext app;

    public MockWebApplicationContext(){
        super( app );
    }

    public static void setCurrentApplicationContext( ConfigurableWebApplicationContext apps ){
        app = apps;
    }

}
