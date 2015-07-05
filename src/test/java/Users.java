package TestCase_Automation;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

/**
 * Users class handles all login, registration related tests
 * @author afarah
 */

public class Users  extends Page {
	
	private String firstName = "";
	private String lastName = "";
	private String userName = "";
	private String userPassword = "";	
	
	private WebDriver driver;	

	
	/**
	 * User constructor - 
	 * @param dr WebDriver instance - It is very important to pass driver instance in
	 * in order to be able to run the test in parallel. Otherwise the methods in this class
	 * may not know what instance of the WebDriver to operate upon - leading to all tests failing
	 * and hard to debug Null pointer exceptions
	 * see DataProviders for Locators elements
	 */

	public Users(WebDriver dr) {		
		
    	log.debug("Users constructor - no param");
    	
    	this.driver = dr;
		
	}
	
	/**
	 * User Constructor
	 * @param dr 			WebDriver instance
	 * @param args 			Variable number of input args
	 */
	public Users(WebDriver dr, String ... args) {		
		
    	this.driver = dr;
         
		initUser(args);

    	log.debug("Users constructor - 2 parameters");		
	}
	
	
	/**
	 * User registration
	 * find input Locators elements, enter user info, submit form
	 * @param args   		Variable number of input args
	 * @return 				true or false
	 */
	public boolean register(String ... args) {	
		
		
		initUser(args);

    	log.info("User registration");
    	
    	 		
    	try {
			findElem(driver, Locators.registerButton).click();
	
	    	findElem(driver, Locators.firstName_inputBox).sendKeys(firstName);
	    	
	    	findElem(driver, Locators.lastName_inputBox).sendKeys(lastName);
	
	    	findElem(driver, Locators.email_inputBox).sendKeys(userName);
	    	
	    	findElem(driver, Locators.passWord_inputBox).sendKeys(userPassword);
	    	
	    	findElem(driver, Locators.submitButton).click();    
	
    	return true;    		
    	
    	} catch(NullPointerException e) {
    		
    		return false;
    		
    	}    		
    	
	}
	/**
	 * User login
	 * @return   true or false
	 */
	public boolean login() {				
		
		try {
	    	log.info("User Login ");    	
	    		
			findElem(driver, Locators.email_inputBox).sendKeys(userName);
			
			findElem(driver,Locators.passWord_inputBox).sendKeys(userPassword);
			
			findElem(driver, "signin", BY_ID).click();  // submit() did not work, click() does
			
		
			return true;   		
		
		} catch(NullPointerException e) {
			
			return false;
		
		}	    		
	}
	
	
	/**
	 * Verify login success
	 * @return true or false
	 */
	public boolean verifyLogin() {		
			
		log.info("Verify login ");

		if ( findElem(driver, "user_account", BY_ID) == null ){
			
			return false;
		}
		return true;					
		
	}
	
	/**
	 * User logout
	 */
	
	public void logout() {
		
		log.info("Logout");
		
		findElem(driver,"logoutButton", BY_ID).click();
		
	}	
	
	
	/**
	 * initialize user info
	 * @param args     variable number of parameters - same order as the form fields
	 * @return  true or false
	 */
	public boolean initUser(String ... args){
		
		log.debug("initUser params: (" + args.length + ") " + args[0] + " " + args[1]);
				
		if (args.length == 4) {
			this.firstName    = args[0];
			this.lastName     = args[1];
			this.userName     = args[2];                    // possibly an email address
			this.userPassword = args[3];				
		}
		if (args.length == 2) {
			this.userName     = args[0];                    // possibly an email address
			this.userPassword = args[1];					
		}		
		return true;
		
	}

}
