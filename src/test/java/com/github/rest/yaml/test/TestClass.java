package com.github.rest.yaml.test;
import static com.jayway.restassured.RestAssured.given;

import java.lang.reflect.Method;

import org.junit.jupiter.api.function.Executable;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.github.rest.yaml.test.CurrentState;
import com.github.rest.yaml.test.MainTest;
import com.github.rest.yaml.test.RestRequest;
import com.github.rest.yaml.test.beans.YamlDataGroup;
import com.github.rest.yaml.test.beans.YamlInitGroup;
import com.github.rest.yaml.test.beans.YamlTest;
import com.github.rest.yaml.test.beans.YamlTestGroup;
import com.github.rest.yaml.test.beans.YamlTestTemplateGroup;
import com.github.rest.yaml.test.util.Logger;
import com.jayway.restassured.specification.RequestSpecification;

public class TestClass extends AbstractITest{
	
	private Logger logger = new Logger();
	
	private YamlTestGroup yamlTestGroup;
	private YamlTest yamlTest;
	private String testcaseName;
	private YamlInitGroup yamlInitGroup;
	private YamlDataGroup yamlDataGroup;
	private YamlTestTemplateGroup yamlTestTemplateGroup;
	private ThreadLocal<String> testName = new ThreadLocal<>();
	
	public TestClass(YamlTestGroup yamlTestGroup, YamlTest yamlTest, String testcaseName, YamlInitGroup yamlInitGroup, YamlDataGroup yamlDataGroup, YamlTestTemplateGroup yamlTestTemplateGroup) {
		// TODO Auto-generated constructor stub
		 this.yamlTestGroup= yamlTestGroup;
		this.yamlTest =yamlTest;
		this.testcaseName = testcaseName;
		this.yamlInitGroup=yamlInitGroup;
		this.yamlDataGroup=yamlDataGroup;
		this.yamlTestTemplateGroup= yamlTestTemplateGroup;
	}
	
	
	@BeforeClass
	public void setTestName() {
		CurrentState.setState(yamlInitGroup, yamlTestGroup, yamlDataGroup, yamlTestTemplateGroup, yamlTest);
	}
	


	@Test
		private void setupTest() {

			RequestSpecification rs = given().spec(rspec);

			
				logger.log("\n\n\n-->start " + testcaseName);
				// Initialize current state to be access globally
				
				
				try {
					RestRequest.build(rs, yamlTest).request().doAssert();
				} catch (Throwable e) {
					e.printStackTrace();
					throw e;
				}

		

			
		}
	

}
