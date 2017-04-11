package warranty;

import warranty.util.ImmutableDate;

public class Claim {

	public Claim(int id, double amount, ImmutableDate date) {
		super();
		this.amount = amount;
		this.date = date;
	}
	public int id;
	public double amount;
	public ImmutableDate date;
	public ProductReplacementEvent productReplacement;
	public CustomerReimbursementEvent customerReimbursement;
}
