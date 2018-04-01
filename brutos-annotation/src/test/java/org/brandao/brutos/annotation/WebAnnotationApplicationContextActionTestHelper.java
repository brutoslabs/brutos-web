package org.brandao.brutos.annotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.annotation.web.WebActionStrategyType;

public class WebAnnotationApplicationContextActionTestHelper {

	public static class Values{

		public static final String A = "A";
		
		public static final String B = "B";
		
		public static final Entity entityA = new Entity("A");
		
		public static final Entity entityB = new Entity("B");
		
	}
	
	public static class Entity{
	
		public String property;
		
		@Constructor
		public Entity(){
		}

		public Entity(String property) {
			this.property = property;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((property == null) ? 0 : property.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entity other = (Entity) obj;
			if (property == null) {
				if (other.property != null)
					return false;
			} else if (!property.equals(other.property))
				return false;
			return true;
		}
		
	}

	@ActionStrategy(WebActionStrategyType.DETACHED)
	@Controller
	public static class ActionParamValueTest{
	
		public void actionAction(@Basic(bean="value")Integer value){
		}
		
	}
	
	@ActionStrategy(WebActionStrategyType.DETACHED)
	@Controller
	public static class ActionParamObjectTest{
	
		public void actionAction(Entity value){
		}
		
	}
	
	@ActionStrategy(WebActionStrategyType.DETACHED)
	@Controller
	public static class ActionNameTest{
	
		@Action("/test")
		public void actionAction(@Basic(bean="value")Integer value){
		}
		
	}
	
	@ActionStrategy(WebActionStrategyType.DETACHED)
	@Controller
	public static class ActionParamListObjectTest{
	
		public void listTestAction(@Basic(bean="list", mappingType=MappingTypes.OBJECT)List<Entity> list){
			list.size();
		}
		
	}

	@ActionStrategy(WebActionStrategyType.DETACHED)
	@Controller
	public static class ActionParamListValueTest{
	
		public void actionAction(List<Integer> list){
		}
		
	}
	
	@ActionStrategy(WebActionStrategyType.DETACHED)
	@Controller
	public static class ActionParamMapSimpleKeyIndexTest{
	
		public void actionAction(
				@Basic(mappingType=MappingTypes.OBJECT)
				Map<String,String> map){
			map.size();
		}
		
	}

	@ActionStrategy(WebActionStrategyType.DETACHED)
	@Controller
	public static class ActionParamMapSimpleKeyTest{
	
		public void actionAction(
				@Basic(mappingType=MappingTypes.OBJECT)
				Map<String,String> map){
			map.size();
		}
		
	}
	
	@ActionStrategy(WebActionStrategyType.DETACHED)
	@Controller
	public static class ActionResultValueMapTest{
	
		@Result(mappingType=MappingTypes.VALUE)
		public Map<Entity, String> actionAction(){
			Map<Entity, String> result = new HashMap<Entity, String>();
			result.put(new Entity("A"), "A");
			result.put(new Entity("B"), "B");
			return result;
		}
		
	}
	
}
