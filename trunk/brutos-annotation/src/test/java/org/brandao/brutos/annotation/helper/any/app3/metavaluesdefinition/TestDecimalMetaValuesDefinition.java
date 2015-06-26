package org.brandao.brutos.annotation.helper.any.app3.metavaluesdefinition;

import java.util.ArrayList;
import java.util.List;

import org.brandao.brutos.annotation.configuration.MetaValueDefinition;
import org.brandao.brutos.annotation.configuration.MetaValuesDefinition;
import org.brandao.brutos.annotation.helper.any.app1.DecimalProperty;
import org.brandao.brutos.annotation.helper.any.app1.SetProperty;

public class TestDecimalMetaValuesDefinition 
	implements MetaValuesDefinition{

	public List<MetaValueDefinition> getMetaValues() {
		List<MetaValueDefinition> list = new ArrayList<MetaValueDefinition>(); 
		list.add(new MetaValueDefinition("0", DecimalProperty.class));
		list.add(new MetaValueDefinition("1", SetProperty.class));
		return list;
	}

}
