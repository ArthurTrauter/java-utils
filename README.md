Try Gitter chat: [![Gitter chat](https://badges.gitter.im/java-utils.png)](https://gitter.im/DennisAhaus/java-utils)

#Java-utils
This project has the goal to solve common problems a java developer has when developing in java.

See [Helpers](#helpers) section to cover the existing helpers.

### Helpers

* JUnit testing
 * [Test coverage](#test-coverage)
* XML
  * [w3c.dom.document utils](#w3cdomdocument-utils)

## JUnit testing

###Test coverage

Problem to solve: How to check, while working with junit tests, wheter all methods in class under test are covered by at least one test method? 
Java-utils can solve this probem by using some implemented annotations and extend/create a TestCoverage parent/member class. The TestCoverage implementation has the goal to force the unit test to fail there is at least one method in class under test which is not covered by at least one test method inside the test class. A coverage can be declared by two annotations: @ClassUnderTest ans @MethodUnderTest. See examples...

###### Example 1:

Your class which should be tested by unit test:
```
public class YourService{
  public String method1(){...}
  public String doSomething(){...}
}
```

Your Junit test which will test your YourService class:
```
@ClassUnderTest(value = YourService.class, onFailBehavior = OnFailBehavior.EXCEPTION)
public class YourTestClass extends TestCoverage {

	@Test
	@MethodUnderTest("method1")
	public void testServiceMethod1() {
		yourService.method1(); // testing service method
	}

	@Test(expected = IllegalArgumentException.class)
	@MethodUnderTest("doSomething")
	public void testServiceDoSomething() {
		yourService.doSomething(); // testing service method
	}

}

```
As you can see in [Example 1](#example-1) there are two new annotations: [@ClassUnderTest](java-utils-impl/src/main/java/de/ahaus/dennis/javautils/impl/junit/annotations/ClassUnderTest.java) and [@MethodUnderTest](java-utils-impl/src/main/java/de/ahaus/dennis/javautils/impl/junit/annotations/MethodUnderTest.java).

###### @ClassUnderTest
This annoation is needed to maintain the "class under test". In other words the class that should be tested. 
In this annotation you can also control if the junit test should fail by a regular `Assert.fail(...)` or by an [MethodNotUnderTestException](java-utils-impl/src/main/java/de/ahaus/dennis/javautils/impl/junit/annotations/MethodNotUnderTestException.java). The `onFailBehavior` parameter controls that behavior. Default is `Assert.fail(..)`.

There is an additional parameter `methods` which gives control over the methods which should be covered in class under test. Here you can decide wheter all methods (inclusive those from parent classes) should be covered or only the declared ones in class under test.

###### @MethodUnderTest
This annoation is needed to maintain the "method under test". In other words the method which exists inside the class under test and is tested by this annotated test method. The name of the method is case sensitive and is used without any other signatures.

#### Summary
It is very easy and fast to add some annotations to your test classes while coding. This implementation will help to write better code because tests will fail when there is a method not covered. So whenever you add a new method to your class under test all tests will fail until there is a testmethod which covers that new method. This ends up in a better execution and test quality and keeps the developer on to write more tests.

## XML

### w3c.dom.Document utils

Documention will be arrive asap the next days. Furthermore see implementation [XmlUtil](java-utils-impl/src/main/java/de/ahaus/dennis/javautils/impl/xml/XmlUtil.java)

