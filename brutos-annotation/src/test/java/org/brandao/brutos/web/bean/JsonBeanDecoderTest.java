package org.brandao.brutos.web.bean;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.bean.helper.JsonBeanEncoderBean;
import org.brandao.brutos.web.test.BasicWebApplicationTester;
import org.brandao.brutos.web.test.WebApplicationContextTester;

import com.mockrunner.mock.web.MockHttpServletRequest;

public class JsonBeanDecoderTest extends TestCase{

    public void testIntProperty() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
            new BasicWebApplicationTester() {

                public void prepareRequest(MockHttpServletRequest request){
                	try{
                		String requestData =
            				  "{"
                			 + "\"propertyA\": 10"
                	         + "}";
                		
	                	request.setContentType("application/json; charset=UTF-8");
	                	request.setBodyContent(requestData);
	                	request.setHeader("Accept", "*/*");
                	}
                	catch(Throwable ex){
                		fail(ex.toString());
                	}
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	JsonBeanDecoderControllerTest controller = 
                			(JsonBeanDecoderControllerTest)request
                			.getAttribute(BrutosConstants.CONTROLLER);
                	
                	JsonBeanEncoderBean p = controller.getProperty();
                	assertNotNull(p);
                	assertEquals(10, p.getPropertyA());
                }
                
            },
            new Class[]{JsonBeanDecoderControllerTest.class});
    }
	
    public class LocalWebApplicationTester extends BasicWebApplicationTester{
    	
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
        	e.printStackTrace();
        	JsonBeanDecoderTest.fail(e.toString());
        }
        
    }
    
    @Controller("/controller")
    public class JsonBeanDecoderControllerTest{
		
		private JsonBeanEncoderBean property;

		public JsonBeanEncoderBean getProperty() {
			return property;
		}

		public void setProperty(JsonBeanEncoderBean property) {
			this.property = property;
		}
		
	}
}
