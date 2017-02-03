package org.brandao.brutos;

public class FlowController {

	public FlowController() {
	}

	public static DispatcherView dispatcher(DispatcherType dispatcher) {
		return new DispatcherView(dispatcher);
	}

	public static DispatcherView dispatcher() {
		return dispatcher(DispatcherType.FORWARD);
	}

	public static Object getController(Class clazz) {
		return getControllerInstance(clazz);
	}

	public static Object execute(Class clazz, String actionName) {
		return Invoker.getInstance().invoke(clazz, actionName);
	}

	private static Object getControllerInstance(Class controllerClass) {
		ApplicationContext context = Invoker.getCurrentApplicationContext();
		return context.getController(controllerClass);
	}

}
