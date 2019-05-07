package org.brandao.brutos.web.mapping;

import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.ThrowableSafeData;

public class WebThrowableSafeData 
	extends ThrowableSafeData{

	public WebThrowableSafeData(Action action) {
		super(action);
		super.action = new ThrowWebAction();
	}

	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
