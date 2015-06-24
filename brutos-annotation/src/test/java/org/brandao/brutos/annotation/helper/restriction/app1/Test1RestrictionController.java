package org.brandao.brutos.annotation.helper.restriction.app1;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Restriction;
import org.brandao.brutos.annotation.Restrictions;
import org.brandao.brutos.annotation.RestrictionsRules;

@Controller("/controller")
public class Test1RestrictionController {

	@Restriction(rule=RestrictionsRules.MAX, value="10", message="property1 > 10")
	public Integer property1;

	@Restrictions(
			rules={
			@Restriction(rule=RestrictionsRules.MAX, value="10", message="property2 > 10"),
			@Restriction(rule=RestrictionsRules.MIN, value="2", message="property2 < 2")}
	)
	public Integer property2;
	

	public void testAction(
			@Restriction(rule=RestrictionsRules.MAX, value="10", message="arg0 > 10")
			Integer arg0,
			@Restrictions(
					rules={
					@Restriction(rule=RestrictionsRules.MAX, value="10", message="arg1 > 10"),
					@Restriction(rule=RestrictionsRules.MIN, value="2", message="arg1 < 2")}
			)
			Integer arg1){
		
	}
}
