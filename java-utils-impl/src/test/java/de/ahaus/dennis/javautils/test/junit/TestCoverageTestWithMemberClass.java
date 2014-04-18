package de.ahaus.dennis.javautils.test.junit;

import org.junit.Test;

import de.ahaus.dennis.javautils.impl.junit.TestCoverage;
import de.ahaus.dennis.javautils.impl.junit.annotations.ClassUnderTest;
import de.ahaus.dennis.javautils.impl.junit.annotations.MethodUnderTest;
import de.ahaus.dennis.javautils.impl.junit.annotations.OnFailBehavior;

/**
 * @author Dennis Ahaus
 * 
 */
@ClassUnderTest(value = TestCoverageTestWithMemberClass.class, onFailBehavior = OnFailBehavior.EXCEPTION)
public class TestCoverageTestWithMemberClass {

	TestCoverage coverage = new TestCoverage(this);

	@Test
	@MethodUnderTest("test1")
	public void test1() throws NoSuchMethodException, SecurityException {
		coverage.testCoverage();
	}

	@Test(expected = IllegalArgumentException.class)
	@MethodUnderTest("test2")
	public void test2() {
		new TestCoverage();
	}

}
