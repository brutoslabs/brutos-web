package org.brandao.brutos.examples.controller;

import org.brandao.brutos.annotation.ActionStrategy;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.web.WebActionStrategyType;

@Controller(actionId="act")
@ActionStrategy(WebActionStrategyType.PARAMETER)
public class ControllerEx6Controller {
	
	public void action1Action(){
	}
	
	public void action2Action(){
	}
	
}
