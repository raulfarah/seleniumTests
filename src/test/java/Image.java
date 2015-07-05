package TestCase_Automation;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Images tests class handles all image tag related tests.
 * @author afarah
 *
 */
public class Image extends Page {
	
	static public boolean verifyImage(WebDriver driver, WebElement image) {
		
		boolean ret = true;
		//log.info("Verify image ");

			
		try {
			
			// check if the image ALT attribute text is not visible on the page
		    String imageAlt = image.getAttribute("alt").toString();		    
		    
		    // if ( verifyPageText(driver, imageAlt) ) {
		    if (! image.isDisplayed()) {
		    	
				// if ALT text is visible, then the image is not displayed!!
		    	log.error("28 ");  // + imageAlt + "src: " + image.getAttribute("src") + " size: " + image.getSize() );
			    
	            ret = false;		    	
		    }		
			
		} catch(NullPointerException e) {
			
			log.error("35 src: "); // + image.getAttribute("src") + "size: " + image.getSize() );
            ret = false;			
		}
		
			
		// has width
		if (! verifySize(driver, image)){
			
			log.error("43 error" + image.getAttribute("src") + " " + image.getSize() );
			 ret = false;
			 
		} else {
			
			log.debug("Image width OK => "  + image.getAttribute("src") + " " + image.getSize() );

		}	
		return ret;			
    		
	}	
	
	// verify image width is not zero
	static public boolean verifySize(WebDriver driver, WebElement elem) {
		
		log.info("Verify img size ");
		try {			
			
			// images has some width			
			if (elem.getSize().width <= 0){
				
				log.error("64 Image May NOT have loaded width: " + elem.getSize().width);
				return false;	
			}
			return true;

			
		} catch(NoSuchElementException e) {
			log.error("71 Image loaded problem ");
			return false;			
		}    		
	}	
	

}
