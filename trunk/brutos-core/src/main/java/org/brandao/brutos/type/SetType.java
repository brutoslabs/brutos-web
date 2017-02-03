package org.brandao.brutos.type;

public class SetType extends AbstractCollectionType {

	@Override
	protected Class getCollectionClass() {
		return this.classType == SetType.class ? TypeUtil.getDefaultSetType()
				: this.classType;
	}

}
