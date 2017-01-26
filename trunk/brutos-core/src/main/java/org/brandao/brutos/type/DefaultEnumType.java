

package org.brandao.brutos.type;

import java.io.IOException;
import java.lang.reflect.Array;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.bean.EnumUtil;


public class DefaultEnumType 
    extends AbstractType implements EnumType{

    private EnumerationType type;
    private Type intType;
    private Type stringType;
    private EnumUtil enumUtil;

    public DefaultEnumType() {
        intType    = new IntegerType();
        stringType = new StringType();
    }

    public void setClassType(Class classType) {
        super.setClassType(classType);
        this.enumUtil = new EnumUtil(classType);
    }

    public Object convert(Object value) {
        try{
            if( value == null )
                return null;
            else
            if( this.classType.isAssignableFrom(value.getClass()) )
                return value;
            else
            if(value instanceof String){
            	String tmp = (String)value;
            	Object result = null;
            	
                if( type == EnumerationType.AUTO ){
                	result = this.enumUtil.valueByIndex(tmp);
                	
                	if(result == null)
                		result = this.enumUtil.valueByName(tmp);
                }
                else
                if( type == EnumerationType.ORDINAL ){
                    result = this.enumUtil.valueByIndex(tmp);
                }
                else
                    result = this.enumUtil.valueByName(tmp);

            	if(result == null)
            		throw new UnknownTypeException("enum not found: " + this.classType.getName() + "." + value);
            	else
            		return result;
            }
            else
            	throw new UnknownTypeException(value.getClass().getName());
        }
        catch( UnknownTypeException e ){
        	throw e;
        }
        catch( Exception e ){
            throw new UnknownTypeException(e);
        }
    }

    public void show(MvcResponse response, Object value) throws IOException {
        response.process(value);
    }

    public EnumerationType getEnumerationType() {
        return this.type;
    }

    public void setEnumerationType(EnumerationType type) {
        this.type = type;
    }

}
