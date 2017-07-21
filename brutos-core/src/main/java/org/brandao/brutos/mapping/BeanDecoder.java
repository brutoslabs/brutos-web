package org.brandao.brutos.mapping;

public interface BeanDecoder {

	Object decode(UseBeanData entity, Object data) throws BeanDecoderException;
	
}
