package org.brandao.brutos.examples.action;

import org.brandao.brutos.annotation.ResultView;
import org.brandao.brutos.annotation.View;

public class ActionEx2Controller {

	@View("actionex1/act1")
	public void act1Action(){
	}
	
	@View(rendered=false)
	public void act2Action(){
	}
	
	@ResultView(rendered=true)
	public String act3Action(){
		return "<html><body><h3>Action 3 result</h3></body></html>";
	}
	
}
