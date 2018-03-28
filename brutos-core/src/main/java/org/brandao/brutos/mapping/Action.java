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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ClassUtil;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.type.AnyType;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.validator.Validator;


/**
 * 
 * @author Brandao
 */
public class Action {

	private long code;

	private Controller controller;

	private ActionID id;

	private String name;

	private String simpleName;

	private List<ActionID> alias;

	private String executor;

	private List<ParameterAction> parameters;

	private Map<Class<?>, ThrowableSafeData> throwsSafe;

	private Method method;

	private List<Class<?>> parametersType;

	private boolean returnRendered;

	private String view;

	private boolean resolvedView;

	private ResultAction resultAction;
	
	/*
	private String returnIn;
	
	private Type returnType;

	private Class<?> returnClass;
	*/
	
	private boolean redirect;

	private DispatcherType dispatcherType;

	private Validator parametersValidator;
	
	private Validator resultValidator;
	
	private DataTypeMap requestTypes;

	private DataTypeMap responseTypes;
	
	public Action() {
		this.parameters = new ArrayList<ParameterAction>();
		this.parametersType 	= new ArrayList<Class<?>>();
		this.throwsSafe 		= new HashMap<Class<?>, ThrowableSafeData>();
		this.dispatcherType 	= DispatcherType.FORWARD;
		this.resultAction       = new ResultAction(this);
		//this.returnClass 		= void.class;
		this.redirect 			= false;
		this.alias 				= new ArrayList<ActionID>();
		this.requestTypes 	    = new DataTypeMap();
		this.responseTypes      = new DataTypeMap();
	}

	public DataTypeMap getRequestTypes() {
		return requestTypes;
	}

	public void setRequestTypes(DataTypeMap requestTypes) {
		this.requestTypes = requestTypes;
	}

	public DataTypeMap getResponseTypes() {
		return responseTypes;
	}

	public void setResponseTypes(DataTypeMap responseTypes) {
		this.responseTypes = responseTypes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addParameter(ParameterAction value) {
		parameters.add(value);
	}

	public ParameterAction getParameter(int index) {
		return (ParameterAction) parameters.get(index);
	}

	public void removeParameter(int index) {
		parameters.remove(index);
	}

	public List<ParameterAction> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterAction> parameters) {
		this.parameters = parameters;
	}

	public ThrowableSafeData getThrowsSafeOnAction(Class<?> thr) {
		return throwsSafe.get(thr);
	}

	public ThrowableSafeData getThrowsSafe(Class<?> thr) {
		
		ThrowableSafeData e = this.throwsSafe.get(thr);
		
		if(e == null){
			e = this.throwsSafe.get(Throwable.class);
		}
		
		if(e == null){
			e = this.controller.getThrowsSafe(thr);
		}
		
		return e;
	}

	public void setThrowsSafe(ThrowableSafeData thr) {
		this.throwsSafe.put(thr.getTarget(), thr);
	}

	public int getParamterSize() {
		return this.parameters.size();
	}

	public Class<?> getParameterType(int index) {
		return this.parametersType.get(index);
	}

	public java.lang.reflect.Type getGenericParameterType(int index) {
		return method.getGenericParameterTypes()[index];
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public List<Class<?>> getParametersType() {
		return parametersType;
	}

	public void setParametersType(List<Class<?>> parametersType) {
		this.parametersType = parametersType;
	}

	/*
	public String getReturnIn() {
		return returnIn == null ? BrutosConstants.DEFAULT_RETURN_NAME
				: returnIn;
	}

	public void setReturnIn(String returnIn) {
		this.returnIn = returnIn;
	}
    */
	
	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	/*
	public Type getReturnType() {
		return returnType;
	}

	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}

	public Class<?> getReturnClass() {
		return returnClass;
	}

	public void setReturnClass(Class<?> returnClass) {
		this.returnClass = returnClass;
	}
    */
	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	public DispatcherType getDispatcherType() {
		return dispatcherType;
	}

	public void setDispatcherType(DispatcherType dispatcherType) {
		this.dispatcherType = dispatcherType;
	}

	public synchronized void flush() {
		try {
			if (this.executor == null) {
				return;
			}

			method = getMethod(executor, controller.getClassType());
			controller.addReserveMethod(method, this);
			setParametersType(Arrays.asList(method.getParameterTypes()));

			Class<?> returnClassType = method.getReturnType();

			if (returnClassType != void.class) {
				Type resultType =
					this.controller.getContext()
					.getTypeManager().getType(returnClassType);
				
				if(this.resultAction.getType() != null){
					Type expectedType = this.resultAction.getType();
					
					if(!resultType.getClassType().isAssignableFrom(expectedType.getClassType())){
						throw new MappingException(
								"Invalid result type: " +
								"expected " + expectedType.getClassType() +
								"found " + resultType
						);
					}
					
				}
				else{
					this.resultAction.setType(resultType);
				}
			}

			setMethod(method);
		} catch (BrutosException e) {
			throw e;
		} catch (Exception e) {
			throw new BrutosException(e);
		}

	}

	private Method getMethod(String name, Class<?> clazz) {
		int size = parameters.size();
		Class<?>[] classArgs = new Class[size];
		for (int i = 0; i < size; i++) {
			ParameterAction arg = (ParameterAction) parameters.get(i);
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

						ParameterAction arg = (ParameterAction) parameters
								.get(k);

						String parameterName = ParameterNameResolver.getName(m, k);
						arg.setRealName(parameterName);
						
						if(BrutosConstants.EMPTY_ARGNAME.equals(arg.getName())){
							arg.setName(parameterName);	
						}
						
						if (arg.getType() == null) {
							if (arg.getMetaBean() != null)
								arg.setType(new AnyType(params[k]));
							else {
								arg.setType(this.controller
										.getContext().getTypeManager()
										.getType(params[k]));
							}
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

	public Class<?>[] getParameterClass() {
		int length = this.parameters.size();
		Class<?>[] result = new Class[length];

		for (int i = 0; i < length; i++) {
			ParameterAction p = (ParameterAction) this.parameters.get(i);
			result[i] = p.getClassType();
		}

		return result;
	}

	/*
	public Object[] getParameterValues(Object controllerInstance) {
		int length = this.parameters.size();
		Object[] values = new Object[length];

		int index = 0;
		for (int i = 0; i < length; i++) {
			ParameterAction p = (ParameterAction) this.parameters.get(i);
			values[index++] = p.getValue(controllerInstance);
		}

		this.parametersValidator.validate(this, controllerInstance, values);

		return values;
	}

	public Object[] getParameterValues(Object controllerInstance,
			Object[] values) {
		this.parametersValidator.validate(this, controllerInstance, values);
		return values;
	}
*/
	
	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String value) {
		this.executor = value;
	}

	public Object invoke(Object source, Object[] args)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Object result = null;
		if (this.method != null) {

			if (this.parametersValidator != null)
				this.parametersValidator.validate(this, source, args);

			result = method.invoke(source, args);

			if (this.resultValidator != null)
				this.resultValidator.validate(this.resultAction, source, result);
		}

		return result;
	}

	public boolean isAbstract() {
		return this.method == null;
	}

	public List<ActionID> getAlias() {
		return this.alias;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public ActionID getId() {
		return id;
	}

	public void setId(ActionID value){
		this.id = value;
	}
	
	public boolean isReturnRendered() {
		return returnRendered;
	}

	public void setReturnRendered(boolean returnRendered) {
		this.returnRendered = returnRendered;
	}

	public boolean isResolvedView() {
		return resolvedView;
	}

	public void setResolvedView(boolean resolvedView) {
		this.resolvedView = resolvedView;
	}

	public Validator getParametersValidator() {
		return this.parametersValidator;
	}

	public void setParametersValidator(Validator parametersValidator) {
		this.parametersValidator = parametersValidator;
	}

	public Validator getResultValidator() {
		return resultValidator;
	}

	public void setResultValidator(Validator resultValidator) {
		this.resultValidator = resultValidator;
	}

	public ResultAction getResultAction() {
		return resultAction;
	}

	public void setResultAction(ResultAction resultAction) {
		this.resultAction = resultAction;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	private static long counter = 1;

	public static synchronized final long getNextId() {
		return counter++;
	}

}
