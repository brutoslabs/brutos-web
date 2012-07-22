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

package org.brandao.brutos.annotation.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.brandao.brutos.annotation.*;

/**
 *
 * @author Brandao
 */
public class AnnotationUtil {
    
    public static List<ThrowSafe> toList(ThrowSafeList value){
        return Arrays.asList(value.value());
    }
    
    public static ThrowableEntry toEntry(ThrowSafe value){
        return new ThrowableEntry(value);
    }
    
    public static List<ThrowableEntry> toList(List<ThrowSafe> list){
        
        List<ThrowableEntry> result = new ArrayList<ThrowableEntry>();
        
        for(ThrowSafe t: list)
            result.add(toEntry(t));
        
        return result;
    }
    
    public static boolean isInterceptor(Class clazz){
        boolean isInterceptor =
            clazz.getSimpleName().endsWith("InterceptorController") ||
            clazz.isAnnotationPresent(Intercepts.class);
        
        return isInterceptor && !isTransient(clazz);
    }
    
    public static boolean isController(Class clazz){
        boolean isController = 
               clazz.getSimpleName().endsWith("Controller") ||
               clazz.isAnnotationPresent(Controller.class);
        
        return isController && !isTransient(clazz) && !isInterceptor(clazz);
    }
    
    public static boolean isTransient(Class clazz){
        return clazz.isAnnotationPresent(Transient.class);
    }
    
}
