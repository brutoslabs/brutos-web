/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Brandao
 */
public class ListTypeTest extends TestCase{

    public void test1(){
        ParameterizedType classType = new ParameterizedType(){

            public Type[] getActualTypeArguments() {
                return new Type[]{Integer.class};
            }

            public Type getRawType() {
                return List.class;
            }

            public Type getOwnerType() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
        };
        
        TypeManagerImp manager = new TypeManagerImp();
        manager.register(new DefaultTypeFactory(ListType.class, List.class));
        CollectionType type = (CollectionType) manager.getType(classType,null,null);
    }
}
