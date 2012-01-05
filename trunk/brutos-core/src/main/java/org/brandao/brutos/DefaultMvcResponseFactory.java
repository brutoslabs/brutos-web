/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.brutos;

/**
 *
 * @author Brandao
 */
public class DefaultMvcResponseFactory extends MvcResponseFactory{

    protected MvcResponse getNewResponse() {
        return new DefaultMvcResponse();
    }

}
