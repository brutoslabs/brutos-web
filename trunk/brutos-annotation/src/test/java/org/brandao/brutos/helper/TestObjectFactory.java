/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.brandao.brutos.helper;

import java.util.Properties;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ObjectFactory;

/**
 *
 * @author Brandao
 */
public class TestObjectFactory implements ObjectFactory{

    public Object getBean(String name) {
        return null;
    }

    public Object getBean(Class clazz) {
        try{
            return clazz.newInstance();
        } 
        catch (Exception e) {
            throw new BrutosException(e);
        } 
    }

    public void configure(Properties properties) {
    }

    public void destroy() {
    }
    
}
