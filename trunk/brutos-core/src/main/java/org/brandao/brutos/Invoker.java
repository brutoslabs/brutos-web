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

package org.brandao.brutos;

import org.brandao.brutos.interceptor.InterceptorHandlerImp;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.scope.ThreadScope;

/**
 * 
 * @author Brandao
 */
public class Invoker {

	private static final ThreadLocal<ConfigurableApplicationContext> currentApp;

	static {
		currentApp = new ThreadLocal<ConfigurableApplicationContext>();
	}

	protected Logger logger = LoggerProvider.getCurrentLoggerProvider()
			.getLogger(Invoker.class);

	protected ObjectFactory objectFactory;
	
	protected ControllerManager controllerManager;
	
	protected ActionResolver actionResolver;
	
	protected ConfigurableApplicationContext applicationContext;
	
	protected RenderView renderView;
	
	protected RequestProvider requestProvider;
	
	protected ResponseProvider responseProvider;

	protected ConfigurableRequestParser requestParser;
	
	protected RequestParserListenerFactory requestParserListenerFactory;
	
	protected RequestParserListener requestParserListener;
	
	public Invoker() {
	}

	public Invoker(
			ObjectFactory objectFactory, 
			ControllerManager controllerManager,
			ActionResolver actionResolver,
			ConfigurableApplicationContext applicationContext,
			RenderView renderView,
			RequestParserListenerFactory requestParserListenerFactory) {

		this.objectFactory = objectFactory;
		this.controllerManager = controllerManager;
		this.actionResolver = actionResolver;
		this.applicationContext = applicationContext;
		this.renderView = renderView;
		this.requestProvider = new RequestProvider();
		this.responseProvider = new ResponseProvider();
		this.requestParser = new RequestParserImp();
		this.requestParserListenerFactory = requestParserListenerFactory;
		this.requestParserListener = requestParserListenerFactory.getNewListener();
	}

	public boolean invoke(MutableMvcRequest request, MutableMvcResponse response) {

		MutableRequestParserEvent event = new MutableRequestParserEventImp();
		
		try{
			this.requestParserListener.started(event);
			this.requestParser.parserContentType(request, 
					request.getType(), 
					this.applicationContext.getConfiguration(), event);
		}
		finally{
			this.requestParserListener.finished(event);
		}
		
		request.setApplicationContext(this.applicationContext);
		
		ResourceAction resourceAction = 
				actionResolver.getResourceAction(controllerManager, request);
		
		if(resourceAction == null){
			return false;
		}
		
		request.setResource(resourceAction.getController().getInstance(objectFactory));
		request.setResourceAction(resourceAction);

		StackRequestElement element = createStackRequestElement();

		element.setAction(request.getResourceAction());
		element.setController(resourceAction.getController());
		element.setRequest(request);
		element.setResponse(response);
		element.setResource(request.getResource());
		element.setObjectThrow(request.getThrowable());
		return this.invoke(element);
	}

	public Object invoke(Controller controller, ResourceAction action,
			Object resource, Object[] parameters) {

		if (controller == null)
			throw new IllegalArgumentException("controller not found");

		if (action == null)
			throw new IllegalArgumentException("action not found");

		MutableMvcRequest request   = new DefaultMvcRequest();
		MutableMvcResponse response = new DefaultMvcResponse();
		
		request.setResource(resource);
		request.setResourceAction(action);
		request.setParameters(parameters);
		
		StackRequestElement element = createStackRequestElement();

		element.setAction(request.getResourceAction());
		element.setController(controller);
		element.setRequest(request);
		element.setResponse(response);
		element.setResource(request.getResource());
		element.setObjectThrow(request.getThrowable());
		this.invoke(element);
		return response.getResult();
	}
	
	public Object invoke(Controller controller, ResourceAction action,
			Object[] parameters) {
		return invoke(controller, action, null, parameters);
	}

	public Object invoke(Class<?> controllerClass, String actionId) {
		Controller controller = 
				applicationContext
				.getControllerManager()
				.getController(controllerClass);

		ResourceAction resourceAction = 
				actionResolver.getResourceAction(controller, actionId, null);
		
		if(resourceAction == null){
			return false;
		}
		else{
			return this.invoke(controller, resourceAction, null);
		}
	}

	public RequestInstrument getRequestInstrument() {
		Scopes scopes = applicationContext.getScopes();
		Scope requestScope = scopes.get(ScopeType.REQUEST);

		RequestInstrument requestInstrument = getRequestInstrument(requestScope);

		return requestInstrument;
	}

	public StackRequest getStackRequest() {
		RequestInstrument requestInstrument = getRequestInstrument();
		return getStackRequest(requestInstrument);
	}

	public StackRequest getStackRequest(RequestInstrument value) {
		return (StackRequest) value;
	}

	public StackRequestElement getStackRequestElement() {
		return getStackRequest().getCurrent();
	}

	public boolean invoke(StackRequestElement element) {

		long time                  = -1;
		boolean createdThreadScope = false;
		StackRequest stackRequest  = null;
		boolean isFirstCall        = false;
		MvcRequest oldRequest      = null;
		MvcResponse oldresponse    = null;
		
		RequestInstrument requestInstrument;

		try{
			MutableMvcRequest request   = element.getRequest();
			MutableMvcResponse response = element.getResponse();
			oldRequest                  = this.requestProvider.init(request);
			oldresponse                 = this.responseProvider.init(response);
			
			time = System.currentTimeMillis();
			createdThreadScope = ThreadScope.create();
			requestInstrument = getRequestInstrument();
			stackRequest = getStackRequest(requestInstrument);
			isFirstCall = stackRequest.isEmpty();
			
			
			request.setRequestInstrument(requestInstrument);
			request.setStackRequestElement(element);

			if (isFirstCall)
				currentApp.set(this.applicationContext);

			stackRequest.push(element);
			
			InterceptorHandlerImp ih = new InterceptorHandlerImp(request, response);
			element.getController().proccessBrutosAction(ih);
			return true;
		}
		finally {

			this.requestProvider.destroy(oldRequest);
			this.responseProvider.destroy(oldresponse);
			
			if (createdThreadScope)
				ThreadScope.destroy();

			stackRequest.pop();

			if (isFirstCall)
				currentApp.remove();

			if (logger.isDebugEnabled())
				logger.debug(String.format("Request processed in %d ms",
						new Object[] { new Long(
								(System.currentTimeMillis() - time)) }));
		}
	}

	private RequestInstrument getRequestInstrument(Scope scope) {
		RequestInstrument requestInstrument = (RequestInstrument) scope
				.get(BrutosConstants.REQUEST_INSTRUMENT);

		if (requestInstrument == null) {
			requestInstrument = new RequestInstrumentImp(
					this.applicationContext, this.objectFactory,
					this.renderView);

			scope.put(BrutosConstants.REQUEST_INSTRUMENT, requestInstrument);
		}

		return requestInstrument;
	}

	StackRequestElement createStackRequestElement() {
		return new StackRequestElementImp();
	}

	public static ApplicationContext getCurrentApplicationContext() {
		return (ApplicationContext) currentApp.get();
	}

	public static Invoker getInstance() {
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) getCurrentApplicationContext();

		if (context == null)
			throw new BrutosException("can not get invoker");

		return context.getInvoker();
	}

	public void flush() {
	}
	
}
