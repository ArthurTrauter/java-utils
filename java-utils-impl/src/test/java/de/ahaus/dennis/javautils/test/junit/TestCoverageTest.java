package de.ahaus.dennis.javautils.test.junit;

import org.junit.Test;

import de.ahaus.dennis.javautils.impl.junit.TestCoverage;
import de.ahaus.dennis.javautils.impl.junit.annotations.ClassUnderTest;
import de.ahaus.dennis.javautils.impl.junit.annotations.MethodUnderTest;
import de.ahaus.dennis.javautils.impl.junit.annotations.OnFailBehavior;
import de.ahaus.dennis.javautils.impl.xml.XmlUtil;

/**
 * @author Dennis Ahaus
 * 
 */
@ClassUnderTest(value=TestCoverageTest.class, onFailBehavior=OnFailBehavior.EXCEPTION)
public class TestCoverageTest extends TestCoverage {
	
	//TestCoverage coverage = new TestCoverage();

	@Test
	@MethodUnderTest("test1")
	public void test1() throws NoSuchMethodException, SecurityException {

	}

	@Test
	@MethodUnderTest("doSomething1")
	public void test2() {

	}

}
