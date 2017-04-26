package org.brandao.brutos.annotation.helper.extendedscope.app1;

import java.util.List;

import org.brandao.brutos.annotation.ExtendedScope;
import org.brandao.brutos.scope.Scope;

@ExtendedScope("myScope")
public class DScope 
	implements Scope{

	public void put(String name, Object value) {
		// TODO Auto-generated method stub
		
	}

	public Object get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getCollection(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String name) {
		// TODO Auto-generated method stub
		
	}

	public List<String> getNamesStartsWith(String value) {
		// TODO Auto-generated method stub
		return null;
	}

}
