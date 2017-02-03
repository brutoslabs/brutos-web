package org.brandao.brutos;

public class RedirectException extends BrutosException {

	private String page;
	private String view;
	private DispatcherType dispatcher;

	public RedirectException() {
		super();
	}

	public RedirectException(String view, DispatcherType dispatcher) {
		this.dispatcher = dispatcher;
		this.view = view;
	}

	public RedirectException(String message) {
		super(message);
	}

	public RedirectException(String message, Throwable cause) {
		super(message, cause);
	}

	public RedirectException(Throwable cause) {
		super(cause);
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getView() {
		return view;
	}

	public DispatcherType getDispatcher() {
		return dispatcher;
	}

}
