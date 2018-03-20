package org.brandao.brutos.annotation;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.CustomArrayList;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.DateTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.EnumTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.EnumValues;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.FieldTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.ListTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.MapElementTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.MapKeyTest;
import org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.Values;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.test.BasicWebApplicationTester;
import org.brandao.brutos.web.test.WebApplicationContextTester;

import com.mockrunner.mock.web.MockHttpServletRequest;

public class WebAnnotationApplicationContextBeanTest extends BrutosTestCase{

	public void testField(){
		WebApplicationContextTester.run(
			"/fieldtest", 
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
				
				public void prepareRequest(Map<String, String> parameters,
						Map<String, String> header, Map<String, Object> properties) {
					
					parameters.put("fieldTest.propertyA", "1");
					parameters.put("fieldTest.propertyB", "teste");
					parameters.put("fieldTest.propertyC", "2000-01-01");
					parameters.put("fieldTest.propertyD", "2");
					parameters.put("fieldTest.property",  "3");
				}
				
				public void prepareSession(Map<String, Object> parameters) {
					parameters.put("fieldTest.proprty",  "4");
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					WebAnnotationApplicationContextBeanTestHelper.FieldTest bean =
							(FieldTest) request.getAttribute("fieldTest");
					
					assertEquals(1, bean.propertyA);
					assertEquals("teste", bean.propertyB);
					assertEquals(Values.dateValue, bean.propertyC);
					assertEquals(new Integer(2), bean.propertyD);
					assertEquals(new Integer(3), bean.propertyE);
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{WebAnnotationApplicationContextBeanTestHelper.ControllerTest.class}
		);
	}

	public void testEnum(){
		WebApplicationContextTester.run(
			"/enumtest", 
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
				
				public void prepareRequest(Map<String, String> parameters,
						Map<String, String> header, Map<String, Object> properties) {
					
					parameters.put("enumTest.propertyA", "VALUE1");
					parameters.put("enumTest.propertyB", "0");
					parameters.put("enumTest.propertyC", "VALUE2");
					parameters.put("enumTest.property", "1");
					parameters.put("enumTest.property2", "0");
				}
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					EnumTest bean =
							(EnumTest) request.getAttribute("enumTest");
					
					assertEquals(EnumValues.VALUE1, bean.propertyA);
					assertEquals(EnumValues.VALUE1, bean.propertyB);
					assertEquals(EnumValues.VALUE2, bean.propertyC);
					assertEquals(EnumValues.VALUE2, bean.propertyD);
					assertEquals(EnumValues.VALUE1, bean.propertyE);
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{WebAnnotationApplicationContextBeanTestHelper.ControllerTest.class}
		);
	}
	
	public void testDate(){
		WebApplicationContextTester.run(
			"/datetest", 
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
				
				public void prepareRequest(Map<String, String> parameters,
						Map<String, String> header, Map<String, Object> properties) {
					
					parameters.put("dateTest.propertyA", "2000-01-01");
					parameters.put("dateTest.propertyB", "01/01/2000");
					parameters.put("dateTest.property", "01/01/2000");
				}
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					DateTest bean =
							(DateTest) request.getAttribute("dateTest");
					
					assertEquals(Values.dateValue, bean.propertyA);
					assertEquals(Values.dateValue, bean.propertyB);
					assertEquals(Values.dateValue, bean.propertyC);
					
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{WebAnnotationApplicationContextBeanTestHelper.ControllerTest.class}
		);
	}

	public void testList(){
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
            		request.setupAddParameter("listTest.propertyA", 
            				new String[]{
	            				String.valueOf(Values.intValue), 
			            		String.valueOf(Values.otherIntValue), 
			            		String.valueOf(Values.intValue)
		            		}
            		);
            		
            		request.setupAddParameter("listTest.propertyB.itens[0]", String.valueOf(Values.intValue));
            		request.setupAddParameter("listTest.propertyB.itens[1]", String.valueOf(Values.otherIntValue));
            		request.setupAddParameter("listTest.propertyB.itens[2]", String.valueOf(Values.intValue));
            		
            		request.setupAddParameter("listTest.propertyC", 
            				new String[]{
	            				String.valueOf(Values.enumValue.ordinal()), 
	            				String.valueOf(Values.otherEnumValue.ordinal()), 
	            				String.valueOf(Values.enumValue.ordinal())
		            		}
            		);
            		
            		request.setupAddParameter("listTest.propertyD.itens[0]", Values.enumValue.name());
            		request.setupAddParameter("listTest.propertyD.itens[1]", Values.otherEnumValue.name());
            		request.setupAddParameter("listTest.propertyD.itens[2]", Values.enumValue.name());
            		
            		request.setupAddParameter("listTest.propertyE", 
            				new String[]{
	            				String.valueOf(Values.enumValue.ordinal()), 
	            				String.valueOf(Values.otherEnumValue.ordinal()), 
	            				String.valueOf(Values.enumValue.ordinal())
		            		}
            		);
            		
            		request.setupAddParameter("listTest.propertyF.itens[0]", String.valueOf(Values.enumValue.ordinal()));
            		request.setupAddParameter("listTest.propertyF.itens[1]", String.valueOf(Values.otherEnumValue.ordinal()));
            		request.setupAddParameter("listTest.propertyF.itens[2]", String.valueOf(Values.enumValue.ordinal()));

            		request.setupAddParameter("listTest.propertyG", 
            				new String[]{
	            				Values.stringValue, 
	            				Values.otherStringValue, 
	            				Values.stringValue
		            		}
            		);
            		
            		request.setupAddParameter("listTest.propertyH.itens[0]", Values.stringValue);
            		request.setupAddParameter("listTest.propertyH.itens[1]", Values.otherStringValue);
            		request.setupAddParameter("listTest.propertyH.itens[2]", Values.stringValue);

            		request.setupAddParameter("listTest.propertyI", 
            				new String[]{
	            				Values.brDateStringValue, 
	            				Values.otherBRDateStringValue, 
	            				Values.brDateStringValue
		            		}
            		);
            		
            		request.setupAddParameter("listTest.propertyJ.itens[0]", Values.brDateStringValue);
            		request.setupAddParameter("listTest.propertyJ.itens[1]", Values.otherBRDateStringValue);
            		request.setupAddParameter("listTest.propertyJ.itens[2]", Values.brDateStringValue);

            		request.setupAddParameter("listTest.propertyK.property", 
            				new String[]{
	            				String.valueOf(Values.intValue), 
	            				String.valueOf(Values.otherIntValue), 
	            				String.valueOf(Values.intValue)
		            		}
            		);

            		request.setupAddParameter("listTest.propertyL.itens[0].property", String.valueOf(Values.intValue));
            		request.setupAddParameter("listTest.propertyL.itens[1].property", String.valueOf(Values.otherIntValue));
            		request.setupAddParameter("listTest.propertyL.itens[2].property", String.valueOf(Values.intValue));

            		request.setupAddParameter("listTest.propertyN.itens[0].myElement[0].property", 
            				new String[]{
	            				String.valueOf(Values.intValue), 
	            				String.valueOf(Values.otherIntValue), 
	            				String.valueOf(Values.intValue)
		            		}
            		);
            		
            		request.setupAddParameter("listTest.propertyP.itens[0]", String.valueOf(Values.intValue));
            		request.setupAddParameter("listTest.propertyP.itens[1]", String.valueOf(Values.otherIntValue));
            		request.setupAddParameter("listTest.propertyP.itens[2]", String.valueOf(Values.intValue));
            		
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					ListTest bean =
							(ListTest) request.getAttribute("listTest");
					
					assertEquals(
							Arrays.asList(Values.intValue, Values.otherIntValue, Values.intValue),
							bean.propertyA);
					
					assertEquals(Arrays.asList(Values.intValue, Values.otherIntValue, Values.intValue),
							bean.propertyB);
					
					assertEquals(Arrays.asList(Values.enumValue, Values.otherEnumValue, Values.enumValue),
							bean.propertyC);
            		
					assertEquals(Arrays.asList(Values.enumValue, Values.otherEnumValue, Values.enumValue),
							bean.propertyD);
            		
					assertEquals(Arrays.asList(Values.enumValue, Values.otherEnumValue, Values.enumValue),
							bean.propertyE);
            		
					assertEquals(Arrays.asList(Values.enumValue, Values.otherEnumValue, Values.enumValue),
							bean.propertyF);

					assertEquals(Arrays.asList(Values.dateValue, Values.otherDateValue, Values.dateValue),
							bean.propertyG);
					
					assertEquals(Arrays.asList(Values.dateValue, Values.otherDateValue, Values.dateValue),
							bean.propertyH);
					
					assertEquals(Arrays.asList(Values.dateValue, Values.otherDateValue, Values.dateValue),
							bean.propertyI);
					
					assertEquals(Arrays.asList(Values.dateValue, Values.otherDateValue, Values.dateValue),
							bean.propertyJ);
					
					assertEquals(Arrays.asList(Values.constructorTestValue, Values.otherConstructorTestValue, Values.constructorTestValue),
							bean.propertyK);
					
					assertEquals(Arrays.asList(Values.constructorTestValue, Values.otherConstructorTestValue, Values.constructorTestValue),
							bean.propertyL);
            		
					assertEquals(Arrays.asList(Values.constructorTestValue, Values.otherConstructorTestValue, Values.constructorTestValue),
							bean.propertyN);
					
					assertEquals(Arrays.asList(Values.constructorTestValue, Values.otherConstructorTestValue, Values.constructorTestValue),
							bean.propertyP);
					
				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{WebAnnotationApplicationContextBeanTestHelper.ControllerTest.class}
		);
	}

	public void testMapCollection(){
		WebApplicationContextTester.run(
			"/mapelementtest", 
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
            		request.setupAddParameter("mapElementTest.propertyA." + Values.intValue, 		String.valueOf(Values.intValue));
            		request.setupAddParameter("mapElementTest.propertyA." + Values.otherIntValue, String.valueOf(Values.otherIntValue));
            		
            		request.setupAddParameter("mapElementTest.propertyB.key[0]", 		String.valueOf(Values.intValue));
            		request.setupAddParameter("mapElementTest.propertyB.key[1]", 		String.valueOf(Values.otherIntValue));
            		request.setupAddParameter("mapElementTest.propertyB.itens[0]",	String.valueOf(Values.intValue));
            		request.setupAddParameter("mapElementTest.propertyB.itens[1]",	String.valueOf(Values.otherIntValue));
            		
            		request.setupAddParameter("mapElementTest.propertyC." + Values.enumValue.ordinal(), 		String.valueOf(Values.enumValue.ordinal()));
            		request.setupAddParameter("mapElementTest.propertyC." + Values.otherEnumValue.ordinal(),	String.valueOf(Values.otherEnumValue.ordinal()));
            		
            		request.setupAddParameter("mapElementTest.propertyD.key[0]", Values.enumValue.name());
            		request.setupAddParameter("mapElementTest.propertyD.key[1]", Values.otherEnumValue.name());
            		request.setupAddParameter("mapElementTest.propertyD.itens[0]", Values.enumValue.name());
            		request.setupAddParameter("mapElementTest.propertyD.itens[1]", Values.otherEnumValue.name());
            		
            		request.setupAddParameter("mapElementTest.propertyE." + Values.enumValue.ordinal(), 		String.valueOf(Values.enumValue.ordinal()));
            		request.setupAddParameter("mapElementTest.propertyE." + Values.otherEnumValue.ordinal(),	String.valueOf(Values.otherEnumValue.ordinal()));
            		
            		request.setupAddParameter("mapElementTest.propertyF.key[0]", 		String.valueOf(Values.enumValue.ordinal()));
            		request.setupAddParameter("mapElementTest.propertyF.key[1]",		String.valueOf(Values.otherEnumValue.ordinal()));
            		request.setupAddParameter("mapElementTest.propertyF.itens[0]",	String.valueOf(Values.enumValue.ordinal()));
            		request.setupAddParameter("mapElementTest.propertyF.itens[1]",	String.valueOf(Values.otherEnumValue.ordinal()));
            		
            		request.setupAddParameter("mapElementTest.propertyG.value", Values.stringValue);
            		request.setupAddParameter("mapElementTest.propertyG.othervalue", Values.otherStringValue);
            		
            		request.setupAddParameter("mapElementTest.propertyH.key[0]", "value");
            		request.setupAddParameter("mapElementTest.propertyH.key[1]", "othervalue");
            		request.setupAddParameter("mapElementTest.propertyH.itens[0]", Values.stringValue);
            		request.setupAddParameter("mapElementTest.propertyH.itens[1]", Values.otherStringValue);

            		request.setupAddParameter("mapElementTest.propertyI.value",		Values.brDateStringValue);
            		request.setupAddParameter("mapElementTest.propertyI.othervalue",	Values.otherBRDateStringValue);

            		request.setupAddParameter("mapElementTest.propertyJ.key[0]", "value");
            		request.setupAddParameter("mapElementTest.propertyJ.key[1]", "othervalue");
            		request.setupAddParameter("mapElementTest.propertyJ.itens[0]", Values.brDateStringValue);
            		request.setupAddParameter("mapElementTest.propertyJ.itens[1]", Values.otherBRDateStringValue);
            		
            		request.setupAddParameter("mapElementTest.propertyK." + Values.intValue + ".property", 		String.valueOf(Values.intValue));
            		request.setupAddParameter("mapElementTest.propertyK." + Values.otherIntValue + ".property", 	String.valueOf(Values.otherIntValue));
            		
            		request.setupAddParameter("mapElementTest.propertyL.key[0]", String.valueOf(Values.intValue));
            		request.setupAddParameter("mapElementTest.propertyL.key[1]", String.valueOf(Values.otherIntValue));
            		request.setupAddParameter("mapElementTest.propertyL.itens[0].property", String.valueOf(Values.intValue));
            		request.setupAddParameter("mapElementTest.propertyL.itens[1].property", String.valueOf(Values.otherIntValue));

            		request.setupAddParameter("mapElementTest.propertyN.key[0]", 							String.valueOf(Values.intValue));
            		request.setupAddParameter("mapElementTest.propertyN.key[1]", 							String.valueOf(Values.otherIntValue));
            		request.setupAddParameter("mapElementTest.propertyN.itens[0].myElement[0].property", 	String.valueOf(Values.intValue));
            		request.setupAddParameter("mapElementTest.propertyN.itens[1].myElement[0].property", 	String.valueOf(Values.otherIntValue));
            		
            		request.setupAddParameter("mapElementTest.propertyP.key[0]", 							String.valueOf(Values.intValue));
            		request.setupAddParameter("mapElementTest.propertyP.key[1]", 							String.valueOf(Values.otherIntValue));
            		request.setupAddParameter("mapElementTest.propertyP.itens[0].myElement[0].property", 	String.valueOf(Values.intValue));
            		request.setupAddParameter("mapElementTest.propertyP.itens[1].myElement[0].property", 	String.valueOf(Values.otherIntValue));
            		
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				@SuppressWarnings("unchecked")
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					MapElementTest bean =
							(MapElementTest) request.getAttribute("mapElementTest");

					assertEquals(
							toMap(
								new Entry<String, Integer>(String.valueOf(Values.intValue),Values.intValue),
								new Entry<String, Integer>(String.valueOf(Values.otherIntValue),Values.otherIntValue)
							),
							bean.propertyA);

					assertEquals(
							toMap(
								new Entry<String, Integer>(String.valueOf(Values.intValue),Values.intValue),
								new Entry<String, Integer>(String.valueOf(Values.otherIntValue),Values.otherIntValue)
							),
							bean.propertyB);
					
					
					assertEquals(
							toMap(
								new Entry<String, EnumValues>(String.valueOf(Values.enumValue.ordinal()),Values.enumValue),
								new Entry<String, EnumValues>(String.valueOf(Values.otherEnumValue.ordinal()),Values.otherEnumValue)
							),
							bean.propertyC);
					
					assertEquals(
							toMap(
								new Entry<String, EnumValues>(String.valueOf(Values.enumValue),Values.enumValue),
								new Entry<String, EnumValues>(String.valueOf(Values.otherEnumValue),Values.otherEnumValue)
							),
							bean.propertyD);
            		
					assertEquals(
							toMap(
								new Entry<String, EnumValues>(String.valueOf(Values.enumValue.ordinal()),Values.enumValue),
								new Entry<String, EnumValues>(String.valueOf(Values.otherEnumValue.ordinal()),Values.otherEnumValue)
							),
							bean.propertyE);

					assertEquals(
							toMap(
								new Entry<String, EnumValues>(String.valueOf(Values.enumValue.ordinal()),Values.enumValue),
								new Entry<String, EnumValues>(String.valueOf(Values.otherEnumValue.ordinal()),Values.otherEnumValue)
							),
							bean.propertyF);

					assertEquals(
							toMap(
								new Entry<String, Date>("value", 		Values.dateValue),
								new Entry<String, Date>("othervalue",	Values.otherDateValue)
							),
							bean.propertyG);

					assertEquals(
							toMap(
								new Entry<String, Date>("value", 		Values.dateValue),
								new Entry<String, Date>("othervalue",	Values.otherDateValue)
							),
							bean.propertyH);

					assertEquals(
							toMap(
								new Entry<String, Date>("value", 		Values.dateValue),
								new Entry<String, Date>("othervalue",	Values.otherDateValue)
							),
							bean.propertyI);

					assertEquals(
							toMap(
								new Entry<String, Date>("value", 		Values.dateValue),
								new Entry<String, Date>("othervalue",	Values.otherDateValue)
							),
							bean.propertyJ);
					
					assertEquals(
							toMap(
								new Entry<Integer, org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.ConstructorTest>(Values.intValue, 		Values.constructorTestValue),
								new Entry<Integer, org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.ConstructorTest>(Values.otherIntValue,	Values.otherConstructorTestValue)
							),
							bean.propertyK);

					assertEquals(
							toMap(
								new Entry<Integer, org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.ConstructorTest>(Values.intValue, 		Values.constructorTestValue),
								new Entry<Integer, org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.ConstructorTest>(Values.otherIntValue,	Values.otherConstructorTestValue)
							),
							bean.propertyL);
					
					assertEquals(
							toMap(
								new Entry<Integer, CustomArrayList>(Values.intValue, 		toList(CustomArrayList.class, Values.constructorTestValue)),
								new Entry<Integer, CustomArrayList>(Values.otherIntValue,	toList(CustomArrayList.class, Values.otherConstructorTestValue))
							),
							bean.propertyN);
					
					assertEquals(
							toMap(
								new Entry<Integer, CustomArrayList>(Values.intValue, 		toList(CustomArrayList.class, Values.constructorTestValue)),
								new Entry<Integer, CustomArrayList>(Values.otherIntValue,	toList(CustomArrayList.class, Values.otherConstructorTestValue))
							),
							bean.propertyP);

				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{WebAnnotationApplicationContextBeanTestHelper.ControllerTest.class}
		);
	}

	public void testMapKey(){
		WebApplicationContextTester.run(
			"/mapkeytest", 
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
            		request.setupAddParameter("mapKeyTest.propertyA." + Values.intValue, 		String.valueOf(Values.intValue));
            		request.setupAddParameter("mapKeyTest.propertyA." + Values.otherIntValue, String.valueOf(Values.otherIntValue));
            		
            		request.setupAddParameter("mapKeyTest.propertyB.chaves[0]",	String.valueOf(Values.intValue));
            		request.setupAddParameter("mapKeyTest.propertyB.chaves[1]",	String.valueOf(Values.otherIntValue));
            		request.setupAddParameter("mapKeyTest.propertyB.elements[0]",	String.valueOf(Values.intValue));
            		request.setupAddParameter("mapKeyTest.propertyB.elements[1]",	String.valueOf(Values.otherIntValue));
            		
            		request.setupAddParameter("mapKeyTest.propertyC." + Values.enumValue.ordinal(), 		String.valueOf(Values.enumValue.ordinal()));
            		request.setupAddParameter("mapKeyTest.propertyC." + Values.otherEnumValue.ordinal(),	String.valueOf(Values.otherEnumValue.ordinal()));
            		
            		request.setupAddParameter("mapKeyTest.propertyD.chaves[0]", 	Values.enumValue.name());
            		request.setupAddParameter("mapKeyTest.propertyD.chaves[1]", 	Values.otherEnumValue.name());
            		request.setupAddParameter("mapKeyTest.propertyD.elements[0]", Values.enumValue.name());
            		request.setupAddParameter("mapKeyTest.propertyD.elements[1]", Values.otherEnumValue.name());
            		
            		request.setupAddParameter("mapKeyTest.propertyE." + Values.enumValue.ordinal(), 		String.valueOf(Values.enumValue.ordinal()));
            		request.setupAddParameter("mapKeyTest.propertyE." + Values.otherEnumValue.ordinal(),	String.valueOf(Values.otherEnumValue.ordinal()));
            		
            		request.setupAddParameter("mapKeyTest.propertyF.chaves[0]", 	String.valueOf(Values.enumValue.ordinal()));
            		request.setupAddParameter("mapKeyTest.propertyF.chaves[1]",	String.valueOf(Values.otherEnumValue.ordinal()));
            		request.setupAddParameter("mapKeyTest.propertyF.elements[0]",	String.valueOf(Values.enumValue.ordinal()));
            		request.setupAddParameter("mapKeyTest.propertyF.elements[1]",	String.valueOf(Values.otherEnumValue.ordinal()));
            		
            		request.setupAddParameter("mapKeyTest.propertyG." + Values.stringValue, 		"value");
            		request.setupAddParameter("mapKeyTest.propertyG." + Values.otherStringValue,	"othervalue");
            		
            		request.setupAddParameter("mapKeyTest.propertyH.chaves[0]", Values.stringValue);
            		request.setupAddParameter("mapKeyTest.propertyH.chaves[1]", Values.otherStringValue);
            		request.setupAddParameter("mapKeyTest.propertyH.elements[0]", "value");
            		request.setupAddParameter("mapKeyTest.propertyH.elements[1]", "othervalue");

            		request.setupAddParameter("mapKeyTest.propertyI." + Values.brDateStringValue,			"value");
            		request.setupAddParameter("mapKeyTest.propertyI." + Values.otherBRDateStringValue,	"othervalue");

            		request.setupAddParameter("mapKeyTest.propertyJ.chaves[0]", Values.brDateStringValue);
            		request.setupAddParameter("mapKeyTest.propertyJ.chaves[1]", Values.otherBRDateStringValue);
            		request.setupAddParameter("mapKeyTest.propertyJ.elements[0]", "value");
            		request.setupAddParameter("mapKeyTest.propertyJ.elements[1]", "othervalue");
            		
            		//request.setupAddParameter("mapKeyTest.propertyK." + Values.intValue + ".property", 		String.valueOf(Values.intValue));
            		//request.setupAddParameter("mapKeyTest.propertyK." + Values.otherIntValue + ".property", 	String.valueOf(Values.otherIntValue));
            		
            		request.setupAddParameter("mapKeyTest.propertyL.chaves[0].property", String.valueOf(Values.intValue));
            		request.setupAddParameter("mapKeyTest.propertyL.chaves[1].property", String.valueOf(Values.otherIntValue));
            		request.setupAddParameter("mapKeyTest.propertyL.elements[0]", String.valueOf(Values.intValue));
            		request.setupAddParameter("mapKeyTest.propertyL.elements[1]", String.valueOf(Values.otherIntValue));

            		request.setupAddParameter("mapKeyTest.propertyN.chaves[0].myElement[0].property",	String.valueOf(Values.intValue));
            		request.setupAddParameter("mapKeyTest.propertyN.chaves[1].myElement[0].property",	String.valueOf(Values.otherIntValue));
            		request.setupAddParameter("mapKeyTest.propertyN.itens[0]", 						String.valueOf(Values.intValue));
            		request.setupAddParameter("mapKeyTest.propertyN.itens[1]", 						String.valueOf(Values.otherIntValue));
            		
            		request.setupAddParameter("mapKeyTest.propertyP.chaves[0].myElement[0].property",	String.valueOf(Values.intValue));
            		request.setupAddParameter("mapKeyTest.propertyP.chaves[1].myElement[0].property",	String.valueOf(Values.otherIntValue));
            		request.setupAddParameter("mapKeyTest.propertyP.itens[0]", 						String.valueOf(Values.intValue));
            		request.setupAddParameter("mapKeyTest.propertyP.itens[1]", 						String.valueOf(Values.otherIntValue));
            		
            	}
            	
				
				public void prepareSession(Map<String, Object> parameters) {
				}
				
				@SuppressWarnings("unchecked")
				public void checkResult(HttpServletRequest request,
						HttpServletResponse response, ServletContext context,
						ConfigurableWebApplicationContext applicationContext) {
					
					MapKeyTest bean =
							(MapKeyTest) request.getAttribute("mapKeyTest");

					assertEquals(
							toMap(
								new Entry<Integer, String>(Values.intValue, String.valueOf(Values.intValue)),
								new Entry<Integer, String>(Values.otherIntValue, String.valueOf(Values.otherIntValue))
							),
							bean.propertyA);

					assertEquals(
							toMap(
								new Entry<Integer, String>(Values.intValue, String.valueOf(Values.intValue)),
								new Entry<Integer, String>(Values.otherIntValue, String.valueOf(Values.otherIntValue))
							),
							bean.propertyB);
					
					
					assertEquals(
							toMap(
								new Entry<EnumValues, String>(Values.enumValue, String.valueOf(Values.enumValue.ordinal())),
								new Entry<EnumValues, String>(Values.otherEnumValue, String.valueOf(Values.otherEnumValue.ordinal()))
							),
							bean.propertyC);
					
					assertEquals(
							toMap(
								new Entry<EnumValues, String>(Values.enumValue, String.valueOf(Values.enumValue)),
								new Entry<EnumValues, String>(Values.otherEnumValue, String.valueOf(Values.otherEnumValue))
							),
							bean.propertyD);
            		
					assertEquals(
							toMap(
								new Entry<EnumValues, String>(Values.enumValue, String.valueOf(Values.enumValue.ordinal())),
								new Entry<EnumValues, String>(Values.otherEnumValue, String.valueOf(Values.otherEnumValue.ordinal()))
							),
							bean.propertyE);

					assertEquals(
							toMap(
								new Entry<EnumValues, String>(Values.enumValue, String.valueOf(Values.enumValue.ordinal())),
								new Entry<EnumValues, String>(Values.otherEnumValue, String.valueOf(Values.otherEnumValue.ordinal()))
							),
							bean.propertyF);

					assertEquals(
							toMap(
								new Entry<Date, String>(Values.dateValue, 		"value"),
								new Entry<Date, String>(Values.otherDateValue,	"othervalue")
							),
							bean.propertyG);

					assertEquals(
							toMap(
								new Entry<Date, String>(Values.dateValue, 		"value"),
								new Entry<Date, String>(Values.otherDateValue,	"othervalue")
							),
							bean.propertyH);

					assertEquals(
							toMap(
								new Entry<Date, String>(Values.dateValue, 		"value"),
								new Entry<Date, String>(Values.otherDateValue,	"othervalue")
							),
							bean.propertyI);

					assertEquals(
							toMap(
								new Entry<Date, String>(Values.dateValue, 		"value"),
								new Entry<Date, String>(Values.otherDateValue,	"othervalue")
							),
							bean.propertyJ);
					
					/*
					assertEquals(
							toMap(
								new Entry<Integer, org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.ConstructorTest>(Values.intValue, 		Values.constructorTestValue),
								new Entry<Integer, org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.ConstructorTest>(Values.otherIntValue,	Values.otherConstructorTestValue)
							),
							bean.propertyK);
					*/
					
					assertEquals(
							toMap(
								new Entry<org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.ConstructorTest, Integer>(Values.constructorTestValue, Values.intValue),
								new Entry<org.brandao.brutos.annotation.WebAnnotationApplicationContextBeanTestHelper.ConstructorTest, Integer>(Values.otherConstructorTestValue, Values.otherIntValue)
							),
							bean.propertyL);
					
					assertEquals(
							toMap(
								new Entry<CustomArrayList, Integer>(toList(CustomArrayList.class, Values.constructorTestValue), Values.intValue),
								new Entry<CustomArrayList, Integer>(toList(CustomArrayList.class, Values.otherConstructorTestValue), Values.otherIntValue)
							),
							bean.propertyN);
					
					assertEquals(
							toMap(
								new Entry<CustomArrayList, Integer>(toList(CustomArrayList.class, Values.constructorTestValue), Values.intValue),
								new Entry<CustomArrayList, Integer>(toList(CustomArrayList.class, Values.otherConstructorTestValue), Values.otherIntValue)
							),
							bean.propertyP);

				}
				
				public void checkException(Throwable e) {
					throw new RuntimeException(e);
				}
				
			}, 
			new Class[]{WebAnnotationApplicationContextBeanTestHelper.ControllerTest.class}
		);
	}
	
}
