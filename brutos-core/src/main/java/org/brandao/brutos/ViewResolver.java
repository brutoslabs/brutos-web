package org.brandao.brutos;

public interface ViewResolver {

	String getView(ControllerBuilder controllerBuilder,
			ActionBuilder actionBuilder, Class<?> exceptionType, String view);

	void setApplicationContext(ApplicationContext context);

	String getPrefix();

	String getSuffix();

	String getIndexName();

	String getSeparator();

	String getControllerView(Class<?> controllerType, String view);

	String getActionView(Class<?> controllerType, String actionExecutor,
			String view);

	String getExceptionView(Class<?> controllerType, String actionExecutor,
			Class<?> exceptionType, String view);

	String getExceptionView(Class<?> controllerType, Class<?> exceptionType,
			String view);

}
