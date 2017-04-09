package org.brandao.brutos.annotation;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.any.app2.Test10AnyController;
import org.brandao.brutos.annotation.helper.any.app2.Test5AnyBean;
import org.brandao.brutos.annotation.helper.any.app2.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app2.SetProperty;
import org.brandao.brutos.annotation.helper.any.app2.Test1AnyBean;
import org.brandao.brutos.annotation.helper.any.app2.Test1AnyController;
import org.brandao.brutos.annotation.helper.any.app2.Test2AnyBean;
import org.brandao.brutos.annotation.helper.any.app2.Test2AnyController;
import org.brandao.brutos.annotation.helper.any.app2.Test3AnyBean;
import org.brandao.brutos.annotation.helper.any.app2.Test3AnyController;
import org.brandao.brutos.annotation.helper.any.app2.Test4AnyBean;
import org.brandao.brutos.annotation.helper.any.app2.Test4AnyController;
import org.brandao.brutos.annotation.helper.any.app2.Test5AnyController;
import org.brandao.brutos.annotation.helper.any.app2.Test6AnyController;
import org.brandao.brutos.annotation.helper.any.app2.Test7AnyController;
import org.brandao.brutos.annotation.helper.any.app2.Test8AnyController;
import org.brandao.brutos.annotation.helper.any.app2.Test9AnyController;
import org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition.Test1AnyBeanMetaValuesDefinition;
import org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition.Test1AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition.Test2AnyBeanMetaValuesDefinition;
import org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition.Test2AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition.Test3AnyBeanMetaValuesDefinition;
import org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition.Test3AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition.Test4AnyBeanMetaValuesDefinition;
import org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition.Test4AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition.Test5AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition.Test6AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition.Test7AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition.Test8AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app2.metavaluesdefinition.Test9AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class AnyElementCollectionTest extends TestCase{
    
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
                	parameters.put("property.element[0].propertyType", "decimal");
                	parameters.put("property.element[0].name", "propName");
                	parameters.put("property.element[0].length", "10");
                	parameters.put("property.element[0].decimals", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
                	
                	assertEquals("propName", property.getName());
                	
                	assertTrue(property instanceof DecimalProperty);
                	assertEquals(10, ((DecimalProperty)property).getLength());
                	assertEquals(2, ((DecimalProperty)property).getDecimals());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
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
                	parameters.put("property.element[0].propertyType", "set");
                	parameters.put("property.element[0].name", "propName");
                	parameters.put("property.element[0].values.element[0]", "VALUE1");
                	parameters.put("property.element[0].values.element[1]", "VALUE2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
                	
                	assertTrue(property instanceof SetProperty);
                	assertEquals("propName", property.getName());
                	
                	List<String> props = ((SetProperty)property).getValues();
                	
                	assertNotNull(props);
                	assertEquals(2, props.size());
                	assertEquals("VALUE1", props.get(0));
                	assertEquals("VALUE2", props.get(1));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
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
                	parameters.put("property.element[0].propertyType", "DECIMAL");
                	parameters.put("property.element[0].name", "propName");
                	parameters.put("property.element[0].length", "10");
                	parameters.put("property.element[0].decimals", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
                	
                	assertEquals("propName", property.getName());
                	
                	assertTrue(property instanceof DecimalProperty);
                	assertEquals(10, ((DecimalProperty)property).getLength());
                	assertEquals(2, ((DecimalProperty)property).getDecimals());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
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
                	parameters.put("property.element[0].propertyType", "SET");
                	parameters.put("property.element[0].name", "propName");
                	parameters.put("property.element[0].values.element[0]", "VALUE1");
                	parameters.put("property.element[0].values.element[1]", "VALUE2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
                	
                	assertTrue(property instanceof SetProperty);
                	assertEquals("propName", property.getName());
                	
                	List<String> props = ((SetProperty)property).getValues();
                	
                	assertNotNull(props);
                	assertEquals(2, props.size());
                	assertEquals("VALUE1", props.get(0));
                	assertEquals("VALUE2", props.get(1));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
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
                	parameters.put("property.element[0].propertyType", "0");
                	parameters.put("property.element[0].name", "propName");
                	parameters.put("property.element[0].length", "10");
                	parameters.put("property.element[0].decimals", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
                	
                	assertEquals("propName", property.getName());
                	
                	assertTrue(property instanceof DecimalProperty);
                	assertEquals(10, ((DecimalProperty)property).getLength());
                	assertEquals(2, ((DecimalProperty)property).getDecimals());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
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
                	parameters.put("property.element[0].propertyType", "1");
                	parameters.put("property.element[0].name", "propName");
                	parameters.put("property.element[0].values.element[0]", "VALUE1");
                	parameters.put("property.element[0].values.element[1]", "VALUE2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
                	
                	assertTrue(property instanceof SetProperty);
                	assertEquals("propName", property.getName());
                	
                	List<String> props = ((SetProperty)property).getValues();
                	
                	assertNotNull(props);
                	assertEquals(2, props.size());
                	assertEquals("VALUE1", props.get(0));
                	assertEquals("VALUE2", props.get(1));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
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
                	parameters.put("property.element[0].propertyType", "2015-01-01");
                	parameters.put("property.element[0].name", "propName");
                	parameters.put("property.element[0].length", "10");
                	parameters.put("property.element[0].decimals", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
                	
                	assertEquals("propName", property.getName());
                	
                	assertTrue(property instanceof DecimalProperty);
                	assertEquals(10, ((DecimalProperty)property).getLength());
                	assertEquals(2, ((DecimalProperty)property).getDecimals());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
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
                	parameters.put("property.element[0].propertyType", "2015-01-02");
                	parameters.put("property.element[0].name", "propName");
                	parameters.put("property.element[0].values.element[0]", "VALUE1");
                	parameters.put("property.element[0].values.element[1]", "VALUE2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
                	
                	assertTrue(property instanceof SetProperty);
                	assertEquals("propName", property.getName());
                	
                	List<String> props = ((SetProperty)property).getValues();
                	
                	assertNotNull(props);
                	assertEquals(2, props.size());
                	assertEquals("VALUE1", props.get(0));
                	assertEquals("VALUE2", props.get(1));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test1AnyController.class});
    }

    public void testTest2AnyController_decimal() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("propertyA.element[0].propertyType", "decimal");
                	parameters.put("propertyA.element[0].name", "propName");
                	parameters.put("propertyA.element[0].length", "1");
                	parameters.put("propertyA.element[0].decimals", "2");
                	
                	parameters.put("propertyB.element[0].propertyType2", "decimal");
                	parameters.put("propertyB.element[0].name", "propName");
                	parameters.put("propertyB.element[0].length", "3");
                	parameters.put("propertyB.element[0].decimals", "4");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test2AnyController controller = (Test2AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName", property1.getName());
                	
                	assertTrue(property1 instanceof DecimalProperty);
                	assertEquals(1, ((DecimalProperty)property1).getLength());
                	assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName", property2.getName());
                	
                	assertTrue(property2 instanceof DecimalProperty);
                	assertEquals(3, ((DecimalProperty)property2).getLength());
                	assertEquals(4, ((DecimalProperty)property2).getDecimals());
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test2AnyController.class});
    }

    public void testTest2AnyController_set() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("propertyA.element[0].propertyType", "set");
                	parameters.put("propertyA.element[0].name", "propName");
                	parameters.put("propertyA.element[0].values.element[0]", "VALUE1");
                	parameters.put("propertyA.element[0].values.element[1]", "VALUE2");
                	parameters.put("propertyA.element[0].values.element[3]", "VALUEX");
                	
                	parameters.put("propertyB.element[0].propertyType2", "set");
                	parameters.put("propertyB.element[0].name", "propName");
                	parameters.put("propertyB.element[0].values.element[0]", "VALUE3");
                	parameters.put("propertyB.element[0].values.element[1]", "VALUE4");
                	parameters.put("propertyB.element[0].values.element[3]", "VALUEXX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test2AnyController controller = (Test2AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName", property1.getName());
                	
                	assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	assertNotNull(props1);
                	assertEquals(2, props1.size());
                	assertEquals("VALUE1", props1.get(0));
                	assertEquals("VALUE2", props1.get(1));
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName", property2.getName());
                	
                	assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	assertNotNull(props2);
                	assertEquals(2, props2.size());
                	assertEquals("VALUE3", props2.get(0));
                	assertEquals("VALUE4", props2.get(1));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test2AnyController.class});
    }

    public void testTest3AnyController_decimal() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("propertyA.element[0].propertyType", "DECIMAL");
                	parameters.put("propertyA.element[0].name", "propName");
                	parameters.put("propertyA.element[0].length", "1");
                	parameters.put("propertyA.element[0].decimals", "2");
                	
                	parameters.put("propertyB.element[0].propertyType2", "DECIMAL");
                	parameters.put("propertyB.element[0].name", "propName");
                	parameters.put("propertyB.element[0].length", "3");
                	parameters.put("propertyB.element[0].decimals", "4");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test3AnyController controller = (Test3AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName", property1.getName());
                	
                	assertTrue(property1 instanceof DecimalProperty);
                	assertEquals(1, ((DecimalProperty)property1).getLength());
                	assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName", property2.getName());
                	
                	assertTrue(property2 instanceof DecimalProperty);
                	assertEquals(3, ((DecimalProperty)property2).getLength());
                	assertEquals(4, ((DecimalProperty)property2).getDecimals());
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test3AnyController.class});
    }

    public void testTest3AnyController_set() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("propertyA.element[0].propertyType", "SET");
                	parameters.put("propertyA.element[0].name", "propName");
                	parameters.put("propertyA.element[0].values.element[0]", "VALUE1");
                	parameters.put("propertyA.element[0].values.element[1]", "VALUE2");
                	parameters.put("propertyA.element[0].values.element[3]", "VALUEX");
                	
                	parameters.put("propertyB.element[0].propertyType2", "SET");
                	parameters.put("propertyB.element[0].name", "propName");
                	parameters.put("propertyB.element[0].values.element[0]", "VALUE3");
                	parameters.put("propertyB.element[0].values.element[1]", "VALUE4");
                	parameters.put("propertyB.element[0].values.element[3]", "VALUEXX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test3AnyController controller = (Test3AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName", property1.getName());
                	
                	assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	assertNotNull(props1);
                	assertEquals(2, props1.size());
                	assertEquals("VALUE1", props1.get(0));
                	assertEquals("VALUE2", props1.get(1));
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName", property2.getName());
                	
                	assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	assertNotNull(props2);
                	assertEquals(2, props2.size());
                	assertEquals("VALUE3", props2.get(0));
                	assertEquals("VALUE4", props2.get(1));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test3AnyController.class});
    }

    public void testTest4AnyController_decimal() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("propertyA.element[0].propertyType", "0");
                	parameters.put("propertyA.element[0].name", "propName");
                	parameters.put("propertyA.element[0].length", "1");
                	parameters.put("propertyA.element[0].decimals", "2");
                	
                	parameters.put("propertyB.element[0].propertyType2", "0");
                	parameters.put("propertyB.element[0].name", "propName");
                	parameters.put("propertyB.element[0].length", "3");
                	parameters.put("propertyB.element[0].decimals", "4");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test4AnyController controller = (Test4AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName", property1.getName());
                	
                	assertTrue(property1 instanceof DecimalProperty);
                	assertEquals(1, ((DecimalProperty)property1).getLength());
                	assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName", property2.getName());
                	
                	assertTrue(property2 instanceof DecimalProperty);
                	assertEquals(3, ((DecimalProperty)property2).getLength());
                	assertEquals(4, ((DecimalProperty)property2).getDecimals());
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test4AnyController.class});
    }

    public void testTest4AnyController_set() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("propertyA.element[0].propertyType", "1");
                	parameters.put("propertyA.element[0].name", "propName");
                	parameters.put("propertyA.element[0].values.element[0]", "VALUE1");
                	parameters.put("propertyA.element[0].values.element[1]", "VALUE2");
                	parameters.put("propertyA.element[0].values.element[3]", "VALUEX");
                	
                	parameters.put("propertyB.element[0].propertyType2", "1");
                	parameters.put("propertyB.element[0].name", "propName");
                	parameters.put("propertyB.element[0].values.element[0]", "VALUE3");
                	parameters.put("propertyB.element[0].values.element[1]", "VALUE4");
                	parameters.put("propertyB.element[0].values.element[3]", "VALUEXX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test4AnyController controller = (Test4AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName", property1.getName());
                	
                	assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	assertNotNull(props1);
                	assertEquals(2, props1.size());
                	assertEquals("VALUE1", props1.get(0));
                	assertEquals("VALUE2", props1.get(1));
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName", property2.getName());
                	
                	assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	assertNotNull(props2);
                	assertEquals(2, props2.size());
                	assertEquals("VALUE3", props2.get(0));
                	assertEquals("VALUE4", props2.get(1));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test4AnyController.class});
    }

    public void testTest5AnyController_decimal() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("propertyA.element[0].propertyType", "2015-01-01");
                	parameters.put("propertyA.element[0].name", "propName");
                	parameters.put("propertyA.element[0].length", "1");
                	parameters.put("propertyA.element[0].decimals", "2");
                	
                	parameters.put("propertyB.element[0].propertyType2", "2015-01-01");
                	parameters.put("propertyB.element[0].name", "propName");
                	parameters.put("propertyB.element[0].length", "3");
                	parameters.put("propertyB.element[0].decimals", "4");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test5AnyController controller = (Test5AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName", property1.getName());
                	
                	assertTrue(property1 instanceof DecimalProperty);
                	assertEquals(1, ((DecimalProperty)property1).getLength());
                	assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName", property2.getName());
                	
                	assertTrue(property2 instanceof DecimalProperty);
                	assertEquals(3, ((DecimalProperty)property2).getLength());
                	assertEquals(4, ((DecimalProperty)property2).getDecimals());
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test5AnyController.class});
    }

    public void testTest5AnyController_set() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("propertyA.element[0].propertyType", "2015-01-02");
                	parameters.put("propertyA.element[0].name", "propName");
                	parameters.put("propertyA.element[0].values.element[0]", "VALUE1");
                	parameters.put("propertyA.element[0].values.element[1]", "VALUE2");
                	parameters.put("propertyA.element[0].values.element[3]", "VALUEX");
                	
                	parameters.put("propertyB.element[0].propertyType2", "2015-01-02");
                	parameters.put("propertyB.element[0].name", "propName");
                	parameters.put("propertyB.element[0].values.element[0]", "VALUE3");
                	parameters.put("propertyB.element[0].values.element[1]", "VALUE4");
                	parameters.put("propertyB.element[0].values.element[3]", "VALUEXX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test5AnyController controller = (Test5AnyController)request.getAttribute("Controller");
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName", property1.getName());
                	
                	assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	assertNotNull(props1);
                	assertEquals(2, props1.size());
                	assertEquals("VALUE1", props1.get(0));
                	assertEquals("VALUE2", props1.get(1));
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName", property2.getName());
                	
                	assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	assertNotNull(props2);
                	assertEquals(2, props2.size());
                	assertEquals("VALUE3", props2.get(0));
                	assertEquals("VALUE4", props2.get(1));
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test5AnyController.class});
    }
    
    public void testTest6AnyController_decimal() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("property1.propertyA.element[0].propertyType", "decimal");
                	parameters.put("property1.propertyA.element[0].name", "propName1");
                	parameters.put("property1.propertyA.element[0].length", "1");
                	parameters.put("property1.propertyA.element[0].decimals", "2");
                	
                	parameters.put("property1.propertyB.element[0].propertyType2", "decimal");
                	parameters.put("property1.propertyB.element[0].name", "propName2");
                	parameters.put("property1.propertyB.element[0].length", "3");
                	parameters.put("property1.propertyB.element[0].decimals", "4");

                	parameters.put("property1.propertyC.element[0].propertyType3", "decimal");
                	parameters.put("property1.propertyC.element[0].name", "propName3");
                	parameters.put("property1.propertyC.element[0].length", "5");
                	parameters.put("property1.propertyC.element[0].decimals", "6");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test6AnyController controller = (Test6AnyController)request.getAttribute("Controller");
                	
                	Test1AnyBean bean = controller.property1;
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName1", property1.getName());
                	
                	assertTrue(property1 instanceof DecimalProperty);
                	assertEquals(1, ((DecimalProperty)property1).getLength());
                	assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName2", property2.getName());
                	
                	assertTrue(property2 instanceof DecimalProperty);
                	assertEquals(3, ((DecimalProperty)property2).getLength());
                	assertEquals(4, ((DecimalProperty)property2).getDecimals());

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
                	
                	assertEquals("propName3", property3.getName());
                	
                	assertTrue(property3 instanceof DecimalProperty);
                	assertEquals(5, ((DecimalProperty)property3).getLength());
                	assertEquals(6, ((DecimalProperty)property3).getDecimals());
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test6AnyController.class});
    }

    public void testTest6AnyController_set() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("property1.propertyA.element[0].propertyType", "set");
                	parameters.put("property1.propertyA.element[0].name", "propName1");
                	parameters.put("property1.propertyA.element[0].values.element[0]", "VALUE1");
                	parameters.put("property1.propertyA.element[0].values.element[1]", "VALUE2");
                	parameters.put("property1.propertyA.element[0].values.element[3]", "VALUEX");
                	
                	parameters.put("property1.propertyB.element[0].propertyType2", "set");
                	parameters.put("property1.propertyB.element[0].name", "propName2");
                	parameters.put("property1.propertyB.element[0].values.element[0]", "VALUE3");
                	parameters.put("property1.propertyB.element[0].values.element[1]", "VALUE4");
                	parameters.put("property1.propertyB.element[0].values.element[3]", "VALUEX");

                	parameters.put("property1.propertyC.element[0].propertyType3", "set");
                	parameters.put("property1.propertyC.element[0].name", "propName3");
                	parameters.put("property1.propertyC.element[0].values.element[0]", "VALUE5");
                	parameters.put("property1.propertyC.element[0].values.element[1]", "VALUE6");
                	parameters.put("property1.propertyC.element[0].values.element[3]", "VALUEX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test6AnyController controller = (Test6AnyController)request.getAttribute("Controller");
                	
                	Test1AnyBean bean = controller.property1;
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName1", property1.getName());
                	
                	assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	assertNotNull(props1);
                	assertEquals(2, props1.size());
                	assertEquals("VALUE1", props1.get(0));
                	assertEquals("VALUE2", props1.get(1));
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName2", property2.getName());
                	
                	assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	assertNotNull(props2);
                	assertEquals(2, props2.size());
                	assertEquals("VALUE3", props2.get(0));
                	assertEquals("VALUE4", props2.get(1));                	

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);

                	assertEquals("propName3", property3.getName());
                	
                	assertTrue(property3 instanceof SetProperty);

                	List<String> props3 = ((SetProperty)property3).getValues();
                	
                	assertNotNull(props3);
                	assertEquals(2, props3.size());
                	assertEquals("VALUE5", props3.get(0));
                	assertEquals("VALUE6", props3.get(1));                	
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test6AnyController.class});
    }
    
    public void testTest7AnyController_decimal() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("property1.propertyA.element[0].propertyType", "DECIMAL");
                	parameters.put("property1.propertyA.element[0].name", "propName1");
                	parameters.put("property1.propertyA.element[0].length", "1");
                	parameters.put("property1.propertyA.element[0].decimals", "2");
                	
                	parameters.put("property1.propertyB.element[0].propertyType2", "DECIMAL");
                	parameters.put("property1.propertyB.element[0].name", "propName2");
                	parameters.put("property1.propertyB.element[0].length", "3");
                	parameters.put("property1.propertyB.element[0].decimals", "4");

                	parameters.put("property1.propertyC.element[0].propertyType3", "DECIMAL");
                	parameters.put("property1.propertyC.element[0].name", "propName3");
                	parameters.put("property1.propertyC.element[0].length", "5");
                	parameters.put("property1.propertyC.element[0].decimals", "6");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test7AnyController controller = (Test7AnyController)request.getAttribute("Controller");
                	
                	Test2AnyBean bean = controller.property1;
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName1", property1.getName());
                	
                	assertTrue(property1 instanceof DecimalProperty);
                	assertEquals(1, ((DecimalProperty)property1).getLength());
                	assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName2", property2.getName());
                	
                	assertTrue(property2 instanceof DecimalProperty);
                	assertEquals(3, ((DecimalProperty)property2).getLength());
                	assertEquals(4, ((DecimalProperty)property2).getDecimals());

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
                	
                	assertEquals("propName3", property3.getName());
                	
                	assertTrue(property3 instanceof DecimalProperty);
                	assertEquals(5, ((DecimalProperty)property3).getLength());
                	assertEquals(6, ((DecimalProperty)property3).getDecimals());
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test7AnyController.class});
    }

    public void testTest7AnyController_set() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("property1.propertyA.element[0].propertyType", "SET");
                	parameters.put("property1.propertyA.element[0].name", "propName1");
                	parameters.put("property1.propertyA.element[0].values.element[0]", "VALUE1");
                	parameters.put("property1.propertyA.element[0].values.element[1]", "VALUE2");
                	parameters.put("property1.propertyA.element[0].values.element[3]", "VALUEX");
                	
                	parameters.put("property1.propertyB.element[0].propertyType2", "SET");
                	parameters.put("property1.propertyB.element[0].name", "propName2");
                	parameters.put("property1.propertyB.element[0].values.element[0]", "VALUE3");
                	parameters.put("property1.propertyB.element[0].values.element[1]", "VALUE4");
                	parameters.put("property1.propertyB.element[0].values.element[3]", "VALUEX");

                	parameters.put("property1.propertyC.element[0].propertyType3", "SET");
                	parameters.put("property1.propertyC.element[0].name", "propName3");
                	parameters.put("property1.propertyC.element[0].values.element[0]", "VALUE5");
                	parameters.put("property1.propertyC.element[0].values.element[1]", "VALUE6");
                	parameters.put("property1.propertyC.element[0].values.element[3]", "VALUEX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test7AnyController controller = (Test7AnyController)request.getAttribute("Controller");
                	
                	Test2AnyBean bean = controller.property1;
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName1", property1.getName());
                	
                	assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	assertNotNull(props1);
                	assertEquals(2, props1.size());
                	assertEquals("VALUE1", props1.get(0));
                	assertEquals("VALUE2", props1.get(1));
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName2", property2.getName());
                	
                	assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	assertNotNull(props2);
                	assertEquals(2, props2.size());
                	assertEquals("VALUE3", props2.get(0));
                	assertEquals("VALUE4", props2.get(1));                	

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);

                	assertEquals("propName3", property3.getName());
                	
                	assertTrue(property3 instanceof SetProperty);

                	List<String> props3 = ((SetProperty)property3).getValues();
                	
                	assertNotNull(props3);
                	assertEquals(2, props3.size());
                	assertEquals("VALUE5", props3.get(0));
                	assertEquals("VALUE6", props3.get(1));                	
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test7AnyController.class});
    }    
    
    public void testTest8AnyController_decimal() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("property1.propertyA.element[0].propertyType", "0");
                	parameters.put("property1.propertyA.element[0].name", "propName1");
                	parameters.put("property1.propertyA.element[0].length", "1");
                	parameters.put("property1.propertyA.element[0].decimals", "2");
                	
                	parameters.put("property1.propertyB.element[0].propertyType2", "0");
                	parameters.put("property1.propertyB.element[0].name", "propName2");
                	parameters.put("property1.propertyB.element[0].length", "3");
                	parameters.put("property1.propertyB.element[0].decimals", "4");

                	parameters.put("property1.propertyC.element[0].propertyType3", "0");
                	parameters.put("property1.propertyC.element[0].name", "propName3");
                	parameters.put("property1.propertyC.element[0].length", "5");
                	parameters.put("property1.propertyC.element[0].decimals", "6");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test8AnyController controller = (Test8AnyController)request.getAttribute("Controller");
                	
                	Test3AnyBean bean = controller.property1;
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName1", property1.getName());
                	
                	assertTrue(property1 instanceof DecimalProperty);
                	assertEquals(1, ((DecimalProperty)property1).getLength());
                	assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName2", property2.getName());
                	
                	assertTrue(property2 instanceof DecimalProperty);
                	assertEquals(3, ((DecimalProperty)property2).getLength());
                	assertEquals(4, ((DecimalProperty)property2).getDecimals());

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
                	
                	assertEquals("propName3", property3.getName());
                	
                	assertTrue(property3 instanceof DecimalProperty);
                	assertEquals(5, ((DecimalProperty)property3).getLength());
                	assertEquals(6, ((DecimalProperty)property3).getDecimals());
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test8AnyController.class});
    }

    public void testTest8AnyController_set() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("property1.propertyA.element[0].propertyType", "1");
                	parameters.put("property1.propertyA.element[0].name", "propName1");
                	parameters.put("property1.propertyA.element[0].values.element[0]", "VALUE1");
                	parameters.put("property1.propertyA.element[0].values.element[1]", "VALUE2");
                	parameters.put("property1.propertyA.element[0].values.element[3]", "VALUEX");
                	
                	parameters.put("property1.propertyB.element[0].propertyType2", "1");
                	parameters.put("property1.propertyB.element[0].name", "propName2");
                	parameters.put("property1.propertyB.element[0].values.element[0]", "VALUE3");
                	parameters.put("property1.propertyB.element[0].values.element[1]", "VALUE4");
                	parameters.put("property1.propertyB.element[0].values.element[3]", "VALUEX");

                	parameters.put("property1.propertyC.element[0].propertyType3", "1");
                	parameters.put("property1.propertyC.element[0].name", "propName3");
                	parameters.put("property1.propertyC.element[0].values.element[0]", "VALUE5");
                	parameters.put("property1.propertyC.element[0].values.element[1]", "VALUE6");
                	parameters.put("property1.propertyC.element[0].values.element[3]", "VALUEX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test8AnyController controller = (Test8AnyController)request.getAttribute("Controller");
                	
                	Test3AnyBean bean = controller.property1;
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName1", property1.getName());
                	
                	assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	assertNotNull(props1);
                	assertEquals(2, props1.size());
                	assertEquals("VALUE1", props1.get(0));
                	assertEquals("VALUE2", props1.get(1));
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName2", property2.getName());
                	
                	assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	assertNotNull(props2);
                	assertEquals(2, props2.size());
                	assertEquals("VALUE3", props2.get(0));
                	assertEquals("VALUE4", props2.get(1));                	

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);

                	assertEquals("propName3", property3.getName());
                	
                	assertTrue(property3 instanceof SetProperty);

                	List<String> props3 = ((SetProperty)property3).getValues();
                	
                	assertNotNull(props3);
                	assertEquals(2, props3.size());
                	assertEquals("VALUE5", props3.get(0));
                	assertEquals("VALUE6", props3.get(1));                	
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test8AnyController.class});
    }    

    public void testTest9AnyController_decimal() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("property1.propertyA.element[0].propertyType", "2015-01-01");
                	parameters.put("property1.propertyA.element[0].name", "propName1");
                	parameters.put("property1.propertyA.element[0].length", "1");
                	parameters.put("property1.propertyA.element[0].decimals", "2");
                	
                	parameters.put("property1.propertyB.element[0].propertyType2", "2015-01-01");
                	parameters.put("property1.propertyB.element[0].name", "propName2");
                	parameters.put("property1.propertyB.element[0].length", "3");
                	parameters.put("property1.propertyB.element[0].decimals", "4");

                	parameters.put("property1.propertyC.element[0].propertyType3", "2015-01-01");
                	parameters.put("property1.propertyC.element[0].name", "propName3");
                	parameters.put("property1.propertyC.element[0].length", "5");
                	parameters.put("property1.propertyC.element[0].decimals", "6");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test9AnyController controller = (Test9AnyController)request.getAttribute("Controller");
                	
                	Test4AnyBean bean = controller.property1;
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName1", property1.getName());
                	
                	assertTrue(property1 instanceof DecimalProperty);
                	assertEquals(1, ((DecimalProperty)property1).getLength());
                	assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName2", property2.getName());
                	
                	assertTrue(property2 instanceof DecimalProperty);
                	assertEquals(3, ((DecimalProperty)property2).getLength());
                	assertEquals(4, ((DecimalProperty)property2).getDecimals());

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
                	
                	assertEquals("propName3", property3.getName());
                	
                	assertTrue(property3 instanceof DecimalProperty);
                	assertEquals(5, ((DecimalProperty)property3).getLength());
                	assertEquals(6, ((DecimalProperty)property3).getDecimals());
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test9AnyController.class});
    }

    public void testTest9AnyController_set() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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
                	parameters.put("property1.propertyA.element[0].propertyType", "2015-01-02");
                	parameters.put("property1.propertyA.element[0].name", "propName1");
                	parameters.put("property1.propertyA.element[0].values.element[0]", "VALUE1");
                	parameters.put("property1.propertyA.element[0].values.element[1]", "VALUE2");
                	parameters.put("property1.propertyA.element[0].values.element[3]", "VALUEX");
                	
                	parameters.put("property1.propertyB.element[0].propertyType2", "2015-01-02");
                	parameters.put("property1.propertyB.element[0].name", "propName2");
                	parameters.put("property1.propertyB.element[0].values.element[0]", "VALUE3");
                	parameters.put("property1.propertyB.element[0].values.element[1]", "VALUE4");
                	parameters.put("property1.propertyB.element[0].values.element[3]", "VALUEX");

                	parameters.put("property1.propertyC.element[0].propertyType3", "2015-01-02");
                	parameters.put("property1.propertyC.element[0].name", "propName3");
                	parameters.put("property1.propertyC.element[0].values.element[0]", "VALUE5");
                	parameters.put("property1.propertyC.element[0].values.element[1]", "VALUE6");
                	parameters.put("property1.propertyC.element[0].values.element[3]", "VALUEX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test9AnyController controller = (Test9AnyController)request.getAttribute("Controller");
                	
                	Test4AnyBean bean = controller.property1;
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	assertEquals("propName1", property1.getName());
                	
                	assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	assertNotNull(props1);
                	assertEquals(2, props1.size());
                	assertEquals("VALUE1", props1.get(0));
                	assertEquals("VALUE2", props1.get(1));
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	assertEquals("propName2", property2.getName());
                	
                	assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	assertNotNull(props2);
                	assertEquals(2, props2.size());
                	assertEquals("VALUE3", props2.get(0));
                	assertEquals("VALUE4", props2.get(1));                	

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);

                	assertEquals("propName3", property3.getName());
                	
                	assertTrue(property3 instanceof SetProperty);

                	List<String> props3 = ((SetProperty)property3).getValues();
                	
                	assertNotNull(props3);
                	assertEquals(2, props3.size());
                	assertEquals("VALUE5", props3.get(0));
                	assertEquals("VALUE6", props3.get(1));                	
                	
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{Test9AnyController.class});
    }    

	public void testTest1AnyMetaValuesDefinitionController_test1Action_decimal() throws Throwable{
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
	            	parameters.put("property.element[0].propertyType", "decimal");
	            	parameters.put("property.element[0].name", "propName");
	            	parameters.put("property.element[0].length", "10");
	            	parameters.put("property.element[0].decimals", "2");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
	            	assertEquals("propName", property.getName());
	            	
	            	assertTrue(property instanceof DecimalProperty);
	            	assertEquals(10, ((DecimalProperty)property).getLength());
	            	assertEquals(2, ((DecimalProperty)property).getDecimals());
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test1AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest1AnyMetaValuesDefinitionController_test1Action_set() throws Throwable{
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
	            	parameters.put("property.element[0].propertyType", "set");
	            	parameters.put("property.element[0].name", "propName");
	            	parameters.put("property.element[0].values.element[0]", "VALUE1");
	            	parameters.put("property.element[0].values.element[1]", "VALUE2");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
	            	assertTrue(property instanceof SetProperty);
	            	assertEquals("propName", property.getName());
	            	
	            	List<String> props = ((SetProperty)property).getValues();
	            	
	            	assertNotNull(props);
	            	assertEquals(2, props.size());
	            	assertEquals("VALUE1", props.get(0));
	            	assertEquals("VALUE2", props.get(1));
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test1AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest1AnyMetaValuesDefinitionController_test2Action_decimal() throws Throwable{
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
	            	parameters.put("property.element[0].propertyType", "DECIMAL");
	            	parameters.put("property.element[0].name", "propName");
	            	parameters.put("property.element[0].length", "10");
	            	parameters.put("property.element[0].decimals", "2");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
	            	assertEquals("propName", property.getName());
	            	
	            	assertTrue(property instanceof DecimalProperty);
	            	assertEquals(10, ((DecimalProperty)property).getLength());
	            	assertEquals(2, ((DecimalProperty)property).getDecimals());
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test1AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest1AnyMetaValuesDefinitionController_test2Action_set() throws Throwable{
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
	            	parameters.put("property.element[0].propertyType", "SET");
	            	parameters.put("property.element[0].name", "propName");
	            	parameters.put("property.element[0].values.element[0]", "VALUE1");
	            	parameters.put("property.element[0].values.element[1]", "VALUE2");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
	            	assertTrue(property instanceof SetProperty);
	            	assertEquals("propName", property.getName());
	            	
	            	List<String> props = ((SetProperty)property).getValues();
	            	
	            	assertNotNull(props);
	            	assertEquals(2, props.size());
	            	assertEquals("VALUE1", props.get(0));
	            	assertEquals("VALUE2", props.get(1));
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test1AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest1AnyMetaValuesDefinitionController_test3Action_decimal() throws Throwable{
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
	            	parameters.put("property.element[0].propertyType", "0");
	            	parameters.put("property.element[0].name", "propName");
	            	parameters.put("property.element[0].length", "10");
	            	parameters.put("property.element[0].decimals", "2");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
	            	assertEquals("propName", property.getName());
	            	
	            	assertTrue(property instanceof DecimalProperty);
	            	assertEquals(10, ((DecimalProperty)property).getLength());
	            	assertEquals(2, ((DecimalProperty)property).getDecimals());
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test1AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest1AnyMetaValuesDefinitionController_test3Action_set() throws Throwable{
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
	            	parameters.put("property.element[0].propertyType", "1");
	            	parameters.put("property.element[0].name", "propName");
	            	parameters.put("property.element[0].values.element[0]", "VALUE1");
	            	parameters.put("property.element[0].values.element[1]", "VALUE2");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
	            	assertTrue(property instanceof SetProperty);
	            	assertEquals("propName", property.getName());
	            	
	            	List<String> props = ((SetProperty)property).getValues();
	            	
	            	assertNotNull(props);
	            	assertEquals(2, props.size());
	            	assertEquals("VALUE1", props.get(0));
	            	assertEquals("VALUE2", props.get(1));
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test1AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest1AnyMetaValuesDefinitionController_test4Action_decimal() throws Throwable{
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
	            	parameters.put("property.element[0].propertyType", "2015-01-01");
	            	parameters.put("property.element[0].name", "propName");
	            	parameters.put("property.element[0].length", "10");
	            	parameters.put("property.element[0].decimals", "2");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
	            	assertEquals("propName", property.getName());
	            	
	            	assertTrue(property instanceof DecimalProperty);
	            	assertEquals(10, ((DecimalProperty)property).getLength());
	            	assertEquals(2, ((DecimalProperty)property).getDecimals());
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test1AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest1AnyMetaValuesDefinitionController_test4Action_set() throws Throwable{
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
	            	parameters.put("property.element[0].propertyType", "2015-01-02");
	            	parameters.put("property.element[0].name", "propName");
	            	parameters.put("property.element[0].values.element[0]", "VALUE1");
	            	parameters.put("property.element[0].values.element[1]", "VALUE2");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
	            	assertTrue(property instanceof SetProperty);
	            	assertEquals("propName", property.getName());
	            	
	            	List<String> props = ((SetProperty)property).getValues();
	            	
	            	assertNotNull(props);
	            	assertEquals(2, props.size());
	            	assertEquals("VALUE1", props.get(0));
	            	assertEquals("VALUE2", props.get(1));
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test1AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest2AnyMetaValuesDefinitionController_decimal() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("propertyA.element[0].propertyType", "decimal");
	            	parameters.put("propertyA.element[0].name", "propName");
	            	parameters.put("propertyA.element[0].length", "1");
	            	parameters.put("propertyA.element[0].decimals", "2");
	            	
	            	parameters.put("propertyB.element[0].propertyType2", "decimal");
	            	parameters.put("propertyB.element[0].name", "propName");
	            	parameters.put("propertyB.element[0].length", "3");
	            	parameters.put("propertyB.element[0].decimals", "4");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test2AnyMetaValuesDefinitionController controller = (Test2AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName", property1.getName());
	            	
	            	assertTrue(property1 instanceof DecimalProperty);
	            	assertEquals(1, ((DecimalProperty)property1).getLength());
	            	assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName", property2.getName());
	            	
	            	assertTrue(property2 instanceof DecimalProperty);
	            	assertEquals(3, ((DecimalProperty)property2).getLength());
	            	assertEquals(4, ((DecimalProperty)property2).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test2AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest2AnyMetaValuesDefinitionController_set() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("propertyA.element[0].propertyType", "set");
	            	parameters.put("propertyA.element[0].name", "propName");
	            	parameters.put("propertyA.element[0].values.element[0]", "VALUE1");
	            	parameters.put("propertyA.element[0].values.element[1]", "VALUE2");
	            	parameters.put("propertyA.element[0].values.element[3]", "VALUEX");
	            	
	            	parameters.put("propertyB.element[0].propertyType2", "set");
	            	parameters.put("propertyB.element[0].name", "propName");
	            	parameters.put("propertyB.element[0].values.element[0]", "VALUE3");
	            	parameters.put("propertyB.element[0].values.element[1]", "VALUE4");
	            	parameters.put("propertyB.element[0].values.element[3]", "VALUEXX");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test2AnyMetaValuesDefinitionController controller = (Test2AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName", property1.getName());
	            	
	            	assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	assertNotNull(props1);
	            	assertEquals(2, props1.size());
	            	assertEquals("VALUE1", props1.get(0));
	            	assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName", property2.getName());
	            	
	            	assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	assertNotNull(props2);
	            	assertEquals(2, props2.size());
	            	assertEquals("VALUE3", props2.get(0));
	            	assertEquals("VALUE4", props2.get(1));
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test2AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest3AnyMetaValuesDefinitionController_decimal() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("propertyA.element[0].propertyType", "DECIMAL");
	            	parameters.put("propertyA.element[0].name", "propName");
	            	parameters.put("propertyA.element[0].length", "1");
	            	parameters.put("propertyA.element[0].decimals", "2");
	            	
	            	parameters.put("propertyB.element[0].propertyType2", "DECIMAL");
	            	parameters.put("propertyB.element[0].name", "propName");
	            	parameters.put("propertyB.element[0].length", "3");
	            	parameters.put("propertyB.element[0].decimals", "4");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test3AnyMetaValuesDefinitionController controller = (Test3AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName", property1.getName());
	            	
	            	assertTrue(property1 instanceof DecimalProperty);
	            	assertEquals(1, ((DecimalProperty)property1).getLength());
	            	assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName", property2.getName());
	            	
	            	assertTrue(property2 instanceof DecimalProperty);
	            	assertEquals(3, ((DecimalProperty)property2).getLength());
	            	assertEquals(4, ((DecimalProperty)property2).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test3AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest3AnyMetaValuesDefinitionController_set() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("propertyA.element[0].propertyType", "SET");
	            	parameters.put("propertyA.element[0].name", "propName");
	            	parameters.put("propertyA.element[0].values.element[0]", "VALUE1");
	            	parameters.put("propertyA.element[0].values.element[1]", "VALUE2");
	            	parameters.put("propertyA.element[0].values.element[3]", "VALUEX");
	            	
	            	parameters.put("propertyB.element[0].propertyType2", "SET");
	            	parameters.put("propertyB.element[0].name", "propName");
	            	parameters.put("propertyB.element[0].values.element[0]", "VALUE3");
	            	parameters.put("propertyB.element[0].values.element[1]", "VALUE4");
	            	parameters.put("propertyB.element[0].values.element[3]", "VALUEXX");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test3AnyMetaValuesDefinitionController controller = (Test3AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName", property1.getName());
	            	
	            	assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	assertNotNull(props1);
	            	assertEquals(2, props1.size());
	            	assertEquals("VALUE1", props1.get(0));
	            	assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName", property2.getName());
	            	
	            	assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	assertNotNull(props2);
	            	assertEquals(2, props2.size());
	            	assertEquals("VALUE3", props2.get(0));
	            	assertEquals("VALUE4", props2.get(1));
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test3AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest4AnyMetaValuesDefinitionController_decimal() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("propertyA.element[0].propertyType", "0");
	            	parameters.put("propertyA.element[0].name", "propName");
	            	parameters.put("propertyA.element[0].length", "1");
	            	parameters.put("propertyA.element[0].decimals", "2");
	            	
	            	parameters.put("propertyB.element[0].propertyType2", "0");
	            	parameters.put("propertyB.element[0].name", "propName");
	            	parameters.put("propertyB.element[0].length", "3");
	            	parameters.put("propertyB.element[0].decimals", "4");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test4AnyMetaValuesDefinitionController controller = (Test4AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName", property1.getName());
	            	
	            	assertTrue(property1 instanceof DecimalProperty);
	            	assertEquals(1, ((DecimalProperty)property1).getLength());
	            	assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName", property2.getName());
	            	
	            	assertTrue(property2 instanceof DecimalProperty);
	            	assertEquals(3, ((DecimalProperty)property2).getLength());
	            	assertEquals(4, ((DecimalProperty)property2).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test4AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest4AnyMetaValuesDefinitionController_set() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("propertyA.element[0].propertyType", "1");
	            	parameters.put("propertyA.element[0].name", "propName");
	            	parameters.put("propertyA.element[0].values.element[0]", "VALUE1");
	            	parameters.put("propertyA.element[0].values.element[1]", "VALUE2");
	            	parameters.put("propertyA.element[0].values.element[3]", "VALUEX");
	            	
	            	parameters.put("propertyB.element[0].propertyType2", "1");
	            	parameters.put("propertyB.element[0].name", "propName");
	            	parameters.put("propertyB.element[0].values.element[0]", "VALUE3");
	            	parameters.put("propertyB.element[0].values.element[1]", "VALUE4");
	            	parameters.put("propertyB.element[0].values.element[3]", "VALUEXX");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test4AnyMetaValuesDefinitionController controller = (Test4AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName", property1.getName());
	            	
	            	assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	assertNotNull(props1);
	            	assertEquals(2, props1.size());
	            	assertEquals("VALUE1", props1.get(0));
	            	assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName", property2.getName());
	            	
	            	assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	assertNotNull(props2);
	            	assertEquals(2, props2.size());
	            	assertEquals("VALUE3", props2.get(0));
	            	assertEquals("VALUE4", props2.get(1));
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test4AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest5AnyMetaValuesDefinitionController_decimal() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("propertyA.element[0].propertyType", "2015-01-01");
	            	parameters.put("propertyA.element[0].name", "propName");
	            	parameters.put("propertyA.element[0].length", "1");
	            	parameters.put("propertyA.element[0].decimals", "2");
	            	
	            	parameters.put("propertyB.element[0].propertyType2", "2015-01-01");
	            	parameters.put("propertyB.element[0].name", "propName");
	            	parameters.put("propertyB.element[0].length", "3");
	            	parameters.put("propertyB.element[0].decimals", "4");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test5AnyMetaValuesDefinitionController controller = (Test5AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName", property1.getName());
	            	
	            	assertTrue(property1 instanceof DecimalProperty);
	            	assertEquals(1, ((DecimalProperty)property1).getLength());
	            	assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName", property2.getName());
	            	
	            	assertTrue(property2 instanceof DecimalProperty);
	            	assertEquals(3, ((DecimalProperty)property2).getLength());
	            	assertEquals(4, ((DecimalProperty)property2).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test5AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest5AnyMetaValuesDefinitionController_set() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("propertyA.element[0].propertyType", "2015-01-02");
	            	parameters.put("propertyA.element[0].name", "propName");
	            	parameters.put("propertyA.element[0].values.element[0]", "VALUE1");
	            	parameters.put("propertyA.element[0].values.element[1]", "VALUE2");
	            	parameters.put("propertyA.element[0].values.element[3]", "VALUEX");
	            	
	            	parameters.put("propertyB.element[0].propertyType2", "2015-01-02");
	            	parameters.put("propertyB.element[0].name", "propName");
	            	parameters.put("propertyB.element[0].values.element[0]", "VALUE3");
	            	parameters.put("propertyB.element[0].values.element[1]", "VALUE4");
	            	parameters.put("propertyB.element[0].values.element[3]", "VALUEXX");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test5AnyMetaValuesDefinitionController controller = (Test5AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName", property1.getName());
	            	
	            	assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	assertNotNull(props1);
	            	assertEquals(2, props1.size());
	            	assertEquals("VALUE1", props1.get(0));
	            	assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName", property2.getName());
	            	
	            	assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	assertNotNull(props2);
	            	assertEquals(2, props2.size());
	            	assertEquals("VALUE3", props2.get(0));
	            	assertEquals("VALUE4", props2.get(1));
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test5AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest6AnyMetaValuesDefinitionController_decimal() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("property1.propertyA.element[0].propertyType", "decimal");
	            	parameters.put("property1.propertyA.element[0].name", "propName1");
	            	parameters.put("property1.propertyA.element[0].length", "1");
	            	parameters.put("property1.propertyA.element[0].decimals", "2");
	            	
	            	parameters.put("property1.propertyB.element[0].propertyType2", "decimal");
	            	parameters.put("property1.propertyB.element[0].name", "propName2");
	            	parameters.put("property1.propertyB.element[0].length", "3");
	            	parameters.put("property1.propertyB.element[0].decimals", "4");
	
	            	parameters.put("property1.propertyC.element[0].propertyType3", "decimal");
	            	parameters.put("property1.propertyC.element[0].name", "propName3");
	            	parameters.put("property1.propertyC.element[0].length", "5");
	            	parameters.put("property1.propertyC.element[0].decimals", "6");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test6AnyMetaValuesDefinitionController controller = (Test6AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test1AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName1", property1.getName());
	            	
	            	assertTrue(property1 instanceof DecimalProperty);
	            	assertEquals(1, ((DecimalProperty)property1).getLength());
	            	assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName2", property2.getName());
	            	
	            	assertTrue(property2 instanceof DecimalProperty);
	            	assertEquals(3, ((DecimalProperty)property2).getLength());
	            	assertEquals(4, ((DecimalProperty)property2).getDecimals());
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	            	
	            	assertEquals("propName3", property3.getName());
	            	
	            	assertTrue(property3 instanceof DecimalProperty);
	            	assertEquals(5, ((DecimalProperty)property3).getLength());
	            	assertEquals(6, ((DecimalProperty)property3).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test6AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest6AnyMetaValuesDefinitionController_set() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("property1.propertyA.element[0].propertyType", "set");
	            	parameters.put("property1.propertyA.element[0].name", "propName1");
	            	parameters.put("property1.propertyA.element[0].values.element[0]", "VALUE1");
	            	parameters.put("property1.propertyA.element[0].values.element[1]", "VALUE2");
	            	parameters.put("property1.propertyA.element[0].values.element[3]", "VALUEX");
	            	
	            	parameters.put("property1.propertyB.element[0].propertyType2", "set");
	            	parameters.put("property1.propertyB.element[0].name", "propName2");
	            	parameters.put("property1.propertyB.element[0].values.element[0]", "VALUE3");
	            	parameters.put("property1.propertyB.element[0].values.element[1]", "VALUE4");
	            	parameters.put("property1.propertyB.element[0].values.element[3]", "VALUEX");
	
	            	parameters.put("property1.propertyC.element[0].propertyType3", "set");
	            	parameters.put("property1.propertyC.element[0].name", "propName3");
	            	parameters.put("property1.propertyC.element[0].values.element[0]", "VALUE5");
	            	parameters.put("property1.propertyC.element[0].values.element[1]", "VALUE6");
	            	parameters.put("property1.propertyC.element[0].values.element[3]", "VALUEX");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test6AnyMetaValuesDefinitionController controller = (Test6AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test1AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName1", property1.getName());
	            	
	            	assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	assertNotNull(props1);
	            	assertEquals(2, props1.size());
	            	assertEquals("VALUE1", props1.get(0));
	            	assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName2", property2.getName());
	            	
	            	assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	assertNotNull(props2);
	            	assertEquals(2, props2.size());
	            	assertEquals("VALUE3", props2.get(0));
	            	assertEquals("VALUE4", props2.get(1));                	
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	
	            	assertEquals("propName3", property3.getName());
	            	
	            	assertTrue(property3 instanceof SetProperty);
	
	            	List<String> props3 = ((SetProperty)property3).getValues();
	            	
	            	assertNotNull(props3);
	            	assertEquals(2, props3.size());
	            	assertEquals("VALUE5", props3.get(0));
	            	assertEquals("VALUE6", props3.get(1));                	
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test6AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest7AnyMetaValuesDefinitionController_decimal() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("property1.propertyA.element[0].propertyType", "DECIMAL");
	            	parameters.put("property1.propertyA.element[0].name", "propName1");
	            	parameters.put("property1.propertyA.element[0].length", "1");
	            	parameters.put("property1.propertyA.element[0].decimals", "2");
	            	
	            	parameters.put("property1.propertyB.element[0].propertyType2", "DECIMAL");
	            	parameters.put("property1.propertyB.element[0].name", "propName2");
	            	parameters.put("property1.propertyB.element[0].length", "3");
	            	parameters.put("property1.propertyB.element[0].decimals", "4");
	
	            	parameters.put("property1.propertyC.element[0].propertyType3", "DECIMAL");
	            	parameters.put("property1.propertyC.element[0].name", "propName3");
	            	parameters.put("property1.propertyC.element[0].length", "5");
	            	parameters.put("property1.propertyC.element[0].decimals", "6");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test7AnyMetaValuesDefinitionController controller = (Test7AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test2AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName1", property1.getName());
	            	
	            	assertTrue(property1 instanceof DecimalProperty);
	            	assertEquals(1, ((DecimalProperty)property1).getLength());
	            	assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName2", property2.getName());
	            	
	            	assertTrue(property2 instanceof DecimalProperty);
	            	assertEquals(3, ((DecimalProperty)property2).getLength());
	            	assertEquals(4, ((DecimalProperty)property2).getDecimals());
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	            	
	            	assertEquals("propName3", property3.getName());
	            	
	            	assertTrue(property3 instanceof DecimalProperty);
	            	assertEquals(5, ((DecimalProperty)property3).getLength());
	            	assertEquals(6, ((DecimalProperty)property3).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test7AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest7AnyMetaValuesDefinitionController_set() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("property1.propertyA.element[0].propertyType", "SET");
	            	parameters.put("property1.propertyA.element[0].name", "propName1");
	            	parameters.put("property1.propertyA.element[0].values.element[0]", "VALUE1");
	            	parameters.put("property1.propertyA.element[0].values.element[1]", "VALUE2");
	            	parameters.put("property1.propertyA.element[0].values.element[3]", "VALUEX");
	            	
	            	parameters.put("property1.propertyB.element[0].propertyType2", "SET");
	            	parameters.put("property1.propertyB.element[0].name", "propName2");
	            	parameters.put("property1.propertyB.element[0].values.element[0]", "VALUE3");
	            	parameters.put("property1.propertyB.element[0].values.element[1]", "VALUE4");
	            	parameters.put("property1.propertyB.element[0].values.element[3]", "VALUEX");
	
	            	parameters.put("property1.propertyC.element[0].propertyType3", "SET");
	            	parameters.put("property1.propertyC.element[0].name", "propName3");
	            	parameters.put("property1.propertyC.element[0].values.element[0]", "VALUE5");
	            	parameters.put("property1.propertyC.element[0].values.element[1]", "VALUE6");
	            	parameters.put("property1.propertyC.element[0].values.element[3]", "VALUEX");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test7AnyMetaValuesDefinitionController controller = (Test7AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test2AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName1", property1.getName());
	            	
	            	assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	assertNotNull(props1);
	            	assertEquals(2, props1.size());
	            	assertEquals("VALUE1", props1.get(0));
	            	assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName2", property2.getName());
	            	
	            	assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	assertNotNull(props2);
	            	assertEquals(2, props2.size());
	            	assertEquals("VALUE3", props2.get(0));
	            	assertEquals("VALUE4", props2.get(1));                	
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	
	            	assertEquals("propName3", property3.getName());
	            	
	            	assertTrue(property3 instanceof SetProperty);
	
	            	List<String> props3 = ((SetProperty)property3).getValues();
	            	
	            	assertNotNull(props3);
	            	assertEquals(2, props3.size());
	            	assertEquals("VALUE5", props3.get(0));
	            	assertEquals("VALUE6", props3.get(1));                	
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test7AnyMetaValuesDefinitionController.class});
	}    
	
	public void testTest8AnyMetaValuesDefinitionController_decimal() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("property1.propertyA.element[0].propertyType", "0");
	            	parameters.put("property1.propertyA.element[0].name", "propName1");
	            	parameters.put("property1.propertyA.element[0].length", "1");
	            	parameters.put("property1.propertyA.element[0].decimals", "2");
	            	
	            	parameters.put("property1.propertyB.element[0].propertyType2", "0");
	            	parameters.put("property1.propertyB.element[0].name", "propName2");
	            	parameters.put("property1.propertyB.element[0].length", "3");
	            	parameters.put("property1.propertyB.element[0].decimals", "4");
	
	            	parameters.put("property1.propertyC.element[0].propertyType3", "0");
	            	parameters.put("property1.propertyC.element[0].name", "propName3");
	            	parameters.put("property1.propertyC.element[0].length", "5");
	            	parameters.put("property1.propertyC.element[0].decimals", "6");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test8AnyMetaValuesDefinitionController controller = (Test8AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test3AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName1", property1.getName());
	            	
	            	assertTrue(property1 instanceof DecimalProperty);
	            	assertEquals(1, ((DecimalProperty)property1).getLength());
	            	assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName2", property2.getName());
	            	
	            	assertTrue(property2 instanceof DecimalProperty);
	            	assertEquals(3, ((DecimalProperty)property2).getLength());
	            	assertEquals(4, ((DecimalProperty)property2).getDecimals());
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	            	
	            	assertEquals("propName3", property3.getName());
	            	
	            	assertTrue(property3 instanceof DecimalProperty);
	            	assertEquals(5, ((DecimalProperty)property3).getLength());
	            	assertEquals(6, ((DecimalProperty)property3).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test8AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest8AnyMetaValuesDefinitionController_set() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("property1.propertyA.element[0].propertyType", "1");
	            	parameters.put("property1.propertyA.element[0].name", "propName1");
	            	parameters.put("property1.propertyA.element[0].values.element[0]", "VALUE1");
	            	parameters.put("property1.propertyA.element[0].values.element[1]", "VALUE2");
	            	parameters.put("property1.propertyA.element[0].values.element[3]", "VALUEX");
	            	
	            	parameters.put("property1.propertyB.element[0].propertyType2", "1");
	            	parameters.put("property1.propertyB.element[0].name", "propName2");
	            	parameters.put("property1.propertyB.element[0].values.element[0]", "VALUE3");
	            	parameters.put("property1.propertyB.element[0].values.element[1]", "VALUE4");
	            	parameters.put("property1.propertyB.element[0].values.element[3]", "VALUEX");
	
	            	parameters.put("property1.propertyC.element[0].propertyType3", "1");
	            	parameters.put("property1.propertyC.element[0].name", "propName3");
	            	parameters.put("property1.propertyC.element[0].values.element[0]", "VALUE5");
	            	parameters.put("property1.propertyC.element[0].values.element[1]", "VALUE6");
	            	parameters.put("property1.propertyC.element[0].values.element[3]", "VALUEX");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test8AnyMetaValuesDefinitionController controller = (Test8AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test3AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName1", property1.getName());
	            	
	            	assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	assertNotNull(props1);
	            	assertEquals(2, props1.size());
	            	assertEquals("VALUE1", props1.get(0));
	            	assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName2", property2.getName());
	            	
	            	assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	assertNotNull(props2);
	            	assertEquals(2, props2.size());
	            	assertEquals("VALUE3", props2.get(0));
	            	assertEquals("VALUE4", props2.get(1));                	
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	
	            	assertEquals("propName3", property3.getName());
	            	
	            	assertTrue(property3 instanceof SetProperty);
	
	            	List<String> props3 = ((SetProperty)property3).getValues();
	            	
	            	assertNotNull(props3);
	            	assertEquals(2, props3.size());
	            	assertEquals("VALUE5", props3.get(0));
	            	assertEquals("VALUE6", props3.get(1));                	
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test8AnyMetaValuesDefinitionController.class});
	}    
	
	public void testTest9AnyMetaValuesDefinitionController_decimal() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("property1.propertyA.element[0].propertyType", "2015-01-01");
	            	parameters.put("property1.propertyA.element[0].name", "propName1");
	            	parameters.put("property1.propertyA.element[0].length", "1");
	            	parameters.put("property1.propertyA.element[0].decimals", "2");
	            	
	            	parameters.put("property1.propertyB.element[0].propertyType2", "2015-01-01");
	            	parameters.put("property1.propertyB.element[0].name", "propName2");
	            	parameters.put("property1.propertyB.element[0].length", "3");
	            	parameters.put("property1.propertyB.element[0].decimals", "4");
	
	            	parameters.put("property1.propertyC.element[0].propertyType3", "2015-01-01");
	            	parameters.put("property1.propertyC.element[0].name", "propName3");
	            	parameters.put("property1.propertyC.element[0].length", "5");
	            	parameters.put("property1.propertyC.element[0].decimals", "6");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test9AnyMetaValuesDefinitionController controller = (Test9AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test4AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName1", property1.getName());
	            	
	            	assertTrue(property1 instanceof DecimalProperty);
	            	assertEquals(1, ((DecimalProperty)property1).getLength());
	            	assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName2", property2.getName());
	            	
	            	assertTrue(property2 instanceof DecimalProperty);
	            	assertEquals(3, ((DecimalProperty)property2).getLength());
	            	assertEquals(4, ((DecimalProperty)property2).getDecimals());
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	            	
	            	assertEquals("propName3", property3.getName());
	            	
	            	assertTrue(property3 instanceof DecimalProperty);
	            	assertEquals(5, ((DecimalProperty)property3).getLength());
	            	assertEquals(6, ((DecimalProperty)property3).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test9AnyMetaValuesDefinitionController.class});
	}
	
	public void testTest9AnyMetaValuesDefinitionController_set() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller", 
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
	            	parameters.put("property1.propertyA.element[0].propertyType", "2015-01-02");
	            	parameters.put("property1.propertyA.element[0].name", "propName1");
	            	parameters.put("property1.propertyA.element[0].values.element[0]", "VALUE1");
	            	parameters.put("property1.propertyA.element[0].values.element[1]", "VALUE2");
	            	parameters.put("property1.propertyA.element[0].values.element[3]", "VALUEX");
	            	
	            	parameters.put("property1.propertyB.element[0].propertyType2", "2015-01-02");
	            	parameters.put("property1.propertyB.element[0].name", "propName2");
	            	parameters.put("property1.propertyB.element[0].values.element[0]", "VALUE3");
	            	parameters.put("property1.propertyB.element[0].values.element[1]", "VALUE4");
	            	parameters.put("property1.propertyB.element[0].values.element[3]", "VALUEX");
	
	            	parameters.put("property1.propertyC.element[0].propertyType3", "2015-01-02");
	            	parameters.put("property1.propertyC.element[0].name", "propName3");
	            	parameters.put("property1.propertyC.element[0].values.element[0]", "VALUE5");
	            	parameters.put("property1.propertyC.element[0].values.element[1]", "VALUE6");
	            	parameters.put("property1.propertyC.element[0].values.element[3]", "VALUEX");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test9AnyMetaValuesDefinitionController controller = (Test9AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test4AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName1", property1.getName());
	            	
	            	assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	assertNotNull(props1);
	            	assertEquals(2, props1.size());
	            	assertEquals("VALUE1", props1.get(0));
	            	assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName2", property2.getName());
	            	
	            	assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	assertNotNull(props2);
	            	assertEquals(2, props2.size());
	            	assertEquals("VALUE3", props2.get(0));
	            	assertEquals("VALUE4", props2.get(1));                	
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	
	            	assertEquals("propName3", property3.getName());
	            	
	            	assertTrue(property3 instanceof SetProperty);
	
	            	List<String> props3 = ((SetProperty)property3).getValues();
	            	
	            	assertNotNull(props3);
	            	assertEquals(2, props3.size());
	            	assertEquals("VALUE5", props3.get(0));
	            	assertEquals("VALUE6", props3.get(1));                	
	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test9AnyMetaValuesDefinitionController.class});
	}    

	public void testTest10Controller() throws Throwable{
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
	            	parameters.put("property1.propertyA.element[0].propertyType", "set");
	            	parameters.put("property1.propertyA.element[0].name", "propName1");
	            	parameters.put("property1.propertyA.element[0].values.element[0]", "VALUE1");
	            	parameters.put("property1.propertyA.element[0].values.element[1]", "VALUE2");
	            	parameters.put("property1.propertyA.element[0].values.element[3]", "VALUEX");
	            	
	            	parameters.put("property1.propertyB.element[0].propertyType2", "set");
	            	parameters.put("property1.propertyB.element[0].name", "propName2");
	            	parameters.put("property1.propertyB.element[0].values.element[0]", "VALUE3");
	            	parameters.put("property1.propertyB.element[0].values.element[1]", "VALUE4");
	            	parameters.put("property1.propertyB.element[0].values.element[3]", "VALUEX");
	
	            	parameters.put("property1.propertyC.element[0].propertyType3", "set");
	            	parameters.put("property1.propertyC.element[0].name", "propName3");
	            	parameters.put("property1.propertyC.element[0].values.element[0]", "VALUE5");
	            	parameters.put("property1.propertyC.element[0].values.element[1]", "VALUE6");
	            	parameters.put("property1.propertyC.element[0].values.element[3]", "VALUEX");

	            	parameters.put("propertyD.element[0].propertyType4", "set");
	            	parameters.put("propertyD.element[0].name", "propName4");
	            	parameters.put("propertyD.element[0].values.element[0]", "VALUE7");
	            	parameters.put("propertyD.element[0].values.element[1]", "VALUE8");
	            	parameters.put("propertyD.element[0].values.element[3]", "VALUEX");
	            	
	            	parameters.put("propertyE.element[0].propertyType5", "set");
	            	parameters.put("propertyE.element[0].name", "propName5");
	            	parameters.put("propertyE.element[0].values.element[0]", "VALUE9");
	            	parameters.put("propertyE.element[0].values.element[1]", "VALUE10");
	            	parameters.put("propertyE.element[0].values.element[3]", "VALUEX");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test10AnyController controller = (Test10AnyController)request.getAttribute("Controller");
	            	
	            	Test5AnyBean bean = controller.property1;
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	assertEquals("propName1", property1.getName());
	            	
	            	assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	assertNotNull(props1);
	            	assertEquals(2, props1.size());
	            	assertEquals("VALUE1", props1.get(0));
	            	assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	assertEquals("propName2", property2.getName());
	            	
	            	assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	assertNotNull(props2);
	            	assertEquals(2, props2.size());
	            	assertEquals("VALUE3", props2.get(0));
	            	assertEquals("VALUE4", props2.get(1));                	
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	
	            	assertEquals("propName3", property3.getName());
	            	
	            	assertTrue(property3 instanceof SetProperty);
	
	            	List<String> props3 = ((SetProperty)property3).getValues();
	            	
	            	assertNotNull(props3);
	            	assertEquals(2, props3.size());
	            	assertEquals("VALUE5", props3.get(0));
	            	assertEquals("VALUE6", props3.get(1));                   	

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list4 = 
                			controller.property2;
                	
                	assertEquals(1, list4.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property4 = list4.get(0);
	
	            	assertEquals("propName4", property4.getName());
	            	
	            	assertTrue(property4 instanceof SetProperty);
	
	            	List<String> props4 = ((SetProperty)property4).getValues();
	            	
	            	assertNotNull(props4);
	            	assertEquals(2, props4.size());
	            	assertEquals("VALUE7", props4.get(0));
	            	assertEquals("VALUE8", props4.get(1));                   	
	            
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list5 = 
                			controller.property3;
                	
                	assertEquals(1, list5.size());
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property5 = list5.get(0);
	
	            	assertEquals("propName5", property5.getName());
	            	
	            	assertTrue(property5 instanceof SetProperty);
	
	            	List<String> props5 = ((SetProperty)property5).getValues();
	            	
	            	assertNotNull(props5);
	            	assertEquals(2, props5.size());
	            	assertEquals("VALUE9", props5.get(0));
	            	assertEquals("VALUE10", props5.get(1));           	            	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test10AnyController.class});
	}    
	
}
