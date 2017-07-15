/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
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

package org.brandao.brutos.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;

/**
 * 
 * @author Brandao
 */
public class BeanInstance {

	private static Map<Class<?>, BeanData> cache;

	static {
		cache = new HashMap<Class<?>, BeanData>();
	}

	private Object object;
	private Class<?> clazz;
	private BeanData data;

	public BeanInstance(Object object) {
		this(object, object.getClass());
	}

	public BeanInstance(Object object, Class<?> clazz) {
		this.object = object;
		this.clazz = clazz;
		this.data = getBeanData(this.clazz);
	}

	public void set(String property, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		getProperty(property).set(object, value);
	}

	public Object get(String property) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return getProperty(property).get(object);
	}

	public void set(String property, Object source, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		getProperty(property).set(source, value);
	}

	public Object get(String property, Object source)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return getProperty(property).get(source);
	}

	public BeanProperty getProperty(String property) {

		BeanProperty prop = data.getProperty(property);
		if (prop == null)
			throw new BrutosException("not found: " + clazz.getName() + "."
					+ property);
		else
			return prop;
	}

	/*
	private void loadFields(BeanData data, Class<?> clazz){
	
		Field[] fields = clazz.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			int mod = f.getModifiers();
			
			if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
				continue;
			}
			
			data.addProperty(f.getName(), new BeanPropertyImp(f, null,
					null, f.getName()));
			data.getSetter().put(f.getName(), f);
			data.getGetter().put(f.getName(), f);
		}
		
	}
	 */
	
	private void loadFields(BeanData data, Class<?> clazz){
		
		Class<?> superClass = clazz.getSuperclass();
		
		if(superClass != Object.class){
			loadFields(data, superClass);
		}
		
		Field[] fields = clazz.getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			int mod = f.getModifiers();
			
			if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
				continue;
			}
			
			data.addProperty(f.getName(), new BeanPropertyImp(f, null,
					null, f.getName()));
			data.getSetter().put(f.getName(), f);
			data.getGetter().put(f.getName(), f);
		}
		
	}
	
	private void loadMethods(BeanData data, Class<?> clazz){
		
		Method[] methods = clazz.getMethods();

		for (int i = 0; i < methods.length; i++) {
			
			Method method = methods[i];
			String methodName = method.getName();

			if (methodName.equals("getClass")){
				continue;
			}

			if (methodName.startsWith("set")
					&& method.getParameterTypes().length == 1) {
				String id = methodName.substring(3, methodName.length());

				id = Character.toLowerCase(id.charAt(0))
						+ id.substring(1, id.length());

				if (data.getProperty(id) != null){
					data.getProperty(id).setSet(method);
				}
				else{
					data.addProperty(
						id, 
						new BeanPropertyImp(null, method, null, id));
				}

				data.getSetter().put(id, method);
			}
			else
			if (methodName.startsWith("get") && 
				method.getParameterTypes().length == 0 &&
				method.getReturnType() != void.class) {
				
				String id = methodName.substring(3, methodName.length());

				id = Character.toLowerCase(id.charAt(0))
						+ id.substring(1, id.length());

				if (data.getProperty(id) != null)
					data.getProperty(id).setGet(method);
				else
					data.addProperty(id, new BeanPropertyImp(null, null,
							method, id));

				data.getGetter().put(id, method);
			}
			else
			if (methodName.startsWith("is") &&
				method.getParameterTypes().length == 0 &&
				ClassUtil.getWrapper(method.getReturnType()) == Boolean.class){
				
				String id = methodName.substring(2, methodName.length());

				id = Character.toLowerCase(id.charAt(0))
						+ id.substring(1, id.length());

				if (data.getProperty(id) != null)
					data.getProperty(id).setGet(method);
				else
					data.addProperty(id, new BeanPropertyImp(null, null,
							method, id));

				data.getGetter().put(id, method);
			}
		}
		
	}
	
	private BeanData getBeanData(Class<?> clazz) {

		if (cache.containsKey(clazz)){
			return (BeanData) cache.get(clazz);
		}
		
		BeanData data = new BeanData();
		data.setClassType(clazz);
		
		this.loadFields(data, clazz);
		this.loadMethods(data, clazz);
		
		cache.put(clazz, data);
		return data;
	}

	public boolean containProperty(String property) {
		return data.getProperties().containsKey(property);
	}

	public Class<?> getType(String property) {
		// Method method = (Method) data.getGetter().get(property);

		BeanProperty prop = data.getProperty(property);

		if (prop == null)
			throw new BrutosException("not found: " + clazz.getName() + "."
					+ property);

		// return method.getReturnType();

		return prop.getType();
	}

	public Object getGenericType(String property) {

		BeanProperty prop = data.getProperty(property);
		// Method method = (Method) data.getGetter().get(property);

		if (prop == null)
			throw new BrutosException("not found: " + clazz.getName() + "."
					+ property);

		return prop.getGenericType();

	}

	public Class<?> getClassType() {
		return this.clazz;
	}

	public List<BeanProperty> getProperties() {
		return new LinkedList<BeanProperty>(this.data.getProperties().values());
	}

}

class BeanData {

	private Class<?> classType;
	private Map<String, Object> setter;
	private Map<String, Object> getter;
	private Map<String, BeanProperty> properties;

	public BeanData() {
		this.setter     = new HashMap<String, Object>();
		this.getter     = new HashMap<String, Object>();
		this.properties = new HashMap<String, BeanProperty>();
	}

	public void addProperty(String name, BeanProperty property) {
		this.properties.put(name, property);
	}

	public BeanProperty getProperty(String name) {
		return this.properties.get(name);
	}

	public Class<?> getClassType() {
		return classType;
	}

	public void setClassType(Class<?> classType) {
		this.classType = classType;
	}

	public Map<String, Object> getSetter() {
		return setter;
	}

	public void setSetter(Map<String, Object> setter) {
		this.setter = setter;
	}

	public Map<String, Object> getGetter() {
		return getter;
	}

	public void setGetter(Map<String, Object> getter) {
		this.getter = getter;
	}

	public Map<String, BeanProperty> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, BeanProperty> properties) {
		this.properties = properties;
	}
}