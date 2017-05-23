package org.brandao.brutos.annotation.configuration;

import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.DataType;
import org.brandao.brutos.annotation.AcceptRequestType;
import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.configuration.AbstractAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ActionEntry;
import org.brandao.brutos.annotation.configuration.AnnotationUtil;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.web.WebActionBuilder;
import org.brandao.brutos.web.WebControllerBuilder;

@Stereotype(
	target=AcceptRequestType.class, 
	executeAfter={
		Controller.class,
		Action.class
	}
)
public class AcceptRequestTypeAnnotationConfig 
	extends AbstractAnnotationConfig {

	public boolean isApplicable(Object source) {
		if(source instanceof Class){
			return AnnotationUtil.isController((Class<?>) source);	
		}
		else
		if(source instanceof ActionEntry){
			return
			(((ActionEntry) source).isAnnotationPresent(Action.class) || 
			((ActionEntry) source).getName().endsWith("Action") || 
			((ActionEntry) source).isAbstractAction()) && 
			!((ActionEntry) source).isAnnotationPresent(Transient.class);
		}
		else
			return false;
		
	}

	public Object applyConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		if(source instanceof WebControllerBuilder){
			Class<?> clazz = (Class<?>)source;
			
			if(clazz.isAnnotationPresent(RequestMethod.class)){
				AcceptRequestType rm = clazz.getAnnotation(AcceptRequestType.class);
				WebControllerBuilder b = (WebControllerBuilder)builder;
				String[] values = rm.value();
				for(String v: values){
					b.addRequestType(DataType.valueOf(v));
				}
			}
		}
		else
		if(source instanceof WebActionBuilder){
			ActionEntry action = (ActionEntry)source;
			
			if(action.isAnnotationPresent(RequestMethod.class)){
				AcceptRequestType rm = action.getAnnotation(AcceptRequestType.class);
				WebActionBuilder b = (WebActionBuilder)builder;
				String[] values = rm.value();
				for(String v: values){
					b.addRequestType(DataType.valueOf(v));
				}
			}
		}
		
		return builder;
	}

}
