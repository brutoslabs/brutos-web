package org.brandao.brutos.annotation.helper.interceptedby.fail;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Intercept;
import org.brandao.brutos.annotation.InterceptedBy;

@Controller("/controller")
@InterceptedBy(@Intercept(name="interceptorNotExist"))
public class Test3InterceptedByFailController {

}
