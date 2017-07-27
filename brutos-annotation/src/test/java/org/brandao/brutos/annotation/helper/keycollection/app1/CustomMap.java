package org.brandao.brutos.annotation.helper.keycollection.app1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.brandao.brutos.annotation.KeyCollection;
import org.brandao.brutos.annotation.Transient;

@SuppressWarnings({"serial", "rawtypes"})
@KeyCollection(bean="subKey")
public class CustomMap
	extends HashMap<Integer,String>{

	@Transient
	private Collection values;
	
	@Transient
	private boolean useAltHashing;

	@Transient
	private Set entrySet;
	
	@Transient
	private boolean empty;
	
	@Transient
	private Set keySet;
	
	@Transient
	private Map.Entry table;
	
	@Transient
	private int modCount;
	
	@Transient
	private int threshold;
	
	@Transient
	private int size;
	
}
