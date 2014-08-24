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

package org.brandao.brutos.web.http;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.AbstractRenderView;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.RequestInstrument;
import org.brandao.brutos.web.RequestInfo;

/**
 *
 * @author Afonso Brandao
 */
public class JSPRenderView extends AbstractRenderView{
    
    public JSPRenderView() {
    }

    public void configure(Properties properties) {
    }

    protected void show(RequestInstrument requestInstrument,
            String view, DispatcherType dispatcherType) throws IOException {
        try{
            show0(view, dispatcherType);
        }
        catch( BrutosException e ){
            throw e;
        }
        catch( ServletException e ){
            throw new BrutosException(e);
        }

    }
    public void show0(String view, DispatcherType dispatcherType) throws IOException, ServletException {

        if( view == null )
            return;
        
        RequestInfo requestInfo = RequestInfo.getCurrentRequestInfo();

        if( dispatcherType == DispatcherType.FORWARD ){
            requestInfo.getRequest().getRequestDispatcher( view )
                        .forward( requestInfo.getRequest(), requestInfo.getResponse() );
        }
        else
        if( dispatcherType == DispatcherType.INCLUDE ){
            requestInfo.getRequest().getRequestDispatcher( view )
                        .include( requestInfo.getRequest(), requestInfo.getResponse() );
        }
        else
        if( dispatcherType == DispatcherType.REDIRECT )
            ((HttpServletResponse)requestInfo.getResponse()).sendRedirect(view);
        else
            throw new BrutosException( "invalid dispatcher type: " + dispatcherType );
    }

    public void destroy() {
    }
    
}
