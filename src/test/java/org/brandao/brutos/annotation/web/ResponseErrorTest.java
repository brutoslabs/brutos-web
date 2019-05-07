package org.brandao.brutos.annotation.web;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.annotation.web.helper.BrutosTestCase;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.ActionLevelController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.ActionLevelExceptionWithViewController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.ControllerLevelController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.ControllerLevelExceptionWithViewController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.DefaultConfigActionController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.DefaultConfigController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.ExceptionAliasActionLevelController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.ExceptionAliasControllerLevelController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.ExceptionWithMethodAndAliasAnnotationController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.ExceptionWithMethodAndBuildResponseController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.ExceptionWithMethodAndBuildResponseWithViewController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.ExceptionWithMethodAndViewAnnotationController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.ExceptionWithMethodAndViewController;
import org.brandao.brutos.annotation.web.helper.ResponseErrorTestHelper.Controllers.ExceptionWithMethodDefaultConfigController;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.HttpStatus;
import org.brandao.brutos.web.test.BasicWebApplicationTester;
import org.brandao.brutos.web.test.WebApplicationContextTester;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockRequestDispatcher;

public class ResponseErrorTest extends BrutosTestCase{

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

	/* Exceção delegada a um método */
	
	public void testExceptionWithMethodDefaultConfigController(){
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
					MockRequestDispatcher rd    = 
							(MockRequestDispatcher) req.getRequestDispatcherMap()
							.get("/WEB-INF/views/view.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ExceptionWithMethodDefaultConfigController.class}
		);
	}

	public void testExceptionWithMethodAndViewController(){
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
					MockRequestDispatcher rd    = 
							(MockRequestDispatcher) req.getRequestDispatcherMap()
							.get("/WEB-INF/views/npe.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ExceptionWithMethodAndViewController.class}
		);
	}

	public void testExceptionWithMethodAndViewAnnotationController(){
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
					MockRequestDispatcher rd    = 
							(MockRequestDispatcher) req.getRequestDispatcherMap()
							.get("/WEB-INF/views/npe.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ExceptionWithMethodAndViewAnnotationController.class}
		);
	}

	public void testExceptionAliasControllerLevelController(){
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
					MockRequestDispatcher rd    = 
							(MockRequestDispatcher) req.getRequestDispatcherMap()
							.get("/WEB-INF/views/view.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ExceptionAliasControllerLevelController.class}
		);
	}
	
	public void testExceptionAliasActionLevelController(){
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
					MockRequestDispatcher rd    = 
							(MockRequestDispatcher) req.getRequestDispatcherMap()
							.get("/WEB-INF/views/view.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ExceptionAliasActionLevelController.class}
		);
	}
	
	public void testExceptionWithMethodAndAliasAnnotationController(){
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
					MockRequestDispatcher rd    = 
							(MockRequestDispatcher) req.getRequestDispatcherMap()
							.get("/WEB-INF/views/view.jsp");
					
					assertNotNull(rd);
					assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ExceptionWithMethodAndAliasAnnotationController.class}
		);
	}

	public void testExceptionWithMethodAndBuildResponseController(){
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
					
					MockHttpServletResponse res = (MockHttpServletResponse)response;
					assertEquals(HttpStatus.BAD_REQUEST, res.getErrorCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ExceptionWithMethodAndBuildResponseController.class}
		);
	}

	public void testExceptionWithMethodAndBuildResponseWithViewController(){
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
					MockRequestDispatcher rd    = 
							(MockRequestDispatcher) req.getRequestDispatcherMap()
							.get("/WEB-INF/views/exp.jsp");
					
					assertNotNull(rd);
					assertNotNull(rd.getForwardedRequest().getAttribute("ex"));
					assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ExceptionWithMethodAndBuildResponseWithViewController.class}
		);
	}
	
}
