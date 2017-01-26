

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
