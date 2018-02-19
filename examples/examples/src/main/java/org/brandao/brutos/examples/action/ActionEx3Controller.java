package org.brandao.brutos.examples.action;

import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.ResultActionImp;
import org.brandao.brutos.annotation.Result;
import org.brandao.brutos.ResultAction;

public class ActionEx3Controller {

	public String act1Action(){
		return "test";
	}
	
	@Result("vars")
	public Map<String,Object> act2Action(){
		Map<String,Object> vars = new HashMap<String, Object>();
		vars.put("Name", "Jose aparecido");
		vars.put("email", "test@test.com");
		return vars;
	}
	
	public ResultAction act3Action(){
		ResultAction ra = new ResultActionImp();
		ra.setContentType(String.class);
		ra.setContent("<html><body><h3>Action 3 result</h3></body></html>");
		return ra;
	}
	
	public ResultAction act4Action(){
		ResultAction ra = new ResultActionImp();
		ra.setView("actionEx3Controller/act4");
		ra.add("message", "dkl ldskhf lksajdfhlaksdfhlkdsahflkdsahf ldkjsf");
		return ra;
	}
	
	/*
	public ResultAction act4Action(){
		ResultAction ra = new ResultActionImp();
		ra.setView("actionEx3Controller/act4");
		ra.add("message", "dkl ldskhf lksajdfhlaksdfhlkdsahflkdsahf ldkjsf");
		return ra;
	}
	*/
	
}
