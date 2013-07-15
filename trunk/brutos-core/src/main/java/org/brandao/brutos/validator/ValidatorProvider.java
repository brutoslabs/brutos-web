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
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
/**
 * Provê os objetos que auxiliam na validação dos dados da requisição.
 * 
 * @author Afonso Brandao
 */
public abstract class ValidatorProvider {

    /**
     * Obtém um novo provedor de validação.
     * @param config Configuração da aplicação.
     * @return Provedor de validação.
     */

    public static ValidatorProvider getValidatorProvider( Properties config ){
        String validatorProviderName =
            config.getProperty(
                BrutosConstants.VALIDATOR_PROVIDER_CLASS,
                BrutosConstants.DEFAULT_VALIDATOR_PROVIDER );
        try{
            ValidatorProvider provider;
            Class validatorProvider = ClassUtil.get(validatorProviderName);
            
            provider = (ValidatorProvider)ClassUtil.getInstance(validatorProvider);
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
     * Aplica as configurações da aplicação
     * @param config Configuração da aplicação.
     */
    public abstract void configure( Properties config );

    /**
     * Obtém um novo validador.
     * @param config Configuração do validador.
     * @return Validador.
     */
    public abstract Validator getValidator( Properties config );

    
}
