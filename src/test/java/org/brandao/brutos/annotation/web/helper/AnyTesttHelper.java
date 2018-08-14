package org.brandao.brutos.annotation.web.helper;

import org.brandao.brutos.annotation.ActionStrategy;
import org.brandao.brutos.annotation.Any;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.MetaValue;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.web.WebActionStrategyType;

public class AnyTesttHelper {
	
	public static class PropertyType{
		
		public String commonField;
		
	}

	public static class PropertyTypeA extends PropertyType{

		public String fieldA;
		
	}

	public static class PropertyTypeB extends PropertyType{

		public String fieldB;
		
	}

	public static class BeanAnyField{

		@Any(
			metaBean=@Basic(bean="type"),
			metaValues={
				@MetaValue(name="A", target=PropertyTypeA.class),
				@MetaValue(name="B", target=PropertyTypeB.class)
			}
		)
		public PropertyType property;
		
	}

	public static class BeanAnyProperty{

		private PropertyType property;
		
		@Any(
				metaBean=@Basic(bean="type"),
				metaValues={
					@MetaValue(name="A", target=PropertyTypeA.class),
					@MetaValue(name="B", target=PropertyTypeB.class)
				}
			)
		public void setProperty(PropertyType property){
			this.property = property;
		}
		
		public PropertyType getProperty(){
			return this.property;
		}
		
	}
	
	public static class BeanAnyConstructor{

		@Transient
		public PropertyType property;
		
		public BeanAnyConstructor(
				@Any(
						metaBean=@Basic(bean="type"),
						metaValues={
							@MetaValue(name="A", target=PropertyTypeA.class),
							@MetaValue(name="B", target=PropertyTypeB.class)
						}
					)
				@Basic(bean="property")
				PropertyType property
				){
			this.property = property;
		}
		
	}	
	
	@ActionStrategy(WebActionStrategyType.DETACHED)
	@Controller
	public static class BeanAnyFieldControllerTest{
	
		public void actionAction(@Basic(bean="prop")BeanAnyField field){
		}
		
	}

	@ActionStrategy(WebActionStrategyType.DETACHED)
	@Controller
	public static class BeanAnyPropertyControllerTest{
	
		public void actionAction(@Basic(bean="prop")BeanAnyProperty field){
		}
		
	}

	@ActionStrategy(WebActionStrategyType.DETACHED)
	@Controller
	public static class BeanAnyConstructorControllerTest{
	
		public void actionAction(@Basic(bean="prop")BeanAnyConstructor field){
		}
		
	}
	
}
