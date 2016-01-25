package warranty;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import junit.framework.Assert;

import org.junit.Test;

public class TestClaimsAdjudication {
	
	public Contract testContract() throws ParseException
	{
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");

		TermsAndConditions termsAndConditions = new TermsAndConditions(
														sourceFormat.parse("08-05-2010"),
														sourceFormat.parse("08-05-2012"), 
														sourceFormat.parse("08-05-2010"), 90);
		
		Contract contract = new Contract(999, 100.0, termsAndConditions);

		return contract;
	}

	@Test
	public void testClaimsAdjudication() throws ParseException
	{
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");
		Contract contract = testContract();
		Claim claim = new Claim(888, 79.0, sourceFormat.parse("08-05-2010"));
		ClaimsAdjudicationService adjudicator = new ClaimsAdjudicationService();
		
		adjudicator.Adjudicate(contract, claim);
        
		Assert.assertEquals(1, contract.getClaims().size());
	}
	

	@Test
	public void testClaimsAdjudicationForClaimExceedingLimitOfLiability() throws ParseException
	{
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");
		Contract contract = testContract();
		Claim claim = new Claim(888, 81.0, sourceFormat.parse("08-05-2010"));
		ClaimsAdjudicationService adjudicator = new ClaimsAdjudicationService();
		
		adjudicator.Adjudicate(contract, claim);

		Assert.assertEquals(0, contract.getClaims().size());
	}
	
	@Test
	public void testClaimsAdjudicationForPendingContract() throws ParseException
	{
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");
		Contract contract = testContract();
		Claim claim = new Claim(888, 81.0, sourceFormat.parse("08-04-2010"));
		ClaimsAdjudicationService adjudicator = new ClaimsAdjudicationService();
		
		adjudicator.Adjudicate(contract, claim);

		Assert.assertEquals(0, contract.getClaims().size());
	}
	
	@Test
	public void testClaimsAdjudicationForExpiredContract() throws ParseException
	{
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");
		Contract contract = testContract();
		Claim claim = new Claim(888, 81.0, sourceFormat.parse("08-05-2012"));
		ClaimsAdjudicationService adjudicator = new ClaimsAdjudicationService();

		adjudicator.Adjudicate(contract, claim);

		Assert.assertEquals(0, contract.getClaims().size());
	}
}
