package org.brandao.brutos.annotation;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

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
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new DecimalProperty("propName", 10, 2)));
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
                	parameters.put("property.key[0].propertyType", "set");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0].values[0]", "VALUE1");
                	parameters.put("property.key[0].values[1]", "VALUE2");
                	parameters.put("property.element[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
                	
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
                	parameters.put("property.key[0].propertyType", "DECIMAL");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0]length", "10");
                	parameters.put("property.key[0].decimals", "2");
                	parameters.put("property.element[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new DecimalProperty("propName", 10, 2)));
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
                	parameters.put("property.key[0].propertyType", "SET");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0].values[0]", "VALUE1");
                	parameters.put("property.key[0].values[1]", "VALUE2");
                	parameters.put("property.element[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
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
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new DecimalProperty("propName", 10, 2)));
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
                	parameters.put("property.key[0].propertyType", "1");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0].values[0]", "VALUE1");
                	parameters.put("property.key[0].values[1]", "VALUE2");
                	parameters.put("property.element[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
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
                	parameters.put("property.key[0].propertyType", "2015-01-01");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0].length", "10");
                	parameters.put("property.key[0].decimals", "2");
                	parameters.put("property.key[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new DecimalProperty("propName", 10, 2)));
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
                	parameters.put("property.key[0].propertyType", "2015-01-02");
                	parameters.put("property.key[0].name", "propName");
                	parameters.put("property.key[0].values[0]", "VALUE1");
                	parameters.put("property.key[0].values[1]", "VALUE2");
                	parameters.put("property.element[0]", "valuex");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new DecimalProperty("propName", 1, 2)));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	Assert.assertEquals("valuexx", list.get(new DecimalProperty("propName", 3, 4)));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	parameters.put("propertyA.key[0].values[0]", "VALUE1");
                	parameters.put("propertyA.key[0].values[1]", "VALUE2");
                	parameters.put("propertyA.key[0].values[2]", "VALUEX");
                	parameters.put("propertyA.element[0]", "valuex");
                	
                	parameters.put("propertyB.key[0].propertyType2", "set");
                	parameters.put("propertyB.key[0].name", "propName");
                	parameters.put("propertyB.key[0].values[0]", "VALUE3");
                	parameters.put("propertyB.key[0].values[1]", "VALUE4");
                	parameters.put("propertyB.key[0].values[2]", "VALUEXX");
                	parameters.put("propertyB.element[0]", "valuexx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test2AnyController controller = (Test2AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	Assert.assertEquals("valuexx", list.get(new SetProperty("propName", new String[]{"VALUE3","VALUE4"})));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new DecimalProperty("propName", 1, 2)));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	Assert.assertEquals("valuexx", list.get(new DecimalProperty("propName", 3, 4)));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	parameters.put("propertyA.key[0].values[0]", "VALUE1");
                	parameters.put("propertyA.key[0].values[1]", "VALUE2");
                	parameters.put("propertyA.key[0].values[2]", "VALUEX");
                	parameters.put("propertyA.element[0]", "valuex");
                	
                	parameters.put("propertyB.key[0].propertyType2", "SET");
                	parameters.put("propertyB.key[0].name", "propName");
                	parameters.put("propertyB.key[0].values[0]", "VALUE3");
                	parameters.put("propertyB.key[0].values[1]", "VALUE4");
                	parameters.put("propertyB.key[0].values[2]", "VALUEXX");
                	parameters.put("propertyB.element[0]", "valuexx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test3AnyController controller = (Test3AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	Assert.assertEquals("valuexx", list.get(new SetProperty("propName", new String[]{"VALUE3","VALUE4"})));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new DecimalProperty("propName", 1, 2)));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	Assert.assertEquals("valuexx", list.get(new DecimalProperty("propName", 3, 4)));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	parameters.put("propertyA.key[0].values[0]", "VALUE1");
                	parameters.put("propertyA.key[0].values[1]", "VALUE2");
                	parameters.put("propertyA.key[0].values[2]", "VALUEX");
                	parameters.put("propertyA.element[0]", "valuex");
                	
                	parameters.put("propertyB.key[0].propertyType2", "1");
                	parameters.put("propertyB.key[0].name", "propName");
                	parameters.put("propertyB.key[0].values[0]", "VALUE3");
                	parameters.put("propertyB.key[0].values[1]", "VALUE4");
                	parameters.put("propertyB.key[0].values[2]", "VALUEXX");
                	parameters.put("propertyB.element[0]", "valuexx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test4AnyController controller = (Test4AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	Assert.assertEquals("valuexx", list.get(new SetProperty("propName", new String[]{"VALUE3","VALUE4"})));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new DecimalProperty("propName", 1, 2)));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	Assert.assertEquals("valuexx", list.get(new DecimalProperty("propName", 3, 4)));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	parameters.put("propertyA.key[0].values[0]", "VALUE1");
                	parameters.put("propertyA.key[0].values[1]", "VALUE2");
                	parameters.put("propertyA.key[0].values[2]", "VALUEX");
                	parameters.put("propertyA.element[0]", "valuex");
                	
                	parameters.put("propertyB.key[0].propertyType2", "2015-01-02");
                	parameters.put("propertyB.key[0].name", "propName");
                	parameters.put("propertyB.key[0].values[0]", "VALUE3");
                	parameters.put("propertyB.key[0].values[1]", "VALUE4");
                	parameters.put("propertyB.key[0].values[2]", "VALUEXX");
                	parameters.put("propertyB.element[0]", "valuex");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test5AnyController controller = (Test5AnyController)request.getAttribute("Controller");
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list = 
                			controller.property1;
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new SetProperty("propName", new String[]{"VALUE1","VALUE2"})));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	Assert.assertEquals("valuexx", list.get(new SetProperty("propName", new String[]{"VALUE3","VALUE4"})));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuex", list.get(new DecimalProperty("propName1", 1, 2)));
                	
                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list);
                	Assert.assertEquals("valuexx", list.get(new DecimalProperty("propName2", 3, 4)));

                	Map<org.brandao.brutos.annotation.helper.any.app3.Property,String> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list3);
                	Assert.assertEquals("valuexxx", list.get(new DecimalProperty("propName3", 5, 6)));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	parameters.put("property1.propertyA.key[0].values[0]", "VALUE1");
                	parameters.put("property1.propertyA.key[0].values[1]", "VALUE2");
                	parameters.put("property1.propertyA.key[0].values[2]", "VALUEX");
                	parameters.put("property1.propertyA.element[0]", "valuex");
                	
                	parameters.put("property1.propertyB.key[0].propertyType2", "set");
                	parameters.put("property1.propertyB.key[0].name", "propName2");
                	parameters.put("property1.propertyB.key[0].values[0]", "VALUE3");
                	parameters.put("property1.propertyB.key[0].values[1]", "VALUE4");
                	parameters.put("property1.propertyB.key[0].values[2]", "VALUEX");
                	parameters.put("property1.propertyB.element[0]", "valuexx");

                	parameters.put("property1.propertyC.key[0].propertyType2", "set");
                	parameters.put("property1.propertyC.key[0].name", "propName3");
                	parameters.put("property1.propertyC.key[0].values[0]", "VALUE5");
                	parameters.put("property1.propertyC.key[0].values[1]", "VALUE6");
                	parameters.put("property1.propertyC.key[0].values[2]", "VALUEX");
                	parameters.put("property1.propertyC.element[0]", "valuexxx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test6AnyController controller = (Test6AnyController)request.getAttribute("Controller");
                	
                	Test1AnyBean bean = controller.property1;
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	Assert.assertNull(props1);
                	Assert.assertEquals(2, props1.size());
                	Assert.assertEquals("VALUE1", props1.get(0));
                	Assert.assertEquals("VALUE2", props1.get(1));
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	Assert.assertNull(props2);
                	Assert.assertEquals(2, props2.size());
                	Assert.assertEquals("VALUE3", props2.get(0));
                	Assert.assertEquals("VALUE4", props2.get(1));                	

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);

                	Assert.assertEquals("propName3", property3.getName());
                	
                	Assert.assertTrue(property3 instanceof SetProperty);

                	List<String> props3 = ((SetProperty)property3).getValues();
                	
                	Assert.assertNull(props3);
                	Assert.assertEquals(2, props3.size());
                	Assert.assertEquals("VALUE5", props3.get(0));
                	Assert.assertEquals("VALUE6", props3.get(1));                	
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof DecimalProperty);
                	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof DecimalProperty);
                	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
                	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list3);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
                	
                	Assert.assertEquals("propName3", property3.getName());
                	
                	Assert.assertTrue(property3 instanceof DecimalProperty);
                	Assert.assertEquals(5, ((DecimalProperty)property3).getLength());
                	Assert.assertEquals(6, ((DecimalProperty)property3).getDecimals());
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	parameters.put("property1.propertyA.key[0].values[0]", "VALUE1");
                	parameters.put("property1.propertyA.key[0].values[1]", "VALUE2");
                	parameters.put("property1.propertyA.key[0].values[2]", "VALUEX");
                	parameters.put("property1.propertyA.element[0]", "valuex");
                	
                	parameters.put("property1.propertyB.key[0].propertyType2", "SET");
                	parameters.put("property1.propertyB.key[0].name", "propName2");
                	parameters.put("property1.propertyB.key[0].values[0]", "VALUE3");
                	parameters.put("property1.propertyB.key[0].values[1]", "VALUE4");
                	parameters.put("property1.propertyB.key[0].values[2]", "VALUEX");
                	parameters.put("property1.propertyB.element[0]", "valuexx");

                	parameters.put("property1.propertyC.key[0].propertyType2", "SET");
                	parameters.put("property1.propertyC.key[0].name", "propName3");
                	parameters.put("property1.propertyC.key[0].values[0]", "VALUE5");
                	parameters.put("property1.propertyC.key[0].values[1]", "VALUE6");
                	parameters.put("property1.propertyC.key[0].values[2]", "VALUEX");
                	parameters.put("property1.propertyC.element[0]", "valuexxx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test7AnyController controller = (Test7AnyController)request.getAttribute("Controller");
                	
                	Test2AnyBean bean = controller.property1;
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	Assert.assertNull(props1);
                	Assert.assertEquals(2, props1.size());
                	Assert.assertEquals("VALUE1", props1.get(0));
                	Assert.assertEquals("VALUE2", props1.get(1));
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	Assert.assertNull(props2);
                	Assert.assertEquals(2, props2.size());
                	Assert.assertEquals("VALUE3", props2.get(0));
                	Assert.assertEquals("VALUE4", props2.get(1));                	

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list3);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);

                	Assert.assertEquals("propName3", property3.getName());
                	
                	Assert.assertTrue(property3 instanceof SetProperty);

                	List<String> props3 = ((SetProperty)property3).getValues();
                	
                	Assert.assertNull(props3);
                	Assert.assertEquals(2, props3.size());
                	Assert.assertEquals("VALUE5", props3.get(0));
                	Assert.assertEquals("VALUE6", props3.get(1));                	
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof DecimalProperty);
                	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof DecimalProperty);
                	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
                	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list3);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
                	
                	Assert.assertEquals("propName3", property3.getName());
                	
                	Assert.assertTrue(property3 instanceof DecimalProperty);
                	Assert.assertEquals(5, ((DecimalProperty)property3).getLength());
                	Assert.assertEquals(6, ((DecimalProperty)property3).getDecimals());
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	parameters.put("property1.propertyA.key[0].values[0]", "VALUE1");
                	parameters.put("property1.propertyA.key[0].values[1]", "VALUE2");
                	parameters.put("property1.propertyA.key[0].values[2]", "VALUEX");
                	parameters.put("property1.propertyA.element[0]", "valuex");
                	
                	parameters.put("property1.propertyB.key[0].propertyType2", "1");
                	parameters.put("property1.propertyB.key[0].name", "propName2");
                	parameters.put("property1.propertyB.key[0].values[0]", "VALUE3");
                	parameters.put("property1.propertyB.key[0].values[1]", "VALUE4");
                	parameters.put("property1.propertyB.key[0].values[2]", "VALUEX");
                	parameters.put("property1.propertyB.element[0]", "valuexx");

                	parameters.put("property1.propertyC.key[0].propertyType2", "1");
                	parameters.put("property1.propertyC.key[0].name", "propName3");
                	parameters.put("property1.propertyC.key[0].values[0]", "VALUE5");
                	parameters.put("property1.propertyC.key[0].values[1]", "VALUE6");
                	parameters.put("property1.propertyC.key[0].values[2]", "VALUEX");
                	parameters.put("property1.propertyC.element[0]", "valuexxx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test8AnyController controller = (Test8AnyController)request.getAttribute("Controller");
                	
                	Test3AnyBean bean = controller.property1;
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	Assert.assertNull(props1);
                	Assert.assertEquals(2, props1.size());
                	Assert.assertEquals("VALUE1", props1.get(0));
                	Assert.assertEquals("VALUE2", props1.get(1));
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	Assert.assertNull(props2);
                	Assert.assertEquals(2, props2.size());
                	Assert.assertEquals("VALUE3", props2.get(0));
                	Assert.assertEquals("VALUE4", props2.get(1));                	

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list3);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);

                	Assert.assertEquals("propName3", property3.getName());
                	
                	Assert.assertTrue(property3 instanceof SetProperty);

                	List<String> props3 = ((SetProperty)property3).getValues();
                	
                	Assert.assertNull(props3);
                	Assert.assertEquals(2, props3.size());
                	Assert.assertEquals("VALUE5", props3.get(0));
                	Assert.assertEquals("VALUE6", props3.get(1));                	
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	
                	parameters.put("property1.propertyB.key[0].propertyType2", "2015-01-01");
                	parameters.put("property1.propertyB.key[0].name", "propName2");
                	parameters.put("property1.propertyB.key[0].length", "3");
                	parameters.put("property1.propertyB.key[0].decimals", "4");

                	parameters.put("property1.propertyC.key[0].propertyType3", "2015-01-01");
                	parameters.put("property1.propertyC.key[0].name", "propName3");
                	parameters.put("property1.propertyC.key[0].length", "5");
                	parameters.put("property1.propertyC.key[0].decimals", "6");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test9AnyController controller = (Test9AnyController)request.getAttribute("Controller");
                	
                	Test4AnyBean bean = controller.property1;
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof DecimalProperty);
                	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof DecimalProperty);
                	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
                	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
                	
                	Assert.assertEquals("propName3", property3.getName());
                	
                	Assert.assertTrue(property3 instanceof DecimalProperty);
                	Assert.assertEquals(5, ((DecimalProperty)property3).getLength());
                	Assert.assertEquals(6, ((DecimalProperty)property3).getDecimals());
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
                	parameters.put("property1.propertyA.key[0].values[0]", "VALUE1");
                	parameters.put("property1.propertyA.key[0].values[1]", "VALUE2");
                	parameters.put("property1.propertyA.key[0].values[2]", "VALUEX");
                	parameters.put("property1.propertyA.element[0]", "valuex");
                	
                	parameters.put("property1.propertyB.key[0].propertyType2", "2015-01-02");
                	parameters.put("property1.propertyB.key[0].name", "propName2");
                	parameters.put("property1.propertyB.key[0].values[0]", "VALUE3");
                	parameters.put("property1.propertyB.key[0].values[1]", "VALUE4");
                	parameters.put("property1.propertyB.key[0].values[2]", "VALUEX");
                	parameters.put("property1.propertyB.element[0]", "valuexx");

                	parameters.put("property1.propertyC.key[0].propertyType2", "2015-01-02");
                	parameters.put("property1.propertyC.key[0].name", "propName3");
                	parameters.put("property1.propertyC.key[0].values[0]", "VALUE5");
                	parameters.put("property1.propertyC.key[0].values[1]", "VALUE6");
                	parameters.put("property1.propertyC.key[0].values[2]", "VALUEX");
                	parameters.put("property1.propertyC.element[0]", "valuexxx");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test9AnyController controller = (Test9AnyController)request.getAttribute("Controller");
                	
                	Test4AnyBean bean = controller.property1;
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	Assert.assertNull(props1);
                	Assert.assertEquals(2, props1.size());
                	Assert.assertEquals("VALUE1", props1.get(0));
                	Assert.assertEquals("VALUE2", props1.get(1));
                	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	Assert.assertNull(props2);
                	Assert.assertEquals(2, props2.size());
                	Assert.assertEquals("VALUE3", props2.get(0));
                	Assert.assertEquals("VALUE4", props2.get(1));                	

                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);

                	Assert.assertEquals("propName3", property3.getName());
                	
                	Assert.assertTrue(property3 instanceof SetProperty);

                	List<String> props3 = ((SetProperty)property3).getValues();
                	
                	Assert.assertNull(props3);
                	Assert.assertEquals(2, props3.size());
                	Assert.assertEquals("VALUE5", props3.get(0));
                	Assert.assertEquals("VALUE6", props3.get(1));                	
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
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
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
	            	Assert.assertEquals("propName", property.getName());
	            	
	            	Assert.assertTrue(property instanceof DecimalProperty);
	            	Assert.assertEquals(10, ((DecimalProperty)property).getLength());
	            	Assert.assertEquals(2, ((DecimalProperty)property).getDecimals());
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	parameters.put("property.key[0].values[0]", "VALUE1");
	            	parameters.put("property.key[0].values[1]", "VALUE2");
	            	parameters.put("property.element[0]", "valuex");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
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
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
	            	Assert.assertEquals("propName", property.getName());
	            	
	            	Assert.assertTrue(property instanceof DecimalProperty);
	            	Assert.assertEquals(10, ((DecimalProperty)property).getLength());
	            	Assert.assertEquals(2, ((DecimalProperty)property).getDecimals());
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	parameters.put("property.key[0].values[0]", "VALUE1");
	            	parameters.put("property.key[0].values[1]", "VALUE2");
	            	parameters.put("property.element[0]", "valuex");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
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
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
	            	Assert.assertEquals("propName", property.getName());
	            	
	            	Assert.assertTrue(property instanceof DecimalProperty);
	            	Assert.assertEquals(10, ((DecimalProperty)property).getLength());
	            	Assert.assertEquals(2, ((DecimalProperty)property).getDecimals());
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	parameters.put("property.key[0].values[0]", "VALUE1");
	            	parameters.put("property.key[0].values[1]", "VALUE2");
	            	parameters.put("property.element[0]", "valuex");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
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
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
	            	Assert.assertEquals("propName", property.getName());
	            	
	            	Assert.assertTrue(property instanceof DecimalProperty);
	            	Assert.assertEquals(10, ((DecimalProperty)property).getLength());
	            	Assert.assertEquals(2, ((DecimalProperty)property).getDecimals());
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	parameters.put("property.key[0].values[0]", "VALUE1");
	            	parameters.put("property.key[0].values[1]", "VALUE2");
	            	parameters.put("property.element[0]", "valuex");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test1AnyMetaValuesDefinitionController controller = (Test1AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.getProperty();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property = list.get(0);
	            	
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
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof DecimalProperty);
	            	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
	            	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof DecimalProperty);
	            	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
	            	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	parameters.put("propertyA.key[0].values[0]", "VALUE1");
	            	parameters.put("propertyA.key[0].values[1]", "VALUE2");
	            	parameters.put("propertyA.key[0].values[2]", "VALUEX");
	            	parameters.put("propertyA.element[0]", "valuex");
	            	
	            	parameters.put("propertyB.key[0].propertyType2", "set");
	            	parameters.put("propertyB.key[0].name", "propName");
	            	parameters.put("propertyB.key[0].values[0]", "VALUE3");
	            	parameters.put("propertyB.key[0].values[1]", "VALUE4");
	            	parameters.put("propertyB.key[0].values[2]", "VALUEXX");
	            	parameters.put("propertyB.element[0]", "valuexx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test2AnyMetaValuesDefinitionController controller = (Test2AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	Assert.assertNull(props1);
	            	Assert.assertEquals(2, props1.size());
	            	Assert.assertEquals("VALUE1", props1.get(0));
	            	Assert.assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof DecimalProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	Assert.assertNull(props2);
	            	Assert.assertEquals(2, props2.size());
	            	Assert.assertEquals("VALUE3", props2.get(0));
	            	Assert.assertEquals("VALUE4", props2.get(1));
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof DecimalProperty);
	            	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
	            	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof DecimalProperty);
	            	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
	            	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	parameters.put("propertyA.key[0].values[0]", "VALUE1");
	            	parameters.put("propertyA.key[0].values[1]", "VALUE2");
	            	parameters.put("propertyA.key[0].values[2]", "VALUEX");
	            	parameters.put("propertyA.element[0]", "valuex");
	            	
	            	parameters.put("propertyB.key[0].propertyType2", "SET");
	            	parameters.put("propertyB.key[0].name", "propName");
	            	parameters.put("propertyB.key[0].values[0]", "VALUE3");
	            	parameters.put("propertyB.key[0].values[1]", "VALUE4");
	            	parameters.put("propertyB.key[0].values[2]", "VALUEXX");
	            	parameters.put("propertyB.element[0]", "valuexx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test3AnyMetaValuesDefinitionController controller = (Test3AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	Assert.assertNull(props1);
	            	Assert.assertEquals(2, props1.size());
	            	Assert.assertEquals("VALUE1", props1.get(0));
	            	Assert.assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof DecimalProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	Assert.assertNull(props2);
	            	Assert.assertEquals(2, props2.size());
	            	Assert.assertEquals("VALUE3", props2.get(0));
	            	Assert.assertEquals("VALUE4", props2.get(1));
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof DecimalProperty);
	            	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
	            	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof DecimalProperty);
	            	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
	            	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	parameters.put("propertyA.key[0].values[0]", "VALUE1");
	            	parameters.put("propertyA.key[0].values[1]", "VALUE2");
	            	parameters.put("propertyA.key[0].values[2]", "VALUEX");
	            	parameters.put("propertyA.element[0]", "valuex");
	            	
	            	parameters.put("propertyB.key[0].propertyType2", "1");
	            	parameters.put("propertyB.key[0].name", "propName");
	            	parameters.put("propertyB.key[0].values[0]", "VALUE3");
	            	parameters.put("propertyB.key[0].values[1]", "VALUE4");
	            	parameters.put("propertyB.key[0].values[2]", "VALUEXX");
	            	parameters.put("propertyB.element[0]", "valuexx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test4AnyMetaValuesDefinitionController controller = (Test4AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	Assert.assertNull(props1);
	            	Assert.assertEquals(2, props1.size());
	            	Assert.assertEquals("VALUE1", props1.get(0));
	            	Assert.assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof DecimalProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	Assert.assertNull(props2);
	            	Assert.assertEquals(2, props2.size());
	            	Assert.assertEquals("VALUE3", props2.get(0));
	            	Assert.assertEquals("VALUE4", props2.get(1));
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof DecimalProperty);
	            	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
	            	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof DecimalProperty);
	            	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
	            	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	parameters.put("propertyA.key[0].values[0]", "VALUE1");
	            	parameters.put("propertyA.key[0].values[1]", "VALUE2");
	            	parameters.put("propertyA.key[0].values[2]", "VALUEX");
	            	parameters.put("propertyA.element[0]", "valuex");
	            	
	            	parameters.put("propertyB.key[0].propertyType2", "2015-01-02");
	            	parameters.put("propertyB.key[0].name", "propName");
	            	parameters.put("propertyB.key[0].values[0]", "VALUE3");
	            	parameters.put("propertyB.key[0].values[1]", "VALUE4");
	            	parameters.put("propertyB.key[0].values[2]", "VALUEXX");
	            	parameters.put("propertyB.element[0]", "valuexx");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	            	
	            	Test5AnyMetaValuesDefinitionController controller = (Test5AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			controller.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	Assert.assertNull(props1);
	            	Assert.assertEquals(2, props1.size());
	            	Assert.assertEquals("VALUE1", props1.get(0));
	            	Assert.assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			controller.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof DecimalProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	Assert.assertNull(props2);
	            	Assert.assertEquals(2, props2.size());
	            	Assert.assertEquals("VALUE3", props2.get(0));
	            	Assert.assertEquals("VALUE4", props2.get(1));
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName1", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof DecimalProperty);
	            	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
	            	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName2", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof DecimalProperty);
	            	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
	            	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	            	
	            	Assert.assertEquals("propName3", property3.getName());
	            	
	            	Assert.assertTrue(property3 instanceof DecimalProperty);
	            	Assert.assertEquals(5, ((DecimalProperty)property3).getLength());
	            	Assert.assertEquals(6, ((DecimalProperty)property3).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	parameters.put("property1.propertyA.key[0].values[0]", "VALUE1");
	            	parameters.put("property1.propertyA.key[0].values[1]", "VALUE2");
	            	parameters.put("property1.propertyA.key[0].values[2]", "VALUEX");
	            	parameters.put("property1.propertyA.element[0]", "valuex");
	            	
	            	parameters.put("property1.propertyB.key[0].propertyType2", "set");
	            	parameters.put("property1.propertyB.key[0].name", "propName2");
	            	parameters.put("property1.propertyB.key[0].values[0]", "VALUE3");
	            	parameters.put("property1.propertyB.key[0].values[1]", "VALUE4");
	            	parameters.put("property1.propertyB.key[0].values[2]", "VALUEX");
	            	parameters.put("property1.propertyB.element[0]", "valuexx");
	
	            	parameters.put("property1.propertyC.key[0].propertyType2", "set");
	            	parameters.put("property1.propertyC.key[0].name", "propName3");
	            	parameters.put("property1.propertyC.key[0].values[0]", "VALUE5");
	            	parameters.put("property1.propertyC.key[0].values[1]", "VALUE6");
	            	parameters.put("property1.propertyC.key[0].values[2]", "VALUEX");
	            	parameters.put("property1.propertyC.element[0]", "valuexxx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test6AnyMetaValuesDefinitionController controller = (Test6AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test1AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName1", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	Assert.assertNull(props1);
	            	Assert.assertEquals(2, props1.size());
	            	Assert.assertEquals("VALUE1", props1.get(0));
	            	Assert.assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName2", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	Assert.assertNull(props2);
	            	Assert.assertEquals(2, props2.size());
	            	Assert.assertEquals("VALUE3", props2.get(0));
	            	Assert.assertEquals("VALUE4", props2.get(1));                	
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	
	            	Assert.assertEquals("propName3", property3.getName());
	            	
	            	Assert.assertTrue(property3 instanceof SetProperty);
	
	            	List<String> props3 = ((SetProperty)property3).getValues();
	            	
	            	Assert.assertNull(props3);
	            	Assert.assertEquals(2, props3.size());
	            	Assert.assertEquals("VALUE5", props3.get(0));
	            	Assert.assertEquals("VALUE6", props3.get(1));                	
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName1", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof DecimalProperty);
	            	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
	            	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName2", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof DecimalProperty);
	            	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
	            	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	            	
	            	Assert.assertEquals("propName3", property3.getName());
	            	
	            	Assert.assertTrue(property3 instanceof DecimalProperty);
	            	Assert.assertEquals(5, ((DecimalProperty)property3).getLength());
	            	Assert.assertEquals(6, ((DecimalProperty)property3).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	parameters.put("property1.propertyA.key[0].values[0]", "VALUE1");
	            	parameters.put("property1.propertyA.key[0].values[1]", "VALUE2");
	            	parameters.put("property1.propertyA.key[0].values[2]", "VALUEX");
	            	parameters.put("property1.propertyA.element[0]", "valuex");
	            	
	            	parameters.put("property1.propertyB.key[0].propertyType2", "SET");
	            	parameters.put("property1.propertyB.key[0].name", "propName2");
	            	parameters.put("property1.propertyB.key[0].values[0]", "VALUE3");
	            	parameters.put("property1.propertyB.key[0].values[1]", "VALUE4");
	            	parameters.put("property1.propertyB.key[0].values[2]", "VALUEX");
	            	parameters.put("property1.propertyB.element[0]", "valuexx");
	
	            	parameters.put("property1.propertyC.key[0].propertyType2", "SET");
	            	parameters.put("property1.propertyC.key[0].name", "propName3");
	            	parameters.put("property1.propertyC.key[0].values[0]", "VALUE5");
	            	parameters.put("property1.propertyC.key[0].values[1]", "VALUE6");
	            	parameters.put("property1.propertyC.key[0].values[2]", "VALUEX");
	            	parameters.put("property1.propertyC.element[0]", "valuexxx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test7AnyMetaValuesDefinitionController controller = (Test7AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test2AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName1", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	Assert.assertNull(props1);
	            	Assert.assertEquals(2, props1.size());
	            	Assert.assertEquals("VALUE1", props1.get(0));
	            	Assert.assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName2", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	Assert.assertNull(props2);
	            	Assert.assertEquals(2, props2.size());
	            	Assert.assertEquals("VALUE3", props2.get(0));
	            	Assert.assertEquals("VALUE4", props2.get(1));                	
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	
	            	Assert.assertEquals("propName3", property3.getName());
	            	
	            	Assert.assertTrue(property3 instanceof SetProperty);
	
	            	List<String> props3 = ((SetProperty)property3).getValues();
	            	
	            	Assert.assertNull(props3);
	            	Assert.assertEquals(2, props3.size());
	            	Assert.assertEquals("VALUE5", props3.get(0));
	            	Assert.assertEquals("VALUE6", props3.get(1));                	
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName1", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof DecimalProperty);
	            	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
	            	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName2", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof DecimalProperty);
	            	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
	            	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	            	
	            	Assert.assertEquals("propName3", property3.getName());
	            	
	            	Assert.assertTrue(property3 instanceof DecimalProperty);
	            	Assert.assertEquals(5, ((DecimalProperty)property3).getLength());
	            	Assert.assertEquals(6, ((DecimalProperty)property3).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	parameters.put("property1.propertyA.key[0].values[0]", "VALUE1");
	            	parameters.put("property1.propertyA.key[0].values[1]", "VALUE2");
	            	parameters.put("property1.propertyA.key[0].values[2]", "VALUEX");
	            	parameters.put("property1.propertyA.element[0]", "valuex");
	            	
	            	parameters.put("property1.propertyB.key[0].propertyType2", "1");
	            	parameters.put("property1.propertyB.key[0].name", "propName2");
	            	parameters.put("property1.propertyB.key[0].values[0]", "VALUE3");
	            	parameters.put("property1.propertyB.key[0].values[1]", "VALUE4");
	            	parameters.put("property1.propertyB.key[0].values[2]", "VALUEX");
	            	parameters.put("property1.propertyB.element[0]", "valuexx");
	
	            	parameters.put("property1.propertyC.key[0].propertyType2", "1");
	            	parameters.put("property1.propertyC.key[0].name", "propName3");
	            	parameters.put("property1.propertyC.key[0].values[0]", "VALUE5");
	            	parameters.put("property1.propertyC.key[0].values[1]", "VALUE6");
	            	parameters.put("property1.propertyC.key[0].values[2]", "VALUEX");
	            	parameters.put("property1.propertyC.element[0]", "valuexxx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test8AnyMetaValuesDefinitionController controller = (Test8AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test3AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName1", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	Assert.assertNull(props1);
	            	Assert.assertEquals(2, props1.size());
	            	Assert.assertEquals("VALUE1", props1.get(0));
	            	Assert.assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list2);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName2", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	Assert.assertNull(props2);
	            	Assert.assertEquals(2, props2.size());
	            	Assert.assertEquals("VALUE3", props2.get(0));
	            	Assert.assertEquals("VALUE4", props2.get(1));                	
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	
	            	Assert.assertEquals("propName3", property3.getName());
	            	
	            	Assert.assertTrue(property3 instanceof SetProperty);
	
	            	List<String> props3 = ((SetProperty)property3).getValues();
	            	
	            	Assert.assertNull(props3);
	            	Assert.assertEquals(2, props3.size());
	            	Assert.assertEquals("VALUE5", props3.get(0));
	            	Assert.assertEquals("VALUE6", props3.get(1));                	
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName1", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof DecimalProperty);
	            	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
	            	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName2", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof DecimalProperty);
	            	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
	            	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	            	
	            	Assert.assertEquals("propName3", property3.getName());
	            	
	            	Assert.assertTrue(property3 instanceof DecimalProperty);
	            	Assert.assertEquals(5, ((DecimalProperty)property3).getLength());
	            	Assert.assertEquals(6, ((DecimalProperty)property3).getDecimals());
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
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
	            	parameters.put("property1.propertyA.key[0].values[0]", "VALUE1");
	            	parameters.put("property1.propertyA.key[0].values[1]", "VALUE2");
	            	parameters.put("property1.propertyA.key[0].values[2]", "VALUEX");
	            	parameters.put("property1.propertyA.element[0]", "valuex");
	            	
	            	parameters.put("property1.propertyB.key[0].propertyType2", "2015-01-02");
	            	parameters.put("property1.propertyB.key[0].name", "propName2");
	            	parameters.put("property1.propertyB.key[0].values[0]", "VALUE3");
	            	parameters.put("property1.propertyB.key[0].values[1]", "VALUE4");
	            	parameters.put("property1.propertyB.key[0].values[2]", "VALUEX");
	            	parameters.put("property1.propertyB.element[0]", "valuexx");
	
	            	parameters.put("property1.propertyC.key[0].propertyType2", "2015-01-02");
	            	parameters.put("property1.propertyC.key[0].name", "propName3");
	            	parameters.put("property1.propertyC.key[0].values[0]", "VALUE5");
	            	parameters.put("property1.propertyC.key[0].values[1]", "VALUE6");
	            	parameters.put("property1.propertyC.key[0].values[2]", "VALUEX");
	            	parameters.put("property1.propertyC.element[0]", "valuexxx");
	            	
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test9AnyMetaValuesDefinitionController controller = (Test9AnyMetaValuesDefinitionController)request.getAttribute("Controller");
	            	
	            	Test4AnyBeanMetaValuesDefinition bean = controller.property1;
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list = 
                			bean.property1;
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property1 = list.get(0);
	
	            	Assert.assertEquals("propName1", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	Assert.assertNull(props1);
	            	Assert.assertEquals(2, props1.size());
	            	Assert.assertEquals("VALUE1", props1.get(0));
	            	Assert.assertEquals("VALUE2", props1.get(1));
	            	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list2 = 
                			bean.getProperty2();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property2 = list2.get(0);
	
	            	Assert.assertEquals("propName2", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	Assert.assertNull(props2);
	            	Assert.assertEquals(2, props2.size());
	            	Assert.assertEquals("VALUE3", props2.get(0));
	            	Assert.assertEquals("VALUE4", props2.get(1));                	
	
                	List<org.brandao.brutos.annotation.helper.any.app2.Property> list3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals(1, list);
                	
                	org.brandao.brutos.annotation.helper.any.app2.Property property3 = list3.get(0);
	
	            	Assert.assertEquals("propName3", property3.getName());
	            	
	            	Assert.assertTrue(property3 instanceof SetProperty);
	
	            	List<String> props3 = ((SetProperty)property3).getValues();
	            	
	            	Assert.assertNull(props3);
	            	Assert.assertEquals(2, props3.size());
	            	Assert.assertEquals("VALUE5", props3.get(0));
	            	Assert.assertEquals("VALUE6", props3.get(1));                	
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
	            }
	        },
	        new Class[]{Test9AnyMetaValuesDefinitionController.class});
	}    

}
