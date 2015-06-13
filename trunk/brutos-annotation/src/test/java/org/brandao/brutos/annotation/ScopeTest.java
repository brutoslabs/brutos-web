package org.brandao.brutos.annotation;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.brandao.brutos.Scopes;
import org.brandao.brutos.annotation.helper.extendedscope.app1.CScope;
import org.brandao.brutos.annotation.helper.extendedscope.app1.DScope;
import org.brandao.brutos.annotation.helper.extendedscope.app1.ScopeA;
import org.brandao.brutos.annotation.helper.extendedscope.app1.ScopeB;
import org.brandao.brutos.annotation.helper.extendedscope.fail.FScope;
import org.brandao.brutos.annotation.helper.extendedscope.fail.ScopeE;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class ScopeTest extends TestCase{
    
    public void testScopeA() throws Throwable{
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
                	
                	Scopes scopes = applicationContext.getScopes();
                	Scope scope = scopes.get("scopeA");
                	Assert.assertNotNull(scope);
                	Assert.assertTrue(scope instanceof ScopeA);
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ScopeA.class});
    }

    public void testScopeB() throws Throwable{
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
                	
                	Scopes scopes = applicationContext.getScopes();
                	Scope scope = scopes.get("ScopeBeta");
                	Assert.assertNotNull(scope);
                	Assert.assertTrue(scope instanceof ScopeB);
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{ScopeB.class});
    }

    public void testScopeC() throws Throwable{
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
                	
                	Scopes scopes = applicationContext.getScopes();
                	Scope scope = scopes.get("C");
                	Assert.assertNotNull(scope);
                	Assert.assertTrue(scope instanceof CScope);
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{CScope.class});
    }

    public void testScopeD() throws Throwable{
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
                	
                	Scopes scopes = applicationContext.getScopes();
                	Scope scope = scopes.get("MyScope");
                	Assert.assertNotNull(scope);
                	Assert.assertTrue(scope instanceof DScope);
                }

                public void checkException(Throwable e) throws Throwable {
                    throw e;
                }
            },
            new Class[]{DScope.class});
    }

    public void testFailScopeF() throws Throwable{
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
                	
                    Assert.fail("expected: {org.brandao.brutos.annotation.helper.extendedscope.fail.FScope must implement Scope}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("org.brandao.brutos.annotation.helper.extendedscope.fail.FScope must implement Scope"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {org.brandao.brutos.annotation.helper.extendedscope.fail.FScope must implement Scope}");
                }
            },
            new Class[]{FScope.class});
    }

    public void testFailScopeE() throws Throwable{
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
                	
                    Assert.fail("expected: {org.brandao.brutos.annotation.helper.extendedscope.fail.ScopeE must implement Scope}");
                }

                public void checkException(Throwable e) throws Throwable {
                    Assert.assertNotNull(e);
                    Throwable ex = e;
                    do{
                        if(ex.getMessage().equals("org.brandao.brutos.annotation.helper.extendedscope.fail.ScopeE must implement Scope"))
                            return;
                    }while((ex = ex.getCause()) != null);
                    
                    Assert.fail("expected: {org.brandao.brutos.annotation.helper.extendedscope.fail.ScopeE must implement Scope}");
                }
            },
            new Class[]{ScopeE.class});
    }
    
}
