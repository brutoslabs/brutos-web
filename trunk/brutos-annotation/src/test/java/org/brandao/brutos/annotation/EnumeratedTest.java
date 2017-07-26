package org.brandao.brutos.annotation;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.EnumTest;
import org.brandao.brutos.annotation.helper.enumerated.app1.BeanConstructorEnumTest;
import org.brandao.brutos.annotation.helper.enumerated.app1.BeanConstructorEnumTestEnumerated;
import org.brandao.brutos.annotation.helper.enumerated.app1.BeanConstructorEnumTestEnumeratedAuto;
import org.brandao.brutos.annotation.helper.enumerated.app1.BeanContructorEnumTestEnumeratedOrdinal;
import org.brandao.brutos.annotation.helper.enumerated.app1.BeanContructorEnumTestEnumeratedString;
import org.brandao.brutos.annotation.helper.enumerated.app1.BeanEnumeratedTest;
import org.brandao.brutos.annotation.helper.enumerated.app1.ControllerEnumeratedBeanConstructorTest;
import org.brandao.brutos.annotation.helper.enumerated.app1.ControllerEnumeratedBeanTest;
import org.brandao.brutos.annotation.helper.enumerated.app1.ControllerEnumeratedTest;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.BasicWebApplicationTester;
import org.brandao.brutos.web.test.WebApplicationContextTester;

public class EnumeratedTest extends TestCase{
    
    public void testControllerEnumeratedBeanConstructorTest() throws Throwable{
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

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property.arg0", "0");
                	parameters.put("property2.arg0", "1");
                	parameters.put("property3.arg0", "VALUE3");
                	parameters.put("property4.arg0", "3");
                	parameters.put("property5.arg0", "VALUE5");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerEnumeratedBeanConstructorTest controller = 
                			(ControllerEnumeratedBeanConstructorTest)request.getAttribute("Controller");
                	
                	BeanConstructorEnumTest property = controller.property;
                	
                	assertNotNull(property);
                	assertEquals(EnumTest.VALUE1, property.getProperty());
                	
                	BeanConstructorEnumTestEnumerated property2 = controller.property2;
                	
                	assertNotNull(property2);
                	assertEquals(EnumTest.VALUE2, property2.getProperty());

                	BeanConstructorEnumTestEnumeratedAuto property3 = controller.property3;
                	
                	assertNotNull(property3);
                	assertEquals(EnumTest.VALUE3, property3.getProperty());

                	BeanContructorEnumTestEnumeratedOrdinal property4 = controller.property4;
                	
                	assertNotNull(property4);
                	assertEquals(EnumTest.VALUE4, property4.getProperty());

                	BeanContructorEnumTestEnumeratedString property5 = controller.property5;
                	
                	assertNotNull(property5);
                	assertEquals(EnumTest.VALUE5, property5.getProperty());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerEnumeratedBeanConstructorTest.class});
    }

    public void testControllerEnumeratedBeanTest() throws Throwable{
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

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property.property", "0");
                	parameters.put("property.property2", "1");
                	parameters.put("property.property3", "VALUE3");
                	parameters.put("property.property4", "3");
                	parameters.put("property.property5", "VALUE5");
                	
                	parameters.put("property.property6", "5");
                	parameters.put("property.property7", "6");
                	parameters.put("property.property8", "VALUE8");
                	parameters.put("property.property9", "8");
                	parameters.put("property.property10", "VALUE10");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerEnumeratedBeanTest controller = 
                			(ControllerEnumeratedBeanTest)request.getAttribute("Controller");
                	
                	BeanEnumeratedTest property = controller.property;
                	
                	assertNotNull(property);
                	assertEquals(EnumTest.VALUE1, property.property);
                	assertEquals(EnumTest.VALUE2, property.property2);
                	assertEquals(EnumTest.VALUE3, property.property3);
                	assertEquals(EnumTest.VALUE4, property.property4);
                	assertEquals(EnumTest.VALUE5, property.property5);
                	assertEquals(EnumTest.VALUE6, property.getProperty6());
                	assertEquals(EnumTest.VALUE7, property.getProperty7());
                	assertEquals(EnumTest.VALUE8, property.getProperty8());
                	assertEquals(EnumTest.VALUE9, property.getProperty9());
                	assertEquals(EnumTest.VALUE10, property.getProperty10());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerEnumeratedBeanTest.class});
    }

    public void testControllerEnumeratedTest() throws Throwable{
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

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property", "0");
                	parameters.put("property2", "1");
                	parameters.put("property3", "VALUE3");
                	parameters.put("property4", "3");
                	parameters.put("property5", "VALUE5");
                	
                	parameters.put("property6", "5");
                	parameters.put("property7", "6");
                	parameters.put("property8", "VALUE8");
                	parameters.put("property9", "8");
                	parameters.put("property10", "VALUE10");
                	
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerEnumeratedTest controller = 
                			(ControllerEnumeratedTest)request.getAttribute("Controller");
                	
                	assertEquals(EnumTest.VALUE1, controller.property);
                	assertEquals(EnumTest.VALUE2, controller.property2);
                	assertEquals(EnumTest.VALUE3, controller.property3);
                	assertEquals(EnumTest.VALUE4, controller.property4);
                	assertEquals(EnumTest.VALUE5, controller.property5);
                	assertEquals(EnumTest.VALUE6, controller.getProperty6());
                	assertEquals(EnumTest.VALUE7, controller.getProperty7());
                	assertEquals(EnumTest.VALUE8, controller.getProperty8());
                	assertEquals(EnumTest.VALUE9, controller.getProperty9());
                	assertEquals(EnumTest.VALUE10, controller.getProperty10());
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerEnumeratedTest.class});
    }

    public void testControllerEnumeratedTest_property11Action() throws Throwable{
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
                	parameters.put("arg0", "10");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerEnumeratedTest controller = 
                			(ControllerEnumeratedTest)request.getAttribute("Controller");
                	
                	EnumTest value = controller.property11;
                	assertEquals(EnumTest.VALUE11, value);
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerEnumeratedTest.class});
    }

    public void testControllerEnumeratedTest_property12Action() throws Throwable{
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

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("arg0", "VALUE12");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerEnumeratedTest controller = 
                			(ControllerEnumeratedTest)request.getAttribute("Controller");
                	
                	EnumTest value = controller.property12;
                	assertEquals(EnumTest.VALUE12, value);
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerEnumeratedTest.class});
    }

    public void testControllerEnumeratedTest_property13Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property13", 
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
                	parameters.put("arg0", "VALUE13");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerEnumeratedTest controller = 
                			(ControllerEnumeratedTest)request.getAttribute("Controller");
                	
                	EnumTest value = controller.property13;
                	assertEquals(EnumTest.VALUE13, value);
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerEnumeratedTest.class});
    }

    public void testControllerEnumeratedTest_property14Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property14", 
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
                	parameters.put("arg0", "13");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerEnumeratedTest controller = 
                			(ControllerEnumeratedTest)request.getAttribute("Controller");
                	
                	EnumTest value = controller.property14;
                	assertEquals(EnumTest.VALUE14, value);
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerEnumeratedTest.class});
    }

    public void testControllerEnumeratedTest_property15Action() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/property15", 
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
                	parameters.put("arg0", "VALUE15");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {

                	ControllerEnumeratedTest controller = 
                			(ControllerEnumeratedTest)request.getAttribute("Controller");
                	
                	EnumTest value = controller.property15;
                	assertEquals(EnumTest.VALUE15, value);
                }

                public void checkException(Throwable e) {
                    fail(e.toString());
                }
            },
            new Class[]{ControllerEnumeratedTest.class});
    }
    
}
