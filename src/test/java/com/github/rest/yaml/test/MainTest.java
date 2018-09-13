package com.github.rest.yaml.test;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.github.rest.yaml.test.beans.YamlDataGroup;
import com.github.rest.yaml.test.beans.YamlInitGroup;
import com.github.rest.yaml.test.beans.YamlTest;
import com.github.rest.yaml.test.beans.YamlTestGroup;
import com.github.rest.yaml.test.beans.YamlTestTemplateGroup;
import com.github.rest.yaml.test.certificate.CertificateLoader;
import com.github.rest.yaml.test.util.Environment;
import com.jayway.restassured.specification.RequestSpecification;

public class MainTest extends AbstractITest {

	public static int testGroupCount = 0;
	private static List<YamlTestGroup> yamlTestGroups;
	protected static YamlInitGroup yamlInitGroup;
	protected static YamlDataGroup yamlDataGroup;
	protected static YamlTestTemplateGroup yamlTestTemplateGroup;
	
	
	
	public static void setUp() throws Exception {
		abstractSetUp();
		yamlInitGroup = getInitGroup();
		//CertificateLoader.instance().loadCertificates(certificates);
		logger.info("Certificates loading done.");
		logger.info("baseURL="+baseURL+" port="+port);
		logger.info("groupTags="+Environment.instance().getGroupTags()+", testTags="+Environment.instance().getTestTags());
		yamlTestGroups = getTestGroups();
		yamlDataGroup = getDataGroup();
		yamlTestTemplateGroup = getTestTemplateGroup();
	}

	@Factory
	public Object[] tests() throws Exception {
		setUp();
		Collection<DynamicTest> dynamicTests = new ArrayList<DynamicTest>();
		Collection<DynamicTest> tests = new ArrayList<DynamicTest>();
		List<TestSuite> suites = new ArrayList<TestSuite>();
		List<Object> myTests = new ArrayList<Object>();

		for (YamlTestGroup yamlTestGroup : yamlTestGroups) {
			
			if (!yamlTestGroup.isTagged()) {
				logger.info("->testGroup skipped tag does not exist for testGroup name=" 
			                + yamlTestGroup.getName()+", tags="+yamlTestGroup.getTags());
				continue;
			}
			tests.clear();

			for (YamlTest yamlTest : yamlTestGroup.getTests()) {
				final String testcaseName = "testGroup=" + yamlTestGroup.getName()
				                            + ", test=" + yamlTest.getName()
				                            + ", group tags="+yamlTestGroup.getTags()
				                            + ", test tags="+yamlTest.getTags();
				if (!yamlTest.isTagged()) {
					logger.info("->test skipped tag does not exist " + testcaseName);
					continue;
				}
				
				myTests.add(new TestClass( yamlTestGroup, yamlTest,  testcaseName, yamlInitGroup, yamlDataGroup, yamlTestTemplateGroup));
				//Executable executable = setupTest(yamlTestGroup, yamlTest, testcaseName);
				
				/*DynamicTest dTest = DynamicTest.dynamicTest(testcaseName, executable);
				dynamicTests.add(dTest);
				tests.add(dTest);*/

			}
			
			//suites.add(new TestSuite(yamlTestGroup.getName(), tests));
		}
		
		System.out.println(myTests.toArray().toString());
		System.out.println(myTests.toArray().length);
		
		return myTests.toArray();
	//	return dynamicTests.stream();
	//	return suites.stream().map(f->DynamicContainer.dynamicContainer(f.getSuiteName(), f.getTests().stream()));
	}

	/*private Executable setupTest(YamlTestGroup yamlTestGroup, YamlTest yamlTest, String testcaseName) {

		RequestSpecification rs = given().spec(rspec);

		Executable executable = () -> {
			logger.log("\n\n\n-->start " + testcaseName);
			// Initialize current state to be access globally
			CurrentState.setState(yamlInitGroup, yamlTestGroup, yamlDataGroup, yamlTestTemplateGroup, yamlTest);

			try {
				RestRequest.build(rs, yamlTest).request().doAssert();
			} catch (Throwable e) {
				e.printStackTrace();
				throw e;
			}

		};

		return executable;
	}*/
	
}
