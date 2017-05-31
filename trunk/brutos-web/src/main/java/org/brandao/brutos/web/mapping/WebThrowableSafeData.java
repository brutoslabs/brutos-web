package org.brandao.brutos.web.mapping;

import org.brandao.brutos.mapping.ThrowableSafeData;

public class WebThrowableSafeData 
	extends ThrowableSafeData{

	private int responseError;
	
	private String reason;

	public int getResponseError() {
		return responseError;
	}

	public void setResponseError(int responseError) {
		this.responseError = responseError;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
