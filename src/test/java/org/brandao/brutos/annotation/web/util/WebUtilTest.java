package org.brandao.brutos.annotation.web.util;

import java.util.List;

import org.brandao.brutos.web.util.WebUtil;

import junit.framework.TestCase;

public class WebUtilTest extends TestCase{

	public void testParseRegexURI() {
		String uri = "{uri:(/+[a-z0-9]+([-_][a-z0-9]+)?)+}";
		List<String> parts = WebUtil.parserURI(uri, true);
		assertEquals(1, parts.size());
		assertEquals("{uri:(/+[a-z0-9]+([-_][a-z0-9]+)?)+}", parts.get(0));
	}

	public void testParseURI() {
		String uri = "/part1";
		List<String> parts = WebUtil.parserURI(uri, true);
		assertEquals(1, parts.size());
		assertEquals("part1", parts.get(0));
	}
	
	public void testParsePartsRegex() {
		String uri = "/{a:[a-z]+}/{b:[0-9]+}";
		List<String> parts = WebUtil.parserURI(uri, true);
		assertEquals(2, parts.size());
		assertEquals("{a:[a-z]+}", parts.get(0));
		assertEquals("{b:[0-9]+}", parts.get(1));
	}

	public void testParsePartsURI() {
		String uri = "/part1/part2";
		List<String> parts = WebUtil.parserURI(uri, true);
		assertEquals(2, parts.size());
		assertEquals("part1", parts.get(0));
		assertEquals("part2", parts.get(1));
	}
	
}
