

package org.brandao.brutos.web;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.mapping.StringUtil;

public class URIMapping {

    private String originalURI;
    
    private String uriPattern;

    private List<URIParameter> parameters;

    public URIMapping( String uri ) throws MalformedURLException{
        this.parse(uri);
        this.originalURI = uri;
        this.uriPattern  = getURIPattern(null);
    }

    /*
    private void createMap( String uri ) throws MalformedURLException{
        try{
            createMap0(uri);
        }
        catch(Exception e){
            throw new MalformedURLException(e.getMessage() + ": " + uri);
        }
    }
    */
    
    private void parse(String uri) throws MalformedURLException{
        //fragmentos da uri
        List<String> frags  = new ArrayList<String>();
        // identificados detectados
        List<String> ids    = new ArrayList<String>();
        // regex detectados
        List<String> regexs = new ArrayList<String>();
    
        int startFrag      = 0;
        char[] chars       = uri.toCharArray();
        int openKeysCount  = 0;
        int closeKeysCount = 0;
        int firstOpenKeys  = -1;
        int lastCloseKeys  = -1;

        for(int i=0;i<chars.length;i++){
        	
        	if(chars[i] == '{'){
        		
        		if(firstOpenKeys == -1){
        			frags.add(uri.substring(startFrag, i));
        			firstOpenKeys = i;
        		}
        		
        		openKeysCount++;
        	}
        	
        	if(chars[i] == '}'){
        		lastCloseKeys = i;
        		closeKeysCount++;
        	}
        	
        	if(openKeysCount > 0 && openKeysCount == closeKeysCount){
        		
        		
        		String var = uri.substring(firstOpenKeys + 1, lastCloseKeys);

        		int separatorIndex = var.indexOf(":");
        		
        		String id = 
    				var.indexOf(":") == -1?
    					var :
    					var.substring(0, separatorIndex);
    					
		        if(StringUtil.isEmpty(id))
		            throw new MalformedURLException("invalid parameter id " + var);
    					
    			String regex = 
					separatorIndex != -1?
						var.substring(separatorIndex + 1, var.length()) :
							null;
						
				regex = StringUtil.isEmpty(regex)? "\\w{1,}" : regex;
				
				ids.add(id);
				regexs.add(regex);
				
				startFrag      = i + 1;
				firstOpenKeys  = -1;
				lastCloseKeys  = -1;
		        openKeysCount  = 0;
		        closeKeysCount = 0;
        	}
        }
        
        if(openKeysCount > 0 && openKeysCount != closeKeysCount){
            throw new MalformedURLException("expected: }");
        }
        
        if(startFrag >= 0 && startFrag < uri.length()){
        	frags.add(uri.substring(startFrag, uri.length()));
        }
        
        if( frags.size() % 2 == 1 )
            frags.add(null);

        parameters = new ArrayList<URIParameter>();

        for( int i=0;i<ids.size();i++ ){
            parameters.add(
                    new URIParameter(
                        i,
                        (String)ids.get(i),
                        (String)regexs.get(i),
                        (String)frags.get(i),
                        (String)frags.get(i+1) ) );
        }        
    }
    
    /*
    private void createMap0( String uri ) throws MalformedURLException{
        //fragmentos da uri
        List<String> frags = new ArrayList<String>();
        // identificados detectados
        List<String> ids   = new ArrayList<String>();
        // regex detectados
        List<String> regex = new ArrayList<String>();

        //inicio de um identificador
        int index = uri.indexOf("{");
        //identificador
        String id;

        //se index for igual a -1 entao nao existe identificador. Sua definicao é null
        //se index for diferente de null entao existe um identificador. E extraido o
        //fragmento que inicia em 0 e termina em index
        if( index == -1 )
            frags.add( null );
        else
            frags.add( uri.substring( 0, index ) );

        //enquanto index for diferente de -1, a procura por identificadores continua
        while( index != -1 ){
            //fim do identificador
            int index2 = uri.indexOf("}", index );

            id = index+1 < index2? uri.substring( index+1, index2 ) : null;


            if( id == null )
                throw new MalformedURLException();

            //adiciona o identificador
            ids.add(getId(id));
            regex.add(getRegex(id));

            //procura o proximo identificador para obter o proximo fragmento
            int nextIndex = uri.indexOf( "{", index2 );

            if( nextIndex == -1 ){
                nextIndex = uri.length();
            }

            //fragmento atual
            String frag = index2+1 < nextIndex? uri.substring(index2+1, nextIndex) : null;

            //adiciona o fragmento
            frags.add( frag != null? frag.replaceAll("/+", "/") : null );

            index = uri.indexOf("{", index + 1 );
        }

        //se a quantidade de identificadores for impar, entao o ultimo identificador
        // foi encontrado no fim da uri
        if( frags.size() % 2 == 1 )
            frags.add(null);

        parameters = new ArrayList<URIParameter>();

        for( int i=0;i<ids.size();i++ ){
            parameters.add(
                    new URIParameter(
                        i,
                        (String)ids.get(i),
                        (String)regex.get(i),
                        (String)frags.get(i),
                        (String)frags.get(i+1) ) );
        }
    }
    
    private String getId(String value) throws MalformedURLException{
        int index = value.indexOf(":");
        String result= index == -1? value : value.substring(0,index);
        
        if(StringUtil.isEmpty(result))
            throw new MalformedURLException("invalid parameter id " + value);
        else
            return result;
    }

    private String getRegex(String value){
        int index = value.indexOf(":");
        String regex = 
            index == -1? null : value.substring(index+1,value.length());
        
        return StringUtil.isEmpty(regex)? "\\w{1,}" : regex;
    }

    */

    private String getURIPattern(Object[] params){
        String value = null;
        
        if(parameters.isEmpty())
            return this.originalURI;
        
        for(int i=0;i<parameters.size();i++ ){
            URIParameter p = parameters.get(i);
            
            if(i == 0 && p.getStart() != null)
                value = p.getStart();

            if(params == null)
                value += p.getRegex();
            else
            if(params.length == 0)
                value += "(" + p.getId() + ")";
            else
                value += String.valueOf(params[p.getIndex()]);
            
            if(p.getEnd() != null)
                value += p.getEnd();
            
        }
        
        return value;
        
    }
    
    
    public Map<String,List<String>> getParameters(String uri){
        int start = 0;
        int end   = 0;
        Map<String,List<String>> params = new HashMap<String,List<String>>();

        for( int i=0;i<parameters.size();i++ ){
            URIParameter p = (URIParameter)parameters.get(i);
            start = p.getStart() == null? 0 : uri.indexOf( p.getStart(), start ) + p.getStart().length();
            end   = p.getEnd() == null? uri.length() : uri.indexOf( p.getEnd(), start + 1 );

            List<String> values = params.get(p.getId());
            if(values == null){
            	values = new ArrayList<String>();
            	params.put(p.getId(), values);
            }
            
            values.add(uri.substring(start, end));
        }

        return params;
    }

    public String getURI(Object[] params){
        return getURIPattern(params);
    }
    
    public boolean matches( String uri ){
        return uri.matches(this.uriPattern);
    }
    
}
