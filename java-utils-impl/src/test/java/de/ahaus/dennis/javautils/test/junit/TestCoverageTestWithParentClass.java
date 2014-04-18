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
@ClassUnderTest(value = TestCoverageTestWithParentClass.class, onFailBehavior = OnFailBehavior.EXCEPTION)
public class TestCoverageTestWithParentClass extends TestCoverage {

	@Test
	@MethodUnderTest("test1")
	public void test1() throws NoSuchMethodException, SecurityException {

	}

	@Test(expected = IllegalArgumentException.class)
	@MethodUnderTest("test2")
	public void test2() {
		new TestCoverage();
	}

}
