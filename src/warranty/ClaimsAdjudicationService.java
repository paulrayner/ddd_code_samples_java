package warranty;

import warranty.Contract.Status;

public class ClaimsAdjudicationService {

	public void Adjudicate(Contract contract, Claim newClaim) {
		
		if ((newClaim.amount < contract.limitOfLiability() &&
			 contract.status(newClaim.date) == Status.ACTIVE)) {
			
			contract.add(newClaim);
		}
	}
}
