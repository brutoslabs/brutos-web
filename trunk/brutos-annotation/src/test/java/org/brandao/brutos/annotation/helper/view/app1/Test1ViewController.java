package org.brandao.brutos.annotation.helper.view.app1;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Actions;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.DispatcherType;
import org.brandao.brutos.annotation.View;

@Controller("/controller")
@Action(value="/action", view=@View(value="/view01.jsp",resolved=true))
@Actions({
	@Action(value="/action2", view=@View("view02")),
	@Action(value="/action3", view=@View("view03"))
})

public class Test1ViewController {

	@View("view04")
	public void test1Action(){
	}

	@View(value="/view05.jsp", resolved=true)
	public void test2Action(){
	}

	@View(value="view06", dispatcher=DispatcherType.INCLUDE)
	public void test3Action(){
	}

	@View(rendered=false)
	public void test4Action(){
	}
	
}
