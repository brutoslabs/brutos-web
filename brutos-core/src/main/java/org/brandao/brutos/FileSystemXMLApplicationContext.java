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

import org.brandao.brutos.io.FileSystemResource;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.xml.AbstractXMLApplicationContext;

/**
 *
 * @author Brandao
 */
public class FileSystemXMLApplicationContext
        extends AbstractXMLApplicationContext{

    private Resource[] resources;

    public FileSystemXMLApplicationContext( String[] locations,
            ApplicationContext parent ){
        super( parent );

        resources = new Resource[locations.length];
        for( int i=0;i<locations.length;i++ )
            resources[i] = new FileSystemResource( locations[i] );
    }

    public FileSystemXMLApplicationContext( String[] locations ){
        this(locations,null);
    }

    public FileSystemXMLApplicationContext( String location ){
        this(new String[]{location}, null);
    }

    protected Resource getContextResource( String path ){
        return new FileSystemResource( path );
    }

    protected Resource[] getContextResources() {
        return resources;
    }

}
