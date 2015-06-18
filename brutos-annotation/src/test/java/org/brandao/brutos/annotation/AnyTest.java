package org.brandao.brutos.annotation;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.any.app1.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app1.SetProperty;
import org.brandao.brutos.annotation.helper.any.app1.Test1AnyController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class AnyTest extends TestCase{
    
    public void testTest1ResultViewController_test1Action_decimal() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test1", 
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
                	parameters.put("propertyType", "decimal");
                	parameters.put("property.name", "propName");
                	parameters.put("property.length", "10");
                	parameters.put("property.decimals", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.annotation.helper.any.app1.Property property = 
                			controller.getProperty();
                	
                	Assert.assertEquals("propName", property.getName());
                	
                	Assert.assertTrue(property instanceof DecimalProperty);
                	Assert.assertEquals(10, ((DecimalProperty)property).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property).getDecimals());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1AnyController.class});
    }

    public void testTest1ResultViewController_test1Action_set() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test1", 
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
                	parameters.put("propertyType", "set");
                	parameters.put("property.name", "propName");
                	parameters.put("property.values[0]", "VALUE1");
                	parameters.put("property.values[1]", "VALUE2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.annotation.helper.any.app1.Property property = 
                			controller.getProperty();
                	
                	Assert.assertTrue(property instanceof SetProperty);
                	Assert.assertEquals("propName", property.getName());
                	
                	List<String> props = ((SetProperty)property).getValues();
                	
                	Assert.assertNull(props);
                	Assert.assertEquals(2, props.size());
                	Assert.assertEquals("VALUE1", props.get(0));
                	Assert.assertEquals("VALUE2", props.get(1));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1AnyController.class});
    }

    public void testTest1ResultViewController_test2Action_decimal() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test2", 
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
                	parameters.put("propertyType", "DECIMAL");
                	parameters.put("property.name", "propName");
                	parameters.put("property.length", "10");
                	parameters.put("property.decimals", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.annotation.helper.any.app1.Property property = 
                			controller.getProperty();
                	
                	Assert.assertEquals("propName", property.getName());
                	
                	Assert.assertTrue(property instanceof DecimalProperty);
                	Assert.assertEquals(10, ((DecimalProperty)property).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property).getDecimals());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1AnyController.class});
    }

    public void testTest1ResultViewController_test2Action_set() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test2", 
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
                	parameters.put("propertyType", "SET");
                	parameters.put("property.name", "propName");
                	parameters.put("property.values[0]", "VALUE1");
                	parameters.put("property.values[1]", "VALUE2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.annotation.helper.any.app1.Property property = 
                			controller.getProperty();
                	
                	Assert.assertTrue(property instanceof SetProperty);
                	Assert.assertEquals("propName", property.getName());
                	
                	List<String> props = ((SetProperty)property).getValues();
                	
                	Assert.assertNull(props);
                	Assert.assertEquals(2, props.size());
                	Assert.assertEquals("VALUE1", props.get(0));
                	Assert.assertEquals("VALUE2", props.get(1));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1AnyController.class});
    }

    public void testTest1ResultViewController_test3Action_decimal() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test3", 
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
                	parameters.put("propertyType", "0");
                	parameters.put("property.name", "propName");
                	parameters.put("property.length", "10");
                	parameters.put("property.decimals", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.annotation.helper.any.app1.Property property = 
                			controller.getProperty();
                	
                	Assert.assertEquals("propName", property.getName());
                	
                	Assert.assertTrue(property instanceof DecimalProperty);
                	Assert.assertEquals(10, ((DecimalProperty)property).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property).getDecimals());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1AnyController.class});
    }

    public void testTest1ResultViewController_test3Action_set() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test3", 
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
                	parameters.put("propertyType", "1");
                	parameters.put("property.name", "propName");
                	parameters.put("property.values[0]", "VALUE1");
                	parameters.put("property.values[1]", "VALUE2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.annotation.helper.any.app1.Property property = 
                			controller.getProperty();
                	
                	Assert.assertTrue(property instanceof SetProperty);
                	Assert.assertEquals("propName", property.getName());
                	
                	List<String> props = ((SetProperty)property).getValues();
                	
                	Assert.assertNull(props);
                	Assert.assertEquals(2, props.size());
                	Assert.assertEquals("VALUE1", props.get(0));
                	Assert.assertEquals("VALUE2", props.get(1));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1AnyController.class});
    }

    public void testTest1ResultViewController_test4Action_decimal() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test4", 
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
                	parameters.put("propertyType", "2015-01-01");
                	parameters.put("property.name", "propName");
                	parameters.put("property.length", "10");
                	parameters.put("property.decimals", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.annotation.helper.any.app1.Property property = 
                			controller.getProperty();
                	
                	Assert.assertEquals("propName", property.getName());
                	
                	Assert.assertTrue(property instanceof DecimalProperty);
                	Assert.assertEquals(10, ((DecimalProperty)property).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property).getDecimals());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1AnyController.class});
    }

    public void testTest1ResultViewController_test4Action_set() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test4", 
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
                	parameters.put("propertyType", "2015-01-02");
                	parameters.put("property.name", "propName");
                	parameters.put("property.values[0]", "VALUE1");
                	parameters.put("property.values[1]", "VALUE2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.annotation.helper.any.app1.Property property = 
                			controller.getProperty();
                	
                	Assert.assertTrue(property instanceof SetProperty);
                	Assert.assertEquals("propName", property.getName());
                	
                	List<String> props = ((SetProperty)property).getValues();
                	
                	Assert.assertNull(props);
                	Assert.assertEquals(2, props.size());
                	Assert.assertEquals("VALUE1", props.get(0));
                	Assert.assertEquals("VALUE2", props.get(1));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1AnyController.class});
    }
    
}
