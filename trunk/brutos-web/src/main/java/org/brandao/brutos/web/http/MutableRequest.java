

package org.brandao.brutos.web.http;


public interface MutableRequest {
    
    public void setParameter( String name, String value );
    
    public void setParameters( String name, String[] values );

    public void setObject( String name, Object value );
    
    public void setObjects( String name, Object[] value );

}
