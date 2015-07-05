package TestCase_Automation;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *@author afarah:
 * This class opens instance of Selenium Webdriver for a specified browser. It is important to instantiate
 * new instances, if the test are to be run in parallel.
 * browser names Firefox (or ff), Chrome, HtmlUnit, internetExplorer (or IE)  - all single words, not case sensitive.
 * see TestNG.xml  parameter entries.
 * 
 */
public class BrowserWebDriver extends Logging {
	
	WebDriver  driver = null;
	
	String browserName = "unknown";
	String browserVersion = "unknown";
	boolean jsEnabled;
	
	
	/**
	 * Opens selected WebDriver browser
	 * @param browser Browser name (Chrome, firefox, htmlunit, internetexplorer etc.)
	 */
	public BrowserWebDriver(String browser){		
		
		switch(browser.toLowerCase()) {
		
		case "htmlunit": 
		case "headless": {
			
			this.driver =  new HtmlUnitDriver();   // true = js enabled
			
			((HtmlUnitDriver) driver).setJavascriptEnabled(true);    // enable js			

			break;
		}
		case "chrome": {
			/**
			 * Optional, if not specified, WebDriver will search your path for chromedriver.exe
			 * This file must be first down loaded and extracted
			 */
			
			// System.setProperty("webdriver.chrome.driver", "C:/Users/afarah/Desktop/SeleniumChromeDriver/chromedriver_win32/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", "C:/SeleniumDriverServers/chromedriver_win32/chromedriver.exe");

			this.driver = new ChromeDriver();
			
			
			Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
			browserName = caps.getBrowserName();
			browserVersion = caps.getVersion();
			jsEnabled = caps.isJavascriptEnabled();

			
			break;
		}
		case "internetexplorer": 
		case "ie": {
			
			System.setProperty("webdriver.ie.driver", "C:/SeleniumDriverServers/IEDriverServer_Win32_2.46.0/IEDriverServer.exe");
			//System.setProperty("webdriver.ie.driver", "C:/SeleniumDriverServers/IEDriverServer_x64_2.46.0/IEDriverServer.exe");

			this.driver = new InternetExplorerDriver();
			break;
		}
		case "firefox": 
		case "ff": {
			

			this.driver = new FirefoxDriver();	
			
			/**
			 * the following lines no longer needed, Selenium2.46 resolved
			 * the "FF stopped working..." pop-up alert
			 */
			
			//WebDriverWait wait = new WebDriverWait(this.driver, 5);
	        //wait.until(ExpectedConditions.alertIsPresent());
			//Alert alert = this.driver.switchTo().alert();     // get rid off "ff stopped working" alert
			//alert.dismiss();
			
			Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
			browserName = caps.getBrowserName();
			browserVersion = caps.getVersion();
			jsEnabled = caps.isJavascriptEnabled();
			
	        
			
			break;
		}
		default: {
			this.driver = new HtmlUnitDriver();
			log.info("Opening browser: " + browser);
			break;
		}
		}
		this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		this.driver.manage().window().maximize();
		

		log.info("Opened browser: " + browser);	
		log.info(browserName + " version: " + browserVersion + ", Java Script Enabled = " + jsEnabled);

	
	
	}
}
