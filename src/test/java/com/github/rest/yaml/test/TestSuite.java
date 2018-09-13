package com.github.rest.yaml.test;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.DynamicTest;

public class TestSuite {
	
	private String suiteName;
	private Collection<DynamicTest> tests = new ArrayList<DynamicTest>();
	
	public TestSuite(String suiteName, Collection<DynamicTest> tests ) {
		// TODO Auto-generated constructor stub
		this.suiteName = suiteName;
		this.tests.addAll(tests);
	}

	public String getSuiteName() {
		return suiteName;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}

	public Collection<DynamicTest> getTests() {
		return tests;
	}

	public void setTests(Collection<DynamicTest> tests) {
		this.tests = tests;
	}
	

}
