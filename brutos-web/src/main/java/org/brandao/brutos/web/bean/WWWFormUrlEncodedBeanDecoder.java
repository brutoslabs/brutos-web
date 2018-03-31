package org.brandao.brutos.web.bean;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.brandao.brutos.FetchType;
import org.brandao.brutos.mapping.AbstractBeanDecoder;
import org.brandao.brutos.mapping.CollectionBean;
import org.brandao.brutos.mapping.DependencyBean;
import org.brandao.brutos.mapping.Element;
import org.brandao.brutos.mapping.Key;
import org.brandao.brutos.mapping.MapBean;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.MetaBean;
import org.brandao.brutos.mapping.NodeBeanDecoder;
import org.brandao.brutos.mapping.UseBeanData;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.type.ArrayType;
import org.brandao.brutos.type.CollectionType;
import org.brandao.brutos.type.Type;

public class WWWFormUrlEncodedBeanDecoder
	extends AbstractBeanDecoder {

	@Override
	protected Object getNextDataLevel(String name, Object data) {
		return null;
	}

	@Override
	protected Object getValue(MetaBean entity, StringBuilder path, NodeBeanDecoder node) {
		Object metaValue = entity.getScope().get(path.toString());
		return entity.getType().convert(metaValue);
	}

	@Override
	protected Object getValue(UseBeanData entity, StringBuilder path, NodeBeanDecoder node) {
		
		if(entity.getStaticValue() != null){
			return entity.getType().convert(entity.getStaticValue());
		}
		
		Type type = entity.getType();
		Object value;
		
		if(type instanceof CollectionType || type instanceof ArrayType){
			value = 
				path.length() == 0? 
					null : 
					entity.getScope().getCollection(path.toString());
		}
		else{
			value = 
				path.length() == 0? 
						null : 
						entity.getScope().get(path.toString());
		}
		return type.convert(value);
	}

	@Override
	protected Object getValue(DependencyBean entity, StringBuilder path,
			NodeBeanDecoder node) {
		
		if(entity.getValue() != null){
			return entity.getType().convert(entity.getValue());
		}
		
		Type type = entity.getType();
		Object value;
		
		if(type instanceof CollectionType || type instanceof ArrayType){
			value = 
				path.length() == 0? 
					null : 
					entity.getScope().getCollection(path.toString());
		}
		else{
			value = 
				path.length() == 0? 
					null : 
					entity.getScope().get(path.toString());
		}
		
		return type.convert(value);		
	}
	
	/* collection */
	
	@SuppressWarnings("unchecked")
	protected Object getValueCollectionObject(CollectionBean entity, Element e,
			StringBuilder path, NodeBeanDecoder parent) {
		
		Collection<Object> destValue = (Collection<Object>)this.getValueBean(entity, path, parent);
	
		int len 				= path.length();
		NodeBeanDecoder node	= this.getNextNode(e, path, parent);
		int[] idx				= this.getIndex(path.toString(), e.getScope());
		int lenEntity 			= path.length();
		
		for(int i: idx){
			
			path.append(entity.getIndexFormat().replace("$index", String.valueOf(i)));
			
			Object element = this.getValue(e, FetchType.EAGER, path, node);
			
			if(element != null){
				destValue.add(element);
			}
			
			path.setLength(lenEntity);
		}
		
		path.setLength(len);
		
		if(destValue.size() > entity.getMaxItens()){
			throw new MappingException(destValue.size() + " > " + entity.getMaxItens());
		}
		
		return destValue.size() == 0? null : destValue;
	}
	
	@SuppressWarnings("unchecked")
	protected Object getValueCollectionSimple(CollectionBean entity, Element e,
			StringBuilder path, NodeBeanDecoder parent) {
		
		Collection<Object> destValue = (Collection<Object>)this.getValueBean(entity, path, parent);
		
		int[] idx     = this.getIndex(path.toString(), e.getScope());
		int len       = path.length();
		
		for(int i: idx){
			
			path.append(entity.getIndexFormat().replace("$index", String.valueOf(i)));
			
			Object element = this.getValue(e, FetchType.EAGER, path, parent);
			
			if(element != null){
				destValue.add(element);
			}
			
			path.setLength(len);
		}
		
		path.setLength(len);
		
		if(destValue.size() > entity.getMaxItens()){
			throw new MappingException(destValue.size() + " > " + entity.getMaxItens());
		}
		
		return destValue.size() == 0? null : destValue;
		
	}	

	/* map */
	
	@SuppressWarnings("unchecked")
	protected Object getValueMapObject(MapBean entity, Key k, StringBuilder path, NodeBeanDecoder parent){
		
		Map<Object,Object> destValue = 
				(Map<Object,Object>)this.getValueBean(entity, path, parent);

		int len							= path.length();
		Map<Integer,Object> keysBuffer 	= new HashMap<Integer, Object>();
		NodeBeanDecoder keyNode 		= this.getNextNode(k, path, parent);
		int[] idx     					= this.getIndex(path.toString(), k.getScope());
		
		int keyLen = path.length();
		
		for(int i: idx){
			
			path.append(entity.getIndexFormat().replace("$index", String.valueOf(i)));
			
			Object element = this.getValue(k, FetchType.EAGER, path, keyNode);
			
			if(element != null){
				keysBuffer.put(i, element);
			}
			
			path.setLength(keyLen);
		}
		
		path.setLength(len);
		
		Element e 				= (Element)entity.getCollection();
		NodeBeanDecoder eNode	= this.getNextNode(e, path, parent);
		int eLen 				= path.length();
		
		for(Entry<Integer, Object> entry: keysBuffer.entrySet()){
			
			path.append(entity.getIndexFormat().replace("$index", String.valueOf(entry.getKey())));
			
			Object element = this.getValue(e, FetchType.EAGER, path, eNode);
			
			if(element != null && entry.getValue() != null){
				destValue.put(entry.getValue(), element);
			}
			
			path.setLength(eLen);
		}
		
		path.setLength(len);
		
		if(destValue.size() > entity.getMaxItens()){
			throw new MappingException(destValue.size() + " > " + entity.getMaxItens());
		}
		
		return destValue.isEmpty()? null : destValue;
	}

	@SuppressWarnings("unchecked")
	protected Object getValueMapSimple(MapBean entity, Key k, StringBuilder path, NodeBeanDecoder parent){
		
		Map<Object,Object> destValue = 
				(Map<Object,Object>)this.getValueBean(entity, path, parent);

		int len = path.length();
		
		Element e = (Element)entity.getCollection();
		
		String prefix = path.toString();
		
		List<String> itens = 
				k.getScope()
					.getNamesStartsWith(prefix);

		Set<IndexField> keys = this.getKeys(itens, prefix);
		
		for(IndexField keyValue: keys){
			
			Object keyObject = k.convert(keyValue.index);
			
			path.append(keyValue.indexLiteral);
			
			Object element = this.getValue(e, FetchType.EAGER, path, parent);
			
			destValue.put(keyObject, element);
			
			path.setLength(len);
		}
		
		if(destValue.size() > entity.getMaxItens()){
			throw new MappingException(destValue.size() + " > " + entity.getMaxItens());
		}
		
		return destValue.isEmpty()? null : destValue;	
	}
	
	/* util */
	
	private int[] getIndex(String prefix, Scope scope){
		
		List<String> itens = 
				scope
					.getNamesStartsWith(prefix);
		
		Set<IndexField> keys = 
				this.getKeys(itens, prefix);
		
		int[] idx = new int[keys.size()];
		int k     = 0;
		
		for(IndexField key: keys){
			idx[k++] = Integer.parseInt(key.index);
		}
		
		Arrays.sort(idx);
		return idx;
	}
	
	private Set<IndexField> getKeys(List<String> itens, String prefix){
		
		Set<IndexField> result = new HashSet<IndexField>();
		 
		for(String item: itens){
			
			int end = this.getEndPosition(prefix, item);
			
			if(end == -1){
				continue;
			}
			
			int startIndex = this.getStartIndex(prefix, item, end);
			
			String keyPrefix = item.substring(startIndex, end);
			String key = keyPrefix;
			
			if(key.startsWith(".")){
				int endKeyName = key.indexOf(".", 1);
				
				if(endKeyName != -1){
					key = key.substring(1, endKeyName);
				}
				else{
					key = key.substring(1);
				}
			}
			else
			if(key.startsWith("[")){
				int endKeyName = key.indexOf("]");
				
				if(endKeyName != -1){
					key = key.substring(1, endKeyName);
				}
				else{
					throw new MappingException("expected ']' in " + item);
				}
			}
			
			result.add(new IndexField(key, keyPrefix));
		}
		
		return result;
	}
	
	/*
	private boolean checkPrefix(String prefix, String key){
		int nextDot = key.indexOf(".", prefix.length());
		int index   = key.indexOf("[", prefix.length());
		int limit   = nextDot;
		
		if(nextDot == -1 || (index != -1 && index < nextDot)){
			limit = index;
		}
		
		if(limit != -1){
			key = key.substring(0, limit);
		}
		
		return prefix.equals(key);
	}
	*/
	
	private int getEndPosition(String prefix, String key){
		int nextDot = key.indexOf(".", prefix.length() + 1);
		return nextDot == -1? key.length() : nextDot;
	}
	
	private int getStartIndex(String prefix, String key, int endPosition){
		int index = key.indexOf("[", prefix.length());
		return index == -1 || index > endPosition? prefix.length() : index;
	}
	
	private static class IndexField{
		
		public String index;
		
		public String indexLiteral;

		public IndexField(String index, String indexLiteral) {
			super();
			this.index = index;
			this.indexLiteral = indexLiteral;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((indexLiteral == null) ? 0 : indexLiteral.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			IndexField other = (IndexField) obj;
			if (indexLiteral == null) {
				if (other.indexLiteral != null)
					return false;
			} else if (!indexLiteral.equals(other.indexLiteral))
				return false;
			return true;
		}
		
	}
}
