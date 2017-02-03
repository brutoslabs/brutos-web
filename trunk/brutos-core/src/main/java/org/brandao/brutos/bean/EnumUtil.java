package org.brandao.brutos.bean;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.brandao.brutos.BrutosException;

public class EnumUtil {

	private Class enumClass;

	private Map indexMap;

	private Map nameMap;

	public EnumUtil(Class enumClass) {
		this.enumClass = enumClass;
		this.indexMap = new HashMap();
		this.nameMap = new HashMap();

		int maxIndex = this.getEnumConstantLength();
		for (int i = 0; i < maxIndex; i++) {
			Enum e = (Enum) this.getEnumConstant(new Integer(i));
			this.indexMap.put(String.valueOf(e.ordinal()), e);
			this.nameMap.put(e.name(), e);
		}
	}

	public Object getEnumConstants() {
		return getEnumConstants(this.enumClass);
	}

	public static Object getEnumConstants(Class clazz) {
		try {
			Method m = Class.class
					.getMethod("getEnumConstants", new Class[] {});
			return m.invoke(clazz, new Object[] {});
		} catch (Exception e) {
			throw new BrutosException(e);
		}

	}

	public Object valueOf(String value) {
		return valueOf(this.enumClass, value);
	}

	public Object valueByName(String name) {
		return this.nameMap.get(name);
	}

	public Object valueByIndex(String index) {
		return this.indexMap.get(index);
	}

	public int getEnumConstantLength() {
		return getEnumConstantLength(this.enumClass);
	}

	public Object getEnumConstant(Integer index) {
		return getEnumConstant(this.enumClass, index);
	}

	public static int getEnumConstantLength(Class enumClazz) {
		Object cons = getEnumConstants(enumClazz);
		return Array.getLength(cons);
	}

	public static Object getEnumConstant(Class enumClazz, Integer index) {
		Object cons = getEnumConstants(enumClazz);
		return Array.get(cons, index.intValue());
	}

	public static Object valueOf(Class enumClazz, String value) {
		try {
			Class clazz = Class.forName("java.lang.Enum");
			Method m = clazz.getMethod("valueOf", new Class[] { Class.class,
					String.class });
			return m.invoke(clazz, new Object[] { enumClazz, value });
		} catch (Exception e) {
			throw new BrutosException(e);
		}

	}

}
