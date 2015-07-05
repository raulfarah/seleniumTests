package TestCase_Automation;

/**
 * This class defines Test Case static variable and constants
 * @author afarah
 *
 */

public class Vars {
	
	
	// CONSTANTS
	
	final String BY_ID 		= "id";
	final String BY_CSS 	= "cssSelector";
	final String BY_NAME 	= "name";
	final String BY_XPATH	= "xpath";
	final String BY_TAG_NAME 	= "tagName";
	final String BY_LINK_TEXT 	= "linkText";
	final String BY_CLASS_NAME 	= "className";
	final String BY_PARTIAL_LINK_TEXT = "partialLinkText";	
	
	final static String HOME_PAGE	 	=	"http://localhost/latienda/index.php";
	final static String BASE_URL 		= 	"http://localhost/latienda";
	final static String ABOUT_PAGE 		= 	"http://localhost/latienda/aboutus.php";	
	final static String CONTACT_PAGE 	= 	"http://localhost/latienda/contactus.php";
	final static String INVALID_PAGE	= 	"http://localhost/latienda/index1.php";

	final static String DIR_PATH	= 	"http://localhost/latienda/";	
	
	//final static String EXCEL_DATA_FILE = "D:/seleniumTestng.xls";
	final static String EXCEL_DATA_FILE = "resources/SeleniumTestNG.xls";



	
   
	// images variables
    static String logo_img = "summer.jpg";
    static String amazon_img = DIR_PATH + "/images/amazonAcceptance.jpg";
	
    /** log4j log file
     * @see log4j2.xml (log4j creates the directories if not already exist)
     * log file name without the extension.    
     */
    static final String LOG_FILE_NAME = "SeleniumTestNG";    // without ext
    static final String LOG_FILE_PATH = "c:/TestNG_logs";
    static final String TEST_PHASE    = "regression";          
    
    /**
     * if set to true, the script will log system data at the end
     * System.properties and System.getenv outputs
     */
    static final boolean DEBUG_ENABLED = false;    			
    
	// Errors
	static String pageNotFound_error = "Not Found";
	
	//

}
