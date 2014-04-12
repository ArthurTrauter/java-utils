package de.ahaus.dennis.javautils.test;

import org.junit.Test;

import de.ahaus.dennis.javautils.impl.junit.annotations.AbstractTestCoverage;
import de.ahaus.dennis.javautils.impl.junit.annotations.ClassUnderTest;
import de.ahaus.dennis.javautils.impl.junit.annotations.MethodUnderTest;
import de.ahaus.dennis.javautils.impl.xml.XmlUtil;

@ClassUnderTest(XmlUtil.class)
public class TestAbstractTest extends AbstractTestCoverage {

	@Test
	@MethodUnderTest("doSomething")
	public void test() {

	}

}
