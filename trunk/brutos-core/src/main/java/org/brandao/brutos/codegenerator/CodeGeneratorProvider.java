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


package org.brandao.brutos.codegenerator;

import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ioc.IOCProvider;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.proxy.ProxyFactory;

/**
 *
 * @author Brandao
 */
public abstract class CodeGeneratorProvider {

    public static CodeGeneratorProvider getProvider( Properties properties ){
        
        Logger logger = LoggerProvider
                .getCurrentLoggerProvider()
                    .getLogger(IOCProvider.class.getName());
        
        String providerName =
                properties
                    .getProperty(
                        "org.brandao.brutos.proxy.provider",
                        JavassistCodeGeneratorProvider.class.getName());
        CodeGeneratorProvider provider = null;

        logger.info("CodeGenerator provider: " + providerName);
        
        try{
            Class providerClass = Class.forName( providerName, true,
                    Thread.currentThread().getContextClassLoader() );
            provider = (CodeGeneratorProvider)providerClass.newInstance();
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

        return provider;
    }

    public abstract ProxyFactory getProxyFactory( Class classEntity )
            throws BrutosException;
    
}
