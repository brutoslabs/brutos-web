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

import org.brandao.brutos.io.ClassPathResource;
import org.brandao.brutos.io.Resource;
import org.brandao.brutos.xml.AbstractXMLApplicationContext;

/**
 *
 * @author Brandao
 */
public class ClassPathXMLApplicationContext
        extends AbstractXMLApplicationContext{

    private Resource[] resources;

    public ClassPathXMLApplicationContext( String[] locations, 
            ApplicationContext parent, ClassLoader classLoader, Class clazz ){
        super( parent );

        resources = new Resource[locations.length];
        for( int i=0;i<locations.length;i++ ){

            if( clazz != null )
                resources[i] = new ClassPathResource( clazz, locations[i] );
            else
            if( classLoader != null )
                resources[i] = new ClassPathResource( classLoader, locations[i] );
        }
    }

    public ClassPathXMLApplicationContext( String[] locations ){
        this( locations, null,
                Thread.currentThread().getContextClassLoader(), null );
    }

    public ClassPathXMLApplicationContext( String location ){
        this( new String[]{location}, null,
                Thread.currentThread().getContextClassLoader(), null );
    }

    public ClassPathXMLApplicationContext( String location,
            ApplicationContext parent ){
        this( new String[]{location}, parent,
                Thread.currentThread().getContextClassLoader(), null );
    }

    public ClassPathXMLApplicationContext( String[] locations,
            Class clazz ){
        this( locations, null, null, clazz );
    }

    public ClassPathXMLApplicationContext( String[] locations,
            ClassLoader classLoader ){
        this( locations, null, classLoader, null );
    }

    protected Resource getContextResource( String path ){
        return new ClassPathResource( this.getClassloader(),path );
    }

    protected Resource[] getContextResources() {
        return resources;
    }

}
