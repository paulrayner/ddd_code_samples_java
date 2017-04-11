package warranty;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

import warranty.util.ImmutableDate;

public class TestContractAdmin {
	
    @Test
    public void TestContractIsSetupCorrectly() throws ParseException
    {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");

		TermsAndConditions termsAndConditions = new TermsAndConditions(
				ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime()),
				ImmutableDate.of(sourceFormat.parse("08-05-2012").getTime()),
				ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime()),
				90);
		
		Contract contract = new Contract(999, 100.0, termsAndConditions);
        Assert.assertEquals(100.0, contract.purchasePrice,0.0);
    }
    
	
    @Test
    public void TestLimitOfLiabilityNoClaims() throws ParseException
    {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");

		TermsAndConditions termsAndConditions = new TermsAndConditions(
				ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime()),
				ImmutableDate.of(sourceFormat.parse("08-05-2012").getTime()),
				ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime()),
				90);
		
		Contract contract = new Contract(999, 100.0, termsAndConditions);
        Assert.assertEquals(80.0, contract.limitOfLiability(),0.0);
    }
    
    @Test
    public void TestLimitOfLiabilityOneClaim() throws ParseException
    {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");

		TermsAndConditions termsAndConditions = new TermsAndConditions(
				ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime()),
				ImmutableDate.of(sourceFormat.parse("08-05-2012").getTime()),
				ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime()),
				90);
		
		Contract contract = new Contract(999, 100.0, termsAndConditions);
		contract.add(new Claim(888, 10.0, ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime())));

		Assert.assertEquals(72.0, contract.limitOfLiability(),0.0);
    }
    

    @Test
    public void TestLimitOfLiabilityMultipleClaims() throws ParseException
    {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");

		TermsAndConditions termsAndConditions = new TermsAndConditions(
				ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime()),
				ImmutableDate.of(sourceFormat.parse("08-05-2012").getTime()),
				ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime()),
				90);
		
		Contract contract = new Contract(999, 100.0, termsAndConditions);
		contract.add(new Claim(888, 10.0, ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime())));
		contract.add(new Claim(888, 20.0, ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime())));

		Assert.assertEquals(56.0, contract.limitOfLiability(),0.0);
    }
}

