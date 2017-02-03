package org.brandao.brutos.type;

import junit.framework.TestCase;

public class BooleanTypeTest extends TestCase {

	private Type type = new BooleanType();

	private Object expected1 = Boolean.TRUE;
	private Object test1 = "true";

	private Object expected2 = Boolean.FALSE;
	private Object test2 = "false";

	private Object expected3 = Boolean.TRUE;
	private Object test3 = Boolean.TRUE;

	private Object expected4 = Boolean.FALSE;
	private Object test4 = Boolean.FALSE;

	private Object invalidType = new Integer(1);

	public void test1() {
		Object val = type.convert(test1);
		TestCase.assertEquals(expected1, val);
	}

	public void test2() {
		Object val = type.convert(test2);
		TestCase.assertEquals(expected2, val);
	}

	public void test3() {
		Object val = type.convert(test3);
		TestCase.assertEquals(expected3, val);
	}

	public void test4() {
		Object val = type.convert(test4);
		TestCase.assertEquals(expected4, val);
	}

	public void test5() {
		try {
			type.convert(invalidType);
			TestCase.fail("expected UnknownTypeException");
		} catch (UnknownTypeException e) {
		}
	}

	public void test6() {
		Object val = type.convert(null);
		TestCase.assertNull(val);
	}

}
