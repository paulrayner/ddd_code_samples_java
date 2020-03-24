package warranty;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

import warranty.util.ImmutableDate;

public class TestTermsAndConditions {
	
    @Test
    public void extendedByOneYear() throws ParseException
    {
    	// A value object must be created whole.
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");

		ImmutableDate effectiveDate = ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime());
		ImmutableDate purchaseDate = ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime());
		ImmutableDate expirationDate = ImmutableDate.of(sourceFormat.parse("08-05-2012").getTime()); 

		TermsAndConditions termsAndConditions = new TermsAndConditions(
														effectiveDate,
														expirationDate, 
														purchaseDate, 90);

		ImmutableDate newExpirationDate = ImmutableDate.of(sourceFormat.parse("08-05-2013").getTime()); 
		TermsAndConditions expectedTermsAndConditions = new TermsAndConditions(
														effectiveDate,
														newExpirationDate, 
														purchaseDate, 90);

		Assert.assertEquals(expectedTermsAndConditions, termsAndConditions.annuallyExtended());

        // Note: annuallyExtended is a side-effect-free function, with closure of operation
        TermsAndConditions expectedUnmodified = new TermsAndConditions(
				effectiveDate,
				expirationDate, 
				effectiveDate, 90);
        
        Assert.assertEquals(expectedUnmodified, termsAndConditions);       
    }
    
}
