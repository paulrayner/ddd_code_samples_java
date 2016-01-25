package warranty;

import java.util.Date;

public class Claim {

	public Claim(int id, double amount, Date date) {
		super();
		this.amount = amount;
		this.date = date;
	}
	public int id;
	public double amount;
	public Date date = new Date();
	public ProductReplacementEvent productReplacement;
	public CustomerReimbursementEvent customerReimbursement;
}
