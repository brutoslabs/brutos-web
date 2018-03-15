package org.brandao.brutos.mapping;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.brandao.brutos.ClassUtil;

public class ParameterNameResolver {

	private static Method getParametersNameMethodByMethod;

	private static Method getParametersNameMethodByCosntructor;
	
	private static Method getListItem;

	private static Method getName;
	
	static{
		loadParametersNameMethodByMethod();
		loadParametersNameMethodByconstructor();
		loadGetItemListMethod();
		loadGetNameMethod();
	}
	
	private static void loadParametersNameMethodByMethod(){
		try{
			getParametersNameMethodByMethod = Method.class.getMethod("getParameters");
		}
		catch(NoSuchMethodException e){
			getParametersNameMethodByMethod = null;
		}
		catch(Throwable e){
			throw new ExceptionInInitializerError(e);
		}
	}

	private static void loadParametersNameMethodByconstructor(){
		try{
			getParametersNameMethodByCosntructor = Constructor.class.getMethod("getParameters");
		}
		catch(NoSuchMethodException e){
			getParametersNameMethodByCosntructor = null;
		}
		catch(Throwable e){
			throw new ExceptionInInitializerError(e);
		}
	}
	
	private static void loadGetItemListMethod(){
		try{
			getListItem = List.class.getMethod("get", int.class);
		}
		catch(Throwable e){
			throw new ExceptionInInitializerError(e);
		}
	}

	private static void loadGetNameMethod(){
		try{
			Class<?> clazz = ClassUtil.get("java.lang.reflect.Parameter");
			getName        = clazz.getMethod("getName");
		}
		catch(ClassNotFoundException e){
			getName = null;
		}
		catch(Throwable e){
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static String getName(Method origin, int index){
		
		try{
			if(getParametersNameMethodByMethod == null){
				return "arg" + index;
			}
			
			Object parameters = getParameters(origin);
			Object parameter = getItem(parameters, index);
			
			return (String)getParameterName(parameter);
		}
		catch(InvocationTargetException e){
			throw new MappingException(e.getCause());
		}
		catch(Throwable e){
			throw new MappingException(e);
		}
		
	}
	
	public static String getName(Constructor<?> origin, int index){
		
		try{
			if(getParametersNameMethodByCosntructor == null){
				return "arg" + index;
			}
			
			Object parameters = getParameters(origin);
			Object parameter = getItem(parameters, index);
			
			return (String)getParameterName(parameter);
		}
		catch(InvocationTargetException e){
			throw new MappingException(e.getCause());
		}
		catch(Throwable e){
			throw new MappingException(e);
		}
	}
	
	private static Object getParameters(Method method) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return getParametersNameMethodByMethod.invoke(method);
	}

	private static Object getParameters(Constructor<?> constructor) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return getParametersNameMethodByCosntructor.invoke(constructor);
	}
	
	private static Object getItem(Object list, int index) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return getListItem.invoke(list, index);
	}

	private static Object getParameterName(Object parameter) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return (String)getName.invoke(parameter);
	}
	
}
