

package org.brandao.brutos.type;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.brandao.brutos.BrutosException;


public final class TypeUtil {
    
    private static Class defaultListType;
    
    private static Class defaultSetType;
    
    private static Class defaultMapType;
    
    static{
        defaultListType = ArrayList.class;
        defaultSetType = HashSet.class;
        defaultMapType = HashMap.class;
    }
    
    
    public static Class getRawType(Object type) {
    	
    	if(type == null)
    		return null;
    	
        try{
            Class parameterizedTypeClass =
                    Class.forName("java.lang.reflect.ParameterizedType");

            if (parameterizedTypeClass.isAssignableFrom(type.getClass())) {
                Method getRawType =
                        parameterizedTypeClass.getMethod("getRawType", new Class[]{});

                Object clazz = getRawType.invoke(type, new Object[]{});
                return (Class) clazz;
            } else if (type instanceof Class) {
                return (Class) type;
            } else {
                throw new BrutosException("invalid type: " + type);
            }
        } catch (ClassNotFoundException ex) {
            if (type instanceof Class) {
                return (Class) type;
            } else {
                throw new BrutosException("invalid type: " + type);
            }
        } catch (Exception e) {
            throw new BrutosException(e);
        }
    }

    
    public static Object getCollectionType(Object type) {
        int index = -1;

        Class rawType = getRawType(type);

        if (Map.class.isAssignableFrom(rawType)) {
            index = 1;
        } else if (Collection.class.isAssignableFrom(rawType)) {
            index = 0;
        }

        return getParameter(type, index);
    }

    
    public static Object getKeyType(Object type) {
        int index = -1;

        Class rawType = getRawType(type);

        if (Map.class.isAssignableFrom(rawType)) {
            index = 0;
        }

        return getParameter(type, index);
    }

    
    public static Object getParameter(Object type, int index) {
        try{
            Object args = getParameters(type);
            return args == null? null : Array.get(args, index);
        }
        catch (Exception e){
            return null;
        }
    }

    public static Object[] getParameters(Object type) {
        try{
            Class parameterizedTypeClass =
                    Class.forName("java.lang.reflect.ParameterizedType");

            if (parameterizedTypeClass.isAssignableFrom(type.getClass())) {
                Method getRawType =
                        parameterizedTypeClass.getMethod("getActualTypeArguments", new Class[]{});

                Object args = getRawType.invoke(type, new Object[]{});

                return (Object[])args;
            }
            else
                return null;
        }
        catch (Exception e){
            return null;
        }
    }
    
    public static Class getDefaultListType() {
        return defaultListType;
    }

    public static void setDefaultListType(Class aDefaultListType) {
        defaultListType = aDefaultListType;
    }

    public static Class getDefaultSetType() {
        return defaultSetType;
    }

    public static void setDefaultSetType(Class aDefaultSetType) {
        defaultSetType = aDefaultSetType;
    }

    public static Class getDefaultMapType() {
        return defaultMapType;
    }

    public static void setDefaultMapType(Class aDefaultMapType) {
        defaultMapType = aDefaultMapType;
    }
    
}
