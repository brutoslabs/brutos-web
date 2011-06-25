/*
  Brutos Web MVC http://brutos.sourceforge.net/
  Copyright (C) 2009 Afonso Brandão. (afonso.rbn@gmail.com)
*/
package br.brandao.type;

import br.brandao.beans.MyBean;
import br.brandao.controller.IndexController;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.brandao.brutos.mapping.Bean;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.type.Type;
import org.brandao.brutos.web.ConfigurableWebApplicationContext;
import org.brandao.brutos.web.ContextLoader;

/**
 *
 * @author Afonso Brandao
 */
public class MyBeanType implements Type{

    public Class getClassType() {
        return MyBean.class;
    }

    public Object getValue(Object value) {
        ConfigurableWebApplicationContext context =
            (ConfigurableWebApplicationContext)
                ContextLoader.getCurrentWebApplicationContext();
        Controller controller =
                context.getControllerManager()
                    .getController(IndexController.class);
        Bean mapping = controller.getMappingBean( "myBean" );
        return mapping.getValue();
    }

    public void setValue(Object value) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
