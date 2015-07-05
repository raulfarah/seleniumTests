package TestCase_Automation;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.*;
//import org.testng.log4testng.*;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.sun.javafx.Utils;

/**
 * Page element test class - common methods shared among page elements
 * @author afarah
 *
 */
public abstract class Page extends Logging {
	

	String browser = null;  

	
	
	static {
		
		log.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		log.info("^^^^^^^^^^^^^^^^^^^^^^^ Test Execution: ^^^^^^^^^^^^^^^^^^^^^");
		log.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

	}   
	
	/**
	 * get the current page URL and compare against  given URL
	 * @param driver 	WebDriver handle
	 * @param url    	Page URL
	 * @return 			true or false
	 */
	public boolean verifyPageUrl(WebDriver driver, String url){		  

		log.info("verify page URL ");		
		
		if (driver.getCurrentUrl().compareToIgnoreCase(url) == 0) {
			return true;
		}
		log.error("ERROR verifying URL" +  driver.getCurrentUrl() + " vs expected: " + url);
		return false;


	}	  
	
	/**
	 * get page title and compare against expected
	 * @param driver  	WebDriver handle
	 * @param title   	Page title to verify
	 * @return			true or false
	 */
	public boolean verifyTitle(WebDriver driver, String title){		  

		log.info("Verify page title ");
		
		if (driver.getTitle().compareToIgnoreCase(title) == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param driver    WebDriver handle
	 * @param title		Page title
	 * @return			true or false
	 */
	public boolean verifyPageTitleContains(WebDriver driver, String title){		  
	  
		log.info("Verify partial page title ");

		if ( driver.getTitle().contains(title)  ) {
			return true;
		}
		log.error("ERROR >>>>>> verifyTitle contains " + driver.getTitle() + " vs expected: " + title);

		return false;
	}
	  
	
	/**
	 * Verify text on page
	 * @param driver		WebDriver handle
	 * @param txt			String Text to verify
	 * @return				true or false
	 */
	public static boolean verifyPageText(WebDriver driver, String txt) {
		
		log.info("Verify Page Text ");
	  
		if ( driver.getPageSource().contains(txt) ) {
		  
			return true;
		}		  
		log.error("error Text not found ");

		return false;
	}
  
	
	/**
	 * verify any given WebElement is displayed on the page
	 * 
	 * @param driver		WebDriver handle
	 * @param elem			WebElement
	 * @return				true or false
	 */
	static public boolean elementIsDisplayed(WebDriver driver, WebElement elem) {
		
		log.info("Check element is displayed ");
		
		try {			
			
			if (elem.isDisplayed()){
				return true;	
			}
			return false;
			
		} catch(NoSuchElementException e) {
			return false;			
		}			
	}	
	
	/**
	 * Find Web Element on page
	 * @param driver 	WebDriver driver
	 * @param expr	 	String expr (expression to find id, xpath etc.)
	 * @param args   	optional 1 or more params - defaults to xpath
	 * @return       	WebElement if found
	 */
	static public WebElement findElem(WebDriver driver, String expr, String ... args) {
		
				
		WebElement elm = null;
		List<WebElement> allElements;
		By by = null;
		
		
		String findBy = "";     // defaults to xpath

		if (args.length > 0) {
			
			findBy = args[0];     // if specified (defaults is xpath)
			
			// log.debug("By method especified, Not Defaulting to xpath");

		}
		
		switch(findBy.toLowerCase()){
		
			case "xpath":    		{	 by = By.xpath(expr);			break;}
			case "id":       		{	 by = By.id(expr);	       		break;}
			case "linktext": 		{	 by = By.linkText(expr);		break;}
			case "tagname":  		{	 by = By.tagName(expr);	    	break;}
			case "cssselector":     {	 by = By.cssSelector(expr);		break;}
			case "classname":    	{	 by = By.className(expr);		break;}
			case "name":    		{	 by = By.name(expr);			break;}
			case "partiallinktext": {	 by = By.partialLinkText(expr);	break;}	
			default:				{	 by = By.xpath(expr);			break;}
		}
		
		/**
		 *  getting all should be faster than getting one in case elem not found
		 *  findElement polls for elements until it times out. very time wasting for 
		 *  failure tests
		 */
		
		try {
			//List<WebElement> allElements = driver.findElements(By.xpath(expr));
			
			allElements = driver.findElements(by);
			
			// get first element in the list
			if (allElements.size() == 0){
				
				log.info("No elem found " + expr + " " + by);
				
				return elm;
			}
			elm = allElements.get(0);
			
			log.debug("Find: " + elm.toString());
			return elm;
			
		} catch(ElementNotFoundException e) {
			return null;
		}		

	}	

	/**
	 * go to a page
	 * @param driver		WebDriver handle
	 * @param url			Page URL
	 * @return				true or false
	 */
	public boolean loadPage(WebDriver driver, String url){
		   
		driver.get(url);  		   
		log.info("go to page " + url);
		return true;		   
	}   			  

	
	/**
	 * page reload / refresh
	 * @param driver  		WebDriver instance handle
	 */
	public void pageRefresh(WebDriver driver) {
			
		driver.navigate().refresh();

		//loadPage(driver, driver.getCurrentUrl());   
			
	}
	
	
	/**
	 * get the elem / link text
	 * @param elem		WebElement
	 * @return			WebElement text
	 */
	public String getText(WebElement elem) {
		
		String text = elem.getText();
		return text;
	}
	
	/**
	 * Display System properties of the test setup
	 */
	public void displaySystemProperties() {
		
		// display system properties for debugging purposes, can be turned off
		if (DEBUG_ENABLED) {
		
			log.debug("----Java System Properties----");     
			
			Map<Object, Object> prop = System.getProperties();
			Set<Object> props = prop.keySet();
			for (Object key : props) {
				log.debug(key + "=" + prop.get(key));
			}   
		
			log.debug("----System Environment Variables----");
			
			Map<String, String> env = System.getenv();
			Set<String> keys = env.keySet();
			for (String key : keys) {
				log.debug(key + "=" + env.get(key));
			}   
		}			
	}	
}