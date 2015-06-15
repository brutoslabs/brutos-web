package org.brandao.brutos.annotation.helper.restriction.fail;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Restriction;
import org.brandao.brutos.annotation.RestrictionsRules;

@Controller("/cotnroller")
public class Test1FailRestrictionController {

	@Restriction(rule=RestrictionsRules.MAX, value="10", message="property1 > 10")
	public String property1;

}
