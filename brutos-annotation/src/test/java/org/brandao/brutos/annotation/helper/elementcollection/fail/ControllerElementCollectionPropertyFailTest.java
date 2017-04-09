package org.brandao.brutos.annotation.helper.elementcollection.fail;

import java.util.List;

import org.brandao.brutos.annotation.Controller;

@Controller
public class ControllerElementCollectionPropertyFailTest {

	public void setProperty(List value){
	}
	
	public List getProperty(){
		return null;
	}
}
