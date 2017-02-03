^/*
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

import java.util.List;
import java.util.Properties;
import org.brandao.brutos.io.DefaultResourceLoader;
import org.brandao.brutos.javassist.JavassistCodeGenerator;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.ControllerScope;
import org.brandao.brutos.scope.SingletonScope;
import org.brandao.brutos.scope.ThreadScope;
import org.brandao.brutos.validator.DefaultValidatorFactory;

public abstract class AbstractApplicationContext extends DefaultResourceLoader
		implements ConfigurableApplicationContext {

	protected Logger logger;

	protected ObjectFactory objectFactory;

	protected InterceptorManager interceptorManager;

	protected ControllerManager controllerManager;

	protected RenderView renderView;

	protected Invoker invoker;

	protected Properties configuration;

	protected LoggerProvider loggerProvider;

	protected ControllerResolver controllerResolver;

	protected ActionResolver actionResolver;

	protected MvcResponseFactory responseFactory;

	protected MvcRequestFactory requestFactory;

	protected Scopes scopes;

	private ViewResolver viewResolver;

	private ValidatorFactory validatorFactory;

	protected CodeGenerator codeGenerator;

	private ApplicationContext parent;

	protected TypeManager typeManager;

	public AbstractApplicationContext() {
		this(null);
	}

	public AbstractApplicationContext(ApplicationContext parent) {

		this.parent = parent;

		if (parent == null)
			this.configuration = new Configuration();
		else
			this.configuration = new Configuration(parent.getConfiguration());

		this.scopes = new Scopes();
	}

	protected void initInstances() {
		this.objectFactory = getNewObjectFactory(configuration);
		this.interceptorManager = getNewInterceptorManager();
		this.controllerResolver = getNewControllerResolver();
		this.actionResolver = getNewMethodResolver();
		this.requestFactory = getMvcRequestFactory();
		this.responseFactory = getMvcResponseFactory();
		this.validatorFactory = getNewValidatorFactory(configuration);
		this.viewResolver = getNewViewResolver();
		this.controllerManager = getNewControllerManager();
		this.renderView = getNewRenderView(configuration);
		this.codeGenerator = getNewCodeGenerator(configuration);
		this.typeManager = getNewTypeManager();
		this.invoker = createInvoker(this.controllerResolver,
				this.objectFactory, this.controllerManager,
				this.actionResolver, this, this.renderView);
	}

	protected void initTypes() {
	}

	protected void initScopes() {
		getScopes().register(ScopeType.SINGLETON.toString(),
				new SingletonScope());

		getScopes().register(ScopeType.THREAD.toString(), new ThreadScope());

		getScopes().register(ScopeType.PARAM.toString(),
				getScopes().get(ScopeType.THREAD));

		getScopes().register(ScopeType.REQUEST.toString(),
				getScopes().get(ScopeType.THREAD));

		getScopes().register(ScopeType.CONTROLLER.toString(),
				new ControllerScope());

	}

	protected void initComponents() {
		List<Controller> controllers = this.controllerManager.getControllers();
		for (Controller controller : controllers)
			controller.flush();
	}

	protected void initLogger() {
		this.logger = LoggerProvider.getCurrentLoggerProvider().getLogger(
				getClass());
	}

	protected abstract void loadDefinitions(ComponentRegistry registry);

	protected void setControllerResolver(ControllerResolver controllerResolver) {
		this.controllerResolver = controllerResolver;
	}

	protected ControllerResolver getNewControllerResolver() {
		try {
			Class<?> clazz = ClassUtil.get(configuration.getProperty(
					BrutosConstants.CONTROLLER_RESOLVER_CLASS,
					DefaultControllerResolver.class.getName()));

			return (ControllerResolver) ClassUtil.getInstance(clazz);
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	protected MvcResponseFactory getMvcResponseFactory() {
		try {
			Class<?> clazz = ClassUtil.get(configuration.getProperty(
					BrutosConstants.RESPONSE_FACTORY,
					DefaultMvcResponseFactory.class.getName()));

			return (MvcResponseFactory) ClassUtil.getInstance(clazz);
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	protected MvcRequestFactory getMvcRequestFactory() {
		try {
			Class<?> clazz = ClassUtil.get(configuration.getProperty(
					BrutosConstants.REQUEST_FACTORY,
					DefaultMvcRequestFactory.class.getName()));

			return (MvcRequestFactory) ClassUtil.getInstance(clazz);
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	protected ActionResolver getNewMethodResolver() {
		try {
			Class<?> clazz = ClassUtil.get(configuration.getProperty(
					BrutosConstants.ACTION_RESOLVER,
					DefaultActionResolver.class.getName()));

			return (ActionResolver) ClassUtil.getInstance(clazz);
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	protected ControllerManager getNewControllerManager() {
		try {
			String className = configuration.getProperty(
					BrutosConstants.CONTROLLER_MANAGER_CLASS,
					ControllerManagerImp.class.getName());

			Class<?> clazz = ClassUtil.get(className);

			ControllerManager instance = (ControllerManager) ClassUtil
					.getInstance(clazz);

			instance.setInterceptorManager(interceptorManager);
			instance.setValidatorFactory(validatorFactory);
			instance.setParent(this.parent == null ? null
					: ((ConfigurableApplicationContext) parent)
							.getControllerManager());
			instance.setApplicationContext(this);

			return instance;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	protected InterceptorManager getNewInterceptorManager() {
		try {
			String className = configuration.getProperty(
					BrutosConstants.INTERCEPTOR_MANAGER_CLASS,
					InterceptorManagerImp.class.getName());

			Class<?> clazz = ClassUtil.get(className);

			InterceptorManager instance = (InterceptorManager) ClassUtil
					.getInstance(clazz);

			instance.setParent(this.parent == null ? null
					: ((ConfigurableApplicationContext) parent)
							.getInterceptorManager());

			return instance;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	protected ObjectFactory getNewObjectFactory(Properties config) {
		try {
			String className = configuration.getProperty(
					BrutosConstants.OBJECT_FACTORY_CLASS,
					BrutosConstants.DEFAULT_OBJECT_FACTORY_CLASS);

			Class<?> clazz = ClassUtil.get(className);

			ObjectFactory instance = (ObjectFactory) ClassUtil
					.getInstance(clazz);

			instance.configure(config);

			return instance;
		} catch (BrutosException e) {
			throw e;
		} catch (Throwable e) {
			throw new BrutosException(e);
		}
	}

	protected ValidatorFactory getNewValidatorFactory(Properties config) {
		try {
			String className = configuration.getProperty(
					BrutosConstants.VALIDATOR_FACTORY_CLASS,
					DefaultValidatorFactory.class.getName());

			Class<?> clazz = ClassUtil.get(className);

			ValidatorFactory instance = (ValidatorFactory) ClassUtil
					.getInstance(clazz);

			instance.configure(config);

			return instance;
		} catch (BrutosException e) {
			throw e;
		} catch (Throwable e) {
			throw new BrutosException(e);
		}
	}

	protected CodeGenerator getNewCodeGenerator(Properties config) {
		try {
			String className = configuration.getProperty(
					BrutosConstants.CODE_GENERATOR_CLASS,
					JavassistCodeGenerator.class.getName());

			Class<?> clazz = ClassUtil.get(className);

			CodeGenerator instance = (CodeGenerator) ClassUtil
					.getInstance(clazz);

			instance.configure(config);

			return instance;
		} catch (BrutosException e) {
			throw e;
		} catch (Throwable e) {
			throw new BrutosException(e);
		}
	}

	protected RenderView getNewRenderView(Properties config) {
		try {
			String className = configuration.getProperty(
					BrutosConstants.RENDER_VIEW_CLASS,
					DefaultRenderView.class.getName());

			Class<?> clazz = ClassUtil.get(className);

			RenderView instance = (RenderView) ClassUtil.getInstance(clazz);

			instance.configure(config);

			return instance;
		} catch (BrutosException e) {
			throw e;
		} catch (Throwable e) {
			throw new BrutosException(e);
		}
	}

	protected Invoker createInvoker(ControllerResolver controllerResolver,
			ObjectFactory objectFactory, ControllerManager controllerManager,
			ActionResolver actionResolver,
			ConfigurableApplicationContext applicationContext,
			RenderView renderView) {
		try {
			String className = configuration.getProperty(
					BrutosConstants.INVOKER_CLASS,
					BrutosConstants.DEFAULT_INVOKER_CLASS);

			Class<?> clazz = ClassUtil.get(className);

			Invoker instance = (Invoker) ClassUtil.getInstance(clazz,
					new Class[] { ControllerResolver.class,
							ObjectFactory.class, ControllerManager.class,
							ActionResolver.class,
							ConfigurableApplicationContext.class,
							RenderView.class }, new Object[] {
							controllerResolver, objectFactory,
							controllerManager, actionResolver,
							applicationContext, renderView });

			return instance;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	protected ViewResolver getNewViewResolver() {
		try {
			String className = configuration.getProperty(
					"org.brandao.brutos.view.resolver",
					DefaultViewResolver.class.getName());

			ViewResolver tmp = (ViewResolver) ClassUtil.getInstance(ClassUtil
					.get(className));
			tmp.setApplicationContext(this);
			return tmp;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	protected TypeManager getNewTypeManager() {
		try {
			String className = configuration.getProperty(
					BrutosConstants.TYPE_MANAGER_CLASS,
					BrutosConstants.DEFAULT_TYPE_MANAGER_CLASS);

			TypeManager tmp = (TypeManager) ClassUtil.getInstance(ClassUtil
					.get(className));
			return tmp;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	public void destroy() {
		this.objectFactory.destroy();
		this.renderView.destroy();
		this.codeGenerator.destroy();
		this.validatorFactory.destroy();
		this.actionResolver = null;
		this.codeGenerator = null;
		this.configuration = null;
		this.controllerManager = null;
		this.controllerResolver = null;
		this.interceptorManager = null;
		this.invoker = null;
		this.objectFactory = null;
		this.loggerProvider = null;
		this.requestFactory = null;
		this.responseFactory = null;
		this.scopes.clear();
		this.validatorFactory = null;
		this.viewResolver = null;
	}

	public Properties getConfiguration() {
		return configuration;
	}

	public LoggerProvider getLoggerProvider() {
		return loggerProvider;
	}

	public MvcResponse getMvcResponse() {
		return ResponseProvider.getResponse();
	}

	public MvcRequest getMvcRequest() {
		return RequestProvider.getRequest();
	}

	public Scopes getScopes() {
		return scopes;
	}

	public MvcRequestFactory getRequestFactory() {
		return this.requestFactory;
	}

	public MvcResponseFactory getResponseFactory() {
		return this.responseFactory;
	}

	public void setInterceptorManager(InterceptorManager interceptorManager) {
		this.interceptorManager = interceptorManager;
	}

	public void setRenderView(RenderView renderView) {
		this.renderView = renderView;
	}

	public RenderView getRenderView() {
		return this.renderView;
	}

	public ValidatorFactory getValidatorFactory() {
		return this.validatorFactory;
	}

	public Invoker getInvoker() {
		return this.invoker;
	}

	public void setInvoker(Invoker value) {
		this.invoker = value;
	}

	public void setConfiguration(Properties config) {
		this.configuration = config;
	}

	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	public InterceptorManager getInterceptorManager() {
		return this.interceptorManager;
	}

	public ControllerManager getControllerManager() {
		return this.controllerManager;
	}

	public ObjectFactory getObjectFactory() {
		return this.objectFactory;
	}

	public ControllerResolver getControllerResolver() {
		return this.controllerResolver;
	}

	public ActionResolver getActionResolver() {
		return this.actionResolver;
	}

	public CodeGenerator getCodeGenerator() {
		return this.codeGenerator;
	}

	public void setCodeGenerator(CodeGenerator codeGenerator) {
		this.codeGenerator = codeGenerator;
	}

	public ViewResolver getViewResolver() {
		return viewResolver;
	}

	public void setViewResolver(ViewResolver viewResolver) {
		this.viewResolver = viewResolver;
	}

	public Object getController(Class<?> clazz) {

		Controller controller = controllerResolver.getController(
				controllerManager, clazz);

		if (controller == null)
			throw new BrutosException(String.format(
					"controller not configured: %s",
					new Object[] { clazz.getName() }));

		Object resource = controller.getInstance(this.objectFactory);

		ProxyFactory proxyFactory = this.codeGenerator
				.getProxyFactory(controller.getClassType());

		Object proxy = proxyFactory.getNewProxy(resource, controller, this,
				invoker);

		return proxy;
	}

	public void setParent(ApplicationContext applicationContext) {

		if (!(applicationContext instanceof ConfigurableApplicationContext)) {
			throw new IllegalArgumentException("expected: instance of "
					+ ConfigurableApplicationContext.class.getName());
		}

		this.parent = applicationContext;

		this.controllerManager
				.setParent(((ConfigurableApplicationContext) applicationContext)
						.getControllerManager());

		this.interceptorManager
				.setParent(((ConfigurableApplicationContext) applicationContext)
						.getInterceptorManager());
	}

	public ApplicationContext getParent() {
		return this.parent;
	}

	public Object getBean(Class<?> clazz) {
		return this.objectFactory.getBean(clazz);
	}

	public Object getBean(String name) {
		return this.objectFactory.getBean(name);
	}

	public TypeManager getTypeManager() {
		return this.typeManager;
	}

	public boolean isStandardType(Class<?> clazz) {
		return this.typeManager.isStandardType(clazz);
	}

	public void flush() {

		this.initLogger();

		this.initInstances();

		this.initScopes();

		this.initTypes();

		this.invoker.flush();

		this.loadDefinitions(new ComponentRegistryAdapter(this));

		this.initComponents();

	}

}
