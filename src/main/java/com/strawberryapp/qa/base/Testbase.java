package com.strawberryapp.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.strawberryapp.qa.logs.WebEventListener;
import com.strawberryapp.qa.pages.LoginPage;

public class Testbase {
	
	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static ExtentReports extent;
	public static ExtentTest test;
	WebEventListener eListener;
	
	public static LoginPage login;
	
	String configFilePath = "C:\\Users\\Sachin Roy\\eclipse-oxygen-workspace\\StrawberryApp\\src\\main\\java\\com\\strawberryapp\\qa\\configuration\\config.properties";
	String extentReportPath = "C:\\Users\\Sachin Roy\\eclipse-oxygen-workspace\\StrawberryApp\\ExtentReports\\StrawberryAppReport.html";
	String extentConfigPath = "C:\\Users\\Sachin Roy\\eclipse-oxygen-workspace\\StrawberryApp\\ExtentReports\\extent-config.xml";
	
	public Testbase() {
		try {
			FileInputStream fis = new FileInputStream(configFilePath);
			prop = new Properties();
			prop.load(fis);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void initializePages(){
		login = new LoginPage(driver);
	}
	
	@BeforeSuite
	public void setUpSuite() {
		extent = new ExtentReports(extentReportPath);
		extent.loadConfig(new File(extentConfigPath));
	}
	
	@BeforeTest
	@Parameters({"browserName", "remoteURL"})
	public void setUpMethod(String browserName, String remoteURL) throws MalformedURLException {
		/*test = extent.startTest(this.getClass().getSimpleName() + " :: " + method.getName(), method.getName());
		test.assignAuthor("Sachin Roy");
		test.assignCategory("Functional Test");*/
		
		initializePages();
		
		DesiredCapabilities capability = null;

		if(remoteURL.equals("http://192.168.1.226:5566/wd/hub")) {
			capability = DesiredCapabilities.chrome();
			capability.setBrowserName("chrome");
			capability.setPlatform(Platform.WIN10);
		}
		
		else if(remoteURL.equals("http://192.168.1.226:5569/wd/hub")) {
			capability = DesiredCapabilities.firefox();
			capability.setBrowserName("firefox");
			capability.setPlatform(Platform.WINDOWS);
			capability.setVersion("62.0.3");
		}
		driver = new RemoteWebDriver(new URL(remoteURL), capability);
	}
	
	@AfterMethod
	public void tearDownMethod(ITestResult result) {
		/*if(result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Testing failed");
			extent.endTest(test);
		}
		else if(result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, "Testing passed");
			extent.endTest(test);
		}*/
		driver.quit();
	}
	
	@AfterSuite
	public void tearDownSuite() {
		extent.flush();
		extent.close();
	}

}
