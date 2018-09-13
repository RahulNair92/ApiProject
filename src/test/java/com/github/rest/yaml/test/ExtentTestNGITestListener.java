package com.github.rest.yaml.test;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentTestNGITestListener implements ITestListener{

	private static ExtentReports extent = ExtentManager.createInstance("extent.html");
	private static ThreadLocal parentTest = new ThreadLocal();
    private static ThreadLocal test = new ThreadLocal();
	
    @Override
	public synchronized void onStart(ITestContext context) {
    	ExtentTest parent = extent.createTest("All Tests");
    	//ExtentTest parent = extent.createTest(CurrentState.getCurrentYamlTestGroup().getName());
        parentTest.set(parent);
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		extent.flush();
	}
	
	@Override
	public synchronized void onTestStart(ITestResult result) {
		//ExtentTest child = ((ExtentTest) parentTest.get()).createNode(result.getMethod().getMethodName());
		ExtentTest child = ((ExtentTest) parentTest.get()).createNode(CurrentState.getCurrentYamlTest().getName());
		child.assignCategory(CurrentState.getCurrentYamlTestGroup().getName());
		
        test.set(child);
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		logRequestnResponse();
		((ExtentTest) test.get()).pass("Test passed");
		
	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {
		logRequestnResponse();
		((ExtentTest) test.get()).fail(result.getThrowable());
	}

	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		((ExtentTest) test.get()).skip(result.getThrowable());
	}

	@Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}
	
	public void logRequestnResponse() {
		if(CurrentRequestandResponse.getRequest()!=null||!CurrentRequestandResponse.getResponse().isEmpty()) {
			((ExtentTest) test.get()).log(Status.INFO, "Request:");
			((ExtentTest) test.get()).log(Status.INFO, CurrentRequestandResponse.getRequest());
		}
		if(CurrentRequestandResponse.getResponse()!=null||!CurrentRequestandResponse.getResponse().isEmpty()) {
			((ExtentTest) test.get()).log(Status.INFO, "Response:");
			((ExtentTest) test.get()).log(Status.INFO, CurrentRequestandResponse.getResponse());
		}
			
	}
}
