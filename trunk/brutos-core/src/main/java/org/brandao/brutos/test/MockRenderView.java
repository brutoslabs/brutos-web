package org.brandao.brutos.test;

import java.util.Properties;
import org.brandao.brutos.DispatcherType;
import java.io.IOException;
import org.brandao.brutos.*;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.type.Type;

public class MockRenderView extends AbstractRenderView {

	private DispatcherType dispatcherType;

	private String view;

	private boolean redirect;

	private Object actionResult;

	public MockRenderView() {
	}

	public void configure(Configuration properties) {
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	public void configure(Properties properties) {
	}

	public void show(RequestInstrument requestInstrument,
			StackRequestElement stackRequestElement) throws IOException,
			ViewException {

		Action method = stackRequestElement.getAction() == null ? null
				: stackRequestElement.getAction().getMethodForm();

		if (method != null && method.isReturnRendered())
			this.actionResult = stackRequestElement.getResultAction();
		else
			super.show(requestInstrument, stackRequestElement);
	}

	public void show(RequestInstrument requestInstrument, String view,
			DispatcherType dispatcherType) throws IOException {
		this.redirect = dispatcherType == DispatcherType.REDIRECT;
		this.dispatcherType = dispatcherType;
		this.view = view;

	}

	public DispatcherType getDispatcherType() {
		return dispatcherType;
	}

	public void setDispatcherType(DispatcherType dispatcherType) {
		this.dispatcherType = dispatcherType;
	}

	public Object getActionResult() {
		return actionResult;
	}

	public void setActionResult(Type actionResult) {
		this.actionResult = actionResult;
	}

	public void destroy() {
	}

}
