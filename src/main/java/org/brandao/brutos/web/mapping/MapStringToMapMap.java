package org.brandao.brutos.web.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MapStringToMapMap {

	private static final String INDEX_FORMAT = "(.+)\\[(\\d+)]";
	
	private static final Pattern INDEX_FORMAT_PATTERN = Pattern.compile(INDEX_FORMAT);
	
	public static Map<String,Object> toMap(Map<String,String> values, List<String> keys){
		
		Map<String,String[]> keymap = getKeyMap(values.keySet().stream().collect(Collectors.toList()));
		
		Item i = new Item();
		for(String k: keys) {
			createMap(i, keymap.get(k), 0, values.get(k));
		}
		/*
		for(Entry<String,String[]> e: keymap.entrySet()) {
			createMap(i, e.getValue(), 0, values.get(e.getKey()));
		}
		*/
		return parse(i);
	}
	
	private static Map<String,String[]> getKeyMap(List<String> values){
		return values.stream().collect(Collectors.toMap((e)->e,(e)->e.split("\\.")));
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> parse(Item item) {
		return (Map<String, Object>) parseObject(item);
	}

	private static Object parseObject(Item item) {
		
		if(!item.list.isEmpty() || !item.map.isEmpty()) {

			Map<String, Object> o = new HashMap<>();
			
			for(Entry<String, Item> e: item.list.entrySet()) {
				Object x = parseObject(e.getValue());
				o.put(e.getKey(), x);
			}
			
			for(Entry<String, Item> e: item.map.entrySet()) {
				Object x = parseObject(e.getValue());
				o.put(e.getKey(), x);
			}

			return o;
		}
		
		if(!item.values.isEmpty()) {
			List<Integer> index = new ArrayList<>(item.values.keySet());
			Collections.sort(index, (a,b)->a - b);
			
			List<Object> itens = new ArrayList<>();
			
			int c = 0;
			for(Integer i: index) {
				if(c != i) {
					break;
				}
				
				Object x = parseObject(item.values.get(i));
				itens.add(x);
				
				c++;
				
			}
			
			return itens;
		}
		else {
			return item.value;
		}
			
	}
	
	private static void createMap(Item parent, String[] path, int index, Object value) {
		

		if(index > path.length) {
			return;
		}
		
		String local 		= path[index];
		Object[] nameData 	= toName(local);
		int oidx 			= (Integer)nameData[1];
		String nidx 		= (String)nameData[0];
		
		if(index < path.length - 1) {
			
			Item object = oidx == -1? parent.map.get(nidx) : parent.list.get(nidx);
			
			if(object == null) {
				object = new Item();
				if(oidx == -1) {
					parent.map.put(nidx, object);
				}
				else {
					parent.list.put(nidx, object);
				}
			}

			if(oidx != -1) {
				Item indexItem = object.values.get(oidx);
				
				if(indexItem == null) {
					indexItem = new Item();
					object.values.put(oidx, indexItem);
				}

				object = indexItem;
			}
			createMap(object, path, index + 1, value);
		}
		else {
			if(oidx == -1) {
				Item i = parent.map.get(nidx);
				if(i == null) {
					i = new Item();
					parent.map.put(nidx, i);
				}
				i.value = value;
			}
			else {
				Item i = parent.list.get(nidx);
				if(i == null) {
					i = new Item();
					parent.list.put(nidx, i);
				}
				
				Item indexItem = i.values.get(oidx);
				if(indexItem == null) {
					indexItem = new Item();
					i.values.put(oidx, indexItem);
				}
				
				indexItem.value = value;
			}
		}
		
	}

	/*
	public static void main(String[] e) {
		MapStringToMapMap m = new MapStringToMapMap();
		Map<String,String> map = new HashMap<>();
		map.put("field1", "value1");
		map.put("field2", "value2");
		map.put("field4[0]", "value1");
		map.put("field4[1]", "value2");
		map.put("field4[2].field1", "value1");
		map.put("field4[2].field2", "value2");
		map.put("field5[0].field3[0]", "value0.1");
		map.put("field5[0].field3[1]", "value0.2");
		map.put("field5[0].field3[2]", "value0.3");
		map.put("field5[1].field3[0]", "value2");
		map.put("field5[2].field3[0]", "value2.3");
		map.put("field5[2].field3[1]", "value2.3");
		map.put("field5[2].field3[2]", "value2.3");
		
		map.put("field3.field1", "value1");
		map.put("field3.field2", "value2");
		map.put("field3.field3[0]", "value1");
		map.put("field3.field3[1]", "value2");
		map.put("field3.field3[2]", "value3");

		List<String> keys = new ArrayList<>();
		keys.add("field1");
		keys.add("field2");
		keys.add("field4[0]");
		keys.add("field4[1]");
		keys.add("field4[2].field1");
		keys.add("field4[2].field2");
		keys.add("field5[0].field3[0]");
		keys.add("field5[0].field3[1]");
		keys.add("field5[0].field3[2]");
		keys.add("field5[1].field3[0]");
		keys.add("field5[2].field3[0]");
		keys.add("field5[2].field3[1]");
		keys.add("field5[2].field3[2]");
		
		keys.add("field3.field1");
		keys.add("field3.field2");
		keys.add("field3.field3[0]");
		keys.add("field3.field3[1]");
		keys.add("field3.field3[2]");
		
		Map<String,Object> obj = m.toMap(map, keys);
	}
	*/
	
	private static Object[] toName(String value) {
		Matcher matcher = INDEX_FORMAT_PATTERN.matcher(value);
		if(matcher.matches()) {
			return new Object[] {matcher.group(1), Integer.parseInt(matcher.group(2))};
		}
		else {
			return new Object[] {value, -1};
		}
	}
	
	private static class Item {
		
		public Map<String, Item> map = new HashMap<>();
		
		public Map<String, Item> list = new HashMap<>();

		public Object value;
		
		public Map<Integer, Item> values = new HashMap<>();
	}
	
}
