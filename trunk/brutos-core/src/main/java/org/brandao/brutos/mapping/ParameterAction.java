package org.brandao.brutos.mapping;

public class ParameterAction extends UseBeanData {

	private Action action;

	public ParameterAction(Action action) {
		this.action = action;
	}

	protected void validate(Object source, Object value) {
		if (this.validate != null)
			this.validate.validate(this, source, value);
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

}
