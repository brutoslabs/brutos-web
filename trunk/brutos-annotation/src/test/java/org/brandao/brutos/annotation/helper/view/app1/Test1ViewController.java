package org.brandao.brutos.annotation.helper.view.app1;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Actions;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.DispatcherType;
import org.brandao.brutos.annotation.View;

@Controller("/controller")
@Action(value="/action1", view=@View(value="/view01.jsp",resolved=true))
@Actions({
	@Action(value="/action2", view=@View("view02")),
	@Action(value="/action3", view=@View("view03"))
})
@View(value="controller",dispatcher=DispatcherType.INCLUDE)
public class Test1ViewController {

	@View("view04")
	public void action4Action(){
	}

	@View(value="/view05.jsp", resolved=true)
	public void action5Action(){
	}

	@View(value="view06", dispatcher=DispatcherType.INCLUDE)
	public void action6Action(){
	}

	@View(rendered=false)
	public void action7Action(){
	}
	
}
