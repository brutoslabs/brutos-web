package org.brandao.brutos.mapping;

public class ResultAction extends UseBeanData{

	private Action action;
	
	public ResultAction(Action action){
		this.action = action;	
	}
	
	@Override
	protected void validate(Object source, Object value) {
	}

	public Action getAction() {
		return action;
	}

}
