package de.ahaus.dennis.javautils.test;

import org.junit.Test;

import de.ahaus.dennis.javautils.impl.junit.TestCoverage;
import de.ahaus.dennis.javautils.impl.junit.annotations.ClassUnderTest;
import de.ahaus.dennis.javautils.impl.junit.annotations.MethodUnderTest;
import de.ahaus.dennis.javautils.impl.xml.XmlUtil;

@ClassUnderTest(XmlUtil.class)
public class TestAbstractTest extends TestCoverage {

	@Test
	@MethodUnderTest("doSomething")
	public void test() {

	}

}
