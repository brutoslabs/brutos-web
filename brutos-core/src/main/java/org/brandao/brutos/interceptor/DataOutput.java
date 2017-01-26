


package org.brandao.brutos.interceptor;

import java.lang.reflect.Field;
import java.util.List;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.PropertyController;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.Scopes;


public class DataOutput {

    private Scope scope;

    public DataOutput(Scope scope) {
        this.scope = scope;
    }

    public void write( Controller form, Object object ){
       try{
            List fields = form.getProperties();
            for( int i=0;i<fields.size();i++ ){
                PropertyController ff = (PropertyController) fields.get(i);
                Object value = ff.getValueFromSource(object);
                if( value == null )
                    ff.getScope().remove(ff.getName());
                else
                    ff.getScope().put(ff.getName(), value);
            }
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
    
    public void writeFields( Controller form, Object object ){
        try{
            Field[] fields = form.getClassType().getDeclaredFields();
            for(int i=0;i<fields.length;i++){
                Field f = fields[i];
                f.setAccessible( true );
                scope.put( f.getName(), f.get( object ) );
            }
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
}
