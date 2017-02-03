/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.annotation.configuration.converters;

import java.util.*;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.annotation.InterceptsStack;
import org.brandao.brutos.annotation.InterceptsStackList;
import org.brandao.brutos.annotation.configuration.Converter;
import org.brandao.brutos.annotation.configuration.InterceptorStackEntry;
import org.brandao.brutos.interceptor.InterceptorController;
import org.brandao.brutos.mapping.MappingException;
import org.brandao.brutos.mapping.StringUtil;

/**
 *
 * @author Brandao
 */

public class InterceptorStackConverter implements Converter {

	@SuppressWarnings("unchecked")
	public Object converter(Object value, ComponentRegistry componentRegistry) {

		List<Object> list = (List<Object>) value;

		Map<String, Map<Class<?>, InterceptorStackItem>> stacksInfo = groupInfo(list);

		for (String key : stacksInfo.keySet()) {
			Map<Class<?>, InterceptorStackItem> stackData = stacksInfo.get(key);

			list.add(getInterceptorStackEntry(key, stackData));
		}
		return list;
	}

	private InterceptorStackEntry getInterceptorStackEntry(String name,
			Map<Class<?>, InterceptorStackItem> stackData) {

		List<InterceptorStackItem> root = new ArrayList<InterceptorStackItem>();

		for (Class<?> keyItem : stackData.keySet()) {

			InterceptorStackItem item = stackData.get(keyItem);

			Class<?> after = item.getInfo().executeAfter();

			if (after == InterceptorController.class) {
				root.add(item);
				continue;
			}

			InterceptorStackItem previous = stackData.get(after);

			if (previous == null)
				throw new MappingException(
						"does not compose the interceptor stack " + name + ": "
								+ after.getSimpleName());

			item.setPrevious(previous);
			previous.addNext(item);
		}

		if (root.isEmpty())
			throw new MappingException("first interceptor not found: " + name);

		List<InterceptorStackItem> list = new ArrayList<InterceptorStackItem>();

		InterceptorStackEntry entry = new InterceptorStackEntry();
		entry.setName(name);
		entry.setInterceptors(list);
		entry.setDefault(root.get(0).getInfo().isdefault());

		for (InterceptorStackItem item : root) {
			checkCircularReference(item);
			addInterceptor(item, list);
		}

		return entry;
	}

	private void addInterceptor(InterceptorStackItem item,
			List<InterceptorStackItem> list) {
		list.add(item);

		for (InterceptorStackItem next : item.next) {
			addInterceptor(next, list);
		}

	}

	private Map<String, Map<Class<?>, InterceptorStackItem>> groupInfo(
			List<Object> clazzList) {
		Map<String, Map<Class<?>, InterceptorStackItem>> stacks = new HashMap<String, Map<Class<?>, InterceptorStackItem>>();

		for (Object itemList : clazzList) {

			if (!(itemList instanceof Class))
				continue;

			Class<?> clazz = (Class<?>) itemList;
			List<InterceptsStack> stacksDefinition = getStacksDefinition(clazz);

			for (InterceptsStack is : stacksDefinition) {
				String name = StringUtil.adjust(is.name());
				if (name == null)
					throw new MappingException(
							"invalid interceptor stack name: "
									+ clazz.getSimpleName());

				Map<Class<?>, InterceptorStackItem> stack = stacks.get(name);

				if (stack == null) {
					stack = new HashMap<Class<?>, InterceptorStackItem>();
					stacks.put(name, stack);
				}

				if (stack.containsKey(clazz))
					throw new MappingException(
							"duplicate interceptor stack name: "
									+ clazz.getSimpleName());

				InterceptorStackItem item = new InterceptorStackItem(name,
						clazz);
				item.setInfo(is);
				stack.put(clazz, item);
			}
		}
		return stacks;
	}

	private List<InterceptsStack> getStacksDefinition(Class<?> clazz) {
		List<InterceptsStack> stacksDefinition = new ArrayList<InterceptsStack>();

		if (clazz.isAnnotationPresent(InterceptsStackList.class)) {
			InterceptsStackList isl = (InterceptsStackList) clazz
					.getAnnotation(InterceptsStackList.class);

			stacksDefinition.addAll(Arrays.asList(isl.value()));
		}

		if (clazz.isAnnotationPresent(InterceptsStack.class))
			stacksDefinition.add((InterceptsStack) clazz
					.getAnnotation(InterceptsStack.class));

		return stacksDefinition;
	}

	private void checkCircularReference(InterceptorStackItem root) {
		checkNextPart(root, new StringBuilder());
	}

	private void checkNextPart(InterceptorStackItem entry, StringBuilder prefix) {

		prefix.append(entry.getType().getSimpleName());

		for (InterceptorStackItem next : entry.getNext()) {
			String node = entry.getType().getSimpleName() + " -> "
					+ next.getType().getSimpleName();

			if (prefix.indexOf(node) != -1) {
				throw new MappingException(
						"detected circular reference in interceptor stack "
								+ entry.getName() + ": " + node);
			}

			checkNextPart(next, new StringBuilder(prefix).append(" -> "));
		}
	}

	public static class InterceptorStackItem {

		private String name;

		private Class<?> type;

		private InterceptsStack info;

		private List<InterceptorStackItem> next;

		private InterceptorStackItem previous;

		public InterceptorStackItem(String name, Class<?> type) {
			this.name = name;
			this.type = type;
			this.next = new ArrayList<InterceptorStackItem>();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public InterceptsStack getInfo() {
			return info;
		}

		public void setInfo(InterceptsStack info) {
			this.info = info;
		}

		public InterceptorStackItem getPrevious() {
			return previous;
		}

		public void setPrevious(InterceptorStackItem previous) {
			this.previous = previous;
		}

		public List<InterceptorStackItem> getNext() {
			return next;
		}

		public void addNext(InterceptorStackItem next) {
			this.next.add(next);
		}

		public Class<?> getType() {
			return type;
		}

		public void setType(Class<?> type) {
			this.type = type;
		}

	}
}
