package org.brandao.brutos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.annotation.configuration.MetaValuesDefinition;

/**
 * Permite mapear uma associação para mais de um tipo de entidade.
 * 
 * <pre>
 * Ex:
 * 
 * &#064;Controller("/controller")
 * public class PropertyController{
 *
 *    public void saveProperty(
 *        &#064;Basic(bean="property")
 *        &#064;Any(
 *            metaBean=&#064;Basic(bean="property_type")
 *            metaValues={
 *                &#064;MetaValue(name="Decimal", target=DecimalProperty.class),
 *                &#064;MetaValue(name="Set”, target=SetProperty.class)
 *            }
 *        )
 *        Property property){
 *        ...
 *    }
 * }
 *
 * public interface Property{
 *
 *     Object getName();
 *
 * }
 *
 * public abstract class AbstractProperty{
 *    
 *     private String name;
 *
 *     public Stirng getName(){
 *         return this.name;
 *     }
 *
 * }
 *
 * public class DecimalProperty extends AbstractProperty{
 *     
 *     private int length;
 *
 *     private int decimals;
 *     ...
 * }
 *
 * public class SetProperty extends AbstractProperty {
 *     
 *     private List &#60;String&#62; values;
 *     ...
 * }
 *
 * </pre>
 * 
 * @author Brandao
 * 
 */
@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Any {

	/**
	 * Mapeamento dos metadados.
	 */
	Basic metaBean();

	/**
	 * Tipo dos metadados.
	 */
	Class<?> metaType() default void.class;

    /**
     * Usado se os metadados forem do tipo enum. Os valores estão 
     * descritos em {@link EnumerationType}.
     */
    EnumerationType metaEnumerated() default EnumerationType.ORDINAL;
    
    /**
     * Usado se os metadados forem do tipo {@link java.util.Date} ou {@link java.util.Calendar}.
     * Deve seguir o padrão definido em {@link java.text.SimpleDateFormat}.
     */
    String metaTemporal() default BrutosConstants.DEFAULT_TEMPORALPROPERTY;
	
	/**
	 * Especificação dos tipos de mapeamentos.
	 */
	MetaValue[] metaValues() default {};

	/**
	 * Permite, em tempo de execução, definir os tipos de mapeamento.
	 */
	Class<? extends MetaValuesDefinition> metaValuesDefinition() default MetaValuesDefinition.class;
	
    /**
     * Tipo dos metadados.
     */
    Class<? extends org.brandao.brutos.type.Type> metaTypeDef() default org.brandao.brutos.type.Type.class;
	
}
