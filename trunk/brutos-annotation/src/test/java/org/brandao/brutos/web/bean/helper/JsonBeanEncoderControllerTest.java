package org.brandao.brutos.web.bean.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.helper.EnumTest;

@Controller("/controller")
public class JsonBeanEncoderControllerTest {

	public JsonBeanEncoderBean myAction(){
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
    	
    	return o;
	}
}
