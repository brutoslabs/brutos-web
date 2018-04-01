package org.brandao.brutos.annotation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.annotation.WebAnnotationApplicationContextActionTestHelper.ActionNameTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextActionTestHelper.ActionParamListObjectTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextActionTestHelper.ActionParamListValueTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextActionTestHelper.ActionParamMapSimpleKeyIndexTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextActionTestHelper.ActionParamMapSimpleKeyTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextActionTestHelper.ActionParamObjectTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextActionTestHelper.ActionParamValueTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextActionTestHelper.ActionResultValueMapTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextActionTestHelper.Entity;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextActionTestHelper.Values;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.BasicWebApplicationTester;
import org.brandao.brutos.web.test.WebApplicationContextTester;

import com.mockrunner.mock.web.MockHttpServletRequest;

public class WebAnnotationApplicationContextActionTest extends BrutosTestCase{

	@SuppressWarnings("unchecked")
	public void testActionParamListObject(){
		WebApplicationContextTester.run(
			"/listtest", 
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
            		
            		request.setupAddParameter("list[0].property", "1");
            		request.setupAddParameter("list[1].property", "2");
            		
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					List<Entity> bean =
							(List<Entity>) request.getAttribute("list");
					
					assertEquals(
							Arrays.asList(new Entity("1"), new Entity("2")),
							bean);
					
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ActionParamListObjectTest.class}
		);
	}

	@SuppressWarnings("unchecked")
	public void testActionResultValueMap(){
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
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					Map<Entity, String> bean =
							(Map<Entity, String>) request.getAttribute("result");

					assertEquals(
							toMap(
								new Entry<Entity, String>(Values.entityA, Values.A),
								new Entry<Entity, String>(Values.entityB, Values.B)
							),
							bean);
					
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ActionResultValueMapTest.class}
		);
	}
	
	public void testActionParamValue(){
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
            		
            		request.setupAddParameter("value", "1");
            		
            	}
            	
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					Integer value =
							(Integer) request.getAttribute("value");

					assertEquals(new Integer(1), value);
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ActionParamValueTest.class}
		);
	}

	public void testActionParamObject(){
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
            		
            		request.setupAddParameter("arg0.property", Values.A);
            		
            	}
            	
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					Entity value =
							(Entity) request.getAttribute("arg0");

					assertEquals(Values.A, value.property);
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ActionParamObjectTest.class}
		);
	}
	
	public void testActionName(){
		WebApplicationContextTester.run(
			"/test", 
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
            		
            		request.setupAddParameter("value", "1");
            		
            	}
            	
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					Integer value =
							(Integer) request.getAttribute("value");

					assertEquals(new Integer(1), value);
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ActionNameTest.class}
		);
	}

	public void testActionParamMapSimpleKeyIndex(){
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
            		
            		request.setupAddParameter("arg0[A]", Values.A);
            		request.setupAddParameter("arg0[B]", Values.B);
            		
            	}
            	
				@SuppressWarnings("unchecked")
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					Map<String,String> value =
							(Map<String,String>) request.getAttribute("arg0");

					assertEquals(
							toMap(
								new Entry<String, String>(Values.A, Values.A),
								new Entry<String, String>(Values.B, Values.B)),
							value);
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ActionParamMapSimpleKeyIndexTest.class}
		);
	}
	
	public void testActionParamMapSimpleKey(){
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
            		
            		request.setupAddParameter("arg0.A", Values.A);
            		request.setupAddParameter("arg0.B", Values.B);
            		
            	}
            	
				@SuppressWarnings("unchecked")
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					Map<String,String> value =
							(Map<String,String>) request.getAttribute("arg0");

					assertEquals(
							toMap(
								new Entry<String, String>(Values.A, Values.A),
								new Entry<String, String>(Values.B, Values.B)),
							value);
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ActionParamMapSimpleKeyTest.class}
		);
	}

	public void testActionParamListValue(){
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
            		
            		request.setupAddParameter("arg0", new String[]{"1", "2"});
            		
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				@SuppressWarnings("unchecked")
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					List<Integer> bean =
							(List<Integer>) request.getAttribute("arg0");
					
					assertEquals(
							Arrays.asList(1,2),
							bean);
					
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{ActionParamListValueTest.class}
		);
	}
	
}
