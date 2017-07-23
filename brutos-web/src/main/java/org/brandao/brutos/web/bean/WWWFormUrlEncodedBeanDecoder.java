package org.brandao.brutos.web.bean;

import org.brandao.brutos.mapping.BeanDecoder;
import org.brandao.brutos.mapping.BeanDecoderException;
import org.brandao.brutos.mapping.UseBeanData;

public class WWWFormUrlEncodedBeanDecoder 
	implements BeanDecoder{

	public Object decode(UseBeanData entity, Object data)
			throws BeanDecoderException {
		try{
			return entity.getValue(null);
		}
		catch(Throwable e){
			throw new BeanDecoderException(e);
		}
	}

}
