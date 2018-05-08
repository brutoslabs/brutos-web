package org.brandao.brutos.web.mapping;

import java.util.List;
import java.util.Map;

public class RequestEntry {

	private RequestMappingEntry requestMappingEntry;
	
	private Map<String,List<String>> parameters;

	public RequestEntry(RequestMappingEntry requestMappingEntry,
			Map<String, List<String>> parameters) {
		super();
		this.requestMappingEntry = requestMappingEntry;
		this.parameters = parameters;
	}

	public RequestMappingEntry getRequestMappingEntry() {
		return requestMappingEntry;
	}

	public void setRequestMappingEntry(RequestMappingEntry requestMappingEntry) {
		this.requestMappingEntry = requestMappingEntry;
	}

	public Map<String, List<String>> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, List<String>> parameters) {
		this.parameters = parameters;
	}

}
