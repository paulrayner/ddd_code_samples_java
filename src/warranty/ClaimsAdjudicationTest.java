package warranty;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClaimsAdjudicationTest {

    Contract FakeContract()
    {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract = new Contract(100.0, product, termsAndConditions);
        contract.status = Contract.Status.ACTIVE;

        return contract;
    }

    @Test
    void AdjudicateValidClaim() {
        Contract contract = FakeContract();
        Claim claim = new Claim(79.0, new Date(2010, 5, 8));

        new ClaimsAdjudication().Adjudicate(contract, claim);

        assertEquals(1, contract.getClaims().size());
        assertEquals(79.0, contract.getClaims().get(0).amount);
        assertEquals(new Date(2010, 5, 8), contract.getClaims().get(0).failureDate);
    }

    @Test
    void AdjudicateClaimWithInvalidAmount() {
        Contract contract = FakeContract();
        Claim claim = new Claim(81.0, new Date(2010, 5, 8));

        new ClaimsAdjudication().Adjudicate(contract, claim);

        assertEquals(0, contract.getClaims().size());
    }

    @Test
    void AdjudicateClaimForPendingContract() {
        Contract contract = FakeContract();
        contract.status = Contract.Status.PENDING;
        Claim claim = new Claim(79.0, new Date(2010, 5, 8));

        new ClaimsAdjudication().Adjudicate(contract, claim);

        assertEquals(0, contract.getClaims().size());
    }

    @Test
    void AdjudicateClaimForExpiredContract() {
        Contract contract = FakeContract();
        contract.status = Contract.Status.EXPIRED;
        Claim claim = new Claim(79.0, new Date(2010, 5, 8));

        new ClaimsAdjudication().Adjudicate(contract, claim);

        assertEquals(0, contract.getClaims().size());
    }

    @Test
    void AdjudicateClaimPriorToEffectiveDate() {
        Contract contract = FakeContract();
        Claim claim = new Claim(79.0, new Date(2010, 5, 7));

        new ClaimsAdjudication().Adjudicate(contract, claim);

        assertEquals(0, contract.getClaims().size());
    }
    @Test
    void AdjudicateClaimAfterToExpirationDate() {
        Contract contract = FakeContract();
        Claim claim = new Claim(79.0, new Date(2013, 5, 9));

        new ClaimsAdjudication().Adjudicate(contract, claim);

        assertEquals(0, contract.getClaims().size());
    }
}