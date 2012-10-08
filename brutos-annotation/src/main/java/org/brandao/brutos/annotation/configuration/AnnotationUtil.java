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

import java.util.*;
import org.brandao.brutos.*;
import org.brandao.brutos.annotation.*;
import org.brandao.brutos.mapping.StringUtil;
import org.brandao.brutos.type.TypeManager;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.WebApplicationContext;

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

    public static boolean isInterceptorStack(Class clazz){
        boolean isInterceptor =
            isInterceptor(clazz) && 
            (clazz.isAnnotationPresent(InterceptsStackList.class) ||
             clazz.isAnnotationPresent(InterceptsStack.class));
        
        return isInterceptor;
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
    
    public static org.brandao.brutos.type.Type getTypeInstance(Type value){
        if(value != null){
            Class typeClass = value.value();
            return (org.brandao.brutos.type.Type)getTypeInstance(typeClass);
        }
        else
            return null;
    }
    
    public static org.brandao.brutos.type.Type getTypeInstance(Class value){
        try{
            if(value != null)
                return (org.brandao.brutos.type.Type)ClassUtil.getInstance(value);
            else
                return null;
        }
        catch(Exception e){
            throw new BrutosException(e);
        }
    }
    
    public static String getTemporalProperty(Temporal value){
        if(value != null)
            return value.value();
        else
            return BrutosConstants.DEFAULT_TEMPORALPROPERTY;
    }
    
    public static org.brandao.brutos.EnumerationType getEnumerationType(Enumerated value){
        if(value != null){
            return 
                org.brandao.brutos.EnumerationType
                    .valueOf(value.value().name().toLowerCase());
        }
        else
            return BrutosConstants.DEFAULT_ENUMERATIONTYPE;
    }
    
    public static org.brandao.brutos.ScopeType getScope(Identify value){
        
        if(value != null){
            String scope = StringUtil.adjust(value.scope());
            if(!StringUtil.isEmpty(scope))
                return org.brandao.brutos.ScopeType.valueOf(value.scope());
        }
        
        return BrutosConstants.DEFAULT_SCOPETYPE;
    }

    public static boolean isBuildEntity(Boolean buildIfNecessary, Class type){
        boolean isStandardType = TypeManager.isStandardType(type);
        
        isStandardType = isStandardType && !type.isAnnotationPresent(Bean.class);
        
        return
            buildIfNecessary == null?
                !isStandardType : 
                buildIfNecessary.booleanValue() || !isStandardType;
    }

    public static boolean isBuildEntity(KeyCollection identify, Class type){
        return isBuildEntity(identify == null? null : identify.useMapping(), type);
    }

    public static boolean isBuildEntity(ElementCollection identify, Class type){
        return isBuildEntity(identify == null? null : identify.useMapping(), type);
    }
    
    public static boolean isBuildEntity(Identify identify, Class type){
        return isBuildEntity(identify == null? null : identify.useMapping(), type);
    }
    
    public static Object getKeyType(Object type){
        Class rawType = TypeManager.getRawType(type);
        Object keyType = TypeManager.getKeyType(type);
        
        if(keyType != null)
            return keyType;
        
        if(isMap(rawType))
            return getKeyType(rawType.getGenericSuperclass());
        
        return null;
    }
    
    public static Object getCollectionType(Object type){
        Class rawType = TypeManager.getRawType(type);
        Object elementType = TypeManager.getCollectionType(type);
        
        if(elementType != null)
            return elementType;
        
        if(isMap(rawType) || isCollection(rawType))
            return getCollectionType(rawType.getGenericSuperclass());
        else
            return null;
            
    }

    public static boolean isCollection(Class clazz){
        return isMap(clazz) || Collection.class.isAssignableFrom(clazz);
    }
    
    public static boolean isMap(Class clazz){
        return Map.class.isAssignableFrom(clazz);
    }
    
    public static boolean isWebApplication(ApplicationContext applicationContext){
        Properties config = applicationContext.getConfiguration();
        return config.getProperty(BrutosConstants.WEB_APPLICATION_CLASS) != null;
    }
}
