package warranty;
import java.util.Date;

public class ProductReplacementEvent {
	public final Date replacementDate;
	public final String reason;
	
	public ProductReplacementEvent(Date replacementDate, String reason) {
		super();
		this.replacementDate = replacementDate;
		this.reason = reason;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result
				+ ((replacementDate == null) ? 0 : replacementDate.hashCode());
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
		ProductReplacementEvent other = (ProductReplacementEvent) obj;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (replacementDate == null) {
			if (other.replacementDate != null)
				return false;
		} else if (!replacementDate.equals(other.replacementDate))
			return false;
		return true;
	}
}
