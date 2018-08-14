package org.brandao.brutos.annotation.web.helper;

import java.util.Map;


import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.BasicWebApplicationTester;

public class WebApplicationTester extends BasicWebApplicationTester{

    public void prepareContext(Map<String, String> parameters) {
        parameters.put(
                ContextLoader.CONTEXT_CLASS,
                MockAnnotationWebApplicationContext.class.getName()
        );

        parameters.put(
                MockAnnotationWebApplicationContext.IGNORE_RESOURCES,
                "true"
        );
    }
	
	public void checkException(Throwable e) {
		throw new RuntimeException(e);
	}
	
}
