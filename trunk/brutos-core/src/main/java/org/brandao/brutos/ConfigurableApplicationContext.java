package org.brandao.brutos;

import java.util.Properties;
import org.brandao.brutos.TypeManager;

public interface ConfigurableApplicationContext extends ApplicationContext {

	MvcRequestFactory getRequestFactory();

	MvcResponseFactory getResponseFactory();

	void setInterceptorManager(InterceptorManager interceptorManager);

	void setRenderView(RenderView renderView);

	RenderView getRenderView();

	ValidatorFactory getValidatorFactory();

	Invoker getInvoker();

	void setInvoker(Invoker value);

	void setConfiguration(Properties config);

	Properties getConfiguration();

	void setObjectFactory(ObjectFactory objectFactory);

	InterceptorManager getInterceptorManager();

	ControllerManager getControllerManager();

	ObjectFactory getObjectFactory();

	ControllerResolver getControllerResolver();

	ActionResolver getActionResolver();

	CodeGenerator getCodeGenerator();

	void setCodeGenerator(CodeGenerator codeGenerator);

	void setViewResolver(ViewResolver viewResolver);

	ViewResolver getViewResolver();

	TypeManager getTypeManager();

	void setParent(ApplicationContext applicationContext);

	ApplicationContext getParent();

	void flush();

}
