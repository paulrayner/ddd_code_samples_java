package warranty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Contract {

	private final int id;
	public final double purchasePrice;
	private TermsAndConditions termsAndConditions;
	private List<Claim> claims;

	public Contract(int id, double purchasePrice,
			TermsAndConditions termsAndConditions) {
		this.id = id;
		this.purchasePrice = purchasePrice;
		this.termsAndConditions = termsAndConditions;
		this.claims = new ArrayList<Claim>();
	}

	public void add(Claim newClaim) {
		if (newClaim.amount < limitOfLiability()
				&& this.termsAndConditions.isActive(newClaim.date)) {
			claims.add(newClaim);
		} else {
			throw new ContractException(
					"Contract is not active or amount is less than LOL");
		}
	}

	public List<Claim> getClaims() {
		return Collections.unmodifiableList(claims);
	}

	public void remove(Claim Claim) {
		claims.remove(Claim);
	}

	public void extendAnnualSubscription() {
		this.termsAndConditions = this.termsAndConditions.annuallyExtended();
	}

	public TermsAndConditions termsAndConditions() {
		return termsAndConditions;
	}

	public double limitOfLiability() {
		double claimTotal = 0;

		for (Claim claim : getClaims()) {
			claimTotal += claim.amount;
		}

		return (this.purchasePrice - claimTotal) * 0.8;
	}

}
