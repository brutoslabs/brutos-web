package org.brandao.brutos.annotation;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.any.app3.Test10AnyController;
import org.brandao.brutos.annotation.helper.any.app3.Test5AnyBean;
import org.brandao.brutos.annotation.helper.any.app3.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app3.SetProperty;
import org.brandao.brutos.annotation.helper.any.app3.Test1AnyBean;
import org.brandao.brutos.annotation.helper.any.app3.Test1AnyController;
import org.brandao.brutos.annotation.helper.any.app3.Test2AnyBean;
import org.brandao.brutos.annotation.helper.any.app3.Test2AnyController;
import org.brandao.brutos.annotation.helper.any.app3.Test3AnyBean;
import org.brandao.brutos.annotation.helper.any.app3.Test3AnyController;
import org.brandao.brutos.annotation.helper.any.app3.Test4AnyBean;
import org.brandao.brutos.annotation.helper.any.app3.Test4AnyController;
import org.brandao.brutos.annotation.helper.any.app3.Test5AnyController;
import org.brandao.brutos.annotation.helper.any.app3.Test6AnyController;
import org.brandao.brutos.annotation.helper.any.app3.Test7AnyController;
import org.brandao.brutos.annotation.helper.any.app3.Test8AnyController;
import org.brandao.brutos.annotation.helper.any.app3.Test9AnyController;
import org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition.Test1AnyBeanMetaValuesDefinition;
import org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition.Test1AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition.Test2AnyBeanMetaValuesDefinition;
import org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition.Test2AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition.Test3AnyBeanMetaValuesDefinition;
import org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition.Test3AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition.Test4AnyBeanMetaValuesDefinition;
import org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition.Test4AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition.Test5AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition.Test6AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition.Test7AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition.Test8AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition.Test9AnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class AnyKeyCollectionTest extends TestCase{
    
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
                	parameters.put("property.key[0].propertyType", "decimal");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0].length", "10");
                	parameters.put("property.key[0].decimals", "2");
                	parameters.put("property.element[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 10, 2)));
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
                	parameters.put("property.key[0].propertyType", "set");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0].values.element[0]", "VALUE1");
                	parameters.put("property.key[0].values.element[1]", "VALUE2");
                	parameters.put("property.element[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
                	
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
                	parameters.put("property.key[0].propertyType", "DECIMAL");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0].length", "10");
                	parameters.put("property.key[0].decimals", "2");
                	parameters.put("property.element[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 10, 2)));
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
                	parameters.put("property.key[0].propertyType", "SET");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0].values.element[0]", "VALUE1");
                	parameters.put("property.key[0].values.element[1]", "VALUE2");
                	parameters.put("property.element[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
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
                	parameters.put("property.key[0].propertyType", "0");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0].length", "10");
                	parameters.put("property.key[0].decimals", "2");
                	parameters.put("property.element[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 10, 2)));
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
                	parameters.put("property.key[0].propertyType", "1");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0].values.element[0]", "VALUE1");
                	parameters.put("property.key[0].values.element[1]", "VALUE2");
                	parameters.put("property.element[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
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
                	parameters.put("property.key[0].propertyType", "2015-01-01");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0].length", "10");
                	parameters.put("property.key[0].decimals", "2");
                	parameters.put("property.element[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 10, 2)));
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
                	parameters.put("property.key[0].propertyType", "2015-01-02");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0].values.element[0]", "VALUE1");
                	parameters.put("property.key[0].values.element[1]", "VALUE2");
                	parameters.put("property.element[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
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
                	parameters.put("propertyA.key[0].propertyType", "decimal");
                	parameters.put("propertyA.key[0].name", "propName");
                	parameters.put("propertyA.key[0].length", "1");
                	parameters.put("propertyA.key[0].decimals", "2");
                	parameters.put("propertyA.element[0]", "valuex");
                	
                	parameters.put("propertyB.key[0].propertyType2", "decimal");
                	parameters.put("propertyB.key[0].name", "propName");
                	parameters.put("propertyB.key[0].length", "3");
                	parameters.put("propertyB.key[0].decimals", "4");
                	parameters.put("propertyB.element[0]", "valuexx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test2AnyController controller = (Test2AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 1, 2)));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName", 3, 4)));
                	
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
                	parameters.put("propertyA.key[0].propertyType", "set");
                	parameters.put("propertyA.key[0].name", "propName");
                	parameters.put("propertyA.key[0].values.element[0]", "VALUE1");
                	parameters.put("propertyA.key[0].values.element[1]", "VALUE2");
                	parameters.put("propertyA.key[0].values.element[3]", "VALUEX");
                	parameters.put("propertyA.element[0]", "valuex");
                	
                	parameters.put("propertyB.key[0].propertyType2", "set");
                	parameters.put("propertyB.key[0].name", "propName");
                	parameters.put("propertyB.key[0].values.element[0]", "VALUE3");
                	parameters.put("propertyB.key[0].values.element[1]", "VALUE4");
                	parameters.put("propertyB.key[0].values.element[3]", "VALUEXX");
                	parameters.put("propertyB.element[0]", "valuexx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test2AnyController controller = (Test2AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName", new String[]{"VALUE3","VALUE4"})));
                	
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
                	parameters.put("propertyA.key[0].propertyType", "DECIMAL");
                	parameters.put("propertyA.key[0].name", "propName");
                	parameters.put("propertyA.key[0].length", "1");
                	parameters.put("propertyA.key[0].decimals", "2");
                	parameters.put("propertyA.element[0]", "valuex");
                	
                	parameters.put("propertyB.key[0].propertyType2", "DECIMAL");
                	parameters.put("propertyB.key[0].name", "propName");
                	parameters.put("propertyB.key[0].length", "3");
                	parameters.put("propertyB.key[0].decimals", "4");
                	parameters.put("propertyB.element[0]", "valuexx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test3AnyController controller = (Test3AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 1, 2)));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName", 3, 4)));
                	
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
                	parameters.put("propertyA.key[0].propertyType", "SET");
                	parameters.put("propertyA.key[0].name", "propName");
                	parameters.put("propertyA.key[0].values.element[0]", "VALUE1");
                	parameters.put("propertyA.key[0].values.element[1]", "VALUE2");
                	parameters.put("propertyA.key[0].values.element[3]", "VALUEX");
                	parameters.put("propertyA.element[0]", "valuex");
                	
                	parameters.put("propertyB.key[0].propertyType2", "SET");
                	parameters.put("propertyB.key[0].name", "propName");
                	parameters.put("propertyB.key[0].values.element[0]", "VALUE3");
                	parameters.put("propertyB.key[0].values.element[1]", "VALUE4");
                	parameters.put("propertyB.key[0].values.element[3]", "VALUEXX");
                	parameters.put("propertyB.element[0]", "valuexx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test3AnyController controller = (Test3AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName", new String[]{"VALUE3","VALUE4"})));
                	
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
                	parameters.put("propertyA.key[0].propertyType", "0");
                	parameters.put("propertyA.key[0].name", "propName");
                	parameters.put("propertyA.key[0].length", "1");
                	parameters.put("propertyA.key[0].decimals", "2");
                	parameters.put("propertyA.element[0]", "valuex");
                	
                	parameters.put("propertyB.key[0].propertyType2", "0");
                	parameters.put("propertyB.key[0].name", "propName");
                	parameters.put("propertyB.key[0].length", "3");
                	parameters.put("propertyB.key[0].decimals", "4");
                	parameters.put("propertyB.element[0]", "valuexx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test4AnyController controller = (Test4AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 1, 2)));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName", 3, 4)));
                	
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
                	parameters.put("propertyA.key[0].propertyType", "1");
                	parameters.put("propertyA.key[0].name", "propName");
                	parameters.put("propertyA.key[0].values.element[0]", "VALUE1");
                	parameters.put("propertyA.key[0].values.element[1]", "VALUE2");
                	parameters.put("propertyA.key[0].values.element[3]", "VALUEX");
                	parameters.put("propertyA.element[0]", "valuex");
                	
                	parameters.put("propertyB.key[0].propertyType2", "1");
                	parameters.put("propertyB.key[0].name", "propName");
                	parameters.put("propertyB.key[0].values.element[0]", "VALUE3");
                	parameters.put("propertyB.key[0].values.element[1]", "VALUE4");
                	parameters.put("propertyB.key[0].values.element[3]", "VALUEXX");
                	parameters.put("propertyB.element[0]", "valuexx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test4AnyController controller = (Test4AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName", new String[]{"VALUE3","VALUE4"})));
                	
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
                	parameters.put("propertyA.key[0].propertyType", "2015-01-01");
                	parameters.put("propertyA.key[0].name", "propName");
                	parameters.put("propertyA.key[0].length", "1");
                	parameters.put("propertyA.key[0].decimals", "2");
                	parameters.put("propertyA.element[0]", "valuex");
                	
                	parameters.put("propertyB.key[0].propertyType2", "2015-01-01");
                	parameters.put("propertyB.key[0].name", "propName");
                	parameters.put("propertyB.key[0].length", "3");
                	parameters.put("propertyB.key[0].decimals", "4");
                	parameters.put("propertyB.element[0]", "valuexx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test5AnyController controller = (Test5AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 1, 2)));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName", 3, 4)));
                	
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
                	parameters.put("propertyA.key[0].propertyType", "2015-01-02");
                	parameters.put("propertyA.key[0].name", "propName");
                	parameters.put("propertyA.key[0].values.element[0]", "VALUE1");
                	parameters.put("propertyA.key[0].values.element[1]", "VALUE2");
                	parameters.put("propertyA.key[0].values.element[3]", "VALUEX");
                	parameters.put("propertyA.element[0]", "valuex");
                	
                	parameters.put("propertyB.key[0].propertyType2", "2015-01-02");
                	parameters.put("propertyB.key[0].name", "propName");
                	parameters.put("propertyB.key[0].values.element[0]", "VALUE3");
                	parameters.put("propertyB.key[0].values.element[1]", "VALUE4");
                	parameters.put("propertyB.key[0].values.element[3]", "VALUEXX");
                	parameters.put("propertyB.element[0]", "valuexx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test5AnyController controller = (Test5AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName", new String[]{"VALUE3","VALUE4"})));
                	
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
                	parameters.put("property1.propertyA.key[0].propertyType", "decimal");
                	parameters.put("property1.propertyA.key[0].name", "propName1");
                	parameters.put("property1.propertyA.key[0].length", "1");
                	parameters.put("property1.propertyA.key[0].decimals", "2");
                	parameters.put("property1.propertyA.element[0]", "valuex");
                	
                	parameters.put("property1.propertyB.key[0].propertyType2", "decimal");
                	parameters.put("property1.propertyB.key[0].name", "propName2");
                	parameters.put("property1.propertyB.key[0].length", "3");
                	parameters.put("property1.propertyB.key[0].decimals", "4");
                	parameters.put("property1.propertyB.element[0]", "valuexx");

                	parameters.put("property1.propertyC.key[0].propertyType3", "decimal");
                	parameters.put("property1.propertyC.key[0].name", "propName3");
                	parameters.put("property1.propertyC.key[0].length", "5");
                	parameters.put("property1.propertyC.key[0].decimals", "6");
                	parameters.put("property1.propertyC.element[0]", "valuexxx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test6AnyController controller = (Test6AnyController)request.getAttribute("Controller");
                	
                	Test1AnyBean bean = controller.property1;
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName1", 1, 2)));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName2", 3, 4)));

                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new DecimalProperty("propName3", 5, 6)));
                	
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
                	parameters.put("property1.propertyA.key[0].propertyType", "set");
                	parameters.put("property1.propertyA.key[0].name", "propName1");
                	parameters.put("property1.propertyA.key[0].values.element[0]", "VALUE1");
                	parameters.put("property1.propertyA.key[0].values.element[1]", "VALUE2");
                	parameters.put("property1.propertyA.key[0].values.element[3]", "VALUEX");
                	parameters.put("property1.propertyA.element[0]", "valuex");
                	
                	parameters.put("property1.propertyB.key[0].propertyType2", "set");
                	parameters.put("property1.propertyB.key[0].name", "propName2");
                	parameters.put("property1.propertyB.key[0].values.element[0]", "VALUE3");
                	parameters.put("property1.propertyB.key[0].values.element[1]", "VALUE4");
                	parameters.put("property1.propertyB.key[0].values.element[3]", "VALUEX");
                	parameters.put("property1.propertyB.element[0]", "valuexx");

                	parameters.put("property1.propertyC.key[0].propertyType3", "set");
                	parameters.put("property1.propertyC.key[0].name", "propName3");
                	parameters.put("property1.propertyC.key[0].values.element[0]", "VALUE5");
                	parameters.put("property1.propertyC.key[0].values.element[1]", "VALUE6");
                	parameters.put("property1.propertyC.key[0].values.element[3]", "VALUEX");
                	parameters.put("property1.propertyC.element[0]", "valuexxx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test6AnyController controller = (Test6AnyController)request.getAttribute("Controller");
                	
                	Test1AnyBean bean = controller.property1;
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName1", new String[]{"VALUE1","VALUE2"})));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName2", new String[]{"VALUE3","VALUE4"})));

                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new SetProperty("propName3", new String[]{"VALUE5","VALUE6"})));
                	
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
                	parameters.put("property1.propertyA.key[0].propertyType", "DECIMAL");
                	parameters.put("property1.propertyA.key[0].name", "propName1");
                	parameters.put("property1.propertyA.key[0].length", "1");
                	parameters.put("property1.propertyA.key[0].decimals", "2");
                	parameters.put("property1.propertyA.element[0]", "valuex");
                	
                	parameters.put("property1.propertyB.key[0].propertyType2", "DECIMAL");
                	parameters.put("property1.propertyB.key[0].name", "propName2");
                	parameters.put("property1.propertyB.key[0].length", "3");
                	parameters.put("property1.propertyB.key[0].decimals", "4");
                	parameters.put("property1.propertyB.element[0]", "valuexx");

                	parameters.put("property1.propertyC.key[0].propertyType3", "DECIMAL");
                	parameters.put("property1.propertyC.key[0].name", "propName3");
                	parameters.put("property1.propertyC.key[0].length", "5");
                	parameters.put("property1.propertyC.key[0].decimals", "6");
                	parameters.put("property1.propertyC.element[0]", "valuexxx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test7AnyController controller = (Test7AnyController)request.getAttribute("Controller");
                	
                	Test2AnyBean bean = controller.property1;
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName1", 1, 2)));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName2", 3, 4)));

                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new DecimalProperty("propName3", 5, 6)));
                	
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
                	parameters.put("property1.propertyA.key[0].propertyType", "SET");
                	parameters.put("property1.propertyA.key[0].name", "propName1");
                	parameters.put("property1.propertyA.key[0].values.element[0]", "VALUE1");
                	parameters.put("property1.propertyA.key[0].values.element[1]", "VALUE2");
                	parameters.put("property1.propertyA.key[0].values.element[3]", "VALUEX");
                	parameters.put("property1.propertyA.element[0]", "valuex");
                	
                	parameters.put("property1.propertyB.key[0].propertyType2", "SET");
                	parameters.put("property1.propertyB.key[0].name", "propName2");
                	parameters.put("property1.propertyB.key[0].values.element[0]", "VALUE3");
                	parameters.put("property1.propertyB.key[0].values.element[1]", "VALUE4");
                	parameters.put("property1.propertyB.key[0].values.element[3]", "VALUEX");
                	parameters.put("property1.propertyB.element[0]", "valuexx");

                	parameters.put("property1.propertyC.key[0].propertyType3", "SET");
                	parameters.put("property1.propertyC.key[0].name", "propName3");
                	parameters.put("property1.propertyC.key[0].values.element[0]", "VALUE5");
                	parameters.put("property1.propertyC.key[0].values.element[1]", "VALUE6");
                	parameters.put("property1.propertyC.key[0].values.element[3]", "VALUEX");
                	parameters.put("property1.propertyC.element[0]", "valuexxx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test7AnyController controller = (Test7AnyController)request.getAttribute("Controller");
                	
                	Test2AnyBean bean = controller.property1;
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName1", new String[]{"VALUE1","VALUE2"})));

                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName2", new String[]{"VALUE3","VALUE4"})));

                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new SetProperty("propName3", new String[]{"VALUE5","VALUE6"})));
                	
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
                	parameters.put("property1.propertyA.key[0].propertyType", "0");
                	parameters.put("property1.propertyA.key[0].name", "propName1");
                	parameters.put("property1.propertyA.key[0].length", "1");
                	parameters.put("property1.propertyA.key[0].decimals", "2");
                	parameters.put("property1.propertyA.element[0]", "valuex");
                	
                	parameters.put("property1.propertyB.key[0].propertyType2", "0");
                	parameters.put("property1.propertyB.key[0].name", "propName2");
                	parameters.put("property1.propertyB.key[0].length", "3");
                	parameters.put("property1.propertyB.key[0].decimals", "4");
                	parameters.put("property1.propertyB.element[0]", "valuexx");

                	parameters.put("property1.propertyC.key[0].propertyType3", "0");
                	parameters.put("property1.propertyC.key[0].name", "propName3");
                	parameters.put("property1.propertyC.key[0].length", "5");
                	parameters.put("property1.propertyC.key[0].decimals", "6");
                	parameters.put("property1.propertyC.element[0]", "valuexxx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test8AnyController controller = (Test8AnyController)request.getAttribute("Controller");
                	
                	Test3AnyBean bean = controller.property1;
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName1", 1, 2)));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName2", 3, 4)));

                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new DecimalProperty("propName3", 5, 6)));
                	
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
                	parameters.put("property1.propertyA.key[0].propertyType", "1");
                	parameters.put("property1.propertyA.key[0].name", "propName1");
                	parameters.put("property1.propertyA.key[0].values.element[0]", "VALUE1");
                	parameters.put("property1.propertyA.key[0].values.element[1]", "VALUE2");
                	parameters.put("property1.propertyA.key[0].values.element[3]", "VALUEX");
                	parameters.put("property1.propertyA.element[0]", "valuex");
                	
                	parameters.put("property1.propertyB.key[0].propertyType2", "1");
                	parameters.put("property1.propertyB.key[0].name", "propName2");
                	parameters.put("property1.propertyB.key[0].values.element[0]", "VALUE3");
                	parameters.put("property1.propertyB.key[0].values.element[1]", "VALUE4");
                	parameters.put("property1.propertyB.key[0].values.element[3]", "VALUEX");
                	parameters.put("property1.propertyB.element[0]", "valuexx");

                	parameters.put("property1.propertyC.key[0].propertyType3", "1");
                	parameters.put("property1.propertyC.key[0].name", "propName3");
                	parameters.put("property1.propertyC.key[0].values.element[0]", "VALUE5");
                	parameters.put("property1.propertyC.key[0].values.element[1]", "VALUE6");
                	parameters.put("property1.propertyC.key[0].values.element[3]", "VALUEX");
                	parameters.put("property1.propertyC.element[0]", "valuexxx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test8AnyController controller = (Test8AnyController)request.getAttribute("Controller");
                	
                	Test3AnyBean bean = controller.property1;
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName1", new String[]{"VALUE1","VALUE2"})));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName2", new String[]{"VALUE3","VALUE4"})));

                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new SetProperty("propName3", new String[]{"VALUE5","VALUE6"})));
                	
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
                	parameters.put("property1.propertyA.key[0].propertyType", "2015-01-01");
                	parameters.put("property1.propertyA.key[0].name", "propName1");
                	parameters.put("property1.propertyA.key[0].length", "1");
                	parameters.put("property1.propertyA.key[0].decimals", "2");
                	parameters.put("property1.propertyA.element[0]", "valuex");
                	
                	parameters.put("property1.propertyB.key[0].propertyType2", "2015-01-01");
                	parameters.put("property1.propertyB.key[0].name", "propName2");
                	parameters.put("property1.propertyB.key[0].length", "3");
                	parameters.put("property1.propertyB.key[0].decimals", "4");
                	parameters.put("property1.propertyB.element[0]", "valuexx");

                	parameters.put("property1.propertyC.key[0].propertyType3", "2015-01-01");
                	parameters.put("property1.propertyC.key[0].name", "propName3");
                	parameters.put("property1.propertyC.key[0].length", "5");
                	parameters.put("property1.propertyC.key[0].decimals", "6");
                	parameters.put("property1.propertyC.element[0]", "valuexxx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test9AnyController controller = (Test9AnyController)request.getAttribute("Controller");
                	
                	Test4AnyBean bean = controller.property1;
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName1", 1, 2)));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName2", 3, 4)));

                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new DecimalProperty("propName3", 5, 6)));
                	
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
                	parameters.put("property1.propertyA.key[0].propertyType", "2015-01-02");
                	parameters.put("property1.propertyA.key[0].name", "propName1");
                	parameters.put("property1.propertyA.key[0].values.element[0]", "VALUE1");
                	parameters.put("property1.propertyA.key[0].values.element[1]", "VALUE2");
                	parameters.put("property1.propertyA.key[0].values.element[3]", "VALUEX");
                	parameters.put("property1.propertyA.element[0]", "valuex");
                	
                	parameters.put("property1.propertyB.key[0].propertyType2", "2015-01-02");
                	parameters.put("property1.propertyB.key[0].name", "propName2");
                	parameters.put("property1.propertyB.key[0].values.element[0]", "VALUE3");
                	parameters.put("property1.propertyB.key[0].values.element[1]", "VALUE4");
                	parameters.put("property1.propertyB.key[0].values.element[3]", "VALUEX");
                	parameters.put("property1.propertyB.element[0]", "valuexx");

                	parameters.put("property1.propertyC.key[0].propertyType3", "2015-01-02");
                	parameters.put("property1.propertyC.key[0].name", "propName3");
                	parameters.put("property1.propertyC.key[0].values.element[0]", "VALUE5");
                	parameters.put("property1.propertyC.key[0].values.element[1]", "VALUE6");
                	parameters.put("property1.propertyC.key[0].values.element[3]", "VALUEX");
                	parameters.put("property1.propertyC.element[0]", "valuexxx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test9AnyController controller = (Test9AnyController)request.getAttribute("Controller");
                	
                	Test4AnyBean bean = controller.property1;
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName1", new String[]{"VALUE1","VALUE2"})));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName2", new String[]{"VALUE3","VALUE4"})));

                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new SetProperty("propName3", new String[]{"VALUE5","VALUE6"})));
                	
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
	            	parameters.put("property.key[0].propertyType", "decimal");
	            	parameters.put("property.key[0].name", "propName");
	            	parameters.put("property.key[0].length", "10");
	            	parameters.put("property.key[0].decimals", "2");
	            	parameters.put("property.element[0]", "valuex");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 10, 2)));
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
	            	parameters.put("property.key[0].propertyType", "set");
	            	parameters.put("property.key[0].name", "propName");
	            	parameters.put("property.key[0].values.element[0]", "VALUE1");
	            	parameters.put("property.key[0].values.element[1]", "VALUE2");
	            	parameters.put("property.element[0]", "valuex");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
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
	            	parameters.put("property.key[0].propertyType", "DECIMAL");
	            	parameters.put("property.key[0].name", "propName");
	            	parameters.put("property.key[0].length", "10");
	            	parameters.put("property.key[0].decimals", "2");
	            	parameters.put("property.element[0]", "valuex");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 10, 2)));
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
	            	parameters.put("property.key[0].propertyType", "SET");
	            	parameters.put("property.key[0].name", "propName");
	            	parameters.put("property.key[0].values.element[0]", "VALUE1");
	            	parameters.put("property.key[0].values.element[1]", "VALUE2");
	            	parameters.put("property.element[0]", "valuex");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
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
	            	parameters.put("property.key[0].propertyType", "0");
	            	parameters.put("property.key[0].name", "propName");
	            	parameters.put("property.key[0].length", "10");
	            	parameters.put("property.key[0].decimals", "2");
	            	parameters.put("property.element[0]", "valuex");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 10, 2)));
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
	            	parameters.put("property.key[0].propertyType", "1");
	            	parameters.put("property.key[0].name", "propName");
	            	parameters.put("property.key[0].values.element[0]", "VALUE1");
	            	parameters.put("property.key[0].values.element[1]", "VALUE2");
	            	parameters.put("property.element[0]", "valuex");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
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
	            	parameters.put("property.key[0].propertyType", "2015-01-01");
	            	parameters.put("property.key[0].name", "propName");
	            	parameters.put("property.key[0].length", "10");
	            	parameters.put("property.key[0].decimals", "2");
	            	parameters.put("property.element[0]", "valuex");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 10, 2)));
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
	            	parameters.put("property.key[0].propertyType", "2015-01-02");
	            	parameters.put("property.key[0].name", "propName");
	            	parameters.put("property.key[0].values.element[0]", "VALUE1");
	            	parameters.put("property.key[0].values.element[1]", "VALUE2");
	            	parameters.put("property.element[0]", "valuex");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
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
	            	parameters.put("propertyA.key[0].propertyType", "decimal");
	            	parameters.put("propertyA.key[0].name", "propName");
	            	parameters.put("propertyA.key[0].length", "1");
	            	parameters.put("propertyA.key[0].decimals", "2");
	            	parameters.put("propertyA.element[0]", "valuex");
	            	
	            	parameters.put("propertyB.key[0].propertyType2", "decimal");
	            	parameters.put("propertyB.key[0].name", "propName");
	            	parameters.put("propertyB.key[0].length", "3");
	            	parameters.put("propertyB.key[0].decimals", "4");
	            	parameters.put("propertyB.element[0]", "valuexx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test2AnyMetaValuesDefinitionController controller = (Test2AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 1, 2)));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName", 3, 4)));
	            	
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
	            	parameters.put("propertyA.key[0].propertyType", "set");
	            	parameters.put("propertyA.key[0].name", "propName");
	            	parameters.put("propertyA.key[0].values.element[0]", "VALUE1");
	            	parameters.put("propertyA.key[0].values.element[1]", "VALUE2");
	            	parameters.put("propertyA.key[0].values.element[3]", "VALUEX");
	            	parameters.put("propertyA.element[0]", "valuex");
	            	
	            	parameters.put("propertyB.key[0].propertyType2", "set");
	            	parameters.put("propertyB.key[0].name", "propName");
	            	parameters.put("propertyB.key[0].values.element[0]", "VALUE3");
	            	parameters.put("propertyB.key[0].values.element[1]", "VALUE4");
	            	parameters.put("propertyB.key[0].values.element[3]", "VALUEXX");
	            	parameters.put("propertyB.element[0]", "valuexx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test2AnyMetaValuesDefinitionController controller = (Test2AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName", new String[]{"VALUE3","VALUE4"})));
	            	
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
	            	parameters.put("propertyA.key[0].propertyType", "DECIMAL");
	            	parameters.put("propertyA.key[0].name", "propName");
	            	parameters.put("propertyA.key[0].length", "1");
	            	parameters.put("propertyA.key[0].decimals", "2");
	            	parameters.put("propertyA.element[0]", "valuex");
	            	
	            	parameters.put("propertyB.key[0].propertyType2", "DECIMAL");
	            	parameters.put("propertyB.key[0].name", "propName");
	            	parameters.put("propertyB.key[0].length", "3");
	            	parameters.put("propertyB.key[0].decimals", "4");
	            	parameters.put("propertyB.element[0]", "valuexx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test3AnyMetaValuesDefinitionController controller = (Test3AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 1, 2)));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName", 3, 4)));
	            	
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
	            	parameters.put("propertyA.key[0].propertyType", "SET");
	            	parameters.put("propertyA.key[0].name", "propName");
	            	parameters.put("propertyA.key[0].values.element[0]", "VALUE1");
	            	parameters.put("propertyA.key[0].values.element[1]", "VALUE2");
	            	parameters.put("propertyA.key[0].values.element[3]", "VALUEX");
	            	parameters.put("propertyA.element[0]", "valuex");
	            	
	            	parameters.put("propertyB.key[0].propertyType2", "SET");
	            	parameters.put("propertyB.key[0].name", "propName");
	            	parameters.put("propertyB.key[0].values.element[0]", "VALUE3");
	            	parameters.put("propertyB.key[0].values.element[1]", "VALUE4");
	            	parameters.put("propertyB.key[0].values.element[3]", "VALUEXX");
	            	parameters.put("propertyB.element[0]", "valuexx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test3AnyMetaValuesDefinitionController controller = (Test3AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName", new String[]{"VALUE3","VALUE4"})));
	            	
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
	            	parameters.put("propertyA.key[0].propertyType", "0");
	            	parameters.put("propertyA.key[0].name", "propName");
	            	parameters.put("propertyA.key[0].length", "1");
	            	parameters.put("propertyA.key[0].decimals", "2");
	            	parameters.put("propertyA.element[0]", "valuex");
	            	
	            	parameters.put("propertyB.key[0].propertyType2", "0");
	            	parameters.put("propertyB.key[0].name", "propName");
	            	parameters.put("propertyB.key[0].length", "3");
	            	parameters.put("propertyB.key[0].decimals", "4");
	            	parameters.put("propertyB.element[0]", "valuexx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test4AnyMetaValuesDefinitionController controller = (Test4AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 1, 2)));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName", 3, 4)));
	            	
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
	            	parameters.put("propertyA.key[0].propertyType", "1");
	            	parameters.put("propertyA.key[0].name", "propName");
	            	parameters.put("propertyA.key[0].values.element[0]", "VALUE1");
	            	parameters.put("propertyA.key[0].values.element[1]", "VALUE2");
	            	parameters.put("propertyA.key[0].values.element[3]", "VALUEX");
	            	parameters.put("propertyA.element[0]", "valuex");
	            	
	            	parameters.put("propertyB.key[0].propertyType2", "1");
	            	parameters.put("propertyB.key[0].name", "propName");
	            	parameters.put("propertyB.key[0].values.element[0]", "VALUE3");
	            	parameters.put("propertyB.key[0].values.element[1]", "VALUE4");
	            	parameters.put("propertyB.key[0].values.element[3]", "VALUEXX");
	            	parameters.put("propertyB.element[0]", "valuexx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test4AnyMetaValuesDefinitionController controller = (Test4AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName", new String[]{"VALUE3","VALUE4"})));
	            	
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
	            	parameters.put("propertyA.key[0].propertyType", "2015-01-01");
	            	parameters.put("propertyA.key[0].name", "propName");
	            	parameters.put("propertyA.key[0].length", "1");
	            	parameters.put("propertyA.key[0].decimals", "2");
	            	parameters.put("propertyA.element[0]", "valuex");
	            	
	            	parameters.put("propertyB.key[0].propertyType2", "2015-01-01");
	            	parameters.put("propertyB.key[0].name", "propName");
	            	parameters.put("propertyB.key[0].length", "3");
	            	parameters.put("propertyB.key[0].decimals", "4");
	            	parameters.put("propertyB.element[0]", "valuexx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test5AnyMetaValuesDefinitionController controller = (Test5AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName", 1, 2)));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName", 3, 4)));
	            	
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
	            	parameters.put("propertyA.key[0].propertyType", "2015-01-02");
	            	parameters.put("propertyA.key[0].name", "propName");
	            	parameters.put("propertyA.key[0].values.element[0]", "VALUE1");
	            	parameters.put("propertyA.key[0].values.element[1]", "VALUE2");
	            	parameters.put("propertyA.key[0].values.element[3]", "VALUEX");
	            	parameters.put("propertyA.element[0]", "valuex");
	            	
	            	parameters.put("propertyB.key[0].propertyType2", "2015-01-02");
	            	parameters.put("propertyB.key[0].name", "propName");
	            	parameters.put("propertyB.key[0].values.element[0]", "VALUE3");
	            	parameters.put("propertyB.key[0].values.element[1]", "VALUE4");
	            	parameters.put("propertyB.key[0].values.element[3]", "VALUEXX");
	            	parameters.put("propertyB.element[0]", "valuexx");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test5AnyMetaValuesDefinitionController controller = (Test5AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName", new String[]{"VALUE3","VALUE4"})));
	            	
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
	            	parameters.put("property1.propertyA.key[0].propertyType", "decimal");
	            	parameters.put("property1.propertyA.key[0].name", "propName1");
	            	parameters.put("property1.propertyA.key[0].length", "1");
	            	parameters.put("property1.propertyA.key[0].decimals", "2");
	            	parameters.put("property1.propertyA.element[0]", "valuex");
	            	
	            	parameters.put("property1.propertyB.key[0].propertyType2", "decimal");
	            	parameters.put("property1.propertyB.key[0].name", "propName2");
	            	parameters.put("property1.propertyB.key[0].length", "3");
	            	parameters.put("property1.propertyB.key[0].decimals", "4");
	            	parameters.put("property1.propertyB.element[0]", "valuexx");
	
	            	parameters.put("property1.propertyC.key[0].propertyType3", "decimal");
	            	parameters.put("property1.propertyC.key[0].name", "propName3");
	            	parameters.put("property1.propertyC.key[0].length", "5");
	            	parameters.put("property1.propertyC.key[0].decimals", "6");
	            	parameters.put("property1.propertyC.element[0]", "valuexxx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test6AnyMetaValuesDefinitionController controller = (Test6AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test1AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName1", 1, 2)));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName2", 3, 4)));
	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new DecimalProperty("propName3", 5, 6)));
	            	
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
	            	parameters.put("property1.propertyA.key[0].propertyType", "set");
	            	parameters.put("property1.propertyA.key[0].name", "propName1");
	            	parameters.put("property1.propertyA.key[0].values.element[0]", "VALUE1");
	            	parameters.put("property1.propertyA.key[0].values.element[1]", "VALUE2");
	            	parameters.put("property1.propertyA.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyA.element[0]", "valuex");
	            	
	            	parameters.put("property1.propertyB.key[0].propertyType2", "set");
	            	parameters.put("property1.propertyB.key[0].name", "propName2");
	            	parameters.put("property1.propertyB.key[0].values.element[0]", "VALUE3");
	            	parameters.put("property1.propertyB.key[0].values.element[1]", "VALUE4");
	            	parameters.put("property1.propertyB.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyB.element[0]", "valuexx");
	
	            	parameters.put("property1.propertyC.key[0].propertyType3", "set");
	            	parameters.put("property1.propertyC.key[0].name", "propName3");
	            	parameters.put("property1.propertyC.key[0].values.element[0]", "VALUE5");
	            	parameters.put("property1.propertyC.key[0].values.element[1]", "VALUE6");
	            	parameters.put("property1.propertyC.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyC.element[0]", "valuexxx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test6AnyMetaValuesDefinitionController controller = (Test6AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test1AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName1", new String[]{"VALUE1","VALUE2"})));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName2", new String[]{"VALUE3","VALUE4"})));
	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new SetProperty("propName3", new String[]{"VALUE5","VALUE6"})));
	            	
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
	            	parameters.put("property1.propertyA.key[0].propertyType", "DECIMAL");
	            	parameters.put("property1.propertyA.key[0].name", "propName1");
	            	parameters.put("property1.propertyA.key[0].length", "1");
	            	parameters.put("property1.propertyA.key[0].decimals", "2");
	            	parameters.put("property1.propertyA.element[0]", "valuex");
	            	
	            	parameters.put("property1.propertyB.key[0].propertyType2", "DECIMAL");
	            	parameters.put("property1.propertyB.key[0].name", "propName2");
	            	parameters.put("property1.propertyB.key[0].length", "3");
	            	parameters.put("property1.propertyB.key[0].decimals", "4");
	            	parameters.put("property1.propertyB.element[0]", "valuexx");
	
	            	parameters.put("property1.propertyC.key[0].propertyType3", "DECIMAL");
	            	parameters.put("property1.propertyC.key[0].name", "propName3");
	            	parameters.put("property1.propertyC.key[0].length", "5");
	            	parameters.put("property1.propertyC.key[0].decimals", "6");
	            	parameters.put("property1.propertyC.element[0]", "valuexxx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test7AnyMetaValuesDefinitionController controller = (Test7AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test2AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName1", 1, 2)));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName2", 3, 4)));
	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new DecimalProperty("propName3", 5, 6)));
	            	
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
	            	parameters.put("property1.propertyA.key[0].propertyType", "SET");
	            	parameters.put("property1.propertyA.key[0].name", "propName1");
	            	parameters.put("property1.propertyA.key[0].values.element[0]", "VALUE1");
	            	parameters.put("property1.propertyA.key[0].values.element[1]", "VALUE2");
	            	parameters.put("property1.propertyA.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyA.element[0]", "valuex");
	            	
	            	parameters.put("property1.propertyB.key[0].propertyType2", "SET");
	            	parameters.put("property1.propertyB.key[0].name", "propName2");
	            	parameters.put("property1.propertyB.key[0].values.element[0]", "VALUE3");
	            	parameters.put("property1.propertyB.key[0].values.element[1]", "VALUE4");
	            	parameters.put("property1.propertyB.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyB.element[0]", "valuexx");
	
	            	parameters.put("property1.propertyC.key[0].propertyType3", "SET");
	            	parameters.put("property1.propertyC.key[0].name", "propName3");
	            	parameters.put("property1.propertyC.key[0].values.element[0]", "VALUE5");
	            	parameters.put("property1.propertyC.key[0].values.element[1]", "VALUE6");
	            	parameters.put("property1.propertyC.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyC.element[0]", "valuexxx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test7AnyMetaValuesDefinitionController controller = (Test7AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test2AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName1", new String[]{"VALUE1","VALUE2"})));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName2", new String[]{"VALUE3","VALUE4"})));
	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new SetProperty("propName3", new String[]{"VALUE5","VALUE6"})));
	            	
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
	            	parameters.put("property1.propertyA.key[0].propertyType", "0");
	            	parameters.put("property1.propertyA.key[0].name", "propName1");
	            	parameters.put("property1.propertyA.key[0].length", "1");
	            	parameters.put("property1.propertyA.key[0].decimals", "2");
	            	parameters.put("property1.propertyA.element[0]", "valuex");
	            	
	            	parameters.put("property1.propertyB.key[0].propertyType2", "0");
	            	parameters.put("property1.propertyB.key[0].name", "propName2");
	            	parameters.put("property1.propertyB.key[0].length", "3");
	            	parameters.put("property1.propertyB.key[0].decimals", "4");
	            	parameters.put("property1.propertyB.element[0]", "valuexx");
	
	            	parameters.put("property1.propertyC.key[0].propertyType3", "0");
	            	parameters.put("property1.propertyC.key[0].name", "propName3");
	            	parameters.put("property1.propertyC.key[0].length", "5");
	            	parameters.put("property1.propertyC.key[0].decimals", "6");
	            	parameters.put("property1.propertyC.element[0]", "valuexxx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test8AnyMetaValuesDefinitionController controller = (Test8AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test3AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName1", 1, 2)));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName2", 3, 4)));
	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new DecimalProperty("propName3", 5, 6)));
	            	
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
	            	parameters.put("property1.propertyA.key[0].propertyType", "1");
	            	parameters.put("property1.propertyA.key[0].name", "propName1");
	            	parameters.put("property1.propertyA.key[0].values.element[0]", "VALUE1");
	            	parameters.put("property1.propertyA.key[0].values.element[1]", "VALUE2");
	            	parameters.put("property1.propertyA.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyA.element[0]", "valuex");
	            	
	            	parameters.put("property1.propertyB.key[0].propertyType2", "1");
	            	parameters.put("property1.propertyB.key[0].name", "propName2");
	            	parameters.put("property1.propertyB.key[0].values.element[0]", "VALUE3");
	            	parameters.put("property1.propertyB.key[0].values.element[1]", "VALUE4");
	            	parameters.put("property1.propertyB.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyB.element[0]", "valuexx");
	
	            	parameters.put("property1.propertyC.key[0].propertyType3", "1");
	            	parameters.put("property1.propertyC.key[0].name", "propName3");
	            	parameters.put("property1.propertyC.key[0].values.element[0]", "VALUE5");
	            	parameters.put("property1.propertyC.key[0].values.element[1]", "VALUE6");
	            	parameters.put("property1.propertyC.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyC.element[0]", "valuexxx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test8AnyMetaValuesDefinitionController controller = (Test8AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test3AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName1", new String[]{"VALUE1","VALUE2"})));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName2", new String[]{"VALUE3","VALUE4"})));
	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new SetProperty("propName3", new String[]{"VALUE5","VALUE5"})));
	            	
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
	            	parameters.put("property1.propertyA.key[0].propertyType", "2015-01-01");
	            	parameters.put("property1.propertyA.key[0].name", "propName1");
	            	parameters.put("property1.propertyA.key[0].length", "1");
	            	parameters.put("property1.propertyA.key[0].decimals", "2");
	            	parameters.put("property1.propertyA.element[0]", "valuex");
	            	
	            	parameters.put("property1.propertyB.key[0].propertyType2", "2015-01-01");
	            	parameters.put("property1.propertyB.key[0].name", "propName2");
	            	parameters.put("property1.propertyB.key[0].length", "3");
	            	parameters.put("property1.propertyB.key[0].decimals", "4");
	            	parameters.put("property1.propertyB.element[0]", "valuexx");
	
	            	parameters.put("property1.propertyC.key[0].propertyType3", "2015-01-01");
	            	parameters.put("property1.propertyC.key[0].name", "propName3");
	            	parameters.put("property1.propertyC.key[0].length", "5");
	            	parameters.put("property1.propertyC.key[0].decimals", "6");
	            	parameters.put("property1.propertyC.element[0]", "valuexxx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test9AnyMetaValuesDefinitionController controller = (Test9AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test4AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new DecimalProperty("propName1", 1, 2)));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new DecimalProperty("propName2", 3, 4)));
	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new DecimalProperty("propName3", 5, 6)));
	            	
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
	            	parameters.put("property1.propertyA.key[0].propertyType", "2015-01-02");
	            	parameters.put("property1.propertyA.key[0].name", "propName1");
	            	parameters.put("property1.propertyA.key[0].values.element[0]", "VALUE1");
	            	parameters.put("property1.propertyA.key[0].values.element[1]", "VALUE2");
	            	parameters.put("property1.propertyA.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyA.element[0]", "valuex");
	            	
	            	parameters.put("property1.propertyB.key[0].propertyType2", "2015-01-02");
	            	parameters.put("property1.propertyB.key[0].name", "propName2");
	            	parameters.put("property1.propertyB.key[0].values.element[0]", "VALUE3");
	            	parameters.put("property1.propertyB.key[0].values.element[1]", "VALUE4");
	            	parameters.put("property1.propertyB.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyB.element[0]", "valuexx");
	
	            	parameters.put("property1.propertyC.key[0].propertyType3", "2015-01-02");
	            	parameters.put("property1.propertyC.key[0].name", "propName3");
	            	parameters.put("property1.propertyC.key[0].values.element[0]", "VALUE5");
	            	parameters.put("property1.propertyC.key[0].values.element[1]", "VALUE6");
	            	parameters.put("property1.propertyC.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyC.element[0]", "valuexxx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test9AnyMetaValuesDefinitionController controller = (Test9AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test4AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName1", new String[]{"VALUE1","VALUE2"})));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName2", new String[]{"VALUE3","VALUE4"})));
	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new SetProperty("propName3", new String[]{"VALUE5","VALUE6"})));
	            	
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
	            	parameters.put("property1.propertyA.key[0].propertyType", "set");
	            	parameters.put("property1.propertyA.key[0].name", "propName1");
	            	parameters.put("property1.propertyA.key[0].values.element[0]", "VALUE1");
	            	parameters.put("property1.propertyA.key[0].values.element[1]", "VALUE2");
	            	parameters.put("property1.propertyA.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyA.element[0]", "valuex");
	            	
	            	parameters.put("property1.propertyB.key[0].propertyType2", "set");
	            	parameters.put("property1.propertyB.key[0].name", "propName2");
	            	parameters.put("property1.propertyB.key[0].values.element[0]", "VALUE3");
	            	parameters.put("property1.propertyB.key[0].values.element[1]", "VALUE4");
	            	parameters.put("property1.propertyB.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyB.element[0]", "valuexx");
	
	            	parameters.put("property1.propertyC.key[0].propertyType3", "set");
	            	parameters.put("property1.propertyC.key[0].name", "propName3");
	            	parameters.put("property1.propertyC.key[0].values.element[0]", "VALUE5");
	            	parameters.put("property1.propertyC.key[0].values.element[1]", "VALUE6");
	            	parameters.put("property1.propertyC.key[0].values.element[3]", "VALUEX");
	            	parameters.put("property1.propertyC.element[0]", "valuexxx");

	            	parameters.put("propertyD.key[0].propertyType4", "set");
	            	parameters.put("propertyD.key[0].name", "propName4");
	            	parameters.put("propertyD.key[0].values.element[0]", "VALUE7");
	            	parameters.put("propertyD.key[0].values.element[1]", "VALUE8");
	            	parameters.put("propertyD.key[0].values.element[3]", "VALUEX");
	            	parameters.put("propertyD.element[0]", "valuexxxx");
	            	
	            	parameters.put("propertyE.key[0].propertyType5", "set");
	            	parameters.put("propertyE.key[0].name", "propName5");
	            	parameters.put("propertyE.key[0].values.element[0]", "VALUE9");
	            	parameters.put("propertyE.key[0].values.element[1]", "VALUE10");
	            	parameters.put("propertyE.key[0].values.element[3]", "VALUEX");
	            	parameters.put("propertyE.element[0]", "valuexxxxx");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test10AnyController controller = (Test10AnyController)request.getAttribute("Controller");
	            	
	            	Test5AnyBean bean = controller.property1;
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			bean.property1;
                	
                	assertEquals(1, list.size());
                	assertEquals("valuex", list.get(new SetProperty("propName1", new String[]{"VALUE1","VALUE2"})));
	            	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	assertEquals(1, list2.size());
                	assertEquals("valuexx", list2.get(new SetProperty("propName2", new String[]{"VALUE3","VALUE4"})));
	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	assertEquals(1, list3.size());
                	assertEquals("valuexxx", list3.get(new SetProperty("propName3", new String[]{"VALUE5","VALUE6"})));

                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list4 = 
                			controller.property2;
                	
                	assertEquals(1, list4.size());
                	assertEquals("valuexxxx", list4.get(new SetProperty("propName4", new String[]{"VALUE7","VALUE8"})));

                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list5 = 
                			controller.property3;
                	
                	assertEquals(1, list5.size());
                	assertEquals("valuexxxxx", list5.get(new SetProperty("propName5", new String[]{"VALUE9","VALUE10"})));
                	
	            }
	
	            public void checkException(Throwable e) {
	                fail(e.toString());
	            }
	        },
	        new Class[]{Test10AnyController.class});
	}    
	
}
