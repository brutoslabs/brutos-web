package org.brandao.brutos;

public class DispatcherView {

	private DispatcherType dispatcher;

	public DispatcherView() {
		this(null);
	}

	public DispatcherView(DispatcherType dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void to(String value) {
		StackRequestElement request = Invoker.getInstance().getStackRequest()
				.getCurrent();
		request.setDispatcherType(dispatcher);
		request.setView(value);
	}

	public void toRedirectNow(String value) {
		throw new RedirectException(value, dispatcher);
	}

}
