package org.brandao.brutos.annotation.helper.importbeans.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.ImportBeans;

@Controller
@ImportBeans({
	BeanTest.class,
	BeanTest2.class,
	BeanTest3.class})
public class ControllerBeanTest {

}
