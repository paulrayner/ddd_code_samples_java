package warranty;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

import warranty.util.ImmutableDate;

public class TestClaimsAdjudication {

	public Contract testContract() throws ParseException {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");

		TermsAndConditions termsAndConditions = new TermsAndConditions(
				ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime()),
				ImmutableDate.of(sourceFormat.parse("08-05-2012").getTime()),
				ImmutableDate.of(sourceFormat.parse("08-05-2010").getTime()),
				90);
		
		Contract contract = new Contract(999, 100.0, termsAndConditions);

		return contract;
	}

	@Test
	public void testClaimsAdjudication() throws ParseException {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");
		Contract contract = testContract();
		Claim claim = new Claim(888, 79.0, ImmutableDate.of(sourceFormat.parse(
				"08-05-2010").getTime()));
		ClaimsAdjudicationService adjudicator = new ClaimsAdjudicationService();

		adjudicator.Adjudicate(contract, claim);

		Assert.assertEquals(1, contract.getClaims().size());
	}

	@Test(expected = ContractException.class)
	public void testClaimsAdjudicationForClaimExceedingLimitOfLiability()
			throws ParseException {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");
		Contract contract = testContract();
		Claim claim = new Claim(888, 81.0, ImmutableDate.of(sourceFormat.parse(
				"08-05-2010").getTime()));
		ClaimsAdjudicationService adjudicator = new ClaimsAdjudicationService();

		adjudicator.Adjudicate(contract, claim);

		Assert.fail();
	}

	@Test(expected = ContractException.class)
	public void testClaimsAdjudicationForPendingContract()
			throws ParseException {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");
		Contract contract = testContract();
		Claim claim = new Claim(888, 81.0, ImmutableDate.of(sourceFormat.parse(
				"08-04-2010").getTime()));
		ClaimsAdjudicationService adjudicator = new ClaimsAdjudicationService();

		adjudicator.Adjudicate(contract, claim);

		Assert.fail();
	}

	@Test(expected = ContractException.class)
	public void testClaimsAdjudicationForExpiredContract()
			throws ParseException {
		SimpleDateFormat sourceFormat = new SimpleDateFormat("MM-dd-yyyy");
		Contract contract = testContract();
		Claim claim = new Claim(888, 81.0, ImmutableDate.of(sourceFormat.parse(
				"08-05-2012").getTime()));
		ClaimsAdjudicationService adjudicator = new ClaimsAdjudicationService();

		adjudicator.Adjudicate(contract, claim);

		Assert.fail();
	}
}
