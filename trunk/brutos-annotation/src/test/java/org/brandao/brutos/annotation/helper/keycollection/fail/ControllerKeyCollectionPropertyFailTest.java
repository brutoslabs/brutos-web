package org.brandao.brutos.annotation.helper.keycollection.fail;

import java.util.Map;

import org.brandao.brutos.annotation.Controller;

@Controller
public class ControllerKeyCollectionPropertyFailTest {

	public void setProperty(Map value){
	}
	
	public Map getProperty(){
		return null;
	}
}