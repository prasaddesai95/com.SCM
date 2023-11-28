package com.SCM.GenericUtils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.SCM.ObjectPom.AdminHomePage;
import com.SCM.ObjectPom.LoginPage;

public class BaseClass {

	public DatabaseUtils dLib = new DatabaseUtils();
	public FileUtils fLib = new FileUtils();
	public ExcelUtils eLib= new ExcelUtils();
	public WebDriverUtils wLib = new WebDriverUtils();
	public WebDriver driver;
	public static WebDriver sdriver;
	
	@BeforeSuite
	public void config_BS() throws Throwable
	{
		dLib.connectDB();
		System.out.println("-- connect to DB --");
	}
	
	//@Parameters("BROWSER")
	@BeforeClass
	public void config_BC() throws Throwable
	{
		String BROWSER = fLib.readDataFromPropertyFile("browser");
		
		if (BROWSER.equalsIgnoreCase("chrome")) 
		{
			driver = new ChromeDriver();
			
		} 
		else if(BROWSER.equalsIgnoreCase("firefox")) 
		{
			driver = new FirefoxDriver();

		}
		else
		{
			System.out.println("-- Invalid Browser --");
		}
		sdriver = driver;
		wLib.maximizeWindow(driver);
		
	}
	
	@BeforeMethod
	public void config_BM() throws Throwable
	{
		String URL = fLib.readDataFromPropertyFile("url");
		String USERNAME = fLib.readDataFromPropertyFile("username");
		String PASSWORD = fLib.readDataFromPropertyFile("password");
		String LOGTYPE = fLib.readDataFromPropertyFile("admin3");
		
		driver.get(URL);
		wLib.waitforPageLoad(driver, 10);
		
		LoginPage lp = new LoginPage(driver);
		lp.login(USERNAME, PASSWORD, LOGTYPE);
		
		System.out.println("-- Logged in to application --");
	}
	
	@AfterMethod
	public void config_AM()
	{
		AdminHomePage ahp= new AdminHomePage(driver);
		ahp.Logout();
		System.out.println("-- Logged Out from appln --");
	}
	
	@AfterClass
	public void config_AC()
	{
		driver.quit();
		System.out.println("-- Browser closed --");
	}
	
	@AfterSuite
	public void config_AS() throws Throwable
	{
		dLib.disconnectDB();
		System.out.println("-- Disconnected from DB --");
	}
	
}
