package org.brandao.brutos.test;

import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.MvcRequestFactory;

public class MockMvcRequestFactory implements MvcRequestFactory {

	public MvcRequest getRequest() {
		return new MockMvcRequest();
	}

}
