package br.brandao;

import org.brandao.brutos.web.XMLWebApplicationContext;

/**
 *
 * @author Neto
 */
public class ApplicationContext extends XMLWebApplicationContext{

    @Override
    public void flush(){
        this.getConfiguration()
            .setProperty(
                "web.root" ,
                this.getContext().getRealPath( "/" ) );
        super.flush();
    }
    
}
