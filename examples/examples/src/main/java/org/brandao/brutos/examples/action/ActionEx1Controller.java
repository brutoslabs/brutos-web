package org.brandao.brutos.examples.action;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.View;

@Action(value="act",view=@View("actionex1/act"))
public class ActionEx1Controller {

	public void act1Action(){
	}
	
	@Action("action2")
	public void act2Action(){
	}
	
}
