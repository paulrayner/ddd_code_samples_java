package warranty;

import warranty.Contract.Status;

/**
 * Adjudicate/adjudication - a judgment made on a claim to determine whether
 * we are legally obligated to process the claim against the warranty. From
 * Wikipedia (https://en.wikipedia.org/wiki/Adjudication):
 * "Claims adjudication" is a phrase used in the insurance industry to refer to
 * the process of paying claims submitted or denying them after comparing claims
 * to the benefit or coverage requirements.
 */

public class ClaimsAdjudication {

	public void adjudicate(Contract contract, Claim newClaim) {
		double claimTotal = contract.getClaims().stream().mapToDouble(c -> c.amount).sum();
		if (((contract.purchasePrice - claimTotal) * 0.8 > newClaim.amount) &&
				(contract.status == Status.ACTIVE) &&
				(newClaim.failureDate.compareTo(contract.effectiveDate) >= 0) &&
				(newClaim.failureDate.compareTo(contract.expirationDate) <= 0)) {
			contract.add(newClaim);
		}
	}
}
