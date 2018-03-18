package org.brandao.brutos.annotation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.brandao.brutos.annotation.web.WebActionStrategyType;

public class WebAnnotationApplicationContextBeanTestHelper {

	public static final Date testDate;
	
	static{
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.YEAR, 2000);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		testDate = cal.getTime();
	}
	
	@ActionStrategy(WebActionStrategyType.DETACHED)
	@Controller
	public static class ControllerTest{
	
		public void fieldTestAction(@Basic(bean="fieldTest")FieldTest fieldTest){
		}
		
		public void enumBeanTestAction(@Basic(bean="enumBeanTest")EnumBeanTest enumBeanTest){
		}
		
		public void dateBeanTestAction(@Basic(bean="dateBeanTest")DateBeanTest dateBeanTest){
		}
		
		public void listTestAction(@Basic(bean="listTest")ListTest listTest){
		}

		public void mapElementTestAction(@Basic(bean="mapElementTest")MapElementTest mapElementTest){
		}
		
		public void mapKeyTestAction(@Basic(bean="mapKeyTest")MapKeyTest mapKeyTest){
		}
		
	}
	
	public static class FieldTest{

		public int propertyA;
		
		public String propertyB;
		
		public Date propertyC;
		
		public Integer propertyD;

		@Basic(bean="property")
		public Integer propertyE;
		
		@Basic(bean="proprty", scope=ScopeType.SESSION)
		public Integer propertyF;
		
	}

	public static class EnumBeanTest{

		public EnumTest propertyA;
		
		@Enumerated(EnumerationType.ORDINAL)
		public EnumTest propertyB;
		
		@Enumerated(EnumerationType.STRING)
		public EnumTest propertyC;

		@Basic(bean="property")
		public EnumTest propertyD;
		
		@Basic(bean="property")
		@Enumerated(EnumerationType.ORDINAL)
		public EnumTest propertyE;
		
	}

	public static class DateBeanTest{

		public Date propertyA;
		
		@Temporal(value="yyyy-MM-dd")
		public Date propertyB;
		
		@Basic(bean="property")
		@Temporal(value="yyyy-MM-dd")
		public Date propertyC;
		
	}
	
	public static class ListTest{

		public List<Integer> propertyA;
		
		@ElementCollection(bean="itens")
		public List<Integer> propertyB;

		public List<EnumTest> propertyC;
		
		@ElementCollection(bean="itens")
		public List<EnumTest> propertyD;

		@ElementCollection(enumerated=EnumerationType.ORDINAL)
		public List<EnumTest> propertyE;
		
		@ElementCollection(bean="itens", enumerated=EnumerationType.ORDINAL)
		public List<EnumTest> propertyF;
		
		public List<Date> propertyG;
		
		@ElementCollection(bean="itens")
		public List<Date> propertyH;

		@ElementCollection(temporal="dd/MM/yyyy")
		public List<Date> propertyI;
		
		@ElementCollection(bean="itens", temporal="dd/MM/yyyy")
		public List<Date> propertyJ;
		
		public List<ConstructorTest> propertyK;
		
		@ElementCollection(bean="itens")
		public List<ConstructorTest> propertyL;

		public List<CustomArrayList> propertyM;
		
		@ElementCollection(bean="itens")
		public List<CustomArrayList> propertyN;

		@Target(CustomArrayList.class)
		public List<Integer> propertyO;
		
		@Target(CustomArrayList.class)
		@ElementCollection(bean="itens")
		public List<Integer> propertyP;
		
	}

	public static class MapElementTest{

		public Map<String, Integer> propertyA;
		
		@ElementCollection(bean="itens")
		public Map<String, Integer> propertyB;

		public Map<String, EnumTest> propertyC;
		
		@ElementCollection(bean="itens")
		public Map<String, EnumTest> propertyD;

		@ElementCollection(enumerated=EnumerationType.ORDINAL)
		public Map<String, EnumTest> propertyE;
		
		@ElementCollection(bean="itens", enumerated=EnumerationType.ORDINAL)
		public Map<String, EnumTest> propertyF;
		
		public Map<String, Date> propertyG;
		
		@ElementCollection(bean="itens")
		public Map<String, Date> propertyH;

		@ElementCollection(temporal="dd/MM/yyyy")
		public Map<String, Date> propertyI;
		
		@ElementCollection(bean="itens", temporal="dd/MM/yyyy")
		public Map<String, Date> propertyJ;
		
		public Map<String, ConstructorTest> propertyK;
		
		@ElementCollection(bean="itens")
		public Map<String, ConstructorTest> propertyL;

		public Map<String, CustomArrayList> propertyM;
		
		@ElementCollection(bean="itens")
		public Map<String, CustomArrayList> propertyN;
		
		@Target(CustomMap.class)
		public Map<String, BeanConstructorTest> propertyO;
		
		@Target(CustomMap.class)
		@ElementCollection(bean="itens")
		public Map<String, BeanConstructorTest> propertyP;
		
	}

	public static class MapKeyTest{

		public Map<Integer, String> propertyA;
		
		@KeyCollection(bean="itens")
		public Map<Integer, String> propertyB;

		public Map<EnumTest, String> propertyC;
		
		@KeyCollection(bean="itens")
		public Map<EnumTest, String> propertyD;

		@KeyCollection(enumerated=EnumerationType.ORDINAL)
		public Map<EnumTest,String> propertyE;
		
		@KeyCollection(bean="itens", enumerated=EnumerationType.ORDINAL)
		public Map<EnumTest, String> propertyF;
		
		public Map<Date, String> propertyG;
		
		@KeyCollection(bean="itens")
		public Map<Date, String> propertyH;

		@KeyCollection(temporal="dd/MM/yyyy")
		public Map<Date, String> propertyI;
		
		@KeyCollection(bean="itens", temporal="dd/MM/yyyy")
		public Map<Date, String> propertyJ;
		
		//Não aplicável
		//@KeyCollection.bean é obrigatório
		//public Map<ConstructorTest, String> propertyK;
		
		@KeyCollection(bean="itens")
		public Map<ConstructorTest, String> propertyL;

		//Não aplicável
		//@KeyCollection.bean é obrigatório
		//public Map<CustomArrayList, String> propertyM;
		
		@KeyCollection(bean="itens")
		public Map<CustomArrayList, String> propertyN;
		
		@Target(CustomMap.class)
		public Map<String, BeanConstructorTest> propertyO;
		
		@Target(CustomMap.class)
		@KeyCollection(bean="itens")
		public Map<String, BeanConstructorTest> propertyP;
		
	}
	
	public enum EnumTest {

	    VALUE1,
	    
	    VALUE2;
	    
	}
	
	public static class BeanConstructorTest {
	    
		@Transient
		public int propertyA;
		
	    public BeanConstructorTest(@Basic(bean="property")int propertyA){
	    	this.propertyA = propertyA;
	    }

		public int getPropertyA() {
			return propertyA;
		}

		public void setPropertyA(int propertyA) {
			this.propertyA = propertyA;
		}
	    
	}

	@ElementCollection(bean="myElement",mappingType=MappingTypes.COMPLEX)
	public static class CustomArrayList extends ArrayList<BeanConstructorTest>{
		
		private static final long serialVersionUID = -8645119830023527667L;

		@Transient
		public Object[] elementData;
		
		@Transient
		public boolean empty;
		
		@Transient
		public int size;
		
		@Transient
		public int modCount;
		
		public CustomArrayList(){
			super();
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	@KeyCollection(bean="keys")
	@ElementCollection(bean="elements", mappingType=MappingTypes.COMPLEX)
	public static class CustomMap 
		extends HashMap<String, BeanConstructorTest>{
	
		private static final long serialVersionUID = 7978559428377788179L;
		
		@Transient
		public boolean isEmpty(){
			return super.isEmpty();
		}
		
		@Override
		public boolean equals(Object arg0){
			return super.equals(arg0);
		}
		
		@Override
		public int hashCode(){
			return super.hashCode();
		}
		
		@Transient
		public void setValues(Collection v){
		}
	
		@Transient
		public void setUseAltHashing(boolean v){
		}
		
		@Transient
		public void setEntrySet(Set v){
		}
		
		@Transient
		public void setEmpty(boolean v){
		}
	
		@Transient
		public void setKeySet(Set v){
		}
	
		@Transient
		public void setTable(java.util.Map.Entry<Object, Object>[] e){
		}
		
		@Transient
		public void setModCount(int v){
		}
		
		@Transient
		public void setThreshold(int v){
			
		}
		@Transient
		public void setSize(int v){
			
		}
	}
	
}
