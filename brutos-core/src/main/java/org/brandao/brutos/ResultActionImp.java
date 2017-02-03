package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

public class ResultActionImp implements ConfigurableResultAction {

	private Map infos;

	private Map values;

	private Class contentType;

	private Object content;

	private String view;

	private boolean resolved;

	public ResultActionImp() {
		this.infos = new HashMap();
		this.values = new HashMap();
	}

	public String getView() {
		return this.view;
	}

	public boolean isResolvedView() {
		return this.resolved;
	}

	public Class getContentType() {
		return this.contentType;
	}

	public Object getContent() {
		return this.content;
	}

	public Map getValues() {
		return this.values;
	}

	public void setValues(Map values) {
		this.values = values;
	}

	public Map getInfos() {
		return this.infos;
	}

	public void setInfos(Map infos) {
		this.infos = infos;
	}

	public ResultAction setView(String view) {
		return this.setView(view, false);
	}

	public ResultAction setView(String view, boolean resolved) {
		if (this.content != null || this.contentType != null)
			throw new IllegalStateException();
		else {
			this.view = view;
			return this;
		}
	}

	public ResultAction setContentType(Class type) {
		if (this.view != null)
			throw new IllegalStateException();
		else {
			this.contentType = type;
			return this;
		}
	}

	public ResultAction addInfo(String name, String o) {
		this.infos.put(name, o);
		return this;
	}

	public ResultAction setContent(Object value) {
		if (this.view != null)
			throw new IllegalStateException();
		else {
			this.content = value;
			return this;
		}
	}

	public ResultAction add(String name, Object o) {
		this.values.put(name, o);
		return this;
	}

}
