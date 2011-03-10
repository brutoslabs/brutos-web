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


package org.brandao.brutos.xml;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.brandao.brutos.web.WebApplicationContext;

/**
 *
 * @author Brandao
 */
public class BuildApplicationTest extends TestCase {
/*
    public void testImportFile() throws Exception{
        URL urlConfig = (new File("test.xml")).toURI().toURL();

        WebApplicationContext app = new WebApplicationContext();

        BuildApplication buildApplication = 
            new BuildApplication( urlConfig,app, new ArrayList() );

        buildApplication.build();
    }
*/
    public void testImportClassPath() throws Exception{
        URL urlConfig = Thread.currentThread()
                .getContextClassLoader().getResource( "brutos-config.xml" );

        WebApplicationContext app = new WebApplicationContext();

        BuildApplication buildApplication =
            new BuildApplication( urlConfig,app, new ArrayList() );

        buildApplication.build();
    }

}
