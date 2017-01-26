

package org.brandao.brutos.web;

import javax.servlet.ServletRequest;
import org.brandao.brutos.*;


public interface WebMvcRequest extends MvcRequest{

    ServletRequest getServletRequest();

}
