package warranty;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

import warranty.Contract;

import junit.framework.Assert;

public class TestContractAdmin {
	
    @Test
    public void TestContractIsSetupCorrectly() throws ParseException
    {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");

		TermsAndConditions termsAndConditions = new TermsAndConditions(
														sourceFormat.parse("08-05-2010"),
														sourceFormat.parse("08-05-2012"), 
														sourceFormat.parse("08-05-2010"), 90);
		
		Contract contract = new Contract(999, 100.0, termsAndConditions);
        Assert.assertEquals(100.0, contract.purchasePrice);
    }
    
	
    @Test
    public void TestLimitOfLiabilityNoClaims() throws ParseException
    {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");

		TermsAndConditions termsAndConditions = new TermsAndConditions(
														sourceFormat.parse("08-05-2010"),
														sourceFormat.parse("08-05-2012"), 
														sourceFormat.parse("08-05-2010"), 90);
		
		Contract contract = new Contract(999, 100.0, termsAndConditions);
        Assert.assertEquals(80.0, contract.limitOfLiability());
    }
    
    @Test
    public void TestLimitOfLiabilityOneClaim() throws ParseException
    {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");

		TermsAndConditions termsAndConditions = new TermsAndConditions(
														sourceFormat.parse("08-05-2010"),
														sourceFormat.parse("08-05-2012"), 
														sourceFormat.parse("08-05-2010"), 90);
		
		Contract contract = new Contract(999, 100.0, termsAndConditions);
		contract.add(new Claim(888, 10.0, sourceFormat.parse("08-05-2010")));

		Assert.assertEquals(72.0, contract.limitOfLiability());
    }
    

    @Test
    public void TestLimitOfLiabilityMultipleClaims() throws ParseException
    {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");

		TermsAndConditions termsAndConditions = new TermsAndConditions(
														sourceFormat.parse("08-05-2010"),
														sourceFormat.parse("08-05-2012"), 
														sourceFormat.parse("08-05-2010"), 90);
		
		Contract contract = new Contract(999, 100.0, termsAndConditions);
		contract.add(new Claim(888, 10.0, sourceFormat.parse("08-05-2010")));
		contract.add(new Claim(888, 20.0, sourceFormat.parse("08-05-2010")));

		Assert.assertEquals(56.0, contract.limitOfLiability());
    }
}

