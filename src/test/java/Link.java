package TestCase_Automation;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Link class - handles all anchor element related tests and methods.
 * @author afarah
 *
 */
public class Link extends Page {
	
	public WebElement elem = null;	
	private WebDriver driver;	
	public String href = "";       // link href is stored before link click
	public String linkText = "";
	
    // constructor
	public Link(WebDriver dr) {
		
		this.driver = dr;	
	}	
	
	/**
	 * 
	 * @param sLink					Link to find
	 * @param xpathExpression		Find By expression
	 * @return						true or false
	 */
	public boolean findLink(String sLink, String xpathExpression) {		
		
	    try {
	    	log.info("@Test : >> " + xpathExpression);			  
	
	    	this.elem = findElem(driver, xpathExpression);
	    	
	    	// store before link href, for later comparisons
	    	this.href = this.elem.getAttribute("href");  
	    	this.linkText = this.elem.getText().toLowerCase();
	
	    	log.info("findLink >> link text " + this.linkText + " href: " + this.href);
		
		
		} catch(StaleElementReferenceException e) {
			
			log.error("ERROR  StaleElementReferenceException " );	
			  
			// e.printStackTrace();
	    }		
		return true;
	}
	
	/**
	 * verify link text matches given text
	 * @param sText text to verify
	 * @return    true or false
	 */
	public boolean verifyLinkText(String sText) {
		
		this.linkText = getText(this.elem);
		
		if ( this.linkText.compareToIgnoreCase(sText) == 0) {	
			
			log.info("Link text: " + this.linkText + " vs " + sText);
			return true;			
		}
		log.error("ERROR Link text: " + this.linkText + " vs " + sText);
		return false;
			
	}
	
	/**
	 * verify link text contains given text
	 * 
	 * @param sText 		text to verify
	 * @return  			true or false
	 */
	public boolean verifyLinkTextContains(String sText) {
		
		this.linkText = getText(this.elem).toLowerCase();
		
		if ( this.linkText.contains(sText.toLowerCase())) {	
			
			log.info("Link text: " + this.linkText + " vs " + sText);
			return true;			
		}
		log.error("ERROR Link text: " + this.linkText + " does not contain " + sText);
		return false;
			
	}
		
    /**
     * Verify if element is displayed/visible on the page
     * @return				true or false
     */
	public boolean isDisplayed(){		
		
		return this.elem.isDisplayed();
		
	}
	
	/**
	 * follow link
	 * @return  		true or false
	 */
	public boolean clickLink() {
		
		try {
			
			this.elem.click();
			log.info("clicking link" );		
			return true;
			
		} catch(NullPointerException e) {
			
			return false;
		}
		
		
	}
}