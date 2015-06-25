package org.brandao.brutos.annotation.helper.temporal.app1;

import java.util.Calendar;
import java.util.Date;

import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.Temporal;
import org.brandao.brutos.annotation.Transient;

@Controller("/controller")
public class Test1TemporalController {

	@Temporal("yyyy_MM_dd")
	public Date property1;
	
	private Calendar property2;

	@Transient
	public Date property3;
	
	public Test1TemporalBean property4;
	
	public Calendar getProperty2() {
		return property2;
	}

	@Temporal("yyyy-MM-dd")
	public void setProperty2(Calendar property2) {
		this.property2 = property2;
	}
	
	public void testAction(Date arg0){
		this.property3 = arg0;
	}
}
