package TestCase_Automation;

/**
 * @author afarah
 * This class contains element locators - all in one place!
 * All other variables used in this test case are located in Vars class.
 */
public class Locators extends Vars{

	  /**
	   * Only User Interface / element locators in this class!
	   */
	   	
	
		// user registration form fields - given human readable names
		static String registerButton = "//input[@name='registerButton']";
		
		static String firstName_inputBox = "//input[@name='firstname']";
		static String lastName_inputBox  = "//input[@name='lastname']";

		static String email_inputBox = "//input[@name='email']";
		static String submitButton   = "//input[@id='submitButton']";
		static String passWord_inputBox = "//input[@name='password']";

		
	 	
	 	
	 	// images locators
	 	
	 	// all the img tags except those under the slideShow dv
	 	static String allImagesOnPage = "//img[not(parent::div[@id='slideShow'])]";   	 		   

	
		public Locators() {
		// TODO Auto-generated constructor stub
		}

}
