package org.brandao.brutos;

import java.util.Properties;

public class Configuration extends Properties {

	public Configuration() {
		super();
	}

	public Configuration(Properties props) {
		super(props);
	}

	public String getProperty(String key, String defaultValue) {
		String value = super.getProperty(key, defaultValue);
		value = (value == null) ? System.getProperty(key) : value;

		if (value != null)
			value = getVars(value);

		return value;
	}

	public String getProperty(String key) {
		String value = super.getProperty(key);
		value = (value == null) ? System.getProperty(key) : value;
		if (value != null)
			value = getVars(value);

		return value;
	}

	public String getProperty(String key, boolean insert) {
		String value = super.getProperty(key);
		value = (value == null) ? System.getProperty(key) : value;
		if (value != null && insert)
			value = getVars(value);

		return value;
	}

	private String getVars(String value) {

		int index = value.indexOf("${");

		while (index != -1) {
			int end = value.indexOf("}", index);

			if (end != -1) {
				String key = value.substring(index + 2, end);
				String prop = getProperty(key, null);
				if (prop != null)
					value = value.replace("${" + key + "}", prop);
			}
			index = value.indexOf("${", end);
		}
		return value;
	}

}
