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

package org.brandao.brutos.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpSessionEvent;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.old.programatic.Bean;

/**
 *
 * @author Afonso Brandao
 */
public abstract class IOCProvider {
    
    private Map<String, Bean> beans;
    
    public IOCProvider(){
        this.beans = new HashMap<String, Bean>();
    }
    
    public static IOCProvider getProvider( Properties properties ){
        String iocProviderName = 
                properties
                    .getProperty(
                        "org.brandao.brutos.ioc.provider",
                        "org.brandao.brutos.ioc.PicoContainerIOCProvider");
        IOCProvider ioc        = null;
        
        if( iocProviderName == null )
            return null;

        try{
            Class<?> iocProvider = Class.forName( iocProviderName, true, Thread.currentThread().getContextClassLoader() );
            ioc = (IOCProvider)iocProvider.newInstance();
        }
        catch( ClassNotFoundException e ){
            throw new BrutosException( e );
        }
        catch( InstantiationException e ){
            throw new BrutosException( e );
        }
        catch( IllegalAccessException e ){
            throw new BrutosException( e );
        }
        
        //ioc.configure( properties, sce );
        return ioc;
    }
    
    public boolean containsBeanDefinition( String name ){
        return beans.containsKey( name );
    }
    
    public void addBeanDefinition( Bean bean ){
        beans.put( bean.getInjectable().getName(), bean );
    }
    
    public Bean removeBeanDefinition( Bean bean ){
        if( bean != null )
            return beans.remove( bean.getInjectable().getName() );
        else
            return null;
    }
    
    public Bean getBeanDefinition( String name ){
        return beans.get( name );
    }
    
    public List<Bean> getBeansDefinition(){
        return new ArrayList( beans.values() );
    }

    public abstract Object getBean( String name );
    
    public abstract void configure( Properties properties );

    public Object getInstance( String name ){
        throw new UnsupportedOperationException( "use getBean(String)" );
    }
    
    public abstract void requestDestroyed(ServletRequestEvent sre);

    public abstract void requestInitialized(ServletRequestEvent sre);
    
    public abstract void sessionCreated(HttpSessionEvent se);

    public abstract void sessionDestroyed(HttpSessionEvent se);
    
    public abstract void destroy();
    
}
