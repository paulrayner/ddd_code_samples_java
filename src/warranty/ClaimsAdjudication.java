package warranty;

import warranty.Contract.Status;

import java.util.Date;

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
		if ((limitOfLiability(contract) > newClaim.amount) &&
			 inEffectFor(contract, newClaim.failureDate)) {
			contract.add(newClaim);
		}
	}

	// These two new methods we've added seem to be responsibilities of Contract. Let's move them...
	public double limitOfLiability(Contract contract) {
		double claimTotal = contract.getClaims().stream().mapToDouble(c -> c.amount).sum();
		return (contract.purchasePrice - claimTotal) * 0.8;
	}

	public boolean inEffectFor(Contract contract, Date failureDate) {
		return  (contract.status == Status.ACTIVE) &&
				(failureDate.compareTo(contract.effectiveDate) >= 0) &&
				(failureDate.compareTo(contract.expirationDate) <= 0);
	}
}
