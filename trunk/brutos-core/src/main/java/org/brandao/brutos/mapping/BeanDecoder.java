package org.brandao.brutos.mapping;

import org.brandao.brutos.CodeGenerator;
import org.brandao.brutos.FetchType;

public interface BeanDecoder {

	void setCodeGenerator(CodeGenerator value);
	
	Object decode(UseBeanData entity, FetchType fetchType, Object data) throws BeanDecoderException;

	Object decode(DependencyBean dependencyBean, FetchType fetchType, Object data) throws BeanDecoderException;
	
}
