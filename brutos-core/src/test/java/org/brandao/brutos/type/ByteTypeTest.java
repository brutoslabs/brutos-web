package org.brandao.brutos.type;

import junit.framework.TestCase;

public class ByteTypeTest extends TestCase {

	private Type type = new ByteType();

	private Object expected1 = new Byte((byte) 2);
	private Object test1 = "2";

	private Object expected2 = new Byte((byte) 0);
	private Object test2 = "0";

	private Object expected3 = new Byte((byte) 2);
	private Object test3 = new Byte((byte) 2);

	private Object expected4 = new Byte((byte) 0);
	private Object test4 = new Byte((byte) 0);

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
