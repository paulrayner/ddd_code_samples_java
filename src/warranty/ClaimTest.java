package warranty;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClaimTest {

    @Test
    public void TestClaimIsSetupCorrectly()
    {
        LineItem lineItem1 = new LineItem("PARTS", 45.0, "Replacement part for soap dispenser");
        LineItem lineItem2 = new LineItem("LABOR", 50.0, "1 hour repair");
        RepairPO repairPO  = new RepairPO();
        repairPO.lineItems.add(lineItem1);
        repairPO.lineItems.add(lineItem2);

        Claim claim = new Claim(100.0, new Date(2010, 5, 8));
        claim.repairPO.add(repairPO);

        assertEquals(100.0, claim.amount);
        assertEquals(new Date(2010, 5, 8), claim.failureDate);
        assertEquals("PARTS", claim.repairPO.get(0).lineItems.get(0).type);
        assertEquals(45.0, claim.repairPO.get(0).lineItems.get(0).amount);
        assertEquals("Replacement part for soap dispenser", claim.repairPO.get(0).lineItems.get(0).description);
        assertEquals("LABOR", claim.repairPO.get(0).lineItems.get(1).type);
        assertEquals(50.0, claim.repairPO.get(0).lineItems.get(1).amount);
        assertEquals("1 hour repair", claim.repairPO.get(0).lineItems.get(1).description);
    }

    // Entities compare by unique IDs, not properties
    @Test
    public void TestClaimEquality()
    {
    }

}