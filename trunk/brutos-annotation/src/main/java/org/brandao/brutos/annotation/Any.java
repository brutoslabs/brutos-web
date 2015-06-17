package org.brandao.brutos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.annotation.configuration.MetaValuesDefinition;

/**
 * Permite mapear uma associação para mais de um tipo de bean. 
 * Em um bean ela pode ser usada nas propriedades ou nos argumentos 
 * de um construtor. Em um controlador ela pode ser usada nas propriedades 
 * ou nos parâmetros de uma ação.
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
	 * Variável que representa as informações de meta dados.
	 */
	Basic metaBean() default @Basic;

	/**
	 * Tipo da variável que representa as informações de meta dados.
	 */
	Class<?> metaType() default String.class;

    /**
     * Usado em tipos enum. Os valores estão 
     * descritos em {@link EnumerationType}.
     */
    EnumerationType metaEnumerated() default EnumerationType.ORDINAL;
    
    /**
     * Usado em tipos {@link java.util.Date} e {@link java.util.Calendar}.
     * Deve seguir o padrão definido em {@link java.text.SimpleDateFormat}.
     */
    String metaTemporal() default BrutosConstants.DEFAULT_TEMPORALPROPERTY;
	
	/**
	 * Associa um valor de informação de meta dados a um bean.
	 * @return
	 */
	MetaValue[] metaValues() default {};

	/**
	 * Permite em tempo de execução associar um valor de informação de meta dados a um bean.
	 */
	Class<? extends MetaValuesDefinition> metaValuesDefinition() default MetaValuesDefinition.class;
	
}
