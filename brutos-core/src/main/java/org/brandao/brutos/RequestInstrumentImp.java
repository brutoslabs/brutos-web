package org.brandao.brutos;

import java.util.LinkedList;
import java.util.List;

public class RequestInstrumentImp implements RequestInstrument, StackRequest {

	private ApplicationContext context;
	private boolean hasViewProcessed;
	private ObjectFactory objectFactory;
	private RenderView renderView;
	private List stackRequest;

	public RequestInstrumentImp(ApplicationContext context,
			ObjectFactory objectFactory, RenderView renderView) {

		this.context = context;
		this.hasViewProcessed = false;
		this.objectFactory = objectFactory;
		this.stackRequest = new LinkedList();
		this.renderView = renderView;
	}

	public void push(StackRequestElement stackrequestElement) {
		stackRequest.add(stackrequestElement);
	}

	public StackRequestElement getCurrent() {
		return (StackRequestElement) (stackRequest.size() > 0 ? stackRequest
				.get(stackRequest.size() - 1) : null);
	}

	public StackRequestElement getNext(StackRequestElement stackrequestElement) {
		int indexOf = stackRequest.indexOf(stackrequestElement);
		return (StackRequestElement) (indexOf != -1
				&& indexOf + 1 < stackRequest.size() ? stackRequest
				.get(indexOf + 1) : null);
	}

	public boolean isEmpty() {
		return stackRequest.isEmpty();
	}

	public void pop() {
		if (stackRequest.size() > 0)
			stackRequest.remove(stackRequest.size() - 1);
	}

	public ApplicationContext getContext() {
		return context;
	}

	public void setContext(AbstractApplicationContext context) {
		this.context = context;
	}

	public boolean isHasViewProcessed() {
		return hasViewProcessed;
	}

	public void setHasViewProcessed(boolean hasViewProcessed) {
		this.hasViewProcessed = hasViewProcessed;
	}

	public ObjectFactory getObjectFactory() {
		return objectFactory;
	}

	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	public RenderView getRenderView() {
		return this.renderView;
	}

	public void setRenderView(RenderView renderView) {
		this.renderView = renderView;
	}

}
