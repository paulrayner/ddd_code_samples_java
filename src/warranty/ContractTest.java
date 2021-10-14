package warranty;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ContractTest {

    @Test
    public void TestContractIsSetupCorrectly()
    {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract = new Contract(100.0, product, termsAndConditions);

        assertNotNull(contract.id);
        assertEquals(100.0, contract.purchasePrice);
        assertEquals(Contract.Status.PENDING, contract.status);
        assertEquals(termsAndConditions, contract.termsAndConditions);
        assertEquals(product, contract.coveredProduct);
    }

    @Test
    public void TestContractInEffectBasedOnStatusAndEffectiveAndExpirationDateRange() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract = new Contract(100.0, product, termsAndConditions);

        // check PENDING (default) state
        assertFalse(contract.InEffectFor(new Date(2010, 5, 9)));
        // check ACTIVE state
        contract.status = Contract.Status.ACTIVE;
        assertFalse(contract.InEffectFor(new Date(2010, 5, 7)));
        assertTrue(contract.InEffectFor(new Date(2010, 5, 8)));
        assertTrue(contract.InEffectFor(new Date(2013, 5, 7)));
        assertFalse(contract.InEffectFor(new Date(2013, 5, 9)));
    }

    @Test
    public void TestClaimTotalSumOfClaimAmounts() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract = new Contract(100.0, product, termsAndConditions);

        assertNotNull(contract.id);
        contract.add(new Claim(10.0, new Date(2010, 10, 1)));
        contract.add(new Claim(20.0, new Date(2010, 10, 1)));

        assertEquals(30.0, contract.ClaimTotal());
    }

    @Test
    public void TestLimitOfLiabilityWithNoClaims() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract = new Contract(100.0, product, termsAndConditions);

        assertNotNull(contract.id);

        assertEquals(80.0, contract.LimitOfLiability());
        assertTrue(contract.WithinLimitOfLiability(10));
        assertTrue(contract.WithinLimitOfLiability(79));
        assertFalse(contract.WithinLimitOfLiability(80)); // Must be less than the limit amount
        assertFalse(contract.WithinLimitOfLiability(90));
    }

    @Test
    public void TestLimitOfLiabilityWithOneClaim() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract = new Contract(100.0, product, termsAndConditions);

        assertNotNull(contract.id);
        contract.add(new Claim(10.0, new Date(2010, 10, 1)));

        assertEquals(70.0, contract.LimitOfLiability());
    }

    @Test
    public void TestLimitOfLiabilityWithMultipleClaims() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract = new Contract(100.0, product, termsAndConditions);

        assertNotNull(contract.id);
        contract.add(new Claim(10.0, new Date(2010, 10, 1)));
        contract.add(new Claim(20.0, new Date(2010, 10, 1)));

        assertEquals(50.0, contract.LimitOfLiability());
    }

    @Test
    public void TestExtendAnnualSubscription() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract = new Contract(100.0, product, termsAndConditions);

        contract.ExtendAnnualSubscription();
        var extendedTermsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2014, 5, 8));

        assertEquals(extendedTermsAndConditions, contract.termsAndConditions);
    }

    // Entities compare by unique IDs, not properties
    @Test
    public void TestContractEquality()
    {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract1 = new Contract(100.0, product, termsAndConditions);
        Contract contract2 = new Contract(100.0, product, termsAndConditions);
        Contract contract3 = new Contract(100.0, product, termsAndConditions);

        UUID expectedId = UUID.randomUUID();
        contract1.id = expectedId;
        contract2.id = expectedId;

        assertEquals(contract2, contract1);
        assertNotEquals(contract3, contract1);
    }

}