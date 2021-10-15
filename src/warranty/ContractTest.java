package warranty;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ContractTest {

    @Test
    public void contractIsSetupCorrectly()
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
    public void contractInEffectBasedOnStatusAndEffectiveAndExpirationDateRange() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract = new Contract(100.0, product, termsAndConditions);

        contract.status = Contract.Status.PENDING;
        assertFalse(contract.inEffectFor(new Date(2010, 5, 9)));

        contract.status = Contract.Status.ACTIVE;
        assertFalse(contract.inEffectFor(new Date(2010, 5, 7)));
        assertTrue(contract.inEffectFor(new Date(2010, 5, 8)));
        assertTrue(contract.inEffectFor(new Date(2013, 5, 7)));
        assertFalse(contract.inEffectFor(new Date(2013, 5, 9)));

        contract.status = Contract.Status.EXPIRED;
        assertFalse(contract.inEffectFor(new Date(2010, 5, 8)));
    }

    @Test
    public void claimTotalSumOfClaimAmounts() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract = new Contract(100.0, product, termsAndConditions);

        contract.add(new Claim(10.0, new Date(2010, 10, 1)));
        contract.add(new Claim(20.0, new Date(2010, 10, 1)));

        assertEquals(30.0, contract.claimTotal());
    }

    @Test
    public void claimAmountsWithinLimitOfLiability() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract = new Contract(100.0, product, termsAndConditions);

        assertTrue(contract.withinLimitOfLiability(10));
        assertTrue(contract.withinLimitOfLiability(79));
        assertFalse(contract.withinLimitOfLiability(80)); // Must be less than the limit amount
        assertFalse(contract.withinLimitOfLiability(90));
    }

    @Test
    public void activeContractCoverage() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract = new Contract(100.0, product, termsAndConditions);

        contract.status = Contract.Status.PENDING;
        assertFalse(contract.covers(new Claim(10.0, new Date(2010, 10, 1))));

        contract.status = Contract.Status.ACTIVE;
        assertTrue(contract.covers(new Claim(10.0, new Date(2010, 10, 1))));
        assertTrue(contract.covers(new Claim(79.0, new Date(2010, 10, 1))));
        assertFalse(contract.covers(new Claim(80.0, new Date(2010, 10, 1))));
        assertFalse(contract.covers(new Claim(90.0, new Date(2010, 10, 1))));

        contract.status = Contract.Status.EXPIRED;
        assertFalse(contract.covers(new Claim(10.0, new Date(2010, 10, 1))));
    }

    @Test
    public void extendAnnualSubscription() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract = new Contract(100.0, product, termsAndConditions);

        contract.extendAnnualSubscription();
        var extendedTermsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2014, 5, 8));

        assertEquals(extendedTermsAndConditions, contract.termsAndConditions);
        assertEquals(1, contract.events.size());
        assertTrue(contract.events.get(0) instanceof SubscriptionRenewed);
        assertEquals(contract.id, ((SubscriptionRenewed) contract.events.get(0)).contract_id);
        assertEquals("Automatic Annual Renewal", ((SubscriptionRenewed) contract.events.get(0)).reason);
    }

// TODO: Practice using domain events by making the terminate contract test pass
//    @Test
//    public void TestTerminateContract() {
//        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
//        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
//        Contract contract = new Contract(100.0, product, termsAndConditions);
//
//        contract.terminate("Debbie", "Limit of Liability Exceeded");
//
//        assertEquals(Contract.Status.FULFILLED, contract.status);
//        assertEquals(1, contract.events.size());
//        assertTrue(contract.events.get(0) instanceof CustomerReimbursementRequested);
//        assertEquals(contract.id, ((CustomerReimbursementRequested) contract.events.get(0)).contract_id);
//        assertEquals("Debbie", ((CustomerReimbursementRequested) contract.events.get(0)).rep_name);
//        assertEquals("Limit of Liability Exceeded", ((CustomerReimbursementRequested) contract.events.get(0)).reason);
//    }

    // Entities compare by unique IDs, not properties
    @Test
    public void contractEquality()
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