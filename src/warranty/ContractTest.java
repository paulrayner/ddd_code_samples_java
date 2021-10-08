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
        Contract contract = new Contract(100.0, product, new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        assertNotNull(contract.id);
        assertEquals(100.0, contract.purchasePrice);
        assertEquals(Contract.Status.PENDING, contract.status);
        assertEquals(contract.purchaseDate, new Date(2010, 5, 7));
        assertEquals(contract.effectiveDate, new Date(2010, 5, 8));
        assertEquals(contract.expirationDate, new Date(2013, 5, 8));
        assertEquals(product, contract.coveredProduct);
    }

    // Entities compare by unique IDs, not properties
    @Test
    public void TestContractEquality()
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