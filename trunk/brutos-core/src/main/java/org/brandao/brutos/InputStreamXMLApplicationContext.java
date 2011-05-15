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
import org.brandao.brutos.io.InputStreamResource;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.xml.AbstractXMLApplicationContext;

/**
 *
 * @author Brandao
 */
public class InputStreamXMLApplicationContext
        extends AbstractXMLApplicationContext{

    private Resource[] resources;

    public InputStreamXMLApplicationContext( InputStream[] inputs,
            AbstractApplicationContext parent ){
        super( parent );

        resources = new Resource[inputs.length];
        for( int i=0;i<inputs.length;i++ )
            resources[i] = new InputStreamResource( inputs[i] );
    }

    public InputStreamXMLApplicationContext( InputStream[] inputs ){
        this(inputs,null);
    }

    public InputStreamXMLApplicationContext( InputStream input ){
        this(new InputStream[]{input}, null);
    }

    protected Resource[] getContextResources() {
        return resources;
    }

}
