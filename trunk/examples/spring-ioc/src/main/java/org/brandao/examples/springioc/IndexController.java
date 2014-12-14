/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.brandao.examples.springioc;

import org.brandao.brutos.annotation.ActionStrategy;
import org.brandao.brutos.annotation.ActionStrategyType;
import org.brandao.brutos.annotation.Controller;
import org.springframework.stereotype.Component;

/**
 *
 * @author Cliente
 */

@Component
@Controller(defaultActionName = "index")
@ActionStrategy(ActionStrategyType.DETACHED)
public class IndexController {

    public void indexAction(){
    }
    
}
