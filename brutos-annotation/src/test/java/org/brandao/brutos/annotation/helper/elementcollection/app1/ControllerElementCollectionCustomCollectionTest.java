package org.brandao.brutos.annotation.helper.elementcollection.app1;

import java.util.List;

import org.brandao.brutos.annotation.Controller;

@Controller("/controller")
public class ControllerElementCollectionCustomCollectionTest {

	public List<CustomList> property;

	public List<List<Integer>> property2;
	
}
