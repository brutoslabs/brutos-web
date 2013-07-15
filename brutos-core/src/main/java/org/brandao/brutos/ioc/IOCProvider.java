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

import java.util.Properties;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;

/**
 * Provê os objetos gerenciados pelo container IoC.
 * 
 * @author Afonso Brandao
 */
public abstract class IOCProvider {

    public IOCProvider(){
    }
    
    public static IOCProvider getProvider( Properties properties ){

        Logger logger = LoggerProvider
                .getCurrentLoggerProvider()
                    .getLogger(IOCProvider.class.getName());
        
        String iocProviderName = 
                properties
                    .getProperty(
                        BrutosConstants.IOC_PROVIDER_CLASS,
                        null);
        IOCProvider ioc        = null;
        
        if( iocProviderName == null )
            throw new BrutosException("IoC provider not configured");

        try{
            logger.info("IoC provider: " + iocProviderName );
            Class iocProvider = ClassUtil.get(iocProviderName);
            ioc = (IOCProvider)ClassUtil.getInstance(iocProvider);
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
        
        return ioc;
    }

    /**
     * Obtém um objeto a partir de seu nome.
     * @param name Nome que identifica o objeto.
     * @return Objeto.
     */
    public abstract Object getBean( String name );
    
    /**
     * Obtém um objeto a partir de seu classe.
     * @param clazz Classe do objeto.
     * @return Objecto.
     */
    public abstract Object getBean( Class clazz );

    /**
     * Aplica a configuração da aplicação.
     * @param properties Configuração da aplicação.
     */
    public abstract void configure( Properties properties );

    /**
     * Desliga o container  IoC.
     */
    public abstract void destroy();
    
}
