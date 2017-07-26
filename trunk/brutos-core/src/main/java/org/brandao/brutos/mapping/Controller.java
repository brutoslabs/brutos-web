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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorProcess;
import org.brandao.brutos.ActionType;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.Invoker;
import org.brandao.brutos.ObjectFactory;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.Scopes;
import org.brandao.brutos.bean.BeanInstance;
import org.brandao.brutos.scope.Scope;

/**
 * 
 * @author Brandao
 */
public class Controller {

	private String name;

	private ControllerID id;

	private Class<?> classType;

	private String actionId;

	private Map<String, Bean> mappingBeans;

	private List<PropertyController> fields;

	private Map<ActionID, Action> actions;

	// TODO: update to Map<ReverseActionKey,Action>
	private Map<ReverseActionKey, List<Action>> reverseMethods;

	private ActionListener actionListener;

	private Map<Class<?>, ThrowableSafeData> throwsSafe;
	
	private List<ControllerID> alias;

	private ScopeType scope;

	private String view;

	private boolean redirect;

	private ActionID defaultAction;

	private List<Interceptor> interceptorStack;

	private InterceptorProcess interceptorProcess;

	private DispatcherType dispatcherType;

	private List<Interceptor> defaultInterceptorList;

	private ActionType actionType;

	private boolean resolvedView;

	private ConfigurableApplicationContext context;

	private BeanInstance beanInstance;

	private DataTypeMap requestTypes;

	private DataTypeMap responseTypes;
	
	public Controller(ConfigurableApplicationContext context) {
		this.fields 			= new ArrayList<PropertyController>();
		this.mappingBeans 		= new LinkedHashMap<String, Bean>();
		this.actions 			= new LinkedHashMap<ActionID, Action>();
		this.interceptorStack 	= new ArrayList<Interceptor>();
		this.alias 				= new ArrayList<ControllerID>();
		this.reverseMethods 	= new LinkedHashMap<ReverseActionKey, List<Action>>();
		this.interceptorProcess = new InterceptorProcess();
		this.scope 				= ScopeType.PARAM;
		this.redirect 			= false;
		this.actionType 		= ActionType.PARAMETER;
		this.context 			= context;
		this.requestTypes 	    = new DataTypeMap();
		this.responseTypes      = new DataTypeMap();
		this.throwsSafe         = new HashMap<Class<?>, ThrowableSafeData>();
		this.interceptorProcess.setForm(this);
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
	
	public String getActionId() {
		return actionId;
	}

	public Object getInstance(ObjectFactory objectFactory) {
		Object instance = name == null ? null : objectFactory.getBean(name);
		instance = instance == null ? objectFactory.getBean(classType)
				: instance;

		if (instance == null)
			throw new BrutosException("can't get instance " + name + ":"
					+ classType);
		else
			return instance;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public Bean getBean(String name) {
		return (Bean) mappingBeans.get(name);
	}

	public Map<String, Bean> getBeans() {
		return this.mappingBeans;
	}
	
	public void addBean(String name, Bean bean) {
		mappingBeans.put(name, bean);
	}

	public boolean containsProperty(String name) {
		return getProperty(name) != null;
	}

	public PropertyController getProperty(String name) {

		for (int i = 0; i < fields.size(); i++) {
			if (((PropertyController) fields.get(i)).getPropertyName().equals(
					name))
				return (PropertyController) fields.get(i);
		}

		return null;
	}

	public List<PropertyController> getProperties() {
		return fields;
	}

	public void addProperty(PropertyController property) {
		if (!containsProperty(property.getPropertyName()))
			fields.add(property);
	}

	public ActionListener getAcion() {
		return getActionListener();
	}

	public void setAcion(ActionListener acion) {
		this.setActionListener(acion);
	}

	public Class<?> getClassType() {
		return classType;
	}

	public void setClassType(Class<?> classType) {
		this.classType = classType;
		this.beanInstance = new BeanInstance(null, classType);
	}

	public Action getActionById(ActionID id) {
		return (Action) actions.get(id);
	}

	public Action getActionByName(String name){
		return (Action) actions.get(new ActionID(name));
	}
	
	public Map<ActionID, Action> getActions() {
		return actions;
	}

	public void addAction(ActionID id, Action method) {
		this.actions.put(id, method);
		this.context.getActionResolver()
			.registry(null, method.getController(), id, method);
	}

	public void removeAction(ActionID id) {
		Action method = this.actions.get(id);
		
		if(method == null){
			return;
		}
		
		if(id.equals(method.getId())){
			this.actions.remove(id);
			
			for(ActionID alias: method.getAlias()){
				this.actions.remove(alias);
			}
			
			this.context.getActionResolver()
				.remove(null, method.getController(), null, method);
		}
		else{
			this.actions.remove(id);
			
			this.context.getActionResolver()
				.remove(null, method.getController(), id, method);
		}
		
		
	}

	Map<ReverseActionKey, List<Action>> getReverseMethods() {
		return reverseMethods;
	}

	void addReserveMethod(Method method, Action action) {

		ReverseActionKey key = new ReverseActionKey(method);

		List<Action> list = (List<Action>) reverseMethods.get(key);

		if (list == null) {
			list = new LinkedList<Action>();
			reverseMethods.put(key, list);
		}

		list.add(action);
	}

	public Action getMethod(Method method) {

		ReverseActionKey key = new ReverseActionKey(method);

		List<Action> list = reverseMethods.get(key);

		if (list == null)
			return null;

		if (list.size() > 1)
			throw new BrutosException(String.format(
					"Ambiguous reference to action: %s",
					new Object[] { method.getName() }));

		return (Action) list.get(0);
	}

	public void setMethods(Map<ActionID, Action> methods) {
		this.actions = methods;
	}

	public void addInterceptor(Interceptor interceptor) {
		this.addInterceptor(new Interceptor[] { interceptor });
	}

	public void addInterceptor(Interceptor[] interceptor) {
		for (Interceptor i : interceptor) {
			if (this.interceptorStack.contains(i))
				throw new BrutosException(i.getName()
						+ " already associated with the controller");
			this.interceptorStack.add(i);
		}
	}

	public void removeInterceptor(Interceptor interceptor) {
		this.removeInterceptor(new Interceptor[] { interceptor });
	}

	public void removeInterceptor(Interceptor[] interceptor) {
		for (Interceptor i : interceptor) {
			if (!this.interceptorStack.contains(i))
				throw new BrutosException("interceptor not found: "
						+ i.getName());
			this.interceptorStack.remove(i);
		}
	}

	public boolean isInterceptedBy(Interceptor interceptor) {
		return this.interceptorStack.contains(interceptor);
	}

	public List<Interceptor> getInterceptors() {
		return getInterceptorStack();
	}

	public Object getInstance() {
		try {
			return getClassType().newInstance();
		} catch (Exception e) {
			throw new InvokeException(e);
		}
	}

	public Action getAction(ActionID value) {
		Action mf;
		mf = (Action) (name == null ? null : actions.get(value));
		mf = (Action) (mf == null ? actions.get(getDefaultAction()) : mf);
		return mf;
	}

	public void proccessBrutosAction(InterceptorHandler handler) {
		interceptorProcess.process(handler);
	}

	public synchronized void flush() {
		this.interceptorProcess.flush();
		for(Action ac: actions.values()){
			ac.flush();
		}
	}

	public void fieldsToRequest(Object webFrame) {
		try {
			Scopes scopes = Invoker.getCurrentApplicationContext().getScopes();
			Field[] fields = getClassType().getDeclaredFields();
			Scope scope = scopes.get(ScopeType.REQUEST);

			// for( Field f: fields ){
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				f.setAccessible(true);
				scope.put(f.getName(), f.get(webFrame));
			}
		} catch (Exception e) {

		}
	}

	public ThrowableSafeData getThrowsSafe(Class<?> thr) {
		
		ThrowableSafeData e = this.throwsSafe.get(thr);
		
		if(e == null){
			e = this.throwsSafe.get(Throwable.class);
		}
		
		return e;
	}

	public void removeThrowsSafe(Class<?> thr) {
		this.throwsSafe.remove(thr);
	}

	public void setThrowsSafe(ThrowableSafeData thr) {
		this.throwsSafe.put(thr.getTarget(), thr);
	}
	
	public List<ControllerID> getAlias() {
		return this.alias;
	}

	public ScopeType getScope() {
		return scope;
	}

	public void setScope(ScopeType scope) {
		this.scope = scope;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		view = view == null || view.trim().length() == 0 ? null : view;
		this.view = view;
	}

	public ActionID getDefaultAction() {
		return defaultAction;
	}

	public void setDefaultAction(ActionID defaultAction) {
		this.defaultAction = defaultAction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ControllerID getId() {
		return id;
	}

	public void setId(ControllerID id) {
		this.id = id;
	}

	public ActionListener getActionListener() {
		return actionListener;
	}

	public void setActionListener(ActionListener action) {
		this.actionListener = action;
	}

	public List<Interceptor> getInterceptorStack() {
		return interceptorStack;
	}

	public void setInterceptorStack(List<Interceptor> interceptorStack) {
		this.interceptorStack = interceptorStack;
	}

	public InterceptorProcess getInterceptorProcess() {
		return interceptorProcess;
	}

	public void setInterceptorProcess(InterceptorProcess interceptorProcess) {
		this.interceptorProcess = interceptorProcess;
	}

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

	public List<Interceptor> getDefaultInterceptorList() {
		return defaultInterceptorList;
	}

	public void setDefaultInterceptorList(
			List<Interceptor> defaultInterceptorList) {
		this.defaultInterceptorList = defaultInterceptorList;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public ConfigurableApplicationContext getContext() {
		return context;
	}

	public void setContext(ConfigurableApplicationContext context) {
		this.context = context;
	}

	public boolean isResolvedView() {
		return resolvedView;
	}

	public void setResolvedView(boolean resolvedView) {
		this.resolvedView = resolvedView;
	}

	public BeanInstance getBeanInstance() {
		return beanInstance;
	}

	public void setBeanInstance(BeanInstance beanInstance) {
		this.beanInstance = beanInstance;
	}

}
