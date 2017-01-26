

package org.brandao.brutos.web.http;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletRequest;


public interface BrutosRequest 
    extends MutableRequest,ServletRequest{

    Object getObject( String name );
    
    List<Object> getObjects( String name );

    UploadListener getUploadListener();

    void parseRequest() throws IOException;

    ServletRequest getServletRequest();
    
    void setServletRequest(ServletRequest request);

    String getRequestId();
    
}
