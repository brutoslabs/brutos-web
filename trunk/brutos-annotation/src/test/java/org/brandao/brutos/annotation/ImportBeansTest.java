package org.brandao.brutos.annotation;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.importbeans.app1.ControllerBeanTest;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.BasicWebApplicationTester;
import org.brandao.brutos.web.test.WebApplicationContextTester;

public class ImportBeansTest extends TestCase{
    
    public void testControllerBeanTest() throws Throwable{
        WebApplicationContextTester.run(
            "", 
            new BasicWebApplicationTester() {

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
                	
                	org.brandao.brutos.mapping.Controller controller = 
                			applicationContext.getControllerManager().getController(ControllerBeanTest.class);
                	
                	assertNotNull(controller);
                	assertNotNull(controller.getBean("beanTest"));
                	assertEquals(org.brandao.brutos.annotation.helper.importbeans.app1.BeanTest.class,controller.getBean("beanTest").getClassType());
                	assertNotNull(controller.getBean("beanTest2"));
                	assertEquals(org.brandao.brutos.annotation.helper.importbeans.app1.BeanTest2.class,controller.getBean("beanTest2").getClassType());
                	assertNotNull(controller.getBean("beanName"));
                	assertEquals(org.brandao.brutos.annotation.helper.importbeans.app1.BeanTest3.class,controller.getBean("beanName").getClassType());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerBeanTest.class});
    }
    
}
