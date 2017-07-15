package org.brandao.brutos.annotation.configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class ResultActionBeanEntry 
	extends ResultActionEntry 
	implements BeanEntry{

	private ResultActionEntry o;
	
	public ResultActionBeanEntry(ResultActionEntry o){
		this.o = o;
	}
	
	public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
		return this.o.isAnnotationPresent(annotation);
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotation) {
		return this.o.getAnnotation(annotation);
	}

	public Type getDeclaredGenericType() {
		return this.o.getDeclaredGenericType();
	}

	public Type getGenericType() {
		return this.o.getGenericType();
	}

	public Class<?> getDeclaredType() {
		return this.o.getDeclaredType();
	}

	public Class<?> getType() {
		return this.o.getType();
	}

	public String getName() {
		return this.o.getName();
	}

	public String getDefaultName(){
		return this.o.getDefaultName();
	}
	
	public Class<?> getBeanType() {
		return this.getType();
	}
	
}
