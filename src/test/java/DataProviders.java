package TestCase_Automation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import jxl.BooleanCell;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * This class houses the test data that drives this "Data Driven" Test Suite.
 * @author afarah
 * @since	2015
 * Excel file for the details of the test
 */
public class DataProviders extends Logging {  
	
	 //static InputStream fs = DataProviders.class.getResourceAsStream("/seleniumTestng.xls");
	
	//static InputStream fs = DataProviders.class.getResourceAsStream("/seleniumTestng.xls");
	
	static URL ur = DataProviders.class.getClassLoader().getResource("seleniumTestng.xls"); 
	
	
	

    @DataProvider(name="signUpTests")
	public static Object[][] signUpTests() throws BiffException {
    	
    	// test read from Excel
    	return getExcelData(EXCEL_DATA_FILE,"signUpTests");
		
    }

	@DataProvider(name="loginTests")
	public static Object[][] loginTests() throws BiffException {
	    
		// test read from Excel
    	return getExcelData(EXCEL_DATA_FILE,"loginTests");
	}
	
	/**
	 * 
	 * @return data object
	 * @throws BiffException BiffException
	 */
	@DataProvider(name="linkTests")
	public static Object[][] linkTests() throws BiffException {
		
		// test read from Excel
    	return getExcelData(EXCEL_DATA_FILE,"linkTests");    	
	    
	}
	
	@DataProvider(name="imageTests")
	public static Object[][] imageTests() throws BiffException {
		
		// test read from Excel
    	return getExcelData(EXCEL_DATA_FILE,"imageTests");    		   
	}


	/**
	 * List of browser names 
	 * @return object containing browser names
	 */
	@DataProvider(name="browsers", parallel = true)
	public static Object[][] browser() {
  	  	return new Object[][] {
  	  	  	{"HtmlUnit"},
  	  	  	{"chrome"},
  	  	  	{"firefox"}
  	  	};
	}
	
	// page names to test - these names are defined in Locators class
	/**
	 * 
	 * @return object
	 */
	@DataProvider(name="pageList", parallel = true)
	public static Object[][] pages() {
		return new Object[][] {
			{BASE_URL, true} 
  	  	};
	}
	
	// page + element to locate + locate by + str + text to verify + expected result
	// Not used so far
	/**
	 * 
	 * @return object
	 * @throws BiffException  BiffException
	 */
	@DataProvider(name="pageTests")
	public static Object[][] pageTest() throws BiffException {
		
		// test read from Excel
    	return getExcelData(EXCEL_DATA_FILE,"pageTests");    	
	    
	}
	
	
    /**
     * 
     * @param fileName  	File Name
     * @param sheetName    	Sheet Name
     * @return				object
     * @throws BiffException   BiffException
     */
	public static Object[][] getExcelData(String fileName, String sheetName) throws BiffException {
		Object[][] arrayExcelData = null;
		try {
			
			log.info("opening excel file " + fileName + " " + sheetName);
			
			System.out.println("uuuuuuuuuuuuuuuuuuuuur " + ur);
			InputStream fs =  ur.openStream(); 
			//FileInputStream fs = new FileInputStream(fileName);
			
			//Workbook wb = Workbook.getWorkbook( new File(fileName) );
			Workbook wb = Workbook.getWorkbook(fs);
			
			Sheet sh = wb.getSheet(sheetName);

			int totalNoOfCols = sh.getColumns();
			int totalNoOfRows = sh.getRows();
			
			arrayExcelData = new Object[totalNoOfRows-1][totalNoOfCols];

			
			for (int i= 1 ; i < totalNoOfRows; i++) {

				for (int j=0; j < totalNoOfCols; j++) {
					
					// determine the data type of the cells from the sheet
					// and store accordingly, otherwise all the numbers and booleans
					// become strings - causing data provider parameter type mismatches
					
					if ( sh.getCell(j,i).getType() == CellType.NUMBER) {

						// store as a number
						arrayExcelData[i-1][j] = ((NumberCell) sh.getCell(j, i)).getValue();
					
					} else if ( sh.getCell(j,i).getType() == CellType.BOOLEAN ) {
						
						arrayExcelData[i-1][j] = ((BooleanCell) sh.getCell(j, i)).getValue();							

					} else {
						// stored as string
						arrayExcelData[i-1][j] = sh.getCell(j, i).getContents();
					}
				}

			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrayExcelData;
	}


}