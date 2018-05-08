package org.brandao.brutos.web.scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.brandao.brutos.MutableMvcRequest;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.http.ParameterList;

public class HeaderScope implements Scope{

	private static final ThreadLocal<MutableMvcRequest> currentRequest =
			new ThreadLocal<MutableMvcRequest>();
	
    public static void setRequest(MutableMvcRequest value){
    	currentRequest.set(value);
    }

    public static void removeRequest(MutableMvcRequest value){
    	currentRequest.remove();
    }

    public void put(String name, Object value) {
    	MutableMvcRequest request = currentRequest.get();
        request.setHeader(name, value);
    }

    public Object get(String name) {
    	MutableMvcRequest request = currentRequest.get();
        return request.getHeader(name);
    }

    public Object getCollection( String name ){
    	MutableMvcRequest request = currentRequest.get();
        return new ParameterList(
                Arrays.asList(request.getHeader(name)));
    }

    public void remove( String name ){
    }

	public List<String> getNamesStartsWith(String value) {
    	MutableMvcRequest request = currentRequest.get();
		
		List<String> result = new ArrayList<String>();
		
		Set<String> names = 
				request.getHeadersNames();
		
		for(String name: names){
			if(name.startsWith(value)){
				result.add(name);
			}
		}
		
		return result;
	}

}
