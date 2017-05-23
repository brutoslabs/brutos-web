package org.brandao.brutos.web.bean;

import java.io.ByteArrayOutputStream;
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
import org.brandao.brutos.mapping.ParameterAction;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;
import org.brandao.brutos.web.bean.JsonBeanEncoder;
import org.brandao.brutos.web.bean.helper.JsonBeanEncoderBean;
import org.brandao.brutos.web.bean.helper.JsonBeanEncoderControllerTest;
import org.brandao.brutos.web.bean.helper.JsonBeanEncoderInnerBean;
import org.brandao.brutos.web.test.WebApplicationContextTester;
import org.brandao.brutos.web.test.WebApplicationTester;

public class JsonBeanEncoderTest extends TestCase{
    
    public void test() throws Throwable{
        WebApplicationContextTester.run(
            "/controller/test1", 
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
                	
                	org.brandao.brutos.mapping.Controller c = 
                			applicationContext.getControllerManager().getController(JsonBeanEncoderControllerTest.class);
                	
                	org.brandao.brutos.mapping.Action a = c.getAction("/my");
                	ParameterAction pa = a.getParameter(0);
                	
                	JsonBeanEncoderBean o = new JsonBeanEncoderBean();
                	
                	o.setPropertyA(10);
                	o.setPropertyB("value b");
                	o.setPropertyC(new Date());
                	o.setPropertyD(new Date());
                	o.setPropertyE(EnumTest.VALUE1);
                	o.setPropertyF(EnumTest.VALUE11);
                	o.setPropertyG(EnumTest.VALUE12);
                	
                	Map<String,Integer> h = new HashMap<String, Integer>();
                	h.put("a", 1);
                	h.put("b", 2);
                	
                	o.setPropertyH(h);
                	o.setPropertyI(h);
                	
                	Map<JsonBeanEncoderInnerBean,Integer> j =
                			new HashMap<JsonBeanEncoderInnerBean, Integer>();
                	
                	JsonBeanEncoderInnerBean k1j = new JsonBeanEncoderInnerBean();
                	k1j.setProp("k1j");

                	JsonBeanEncoderInnerBean k2j = new JsonBeanEncoderInnerBean();
                	k2j.setProp("k2j");
                	
                	j.put(k1j, 1);
                	j.put(k2j, 2);
                	
                	o.setPropertyJ(j);

                	Map<String, JsonBeanEncoderInnerBean> k =
                			new HashMap<String, JsonBeanEncoderInnerBean>();
                	
                	JsonBeanEncoderInnerBean k1k = new JsonBeanEncoderInnerBean();
                	k1k.setProp("k1k");

                	JsonBeanEncoderInnerBean k2k = new JsonBeanEncoderInnerBean();
                	k2k.setProp("k2k");

                	k.put("k1k", k1k);
                	k.put("k2k", k2k);
                	
                	o.setPropertyK(k);
                	o.setPropertyL(k);
                	
                	List<Integer> m = new ArrayList<Integer>();
                	m.add(1);
                	m.add(4);
                	m.add(6);
                	o.setPropertyM(m);
                	o.setPropertyN(m);
                	o.setPropertyO(m);
                	
                	try{
                		JsonBeanEncoder handler = new JsonBeanEncoder(new ByteArrayOutputStream(), "UTF-8");
	                	handler.encode(pa, o);
	                	System.out.println(handler);
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
