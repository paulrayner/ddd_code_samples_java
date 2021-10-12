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

	public void Adjudicate(Contract contract, Claim newClaim) {
		if ((contract.LimitOfLiability() > newClaim.amount) &&
			 InEffectFor(contract, newClaim.failureDate)) {
			contract.add(newClaim);
		}
	}

	public boolean InEffectFor(Contract contract, Date failureDate) {
		return  (contract.status == Status.ACTIVE) &&
				(failureDate.compareTo(contract.effectiveDate) >= 0) &&
				(failureDate.compareTo(contract.expirationDate) <= 0);
	}
}
