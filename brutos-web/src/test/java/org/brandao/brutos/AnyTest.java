package org.brandao.brutos;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.helper.any.app1.DecimalProperty;
import org.brandao.brutos.helper.any.app1.SetProperty;
import org.brandao.brutos.helper.any.app1.Test10AnyController;
import org.brandao.brutos.helper.any.app1.Test1AnyBean;
import org.brandao.brutos.helper.any.app1.Test1AnyController;
import org.brandao.brutos.helper.any.app1.Test2AnyBean;
import org.brandao.brutos.helper.any.app1.Test2AnyController;
import org.brandao.brutos.helper.any.app1.Test3AnyBean;
import org.brandao.brutos.helper.any.app1.Test3AnyController;
import org.brandao.brutos.helper.any.app1.Test4AnyBean;
import org.brandao.brutos.helper.any.app1.Test4AnyController;
import org.brandao.brutos.helper.any.app1.Test5AnyBean;
import org.brandao.brutos.helper.any.app1.Test5AnyController;
import org.brandao.brutos.helper.any.app1.Test6AnyController;
import org.brandao.brutos.helper.any.app1.Test7AnyController;
import org.brandao.brutos.helper.any.app1.Test8AnyController;
import org.brandao.brutos.helper.any.app1.Test9AnyController;
/*
import org.brandao.brutos.helper.any.metavaluesdefinition.Test1AnyBeanMetaValuesDefinition;
import org.brandao.brutos.helper.any.metavaluesdefinition.Test1AnyMetaValuesDefinitionController;
import org.brandao.brutos.helper.any.metavaluesdefinition.Test2AnyBeanMetaValuesDefinition;
import org.brandao.brutos.helper.any.metavaluesdefinition.Test2AnyMetaValuesDefinitionController;
import org.brandao.brutos.helper.any.metavaluesdefinition.Test3AnyBeanMetaValuesDefinition;
import org.brandao.brutos.helper.any.metavaluesdefinition.Test3AnyMetaValuesDefinitionController;
import org.brandao.brutos.helper.any.metavaluesdefinition.Test4AnyBeanMetaValuesDefinition;
import org.brandao.brutos.helper.any.metavaluesdefinition.Test4AnyMetaValuesDefinitionController;
import org.brandao.brutos.helper.any.metavaluesdefinition.Test5AnyMetaValuesDefinitionController;
import org.brandao.brutos.helper.any.metavaluesdefinition.Test6AnyMetaValuesDefinitionController;
import org.brandao.brutos.helper.any.metavaluesdefinition.Test7AnyMetaValuesDefinitionController;
import org.brandao.brutos.helper.any.metavaluesdefinition.Test8AnyMetaValuesDefinitionController;
import org.brandao.brutos.helper.any.metavaluesdefinition.Test9AnyMetaValuesDefinitionController;
*/
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.MockXMLWebApplicationContext;
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test1AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property.propertyType", "decimal");
                	parameters.put("property.name", "propName");
                	parameters.put("property.length", "10");
                	parameters.put("property.decimals", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property = 
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test1AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property.propertyType", "set");
                	parameters.put("property.name", "propName");
                	parameters.put("property.values.element[0]", "VALUE1");
                	parameters.put("property.values.element[1]", "VALUE2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property = 
                			controller.getProperty();
                	
                	Assert.assertTrue(property instanceof SetProperty);
                	Assert.assertEquals("propName", property.getName());
                	
                	List<String> props = ((SetProperty)property).getValues();
                	
                	Assert.assertNotNull(props);
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test1AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property.propertyType", "DECIMAL");
                	parameters.put("property.name", "propName");
                	parameters.put("property.length", "10");
                	parameters.put("property.decimals", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property = 
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test1AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property.propertyType", "SET");
                	parameters.put("property.name", "propName");
                	parameters.put("property.values.element[0]", "VALUE1");
                	parameters.put("property.values.element[1]", "VALUE2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property = 
                			controller.getProperty();
                	
                	Assert.assertTrue(property instanceof SetProperty);
                	Assert.assertEquals("propName", property.getName());
                	
                	List<String> props = ((SetProperty)property).getValues();
                	
                	Assert.assertNotNull(props);
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test1AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property.propertyType", "0");
                	parameters.put("property.name", "propName");
                	parameters.put("property.length", "10");
                	parameters.put("property.decimals", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property = 
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test1AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property.propertyType", "1");
                	parameters.put("property.name", "propName");
                	parameters.put("property.values.element[0]", "VALUE1");
                	parameters.put("property.values.element[1]", "VALUE2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property = 
                			controller.getProperty();
                	
                	Assert.assertTrue(property instanceof SetProperty);
                	Assert.assertEquals("propName", property.getName());
                	
                	List<String> props = ((SetProperty)property).getValues();
                	
                	Assert.assertNotNull(props);
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test1AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property.propertyType", "2015-01-01");
                	parameters.put("property.name", "propName");
                	parameters.put("property.length", "10");
                	parameters.put("property.decimals", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property = 
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test1AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property.propertyType", "2015-01-02");
                	parameters.put("property.name", "propName");
                	parameters.put("property.values.element[0]", "VALUE1");
                	parameters.put("property.values.element[1]", "VALUE2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1AnyController controller = (Test1AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property = 
                			controller.getProperty();
                	
                	Assert.assertTrue(property instanceof SetProperty);
                	Assert.assertEquals("propName", property.getName());
                	
                	List<String> props = ((SetProperty)property).getValues();
                	
                	Assert.assertNotNull(props);
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

    public void testTest2AnyController_decimal() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
            new WebApplicationTester() {

                public void prepareContext(Map<String, String> parameters) {
                    parameters.put(
                            ContextLoader.CONTEXT_CLASS,
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test2AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("propertyA.propertyType", "decimal");
                	parameters.put("propertyA.name", "propName");
                	parameters.put("propertyA.length", "1");
                	parameters.put("propertyA.decimals", "2");
                	
                	parameters.put("propertyB.propertyType2", "decimal");
                	parameters.put("propertyB.name", "propName");
                	parameters.put("propertyB.length", "3");
                	parameters.put("propertyB.decimals", "4");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test2AnyController controller = (Test2AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			controller.property1;

                	Assert.assertEquals("propName", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof DecimalProperty);
                	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			controller.getProperty2();

                	Assert.assertEquals("propName", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof DecimalProperty);
                	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
                	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());
                	
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test2AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("propertyA.propertyType", "set");
                	parameters.put("propertyA.name", "propName");
                	parameters.put("propertyA.values.element[0]", "VALUE1");
                	parameters.put("propertyA.values.element[1]", "VALUE2");
                	parameters.put("propertyA.values.element[3]", "VALUEX");
                	
                	parameters.put("propertyB.propertyType2", "set");
                	parameters.put("propertyB.name", "propName");
                	parameters.put("propertyB.values.element[0]", "VALUE3");
                	parameters.put("propertyB.values.element[1]", "VALUE4");
                	parameters.put("propertyB.values.element[3]", "VALUEXX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test2AnyController controller = (Test2AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			controller.property1;

                	Assert.assertEquals("propName", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	Assert.assertNotNull(props1);
                	Assert.assertEquals(2, props1.size());
                	Assert.assertEquals("VALUE1", props1.get(0));
                	Assert.assertEquals("VALUE2", props1.get(1));
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			controller.getProperty2();

                	Assert.assertEquals("propName", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	Assert.assertNotNull(props2);
                	Assert.assertEquals(2, props2.size());
                	Assert.assertEquals("VALUE3", props2.get(0));
                	Assert.assertEquals("VALUE4", props2.get(1));
                	
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test3AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("propertyA.propertyType", "DECIMAL");
                	parameters.put("propertyA.name", "propName");
                	parameters.put("propertyA.length", "1");
                	parameters.put("propertyA.decimals", "2");
                	
                	parameters.put("propertyB.propertyType2", "DECIMAL");
                	parameters.put("propertyB.name", "propName");
                	parameters.put("propertyB.length", "3");
                	parameters.put("propertyB.decimals", "4");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test3AnyController controller = (Test3AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			controller.property1;

                	Assert.assertEquals("propName", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof DecimalProperty);
                	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			controller.getProperty2();

                	Assert.assertEquals("propName", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof DecimalProperty);
                	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
                	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());
                	
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test3AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("propertyA.propertyType", "SET");
                	parameters.put("propertyA.name", "propName");
                	parameters.put("propertyA.values.element[0]", "VALUE1");
                	parameters.put("propertyA.values.element[1]", "VALUE2");
                	parameters.put("propertyA.values.element[3]", "VALUEX");
                	
                	parameters.put("propertyB.propertyType2", "SET");
                	parameters.put("propertyB.name", "propName");
                	parameters.put("propertyB.values.element[0]", "VALUE3");
                	parameters.put("propertyB.values.element[1]", "VALUE4");
                	parameters.put("propertyB.values.element[3]", "VALUEXX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test3AnyController controller = (Test3AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			controller.property1;

                	Assert.assertEquals("propName", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	Assert.assertNotNull(props1);
                	Assert.assertEquals(2, props1.size());
                	Assert.assertEquals("VALUE1", props1.get(0));
                	Assert.assertEquals("VALUE2", props1.get(1));
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			controller.getProperty2();

                	Assert.assertEquals("propName", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	Assert.assertNotNull(props2);
                	Assert.assertEquals(2, props2.size());
                	Assert.assertEquals("VALUE3", props2.get(0));
                	Assert.assertEquals("VALUE4", props2.get(1));
                	
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test4AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("propertyA.propertyType", "0");
                	parameters.put("propertyA.name", "propName");
                	parameters.put("propertyA.length", "1");
                	parameters.put("propertyA.decimals", "2");
                	
                	parameters.put("propertyB.propertyType2", "0");
                	parameters.put("propertyB.name", "propName");
                	parameters.put("propertyB.length", "3");
                	parameters.put("propertyB.decimals", "4");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test4AnyController controller = (Test4AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			controller.property1;

                	Assert.assertEquals("propName", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof DecimalProperty);
                	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			controller.getProperty2();

                	Assert.assertEquals("propName", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof DecimalProperty);
                	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
                	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());
                	
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test4AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("propertyA.propertyType", "1");
                	parameters.put("propertyA.name", "propName");
                	parameters.put("propertyA.values.element[0]", "VALUE1");
                	parameters.put("propertyA.values.element[1]", "VALUE2");
                	parameters.put("propertyA.values.element[3]", "VALUEX");
                	
                	parameters.put("propertyB.propertyType2", "1");
                	parameters.put("propertyB.name", "propName");
                	parameters.put("propertyB.values.element[0]", "VALUE3");
                	parameters.put("propertyB.values.element[1]", "VALUE4");
                	parameters.put("propertyB.values.element[3]", "VALUEXX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test4AnyController controller = (Test4AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			controller.property1;

                	Assert.assertEquals("propName", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	Assert.assertNotNull(props1);
                	Assert.assertEquals(2, props1.size());
                	Assert.assertEquals("VALUE1", props1.get(0));
                	Assert.assertEquals("VALUE2", props1.get(1));
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			controller.getProperty2();

                	Assert.assertEquals("propName", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	Assert.assertNotNull(props2);
                	Assert.assertEquals(2, props2.size());
                	Assert.assertEquals("VALUE3", props2.get(0));
                	Assert.assertEquals("VALUE4", props2.get(1));
                	
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test5AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("propertyA.propertyType", "2015-01-01");
                	parameters.put("propertyA.name", "propName");
                	parameters.put("propertyA.length", "1");
                	parameters.put("propertyA.decimals", "2");
                	
                	parameters.put("propertyB.propertyType2", "2015-01-01");
                	parameters.put("propertyB.name", "propName");
                	parameters.put("propertyB.length", "3");
                	parameters.put("propertyB.decimals", "4");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test5AnyController controller = (Test5AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			controller.property1;

                	Assert.assertEquals("propName", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof DecimalProperty);
                	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			controller.getProperty2();

                	Assert.assertEquals("propName", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof DecimalProperty);
                	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
                	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());
                	
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test5AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("propertyA.propertyType", "2015-01-02");
                	parameters.put("propertyA.name", "propName");
                	parameters.put("propertyA.values.element[0]", "VALUE1");
                	parameters.put("propertyA.values.element[1]", "VALUE2");
                	parameters.put("propertyA.values.element[3]", "VALUEX");
                	
                	parameters.put("propertyB.propertyType2", "2015-01-02");
                	parameters.put("propertyB.name", "propName");
                	parameters.put("propertyB.values.element[0]", "VALUE3");
                	parameters.put("propertyB.values.element[1]", "VALUE4");
                	parameters.put("propertyB.values.element[3]", "VALUEXX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test5AnyController controller = (Test5AnyController)request.getAttribute("Controller");
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			controller.property1;

                	Assert.assertEquals("propName", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	Assert.assertNotNull(props1);
                	Assert.assertEquals(2, props1.size());
                	Assert.assertEquals("VALUE1", props1.get(0));
                	Assert.assertEquals("VALUE2", props1.get(1));
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			controller.getProperty2();

                	Assert.assertEquals("propName", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	Assert.assertNotNull(props2);
                	Assert.assertEquals(2, props2.size());
                	Assert.assertEquals("VALUE3", props2.get(0));
                	Assert.assertEquals("VALUE4", props2.get(1));
                	
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test6AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property1.propertyA.propertyType", "decimal");
                	parameters.put("property1.propertyA.name", "propName1");
                	parameters.put("property1.propertyA.length", "1");
                	parameters.put("property1.propertyA.decimals", "2");
                	
                	parameters.put("property1.propertyB.propertyType2", "decimal");
                	parameters.put("property1.propertyB.name", "propName2");
                	parameters.put("property1.propertyB.length", "3");
                	parameters.put("property1.propertyB.decimals", "4");

                	parameters.put("property1.propertyC.propertyType3", "decimal");
                	parameters.put("property1.propertyC.name", "propName3");
                	parameters.put("property1.propertyC.length", "5");
                	parameters.put("property1.propertyC.decimals", "6");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test6AnyController controller = (Test6AnyController)request.getAttribute("Controller");
                	
                	Test1AnyBean bean = controller.property1;
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			bean.property1;

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof DecimalProperty);
                	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			bean.getProperty2();

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof DecimalProperty);
                	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
                	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());

                	org.brandao.brutos.helper.any.app1.Property property3 = 
                			bean.getProperty3();
                	
                	Assert.assertEquals("propName3", property3.getName());
                	
                	Assert.assertTrue(property3 instanceof DecimalProperty);
                	Assert.assertEquals(5, ((DecimalProperty)property3).getLength());
                	Assert.assertEquals(6, ((DecimalProperty)property3).getDecimals());
                	
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test6AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property1.propertyA.propertyType", "set");
                	parameters.put("property1.propertyA.name", "propName1");
                	parameters.put("property1.propertyA.values.element[0]", "VALUE1");
                	parameters.put("property1.propertyA.values.element[1]", "VALUE2");
                	parameters.put("property1.propertyA.values.element[3]", "VALUEX");
                	
                	parameters.put("property1.propertyB.propertyType2", "set");
                	parameters.put("property1.propertyB.name", "propName2");
                	parameters.put("property1.propertyB.values.element[0]", "VALUE3");
                	parameters.put("property1.propertyB.values.element[1]", "VALUE4");
                	parameters.put("property1.propertyB.values.element[3]", "VALUEX");

                	parameters.put("property1.propertyC.propertyType3", "set");
                	parameters.put("property1.propertyC.name", "propName3");
                	parameters.put("property1.propertyC.values.element[0]", "VALUE5");
                	parameters.put("property1.propertyC.values.element[1]", "VALUE6");
                	parameters.put("property1.propertyC.values.element[3]", "VALUEX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test6AnyController controller = (Test6AnyController)request.getAttribute("Controller");
                	
                	Test1AnyBean bean = controller.property1;
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			bean.property1;

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	Assert.assertNotNull(props1);
                	Assert.assertEquals(2, props1.size());
                	Assert.assertEquals("VALUE1", props1.get(0));
                	Assert.assertEquals("VALUE2", props1.get(1));
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			bean.getProperty2();

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	Assert.assertNotNull(props2);
                	Assert.assertEquals(2, props2.size());
                	Assert.assertEquals("VALUE3", props2.get(0));
                	Assert.assertEquals("VALUE4", props2.get(1));                	

                	org.brandao.brutos.helper.any.app1.Property property3 = 
                			bean.getProperty3();

                	Assert.assertEquals("propName3", property3.getName());
                	
                	Assert.assertTrue(property3 instanceof SetProperty);

                	List<String> props3 = ((SetProperty)property3).getValues();
                	
                	Assert.assertNotNull(props3);
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test7AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property1.propertyA.propertyType", "DECIMAL");
                	parameters.put("property1.propertyA.name", "propName1");
                	parameters.put("property1.propertyA.length", "1");
                	parameters.put("property1.propertyA.decimals", "2");
                	
                	parameters.put("property1.propertyB.propertyType2", "DECIMAL");
                	parameters.put("property1.propertyB.name", "propName2");
                	parameters.put("property1.propertyB.length", "3");
                	parameters.put("property1.propertyB.decimals", "4");

                	parameters.put("property1.propertyC.propertyType3", "DECIMAL");
                	parameters.put("property1.propertyC.name", "propName3");
                	parameters.put("property1.propertyC.length", "5");
                	parameters.put("property1.propertyC.decimals", "6");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test7AnyController controller = (Test7AnyController)request.getAttribute("Controller");
                	
                	Test2AnyBean bean = controller.property1;
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			bean.property1;

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof DecimalProperty);
                	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			bean.getProperty2();

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof DecimalProperty);
                	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
                	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());

                	org.brandao.brutos.helper.any.app1.Property property3 = 
                			bean.getProperty3();
                	
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test7AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property1.propertyA.propertyType", "SET");
                	parameters.put("property1.propertyA.name", "propName1");
                	parameters.put("property1.propertyA.values.element[0]", "VALUE1");
                	parameters.put("property1.propertyA.values.element[1]", "VALUE2");
                	parameters.put("property1.propertyA.values.element[3]", "VALUEX");
                	
                	parameters.put("property1.propertyB.propertyType2", "SET");
                	parameters.put("property1.propertyB.name", "propName2");
                	parameters.put("property1.propertyB.values.element[0]", "VALUE3");
                	parameters.put("property1.propertyB.values.element[1]", "VALUE4");
                	parameters.put("property1.propertyB.values.element[3]", "VALUEX");

                	parameters.put("property1.propertyC.propertyType3", "SET");
                	parameters.put("property1.propertyC.name", "propName3");
                	parameters.put("property1.propertyC.values.element[0]", "VALUE5");
                	parameters.put("property1.propertyC.values.element[1]", "VALUE6");
                	parameters.put("property1.propertyC.values.element[3]", "VALUEX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test7AnyController controller = (Test7AnyController)request.getAttribute("Controller");
                	
                	Test2AnyBean bean = controller.property1;
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			bean.property1;

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	Assert.assertNotNull(props1);
                	Assert.assertEquals(2, props1.size());
                	Assert.assertEquals("VALUE1", props1.get(0));
                	Assert.assertEquals("VALUE2", props1.get(1));
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			bean.getProperty2();

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	Assert.assertNotNull(props2);
                	Assert.assertEquals(2, props2.size());
                	Assert.assertEquals("VALUE3", props2.get(0));
                	Assert.assertEquals("VALUE4", props2.get(1));                	

                	org.brandao.brutos.helper.any.app1.Property property3 = 
                			bean.getProperty3();

                	Assert.assertEquals("propName3", property3.getName());
                	
                	Assert.assertTrue(property3 instanceof SetProperty);

                	List<String> props3 = ((SetProperty)property3).getValues();
                	
                	Assert.assertNotNull(props3);
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test8AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property1.propertyA.propertyType", "0");
                	parameters.put("property1.propertyA.name", "propName1");
                	parameters.put("property1.propertyA.length", "1");
                	parameters.put("property1.propertyA.decimals", "2");
                	
                	parameters.put("property1.propertyB.propertyType2", "0");
                	parameters.put("property1.propertyB.name", "propName2");
                	parameters.put("property1.propertyB.length", "3");
                	parameters.put("property1.propertyB.decimals", "4");

                	parameters.put("property1.propertyC.propertyType3", "0");
                	parameters.put("property1.propertyC.name", "propName3");
                	parameters.put("property1.propertyC.length", "5");
                	parameters.put("property1.propertyC.decimals", "6");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test8AnyController controller = (Test8AnyController)request.getAttribute("Controller");
                	
                	Test3AnyBean bean = controller.property1;
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			bean.property1;

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof DecimalProperty);
                	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			bean.getProperty2();

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof DecimalProperty);
                	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
                	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());

                	org.brandao.brutos.helper.any.app1.Property property3 = 
                			bean.getProperty3();
                	
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test8AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property1.propertyA.propertyType", "1");
                	parameters.put("property1.propertyA.name", "propName1");
                	parameters.put("property1.propertyA.values.element[0]", "VALUE1");
                	parameters.put("property1.propertyA.values.element[1]", "VALUE2");
                	parameters.put("property1.propertyA.values.element[3]", "VALUEX");
                	
                	parameters.put("property1.propertyB.propertyType2", "1");
                	parameters.put("property1.propertyB.name", "propName2");
                	parameters.put("property1.propertyB.values.element[0]", "VALUE3");
                	parameters.put("property1.propertyB.values.element[1]", "VALUE4");
                	parameters.put("property1.propertyB.values.element[3]", "VALUEX");

                	parameters.put("property1.propertyC.propertyType3", "1");
                	parameters.put("property1.propertyC.name", "propName3");
                	parameters.put("property1.propertyC.values.element[0]", "VALUE5");
                	parameters.put("property1.propertyC.values.element[1]", "VALUE6");
                	parameters.put("property1.propertyC.values.element[3]", "VALUEX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test8AnyController controller = (Test8AnyController)request.getAttribute("Controller");
                	
                	Test3AnyBean bean = controller.property1;
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			bean.property1;

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	Assert.assertNotNull(props1);
                	Assert.assertEquals(2, props1.size());
                	Assert.assertEquals("VALUE1", props1.get(0));
                	Assert.assertEquals("VALUE2", props1.get(1));
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			bean.getProperty2();

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	Assert.assertNotNull(props2);
                	Assert.assertEquals(2, props2.size());
                	Assert.assertEquals("VALUE3", props2.get(0));
                	Assert.assertEquals("VALUE4", props2.get(1));                	

                	org.brandao.brutos.helper.any.app1.Property property3 = 
                			bean.getProperty3();

                	Assert.assertEquals("propName3", property3.getName());
                	
                	Assert.assertTrue(property3 instanceof SetProperty);

                	List<String> props3 = ((SetProperty)property3).getValues();
                	
                	Assert.assertNotNull(props3);
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test9AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property1.propertyA.propertyType", "2015-01-01");
                	parameters.put("property1.propertyA.name", "propName1");
                	parameters.put("property1.propertyA.length", "1");
                	parameters.put("property1.propertyA.decimals", "2");
                	
                	parameters.put("property1.propertyB.propertyType2", "2015-01-01");
                	parameters.put("property1.propertyB.name", "propName2");
                	parameters.put("property1.propertyB.length", "3");
                	parameters.put("property1.propertyB.decimals", "4");

                	parameters.put("property1.propertyC.propertyType3", "2015-01-01");
                	parameters.put("property1.propertyC.name", "propName3");
                	parameters.put("property1.propertyC.length", "5");
                	parameters.put("property1.propertyC.decimals", "6");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test9AnyController controller = (Test9AnyController)request.getAttribute("Controller");
                	
                	Test4AnyBean bean = controller.property1;
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			bean.property1;

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof DecimalProperty);
                	Assert.assertEquals(1, ((DecimalProperty)property1).getLength());
                	Assert.assertEquals(2, ((DecimalProperty)property1).getDecimals());
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			bean.getProperty2();

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof DecimalProperty);
                	Assert.assertEquals(3, ((DecimalProperty)property2).getLength());
                	Assert.assertEquals(4, ((DecimalProperty)property2).getDecimals());

                	org.brandao.brutos.helper.any.app1.Property property3 = 
                			bean.getProperty3();
                	
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
                            MockXMLWebApplicationContext.class.getName()
                    );

                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test9AnyController.xml"
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property1.propertyA.propertyType", "2015-01-02");
                	parameters.put("property1.propertyA.name", "propName1");
                	parameters.put("property1.propertyA.values.element[0]", "VALUE1");
                	parameters.put("property1.propertyA.values.element[1]", "VALUE2");
                	parameters.put("property1.propertyA.values.element[3]", "VALUEX");
                	
                	parameters.put("property1.propertyB.propertyType2", "2015-01-02");
                	parameters.put("property1.propertyB.name", "propName2");
                	parameters.put("property1.propertyB.values.element[0]", "VALUE3");
                	parameters.put("property1.propertyB.values.element[1]", "VALUE4");
                	parameters.put("property1.propertyB.values.element[3]", "VALUEX");

                	parameters.put("property1.propertyC.propertyType3", "2015-01-02");
                	parameters.put("property1.propertyC.name", "propName3");
                	parameters.put("property1.propertyC.values.element[0]", "VALUE5");
                	parameters.put("property1.propertyC.values.element[1]", "VALUE6");
                	parameters.put("property1.propertyC.values.element[3]", "VALUEX");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	Test9AnyController controller = (Test9AnyController)request.getAttribute("Controller");
                	
                	Test4AnyBean bean = controller.property1;
                	
                	org.brandao.brutos.helper.any.app1.Property property1 = 
                			bean.property1;

                	Assert.assertEquals("propName1", property1.getName());
                	
                	Assert.assertTrue(property1 instanceof SetProperty);
                	
                	List<String> props1 = ((SetProperty)property1).getValues();
                	
                	Assert.assertNotNull(props1);
                	Assert.assertEquals(2, props1.size());
                	Assert.assertEquals("VALUE1", props1.get(0));
                	Assert.assertEquals("VALUE2", props1.get(1));
                	
                	org.brandao.brutos.helper.any.app1.Property property2 = 
                			bean.getProperty2();

                	Assert.assertEquals("propName2", property2.getName());
                	
                	Assert.assertTrue(property2 instanceof SetProperty);

                	List<String> props2 = ((SetProperty)property2).getValues();
                	
                	Assert.assertNotNull(props2);
                	Assert.assertEquals(2, props2.size());
                	Assert.assertEquals("VALUE3", props2.get(0));
                	Assert.assertEquals("VALUE4", props2.get(1));                	

                	org.brandao.brutos.helper.any.app1.Property property3 = 
                			bean.getProperty3();

                	Assert.assertEquals("propName3", property3.getName());
                	
                	Assert.assertTrue(property3 instanceof SetProperty);

                	List<String> props3 = ((SetProperty)property3).getValues();
                	
                	Assert.assertNotNull(props3);
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

	public void testTest10Controller() throws Throwable{
	    WebApplicationContextTester.run(
	        "/controller/test", 
	        new WebApplicationTester() {
	
	            public void prepareContext(Map<String, String> parameters) {
	                parameters.put(
	                        ContextLoader.CONTEXT_CLASS,
	                        MockXMLWebApplicationContext.class.getName()
	                );
	
                    parameters.put(
                            MockXMLWebApplicationContext.contextConfigName,
                            "classpath:org/brandao/brutos/xml/helper/any/app1/Test10AnyController.xml"
                    );
	            }
	
	            public void prepareSession(Map<String, String> parameters) {
	            }
	            
	            public void prepareRequest(Map<String, String> parameters) {
	            	parameters.put("property1.propertyA.propertyType", "set");
	            	parameters.put("property1.propertyA.name", "propName1");
	            	parameters.put("property1.propertyA.values.element[0]", "VALUE1");
	            	parameters.put("property1.propertyA.values.element[1]", "VALUE2");
	            	parameters.put("property1.propertyA.values.element[3]", "VALUEX");
	            	
	            	parameters.put("property1.propertyB.propertyType2", "set");
	            	parameters.put("property1.propertyB.name", "propName2");
	            	parameters.put("property1.propertyB.values.element[0]", "VALUE3");
	            	parameters.put("property1.propertyB.values.element[1]", "VALUE4");
	            	parameters.put("property1.propertyB.values.element[3]", "VALUEX");
	
	            	parameters.put("property1.propertyC.propertyType3", "set");
	            	parameters.put("property1.propertyC.name", "propName3");
	            	parameters.put("property1.propertyC.values.element[0]", "VALUE5");
	            	parameters.put("property1.propertyC.values.element[1]", "VALUE6");
	            	parameters.put("property1.propertyC.values.element[3]", "VALUEX");

	            	parameters.put("propertyD.propertyType4", "set");
	            	parameters.put("propertyD.name", "propName4");
	            	parameters.put("propertyD.values.element[0]", "VALUE7");
	            	parameters.put("propertyD.values.element[1]", "VALUE8");
	            	parameters.put("propertyD.values.element[3]", "VALUEX");
	            	
	            	parameters.put("propertyE.propertyType5", "set");
	            	parameters.put("propertyE.name", "propName5");
	            	parameters.put("propertyE.values.element[0]", "VALUE9");
	            	parameters.put("propertyE.values.element[1]", "VALUE10");
	            	parameters.put("propertyE.values.element[3]", "VALUEX");
	            }
	
	            public void checkResult(HttpServletRequest request, HttpServletResponse response, 
	                    ServletContext context, ConfigurableWebApplicationContext applicationContext) {
	
	            	Test10AnyController controller = (Test10AnyController)request.getAttribute("Controller");
	            	
	            	Test5AnyBean bean = controller.property1;
	            	
	            	org.brandao.brutos.helper.any.app1.Property property1 = 
	            			bean.property1;
	
	            	Assert.assertEquals("propName1", property1.getName());
	            	
	            	Assert.assertTrue(property1 instanceof SetProperty);
	            	
	            	List<String> props1 = ((SetProperty)property1).getValues();
	            	
	            	Assert.assertNotNull(props1);
	            	Assert.assertEquals(2, props1.size());
	            	Assert.assertEquals("VALUE1", props1.get(0));
	            	Assert.assertEquals("VALUE2", props1.get(1));
	            	
	            	org.brandao.brutos.helper.any.app1.Property property2 = 
	            			bean.getProperty2();
	
	            	Assert.assertEquals("propName2", property2.getName());
	            	
	            	Assert.assertTrue(property2 instanceof SetProperty);
	
	            	List<String> props2 = ((SetProperty)property2).getValues();
	            	
	            	Assert.assertNotNull(props2);
	            	Assert.assertEquals(2, props2.size());
	            	Assert.assertEquals("VALUE3", props2.get(0));
	            	Assert.assertEquals("VALUE4", props2.get(1));                	
	
	            	org.brandao.brutos.helper.any.app1.Property property3 = 
	            			bean.getProperty3();
	
	            	Assert.assertEquals("propName3", property3.getName());
	            	
	            	Assert.assertTrue(property3 instanceof SetProperty);
	
	            	List<String> props3 = ((SetProperty)property3).getValues();
	            	
	            	Assert.assertNotNull(props3);
	            	Assert.assertEquals(2, props3.size());
	            	Assert.assertEquals("VALUE5", props3.get(0));
	            	Assert.assertEquals("VALUE6", props3.get(1));                	

	            	org.brandao.brutos.helper.any.app1.Property property4 = 
	            			controller.property2;
	
	            	Assert.assertEquals("propName4", property4.getName());
	            	
	            	Assert.assertTrue(property4 instanceof SetProperty);
	
	            	List<String> props4 = ((SetProperty)property4).getValues();
	            	
	            	Assert.assertNotNull(props4);
	            	Assert.assertEquals(2, props4.size());
	            	Assert.assertEquals("VALUE7", props4.get(0));
	            	Assert.assertEquals("VALUE8", props4.get(1));                	

	            	org.brandao.brutos.helper.any.app1.Property property5 = 
	            			controller.property3;
	
	            	Assert.assertEquals("propName5", property5.getName());
	            	
	            	Assert.assertTrue(property5 instanceof SetProperty);
	
	            	List<String> props5 = ((SetProperty)property5).getValues();
	            	
	            	Assert.assertNotNull(props5);
	            	Assert.assertEquals(2, props5.size());
	            	Assert.assertEquals("VALUE9", props5.get(0));
	            	Assert.assertEquals("VALUE10", props5.get(1));                	
	            	
	            }
	
	            public void checkException(Throwable e) throws Throwable {
	                throw e;
	            }
	        },
	        new Class[]{Test10AnyController.class});
	}    
    
}
