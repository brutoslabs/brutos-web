package org.brandao.brutos.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.ActionTypeResolver;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.scope.Scope;

public abstract class AbstractWebActionTypeResolver implements ActionTypeResolver{

    private static Map<String,StringPattern> uris = new HashMap<String, StringPattern>();

    protected static StringPattern getURIMapping( String uri ){
        try{
            if( uris.containsKey( uri ) )
                return uris.get( uri );
            else{
                StringPattern map = new StringPattern( uri );
                uris.put( uri , map);
                return map;
            }
        }
        catch( Exception e ){
            throw new BrutosException( e.getMessage(), e );
        }
    }
    
    protected void updateRequest(String uri, Scope paramScope, StringPattern uriMap){
        Map<String,List<String>> params = uriMap.getParameters(uri);
        for(String key: params.keySet() ){
        	for(String value: params.get(key)){
        		paramScope.put(key, value);
        	}
        }
    }
    
}
