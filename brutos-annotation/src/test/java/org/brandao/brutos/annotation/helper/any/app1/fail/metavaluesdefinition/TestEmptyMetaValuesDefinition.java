package org.brandao.brutos.annotation.helper.any.app1.fail.metavaluesdefinition;

import java.util.ArrayList;
import java.util.List;

import org.brandao.brutos.annotation.configuration.MetaValueDefinition;
import org.brandao.brutos.annotation.configuration.MetaValuesDefinition;

public class TestEmptyMetaValuesDefinition 
	implements MetaValuesDefinition{

	public List<MetaValueDefinition> getMetaValues() {
		List<MetaValueDefinition> list = new ArrayList<MetaValueDefinition>(); 
		return list;
	}

}
