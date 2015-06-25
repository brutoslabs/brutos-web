package org.brandao.brutos.annotation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
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
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class KeyCollectionTest extends TestCase{
    
	private SimpleDateFormat sdf = new SimpleDateFormat("mm-dd-yyyy");
	
    public void testControllerKeyCollectionActionTest_propertyAction() throws Throwable{
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
                	parameters.put("arg0.key[0]", "0");
                	parameters.put("arg0.key[1]", "1");
                	parameters.put("arg0.key[3]", "3");
                	
                	parameters.put("arg0.element[0]", "x0");
                	parameters.put("arg0.element[1]", "x1");
                	parameters.put("arg0.element[3]", "x3");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> result = controller.getProperty();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("x0", result.get(0));
                	Assert.assertEquals("x1", result.get(1));
                	Assert.assertNull(result.get(3));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }

    public void testControllerKeyCollectionActionTest_property2Action() throws Throwable{
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
                	parameters.put("arg0.key[0]", "0");
                	parameters.put("arg0.key[1]", "1");
                	parameters.put("arg0.key[3]", "3");
                	
                	parameters.put("arg0.element[0]", "x0");
                	parameters.put("arg0.element[1]", "x1");
                	parameters.put("arg0.element[3]", "x3");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> result = controller.getProperty2();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("x0", result.get(0));
                	Assert.assertEquals("x1", result.get(1));
                	Assert.assertNull(result.get(3));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }

    public void testControllerKeyCollectionActionTest_property3Action() throws Throwable{
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
                	
                	parameters.put("arg0.element[0]", "x0");
                	parameters.put("arg0.element[1]", "x1");
                	parameters.put("arg0.element[3]", "x3");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> result = controller.getProperty3();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("x0", result.get(0));
                	Assert.assertEquals("x1", result.get(1));
                	Assert.assertNull(result.get(3));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }

    public void testControllerKeyCollectionActionTest_propert4Action() throws Throwable{
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
                	parameters.put("arg0.key[0]", "VALUE1");
                	parameters.put("arg0.key[1]", "VALUE2");
                	parameters.put("arg0.key[3]", "VALUE3");
                	
                	parameters.put("arg0.element[0]", "xVALUE1");
                	parameters.put("arg0.element[1]", "xVALUE2");
                	parameters.put("arg0.element[3]", "xVALUE3");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<EnumTest,String> result = controller.getProperty4();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("xVALUE1", result.get(EnumTest.VALUE1));
                	Assert.assertEquals("xVALUE2", result.get(EnumTest.VALUE2));
                	Assert.assertNull(result.get(EnumTest.VALUE3));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }

    public void testControllerKeyCollectionActionTest_property5Action() throws Throwable{
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
                	parameters.put("arg0.key[0]", "0");
                	parameters.put("arg0.key[1]", "1");
                	parameters.put("arg0.key[3]", "3");
                }

                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("arg0.element[0]", "x0");
                	parameters.put("arg0.element[1]", "x1");
                	parameters.put("arg0.element[3]", "x3");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> result = controller.getProperty5();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("x0", result.get(0));
                	Assert.assertEquals("x1", result.get(1));
                	Assert.assertNull( result.get(3));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }

    public void testControllerKeyCollectionActionTest_property6Action() throws Throwable{
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
                	parameters.put("arg0.key[0]", "01-01-2015");
                	parameters.put("arg0.key[1]", "02-01-2015");
                	parameters.put("arg0.key[3]", "03-01-2015");
                	
                	parameters.put("arg0.element[0]", "x01-01-2015");
                	parameters.put("arg0.element[1]", "x02-01-2015");
                	parameters.put("arg0.element[3]", "x03-01-2015");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<Date,String> result = controller.getProperty6();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	try{
						Assert.assertEquals("x01-01-2015", result.get(sdf.parseObject("01-01-2015")));
	                	Assert.assertEquals("x02-01-2015", result.get(sdf.parseObject("02-01-2015")));
	                	Assert.assertNull(result.get(sdf.parseObject("03-01-2015")));
					} 
                	catch (ParseException e) {
						throw new RuntimeException(e);
					}
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }
    
    public void testControllerKeyCollectionActionTest_property7Action() throws Throwable{
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
                	parameters.put("arg0.key[0]", "0");
                	parameters.put("arg0.key[1]", "1");
                	parameters.put("arg0.key[3]", "3");
                	
                	parameters.put("arg0.element[0]", "x0");
                	parameters.put("arg0.element[1]", "x1");
                	parameters.put("arg0.element[3]", "x3");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map result = controller.getProperty7();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("x0", result.get(0));
                	Assert.assertEquals("x1", result.get(1));
                	Assert.assertNull(result.get(3));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }
    
    public void testControllerKeyCollectionActionTest_property8Action() throws Throwable{
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
                	parameters.put("arg0.key[0]", "0");
                	parameters.put("arg0.key[1]", "1");
                	parameters.put("arg0.key[3]", "3");
                	
                	parameters.put("arg0.element[0]", "x0");
                	parameters.put("arg0.element[1]", "x1");
                	parameters.put("arg0.element[3]", "x3");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map result = controller.getProperty8();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("x0", result.get("xx-0"));
                	Assert.assertEquals("x1", result.get("xx-1"));
                	Assert.assertNull(result.get("xx-3"));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }
    
    public void testControllerKeyCollectionActionTest_property9Action() throws Throwable{
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
                	parameters.put("arg0.key[0]", "0");
                	parameters.put("arg0.key[1]", "1");
                	parameters.put("arg0.key[3]", "3");
                	
                	parameters.put("arg0.element[0]", "x0");
                	parameters.put("arg0.element[1]", "x1");
                	parameters.put("arg0.element[3]", "x3");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<KeyCollectionBeanTest0,String> result = controller.getProperty9();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("x0", result.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", result.get(new KeyCollectionBeanTest0("1")));
                	Assert.assertNull(result.get(new KeyCollectionBeanTest0("3")));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }        

    public void testControllerKeyCollectionActionTest_property10Action() throws Throwable{
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
                	parameters.put("arg0.key[0].property", "0");
                	parameters.put("arg0.key[1].property", "1");
                	parameters.put("arg0.key[3].property", "3");
                	
                	parameters.put("arg0.element[0]", "x0");
                	parameters.put("arg0.element[1]", "x1");
                	parameters.put("arg0.element[3]", "x3");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<KeyCollectionBeanTest0,String> result = controller.getProperty10();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("x0", result.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", result.get(new KeyCollectionBeanTest0("1")));
                	Assert.assertNull(result.get(new KeyCollectionBeanTest0("3")));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }        

    public void testControllerKeyCollectionActionTest_property11Action() throws Throwable{
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
                	parameters.put("arg0.key[0].property", "0");
                	parameters.put("arg0.key[1].property", "1");
                	parameters.put("arg0.key[3].property", "3");
                	
                	parameters.put("arg0.element[0]", "x0");
                	parameters.put("arg0.element[1]", "x1");
                	parameters.put("arg0.element[3]", "x3");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<KeyCollectionBeanTest0,String> result = controller.getProperty11();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("x0", result.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", result.get(new KeyCollectionBeanTest0("1")));
                	Assert.assertNull(result.get(new KeyCollectionBeanTest0("3")));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }        

    public void testControllerKeyCollectionActionTest_property12Action() throws Throwable{
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
                	parameters.put("arg0.key[0]", "0");
                	parameters.put("arg0.key[1]", "1");
                	parameters.put("arg0.key[3]", "3");
                	
                	parameters.put("arg0.element[0]", "x0");
                	parameters.put("arg0.element[1]", "x1");
                	parameters.put("arg0.element[3]", "x3");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionActionTest controller = 
                			(ControllerKeyCollectionActionTest)request.getAttribute("Controller");
                	
                	Map<EnumTest,String> result = controller.getProperty12();
                	
                	Assert.assertNotNull(result);
                	Assert.assertEquals(2, result.size());
                	Assert.assertEquals("x0", result.get(EnumTest.VALUE1));
                	Assert.assertEquals("x1", result.get(EnumTest.VALUE2));
                	Assert.assertNull(result.get(EnumTest.VALUE3));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionActionTest.class});
    }        

    public void testControllerKeyCollectionBeanTest() throws Throwable{
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
                	
                	parameters.put("property1.property.key[0]", "0");
                	parameters.put("property1.property.key[1]", "1");
                	parameters.put("property1.property.element[0]", "x0");
                	parameters.put("property1.property.element[1]", "x1");
                	
                	parameters.put("property1.property2.key[0]", "0");
                	parameters.put("property1.property2.key[1]", "1");
                	parameters.put("property1.property2.element[0]", "x0");
                	parameters.put("property1.property2.element[1]", "x1");

                	parameters.put("property1.property3.elx[0]", "0");
                	parameters.put("property1.property3.elx[1]", "1");
                	parameters.put("property1.property3.element[0]", "x0");
                	parameters.put("property1.property3.element[1]", "x1");

                	parameters.put("property1.property4.key[0]", "VALUE1");
                	parameters.put("property1.property4.key[1]", "VALUE2");
                	parameters.put("property1.property4.element[0]", "xVALUE1");
                	parameters.put("property1.property4.element[1]", "xVALUE2");

                	parameters.put("property1.property5.element[0]", "x0");
                	parameters.put("property1.property5.element[1]", "x1");
                	
                	parameters.put("property1.property6.key[0]", "01-01-2015");
                	parameters.put("property1.property6.key[1]", "02-01-2015");
                	parameters.put("property1.property6.element[0]", "x01-01-2015");
                	parameters.put("property1.property6.element[1]", "x02-01-2015");

                	parameters.put("property1.property7.key[0]", "0");
                	parameters.put("property1.property7.key[1]", "1");
                	parameters.put("property1.property7.element[0]", "x0");
                	parameters.put("property1.property7.element[1]", "x1");

                	parameters.put("property1.property8.key[0]", "0");
                	parameters.put("property1.property8.key[1]", "1");
                	parameters.put("property1.property8.element[0]", "x0");
                	parameters.put("property1.property8.element[1]", "x1");

                	parameters.put("property1.property9.key[0]", "0");
                	parameters.put("property1.property9.key[1]", "1");
                	parameters.put("property1.property9.element[0]", "x0");
                	parameters.put("property1.property9.element[1]", "x1");

                	parameters.put("property1.property10.key[0].property", "0");
                	parameters.put("property1.property10.key[1].property", "1");
                	parameters.put("property1.property10.element[0]", "x0");
                	parameters.put("property1.property10.element[1]", "x1");

                	parameters.put("property1.property10.key[0].property", "0");
                	parameters.put("property1.property10.key[1].property", "1");
                	parameters.put("property1.property10.element[0]", "x0");
                	parameters.put("property1.property10.element[1]", "x1");

                	parameters.put("property1.property11.key[0].property", "0");
                	parameters.put("property1.property11.key[1].property", "1");
                	parameters.put("property1.property11.element[0]", "x0");
                	parameters.put("property1.property11.element[1]", "x1");
                	
                	parameters.put("property1.property12.key[0]", "0");
                	parameters.put("property1.property12.key[1]", "1");
                	parameters.put("property1.property12.element[0]", "x0");
                	parameters.put("property1.property12.element[1]", "x1");
                	
                	//Property 2
                	
                	parameters.put("property2.property.key[0]", "0");
                	parameters.put("property2.property.key[1]", "1");
                	parameters.put("property2.property.element[0]", "x0");
                	parameters.put("property2.property.element[1]", "x1");
                	
                	parameters.put("property2.property2.key[0]", "0");
                	parameters.put("property2.property2.key[1]", "1");
                	parameters.put("property2.property2.element[0]", "x0");
                	parameters.put("property2.property2.element[1]", "x1");

                	parameters.put("property2.property3.elx[0]", "0");
                	parameters.put("property2.property3.elx[1]", "1");
                	parameters.put("property2.property3.element[0]", "x0");
                	parameters.put("property2.property3.element[1]", "x1");

                	parameters.put("property2.property4.key[0]", "VALUE1");
                	parameters.put("property2.property4.key[1]", "VALUE2");
                	parameters.put("property2.property4.element[0]", "xVALUE1");
                	parameters.put("property2.property4.element[1]", "xVALUE2");

                	parameters.put("property2.property5.element[0]", "x0");
                	parameters.put("property2.property5.element[1]", "x1");
                	
                	parameters.put("property2.property6.key[0]", "01-01-2015");
                	parameters.put("property2.property6.key[1]", "02-01-2015");
                	parameters.put("property2.property6.element[0]", "x01-01-2015");
                	parameters.put("property2.property6.element[1]", "x02-01-2015");

                	parameters.put("property2.property7.key[0]", "0");
                	parameters.put("property2.property7.key[1]", "1");
                	parameters.put("property2.property7.element[0]", "x0");
                	parameters.put("property2.property7.element[1]", "x1");

                	parameters.put("property2.property8.key[0]", "0");
                	parameters.put("property2.property8.key[1]", "1");
                	parameters.put("property2.property8.element[0]", "x0");
                	parameters.put("property2.property8.element[1]", "x1");

                	parameters.put("property2.property9.key[0]", "0");
                	parameters.put("property2.property9.key[1]", "1");
                	parameters.put("property2.property9.element[0]", "x0");
                	parameters.put("property2.property9.element[1]", "x1");

                	parameters.put("property2.property10.key[0].property", "0");
                	parameters.put("property2.property10.key[1].property", "1");
                	parameters.put("property2.property10.element[0]", "x0");
                	parameters.put("property2.property10.element[1]", "x1");

                	parameters.put("property2.property10.key[0].property", "0");
                	parameters.put("property2.property10.key[1].property", "1");
                	parameters.put("property2.property10.element[0]", "x0");
                	parameters.put("property2.property10.element[1]", "x1");

                	parameters.put("property2.property11.key[0].property", "0");
                	parameters.put("property2.property11.key[1].property", "1");
                	parameters.put("property2.property11.element[0]", "x0");
                	parameters.put("property2.property11.element[1]", "x1");
                	
                	parameters.put("property2.property12.key[0]", "0");
                	parameters.put("property2.property12.key[1]", "1");                	
                	parameters.put("property2.property12.element[0]", "x0");
                	parameters.put("property2.property12.element[1]", "x1");                	
                }

                public void prepareSession(Map<String, String> parameters) {
                	parameters.put("property1.property5.key[0]", "0");
                	parameters.put("property1.property5.key[1]", "1");
                	
                	// Property 2
                	parameters.put("property2.property5.key[0]", "0");
                	parameters.put("property2.property5.key[1]", "1");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionBeanTest controller = 
                			(ControllerKeyCollectionBeanTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> property1Property = controller.getProperty1().property;
                	
                	Assert.assertNotNull(property1Property);
                	Assert.assertEquals(2, property1Property.size());
                	Assert.assertEquals("x0", property1Property.get(0));
                	Assert.assertEquals("x1", property1Property.get(1));
                	
                	Map<Integer,String> property1Property2 = controller.getProperty1().property2;
                	
                	Assert.assertNotNull(property1Property2);
                	Assert.assertEquals(2, property1Property2.size());
                	Assert.assertEquals("x0", property1Property2.get(0));
                	Assert.assertEquals("x1", property1Property2.get(1));

                	Map<Integer,String> property1Property3 = controller.getProperty1().property3;
                	
                	Assert.assertNotNull(property1Property3);
                	Assert.assertEquals(2, property1Property3.size());
                	Assert.assertEquals("x0", property1Property3.get(0));
                	Assert.assertEquals("x1", property1Property3.get(1));

                	Map<EnumTest,String> property1Property4 = controller.getProperty1().property4;
                	
                	Assert.assertNotNull(property1Property4);
                	Assert.assertEquals(2, property1Property4.size());
                	Assert.assertEquals("xVALUE1", property1Property4.get(EnumTest.VALUE1));
                	Assert.assertEquals("xVALUE2", property1Property4.get(EnumTest.VALUE2));

                	Map<Integer,String> property1Property5 = controller.getProperty1().property5;
                	
                	Assert.assertNotNull(property1Property5);
                	Assert.assertEquals(2, property1Property5.size());
                	Assert.assertEquals("x0", property1Property5.get(0));
                	Assert.assertEquals("x1", property1Property5.get(1));

                	Map<Date,String> property1Property6 = controller.getProperty1().property6;
                	
                	Assert.assertNotNull(property1Property6);
                	Assert.assertEquals(2, property1Property6.size());
                	
                	try {
						Assert.assertEquals("x01-01-2015", property1Property6.get(sdf.parseObject("01-01-2015")));
	                	Assert.assertEquals("x02-01-2015", property1Property6.get(sdf.parseObject("02-01-2015")));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	Map<Integer,String> property1Property7 = controller.getProperty1().property7;
                	
                	Assert.assertNotNull(property1Property7);
                	Assert.assertEquals(2, property1Property7.size());
                	Assert.assertEquals("x0", property1Property7.get(0));
                	Assert.assertEquals("x1", property1Property7.get(1));

                	Map<String,String> property1Property8 = controller.getProperty1().property8;
                	
                	Assert.assertNotNull(property1Property8);
                	Assert.assertEquals(2, property1Property8.size());
                	Assert.assertEquals("x0", property1Property8.get("xx-0"));
                	Assert.assertEquals("x1", property1Property8.get("xx-1"));

                	Map<KeyCollectionBeanTest0,String> property1Property9 = controller.getProperty1().property9;
                	
                	Assert.assertNotNull(property1Property9);
                	Assert.assertEquals(2, property1Property9.size());
                	Assert.assertEquals("x0", property1Property9.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property1Property9.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property1Property10 = controller.getProperty1().property10;
                	
                	Assert.assertNotNull(property1Property10);
                	Assert.assertEquals(2, property1Property10.size());
                	Assert.assertEquals("x0", property1Property10.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property1Property10.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property1Property11 = controller.getProperty1().property11;
                	
                	Assert.assertNotNull(property1Property11);
                	Assert.assertEquals(2, property1Property11.size());
                	Assert.assertEquals("x0", property1Property11.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property1Property11.get(new KeyCollectionBeanTest0("1")));

                	Map<EnumTest,String> property1Property12 = controller.getProperty1().property12;
                	
                	Assert.assertNotNull(property1Property12);
                	Assert.assertEquals(2, property1Property12.size());
                	Assert.assertEquals("x0", property1Property12.get(EnumTest.VALUE1));
                	Assert.assertEquals("x1", property1Property12.get(EnumTest.VALUE2));

                	//Property 2
                	
                	Map<Integer,String> property2Property = controller.getProperty2().getProperty();
                	
                	Assert.assertNotNull(property2Property);
                	Assert.assertEquals(2, property2Property.size());
                	Assert.assertEquals("x0", property2Property.get(0));
                	Assert.assertEquals("x1", property2Property.get(1));
                	
                	Map<Integer,String> property2Property2 = controller.getProperty2().getProperty2();
                	
                	Assert.assertNotNull(property2Property2);
                	Assert.assertEquals(2, property2Property2.size());
                	Assert.assertEquals("x0", property2Property2.get(0));
                	Assert.assertEquals("x1", property2Property2.get(1));

                	Map<Integer,String> property2Property3 = controller.getProperty2().getProperty3();
                	
                	Assert.assertNotNull(property2Property3);
                	Assert.assertEquals(2, property2Property3.size());
                	Assert.assertEquals("x0", property2Property3.get(0));
                	Assert.assertEquals("x1", property2Property3.get(1));

                	Map<EnumTest,String> property2Property4 = controller.getProperty2().getProperty4();
                	
                	Assert.assertNotNull(property2Property4);
                	Assert.assertEquals(2, property2Property4.size());
                	Assert.assertEquals("xVALUE1", property2Property4.get(EnumTest.VALUE1));
                	Assert.assertEquals("xVALUE2", property2Property4.get(EnumTest.VALUE2));

                	Map<Integer,String> property2Property5 = controller.getProperty2().getProperty5();
                	
                	Assert.assertNotNull(property2Property5);
                	Assert.assertEquals(2, property2Property5.size());
                	Assert.assertEquals("x0", property2Property5.get(0));
                	Assert.assertEquals("x1", property2Property5.get(1));

                	Map<Date,String> property2Property6 = controller.getProperty2().getProperty6();
                	
                	Assert.assertNotNull(property2Property6);
                	Assert.assertEquals(2, property2Property6.size());
                	
                	try {
						Assert.assertEquals("x01-01-2015", property2Property6.get(sdf.parseObject("01-01-2015")));
	                	Assert.assertEquals("x02-01-2015", property2Property6.get(sdf.parseObject("02-01-2015")));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	Map<Integer,String> property2Property7 = controller.getProperty2().getProperty7();
                	
                	Assert.assertNotNull(property2Property7);
                	Assert.assertEquals(2, property2Property7.size());
                	Assert.assertEquals("x0", property2Property7.get(0));
                	Assert.assertEquals("x1", property2Property7.get(1));

                	Map<String,String> property2Property8 = controller.getProperty2().getProperty8();
                	
                	Assert.assertNotNull(property2Property8);
                	Assert.assertEquals(2, property2Property8.size());
                	Assert.assertEquals("x0", property2Property8.get("xx-0"));
                	Assert.assertEquals("x1", property2Property8.get("xx-1"));

                	Map<KeyCollectionBeanTest0,String> property2Property9 = controller.getProperty2().getProperty9();
                	
                	Assert.assertNotNull(property2Property9);
                	Assert.assertEquals(2, property2Property9.size());
                	Assert.assertEquals("x0", property2Property9.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property2Property9.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property2Property10 = controller.getProperty2().getProperty10();
                	
                	Assert.assertNotNull(property2Property10);
                	Assert.assertEquals(2, property2Property10.size());
                	Assert.assertEquals("x0", property2Property10.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property2Property10.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property2Property11 = controller.getProperty2().getProperty11();
                	
                	Assert.assertNotNull(property2Property11);
                	Assert.assertEquals(2, property2Property11.size());
                	Assert.assertEquals("x0", property2Property11.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property2Property11.get(new KeyCollectionBeanTest0("1")));

                	Map<EnumTest,String> property2Property12 = controller.getProperty2().getProperty12();
                	
                	Assert.assertNotNull(property2Property12);
                	Assert.assertEquals(2, property2Property12.size());
                	Assert.assertEquals("x0", property2Property12.get(EnumTest.VALUE1));
                	Assert.assertEquals("x1", property2Property12.get(EnumTest.VALUE2));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionBeanTest.class});
    }        

    public void testControllerKeyCollectionConstructorTest() throws Throwable{
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
                	
                	parameters.put("property1.arg0.key[0]", "0");
                	parameters.put("property1.arg0.key[1]", "1");
                	parameters.put("property1.arg0.element[0]", "x0");
                	parameters.put("property1.arg0.element[1]", "x1");
                	
                	parameters.put("property2.arg0.key[0]", "0");
                	parameters.put("property2.arg0.key[1]", "1");
                	parameters.put("property2.arg0.element[0]", "x0");
                	parameters.put("property2.arg0.element[1]", "x1");

                	parameters.put("property3.arg0.elx[0]", "0");
                	parameters.put("property3.arg0.elx[1]", "1");
                	parameters.put("property3.arg0.element[0]", "x0");
                	parameters.put("property3.arg0.element[1]", "x1");

                	parameters.put("property4.arg0.key[0]", "VALUE1");
                	parameters.put("property4.arg0.key[1]", "VALUE2");
                	parameters.put("property4.arg0.element[0]", "xVALUE1");
                	parameters.put("property4.arg0.element[1]", "xVALUE2");

                	parameters.put("property5.arg0.element[0]", "x0");
                	parameters.put("property5.arg0.element[1]", "x1");
                	
                	parameters.put("property6.arg0.key[0]", "01-01-2015");
                	parameters.put("property6.arg0.key[1]", "02-01-2015");
                	parameters.put("property6.arg0.element[0]", "x01-01-2015");
                	parameters.put("property6.arg0.element[1]", "x02-01-2015");

                	parameters.put("property7.arg0.key[0]", "0");
                	parameters.put("property7.arg0.key[1]", "1");
                	parameters.put("property7.arg0.element[0]", "x0");
                	parameters.put("property7.arg0.element[1]", "x1");

                	parameters.put("property8.arg0.key[0]", "0");
                	parameters.put("property8.arg0.key[1]", "1");
                	parameters.put("property8.arg0.element[0]", "x0");
                	parameters.put("property8.arg0.element[1]", "x1");

                	parameters.put("property9.arg0.key[0]", "0");
                	parameters.put("property9.arg0.key[1]", "1");
                	parameters.put("property9.arg0.element[0]", "x0");
                	parameters.put("property9.arg0.element[1]", "x1");

                	parameters.put("property10.arg0.key[0].property", "0");
                	parameters.put("property10.arg0.key[1].property", "1");
                	parameters.put("property10.arg0.element[0]", "x0");
                	parameters.put("property10.arg0.element[1]", "x1");

                	parameters.put("property11.arg0.key[0].property", "0");
                	parameters.put("property11.arg0.key[1].property", "1");
                	parameters.put("property11.arg0.element[0]", "x0");
                	parameters.put("property11.arg0.element[1]", "x1");

                	parameters.put("property12.arg0.key[0]", "0");
                	parameters.put("property12.arg0.key[1]", "1");            	
                	parameters.put("property12.arg0.element[0]", "x0");
                	parameters.put("property12.arg0.element[1]", "x1");            	
                }

                public void prepareSession(Map<String, String> parameters) {
                	parameters.put("property5.arg0.key[0]", "0");
                	parameters.put("property5.arg0.key[1]", "1");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionConstructorTest controller = 
                			(ControllerKeyCollectionConstructorTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> property1 = controller.property1.getEntity();
                	
                	Assert.assertNotNull(property1);
                	Assert.assertEquals(2, property1.size());
                	Assert.assertEquals("x0", property1.get(0));
                	Assert.assertEquals("x1", property1.get(1));
                	
                	Map<Integer,String> property2 = controller.property2.getEntity();
                	
                	Assert.assertNotNull(property2);
                	Assert.assertEquals(2, property2.size());
                	Assert.assertEquals("x0", property2.get(0));
                	Assert.assertEquals("x1", property2.get(1));

                	Map<Integer,String> property3 = controller.property3.getEntity();
                	
                	Assert.assertNotNull(property3);
                	Assert.assertEquals(2, property3.size());
                	Assert.assertEquals("x0", property3.get(0));
                	Assert.assertEquals("x1", property3.get(1));

                	Map<EnumTest,String> property4 = controller.property4.getEntity();
                	
                	Assert.assertNotNull(property4);
                	Assert.assertEquals(2, property4.size());
                	Assert.assertEquals("xVALUE1", property4.get(EnumTest.VALUE1));
                	Assert.assertEquals("xVALUE2", property4.get(EnumTest.VALUE2));

                	Map<Integer,String> property5 = controller.property5.getEntity();
                	
                	Assert.assertNotNull(property5);
                	Assert.assertEquals(2, property5.size());
                	Assert.assertEquals("x0", property5.get(0));
                	Assert.assertEquals("x1", property5.get(1));

                	Map<Date,String> property6 = controller.property6.getEntity();
                	
                	Assert.assertNotNull(property6);
                	Assert.assertEquals(2, property6.size());
                	
                	try {
						Assert.assertEquals("x01-01-2015", property6.get(sdf.parseObject("01-01-2015")));
	                	Assert.assertEquals("x02-01-2015", property6.get(sdf.parseObject("02-01-2015")));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	Map property7 = controller.property7.getEntity();
                	
                	Assert.assertNotNull(property7);
                	Assert.assertEquals(2, property7.size());
                	Assert.assertEquals("x0", property7.get(0));
                	Assert.assertEquals("x1", property7.get(1));

                	Map property8 = controller.property8.getEntity();
                	
                	Assert.assertNotNull(property8);
                	Assert.assertEquals(2, property8.size());
                	Assert.assertEquals("x0", property8.get("xx-0"));
                	Assert.assertEquals("x1", property8.get("xx-1"));

                	Map<KeyCollectionBeanTest0,String> property9 = controller.property9.getEntity();
                	
                	Assert.assertNotNull(property9);
                	Assert.assertEquals(2, property9.size());
                	Assert.assertEquals("x0", property9.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property9.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property10 = controller.property10.getEntity();
                	
                	Assert.assertNotNull(property10);
                	Assert.assertEquals(2, property10.size());
                	Assert.assertEquals("x0", property10.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property10.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property11 = controller.property11.getEntity();
                	
                	Assert.assertNotNull(property11);
                	Assert.assertEquals(2, property11.size());
                	Assert.assertEquals("x0", property11.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property11.get(new KeyCollectionBeanTest0("1")));

                	Map<EnumTest,String> property12 = controller.property12.getEntity();
                	
                	Assert.assertNotNull(property12);
                	Assert.assertEquals(2, property12.size());
                	Assert.assertEquals("x0", property12.get(EnumTest.VALUE1));
                	Assert.assertEquals("x1", property12.get(EnumTest.VALUE2));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionConstructorTest.class});
    }        

    public void testControllerKeyCollectionCustomCollectionTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                	
                	parameters.put("property.key[0].subKey[0]", "1");
                	parameters.put("property.key[0].subKey[1]", "2");
                	parameters.put("property.key[0].subKey[2]", "3");
                	parameters.put("property.key[0].element[0]", "x1");
                	parameters.put("property.key[0].element[1]", "x2");
                	parameters.put("property.key[0].element[2]", "x3");
                	parameters.put("property.element[0]", "xx0");
                	
                	parameters.put("property.key[1].subKey[0]", "4");
                	parameters.put("property.key[1].subKey[1]", "5");
                	parameters.put("property.key[1].subKey[2]", "6");
                	parameters.put("property.key[1].element[0]", "x4");
                	parameters.put("property.key[1].element[1]", "x5");
                	parameters.put("property.key[1].element[2]", "x6");
                	parameters.put("property.element[1]", "xx1");
                	
                	parameters.put("property.key[2].subKey[0]", "7");
                	parameters.put("property.key[2].subKey[1]", "8");
                	parameters.put("property.key[2].subKey[3]", "9");
                	parameters.put("property.key[2].element[0]", "x7");
                	parameters.put("property.key[2].element[1]", "x8");
                	parameters.put("property.key[2].element[3]", "x9");
                	parameters.put("property.element[2]", "xx2");
                	
                	//Property2
                	
                	parameters.put("property2.key[0].key[0]", "10");
                	parameters.put("property2.key[0].key[1]", "11");
                	parameters.put("property2.key[0].key[2]", "12");
                	parameters.put("property2.key[0].element[0]", "x10");
                	parameters.put("property2.key[0].element[1]", "x11");
                	parameters.put("property2.key[0].element[2]", "x12");
                	parameters.put("property2.element[0]", "xx3");
                	
                	parameters.put("property2.key[1].key[0]", "13");
                	parameters.put("property2.key[1].key[1]", "14");
                	parameters.put("property2.key[1].key[2]", "15");
                	parameters.put("property2.key[1].element[0]", "x13");
                	parameters.put("property2.key[1].element[1]", "x14");
                	parameters.put("property2.key[1].element[2]", "x15");
                	parameters.put("property2.element[1]", "xx4");
                	
                	parameters.put("property2.key[2].key[0]", "16");
                	parameters.put("property2.key[2].key[1]", "17");
                	parameters.put("property2.key[2].key[3]", "18");
                	parameters.put("property2.key[2].element[0]", "x16");
                	parameters.put("property2.key[2].element[1]", "x17");
                	parameters.put("property2.key[2].element[3]", "x18");
                	parameters.put("property2.element[2]", "xx5");
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionCustomCollectionTest controller = 
                			(ControllerKeyCollectionCustomCollectionTest)request.getAttribute("Controller");

                	Map<CustomMap,String> property = controller.property;
                	
                	Assert.assertEquals(3, property.size());
                	
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
                	
                	Assert.assertEquals("xx0", property.get(key1));
                	Assert.assertEquals("xx1", property.get(key2));
                	Assert.assertEquals("xx2", property.get(key3));

                	Map<Map<Integer,String>,String> property2 = controller.property2;
                	
                	Assert.assertEquals(3, property2.size());
                	
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
                	
                	Assert.assertEquals("xx3", property2.get(key4));
                	Assert.assertEquals("xx4", property2.get(key5));
                	Assert.assertEquals("xx5", property2.get(key6));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionCustomCollectionTest.class});
    }        
    
    public void testControllerKeyCollectionFieldTest() throws Throwable{
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
                	
                	parameters.put("property.key[0]", "0");
                	parameters.put("property.key[1]", "1");
                	parameters.put("property.element[0]", "x0");
                	parameters.put("property.element[1]", "x1");
                	
                	parameters.put("property2.key[0]", "0");
                	parameters.put("property2.key[1]", "1");
                	parameters.put("property2.element[0]", "x0");
                	parameters.put("property2.element[1]", "x1");

                	parameters.put("property3.elx[0]", "0");
                	parameters.put("property3.elx[1]", "1");
                	parameters.put("property3.element[0]", "x0");
                	parameters.put("property3.element[1]", "x1");

                	parameters.put("property4.key[0]", "VALUE1");
                	parameters.put("property4.key[1]", "VALUE2");
                	parameters.put("property4.element[0]", "xVALUE1");
                	parameters.put("property4.element[1]", "xVALUE2");

                	parameters.put("property5.element[0]", "x0");
                	parameters.put("property5.element[1]", "x1");
                	
                	parameters.put("property6.key[0]", "01-01-2015");
                	parameters.put("property6.key[1]", "02-01-2015");
                	parameters.put("property6.element[0]", "x01-01-2015");
                	parameters.put("property6.element[1]", "x02-01-2015");

                	parameters.put("property7.key[0]", "0");
                	parameters.put("property7.key[1]", "1");
                	parameters.put("property7.element[0]", "x0");
                	parameters.put("property7.element[1]", "x1");

                	parameters.put("property8.key[0]", "0");
                	parameters.put("property8.key[1]", "1");
                	parameters.put("property8.element[0]", "x0");
                	parameters.put("property8.element[1]", "x1");

                	parameters.put("property9.key[0]", "0");
                	parameters.put("property9.key[1]", "1");
                	parameters.put("property9.element[0]", "x0");
                	parameters.put("property9.element[1]", "x1");

                	parameters.put("property10.key[0].property", "0");
                	parameters.put("property10.key[1].property", "1");
                	parameters.put("property10.element[0]", "x0");
                	parameters.put("property10.element[1]", "x1");

                	parameters.put("property11.key[0].property", "0");
                	parameters.put("property11.key[1].property", "1");
                	parameters.put("property11.element[0]", "x0");
                	parameters.put("property11.element[1]", "x1");

                	parameters.put("property12.key[0]", "0");
                	parameters.put("property12.key[1]", "1");            	
                	parameters.put("property12.element[0]", "x0");
                	parameters.put("property12.element[1]", "x1");            	
                }

                public void prepareSession(Map<String, String> parameters) {
                	parameters.put("property5.key[0]", "0");
                	parameters.put("property5.key[1]", "1");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionFieldTest controller = 
                			(ControllerKeyCollectionFieldTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> property1 = controller.property;
                	
                	Assert.assertNotNull(property1);
                	Assert.assertEquals(2, property1.size());
                	Assert.assertEquals("x0", property1.get(0));
                	Assert.assertEquals("x1", property1.get(1));
                	
                	Map<Integer,String> property2 = controller.property2;
                	
                	Assert.assertNotNull(property2);
                	Assert.assertEquals(2, property2.size());
                	Assert.assertEquals("x0", property2.get(0));
                	Assert.assertEquals("x1", property2.get(1));

                	Map<Integer,String> property3 = controller.property3;
                	
                	Assert.assertNotNull(property3);
                	Assert.assertEquals(2, property3.size());
                	Assert.assertEquals("x0", property3.get(0));
                	Assert.assertEquals("x1", property3.get(1));

                	Map<EnumTest,String> property4 = controller.property4;
                	
                	Assert.assertNotNull(property4);
                	Assert.assertEquals(2, property4.size());
                	Assert.assertEquals("xVALUE1", property4.get(EnumTest.VALUE1));
                	Assert.assertEquals("xVALUE2", property4.get(EnumTest.VALUE2));

                	Map<Integer,String> property5 = controller.property5;
                	
                	Assert.assertNotNull(property5);
                	Assert.assertEquals(2, property5.size());
                	Assert.assertEquals("x0", property5.get(0));
                	Assert.assertEquals("x1", property5.get(1));

                	Map<Date,String> property6 = controller.property6;
                	
                	Assert.assertNotNull(property6);
                	Assert.assertEquals(2, property6.size());
                	
                	try {
						Assert.assertEquals("x01-01-2015", property6.get(sdf.parseObject("01-01-2015")));
	                	Assert.assertEquals("x02-01-2015", property6.get(sdf.parseObject("02-01-2015")));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	Map property7 = controller.property7;
                	
                	Assert.assertNotNull(property7);
                	Assert.assertEquals(2, property7.size());
                	Assert.assertEquals("x0", property7.get(0));
                	Assert.assertEquals("x1", property7.get(1));

                	Map property8 = controller.property8;
                	
                	Assert.assertNotNull(property8);
                	Assert.assertEquals(2, property8.size());
                	Assert.assertEquals("x0", property8.get("xx-0"));
                	Assert.assertEquals("x1", property8.get("xx-1"));

                	Map<KeyCollectionBeanTest0,String> property9 = controller.property9;
                	
                	Assert.assertNotNull(property9);
                	Assert.assertEquals(2, property9.size());
                	Assert.assertEquals("x0", property9.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property9.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property10 = controller.property10;
                	
                	Assert.assertNotNull(property10);
                	Assert.assertEquals(2, property10.size());
                	Assert.assertEquals("x0", property10.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property10.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property11 = controller.property11;
                	
                	Assert.assertNotNull(property11);
                	Assert.assertEquals(2, property11.size());
                	Assert.assertEquals("x0", property11.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property11.get(new KeyCollectionBeanTest0("1")));

                	Map<EnumTest,String> property12 = controller.property12;
                	
                	Assert.assertNotNull(property12);
                	Assert.assertEquals(2, property12.size());
                	Assert.assertEquals("x0", property12.get(EnumTest.VALUE1));
                	Assert.assertEquals("x1", property12.get(EnumTest.VALUE2));
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionFieldTest.class});
    }        
    
    public void testControllerKeyCollectionPropertyTest() throws Throwable{
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
                	
                	parameters.put("property.key[0]", "0");
                	parameters.put("property.key[1]", "1");
                	parameters.put("property.element[0]", "x0");
                	parameters.put("property.element[1]", "x1");
                	
                	parameters.put("property2.key[0]", "0");
                	parameters.put("property2.key[1]", "1");
                	parameters.put("property2.element[0]", "x0");
                	parameters.put("property2.element[1]", "x1");

                	parameters.put("property3.elx[0]", "0");
                	parameters.put("property3.elx[1]", "1");
                	parameters.put("property3.element[0]", "x0");
                	parameters.put("property3.element[1]", "x1");

                	parameters.put("property4.key[0]", "VALUE1");
                	parameters.put("property4.key[1]", "VALUE2");
                	parameters.put("property4.element[0]", "xVALUE1");
                	parameters.put("property4.element[1]", "xVALUE2");

                	parameters.put("property5.element[0]", "x0");
                	parameters.put("property5.element[1]", "x1");
                	
                	parameters.put("property6.key[0]", "01-01-2015");
                	parameters.put("property6.key[1]", "02-01-2015");
                	parameters.put("property6.element[0]", "x01-01-2015");
                	parameters.put("property6.element[1]", "x02-01-2015");

                	parameters.put("property7.key[0]", "0");
                	parameters.put("property7.key[1]", "1");
                	parameters.put("property7.element[0]", "x0");
                	parameters.put("property7.element[1]", "x1");

                	parameters.put("property8.key[0]", "0");
                	parameters.put("property8.key[1]", "1");
                	parameters.put("property8.element[0]", "x0");
                	parameters.put("property8.element[1]", "x1");

                	parameters.put("property9.key[0]", "0");
                	parameters.put("property9.key[1]", "1");
                	parameters.put("property9.element[0]", "x0");
                	parameters.put("property9.element[1]", "x1");

                	parameters.put("property10.key[0].property", "0");
                	parameters.put("property10.key[1].property", "1");
                	parameters.put("property10.element[0]", "x0");
                	parameters.put("property10.element[1]", "x1");

                	parameters.put("property11.key[0].property", "0");
                	parameters.put("property11.key[1].property", "1");
                	parameters.put("property11.element[0]", "x0");
                	parameters.put("property11.element[1]", "x1");

                	parameters.put("property12.key[0]", "0");
                	parameters.put("property12.key[1]", "1");            	
                	parameters.put("property12.element[0]", "x0");
                	parameters.put("property12.element[1]", "x1");            	
                }

                public void prepareSession(Map<String, String> parameters) {
                	parameters.put("property5.key[0]", "0");
                	parameters.put("property5.key[1]", "1");
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerKeyCollectionPropertyTest controller = 
                			(ControllerKeyCollectionPropertyTest)request.getAttribute("Controller");
                	
                	Map<Integer,String> property1 = controller.getProperty();
                	
                	Assert.assertNotNull(property1);
                	Assert.assertEquals(2, property1.size());
                	Assert.assertEquals("x0", property1.get(0));
                	Assert.assertEquals("x1", property1.get(1));
                	
                	Map<Integer,String> property2 = controller.getProperty2();
                	
                	Assert.assertNotNull(property2);
                	Assert.assertEquals(2, property2.size());
                	Assert.assertEquals("x0", property2.get(0));
                	Assert.assertEquals("x1", property2.get(1));

                	Map<Integer,String> property3 = controller.getProperty3();
                	
                	Assert.assertNotNull(property3);
                	Assert.assertEquals(2, property3.size());
                	Assert.assertEquals("x0", property3.get(0));
                	Assert.assertEquals("x1", property3.get(1));

                	Map<EnumTest,String> property4 = controller.getProperty4();
                	
                	Assert.assertNotNull(property4);
                	Assert.assertEquals(2, property4.size());
                	Assert.assertEquals("xVALUE1", property4.get(EnumTest.VALUE1));
                	Assert.assertEquals("xVALUE2", property4.get(EnumTest.VALUE2));

                	Map<Integer,String> property5 = controller.getProperty5();
                	
                	Assert.assertNotNull(property5);
                	Assert.assertEquals(2, property5.size());
                	Assert.assertEquals("x0", property5.get(0));
                	Assert.assertEquals("x1", property5.get(1));

                	Map<Date,String> property6 = controller.getProperty6();
                	
                	Assert.assertNotNull(property6);
                	Assert.assertEquals(2, property6.size());
                	
                	try {
						Assert.assertEquals("x01-01-2015", property6.get(sdf.parseObject("01-01-2015")));
	                	Assert.assertEquals("x02-01-2015", property6.get(sdf.parseObject("02-01-2015")));
					}
                	catch (ParseException e) {
                		throw new RuntimeException(e);
					}
                
                	Map property7 = controller.getProperty7();
                	
                	Assert.assertNotNull(property7);
                	Assert.assertEquals(2, property7.size());
                	Assert.assertEquals("x0", property7.get(0));
                	Assert.assertEquals("x1", property7.get(1));

                	Map property8 = controller.getProperty8();
                	
                	Assert.assertNotNull(property8);
                	Assert.assertEquals(2, property8.size());
                	Assert.assertEquals("x0", property8.get("xx-0"));
                	Assert.assertEquals("x1", property8.get("xx-1"));

                	Map<KeyCollectionBeanTest0,String> property9 = controller.getProperty9();
                	
                	Assert.assertNotNull(property9);
                	Assert.assertEquals(2, property9.size());
                	Assert.assertEquals("x0", property9.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property9.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property10 = controller.getProperty10();
                	
                	Assert.assertNotNull(property10);
                	Assert.assertEquals(2, property10.size());
                	Assert.assertEquals("x0", property10.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property10.get(new KeyCollectionBeanTest0("1")));

                	Map<KeyCollectionBeanTest0,String> property11 = controller.getProperty11();
                	
                	Assert.assertNotNull(property11);
                	Assert.assertEquals(2, property11.size());
                	Assert.assertEquals("x0", property11.get(new KeyCollectionBeanTest0("0")));
                	Assert.assertEquals("x1", property11.get(new KeyCollectionBeanTest0("1")));

                	Map<EnumTest,String> property12 = controller.getProperty12();
                	
                	Assert.assertNotNull(property12);
                	Assert.assertEquals(2, property12.size());
                	Assert.assertEquals("x0", property12.get(EnumTest.VALUE1));
                	Assert.assertEquals("x1", property12.get(EnumTest.VALUE2));
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ControllerKeyCollectionPropertyTest.class});
    }
    
    public void testControllerKeyCollectionBeanConstructorFailTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	Assert.fail("expected: {unknown key type}");
                }

                public void checkException(Throwable e) throws Throwable {
                	
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("unknown key type"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {unknown key type}");
                }
            },
            new Class[]{ControllerKeyCollectionBeanConstructorFailTest.class});
    }           

    public void testControllerKeyCollectionBeanFieldFailTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	Assert.fail("expected: {unknown key type}");
                }

                public void checkException(Throwable e) throws Throwable {
                	
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("unknown key type"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {unknown key type}");
                }
            },
            new Class[]{ControllerKeyCollectionBeanFieldFailTest.class});
    }           

    public void testControllerKeyCollectionBeanPropertyFailTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	Assert.fail("expected: {unknown key type}");
                }

                public void checkException(Throwable e) throws Throwable {
                	
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("unknown key type"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {unknown key type}");
                }
            },
            new Class[]{ControllerKeyCollectionBeanPropertyFailTest.class});
    }           

    public void testControllerKeyCollectionFieldFailTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	Assert.fail("expected: {unknown key type}");
                }

                public void checkException(Throwable e) throws Throwable {
                	
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("unknown key type"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {unknown key type}");
                }
            },
            new Class[]{ControllerKeyCollectionFieldFailTest.class});
    }           

    public void testControllerKeyCollectionPropertyFailTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	Assert.fail("expected: {unknown key type}");
                }

                public void checkException(Throwable e) throws Throwable {
                	
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("unknown key type"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {unknown key type}");
                }
            },
            new Class[]{ControllerKeyCollectionPropertyFailTest.class});
    }           

    public void testControllerKeyCollectionUnknownTypeTest() throws Throwable{
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

                public void prepareRequest(Map<String, String> parameters) {
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    Assert.fail("expected: {unknown type: KeyCollectionBeanTest0}");
                }

                public void checkException(Throwable e) throws Throwable {
                	
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("unknown type: KeyCollectionBeanTest0"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {unknown type: KeyCollectionBeanTest0}");
                }
            },
            new Class[]{ControllerKeyCollectionUnknownTypeTest.class});
    }           
    
}
