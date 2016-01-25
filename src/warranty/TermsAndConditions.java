package warranty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TermsAndConditions {

	public TermsAndConditions(Date effectiveDate, Date expirationDate,
			Date purchaseDate, int inStoreGuaranteeDays) {
		super();
		this.effectiveDate = effectiveDate;
		this.expirationDate = expirationDate;
		this.purchaseDate = purchaseDate;
		this.inStoreGuaranteeDays = inStoreGuaranteeDays;
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
				+ ((purchaseDate() == null) ? 0 : purchaseDate().hashCode());
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
		if (purchaseDate() == null) {
			if (other.purchaseDate() != null)
				return false;
		} else if (!purchaseDate().equals(other.purchaseDate()))
			return false;
		return true;
	}
	
	private Date effectiveDate;
	private Date expirationDate;
	private Date purchaseDate;
	private int inStoreGuaranteeDays;
	
	public int inStoreGuaranteeDays() {
		return inStoreGuaranteeDays;
	}
	public Date purchaseDate() {
		return purchaseDate;
	}
	public Date effectiveDate() {
		return effectiveDate;
	}
	public Date ExpirationDate() {
		return expirationDate;
	}

	public boolean isPending(Date date)
	{
		return  (date.compareTo(effectiveDate) < 0);
	}

	public boolean isExpired(Date date)
	{
		return  (date.compareTo(expirationDate) > 0);
	}
	
	public boolean isActive(Date date)
	{
		return  (date.compareTo(effectiveDate) >= 0) &&
				(date.compareTo(expirationDate) <= 0);
	}
	
	public TermsAndConditions annuallyExtended() throws ParseException
	{
		return new TermsAndConditions(this.effectiveDate, 
									  increaseDateByOneYear(expirationDate), 
									  this.purchaseDate, 
									  this.inStoreGuaranteeDays);
	}
	
	private Date increaseDateByOneYear(Date date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		Calendar cal = Calendar.getInstance();
		cal.setTime(expirationDate);
		cal.add(Calendar.YEAR, 1);	// Add 1 year to current date
		String newDate = dateFormat.format(cal.getTime());
		return dateFormat.parse(newDate);
	}
}
