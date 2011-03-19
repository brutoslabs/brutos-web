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
import junit.framework.TestCase;
import org.brandao.brutos.test.MockMvcRequestFactory;
import org.brandao.brutos.test.MockMvcResponseFactory;
import org.brandao.brutos.test.MockViewProvider;

/**
 *
 * @author Brandao
 */
public class ClassPathXMLApplicationContextTest extends TestCase{

    private static final String locationApplicationContext =
            "applicationContext.xml";

    private Properties config;

    public ClassPathXMLApplicationContextTest(){
        config = new Properties();

        config.setProperty(
            "org.brandao.brutos.controller.request_factory",
            MockMvcRequestFactory.class.getName());

        config.setProperty(
            "org.brandao.brutos.controller.response_factory",
            MockMvcResponseFactory.class.getName());

        config.setProperty( "org.brandao.brutos.view.provider",
            MockViewProvider.class.getName());
    }
    
    public void testMultipleClassPathApplicationContext(){
        ClassPathXMLApplicationContext app =
            new ClassPathXMLApplicationContext(
                new String[]{
                    locationApplicationContext,
                    locationApplicationContext});

        app.configure(config);
    }

    public void testClassPathApplicationContext(){
        ClassPathXMLApplicationContext app =
            new ClassPathXMLApplicationContext(
                    locationApplicationContext );

        app.configure(config);
    }

    public void testClassPathApplicationContextwithParent(){
        ApplicationContext parent = new ApplicationContext(){};
        ClassPathXMLApplicationContext app =
            new ClassPathXMLApplicationContext(
                    locationApplicationContext, parent);

        app.configure(config);
    }

    public void testClassPathApplicationContextWithClass(){
        ClassPathXMLApplicationContext app =
            new ClassPathXMLApplicationContext(
                    new String[]{locationApplicationContext},
                    ClassPathXMLApplicationContextTest.class);

        app.configure(config);
    }

    public void testClassPathApplicationContextWithClassLoader(){
        ClassPathXMLApplicationContext app =
            new ClassPathXMLApplicationContext(
                    new String[]{locationApplicationContext},
                    Thread.currentThread().getContextClassLoader());

        app.configure(config);
    }

}
