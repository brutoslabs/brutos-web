package org.brandao.brutos.annotation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.type.app1.Test1TypeBean;
import org.brandao.brutos.annotation.helper.type.app1.Test1TypeController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class TypeTest extends TestCase{
    
    public void testTest1TargetController() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
            new WebApplicationTester() {

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

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property1","1");
                	parameters.put("property2","2");
                	parameters.put("arg0","3");
                	
                	parameters.put("property4.property1","4");
                	parameters.put("property4.property2","5");
                	parameters.put("property4.arg0","6");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1TypeController controller = (Test1TypeController)request.getAttribute("Controller");
                	
                	Assert.assertEquals("xx-1", controller.property1);
                	Assert.assertEquals("xx-2", controller.getProperty2());
                	Assert.assertEquals("xx-3", controller.property3);
                	
                	Test1TypeBean bean = controller.property4;
                	Assert.assertNotNull(bean);
                	
                	Assert.assertEquals("xx-4", bean.property1);
                	Assert.assertEquals("xx-5", bean.getProperty2());
                	Assert.assertEquals("xx-6", bean.property3);
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1TypeController.class});
    }
    
}
