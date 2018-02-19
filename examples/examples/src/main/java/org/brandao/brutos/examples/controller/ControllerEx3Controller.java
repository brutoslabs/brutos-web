package org.brandao.brutos.examples.controller;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Controller;

@Controller(defaultActionName="/")
public class ControllerEx3Controller {
	
	@Action("/")
	public void indexAction(){
	}
	
}
