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

package org.brandao.brutos.mapping;

/**
 *
 * @author Brandao
 */
public class StringUtil {
    
    public static boolean isEmpty(String value){
        return value == null || value.trim().length() == 0;
    }
    
    public static String trimLeft(String value){
        return value == null? null : value.replaceAll("^\\s+", "");
    }
    
    public static String trimRight(String value){
        return value == null? null : value.replaceAll("\\s+$", "");
    }
    
    public static String adjust(String value){
        if(value != null){
            String tmp = trimLeft(trimRight(value));
            return isEmpty(tmp)? null : tmp;
        }
        else
            return null;
    }
}
