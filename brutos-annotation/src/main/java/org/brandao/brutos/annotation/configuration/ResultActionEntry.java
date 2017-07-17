package org.brandao.brutos.annotation.configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.DetachedName;
import org.brandao.brutos.annotation.Enumerated;
import org.brandao.brutos.annotation.Result;
import org.brandao.brutos.annotation.Target;
import org.brandao.brutos.annotation.Temporal;
import org.brandao.brutos.mapping.StringUtil;

public class ResultActionEntry{

	private String name;
	
	private Method method;
	
	public ResultActionEntry(){
	}
	
	public ResultActionEntry(Method method){
		this.method = method;
	}
	
	public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
		return this.method.isAnnotationPresent(annotation);
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotation) {
		return (T)this.method.getAnnotation(annotation);
	}

	public Type getDeclaredGenericType() {
		return this.method.getGenericReturnType();
	}

	public Type getGenericType() {
		Target target = this.getAnnotation(Target.class);
		return target == null ? this.method.getGenericReturnType() : target.value();
	}

	public Class<?> getDeclaredType() {
		return this.method.getReturnType();
	}

	public Class<?> getType() {
		Target target = this.getAnnotation(Target.class);
		return target == null ? this.method.getReturnType() : target.value();
	}

	public org.brandao.brutos.type.Type getTypeInstance(){
		return AnnotationUtil.getTypeInstance(this.getAnnotation(org.brandao.brutos.annotation.Type.class));
	}
	
	public String getTemporalProperty(){
		return AnnotationUtil.getTemporalProperty(this.getAnnotation(Temporal.class));
	}
	
	public EnumerationType getEnumProperty(){
		return AnnotationUtil.getEnumerationType(this.getAnnotation(Enumerated.class));
	}
	
	public String getName() {

		DetachedName notNamed = (DetachedName) this.getAnnotation(DetachedName.class);

		if (notNamed != null)
			return null;

		Basic basic   = this.getAnnotation(Basic.class);
		Result result = this.getAnnotation(Result.class);

		if (basic != null) {
			String name = StringUtil.adjust(basic.bean());
			if (!StringUtil.isEmpty(name))
				return name;
		}

		if (result != null) {
			String name = StringUtil.adjust(result.value());
			if (!StringUtil.isEmpty(name))
				return name;
		}
		
		if (this.name != null) {
			String actionName = StringUtil.adjust(this.name);
			if (!StringUtil.isEmpty(actionName))
				return actionName;

		}

		return this.getDefaultName();//BrutosConstants.DEFAULT_RETURN_NAME;
	}

	public String getDefaultName(){
		return BrutosConstants.DEFAULT_RETURN_NAME;
	}
	
}
