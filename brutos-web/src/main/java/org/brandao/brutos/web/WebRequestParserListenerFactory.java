package org.brandao.brutos.web;

import org.brandao.brutos.RequestParserListener;
import org.brandao.brutos.RequestParserListenerFactory;

public class WebRequestParserListenerFactory implements RequestParserListenerFactory{

	public RequestParserListener getNewListener() {
		return new WebRequestParserListener();
	}

}
