package warranty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Claim represents a request for a benefit on an extended warranty. It contains a
 * set of purchase orders that provide information about any repairs and associated costs that may have occurred for a claim.
 */

public class Claim {
	public UUID           id;
	public double         amount;
	public Date           failureDate;

	public List<RepairPO> repairPO = new ArrayList<RepairPO>();

	public Claim(double amount, Date failureDate) {
		this.id          = UUID.randomUUID();
		this.amount      = amount;
		this.failureDate = failureDate;
	}
}
