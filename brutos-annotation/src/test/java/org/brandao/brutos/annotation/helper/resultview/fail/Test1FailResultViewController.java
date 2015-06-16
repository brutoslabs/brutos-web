package org.brandao.brutos.annotation.helper.resultview.fail;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ResultView;

@Controller("/controller")
public class Test1FailResultViewController {

	@ResultView(rendered=false)
	public void test1Action(){
	}

}
