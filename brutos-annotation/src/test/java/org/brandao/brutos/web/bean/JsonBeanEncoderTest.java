package org.brandao.brutos.web.bean;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.brandao.brutos.annotation.helper.EnumTest;
import org.brandao.brutos.annotation.web.test.MockAnnotationWebApplicationContext;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.PropertyController;
import org.brandao.brutos.mapping.ResultAction;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.bean.helper.JsonBeanEncoderBean;
import org.brandao.brutos.web.bean.helper.JsonBeanEncoderControllerTest;
import org.brandao.brutos.web.bean.helper.JsonBeanEncoderInnerBean;
import org.brandao.brutos.web.mapping.WebActionID;
import org.brandao.brutos.web.test.BasicWebApplicationTester;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.jbrgates.JSONDecoder;
import org.brandao.jbrgates.JSONEncoder;

import com.mockrunner.mock.web.MockHttpServletRequest;

public class JsonBeanEncoderTest extends TestCase{
    
    public void test() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/myAction", 
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

                public void prepareRequest(MockHttpServletRequest request){
                	JsonBeanEncoderBean o = new JsonBeanEncoderBean();
                	
                	/*
                	{
                		"propertyA": 10,
                		"propertyB": "teste",
                		"propertyC": "2017-07-22",
                		"propertyD": "2017-07-22T00:00:00.000Z",
                		"propertyE": "VALUE1",
                		"propertyF": 0,
                		"propertyG": "VALUE1",
                		"propertyH": {
                			"myElement": [
                				{
                					"myKey": "key1",
                					"myElement": 1
                				},
                				{
                					"myKey": "key2",
                					"myElement": 2
                				}
                			]
                		},
                		"propertyI": {
                			"key1": 1,
                			"key2": 2
                		},
                		"propertyJ": {
                			"element": [
                				{
                					"key": { "prop": "v1" },
                					"element": 1
                				},
                				{
                					"key": { "prop": "v2" },
                					"element": 2
                				}
                			]
                		},
                		"propertyK": {
                			"key1": { "prop": "v1" },
                			"key2": { "prop": "v2" }
                		},
                		"propertyL": {
                			"myElement": [
                				{
                					"key": "1",
                					"myElement": { "prop": "v1" }
                				},
                				{
                					"key": "2",
                					"myElement": { "prop": "v2" }
                				}
                			]
                		},
                		"propertyM": {
                			"myElement": [ 0, 1 ]
                		},
                		"propertyN": [ 0, 1 ],
                		"propertyO": [ 0, 1 ],
                		"propertyP": [
    		              	{ "prop": "v1" },
    		              	{ "prop": "v2" }    		              	
            			],
                		"propertyQ": {
                			"myElement" : [
                       			{ "prop": "v1" },
                    			{ "prop": "v2" }
			               	]
                		},
                		"propertyR": {
                			"myElement": [
                				{
                					"mykey": { "prop": "v1" },
                					"myElement": { "prop": "v1" }
                				},
                				{
                					"mykey": { "prop": "v2" },
                					"myElement": { "prop": "v2" }
                				}
                			]
                		},
                		"propertyS": {
                			"myElement" : [
                       			{ "prop": "v1" },
                    			{ "prop": "v2" }
			               	]
                		},
                		"propertyT": {
                			"key1" : [
    			        		{ "prop": "v1" },
    			        		{ "prop": "v2" }
                			],
                			"key2" : [
          			        		{ "prop": "v1" },
        			        		{ "prop": "v2" }
                			]
                		},
                		"propertyU": {
                			"key1" : {
                				"myElement" : [
    				               {
    				            	   "arg0": 1
    				               },
    				               {
    				            	   "arg0": 2
    				               }
				               ]
                			},
                			"key2" : {
                				"myElement" : [
    				               {
    				            	   "arg0": 3
    				               },
    				               {
    				            	   "arg0": 4
    				               }
				               ]
                			}
                		},
                		"propertyV": {
                			"myElement2" : [
	    			                {
    				                	"key": "key1",
	    			                	"myElement2": {
								"myElement": [
									{
										"arg0": 1
									},
									{
										"arg0": 2
									}
								]
        	                                        }
   				                },
    				                {
    			        	        	"key": "key2",
    			                		"myElement2": {
								"myElement": [
									{
										"arg0": 3
									},
									{
										"arg0": 4
									}
								]
                                                	}
                	               	
    			                	}
                			]
                		}

                	}
                	*/            	
                	FileInputStream file = null;
                	try{
	                	file = new FileInputStream("c:/develop/teste-json.txt");
	                	byte[] buf = new byte[16000];
	                	int l = file.read(buf);
	                	String json = new String(buf, 0, l);
	                	json = json.replaceAll("\r+|\n+|\t+|\\s+", "");
	                	JSONDecoder d = new JSONDecoder(json);
	                	Object ox = d.decode();
	                	
	                	Map<String, Object> r = new HashMap<String, Object>();
	                	r.put("prop", ox);
	                	request.setAttribute("json", r);
	                	request.setContentType("application/json; charset=UTF-8");
	                	request.setBodyContent(json);
	                	request.setHeader("Accept", "*/*");
                	}
                	catch(Throwable ex){
                		fail();
                	}
                }
                
                public void checkResult(HttpServletRequest request, HttpServletResponse response, 
                        ServletContext context, ConfigurableWebApplicationContext applicationContext) {
                	
                	Controller c = 
            			applicationContext.getControllerManager().getController(JsonBeanEncoderControllerTest.class);
                	
                	PropertyController a = c.getProperty("prop");
                	JsonBeanDecoder jbd = new JsonBeanDecoder();
                	Object o = request.getAttribute("json");
                	Object x;
                	try{
                		x = jbd.decode(a, o );
                	}
                	catch(Throwable e){
                		e.printStackTrace();
                	}
                }
                
                public void checkException(Throwable e) {
                	e.printStackTrace();
                }
            },
            new Class[]{JsonBeanEncoderControllerTest.class});
    }
	
}
