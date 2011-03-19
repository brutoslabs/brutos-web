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

import java.io.InputStream;
import java.util.Properties;
import junit.framework.TestCase;
import org.brandao.brutos.test.MockMvcRequestFactory;
import org.brandao.brutos.test.MockMvcResponseFactory;
import org.brandao.brutos.test.MockViewProvider;

/**
 *
 * @author Brandao
 */
public class InputStreamXMLApplicationContextTest extends TestCase{

    private Properties config;

    public InputStreamXMLApplicationContextTest(){
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

    public void testMultipleInputStream(){
        InputStream input =
                XMLHelper.getInputStream(XMLHelper.getSimpleXMLApplicationContext());

        InputStream[] array = new InputStream[]{input,input};
        InputStreamXMLApplicationContext app =
            new InputStreamXMLApplicationContext(array);

        app.configure(config);
    }

    public void testInputStreamApplicationContext(){
        InputStream input =
                XMLHelper.getInputStream(XMLHelper.getSimpleXMLApplicationContext());

        InputStreamXMLApplicationContext app =
            new InputStreamXMLApplicationContext(input);

        app.configure(config);
    }

    public void testInputStreamApplicationContextwithParent(){
        ApplicationContext parent = new ApplicationContext(){};
        InputStream input =
                XMLHelper.getInputStream(XMLHelper.getSimpleXMLApplicationContext());

        InputStream[] array = new InputStream[]{input};
        InputStreamXMLApplicationContext app =
            new InputStreamXMLApplicationContext(array,parent);

        app.configure(config);
    }

}
