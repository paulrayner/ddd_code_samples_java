package warranty;
import java.util.Date;

public class CustomerReimbursementEvent {
	public final Date dateOfReimbursement;
	public final String reason;
	public final double amount;
	
	public CustomerReimbursementEvent(Date dateOfReimbursement, String reason,
			double amount) {
		super();
		this.dateOfReimbursement = dateOfReimbursement;
		this.reason = reason;
		this.amount = amount;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime
				* result
				+ ((dateOfReimbursement == null) ? 0 : dateOfReimbursement
						.hashCode());
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerReimbursementEvent other = (CustomerReimbursementEvent) obj;
		if (Double.doubleToLongBits(amount) != Double
				.doubleToLongBits(other.amount))
			return false;
		if (dateOfReimbursement == null) {
			if (other.dateOfReimbursement != null)
				return false;
		} else if (!dateOfReimbursement.equals(other.dateOfReimbursement))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		return true;
	}
}
