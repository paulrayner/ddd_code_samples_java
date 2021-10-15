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
        Contract contract = new Contract(100.0, product, new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        assertNotNull(contract.id);
        assertEquals(100.0, contract.purchasePrice);
        assertEquals(Contract.Status.PENDING, contract.status);
        assertEquals(contract.purchaseDate, new Date(2010, 5, 7));
        assertEquals(contract.effectiveDate, new Date(2010, 5, 8));
        assertEquals(contract.expirationDate, new Date(2013, 5, 8));
        assertEquals(product, contract.coveredProduct);
    }

    @Test
    public void contractInEffectBasedOnStatusAndEffectiveAndExpirationDateRange() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        Contract contract = new Contract(100.0, product, new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        // check PENDING (default) state
        assertFalse(contract.inEffectFor(new Date(2010, 5, 9)));
        // check ACTIVE state
        contract.status = Contract.Status.ACTIVE;
        assertFalse(contract.inEffectFor(new Date(2010, 5, 7)));
        assertTrue(contract.inEffectFor(new Date(2010, 5, 8)));
        assertTrue(contract.inEffectFor(new Date(2013, 5, 7)));
        assertFalse(contract.inEffectFor(new Date(2013, 5, 9)));
    }

    @Test
    public void limitOfLiabilityWithNoClaims() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        Contract contract = new Contract(100.0, product, new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        assertNotNull(contract.id);

        assertEquals(80.0, contract.limitOfLiability());
    }

    @Test
    public void limitOfLiabilityWithOneClaim() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        Contract contract = new Contract(100.0, product, new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        assertNotNull(contract.id);
        contract.add(new Claim(10.0, new Date(2010, 10, 1)));

        assertEquals(70.0, contract.limitOfLiability());
    }

    @Test
    public void limitOfLiabilityWithMultipleClaims() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        Contract contract = new Contract(100.0, product, new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        assertNotNull(contract.id);
        contract.add(new Claim(10.0, new Date(2010, 10, 1)));
        contract.add(new Claim(20.0, new Date(2010, 10, 1)));

        assertEquals(50.0, contract.limitOfLiability());
    }

    @Test
    public void claimTotalSumOfClaimAmounts() {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        Contract contract = new Contract(100.0, product, new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        assertNotNull(contract.id);
        contract.add(new Claim(10.0, new Date(2010, 10, 1)));
        contract.add(new Claim(20.0, new Date(2010, 10, 1)));

        assertEquals(30.0, contract.claimTotal());
    }

    // Entities compare by unique IDs, not properties
    @Test
    public void contractEquality()
    {
        Product product  = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");
        Contract contract1 = new Contract(100.0, product, new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract2 = new Contract(100.0, product, new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        Contract contract3 = new Contract(100.0, product, new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        UUID expectedId = UUID.randomUUID();
        contract1.id = expectedId;
        contract2.id = expectedId;

        assertEquals(contract2, contract1);
        assertNotEquals(contract3, contract1);
    }
}