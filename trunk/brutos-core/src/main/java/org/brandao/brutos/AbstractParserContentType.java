package org.brandao.brutos;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.BeanDecoder;
import org.brandao.brutos.mapping.BeanDecoderException;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.ParameterAction;
import org.brandao.brutos.mapping.PropertyController;

public abstract class AbstractParserContentType implements ParserContentType{

	public void parser(MutableMvcRequest request,
			MutableRequestParserEvent requestParserInfo, 
			BeanDecoder beanDecoder, Properties config, Object data) throws BeanDecoderException, 
			IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
        ResourceAction resourceAction = request.getResourceAction();
        Controller controller         = resourceAction.getController();
        Action action                 = resourceAction.getMethodForm();
        
        Object resource = request.getResource();
        
        for(PropertyController prop: controller.getProperties()){
        	
        	if(!prop.canSet()){
        		continue;
        	}
        	
        	Object propValue = prop.decode(beanDecoder, data);
        	prop.setValueInSource(resource, propValue);
        }
        
        if(action != null){
        	List<ParameterAction> actionArgs = action.getParameters();
        	Object[] args = new Object[actionArgs.size()];
        	int index     = 0; 
        	for(ParameterAction arg: actionArgs){
        		args[index++] = arg.decode(beanDecoder, data);
        	}
	        request.setParameters(args);
        }

		
	}
	
}
