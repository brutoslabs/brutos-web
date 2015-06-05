package org.brandao.brutos.annotation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.EnumTest;
import org.brandao.brutos.annotation.helper.elementcollection.app1.ControllerElementCollectionActionTest;
import org.brandao.brutos.annotation.helper.elementcollection.app1.ControllerElementCollectionBeanTest;
import org.brandao.brutos.annotation.helper.elementcollection.app1.ControllerElementCollectionConstructorTest;
import org.brandao.brutos.annotation.helper.elementcollection.app1.ControllerElementCollectionFieldTest;
import org.brandao.brutos.annotation.helper.elementcollection.app1.ControllerElementCollectionPropertyTest;
import org.brandao.brutos.annotation.helper.elementcollection.app1.ElementCollectionBeanTest0;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class ElementCollectionTest extends TestCase{
    
	private SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy");
	
    public void testPropertyAction() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property", 
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
                	parameters.put("arg0.element[0]", "0");
                	parameters.put("arg0.element[1]", "1");
                	parameters.put("arg0.element[3]", "3");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionActionTest controller = 
                			(ControllerElementCollectionActionTest)request.getAttribute("Controller");
                	
                	List<Integer> result = controller.getProperty();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals(new Integer(0), result.get(0));
                	Assert.assertEquals(new Integer(1), result.get(1));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionActionTest.class});
    }

    public void testProperty2Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property2", 
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
                	parameters.put("arg0.element[0]", "0");
                	parameters.put("arg0.element[1]", "1");
                	parameters.put("arg0.element[3]", "3");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionActionTest controller = 
                			(ControllerElementCollectionActionTest)request.getAttribute("Controller");
                	
                	List<Integer> result = controller.getProperty2();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals(new Integer(0), result.get(0));
                	Assert.assertEquals(new Integer(1), result.get(1));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionActionTest.class});
    }

    public void testProperty3Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property3", 
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
                	parameters.put("arg0.elx[0]", "0");
                	parameters.put("arg0.elx[1]", "1");
                	parameters.put("arg0.elx[3]", "3");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionActionTest controller = 
                			(ControllerElementCollectionActionTest)request.getAttribute("Controller");
                	
                	List<Integer> result = controller.getProperty3();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals(new Integer(0), result.get(0));
                	Assert.assertEquals(new Integer(1), result.get(1));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionActionTest.class});
    }

    public void testPropert4Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property4", 
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
                	parameters.put("arg0.element[0]", "VALUE1");
                	parameters.put("arg0.element[1]", "VALUE2");
                	parameters.put("arg0.element[3]", "VALUE1");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionActionTest controller = 
                			(ControllerElementCollectionActionTest)request.getAttribute("Controller");
                	
                	List<EnumTest> result = controller.getProperty4();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals(EnumTest.VALUE1, result.get(0));
                	Assert.assertEquals(EnumTest.VALUE2, result.get(1));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionActionTest.class});
    }

    public void testProperty5Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property5", 
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
                	parameters.put("arg0.element[0]", "0");
                	parameters.put("arg0.element[1]", "1");
                	parameters.put("arg0.element[3]", "3");
                }

                public void prepareRequest(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionActionTest controller = 
                			(ControllerElementCollectionActionTest)request.getAttribute("Controller");
                	
                	List<Integer> result = controller.getProperty5();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals(new Integer(0), result.get(0));
                	Assert.assertEquals(new Integer(1), result.get(1));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionActionTest.class});
    }

    public void testProperty6Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property6", 
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
                	parameters.put("arg0.element[0]", "01-01-2015");
                	parameters.put("arg0.element[1]", "02-01-2015");
                	parameters.put("arg0.element[3]", "03-01-2015");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionActionTest controller = 
                			(ControllerElementCollectionActionTest)request.getAttribute("Controller");
                	
                	List<Date> result = controller.getProperty6();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	try{
						Assert.assertEquals(sdf.parseObject("01-01-2015"), result.get(0));
	                	Assert.assertEquals(sdf.parseObject("02-01-2015"), result.get(1));
					} 
                	catch (ParseException e) {
						throw new RuntimeException(e);
					}
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionActionTest.class});
    }
    
    public void testProperty7Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property7", 
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
                	parameters.put("arg0.element[0]", "0");
                	parameters.put("arg0.element[1]", "1");
                	parameters.put("arg0.element[3]", "3");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionActionTest controller = 
                			(ControllerElementCollectionActionTest)request.getAttribute("Controller");
                	
                	List<Integer> result = controller.getProperty7();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals(new Integer(0), result.get(0));
                	Assert.assertEquals(new Integer(1), result.get(1));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionActionTest.class});
    }
    
    public void testProperty8Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property8", 
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
                	parameters.put("arg0.element[0]", "0");
                	parameters.put("arg0.element[1]", "1");
                	parameters.put("arg0.element[3]", "3");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionActionTest controller = 
                			(ControllerElementCollectionActionTest)request.getAttribute("Controller");
                	
                	List<String> result = controller.getProperty8();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("xx-0", result.get(0));
                	Assert.assertEquals("xx-1", result.get(1));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionActionTest.class});
    }
    
    public void testProperty9Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property9", 
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
                	parameters.put("arg0.element[0]", "0");
                	parameters.put("arg0.element[1]", "1");
                	parameters.put("arg0.element[3]", "3");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionActionTest controller = 
                			(ControllerElementCollectionActionTest)request.getAttribute("Controller");
                	
                	List<ElementCollectionBeanTest0> result = controller.getProperty9();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("0", result.get(0).getProperty());
                	Assert.assertEquals("1", result.get(1).getProperty());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionActionTest.class});
    }        

    public void testProperty10Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property10", 
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
                	parameters.put("arg0.element[0].property", "0");
                	parameters.put("arg0.element[1].property", "1");
                	parameters.put("arg0.element[3].property", "3");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionActionTest controller = 
                			(ControllerElementCollectionActionTest)request.getAttribute("Controller");
                	
                	List<ElementCollectionBeanTest0> result = controller.getProperty10();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("0", result.get(0).getProperty());
                	Assert.assertEquals("1", result.get(1).getProperty());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionActionTest.class});
    }        

    public void testProperty11Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property11", 
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
                	parameters.put("arg0.element[0].property", "0");
                	parameters.put("arg0.element[1].property", "1");
                	parameters.put("arg0.element[3].property", "3");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionActionTest controller = 
                			(ControllerElementCollectionActionTest)request.getAttribute("Controller");
                	
                	List<ElementCollectionBeanTest0> result = controller.getProperty11();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("0", result.get(0).getProperty());
                	Assert.assertEquals("1", result.get(1).getProperty());
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionActionTest.class});
    }        

    public void testProperty12Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property12", 
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

                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("arg0.element[0]", "0");
                	parameters.put("arg0.element[1]", "1");
                	parameters.put("arg0.element[3]", "3");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionActionTest controller = 
                			(ControllerElementCollectionActionTest)request.getAttribute("Controller");
                	
                	List<EnumTest> result = controller.getProperty12();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals(EnumTest.VALUE1, result.get(0));
                	Assert.assertEquals(EnumTest.VALUE2, result.get(1));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionActionTest.class});
    }        

    public void testBeanTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                	
                	parameters.put("property1.property.element[0]", "0");
                	parameters.put("property1.property.element[1]", "1");
                	
                	parameters.put("property1.property2.element[0]", "0");
                	parameters.put("property1.property2.element[1]", "1");

                	parameters.put("property1.property3.elx[0]", "0");
                	parameters.put("property1.property3.elx[1]", "1");

                	parameters.put("property1.property4.element[0]", "VALUE1");
                	parameters.put("property1.property4.element[1]", "VALUE2");

                	parameters.put("property1.property6.element[0]", "01-01-2015");
                	parameters.put("property1.property6.element[1]", "02-01-2015");

                	parameters.put("property1.property7.element[0]", "0");
                	parameters.put("property1.property7.element[1]", "1");

                	parameters.put("property1.property8.element[0]", "0");
                	parameters.put("property1.property8.element[1]", "1");

                	parameters.put("property1.property9.element[0]", "0");
                	parameters.put("property1.property9.element[1]", "1");

                	parameters.put("property1.property10.element[0].property", "0");
                	parameters.put("property1.property10.element[1].property", "1");

                	parameters.put("property1.property10.element[0].property", "0");
                	parameters.put("property1.property10.element[1].property", "1");

                	parameters.put("property1.property11.element[0].property", "0");
                	parameters.put("property1.property11.element[1].property", "1");
                	
                	parameters.put("property1.property12.element[0]", "0");
                	parameters.put("property1.property12.element[1]", "1");
                	
                	//Property 2
                	
                	parameters.put("property2.property.element[0]", "0");
                	parameters.put("property2.property.element[1]", "1");
                	
                	parameters.put("property2.property2.element[0]", "0");
                	parameters.put("property2.property2.element[1]", "1");

                	parameters.put("property2.property3.elx[0]", "0");
                	parameters.put("property2.property3.elx[1]", "1");

                	parameters.put("property2.property4.element[0]", "VALUE1");
                	parameters.put("property2.property4.element[1]", "VALUE2");

                	parameters.put("property2.property6.element[0]", "01-01-2015");
                	parameters.put("property2.property6.element[1]", "02-01-2015");

                	parameters.put("property2.property7.element[0]", "0");
                	parameters.put("property2.property7.element[1]", "1");

                	parameters.put("property2.property8.element[0]", "0");
                	parameters.put("property2.property8.element[1]", "1");

                	parameters.put("property2.property9.element[0]", "0");
                	parameters.put("property2.property9.element[1]", "1");

                	parameters.put("property2.property10.element[0].property", "0");
                	parameters.put("property2.property10.element[1].property", "1");

                	parameters.put("property2.property10.element[0].property", "0");
                	parameters.put("property2.property10.element[1].property", "1");

                	parameters.put("property2.property11.element[0].property", "0");
                	parameters.put("property2.property11.element[1].property", "1");
                	
                	parameters.put("property2.property12.element[0]", "0");
                	parameters.put("property2.property12.element[1]", "1");                	
                }

                public void prepareSession(Map<String, String> parameters) {
                	parameters.put("property1.property5.element[0]", "0");
                	parameters.put("property1.property5.element[1]", "1");
                	
                	// Property 2
                	parameters.put("property2.property5.element[0]", "0");
                	parameters.put("property2.property5.element[1]", "1");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionBeanTest controller = 
                			(ControllerElementCollectionBeanTest)request.getAttribute("Controller");
                	
                	List<Integer> property1Property = controller.getProperty1().property;
                	
                	Assert.assertNotNull(property1Property);
                	Assert.assertEquals(2, property1Property.size());
                	Assert.assertEquals(new Integer(0), property1Property.get(0));
                	Assert.assertEquals(new Integer(1), property1Property.get(1));
                	
                	List<Integer> property1Property2 = controller.getProperty1().property2;
                	
                	Assert.assertNotNull(property1Property2);
                	Assert.assertEquals(2, property1Property2.size());
                	Assert.assertEquals(new Integer(0), property1Property2.get(0));
                	Assert.assertEquals(new Integer(1), property1Property2.get(1));

                	List<Integer> property1Property3 = controller.getProperty1().property3;
                	
                	Assert.assertNotNull(property1Property3);
                	Assert.assertEquals(2, property1Property3.size());
                	Assert.assertEquals(new Integer(0), property1Property3.get(0));
                	Assert.assertEquals(new Integer(1), property1Property3.get(1));

                	List<EnumTest> property1Property4 = controller.getProperty1().property4;
                	
                	Assert.assertNotNull(property1Property4);
                	Assert.assertEquals(2, property1Property4.size());
                	Assert.assertEquals(EnumTest.VALUE1, property1Property4.get(0));
                	Assert.assertEquals(EnumTest.VALUE2, property1Property4.get(1));

                	List<Integer> property1Property5 = controller.getProperty1().property5;
                	
                	Assert.assertNotNull(property1Property5);
                	Assert.assertEquals(2, property1Property5.size());
                	Assert.assertEquals(new Integer(0), property1Property5.get(0));
                	Assert.assertEquals(new Integer(1), property1Property5.get(1));

                	List<Date> property1Property6 = controller.getProperty1().property6;
                	
                	Assert.assertNotNull(property1Property6);
                	Assert.assertEquals(2, property1Property6.size());
                	
                	try {
						Assert.assertEquals(sdf.parseObject("01-01-2015"), property1Property6.get(0));
	                	Assert.assertEquals(sdf.parseObject("02-01-2015"), property1Property6.get(1));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	List<Integer> property1Property7 = controller.getProperty1().property7;
                	
                	Assert.assertNotNull(property1Property7);
                	Assert.assertEquals(2, property1Property7.size());
                	Assert.assertEquals(new Integer(0), property1Property7.get(0));
                	Assert.assertEquals(new Integer(1), property1Property7.get(1));

                	List<String> property1Property8 = controller.getProperty1().property8;
                	
                	Assert.assertNotNull(property1Property8);
                	Assert.assertEquals(2, property1Property8.size());
                	Assert.assertEquals("xx-0", property1Property8.get(0));
                	Assert.assertEquals("xx-1", property1Property8.get(1));

                	List<ElementCollectionBeanTest0> property1Property9 = controller.getProperty1().property9;
                	
                	Assert.assertNotNull(property1Property9);
                	Assert.assertEquals(2, property1Property9.size());
                	Assert.assertEquals("0", property1Property9.get(0).getProperty());
                	Assert.assertEquals("1", property1Property9.get(1).getProperty());

                	List<ElementCollectionBeanTest0> property1Property10 = controller.getProperty1().property10;
                	
                	Assert.assertNotNull(property1Property10);
                	Assert.assertEquals(2, property1Property10.size());
                	Assert.assertEquals("0", property1Property10.get(0).getProperty());
                	Assert.assertEquals("1", property1Property10.get(1).getProperty());

                	List<ElementCollectionBeanTest0> property1Property11 = controller.getProperty1().property11;
                	
                	Assert.assertNotNull(property1Property11);
                	Assert.assertEquals(2, property1Property11.size());
                	Assert.assertEquals("0", property1Property11.get(0).getProperty());
                	Assert.assertEquals("1", property1Property11.get(1).getProperty());

                	List<EnumTest> property1Property12 = controller.getProperty1().property12;
                	
                	Assert.assertNotNull(property1Property12);
                	Assert.assertEquals(2, property1Property12.size());
                	Assert.assertEquals(EnumTest.VALUE1, property1Property12.get(0));
                	Assert.assertEquals(EnumTest.VALUE2, property1Property12.get(1));

                	//Property 2
                	
                	List<Integer> property2Property = controller.getProperty2().getProperty();
                	
                	Assert.assertNotNull(property2Property);
                	Assert.assertEquals(2, property1Property.size());
                	Assert.assertEquals(new Integer(0), property2Property.get(0));
                	Assert.assertEquals(new Integer(1), property2Property.get(1));
                	
                	List<Integer> property2Property2 = controller.getProperty2().getProperty2();
                	
                	Assert.assertNotNull(property2Property2);
                	Assert.assertEquals(2, property2Property2.size());
                	Assert.assertEquals(new Integer(0), property2Property2.get(0));
                	Assert.assertEquals(new Integer(1), property2Property2.get(1));

                	List<Integer> property2Property3 = controller.getProperty2().getProperty3();
                	
                	Assert.assertNotNull(property2Property3);
                	Assert.assertEquals(2, property2Property3.size());
                	Assert.assertEquals(new Integer(0), property2Property3.get(0));
                	Assert.assertEquals(new Integer(1), property2Property3.get(1));

                	List<EnumTest> property2Property4 = controller.getProperty2().getProperty4();
                	
                	Assert.assertNotNull(property2Property4);
                	Assert.assertEquals(2, property1Property2.size());
                	Assert.assertEquals(EnumTest.VALUE1, property2Property4.get(0));
                	Assert.assertEquals(EnumTest.VALUE2, property2Property4.get(1));

                	List<Integer> property2Property5 = controller.getProperty2().getProperty5();
                	
                	Assert.assertNotNull(property2Property5);
                	Assert.assertEquals(2, property2Property5.size());
                	Assert.assertEquals(new Integer(0), property2Property5.get(0));
                	Assert.assertEquals(new Integer(1), property2Property5.get(1));

                	List<Date> property2Property6 = controller.getProperty2().getProperty6();
                	
                	Assert.assertNotNull(property2Property6);
                	Assert.assertEquals(2, property2Property6.size());
                	
                	try {
						Assert.assertEquals(sdf.parseObject("01-01-2015"), property2Property6.get(0));
	                	Assert.assertEquals(sdf.parseObject("02-01-2015"), property2Property6.get(1));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	List<Integer> property2Property7 = controller.getProperty2().getProperty7();
                	
                	Assert.assertNotNull(property2Property7);
                	Assert.assertEquals(2, property2Property7.size());
                	Assert.assertEquals(new Integer(0), property2Property7.get(0));
                	Assert.assertEquals(new Integer(1), property2Property7.get(1));

                	List<String> property2Property8 = controller.getProperty2().getProperty8();
                	
                	Assert.assertNotNull(property2Property8);
                	Assert.assertEquals(2, property2Property8.size());
                	Assert.assertEquals("xx-0", property2Property8.get(0));
                	Assert.assertEquals("xx-1", property2Property8.get(1));

                	List<ElementCollectionBeanTest0> property2Property9 = controller.getProperty2().getProperty9();
                	
                	Assert.assertNotNull(property2Property9);
                	Assert.assertEquals(2, property2Property9.size());
                	Assert.assertEquals("0", property2Property9.get(0).getProperty());
                	Assert.assertEquals("1", property2Property9.get(1).getProperty());

                	List<ElementCollectionBeanTest0> property2Property10 = controller.getProperty2().getProperty10();
                	
                	Assert.assertNotNull(property2Property10);
                	Assert.assertEquals(2, property2Property10.size());
                	Assert.assertEquals("0", property2Property10.get(0).getProperty());
                	Assert.assertEquals("1", property2Property10.get(1).getProperty());

                	List<ElementCollectionBeanTest0> property2Property11 = controller.getProperty2().getProperty11();
                	
                	Assert.assertNotNull(property2Property11);
                	Assert.assertEquals(2, property2Property11.size());
                	Assert.assertEquals("0", property2Property11.get(0).getProperty());
                	Assert.assertEquals("1", property2Property11.get(1).getProperty());

                	List<EnumTest> property2Property12 = controller.getProperty2().getProperty12();
                	
                	Assert.assertNotNull(property2Property12);
                	Assert.assertEquals(2, property2Property12.size());
                	Assert.assertEquals(EnumTest.VALUE1, property2Property12.get(0));
                	Assert.assertEquals(EnumTest.VALUE2, property2Property12.get(1));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionBeanTest.class});
    }        

    public void testConstructorTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                	
                	parameters.put("property1.arg0.element[0]", "0");
                	parameters.put("property1.arg0.element[1]", "1");
                	
                	parameters.put("property2.arg0.element[0]", "0");
                	parameters.put("property2.arg0.element[1]", "1");

                	parameters.put("property3.arg0.elx[0]", "0");
                	parameters.put("property3.arg0.elx[1]", "1");

                	parameters.put("property4.arg0.element[0]", "VALUE1");
                	parameters.put("property4.arg0.element[1]", "VALUE2");

                	parameters.put("property6.arg0.element[0]", "01-01-2015");
                	parameters.put("property6.arg0.element[1]", "02-01-2015");

                	parameters.put("property7.arg0.element[0]", "0");
                	parameters.put("property7.arg0.element[1]", "1");

                	parameters.put("property8.arg0.element[0]", "0");
                	parameters.put("property8.arg0.element[1]", "1");

                	parameters.put("property9.arg0.element[0]", "0");
                	parameters.put("property9.arg0.element[1]", "1");

                	parameters.put("property10.arg0.element[0].property", "0");
                	parameters.put("property10.arg0.element[1].property", "1");

                	parameters.put("property11.arg0.element[0].property", "0");
                	parameters.put("property11.arg0.element[1].property", "1");

                	parameters.put("property12.arg0.element[0]", "0");
                	parameters.put("property12.arg0.element[1]", "1");            	
                }

                public void prepareSession(Map<String, String> parameters) {
                	parameters.put("property5.arg0.element[0]", "0");
                	parameters.put("property5.arg0.element[1]", "1");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionConstructorTest controller = 
                			(ControllerElementCollectionConstructorTest)request.getAttribute("Controller");
                	
                	List<Integer> property1 = controller.property1.getEntity();
                	
                	Assert.assertNotNull(property1);
                	Assert.assertEquals(2, property1.size());
                	Assert.assertEquals(new Integer(0), property1.get(0));
                	Assert.assertEquals(new Integer(1), property1.get(1));
                	
                	List<Integer> property2 = controller.property2.getEntity();
                	
                	Assert.assertNotNull(property2);
                	Assert.assertEquals(2, property2.size());
                	Assert.assertEquals(new Integer(0), property2.get(0));
                	Assert.assertEquals(new Integer(1), property2.get(1));

                	List<Integer> property3 = controller.property3.getEntity();
                	
                	Assert.assertNotNull(property3);
                	Assert.assertEquals(2, property3.size());
                	Assert.assertEquals(new Integer(0), property3.get(0));
                	Assert.assertEquals(new Integer(1), property3.get(1));

                	List<EnumTest> property4 = controller.property4.getEntity();
                	
                	Assert.assertNotNull(property4);
                	Assert.assertEquals(2, property4.size());
                	Assert.assertEquals(EnumTest.VALUE1, property4.get(0));
                	Assert.assertEquals(EnumTest.VALUE2, property4.get(1));

                	List<Integer> property5 = controller.property5.getEntity();
                	
                	Assert.assertNotNull(property5);
                	Assert.assertEquals(2, property5.size());
                	Assert.assertEquals(new Integer(0), property5.get(0));
                	Assert.assertEquals(new Integer(1), property5.get(1));

                	List<Date> property6 = controller.property6.getEntity();
                	
                	Assert.assertNotNull(property6);
                	Assert.assertEquals(2, property6.size());
                	
                	try {
						Assert.assertEquals(sdf.parseObject("01-01-2015"), property6.get(0));
	                	Assert.assertEquals(sdf.parseObject("02-01-2015"), property6.get(1));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	List<Integer> property7 = controller.property7.getEntity();
                	
                	Assert.assertNotNull(property7);
                	Assert.assertEquals(2, property7.size());
                	Assert.assertEquals(new Integer(0), property7.get(0));
                	Assert.assertEquals(new Integer(1), property7.get(1));

                	List<String> property8 = controller.property8.getEntity();
                	
                	Assert.assertNotNull(property8);
                	Assert.assertEquals(2, property8.size());
                	Assert.assertEquals("xx-0", property8.get(0));
                	Assert.assertEquals("xx-1", property8.get(1));

                	List<ElementCollectionBeanTest0> property9 = controller.property9.getEntity();
                	
                	Assert.assertNotNull(property9);
                	Assert.assertEquals(2, property9.size());
                	Assert.assertEquals("0", property9.get(0).getProperty());
                	Assert.assertEquals("1", property9.get(1).getProperty());

                	List<ElementCollectionBeanTest0> property10 = controller.property10.getEntity();
                	
                	Assert.assertNotNull(property10);
                	Assert.assertEquals(2, property10.size());
                	Assert.assertEquals("0", property10.get(0).getProperty());
                	Assert.assertEquals("1", property10.get(1).getProperty());

                	List<ElementCollectionBeanTest0> property11 = controller.property11.getEntity();
                	
                	Assert.assertNotNull(property11);
                	Assert.assertEquals(2, property11.size());
                	Assert.assertEquals("0", property11.get(0).getProperty());
                	Assert.assertEquals("1", property11.get(1).getProperty());

                	List<EnumTest> property12 = controller.property12.getEntity();
                	
                	Assert.assertNotNull(property12);
                	Assert.assertEquals(2, property12.size());
                	Assert.assertEquals(EnumTest.VALUE1, property12.get(0));
                	Assert.assertEquals(EnumTest.VALUE2, property12.get(1));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionConstructorTest.class});
    }        

    public void testFieldTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                	
                	parameters.put("property.element[0]", "0");
                	parameters.put("property.element[1]", "1");
                	
                	parameters.put("property2.element[0]", "0");
                	parameters.put("property2.element[1]", "1");

                	parameters.put("property3.elx[0]", "0");
                	parameters.put("property3.elx[1]", "1");

                	parameters.put("property4.element[0]", "VALUE1");
                	parameters.put("property4.element[1]", "VALUE2");

                	parameters.put("property6.element[0]", "01-01-2015");
                	parameters.put("property6.element[1]", "02-01-2015");

                	parameters.put("property7.element[0]", "0");
                	parameters.put("property7.element[1]", "1");

                	parameters.put("property8.element[0]", "0");
                	parameters.put("property8.element[1]", "1");

                	parameters.put("property9.element[0]", "0");
                	parameters.put("property9.element[1]", "1");

                	parameters.put("property10.element[0].property", "0");
                	parameters.put("property10.element[1].property", "1");

                	parameters.put("property11.element[0].property", "0");
                	parameters.put("property11.element[1].property", "1");

                	parameters.put("property12.element[0]", "0");
                	parameters.put("property12.element[1]", "1");            	
                }

                public void prepareSession(Map<String, String> parameters) {
                	parameters.put("property5.element[0]", "0");
                	parameters.put("property5.element[1]", "1");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionFieldTest controller = 
                			(ControllerElementCollectionFieldTest)request.getAttribute("Controller");
                	
                	List<Integer> property1 = controller.property;
                	
                	Assert.assertNotNull(property1);
                	Assert.assertEquals(2, property1.size());
                	Assert.assertEquals(new Integer(0), property1.get(0));
                	Assert.assertEquals(new Integer(1), property1.get(1));
                	
                	List<Integer> property2 = controller.property2;
                	
                	Assert.assertNotNull(property2);
                	Assert.assertEquals(2, property2.size());
                	Assert.assertEquals(new Integer(0), property2.get(0));
                	Assert.assertEquals(new Integer(1), property2.get(1));

                	List<Integer> property3 = controller.property3;
                	
                	Assert.assertNotNull(property3);
                	Assert.assertEquals(2, property3.size());
                	Assert.assertEquals(new Integer(0), property3.get(0));
                	Assert.assertEquals(new Integer(1), property3.get(1));

                	List<EnumTest> property4 = controller.property4;
                	
                	Assert.assertNotNull(property4);
                	Assert.assertEquals(2, property4.size());
                	Assert.assertEquals(EnumTest.VALUE1, property4.get(0));
                	Assert.assertEquals(EnumTest.VALUE2, property4.get(1));

                	List<Integer> property5 = controller.property5;
                	
                	Assert.assertNotNull(property5);
                	Assert.assertEquals(2, property5.size());
                	Assert.assertEquals(new Integer(0), property5.get(0));
                	Assert.assertEquals(new Integer(1), property5.get(1));

                	List<Date> property6 = controller.property6;
                	
                	Assert.assertNotNull(property6);
                	Assert.assertEquals(2, property6.size());
                	
                	try {
						Assert.assertEquals(sdf.parseObject("01-01-2015"), property6.get(0));
	                	Assert.assertEquals(sdf.parseObject("02-01-2015"), property6.get(1));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	List<Integer> property7 = controller.property7;
                	
                	Assert.assertNotNull(property7);
                	Assert.assertEquals(2, property7.size());
                	Assert.assertEquals(new Integer(0), property7.get(0));
                	Assert.assertEquals(new Integer(1), property7.get(1));

                	List<String> property8 = controller.property8;
                	
                	Assert.assertNotNull(property8);
                	Assert.assertEquals(2, property8.size());
                	Assert.assertEquals("xx-0", property8.get(0));
                	Assert.assertEquals("xx-1", property8.get(1));

                	List<ElementCollectionBeanTest0> property9 = controller.property9;
                	
                	Assert.assertNotNull(property9);
                	Assert.assertEquals(2, property9.size());
                	Assert.assertEquals("0", property9.get(0).getProperty());
                	Assert.assertEquals("1", property9.get(1).getProperty());

                	List<ElementCollectionBeanTest0> property10 = controller.property10;
                	
                	Assert.assertNotNull(property10);
                	Assert.assertEquals(2, property10.size());
                	Assert.assertEquals("0", property10.get(0).getProperty());
                	Assert.assertEquals("1", property10.get(1).getProperty());

                	List<ElementCollectionBeanTest0> property11 = controller.property11;
                	
                	Assert.assertNotNull(property11);
                	Assert.assertEquals(2, property11.size());
                	Assert.assertEquals("0", property11.get(0).getProperty());
                	Assert.assertEquals("1", property11.get(1).getProperty());

                	List<EnumTest> property12 = controller.property12;
                	
                	Assert.assertNotNull(property12);
                	Assert.assertEquals(2, property12.size());
                	Assert.assertEquals(EnumTest.VALUE1, property12.get(0));
                	Assert.assertEquals(EnumTest.VALUE2, property12.get(1));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionFieldTest.class});
    }        
    
    public void testPropertyTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                	
                	parameters.put("property.element[0]", "0");
                	parameters.put("property.element[1]", "1");
                	
                	parameters.put("property2.element[0]", "0");
                	parameters.put("property2.element[1]", "1");

                	parameters.put("property3.elx[0]", "0");
                	parameters.put("property3.elx[1]", "1");

                	parameters.put("property4.element[0]", "VALUE1");
                	parameters.put("property4.element[1]", "VALUE2");

                	parameters.put("property6.element[0]", "01-01-2015");
                	parameters.put("property6.element[1]", "02-01-2015");

                	parameters.put("property7.element[0]", "0");
                	parameters.put("property7.element[1]", "1");

                	parameters.put("property8.element[0]", "0");
                	parameters.put("property8.element[1]", "1");

                	parameters.put("property9.element[0]", "0");
                	parameters.put("property9.element[1]", "1");

                	parameters.put("property10.element[0].property", "0");
                	parameters.put("property10.element[1].property", "1");

                	parameters.put("property11.element[0].property", "0");
                	parameters.put("property11.element[1].property", "1");

                	parameters.put("property12.element[0]", "0");
                	parameters.put("property12.element[1]", "1");            	
                }

                public void prepareSession(Map<String, String> parameters) {
                	parameters.put("property5.element[0]", "0");
                	parameters.put("property5.element[1]", "1");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerElementCollectionPropertyTest controller = 
                			(ControllerElementCollectionPropertyTest)request.getAttribute("Controller");
                	
                	List<Integer> property1 = controller.getProperty();
                	
                	Assert.assertNotNull(property1);
                	Assert.assertEquals(2, property1.size());
                	Assert.assertEquals(new Integer(0), property1.get(0));
                	Assert.assertEquals(new Integer(1), property1.get(1));
                	
                	List<Integer> property2 = controller.getProperty2();
                	
                	Assert.assertNotNull(property2);
                	Assert.assertEquals(2, property2.size());
                	Assert.assertEquals(new Integer(0), property2.get(0));
                	Assert.assertEquals(new Integer(1), property2.get(1));

                	List<Integer> property3 = controller.getProperty3();
                	
                	Assert.assertNotNull(property3);
                	Assert.assertEquals(2, property3.size());
                	Assert.assertEquals(new Integer(0), property3.get(0));
                	Assert.assertEquals(new Integer(1), property3.get(1));

                	List<EnumTest> property4 = controller.getProperty4();
                	
                	Assert.assertNotNull(property4);
                	Assert.assertEquals(2, property4.size());
                	Assert.assertEquals(EnumTest.VALUE1, property4.get(0));
                	Assert.assertEquals(EnumTest.VALUE2, property4.get(1));

                	List<Integer> property5 = controller.getProperty5();
                	
                	Assert.assertNotNull(property5);
                	Assert.assertEquals(2, property5.size());
                	Assert.assertEquals(new Integer(0), property5.get(0));
                	Assert.assertEquals(new Integer(1), property5.get(1));

                	List<Date> property6 = controller.getProperty6();
                	
                	Assert.assertNotNull(property6);
                	Assert.assertEquals(2, property6.size());
                	
                	try {
						Assert.assertEquals(sdf.parseObject("01-01-2015"), property6.get(0));
	                	Assert.assertEquals(sdf.parseObject("02-01-2015"), property6.get(1));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	List<Integer> property7 = controller.getProperty7();
                	
                	Assert.assertNotNull(property7);
                	Assert.assertEquals(2, property7.size());
                	Assert.assertEquals(new Integer(0), property7.get(0));
                	Assert.assertEquals(new Integer(1), property7.get(1));

                	List<String> property8 = controller.getProperty8();
                	
                	Assert.assertNotNull(property8);
                	Assert.assertEquals(2, property8.size());
                	Assert.assertEquals("xx-0", property8.get(0));
                	Assert.assertEquals("xx-1", property8.get(1));

                	List<ElementCollectionBeanTest0> property9 = controller.getProperty9();
                	
                	Assert.assertNotNull(property9);
                	Assert.assertEquals(2, property9.size());
                	Assert.assertEquals("0", property9.get(0).getProperty());
                	Assert.assertEquals("1", property9.get(1).getProperty());

                	List<ElementCollectionBeanTest0> property10 = controller.getProperty10();
                	
                	Assert.assertNotNull(property10);
                	Assert.assertEquals(2, property10.size());
                	Assert.assertEquals("0", property10.get(0).getProperty());
                	Assert.assertEquals("1", property10.get(1).getProperty());

                	List<ElementCollectionBeanTest0> property11 = controller.getProperty11();
                	
                	Assert.assertNotNull(property11);
                	Assert.assertEquals(2, property11.size());
                	Assert.assertEquals("0", property11.get(0).getProperty());
                	Assert.assertEquals("1", property11.get(1).getProperty());

                	List<EnumTest> property12 = controller.getProperty12();
                	
                	Assert.assertNotNull(property12);
                	Assert.assertEquals(2, property12.size());
                	Assert.assertEquals(EnumTest.VALUE1, property12.get(0));
                	Assert.assertEquals(EnumTest.VALUE2, property12.get(1));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerElementCollectionPropertyTest.class});
    }           
}
