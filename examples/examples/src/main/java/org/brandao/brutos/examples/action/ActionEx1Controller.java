package org.brandao.brutos.examples.action;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.ActionStrategy;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.web.WebActionStrategyType;

@Action(value="act",view=@View("actionex1/act"))
@ActionStrategy(WebActionStrategyType.PARAMETER)
public class ActionEx1Controller {

	public void act1Action(){
	}
	
	@Action("action2")
	public void act2Action(){
	}
	
}
