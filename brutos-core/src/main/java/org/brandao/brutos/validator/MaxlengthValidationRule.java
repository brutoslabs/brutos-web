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
import org.brandao.brutos.type.Type;
import org.brandao.brutos.type.Types;
import org.brandao.brutos.type.TypeManager;

/**
 *
 * @author Brandao
 */
public class MaxlengthValidationRule implements ValidationRule{

    private Type integerType = TypeManager.getType(Integer.class);

    public void validate(Properties config, Object source, Object value) {
        if( value instanceof String ){
            if( config.containsKey( RestrictionRules.MAXLENGTH.toString() ) ){
                Number tmp = (Number) integerType
                                .convert(
                                //.getValue(
                                config.get(RestrictionRules.MAXLENGTH.toString()));

                if( ((String)value).length() > tmp.intValue() )
                    throw new ValidatorException();
            }
        }
    }

}
