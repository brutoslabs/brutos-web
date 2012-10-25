/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.old.programatic.Bean;

/**
 *
 * @author Afonso Brandao
 */
public abstract class IOCProvider {

    /**
     * @deprecated 
     */
    private Map beans;
    
    public IOCProvider(){
        this.beans = new HashMap();
    }
    
    public static IOCProvider getProvider( Properties properties ){

        Logger logger = LoggerProvider
                .getCurrentLoggerProvider()
                    .getLogger(IOCProvider.class.getName());
        
        String iocProviderName = 
                properties
                    .getProperty(
                        "org.brandao.brutos.ioc.provider",
                        null);
        IOCProvider ioc        = null;
        
        if( iocProviderName == null )
            throw new BrutosException("IoC provider not configured");

        try{
            logger.info("IoC provider: " + iocProviderName );
            Class iocProvider = Class.forName( iocProviderName, true,
                    Thread.currentThread().getContextClassLoader() );
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

    /**
     * @deprecated
     * @param name
     * @return
     */
    public boolean containsBeanDefinition( String name ){
        return beans.containsKey( name );
    }

    /**
     * @deprecated
     * @param bean
     */
    public void addBeanDefinition( Bean bean ){
        beans.put( bean.getInjectable().getName(), bean );
    }

    /**
     * @deprecated 
     * @param bean
     * @return
     */
    public Bean removeBeanDefinition( Bean bean ){
        if( bean != null )
            return (Bean) beans.remove( bean.getInjectable().getName() );
        else
            return null;
    }

    /**
     * @deprecated 
     * @param name
     * @return
     */
    public Bean getBeanDefinition( String name ){
        return (Bean) beans.get( name );
    }

    /**
     * @deprecated 
     * @return
     */
    public List getBeansDefinition(){
        return new ArrayList( beans.values() );
    }

    public abstract Object getBean( String name );
    
    public abstract Object getBean( Class clazz );

    public abstract void configure( Properties properties );

    /**
     * @deprecated 
     * @param name
     * @return
     */
    public Object getInstance( String name ){
        throw new UnsupportedOperationException( "use getBean(String)" );
    }

    public abstract void destroy();
    
}
