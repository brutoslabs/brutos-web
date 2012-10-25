/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2012 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.brandao.brutos.interceptor;

import java.util.List;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.mapping.PropertyController;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.scope.Scope;

/**
 *
 * @author Afonso Brandao
 */
public class DataInput {
    
    private Scope scope;

    public DataInput( Scope requestScope ) {
        this.scope = requestScope;
    }
    
    public void read( Controller form, Object object ){
        try{
            List fields = form.getProperties();
            for( int i=0;i<fields.size();i++ ){
                PropertyController ff = (PropertyController) fields.get(i);
            //for( FieldForm ff: form.getFields() ){
                //Object val = ff.getBean().getValue( context, request );
                Object val = ff.getBean().getValue();
                if( val != null )
                    ff.setValue( object, val );
            }
            setDataWebFrame( form, object );
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( Exception e ){
            throw new BrutosException( e );
        }
    }
    
    private void setDataWebFrame( Controller form, Object wf ){
        /*
        if( wf instanceof WebFrame ){
            WebFrame w = (WebFrame)wf;
            w.setName( form.getId() );
            w.setPage( form.getPage() );
            w.setDefaultMethodName( form.getDefaultMethodName() );
            w.setMethodParameterName( form.getMethodId() );
            w.setRequest( request );
            w.setResponse( response );
            w.setSession( request.getSession() );
            w.setServletContext( context );
            w.setUpdatable( true );
        }
        */
        //request.setAttribute( BrutosConstants.WEBFRAME, wf );
        scope.put( BrutosConstants.WEBFRAME, wf );
        scope.put( BrutosConstants.CONTROLLER, wf );
    }
}
