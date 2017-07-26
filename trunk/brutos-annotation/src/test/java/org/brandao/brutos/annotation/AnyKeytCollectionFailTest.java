package org.brandao.brutos.annotation;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.any.app3.fail.Test10FailAnyController;
import org.brandao.brutos.annotation.helper.any.app3.fail.Test1FailAnyController;
import org.brandao.brutos.annotation.helper.any.app3.fail.Test2FailAnyController;
import org.brandao.brutos.annotation.helper.any.app3.fail.Test3FailAnyController;
import org.brandao.brutos.annotation.helper.any.app3.fail.Test4FailAnyController;
import org.brandao.brutos.annotation.helper.any.app3.fail.Test5FailAnyController;
import org.brandao.brutos.annotation.helper.any.app3.fail.Test6FailAnyController;
import org.brandao.brutos.annotation.helper.any.app3.fail.Test7FailAnyController;
import org.brandao.brutos.annotation.helper.any.app3.fail.Test8FailAnyController;
import org.brandao.brutos.annotation.helper.any.app3.fail.Test9FailAnyController;
import org.brandao.brutos.annotation.helper.any.app3.fail.metavaluesdefinition.Test1FailAnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app3.fail.metavaluesdefinition.Test2FailAnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app3.fail.metavaluesdefinition.Test3FailAnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app3.fail.metavaluesdefinition.Test4FailAnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.helper.any.app3.fail.metavaluesdefinition.Test5FailAnyMetaValuesDefinitionController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.BasicWebApplicationTester;
import org.brandao.brutos.web.test.WebApplicationContextTester;

public class AnyKeytCollectionFailTest extends TestCase{
    
    public void testTest1FailAnyController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta values is required}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta values is required"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta values is required}");
                }
            },
            new Class[]{Test1FailAnyController.class});
    }

    public void testTest2FailAnyController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta values is required}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta values is required"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta values is required}");
                }
            },
            new Class[]{Test2FailAnyController.class});
    }

    public void testTest3FailAnyController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta bean name cannot be empty}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta bean name cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta bean name cannot be empty}");
                }
            },
            new Class[]{Test3FailAnyController.class});
    }

    public void testTest4FailAnyController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta bean name cannot be empty}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta bean name cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta bean name cannot be empty}");
                }
            },
            new Class[]{Test4FailAnyController.class});
    }

    public void testTest5FailAnyController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta bean name cannot be empty}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta bean name cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta bean name cannot be empty}");
                }
            },
            new Class[]{Test5FailAnyController.class});
    }

    public void testTest6FailAnyController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta bean name cannot be empty}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta bean name cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta bean name cannot be empty}");
                }
            },
            new Class[]{Test6FailAnyController.class});
    }
    
    public void testTest7FailAnyController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta bean name cannot be empty}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta bean name cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta bean name cannot be empty}");
                }
            },
            new Class[]{Test7FailAnyController.class});
    }

    public void testTest8FailAnyController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta bean name cannot be empty}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta bean name cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta bean name cannot be empty}");
                }
            },
            new Class[]{Test8FailAnyController.class});
    }

    public void testTest9FailAnyController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta bean name cannot be empty}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta bean name cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta bean name cannot be empty}");
                }
            },
            new Class[]{Test9FailAnyController.class});
    }

    public void testTest10FailAnyController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta bean name cannot be empty}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta bean name cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta bean name cannot be empty}");
                }
            },
            new Class[]{Test10FailAnyController.class});
    }

    public void testTest1FailAnyMetaValuesDefinitionController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta values cannot be empty}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta values cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta values cannot be empty}");
                }
            },
            new Class[]{Test1FailAnyMetaValuesDefinitionController.class});
    }

    public void testTest2FailAnyMetaValuesDefinitionController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta values cannot be empty}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta values cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta values cannot be empty}");
                }
            },
            new Class[]{Test2FailAnyMetaValuesDefinitionController.class});
    }

    public void testTest3FailAnyMetaValuesDefinitionController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta values cannot be empty}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta values cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta values cannot be empty}");
                }
            },
            new Class[]{Test3FailAnyMetaValuesDefinitionController.class});
    }

    public void testTest4FailAnyMetaValuesDefinitionController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta values cannot be empty}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta values cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta values cannot be empty}");
                }
            },
            new Class[]{Test4FailAnyMetaValuesDefinitionController.class});
    }

    public void testTest5FailAnyMetaValuesDefinitionController() throws Throwable{
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
                }

                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                    fail("expected: {meta values cannot be empty}");
                }

                public void checkException(Throwable e) {
                    assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("meta values cannot be empty"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    e.printStackTrace();
                    
                    fail("expected: {meta values cannot be empty}");
                }
            },
            new Class[]{Test5FailAnyMetaValuesDefinitionController.class});
    }
    
}
