package org.brandao.brutos.annotation.helper.temporal.app1;

import java.util.Calendar;
import java.util.Date;

import org.brandao.brutos.annotation.Temporal;
import org.brandao.brutos.annotation.Transient;

public class Test1TemporalBean {

	@Temporal("yyyy_MM_dd")
	public Calendar property1;
	
	private Date property2;

	@Transient
	public Date property3;
	
	public Test1TemporalBean(Date arg0){
		this.property3 = arg0;
	}
	
	public Date getProperty2() {
		return property2;
	}

	@Temporal("yyyy-MM-dd")
	public void setProperty2(Date property2) {
		this.property2 = property2;
	}
	
}
