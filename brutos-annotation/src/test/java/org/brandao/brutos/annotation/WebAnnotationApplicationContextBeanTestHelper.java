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

	public static class Values{

		public static final Integer otherIntegerValue 		= 2;
			
		public static final Integer integerValue 			= 1;
		
		public static final int otherIntValue 				= 2;
		
		public static final int intValue 					= 1;
		
		public static final EnumValues otherEnumValue 		= EnumValues.VALUE2;
		
		public static final EnumValues enumValue 			= EnumValues.VALUE1;
		
		public static final String otherStringValue 		= "2000-01-02";
		
		public static final String stringValue 				= "2000-01-01";

		public static final String otherBRDateStringValue	= "02/01/2000";
		
		public static final String brDateStringValue 		= "01/01/2000";
		
		public static final Date dateValue;

		public static final Date otherDateValue;
		
		public static final ConstructorTest otherConstructorTestValue = new ConstructorTest(otherIntValue);
		
		public static final ConstructorTest constructorTestValue = new ConstructorTest(intValue);
		
		static{
			Calendar cal = GregorianCalendar.getInstance();
			cal.set(Calendar.DAY_OF_YEAR, 1);
			cal.set(Calendar.YEAR, 2000);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			
			dateValue = cal.getTime();
			
			cal.set(Calendar.DAY_OF_YEAR, 2);
			
			otherDateValue = cal.getTime();
		}
		
	}
	
	@ActionStrategy(WebActionStrategyType.DETACHED)
	@Controller
	public static class ControllerTest{
	
		public void fieldTestAction(@Basic(bean="fieldTest")FieldTest fieldTest){
		}
		
		public void enumTestAction(@Basic(bean="enumTest")EnumTest enumTest){
		}
		
		public void dateTestAction(@Basic(bean="dateTest")DateTest dateTest){
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

	public static class EnumTest{

		public EnumValues propertyA;
		
		@Enumerated(EnumerationType.ORDINAL)
		public EnumValues propertyB;
		
		@Enumerated(EnumerationType.STRING)
		public EnumValues propertyC;

		@Basic(bean="property")
		public EnumValues propertyD;
		
		@Basic(bean="property2")
		@Enumerated(EnumerationType.ORDINAL)
		public EnumValues propertyE;
		
	}

	public static class DateTest{

		public Date propertyA;
		
		@Temporal(value="dd/MM/yyyy")
		public Date propertyB;
		
		@Basic(bean="property")
		@Temporal(value="dd/MM/yyyy")
		public Date propertyC;
		
	}
	
	public static class ListTest{

		public List<Integer> propertyA;
		
		@Basic(mappingType=MappingTypes.OBJECT)
		@ElementCollection(bean="itens")
		public List<Integer> propertyB;

		public List<EnumValues> propertyC;
		
		@Basic(mappingType=MappingTypes.OBJECT)
		@ElementCollection(bean="itens")
		public List<EnumValues> propertyD;

		@Basic(mappingType=MappingTypes.OBJECT)
		@ElementCollection(enumerated=EnumerationType.ORDINAL)
		//ElementCollection não se aplica somente a um mapeamento
		public List<EnumValues> propertyE;
		
		@Basic(mappingType=MappingTypes.OBJECT)
		@ElementCollection(bean="itens", enumerated=EnumerationType.ORDINAL)
		public List<EnumValues> propertyF;
		
		public List<Date> propertyG;
		
		@Basic(mappingType=MappingTypes.OBJECT)
		@ElementCollection(bean="itens")
		public List<Date> propertyH;

		@Basic(mappingType=MappingTypes.OBJECT)
		@ElementCollection(temporal="dd/MM/yyyy")
		public List<Date> propertyI;
		
		@Basic(mappingType=MappingTypes.OBJECT)
		@ElementCollection(bean="itens", temporal="dd/MM/yyyy")
		public List<Date> propertyJ;
		
		@Basic(mappingType=MappingTypes.OBJECT)
		public List<ConstructorTest> propertyK;
		
		@Basic(mappingType=MappingTypes.OBJECT)
		@ElementCollection(bean="itens")
		public List<ConstructorTest> propertyL;

		//Não aplicável. Precisa do tipo CustomArrayList registrado.
		//public List<CustomArrayList> propertyM;
		
		@Basic(mappingType=MappingTypes.OBJECT)
		@ElementCollection(bean="itens")
		public List<CustomArrayList> propertyN;

		//Sem efeito. Target somente funciona com mapeamento e não com valor.
		//@Target(LinkedList.class)
		//public List<Integer> propertyO;
		
		@Basic(mappingType=MappingTypes.OBJECT)
		@SuppressWarnings("rawtypes")
		@ElementCollection(bean="itens", target=Integer.class)
		public List propertyP;

	}

	public static class MapElementTest{

		public Map<String, Integer> propertyA;
		
		@ElementCollection(bean="itens")
		public Map<String, Integer> propertyB;

		public Map<String, EnumValues> propertyC;
		
		@ElementCollection(bean="itens")
		public Map<String, EnumValues> propertyD;

		@ElementCollection(enumerated=EnumerationType.ORDINAL)
		public Map<String, EnumValues> propertyE;
		
		@ElementCollection(bean="itens", enumerated=EnumerationType.ORDINAL)
		public Map<String, EnumValues> propertyF;
		
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

		//CustomArrayList tem que ser um tipo
		//public Map<String, CustomArrayList> propertyM;
		
		@ElementCollection(bean="itens")
		public Map<String, CustomArrayList> propertyN;
		
		//Sem efeito. Target somente funciona com mapeamento e não com valor.
		//@Target(CustomMap.class)
		//public Map<String, ConstructorTest> propertyO;
		
		@Target(CustomMap.class)
		@ElementCollection(bean="itens")
		public Map<String, ConstructorTest> propertyP;
		
	}

	public static class MapKeyTest{

		public Map<Integer, String> propertyA;
		
		@KeyCollection(bean="chaves")
		public Map<Integer, String> propertyB;

		public Map<EnumValues, String> propertyC;
		
		@KeyCollection(bean="chaves")
		public Map<EnumValues, String> propertyD;

		@KeyCollection(enumerated=EnumerationType.ORDINAL)
		public Map<EnumValues,String> propertyE;
		
		@KeyCollection(bean="chaves", enumerated=EnumerationType.ORDINAL)
		public Map<EnumValues, String> propertyF;
		
		public Map<Date, String> propertyG;
		
		@KeyCollection(bean="chaves")
		public Map<Date, String> propertyH;

		@KeyCollection(temporal="dd/MM/yyyy")
		public Map<Date, String> propertyI;
		
		@KeyCollection(bean="chaves", temporal="dd/MM/yyyy")
		public Map<Date, String> propertyJ;
		
		//Não aplicável
		//@KeyCollection.bean é obrigatório
		//public Map<ConstructorTest, String> propertyK;
		
		@KeyCollection(bean="chaves")
		public Map<ConstructorTest, String> propertyL;

		//Não aplicável
		//@KeyCollection.bean é obrigatório
		//public Map<CustomArrayList, String> propertyM;
		
		@KeyCollection(bean="chaves")
		public Map<CustomArrayList, String> propertyN;
		
		//@Target(LinkedHashMap.class)
		//public Map<ConstructorTest, String> propertyO;
		
		@Target(CustomMap.class)
		@KeyCollection(bean="chaves")
		public Map<ConstructorTest, String> propertyP;
		
	}
	
	public enum EnumValues {

	    VALUE1,
	    
	    VALUE2;
	    
	}
	
	public static class ConstructorTest {
	    
		@Transient
		public int propertyA;
		
	    public ConstructorTest(@Basic(bean="property")int propertyA){
	    	this.propertyA = propertyA;
	    }

		public int getPropertyA() {
			return propertyA;
		}

		public void setPropertyA(int propertyA) {
			this.propertyA = propertyA;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + propertyA;
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
			ConstructorTest other = (ConstructorTest) obj;
			if (propertyA != other.propertyA)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "ConstructorTest [propertyA=" + propertyA + "]";
		}
	    
	}

	@ElementCollection(bean="myElement",mappingType=MappingTypes.OBJECT)
	public static class CustomArrayList extends ArrayList<ConstructorTest>{
		
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
	@ElementCollection(bean="elements", mappingType=MappingTypes.OBJECT)
	public static class CustomMap 
		extends HashMap<String, ConstructorTest>{
	
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
