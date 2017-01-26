


package org.brandao.brutos;

import java.awt.Component;
import java.io.IOException;
import java.util.Properties;


public class DefaultRenderView extends AbstractRenderView{

    public void configure(Properties properties) {
    }

    protected void show(RequestInstrument requestInstrument,
            String view, DispatcherType dispatcherType) throws IOException {

        ObjectFactory objectFactory = requestInstrument.getObjectFactory();

        Object objectView = objectFactory.getBean(view);

        if( objectView instanceof Component )
            ((Component)objectView).setVisible(true);
        
    }

    public void destroy() {
    }

}
