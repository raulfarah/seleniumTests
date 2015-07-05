package TestCase_Automation;

import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Test result Logging / Reporting class using log4j
 * Log file name and path are defined as a system property so log4j2.xml file can access.
 * @author afarah
 *
 */
public class Logging extends Locators {


	// Define system property for the logging file so it is accessible in the log4j2.xml

	static {  
		System.setProperty("logfilePath", LOG_FILE_PATH);
		System.setProperty("logfileName", LOG_FILE_NAME);
		System.setProperty("testPhase", TEST_PHASE); 
		
		String tester = "tester unkown";
		tester = System.getProperty("user.name");
		System.setProperty("tester", tester); 


		
	}	
	
    public static  Logger log = LogManager.getLogger();   // log4j2   	
    
    static {

		// silence the HtmlUnit browser warning errors
	    LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

	    //java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF); 
        //java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
	  
	} 	
    static {
		log.info("******************* SYSTEM UNDER TEST ***********************");
		
		log.info("OS: "    + System.getProperty("os.name") +
		        " Architecture: "   + System.getProperty("os.arch") +
		        " version: "+ System.getProperty("os.version"));	
		
		log.info("Java run time: " + System.getProperty("java.runtime.name") +
			" Version: " + System.getProperty("java.runtime.version") +
			" Library: " + System.getProperty("java.library.path"));

		log.info("Java vm: "  + System.getProperty("java.vm.name") +
				 " version: " + System.getProperty("java.vm.version"));			
		
		log.info("Logfile: " + LOG_FILE_PATH + "/" + TEST_PHASE + "/<date>"+ "/" + LOG_FILE_NAME + "_<time>.log");

	}
}
