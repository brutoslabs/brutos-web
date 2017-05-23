package org.brandao.brutos;

public class RequestParserListenerFactoryImp 
	implements RequestParserListenerFactory{

	public RequestParserListener getNewListener() {
		return new RequestParserListenerImp();
	}

}
