package warranty;

import java.util.Calendar;

import warranty.util.ImmutableDate;

public class TermsAndConditions {

	public final ImmutableDate effectiveDate;
	public final ImmutableDate expirationDate;
	public final ImmutableDate purchaseDate;
	public final int inStoreGuaranteeDays;

	public TermsAndConditions(ImmutableDate effectiveDate,
			ImmutableDate expirationDate, ImmutableDate purchaseDate,
			int inStoreGuaranteeDays) {
		super();
		this.effectiveDate = effectiveDate;
		this.expirationDate = expirationDate;
		this.purchaseDate = purchaseDate;
		this.inStoreGuaranteeDays = inStoreGuaranteeDays;
	}

	public boolean isPending(ImmutableDate date) {
		return (date.compareTo(effectiveDate) < 0);
	}

	public boolean isExpired(ImmutableDate date) {
		return (date.compareTo(expirationDate) > 0);
	}

	public boolean isActive(ImmutableDate date) {
		return (date.compareTo(effectiveDate) >= 0)
				&& (date.compareTo(expirationDate) <= 0);
	}

	public TermsAndConditions annuallyExtended() {
		return new TermsAndConditions(this.effectiveDate, expirationDate.add(
				Calendar.YEAR, 1), this.purchaseDate, this.inStoreGuaranteeDays);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
		result = prime * result
				+ ((expirationDate == null) ? 0 : expirationDate.hashCode());
		result = prime * result + inStoreGuaranteeDays;
		result = prime * result
				+ ((purchaseDate == null) ? 0 : purchaseDate.hashCode());
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
		TermsAndConditions other = (TermsAndConditions) obj;
		if (effectiveDate == null) {
			if (other.effectiveDate != null)
				return false;
		} else if (!effectiveDate.equals(other.effectiveDate))
			return false;
		if (expirationDate == null) {
			if (other.expirationDate != null)
				return false;
		} else if (!expirationDate.equals(other.expirationDate))
			return false;
		if (inStoreGuaranteeDays != other.inStoreGuaranteeDays)
			return false;
		if (purchaseDate == null) {
			if (other.purchaseDate != null)
				return false;
		} else if (!purchaseDate.equals(other.purchaseDate))
			return false;
		return true;
	}

}
