package org.brandao.brutos.annotation;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.annotation.helper.restriction.app1.Test1RestrictionController;
import org.brandao.brutos.annotation.helper.restriction.fail.Test1FailRestrictionController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.validator.DefaultValidator;
import org.brandao.brutos.validator.DefaultValidatorFactory;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class RestrictionTest extends TestCase{
    
    public void testTest1RestrictionController_property1() throws Throwable{
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
                    
                    parameters.put(
                            BrutosConstants.VALIDATOR_FACTORY_CLASS,
                            DefaultValidatorFactory.class.getName()
                    );
                    
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property1", "10");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1RestrictionController.class});
    }

    public void testTest1RestrictionController_property1Fail() throws Throwable{
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
                    
                    parameters.put(
                            BrutosConstants.VALIDATOR_FACTORY_CLASS,
                            DefaultValidatorFactory.class.getName()
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property1", "11");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                    Assert.fail("expected: {property1 > 10}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("property1 > 10"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {property1 > 10}");
                }
            },
            new Class[]{Test1RestrictionController.class});
    }

    public void testTest1RestrictionController_property2() throws Throwable{
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
                    
                    parameters.put(
                            BrutosConstants.VALIDATOR_FACTORY_CLASS,
                            DefaultValidatorFactory.class.getName()
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property2", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1RestrictionController.class});
    }
    
    public void testTest1RestrictionController_property2Fail() throws Throwable{
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
                    
                    parameters.put(
                            BrutosConstants.VALIDATOR_FACTORY_CLASS,
                            DefaultValidatorFactory.class.getName()
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("property2", "1");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                    Assert.fail("expected: property2 < 2}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("property2 < 2"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {property2 < 2}");
                }
            },
            new Class[]{Test1RestrictionController.class});
    }

    public void testTest1RestrictionController_arg0() throws Throwable{
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
                    
                    parameters.put(
                            BrutosConstants.VALIDATOR_FACTORY_CLASS,
                            DefaultValidatorFactory.class.getName()
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("arg0", "10");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1RestrictionController.class});
    }

    public void testTest1RestrictionController_arg0Fail() throws Throwable{
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
                    
                    parameters.put(
                            BrutosConstants.VALIDATOR_FACTORY_CLASS,
                            DefaultValidatorFactory.class.getName()
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("arg0", "11");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                    Assert.fail("expected: {arg0 > 10}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("arg0 > 10"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {arg0 > 10}");
                }
            },
            new Class[]{Test1RestrictionController.class});
    }

    public void testTest1RestrictionController_arg1() throws Throwable{
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
                    
                    parameters.put(
                            BrutosConstants.VALIDATOR_FACTORY_CLASS,
                            DefaultValidatorFactory.class.getName()
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("arg1", "2");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{Test1RestrictionController.class});
    }
    
    public void testTest1RestrictionController_arg1Fail() throws Throwable{
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
                    
                    parameters.put(
                            BrutosConstants.VALIDATOR_FACTORY_CLASS,
                            DefaultValidatorFactory.class.getName()
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("arg1", "1");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                    Assert.fail("expected: arg1 < 2}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("arg1 < 2"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {arg1 < 2}");
                }
            },
            new Class[]{Test1RestrictionController.class});
    }

    public void testTest1FailRestrictionController() throws Throwable{
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
                    
                    parameters.put(
                            BrutosConstants.VALIDATOR_FACTORY_CLASS,
                            DefaultValidatorFactory.class.getName()
                    );
                }

                public void prepareSession(Map<String, String> parameters) {
                }
                
                public void prepareRequest(Map<String, String> parameters) {
                	parameters.put("arg1", "1");
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                    Assert.fail("expected: numberformatexceptio}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("numberformatexceptio"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {numberformatexceptio}");
                }
            },
            new Class[]{Test1FailRestrictionController.class});
    }
    
}
