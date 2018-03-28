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

package org.brandao.brutos.mapping;

import java.lang.reflect.Constructor;
import java.util.List;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.type.AnyType;
import org.brandao.brutos.validator.Validator;

/**
 * 
 * @author Brandao
 */
public class ConstructorBean {

	private List<ConstructorArgBean> args;

	private Constructor<?> contructor;

	private Method method;

	private String methodFactory;

	private Bean bean;

	private Validator validator;

	private boolean collection;

	public ConstructorBean(Bean bean) {
		this.args = new ArrayList<ConstructorArgBean>();
		this.bean = bean;
		this.collection = bean.getParent() != null
				&& bean.getParent().isCollection();
	}

	public boolean isCollection() {
		return collection;
	}

	public boolean isConstructor() {
		return getMethodFactory() == null;
	}

	public boolean isMethodFactory() {
		return getMethodFactory() != null;
	}

	public Constructor<?> getContructor() {
		initIfNecessary();
		return contructor;
	}

	private synchronized void initIfNecessary() {
		if (contructor == null)
			setContructor(getContructor(getBean().getClassType()));
	}

	public Method getMethod(Object factory) {
		if (getMethod() == null) {
			Class<?> clazz = 
				factory == null ? 
					getBean().getClassType() : 
					factory.getClass();

			setMethod(getMethod(getMethodFactory(), clazz));
			if (getMethod().getReturnType() == void.class)
				throw new BrutosException("invalid return: "
						+ getMethod().toString());
		}
		return getMethod();
	}
	
	/*
	public Method getMethod(Object factory) {
		if (getMethod() == null) {
			Class<?> clazz = factory == null ? getBean().getClassType() : factory
					.getClass();

			setMethod(getMethod(getMethodFactory(), clazz));
			if (getMethod().getReturnType() == void.class)
				throw new BrutosException("invalid return: "
						+ getMethod().toString());
		}
		return getMethod();
	}
*/
	
	public void addConstructorArg(ConstructorArgBean arg) {
		this.args.add(arg);
	}

	public ConstructorArgBean getConstructorArg(int index) {
		return args.get(index);
	}

	public List<ConstructorArgBean> getConstructorArgs() {
		return args;
	}
	
	public int size() {
		return this.args.size();
	}

	private Constructor<?> getContructor(Class<?> clazz) {
		int size = size();
		Class<?>[] classArgs = new Class[size];
		for (int i = 0; i < size; i++) {
			ConstructorArgBean arg = getConstructorArg(i);
			classArgs[i] = arg.getClassType();
		}
		Constructor<?>[] cons = clazz.getConstructors();
		for (int i = 0; i < cons.length; i++) {
			Constructor<?> con = cons[i];
			if (isCompatible(con, classArgs)) {
				Class<?>[] params = con.getParameterTypes();
				for (int k = 0; k < params.length; k++) {
					
					ConstructorArgBean arg = getConstructorArg(k);
					
					String parameterName = ParameterNameResolver.getName(con, k);
					arg.setRealName(parameterName);
					
					if(BrutosConstants.EMPTY_ARGNAME.equals(arg.getParameterName())){
						arg.setParameterName(parameterName);	
					}
					
					if (getConstructorArg(k).getType() == null) {
						ConstructorArgBean argBean = getConstructorArg(k);

						if (argBean.getMetaBean() != null)
							argBean.setType(new AnyType(params[k]));
						else {
							argBean.setType(((ConfigurableApplicationContext) this
									.getBean().getController().getContext())
									.getTypeManager().getType(params[k],
											argBean.getEnumProperty(),
											argBean.getTemporalType()));
						}
					}
				}
				return con;
			}
		}

		String msg = "not found: " + clazz.getName() + "( ";

		for (int i = 0; i < classArgs.length; i++) {
			Class<?> arg = classArgs[i];
			msg += i != 0 ? ", " : "";
			msg += arg == null ? "?" : arg.getName();
		}
		msg += " )";

		throw new BrutosException(msg);
	}

	private Method getMethod(String name, Class<?> clazz) {
		int size = size();
		Class<?>[] classArgs = new Class[size];
		for (int i = 0; i < size; i++) {
			ConstructorArgBean arg = getConstructorArg(i);
			classArgs[i] = arg.getClassType();
		}

		Class<?> tmpClazz = clazz;
		while (tmpClazz != Object.class) {
			Method[] methods = tmpClazz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				Method m = methods[i];
				if (m.getName().equals(name) && isCompatible(m, classArgs)) {
					Class<?>[] params = m.getParameterTypes();
					for (int k = 0; k < params.length; k++) {
						if (getConstructorArg(k).getType() == null) {
							getConstructorArg(k).setType(
									((ConfigurableApplicationContext) this
											.getBean().getController()
											.getContext()).getTypeManager()
											.getType(params[k]));
						}
					}

					return m;
				}
			}
			tmpClazz = tmpClazz.getSuperclass();
		}
		String msg = "not found: " + clazz.getName() + "." + name + "( ";

		for (int i = 0; i < classArgs.length; i++) {
			Class<?> arg = classArgs[i];
			msg += i != 0 ? ", " : "";
			msg += arg == null ? "?" : arg.getName();
		}
		msg += " )";

		throw new BrutosException(msg);
	}

	private boolean isCompatible(Constructor<?> m, Class<?>[] classArgs) {
		Class<?>[] params = m.getParameterTypes();
		if (params.length == classArgs.length) {
			for (int i = 0; i < params.length; i++) {
				if (classArgs[i] != null
						&& !params[i].isAssignableFrom(classArgs[i]))
					return false;
			}
			return true;
		} else
			return false;

	}

	private boolean isCompatible(Method m, Class<?>[] classArgs) {
		Class<?>[] params = m.getParameterTypes();
		if (params.length == classArgs.length) {
			for (int i = 0; i < params.length; i++) {
				if (classArgs[i] != null
						&& !ClassUtil.getWrapper(params[i]).isAssignableFrom(
								ClassUtil.getWrapper(classArgs[i])))
					return false;
			}
			return true;
		} else
			return false;

	}
	
	public void setContructor(Constructor<?> contructor) {
		this.contructor = contructor;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getMethodFactory() {
		return methodFactory;
	}

	public void setMethodFactory(String methodFactory) {
		this.methodFactory = methodFactory;
	}

	public Bean getBean() {
		return bean;
	}

	public void setBean(Bean bean) {
		this.bean = bean;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

}
