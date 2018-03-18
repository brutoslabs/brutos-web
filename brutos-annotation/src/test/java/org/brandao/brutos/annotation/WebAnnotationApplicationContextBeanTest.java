package org.brandao.brutos.annotation;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.FieldTest;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.BasicWebApplicationTester;
import org.brandao.brutos.web.test.WebApplicationContextTester;

import junit.framework.TestCase;

public class WebAnnotationApplicationContextBeanTest extends TestCase{

	public void testField(){
		WebApplicationContextTester.run(
			"/fieldtest", 
			new BasicWebApplicationTester(){
				
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
				
				public void prepareRequest(Map<String, String> parameters,
						Map<String, String> header, Map<String, Object> properties) {
					
					parameters.put("fieldTest.propertyA", "1");
					parameters.put("fieldTest.propertyB", "teste");
					parameters.put("fieldTest.propertyC", "2000-01-01");
					parameters.put("fieldTest.propertyD", "2");
					parameters.put("fieldTest.property",  "3");
				}
				
				public void prepareSession(Map<String, Object> parameters) {
					parameters.put("fieldTest.proprty",  "4");
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					WebAnnotationApplicationContextBeanTestHelper.FieldTest bean =
							(FieldTest) request.getAttribute("fieldTest");
					
					assertEquals(1, bean.propertyA);
					assertEquals("teste", bean.propertyB);
					assertEquals(WebAnnotationApplicationContextBeanTestHelper.testDate, bean.propertyC);
					assertEquals(new Integer(2), bean.propertyD);
					assertEquals(new Integer(3), bean.propertyE);
				}
				
				public void checkException(Throwable e) {
					e.printStackTrace();
				}
				
			}, 
			new Class[]{WebAnnotationApplicationContextBeanTestHelper.ControllerTest.class}
		);
	}
	
}
