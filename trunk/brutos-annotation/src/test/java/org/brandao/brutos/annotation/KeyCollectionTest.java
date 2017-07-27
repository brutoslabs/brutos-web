package org.brandao.brutos.annotation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.EnumTest;
import org.brandao.brutos.annotation.helper.keycollection.app1.ControllerKeyCollectionActionTest;
import org.brandao.brutos.annotation.helper.keycollection.app1.ControllerKeyCollectionBeanTest;
import org.brandao.brutos.annotation.helper.keycollection.app1.ControllerKeyCollectionConstructorTest;
import org.brandao.brutos.annotation.helper.keycollection.app1.ControllerKeyCollectionCustomCollectionTest;
import org.brandao.brutos.annotation.helper.keycollection.app1.ControllerKeyCollectionFieldTest;
import org.brandao.brutos.annotation.helper.keycollection.app1.ControllerKeyCollectionPropertyTest;
import org.brandao.brutos.annotation.helper.keycollection.app1.CustomMap;
import org.brandao.brutos.annotation.helper.keycollection.app1.KeyCollectionBeanTest0;
import org.brandao.brutos.annotation.helper.keycollection.fail.ControllerKeyCollectionBeanConstructorFailTest;
import org.brandao.brutos.annotation.helper.keycollection.fail.ControllerKeyCollectionBeanFieldFailTest;
import org.brandao.brutos.annotation.helper.keycollection.fail.ControllerKeyCollectionBeanPropertyFailTest;
import org.brandao.brutos.annotation.helper.keycollection.fail.ControllerKeyCollectionFieldFailTest;
import org.brandao.brutos.annotation.helper.keycollection.fail.ControllerKeyCollectionPropertyFailTest;
import org.brandao.brutos.annotation.helper.keycollection.fail.ControllerKeyCollectionUnknownTypeTest;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.BasicWebApplicationTester;
import org.brandao.brutos.web.test.WebApplicationContextTester;

@SuppressWarnings({"rawtypes","unchecked"})
public class KeyCollectionTest extends TestCase{
    
	private SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy");
	
    public void testControllerKeyCollectionActionTest_propertyAction() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property", 
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
                	parameters.put("arg0.0", "x0");
                	parameters.put("arg0.1", "x1");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> result = controller.getProperty();
                	
                	assertNotNull(result);
                	assertEquals(2, result.size());
                	assertEquals("x0", result.get(0));
                	assertEquals("x1", result.get(1));
                	assertNull(result.get(3));
                }

                public void checkException(Throwable e) {
                	e.printStackTrace();
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }

    public void testControllerKeyCollectionActionTest_property2Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property2", 
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
                	parameters.put("arg0.0", "x0");
                	parameters.put("arg0.1", "x1");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> result = controller.getProperty2();
                	
                	assertNotNull(result);
                	assertEquals(2, result.size());
                	assertEquals("x0", result.get(0));
                	assertEquals("x1", result.get(1));
                	assertNull(result.get(3));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }

    public void testControllerKeyCollectionActionTest_property3Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property3", 
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
                	parameters.put("arg0.elements[0].elx", "0");
                	parameters.put("arg0.elements[1].elx", "1");
                	parameters.put("arg0.elements[3].elx", "3");
                	
                	parameters.put("arg0.elements[0].element", "x0");
                	parameters.put("arg0.elements[1].element", "x1");
                	parameters.put("arg0.elements[3].element", "x3");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> result = controller.getProperty3();
                	
                	assertNotNull(result);
                	assertEquals(2, result.size());
                	assertEquals("x0", result.get(0));
                	assertEquals("x1", result.get(1));
                	assertNull(result.get(3));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }

    public void testControllerKeyCollectionActionTest_propert4Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property4", 
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
                	parameters.put("arg0.VALUE1", "xVALUE1");
                	parameters.put("arg0.VALUE2", "xVALUE2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<EnumTest,String> result = controller.getProperty4();
                	
                	assertNotNull(result);
                	assertEquals(2, result.size());
                	assertEquals("xVALUE1", result.get(EnumTest.VALUE1));
                	assertEquals("xVALUE2", result.get(EnumTest.VALUE2));
                	assertNull(result.get(EnumTest.VALUE3));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }

    public void testControllerKeyCollectionActionTest_property5Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property5", 
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
                	parameters.put("arg0.0", "x0");
                	parameters.put("arg0.1", "x1");
                }

                public void prepareRequest(Map<String, String> parameters) {
                	//parameters.put("arg0.element[0]", "x0");
                	//parameters.put("arg0.element[1]", "x1");
                	//parameters.put("arg0.element[3]", "x3");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> result = controller.getProperty5();
                	
                	assertNotNull(result);
                	assertEquals(2, result.size());
                	assertEquals("x0", result.get(0));
                	assertEquals("x1", result.get(1));
                	assertNull( result.get(3));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }

    public void testControllerKeyCollectionActionTest_property6Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property6", 
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
                	parameters.put("arg0.01-01-2015", "x01-01-2015");
                	parameters.put("arg0.02-01-2015", "x02-01-2015");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<Date,String> result = controller.getProperty6();
                	
                	assertNotNull(result);
                	assertEquals(2, result.size());
                	try{
						assertEquals("x01-01-2015", result.get(sdf.parseObject("01-01-2015")));
	                	assertEquals("x02-01-2015", result.get(sdf.parseObject("02-01-2015")));
	                	assertNull(result.get(sdf.parseObject("03-01-2015")));
					} 
                	catch (ParseException e) {
						throw new RuntimeException(e);
					}
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }
    
    public void testControllerKeyCollectionActionTest_property7Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property7", 
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
                	parameters.put("arg0.0", "x0");
                	parameters.put("arg0.1", "x1");
                }
                
				public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map result = controller.getProperty7();
                	
                	assertNotNull(result);
                	assertEquals(2, result.size());
                	assertEquals("x0", result.get(0));
                	assertEquals("x1", result.get(1));
                	assertNull(result.get(3));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }
    
    public void testControllerKeyCollectionActionTest_property8Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property8", 
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
                	parameters.put("arg0.0", "x0");
                	parameters.put("arg0.1", "x1");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map result = controller.getProperty8();
                	
                	assertNotNull(result);
                	assertEquals(2, result.size());
                	assertEquals("x0", result.get("xx-0"));
                	assertEquals("x1", result.get("xx-1"));
                	assertNull(result.get("xx-3"));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }
    
    public void testControllerKeyCollectionActionTest_property9Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property9", 
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
                	parameters.put("arg0.0", "x0");
                	parameters.put("arg0.1", "x1");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<KeyCollectionBeanTest0,String> result = controller.getProperty9();
                	
                	assertNotNull(result);
                	assertEquals(2, result.size());
                	assertEquals("x0", result.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", result.get(new KeyCollectionBeanTest0("1")));
                	assertNull(result.get(new KeyCollectionBeanTest0("3")));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }        

    public void testControllerKeyCollectionActionTest_property10Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property10", 
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
                	parameters.put("arg0.elements[0].key.property", "0");
                	parameters.put("arg0.elements[1].key.property", "1");
                	
                	parameters.put("arg0.elements[0].element", "x0");
                	parameters.put("arg0.elements[1].element", "x1");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<KeyCollectionBeanTest0,String> result = controller.getProperty10();
                	
                	assertNotNull(result);
                	assertEquals(2, result.size());
                	assertEquals("x0", result.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", result.get(new KeyCollectionBeanTest0("1")));
                	assertNull(result.get(new KeyCollectionBeanTest0("3")));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }        

    public void testControllerKeyCollectionActionTest_property11Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property11", 
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
                	parameters.put("arg0.elements[0].type.property", "0");
                	parameters.put("arg0.elements[1].type.property", "1");
                	
                	parameters.put("arg0.elements[0].element", "x0");
                	parameters.put("arg0.elements[1].element", "x1");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<KeyCollectionBeanTest0,String> result = controller.getProperty11();
                	
                	assertNotNull(result);
                	assertEquals(2, result.size());
                	assertEquals("x0", result.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", result.get(new KeyCollectionBeanTest0("1")));
                	assertNull(result.get(new KeyCollectionBeanTest0("3")));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }        

    public void testControllerKeyCollectionActionTest_property12Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property12", 
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

                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("arg0.0", "x0");
                	parameters.put("arg0.1", "x1");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<EnumTest,String> result = controller.getProperty12();
                	
                	assertNotNull(result);
                	assertEquals(2, result.size());
                	assertEquals("x0", result.get(EnumTest.VALUE1));
                	assertEquals("x1", result.get(EnumTest.VALUE2));
                	assertNull(result.get(EnumTest.VALUE3));
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }        

    public void testControllerKeyCollectionBeanTest() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
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

                public void prepareRequest(Map<String, String> parameters) {
                	
                	parameters.put("property1.property.0", "x0");
                	parameters.put("property1.property.1", "x1");
                	
                	parameters.put("property1.property2.0", "x0");
                	parameters.put("property1.property2.1", "x1");

                	parameters.put("property1.property3.elements[0].elx", "0");
                	parameters.put("property1.property3.elements[1].elx", "1");
                	parameters.put("property1.property3.elements[0].element", "x0");
                	parameters.put("property1.property3.elements[1].element", "x1");

                	parameters.put("property1.property4.VALUE1", "xVALUE1");
                	parameters.put("property1.property4.VALUE2", "xVALUE2");

                	parameters.put("property1.property5.0", "x0");
                	parameters.put("property1.property5.1", "x1");
                	
                	parameters.put("property1.property6.01-01-2015", "x01-01-2015");
                	parameters.put("property1.property6.02-01-2015", "x02-01-2015");

                	parameters.put("property1.property7.0", "x0");
                	parameters.put("property1.property7.1", "x1");

                	parameters.put("property1.property8.0", "x0");
                	parameters.put("property1.property8.1", "x1");

                	parameters.put("property1.property9.0", "x0");
                	parameters.put("property1.property9.1", "x1");

                	parameters.put("property1.property10.elements[0].key.property", "0");
                	parameters.put("property1.property10.elements[1].key.property", "1");
                	parameters.put("property1.property10.elements[0].element", "x0");
                	parameters.put("property1.property10.elements[1].element", "x1");

                	parameters.put("property1.property11.elements[0].key.property", "0");
                	parameters.put("property1.property11.elements[1].key.property", "1");
                	parameters.put("property1.property11.elements[0].element", "x0");
                	parameters.put("property1.property11.elements[1].element", "x1");

                	parameters.put("property1.property12.0", "x0");
                	parameters.put("property1.property12.1", "x1");                  	
                	
                	//Property 2

                	parameters.put("property2.property.0", "x0");
                	parameters.put("property2.property.1", "x1");
                	
                	parameters.put("property2.property2.0", "x0");
                	parameters.put("property2.property2.1", "x1");

                	parameters.put("property2.property3.elements[0].elx", "0");
                	parameters.put("property2.property3.elements[1].elx", "1");
                	parameters.put("property2.property3.elements[0].element", "x0");
                	parameters.put("property2.property3.elements[1].element", "x1");

                	parameters.put("property2.property4.VALUE1", "xVALUE1");
                	parameters.put("property2.property4.VALUE2", "xVALUE2");

                	parameters.put("property2.property5.0", "x0");
                	parameters.put("property2.property5.1", "x1");
                	
                	parameters.put("property2.property6.01-01-2015", "x01-01-2015");
                	parameters.put("property2.property6.02-01-2015", "x02-01-2015");

                	parameters.put("property2.property7.0", "x0");
                	parameters.put("property2.property7.1", "x1");

                	parameters.put("property2.property8.0", "x0");
                	parameters.put("property2.property8.1", "x1");

                	parameters.put("property2.property9.0", "x0");
                	parameters.put("property2.property9.1", "x1");

                	parameters.put("property2.property10.elements[0].key.property", "0");
                	parameters.put("property2.property10.elements[1].key.property", "1");
                	parameters.put("property2.property10.elements[0].element", "x0");
                	parameters.put("property2.property10.elements[1].element", "x1");

                	parameters.put("property2.property11.elements[0].key.property", "0");
                	parameters.put("property2.property11.elements[1].key.property", "1");
                	parameters.put("property2.property11.elements[0].element", "x0");
                	parameters.put("property2.property11.elements[1].element", "x1");

                	parameters.put("property2.property12.0", "x0");
                	parameters.put("property2.property12.1", "x1");                  	
                	                	
                }

                public void prepareSession(Map<String, String> parameters) {
                	parameters.put("property1.property5.0", "");
                	parameters.put("property1.property5.1", "");
                	
                	// Property 2
                	parameters.put("property2.property5.0", "");
                	parameters.put("property2.property5.1", "");
                }
                
				public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionBeanTest controller = 
                			(ControllerKeyCollectionBeanTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> property1Property = controller.getProperty1().property;
                	
                	assertNotNull(property1Property);
                	assertEquals(2, property1Property.size());
                	assertEquals("x0", property1Property.get(0));
                	assertEquals("x1", property1Property.get(1));
                	
                	Map<Integer,String> property1Property2 = controller.getProperty1().property2;
                	
                	assertNotNull(property1Property2);
                	assertEquals(2, property1Property2.size());
                	assertEquals("x0", property1Property2.get(0));
                	assertEquals("x1", property1Property2.get(1));

                	Map<Integer,String> property1Property3 = controller.getProperty1().property3;
                	
                	assertNotNull(property1Property3);
                	assertEquals(2, property1Property3.size());
                	assertEquals("x0", property1Property3.get(0));
                	assertEquals("x1", property1Property3.get(1));

                	Map<EnumTest,String> property1Property4 = controller.getProperty1().property4;
                	
                	assertNotNull(property1Property4);
                	assertEquals(2, property1Property4.size());
                	assertEquals("xVALUE1", property1Property4.get(EnumTest.VALUE1));
                	assertEquals("xVALUE2", property1Property4.get(EnumTest.VALUE2));

                	Map<Integer,String> property1Property5 = controller.getProperty1().property5;
                	
                	assertNotNull(property1Property5);
                	assertEquals(2, property1Property5.size());
                	assertEquals("x0", property1Property5.get(0));
                	assertEquals("x1", property1Property5.get(1));

                	Map<Date,String> property1Property6 = controller.getProperty1().property6;
                	
                	assertNotNull(property1Property6);
                	assertEquals(2, property1Property6.size());
                	
                	try {
						assertEquals("x01-01-2015", property1Property6.get(sdf.parseObject("01-01-2015")));
	                	assertEquals("x02-01-2015", property1Property6.get(sdf.parseObject("02-01-2015")));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	Map<Integer,String> property1Property7 = controller.getProperty1().property7;
                	
                	assertNotNull(property1Property7);
                	assertEquals(2, property1Property7.size());
                	assertEquals("x0", property1Property7.get(0));
                	assertEquals("x1", property1Property7.get(1));

                	Map<String,String> property1Property8 = controller.getProperty1().property8;
                	
                	assertNotNull(property1Property8);
                	assertEquals(2, property1Property8.size());
                	assertEquals("x0", property1Property8.get("xx-0"));
                	assertEquals("x1", property1Property8.get("xx-1"));

                	Map<KeyCollectionBeanTest0,String> property1Property9 = controller.getProperty1().property9;
                	
                	assertNotNull(property1Property9);
                	assertEquals(2, property1Property9.size());
                	assertEquals("x0", property1Property9.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property1Property9.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property1Property10 = controller.getProperty1().property10;
                	
                	assertNotNull(property1Property10);
                	assertEquals(2, property1Property10.size());
                	assertEquals("x0", property1Property10.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property1Property10.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property1Property11 = controller.getProperty1().property11;
                	
                	assertNotNull(property1Property11);
                	assertEquals(2, property1Property11.size());
                	assertEquals("x0", property1Property11.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property1Property11.get(new KeyCollectionBeanTest0("1")));

                	Map<EnumTest,String> property1Property12 = controller.getProperty1().property12;
                	
                	assertNotNull(property1Property12);
                	assertEquals(2, property1Property12.size());
                	assertEquals("x0", property1Property12.get(EnumTest.VALUE1));
                	assertEquals("x1", property1Property12.get(EnumTest.VALUE2));

                	//Property 2
                	
                	Map<Integer,String> property2Property = controller.getProperty2().getProperty();
                	
                	assertNotNull(property2Property);
                	assertEquals(2, property2Property.size());
                	assertEquals("x0", property2Property.get(0));
                	assertEquals("x1", property2Property.get(1));
                	
                	Map<Integer,String> property2Property2 = controller.getProperty2().getProperty2();
                	
                	assertNotNull(property2Property2);
                	assertEquals(2, property2Property2.size());
                	assertEquals("x0", property2Property2.get(0));
                	assertEquals("x1", property2Property2.get(1));

                	Map<Integer,String> property2Property3 = controller.getProperty2().getProperty3();
                	
                	assertNotNull(property2Property3);
                	assertEquals(2, property2Property3.size());
                	assertEquals("x0", property2Property3.get(0));
                	assertEquals("x1", property2Property3.get(1));

                	Map<EnumTest,String> property2Property4 = controller.getProperty2().getProperty4();
                	
                	assertNotNull(property2Property4);
                	assertEquals(2, property2Property4.size());
                	assertEquals("xVALUE1", property2Property4.get(EnumTest.VALUE1));
                	assertEquals("xVALUE2", property2Property4.get(EnumTest.VALUE2));

                	Map<Integer,String> property2Property5 = controller.getProperty2().getProperty5();
                	
                	assertNotNull(property2Property5);
                	assertEquals(2, property2Property5.size());
                	assertEquals("x0", property2Property5.get(0));
                	assertEquals("x1", property2Property5.get(1));

                	Map<Date,String> property2Property6 = controller.getProperty2().getProperty6();
                	
                	assertNotNull(property2Property6);
                	assertEquals(2, property2Property6.size());
                	
                	try {
						assertEquals("x01-01-2015", property2Property6.get(sdf.parseObject("01-01-2015")));
	                	assertEquals("x02-01-2015", property2Property6.get(sdf.parseObject("02-01-2015")));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	Map<Integer,String> property2Property7 = controller.getProperty2().getProperty7();
                	
                	assertNotNull(property2Property7);
                	assertEquals(2, property2Property7.size());
                	assertEquals("x0", property2Property7.get(0));
                	assertEquals("x1", property2Property7.get(1));

                	Map<String,String> property2Property8 = controller.getProperty2().getProperty8();
                	
                	assertNotNull(property2Property8);
                	assertEquals(2, property2Property8.size());
                	assertEquals("x0", property2Property8.get("xx-0"));
                	assertEquals("x1", property2Property8.get("xx-1"));

                	Map<KeyCollectionBeanTest0,String> property2Property9 = controller.getProperty2().getProperty9();
                	
                	assertNotNull(property2Property9);
                	assertEquals(2, property2Property9.size());
                	assertEquals("x0", property2Property9.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property2Property9.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property2Property10 = controller.getProperty2().getProperty10();
                	
                	assertNotNull(property2Property10);
                	assertEquals(2, property2Property10.size());
                	assertEquals("x0", property2Property10.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property2Property10.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property2Property11 = controller.getProperty2().getProperty11();
                	
                	assertNotNull(property2Property11);
                	assertEquals(2, property2Property11.size());
                	assertEquals("x0", property2Property11.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property2Property11.get(new KeyCollectionBeanTest0("1")));

                	Map<EnumTest,String> property2Property12 = controller.getProperty2().getProperty12();
                	
                	assertNotNull(property2Property12);
                	assertEquals(2, property2Property12.size());
                	assertEquals("x0", property2Property12.get(EnumTest.VALUE1));
                	assertEquals("x1", property2Property12.get(EnumTest.VALUE2));
                	
                }

                public void checkException(Throwable e) {
                	e.printStackTrace();
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionBeanTest.class});
    }        

    public void testControllerKeyCollectionConstructorTest() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
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

                public void prepareRequest(Map<String, String> parameters) {
                	
                	parameters.put("property1.arg0.0", "x0");
                	parameters.put("property1.arg0.1", "x1");
                	
                	parameters.put("property2.arg0.0", "x0");
                	parameters.put("property2.arg0.1", "x1");

                	parameters.put("property3.arg0.elements[0].elx", "0");
                	parameters.put("property3.arg0.elements[1].elx", "1");
                	parameters.put("property3.arg0.elements[0].element", "x0");
                	parameters.put("property3.arg0.elements[1].element", "x1");

                	parameters.put("property4.arg0.VALUE1", "xVALUE1");
                	parameters.put("property4.arg0.VALUE2", "xVALUE2");

                	parameters.put("property5.arg0.0", "x0");
                	parameters.put("property5.arg0.1", "x1");
                	
                	parameters.put("property6.arg0.01-01-2015", "x01-01-2015");
                	parameters.put("property6.arg0.02-01-2015", "x02-01-2015");

                	parameters.put("property7.arg0.0", "x0");
                	parameters.put("property7.arg0.1", "x1");

                	parameters.put("property8.arg0.0", "x0");
                	parameters.put("property8.arg0.1", "x1");

                	parameters.put("property9.arg0.0", "x0");
                	parameters.put("property9.arg0.1", "x1");

                	parameters.put("property10.arg0.elements[0].key.property", "0");
                	parameters.put("property10.arg0.elements[1].key.property", "1");
                	parameters.put("property10.arg0.elements[0].element", "x0");
                	parameters.put("property10.arg0.elements[1].element", "x1");

                	parameters.put("property11.arg0.elements[0].key.property", "0");
                	parameters.put("property11.arg0.elements[1].key.property", "1");
                	parameters.put("property11.arg0.elements[0].element", "x0");
                	parameters.put("property11.arg0.elements[1].element", "x1");

                	parameters.put("property12.arg0.0", "x0");
                	parameters.put("property12.arg0.1", "x1");             	
                }

                public void prepareSession(Map<String, String> parameters) {
                	parameters.put("property5.arg0.0", "");
                	parameters.put("property5.arg0.1", "");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionConstructorTest controller = 
                			(ControllerKeyCollectionConstructorTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> property1 = controller.property1.getEntity();
                	
                	assertNotNull(property1);
                	assertEquals(2, property1.size());
                	assertEquals("x0", property1.get(0));
                	assertEquals("x1", property1.get(1));
                	
                	Map<Integer,String> property2 = controller.property2.getEntity();
                	
                	assertNotNull(property2);
                	assertEquals(2, property2.size());
                	assertEquals("x0", property2.get(0));
                	assertEquals("x1", property2.get(1));

                	Map<Integer,String> property3 = controller.property3.getEntity();
                	
                	assertNotNull(property3);
                	assertEquals(2, property3.size());
                	assertEquals("x0", property3.get(0));
                	assertEquals("x1", property3.get(1));

                	Map<EnumTest,String> property4 = controller.property4.getEntity();
                	
                	assertNotNull(property4);
                	assertEquals(2, property4.size());
                	assertEquals("xVALUE1", property4.get(EnumTest.VALUE1));
                	assertEquals("xVALUE2", property4.get(EnumTest.VALUE2));

                	Map<Integer,String> property5 = controller.property5.getEntity();
                	
                	assertNotNull(property5);
                	assertEquals(2, property5.size());
                	assertEquals("x0", property5.get(0));
                	assertEquals("x1", property5.get(1));

                	Map<Date,String> property6 = controller.property6.getEntity();
                	
                	assertNotNull(property6);
                	assertEquals(2, property6.size());
                	
                	try {
						assertEquals("x01-01-2015", property6.get(sdf.parseObject("01-01-2015")));
	                	assertEquals("x02-01-2015", property6.get(sdf.parseObject("02-01-2015")));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	Map property7 = controller.property7.getEntity();
                	
                	assertNotNull(property7);
                	assertEquals(2, property7.size());
                	assertEquals("x0", property7.get(0));
                	assertEquals("x1", property7.get(1));

                	Map property8 = controller.property8.getEntity();
                	
                	assertNotNull(property8);
                	assertEquals(2, property8.size());
                	assertEquals("x0", property8.get("xx-0"));
                	assertEquals("x1", property8.get("xx-1"));

                	Map<KeyCollectionBeanTest0,String> property9 = controller.property9.getEntity();
                	
                	assertNotNull(property9);
                	assertEquals(2, property9.size());
                	assertEquals("x0", property9.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property9.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property10 = controller.property10.getEntity();
                	
                	assertNotNull(property10);
                	assertEquals(2, property10.size());
                	assertEquals("x0", property10.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property10.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property11 = controller.property11.getEntity();
                	
                	assertNotNull(property11);
                	assertEquals(2, property11.size());
                	assertEquals("x0", property11.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property11.get(new KeyCollectionBeanTest0("1")));

                	Map<EnumTest,String> property12 = controller.property12.getEntity();
                	
                	assertNotNull(property12);
                	assertEquals(2, property12.size());
                	assertEquals("x0", property12.get(EnumTest.VALUE1));
                	assertEquals("x1", property12.get(EnumTest.VALUE2));
                	
                }

                public void checkException(Throwable e) {
                	e.printStackTrace();
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionConstructorTest.class});
    }        

    public void testControllerKeyCollectionCustomCollectionTest() throws Throwable{
        WebApplicationContextTester.run(
            "/controller", 
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

                public void prepareRequest(Map<String, String> parameters) {
                	
                	parameters.put("property.elements[0].key.elements[0].subKey", "1");
                	parameters.put("property.elements[0].key.elements[1].subKey", "2");
                	parameters.put("property.elements[0].key.elements[2].subKey", "3");
                	parameters.put("property.elements[0].key.elements[0].element", "x1");
                	parameters.put("property.elements[0].key.elements[1].element", "x2");
                	parameters.put("property.elements[0].key.elements[2].element", "x3");
                	parameters.put("property.elements[0].element", "xx0");

                	parameters.put("property.elements[1].key.elements[0].subKey", "4");
                	parameters.put("property.elements[1].key.elements[1].subKey", "5");
                	parameters.put("property.elements[1].key.elements[2].subKey", "6");
                	parameters.put("property.elements[1].key.elements[0].element", "x4");
                	parameters.put("property.elements[1].key.elements[1].element", "x5");
                	parameters.put("property.elements[1].key.elements[2].element", "x6");
                	parameters.put("property.elements[1].element", "xx1");

                	parameters.put("property.elements[2].key.elements[0].subKey", "7");
                	parameters.put("property.elements[2].key.elements[1].subKey", "8");
                	parameters.put("property.elements[2].key.elements[3].subKey", "9");
                	parameters.put("property.elements[2].key.elements[0].element", "x7");
                	parameters.put("property.elements[2].key.elements[1].element", "x8");
                	parameters.put("property.elements[2].key.elements[3].element", "x9");
                	parameters.put("property.elements[2].element", "xx2");
                	
                	//Property2
                	
                	parameters.put("property2.elements[0].key.10", "x10");
                	parameters.put("property2.elements[0].key.11", "x11");
                	parameters.put("property2.elements[0].key.12", "x12");
                	parameters.put("property2.elements[0].element", "xx3");

                	parameters.put("property2.elements[1].key.13", "x13");
                	parameters.put("property2.elements[1].key.14", "x14");
                	parameters.put("property2.elements[1].key.15", "x15");
                	parameters.put("property2.elements[1].element", "xx4");

                	parameters.put("property2.elements[2].key.16", "x16");
                	parameters.put("property2.elements[2].key.17", "x17");
                	parameters.put("property2.elements[2].element", "xx5");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionCustomCollectionTest controller = 
                			(ControllerKeyCollectionCustomCollectionTest)request.getAttribute("Controller");

                	Map<CustomMap,String> property = controller.property;
                	
                	assertEquals(3, property.size());
                	
                	CustomMap key1 = new CustomMap();
                	key1.put(1, "x1");
                	key1.put(2, "x2");
                	key1.put(3, "x3");

                	CustomMap key2 = new CustomMap();
                	key2.put(4, "x4");
                	key2.put(5, "x5");
                	key2.put(6, "x6");
                	
                	CustomMap key3 = new CustomMap();
                	key3.put(7, "x7");
                	key3.put(8, "x8");
                	
                	assertEquals("xx0", property.get(key1));
                	assertEquals("xx1", property.get(key2));
                	assertEquals("xx2", property.get(key3));

                	Map<Map<Integer,String>,String> property2 = controller.property2;
                	
                	assertEquals(3, property2.size());
                	
                	CustomMap key4 = new CustomMap();
                	key4.put(10, "x10");
                	key4.put(11, "x11");
                	key4.put(12, "x12");

                	CustomMap key5 = new CustomMap();
                	key5.put(13, "x13");
                	key5.put(14, "x14");
                	key5.put(15, "x15");
                	
                	CustomMap key6 = new CustomMap();
                	key6.put(16, "x16");
                	key6.put(17, "x17");
                	
                	assertEquals("xx3", property2.get(key4));
                	assertEquals("xx4", property2.get(key5));
                	assertEquals("xx5", property2.get(key6));
                	
                }

                public void checkException(Throwable e) {
                	e.printStackTrace();
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionCustomCollectionTest.class});
    }        
    
    public void testControllerKeyCollectionFieldTest() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
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

                public void prepareRequest(Map<String, String> parameters) {
                	
                	parameters.put("property.0", "x0");
                	parameters.put("property.1", "x1");
                	
                	parameters.put("property2.0", "x0");
                	parameters.put("property2.1", "x1");

                	parameters.put("property3.elements[0].elx", "0");
                	parameters.put("property3.elements[1].elx", "1");
                	parameters.put("property3.elements[0].element", "x0");
                	parameters.put("property3.elements[1].element", "x1");

                	parameters.put("property4.VALUE1", "xVALUE1");
                	parameters.put("property4.VALUE2", "xVALUE2");

                	parameters.put("property5.0", "x0");
                	parameters.put("property5.1", "x1");
                	
                	parameters.put("property6.01-01-2015", "x01-01-2015");
                	parameters.put("property6.02-01-2015", "x02-01-2015");

                	parameters.put("property7.0", "x0");
                	parameters.put("property7.1", "x1");

                	parameters.put("property8.0", "x0");
                	parameters.put("property8.1", "x1");

                	parameters.put("property9.0", "x0");
                	parameters.put("property9.1", "x1");

                	parameters.put("property10.elements[0].key.property", "0");
                	parameters.put("property10.elements[1].key.property", "1");
                	parameters.put("property10.elements[0].element", "x0");
                	parameters.put("property10.elements[1].element", "x1");

                	parameters.put("property11.elements[0].key.property", "0");
                	parameters.put("property11.elements[1].key.property", "1");
                	parameters.put("property11.elements[0].element", "x0");
                	parameters.put("property11.elements[1].element", "x1");

                	parameters.put("property12.0", "x0");
                	parameters.put("property12.1", "x1");            	
                }

                public void prepareSession(Map<String, String> parameters) {
                	parameters.put("property5.0", "");
                	parameters.put("property5.1", "");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionFieldTest controller = 
                			(ControllerKeyCollectionFieldTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> property1 = controller.property;
                	
                	assertNotNull(property1);
                	assertEquals(2, property1.size());
                	assertEquals("x0", property1.get(0));
                	assertEquals("x1", property1.get(1));
                	
                	Map<Integer,String> property2 = controller.property2;
                	
                	assertNotNull(property2);
                	assertEquals(2, property2.size());
                	assertEquals("x0", property2.get(0));
                	assertEquals("x1", property2.get(1));

                	Map<Integer,String> property3 = controller.property3;
                	
                	assertNotNull(property3);
                	assertEquals(2, property3.size());
                	assertEquals("x0", property3.get(0));
                	assertEquals("x1", property3.get(1));

                	Map<EnumTest,String> property4 = controller.property4;
                	
                	assertNotNull(property4);
                	assertEquals(2, property4.size());
                	assertEquals("xVALUE1", property4.get(EnumTest.VALUE1));
                	assertEquals("xVALUE2", property4.get(EnumTest.VALUE2));

                	Map<Integer,String> property5 = controller.property5;
                	
                	assertNotNull(property5);
                	assertEquals(2, property5.size());
                	assertEquals("x0", property5.get(0));
                	assertEquals("x1", property5.get(1));

                	Map<Date,String> property6 = controller.property6;
                	
                	assertNotNull(property6);
                	assertEquals(2, property6.size());
                	
                	try {
						assertEquals("x01-01-2015", property6.get(sdf.parseObject("01-01-2015")));
	                	assertEquals("x02-01-2015", property6.get(sdf.parseObject("02-01-2015")));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	Map property7 = controller.property7;
                	
                	assertNotNull(property7);
                	assertEquals(2, property7.size());
                	assertEquals("x0", property7.get(0));
                	assertEquals("x1", property7.get(1));

                	Map property8 = controller.property8;
                	
                	assertNotNull(property8);
                	assertEquals(2, property8.size());
                	assertEquals("x0", property8.get("xx-0"));
                	assertEquals("x1", property8.get("xx-1"));

                	Map<KeyCollectionBeanTest0,String> property9 = controller.property9;
                	
                	assertNotNull(property9);
                	assertEquals(2, property9.size());
                	assertEquals("x0", property9.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property9.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property10 = controller.property10;
                	
                	assertNotNull(property10);
                	assertEquals(2, property10.size());
                	assertEquals("x0", property10.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property10.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property11 = controller.property11;
                	
                	assertNotNull(property11);
                	assertEquals(2, property11.size());
                	assertEquals("x0", property11.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property11.get(new KeyCollectionBeanTest0("1")));

                	Map<EnumTest,String> property12 = controller.property12;
                	
                	assertNotNull(property12);
                	assertEquals(2, property12.size());
                	assertEquals("x0", property12.get(EnumTest.VALUE1));
                	assertEquals("x1", property12.get(EnumTest.VALUE2));
                	
                }

                public void checkException(Throwable e) {
                	e.printStackTrace();
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionFieldTest.class});
    }        
    
    public void testControllerKeyCollectionPropertyTest() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test", 
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

                public void prepareRequest(Map<String, String> parameters) {
                	
                	parameters.put("property.0", "x0");
                	parameters.put("property.1", "x1");
                	
                	parameters.put("property2.0", "x0");
                	parameters.put("property2.1", "x1");

                	parameters.put("property3.elements[0].elx", "0");
                	parameters.put("property3.elements[1].elx", "1");
                	parameters.put("property3.elements[0].element", "x0");
                	parameters.put("property3.elements[1].element", "x1");

                	parameters.put("property4.VALUE1", "xVALUE1");
                	parameters.put("property4.VALUE2", "xVALUE2");

                	parameters.put("property5.0", "x0");
                	parameters.put("property5.1", "x1");
                	
                	parameters.put("property6.01-01-2015", "x01-01-2015");
                	parameters.put("property6.02-01-2015", "x02-01-2015");

                	parameters.put("property7.0", "x0");
                	parameters.put("property7.1", "x1");

                	parameters.put("property8.0", "x0");
                	parameters.put("property8.1", "x1");

                	parameters.put("property9.0", "x0");
                	parameters.put("property9.1", "x1");

                	parameters.put("property10.elements[0].key.property", "0");
                	parameters.put("property10.elements[1].key.property", "1");
                	parameters.put("property10.elements[0].element", "x0");
                	parameters.put("property10.elements[1].element", "x1");

                	parameters.put("property11.elements[0].key.property", "0");
                	parameters.put("property11.elements[1].key.property", "1");
                	parameters.put("property11.elements[0].element", "x0");
                	parameters.put("property11.elements[1].element", "x1");

                	parameters.put("property12.0", "x0");
                	parameters.put("property12.1", "x1");            	
          	
                }

                public void prepareSession(Map<String, String> parameters) {
                	parameters.put("property5.0", "");
                	parameters.put("property5.1", "");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionPropertyTest controller = 
                			(ControllerKeyCollectionPropertyTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> property1 = controller.getProperty();
                	
                	assertNotNull(property1);
                	assertEquals(2, property1.size());
                	assertEquals("x0", property1.get(0));
                	assertEquals("x1", property1.get(1));
                	
                	Map<Integer,String> property2 = controller.getProperty2();
                	
                	assertNotNull(property2);
                	assertEquals(2, property2.size());
                	assertEquals("x0", property2.get(0));
                	assertEquals("x1", property2.get(1));

                	Map<Integer,String> property3 = controller.getProperty3();
                	
                	assertNotNull(property3);
                	assertEquals(2, property3.size());
                	assertEquals("x0", property3.get(0));
                	assertEquals("x1", property3.get(1));

                	Map<EnumTest,String> property4 = controller.getProperty4();
                	
                	assertNotNull(property4);
                	assertEquals(2, property4.size());
                	assertEquals("xVALUE1", property4.get(EnumTest.VALUE1));
                	assertEquals("xVALUE2", property4.get(EnumTest.VALUE2));

                	Map<Integer,String> property5 = controller.getProperty5();
                	
                	assertNotNull(property5);
                	assertEquals(2, property5.size());
                	assertEquals("x0", property5.get(0));
                	assertEquals("x1", property5.get(1));

                	Map<Date,String> property6 = controller.getProperty6();
                	
                	assertNotNull(property6);
                	assertEquals(2, property6.size());
                	
                	try {
						assertEquals("x01-01-2015", property6.get(sdf.parseObject("01-01-2015")));
	                	assertEquals("x02-01-2015", property6.get(sdf.parseObject("02-01-2015")));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	Map property7 = controller.getProperty7();
                	
                	assertNotNull(property7);
                	assertEquals(2, property7.size());
                	assertEquals("x0", property7.get(0));
                	assertEquals("x1", property7.get(1));

                	Map property8 = controller.getProperty8();
                	
                	assertNotNull(property8);
                	assertEquals(2, property8.size());
                	assertEquals("x0", property8.get("xx-0"));
                	assertEquals("x1", property8.get("xx-1"));

                	Map<KeyCollectionBeanTest0,String> property9 = controller.getProperty9();
                	
                	assertNotNull(property9);
                	assertEquals(2, property9.size());
                	assertEquals("x0", property9.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property9.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property10 = controller.getProperty10();
                	
                	assertNotNull(property10);
                	assertEquals(2, property10.size());
                	assertEquals("x0", property10.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property10.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property11 = controller.getProperty11();
                	
                	assertNotNull(property11);
                	assertEquals(2, property11.size());
                	assertEquals("x0", property11.get(new KeyCollectionBeanTest0("0")));
                	assertEquals("x1", property11.get(new KeyCollectionBeanTest0("1")));

                	Map<EnumTest,String> property12 = controller.getProperty12();
                	
                	assertNotNull(property12);
                	assertEquals(2, property12.size());
                	assertEquals("x0", property12.get(EnumTest.VALUE1));
                	assertEquals("x1", property12.get(EnumTest.VALUE2));
                }

                public void checkException(Throwable e) {
                	e.printStackTrace();
                    fail(e.toString());
                }
            },
            new Class[]{ControllerKeyCollectionPropertyTest.class});
    }
    
    public void testControllerKeyCollectionBeanConstructorFailTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	fail("expected: {unknown key type}");
                }

                public void checkException(Throwable e) {
                	
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("unknown key type"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    fail("expected: {unknown key type}");
                }
            },
            new Class[]{ControllerKeyCollectionBeanConstructorFailTest.class});
    }           

    public void testControllerKeyCollectionBeanFieldFailTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	fail("expected: {unknown key type}");
                }

                public void checkException(Throwable e) {
                	
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("unknown key type"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    fail("expected: {unknown key type}");
                }
            },
            new Class[]{ControllerKeyCollectionBeanFieldFailTest.class});
    }           

    public void testControllerKeyCollectionBeanPropertyFailTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	fail("expected: {unknown key type}");
                }

                public void checkException(Throwable e) {
                	
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("unknown key type"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    fail("expected: {unknown key type}");
                }
            },
            new Class[]{ControllerKeyCollectionBeanPropertyFailTest.class});
    }           

    public void testControllerKeyCollectionFieldFailTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	fail("expected: {unknown key type}");
                }

                public void checkException(Throwable e) {
                	
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("unknown key type"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    fail("expected: {unknown key type}");
                }
            },
            new Class[]{ControllerKeyCollectionFieldFailTest.class});
    }           

    public void testControllerKeyCollectionPropertyFailTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	fail("expected: {unknown key type}");
                }

                public void checkException(Throwable e) {
                	
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("unknown key type"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    fail("expected: {unknown key type}");
                }
            },
            new Class[]{ControllerKeyCollectionPropertyFailTest.class});
    }           

    //Não aplicável
    public void testControllerKeyCollectionUnknownTypeTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    //fail("expected: {unknown type: KeyCollectionBeanTest0}");
                }

                public void checkException(Throwable e) {
                	
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("unknown type: KeyCollectionBeanTest0"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    fail("expected: {unknown type: KeyCollectionBeanTest0}");
                }
            },
            new Class[]{ControllerKeyCollectionUnknownTypeTest.class});
    }           
    
}
