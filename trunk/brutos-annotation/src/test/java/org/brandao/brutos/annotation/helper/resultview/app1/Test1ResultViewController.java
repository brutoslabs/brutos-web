package org.brandao.brutos.annotation.helper.resultview.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ResultView;

@Controller("/controller")
public class Test1ResultViewController {

	public String value;
	
	public Test1ResultViewController(){
		this.value = null;
	}
	
	public void testAction(String value){
		this.value = value;
	}
	
	public boolean test1Action(String value){
		this.value = value;
		return true;
	}

	@ResultView(rendered=true)
	public boolean test2Action(String value){
		this.value = value;
		return true;
	}

}
