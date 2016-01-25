package warranty;

import static junit.framework.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TestTermsAndConditions {
	
    @Test
    public void extendedByOneYear() throws ParseException
    {
    	// A value object must be created whole.
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");

		Date effectiveDate = sourceFormat.parse("08-05-2010");
		Date purchaseDate = sourceFormat.parse("08-05-2010");
		Date expirationDate = sourceFormat.parse("08-05-2012"); 

		TermsAndConditions termsAndConditions = new TermsAndConditions(
														effectiveDate,
														expirationDate, 
														purchaseDate, 90);

		Date newExpirationDate = sourceFormat.parse("08-05-2013"); 
		TermsAndConditions expectedTermsAndConditions = new TermsAndConditions(
														effectiveDate,
														newExpirationDate, 
														purchaseDate, 90);

        assertEquals(expectedTermsAndConditions, termsAndConditions.annuallyExtended());

        // Note: annuallyExtended is a side-effect-free function, with closure of operation
        TermsAndConditions expectedUnmodified = new TermsAndConditions(
				effectiveDate,
				expirationDate, 
				effectiveDate, 90);
        
        assertEquals(expectedUnmodified, termsAndConditions);       
    }
    
}
