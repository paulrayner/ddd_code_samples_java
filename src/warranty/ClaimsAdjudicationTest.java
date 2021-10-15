package warranty;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClaimsAdjudicationTest {

    Contract FakeContract()
    {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        Contract contract = new Contract(100.0, product, new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        contract.status = Contract.Status.ACTIVE;

        return contract;
    }

    @Test
    void adjudicateValidClaim() {
        Contract contract = FakeContract();
        Claim claim = new Claim(79.0, new Date(2010, 5, 8));

        new ClaimsAdjudication().adjudicate(contract, claim);

        assertEquals(1, contract.getClaims().size());
        assertEquals(79.0, contract.getClaims().get(0).amount);
        assertEquals(new Date(2010, 5, 8), contract.getClaims().get(0).failureDate);
    }

    @Test
    void adjudicateClaimWithInvalidAmount() {
        Contract contract = FakeContract();
        Claim claim = new Claim(81.0, new Date(2010, 5, 8));

        new ClaimsAdjudication().adjudicate(contract, claim);

        assertEquals(0, contract.getClaims().size());
    }

    @Test
    void adjudicateClaimForPendingContract() {
        Contract contract = FakeContract();
        contract.status = Contract.Status.PENDING;
        Claim claim = new Claim(79.0, new Date(2010, 5, 8));

        new ClaimsAdjudication().adjudicate(contract, claim);

        assertEquals(0, contract.getClaims().size());
    }

    @Test
    void adjudicateClaimForExpiredContract() {
        Contract contract = FakeContract();
        contract.status = Contract.Status.EXPIRED;
        Claim claim = new Claim(79.0, new Date(2010, 5, 8));

        new ClaimsAdjudication().adjudicate(contract, claim);

        assertEquals(0, contract.getClaims().size());
    }

    @Test
    void adjudicateClaimPriorToEffectiveDate() {
        Contract contract = FakeContract();
        Claim claim = new Claim(79.0, new Date(2010, 5, 7));

        new ClaimsAdjudication().adjudicate(contract, claim);

        assertEquals(0, contract.getClaims().size());
    }
    @Test
    void adjudicateClaimAfterToExpirationDate() {
        Contract contract = FakeContract();
        Claim claim = new Claim(79.0, new Date(2013, 5, 9));

        new ClaimsAdjudication().adjudicate(contract, claim);

        assertEquals(0, contract.getClaims().size());
    }
}