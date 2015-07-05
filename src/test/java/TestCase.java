package TestCase_Automation;

import static org.testng.AssertJUnit.assertEquals;

import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;


/**
 * This is the base test case class - This class reads test data from excel 
 * utilizing methods defined in DataProviders class.
 * 
 * 
 */
public class TestCase extends Page {
  
	/**
	 * WebDriver instance must be instantiated if tests are going to be run in parallel.
	 * Default browser is HtmlUnit
	 * @see testNG xml file for test Parameters.
	 */
	
	String  SelectedBrowser;
	
	// Factory repeats the tests for all browsers specified by dataPovider
	// also notice parallel execution may be selected in the TestNg.xml
	
	@Factory(dataProvider="browsers", dataProviderClass = DataProviders.class)
	public TestCase(String browserName) {    
		
		SelectedBrowser = browserName;
	}
	
	protected WebDriver  driver = null;
	boolean loaded = false;
  
	// constructor
	public TestCase() {		  
		  
	}
  
	/**
	 * This method is called once, before the start of the tests/
	 * It start the Selenium WebDriver instance for a given browser.
	 * valid browser names : Firefox or ff, Chrome, internetexplorer or IE, 
	 * HtmlUnit (NOT case sensitive)
	 */
	/**
	 * 
	 */
	//@Parameters("browserOption")          // alternative to factory	
	@BeforeClass
	//public void beforeClass(@Optional("HtmlUnit") String SelectedBrowser) {	  
	public void beforeClass() {	  
	          
        browser = SelectedBrowser;
        
        log.info("----------------------------------------------------------");
        log.info(" - Opening <" + browser + "> browser");	  
        log.info("----------------------------------------------------------");

        // handle to current open browser instance - if parallel testing,
        // may have more than one driver instance at a time
	  
        BrowserWebDriver dr = new BrowserWebDriver(browser);
        driver = dr.driver;              
	  
        loadPage(driver, BASE_URL);
	}  
  
	/**
	 * Cleanup after the tests - shutdown the Selenium WebDriver
	 */
	@AfterClass
	public void afterClass() {
		  
		log.info("afterClass - closing <" + browser + "> browser");	  		  
		driver.quit();
		
		displaySystemProperties();
	} 
	
	/**
	 * This method runs before each test. Test initialization can go in here.
	 * @param m		Method to that is being executed
	 */
	@BeforeMethod
	public void beforeMethod(Method m) {
	  
		log.info("-----------------------------------------------------------");
		log.info("Starting: " + m.getName() + " on <" + browser +          ">");
		log.info("-----------------------------------------------------------");
	}
  
	/**
	 * Clean up after each test 
	 */
	@AfterMethod
	public void afterMethod() {	  	  	  
	}
  
	
	/**
	 * Verify pages, Navigate to a given page and verify the expected contents
	 * 
	 * @param test			test number or name
	 * @param descr			test description
	 * @param pageUrl		page URL
	 * @param txt			Text to verify on page
	 * @param expResult		true or false
	 */
	@Test(dataProvider="pageTests", dataProviderClass = DataProviders.class )
	public void Test1(String test, String descr, String pageUrl, String txt, boolean expResult) {	  

		driver.get(pageUrl);  
		boolean ret = Page.verifyPageText(driver, txt);
		assertEquals(txt, expResult, ret);			  
	}
	
	/**
	 * User register (sign up) tests
	 * @param test			test number or name
	 * @param descr			description
	 * @param fName			first name
	 * @param lName			last name
	 * @param userName		user name or email
	 * @param pswd			password
	 * @param expResult		expected result true or false
	 */
  
	@Test(dataProvider="signUpTests", dataProviderClass = DataProviders.class, enabled = false)
	public void Test2_userRegister(String test, String descr, String fName, String lName, String userName, String pswd, boolean expResult) {
	  
		log.info(test + ": " + descr);		
		
		log.debug(fName + " " + lName + " " + userName + " " + pswd + " " + expResult ); 			
	  
		driver.get(Locators.BASE_URL);

		// user instance
		Users newUser = new Users(driver);         
	  
		// register / sign-up user
		newUser.register(fName,lName,userName,pswd);
	  
		// verify user is automatically signed-in after successful sign up
		assertEquals(descr,expResult, newUser.verifyLogin());
	  
		// refresh page and verify again
		driver.navigate().refresh();
		assertEquals(descr, expResult, newUser.verifyLogin());

		// logout, login, verify login-in is success
		newUser.logout();	  
		newUser.login();	  
		assertEquals(descr, expResult, newUser.verifyLogin());
	  
		newUser.logout();	  
	}
	/**
	 * 
	 * @param test 				Name or number
	 * @param descr  			Test Description
	 * @param userName  		User logging name or email
	 * @param pswd				User password
	 * @param expResult 		true or false
	 */
	@Test(dataProvider="loginTests", dataProviderClass = DataProviders.class, 
			invocationCount=1, threadPoolSize=1, dependsOnMethods={"Test2_userRegister"}, enabled = false
	)	
	public void Test3_userLogin(String test, String descr, String userName, String pswd, boolean expResult) {
	  
		log.info(test + ": " + descr);

		driver.get(BASE_URL);

		Users user = new Users(driver,userName, pswd);    // constructor initializes

		// login the user 
		user.login();	  
	  
		// verify login success
		assertEquals(expResult, user.verifyLogin());
	  
		
		pageRefresh(driver);
	  
		// verify user still logged-in
		assertEquals(descr, expResult, user.verifyLogin());   

		user.logout();
	  
	}
	
	/**
	 * 
	 * @param test      	name 
	 * @param descr 		Test Description 
	 * @param locator		Xpath etc.
	 * @param sTxt      	Link Text to Verify
	 * @param expected 		true or false
	 * see the Excel sheet/TC for the details of the params
	 */
	@Test(dataProvider="linkTests", dataProviderClass = DataProviders.class, enabled = false, priority= -9)
	public void Test4_links(String test, String descr, String locator, String sTxt, boolean expected){
	  
		log.info(test + ": " + descr);

		driver.get(BASE_URL);             // back to known state	  
	  
		// instantiate a link class, must pass WebDriver instance
		Link link = new Link(driver);	  
	  
	  
		link.findLink(BY_XPATH, locator);
	  
		// verify link is displayed on the page
		assertEquals(descr, expected, link.isDisplayed());

		log.debug("@Test :  Link href= " +  link.href  + " vs " + driver.getCurrentUrl());
		log.debug(" page title: " + driver.getTitle() + " vs " + expected);

		// verify link text etc.
		assertEquals(descr, expected, link.verifyLinkTextContains(sTxt));
	
		// click on the link
		link.clickLink();

		// verify link target page title
		// assertEquals(expected, verifyPageTitleContains(driver, "some title"));   // TBD


		// verify the link target specified by the href attribute is the current page after
		// following the link
		assertEquals(expected, verifyPageUrl(driver, link.href));      	  
	}  
  
	
	/**
	 * 
	 * @param page				Page URL
	 * @param img				Image 
	 * @param sTxt				Text
	 * @param expResult			true or false
	 */
	@Test(dataProvider="imageTests", dataProviderClass = DataProviders.class, enabled = false)
	public void Test5_images(String page, String img, String sTxt, boolean expResult) {
	  
		// TODO
		log.info("@Test 3 : using  " + browser);
	  
		loadPage(driver, page);
	  
		if(verifyPageText(driver, Locators.pageNotFound_error) ){
		  
			// page error
			assertEquals(expResult, false);    //passes if fail was expected
		  
		} else {
			
			// TODO verify IMG, size, visible, etc.		  
			log.info("verify image " + page + " vs " + img + " " + expResult);	  
			assertEquals(expResult,true);
		}

	}	
	
	
	/**
	 * Verify all the images on the page have loaded, no missing images
	 * @param page			Page url
	 * @param expResult		true or false
	 */
	@Test(dataProvider="pageList", dataProviderClass = DataProviders.class, enabled=false)
	public void Test6_verifyAllPageImages(String page, boolean expResult) {
	  
		
		driver.get(page);		// navigate to test page
	  
		boolean actualResult = true;
	  
		// get all the images on the page, exclude slide show	  
		List<WebElement> allImages = driver.findElements(By.xpath(Locators.allImagesOnPage));
	  
		for (WebElement image : allImages) {
			try {
		      
				/* htmlUnit has problems with this line!!
			  	loaded = (boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete", image);	   
			  	if (!loaded) {    */		      		      

				// check if the image has loaded (size not 0, or ALT attribute text not displayed)
		      
				if (! Image.verifyImage(driver, image)) {	
		    	   
			    	actualResult = false;
				    log.error("ERROR Image did NOT load =====> " + page + " " 
			    	                                     + image.getAttribute("src") + " " + image.getSize() );					    	
			    } 				    
			    
			} catch (ElementNotVisibleException e) {			  
				actualResult = false;
				log.error("ERROR: ElementNotVisibleException" + image.getAttribute("src"));
			}		   
		    
		}
		assertEquals(expResult,actualResult);	  	  
	}
}