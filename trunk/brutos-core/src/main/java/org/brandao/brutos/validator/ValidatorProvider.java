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

package org.brandao.brutos.validator;

import java.util.Properties;
import org.brandao.brutos.Configuration;
import org.brandao.brutos.BrutosException;
/**
 * Validator provider.
 * 
 * @author Afonso Brandao
 */
public abstract class ValidatorProvider {

    /**
     * Get a new validator provider.
     * @param config Configuration.
     * @return New validator provider.
     */

    public static ValidatorProvider getValidatorProvider( Properties config ){
        String validatorProviderName =
            config.getProperty(
                "org.brandao.brutos.validator.provider",
                "org.brandao.brutos.validator.DefaultValidatorProvider" );
        try{
            ValidatorProvider provider = null;
            Class validatorProvider =
                Class.forName(
                    validatorProviderName,
                    true,
                    Thread.currentThread().getContextClassLoader() );
            
            provider = (ValidatorProvider)validatorProvider.newInstance();
            provider.configure( config );
            return provider;
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
    }

    /**
     * Configure validator provider.
     * @param config Configuration.
     */
    public abstract void configure( Properties config );

    /**
     * Get a new validator.
     * @param config Configuration.
     * @return The validator.
     */
    public abstract Validator getValidator( Properties config );

    
}
