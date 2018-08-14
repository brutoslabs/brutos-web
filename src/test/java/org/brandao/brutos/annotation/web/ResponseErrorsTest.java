package org.brandao.brutos.annotation.web;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.annotation.web.helper.BrutosTestCase;
import org.brandao.brutos.annotation.web.helper.ResponseErrorsTestHelper.Controllers.ActionLevelController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorsTestHelper.Controllers.ActionLevelExceptionController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorsTestHelper.Controllers.ActionLevelExceptionWithViewController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorsTestHelper.Controllers.ActionLevelWithStatusAndViewController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorsTestHelper.Controllers.ControllerLevelController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorsTestHelper.Controllers.ControllerLevelExceptionController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorsTestHelper.Controllers.ControllerLevelExceptionWithViewController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorsTestHelper.Controllers.DefaultConfigActionController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorsTestHelper.Controllers.DefaultConfigController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.HttpStatus;
import org.brandao.brutos.web.test.BasicWebApplicationTester;
import org.brandao.brutos.web.test.WebApplicationContextTester;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockRequestDispatcher;

public class ResponseErrorsTest extends BrutosTestCase{

	/*---- Controller level----*/
	
	public void testDefaultConfig(){
		WebApplicationContextTester.run(
			"/action", 
			new BasicWebApplicationTester(){
				
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
				
            	public void prepareRequest(MockHttpServletRequest request) {
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					MockHttpServletRequest req  = (MockHttpServletRequest)request;
					MockHttpServletResponse res = (MockHttpServletResponse)response;
					MockRequestDispatcher rd    = (MockRequestDispatcher) req.getRequestDispatcherMap().get("/WEB-INF/views/view.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.OK, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{DefaultConfigController.class}
		);
	}

	public void testControllerLevelController(){
		WebApplicationContextTester.run(
			"/action", 
			new BasicWebApplicationTester(){
				
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
				
            	public void prepareRequest(MockHttpServletRequest request) {
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					MockHttpServletRequest req  = (MockHttpServletRequest)request;
					MockHttpServletResponse res = (MockHttpServletResponse)response;
					MockRequestDispatcher rd    = (MockRequestDispatcher) req.getRequestDispatcherMap().get("/WEB-INF/views/view.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ControllerLevelController.class}
		);
	}

	public void testControllerLevelExceptionController(){
		WebApplicationContextTester.run(
			"/action", 
			new BasicWebApplicationTester(){
				
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
				
            	public void prepareRequest(MockHttpServletRequest request) {
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					MockHttpServletRequest req  = (MockHttpServletRequest)request;
					MockHttpServletResponse res = (MockHttpServletResponse)response;
					MockRequestDispatcher rd    = (MockRequestDispatcher) req.getRequestDispatcherMap().get("/WEB-INF/views/view.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ControllerLevelExceptionController.class}
		);
	}

	public void testControllerLevelExceptionWithViewController(){
		WebApplicationContextTester.run(
			"/action", 
			new BasicWebApplicationTester(){
				
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
				
            	public void prepareRequest(MockHttpServletRequest request) {
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					MockHttpServletRequest req  = (MockHttpServletRequest)request;
					MockHttpServletResponse res = (MockHttpServletResponse)response;
					MockRequestDispatcher rd    = (MockRequestDispatcher) req.getRequestDispatcherMap().get("/WEB-INF/views/exp.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ControllerLevelExceptionWithViewController.class}
		);
	}

	/*---- Action level----*/
	
	public void testDefaultActionConfig(){
		WebApplicationContextTester.run(
			"/action", 
			new BasicWebApplicationTester(){
				
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
				
            	public void prepareRequest(MockHttpServletRequest request) {
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					MockHttpServletRequest req  = (MockHttpServletRequest)request;
					MockHttpServletResponse res = (MockHttpServletResponse)response;
					MockRequestDispatcher rd    = (MockRequestDispatcher) req.getRequestDispatcherMap().get("/WEB-INF/views/view.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.OK, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{DefaultConfigActionController.class}
		);
	}

	public void testActionLevelController(){
		WebApplicationContextTester.run(
			"/action", 
			new BasicWebApplicationTester(){
				
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
				
            	public void prepareRequest(MockHttpServletRequest request) {
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					MockHttpServletRequest req  = (MockHttpServletRequest)request;
					MockHttpServletResponse res = (MockHttpServletResponse)response;
					MockRequestDispatcher rd    = (MockRequestDispatcher) req.getRequestDispatcherMap().get("/WEB-INF/views/view.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ActionLevelController.class}
		);
	}

	public void testActionLevelWithStatusAndViewController(){
		WebApplicationContextTester.run(
			"/action", 
			new BasicWebApplicationTester(){
				
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
				
            	public void prepareRequest(MockHttpServletRequest request) {
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					MockHttpServletRequest req  = (MockHttpServletRequest)request;
					MockHttpServletResponse res = (MockHttpServletResponse)response;
					MockRequestDispatcher rd    = (MockRequestDispatcher) req.getRequestDispatcherMap().get("/WEB-INF/views/npe.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ActionLevelWithStatusAndViewController.class}
		);
	}
	
	public void testActionLevelExceptionController(){
		WebApplicationContextTester.run(
			"/action", 
			new BasicWebApplicationTester(){
				
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
				
            	public void prepareRequest(MockHttpServletRequest request) {
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					MockHttpServletRequest req  = (MockHttpServletRequest)request;
					MockHttpServletResponse res = (MockHttpServletResponse)response;
					MockRequestDispatcher rd    = (MockRequestDispatcher) req.getRequestDispatcherMap().get("/WEB-INF/views/view.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ActionLevelExceptionController.class}
		);
	}

	public void testActionLevelExceptionWithViewController(){
		WebApplicationContextTester.run(
			"/action", 
			new BasicWebApplicationTester(){
				
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
				
            	public void prepareRequest(MockHttpServletRequest request) {
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					MockHttpServletRequest req  = (MockHttpServletRequest)request;
					MockHttpServletResponse res = (MockHttpServletResponse)response;
					MockRequestDispatcher rd    = (MockRequestDispatcher) req.getRequestDispatcherMap().get("/WEB-INF/views/exp.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ActionLevelExceptionWithViewController.class}
		);
	}
	
}
