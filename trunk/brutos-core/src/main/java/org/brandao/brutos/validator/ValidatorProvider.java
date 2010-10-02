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

package org.brandao.brutos.validator;

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

    public static ValidatorProvider getValidatorProvider( Configuration config ){
        String validatorProviderName =
            config.getProperty(
                "org.brandao.brutos.validator.provider",
                "org.brandao.brutos.validator.DefaultValidatorProvider" );
        try{
            ValidatorProvider provider = null;
            Class<?> validatorProvider =
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
    public abstract void configure( Configuration config );

    /**
     * Get a new validator.
     * @param config Configuration.
     * @return The validator.
     */
    public abstract Validator getValidator( Configuration config );

    
}
