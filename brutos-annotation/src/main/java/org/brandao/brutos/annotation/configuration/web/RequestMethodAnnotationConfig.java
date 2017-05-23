package org.brandao.brutos.annotation.configuration.web;

import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Stereotype;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.configuration.AbstractAnnotationConfig;
import org.brandao.brutos.annotation.configuration.ActionEntry;
import org.brandao.brutos.annotation.configuration.AnnotationUtil;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.web.RequestMethodType;
import org.brandao.brutos.web.WebActionBuilder;
import org.brandao.brutos.web.WebControllerBuilder;

@Stereotype(
	target=RequestMethod.class, 
	executeAfter={
		Controller.class,
		Action.class
	}
)
public class RequestMethodAnnotationConfig 
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
				RequestMethod rm = clazz.getAnnotation(RequestMethod.class);
				WebControllerBuilder b = (WebControllerBuilder)builder;
				b.setRequestMethod(RequestMethodType.valueOf(rm.value().toUpperCase()));
			}
		}
		else
		if(source instanceof WebActionBuilder){
			ActionEntry action = (ActionEntry)source;
			
			if(action.isAnnotationPresent(RequestMethod.class)){
				RequestMethod rm = action.getAnnotation(RequestMethod.class);
				WebActionBuilder b = (WebActionBuilder)builder;
				b.setRequestMethod(RequestMethodType.valueOf(rm.value().toUpperCase()));
			}
		}
		
		return builder;
	}

}
