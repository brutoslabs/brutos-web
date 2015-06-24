package org.brandao.brutos.annotation;

import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.target.app1.Test1TargetBean;
import org.brandao.brutos.annotation.helper.target.app1.Test1TargetController;
import org.brandao.brutos.annotation.helper.target.fail.Test1FailTargetController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class TargetTest extends TestCase{
    
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
                	
                	Test1TargetController controller = (Test1TargetController)request.getAttribute("Controller");
                	
                	Assert.assertEquals(new Integer(1), controller.property1);
                	Assert.assertEquals(new Long(2), controller.getProperty2());
                	Assert.assertEquals(new BigDecimal(3), controller.property3);
                	
                	Test1TargetBean bean = (Test1TargetBean) controller.property4;
                	Assert.assertNotNull(bean);
                	
                	Assert.assertEquals(new Integer(4), bean.property1);
                	Assert.assertEquals(new Long(5), bean.getProperty2());
                	Assert.assertEquals(new BigDecimal(6), bean.property3);
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1TargetController.class});
    }
    
    public void testTest1FailRestrictionController() throws Throwable{
        WebApplicationContextTester.run(
            "", 
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                    Assert.fail("expected: {unknown type: Test1TargetBean}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("unknown type: Test1TargetBean"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {unknown type: Test1TargetBean}");
                }
            },
            new Class[]{Test1FailTargetController.class});
    }
    
}
