package org.brandao.brutos;

import java.util.HashMap;
import java.util.Map;

public abstract class ActionType {

	private final static Map defaultTypes = new HashMap();

	public static final ActionType PARAMETER = new ActionType() {

		public int type() {
			return 0;
		}

		public String name() {
			return "PARAMETER";
		}
	};

	public static final ActionType HIERARCHY = new ActionType() {

		public int type() {
			return 1;
		}

		public String name() {
			return "HIERARCHY";
		}

	};

	public static final ActionType DETACHED = new ActionType() {

		public int type() {
			return 2;
		}

		public String name() {
			return "DETACHED";
		}

	};

	static {
		defaultTypes.put(PARAMETER.name(), PARAMETER);
		defaultTypes.put(HIERARCHY.name(), HIERARCHY);
		defaultTypes.put(DETACHED.name(), DETACHED);
	}

	public static ActionType valueOf(String value) {
		if (value == null)
			return null;
		else
			return (ActionType) defaultTypes.get(value.toUpperCase());
	}

	public abstract int type();

	public abstract String name();

	public int hashCode() {
		return type();
	}

	public boolean equals(Object x) {
		return x instanceof ActionType ? ((ActionType) x).type() == type()
				: false;
	}

}
