

package org.brandao.brutos.web;

import javax.servlet.ServletResponse;
import org.brandao.brutos.*;


public interface WebMvcResponse extends MvcResponse{

    ServletResponse getServletResponse();
    
}
