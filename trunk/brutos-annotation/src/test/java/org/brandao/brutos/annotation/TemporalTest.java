package org.brandao.brutos.annotation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.temporal.app1.Test1TemporalBean;
import org.brandao.brutos.annotation.helper.temporal.app1.Test1TemporalController;
import org.brandao.brutos.annotation.helper.temporal.fail.Test1FaiTemporalController;
import org.brandao.brutos.annotation.helper.temporal.fail.Test2FaiTemporalController;
import org.brandao.brutos.annotation.helper.temporal.fail.Test3FaiTemporalController;
import org.brandao.brutos.annotation.helper.temporal.fail.Test4FaiTemporalController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class TemporalTest extends TestCase{
    
	private SimpleDateFormat sdf  = new SimpleDateFormat("yyyy'_'MM'_'dd");
	private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy'-'MM'-'dd");
	private SimpleDateFormat sdf3 = new SimpleDateFormat("dd'/'MM'/'yyyy");
	
    public void testTest1TargetController() throws Throwable{
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
                	parameters.put("property1","2015_10_01");
                	parameters.put("property2","2015-10-02");
                	parameters.put("arg0","03/10/2015");
                	
                	parameters.put("property4.property1","2015_10_04");
                	parameters.put("property4.property2","2015-10-05");
                	parameters.put("property4.arg0","06/10/2015");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Test1TemporalController controller = (Test1TemporalController)request.getAttribute("Controller");
                	
                	try{
	                	Calendar cal = GregorianCalendar.getInstance();
	                	Assert.assertEquals(sdf.parse("2015_10_01"), controller.property1);
	                	
	                	cal.setTime(sdf2.parse("2015-10-02"));
	                	Assert.assertEquals(cal, controller.getProperty2());
	                	
	                	Assert.assertEquals(sdf3.parse("03/10/2015"), controller.property3);
	                	
	                	Test1TemporalBean bean = controller.property4;
	                	Assert.assertNotNull(bean);
	                	
	                	cal.setTime(sdf.parse("2015_10_04"));
	                	Assert.assertEquals(cal, bean.property1);
	                	
	                	Assert.assertEquals(sdf2.parse("2015-10-05"), bean.getProperty2());
	                	Assert.assertEquals(sdf3.parse("06/10/2015"), bean.property3);
                	}
                	catch(ParseException e){
                		throw new RuntimeException(e);
                	}
                	
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1TemporalController.class});
    }
    
    // TODO: next version
    /*
    public void testTest1FaiTemporalController() throws Throwable{
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
                	parameters.put("property1", "29/08/1984");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                    Assert.fail("expected: {not a temporal type: property1}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("not a temporal type: property1"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {not a temporal type: property1}");
                }
            },
            new Class[]{Test1FaiTemporalController.class});
    }
    
    public void testTest2FaiTemporalController() throws Throwable{
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

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                    Assert.fail("expected: {not a temporal type: property1}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("not a temporal type: property1"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {not a temporal type: property1}");
                }
            },
            new Class[]{Test2FaiTemporalController.class});
    }

    public void testTest3FaiTemporalController() throws Throwable{
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

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                    Assert.fail("expected: {not a temporal type: property1}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("not a temporal type: property1"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {not a temporal type: property1}");
                }
            },
            new Class[]{Test3FaiTemporalController.class});
    }

    public void testTest4FaiTemporalController() throws Throwable{
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

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                    Assert.fail("expected: {not a temporal type: property1}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("not a temporal type: property1"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {not a temporal type: property1}");
                }
            },
            new Class[]{Test4FaiTemporalController.class});
    }
    */
    
}
