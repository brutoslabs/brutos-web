package org.brandao.brutos.annotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.brandao.brutos.ClassUtil;

import junit.framework.TestCase;

public class BrutosTestCase extends TestCase{

	public static class Entry<K,V> implements java.util.Map.Entry<K, V>{

		private K key;
		
		private V value;
		
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return this.key;
		}

		public V getValue() {
			return this.value;
		}

		public V setValue(V value) {
			V old = this.value;
			this.value = value;
			return old;
		}
		
	}
	
	public static <K,V> Map<K,V> toMap(Entry<K,V> ... values){
		Map<K,V> m = new HashMap<K, V>();
		
		for(Entry<K,V> v: values){
			m.put(v.getKey(), v.getValue());
		}
		
		return m;
	}

	public static <K,V,L extends Map<K,V>> L toMap(Class<L> type, Entry<K,V> ... values){
		try{
			L m = ClassUtil.getInstance(type);
			
			for(Entry<K,V> v: values){
				m.put(v.getKey(), v.getValue());
			}
			
			return m;
		}
		catch(Throwable e){
			throw new RuntimeException(e);
		}
	}
	
	public static <T,L extends List<T>> L toList(Class<L> type, T ... values){
		try{
			L l = ClassUtil.getInstance(type);
			for(T i: values){
				l.add(i);
			}
			
			return l;
		}
		catch(Throwable e){
			throw new RuntimeException(e);
		}
	}
	
}
