package org.brandao.brutos.web.bean.helper;

import java.util.ArrayList;

import org.brandao.brutos.annotation.Bean;
import org.brandao.brutos.annotation.ElementCollection;
import org.brandao.brutos.annotation.MappingTypes;

@Bean
@ElementCollection(bean="myElement",mappingType=MappingTypes.COMPLEX)
public class CustomArrayList extends ArrayList<JsonBeanEncoderInnerBean>{

	private static final long serialVersionUID = -4786296716251634234L;
    
}
