package warranty;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import warranty.Claim;

public class Contract {

	public Contract(int id, double purchasePrice, TermsAndConditions termsAndConditions) {
		this.id = id;
		this.purchasePrice = purchasePrice;
		this.termsAndConditions = termsAndConditions;
	}

	public final int id;
	public double purchasePrice;
	public TermsAndConditions termsAndConditions;

	public Status status(Date date)
	{
		if (termsAndConditions.isActive(date))
			{
				return Status.ACTIVE;
			}
		if (termsAndConditions.isPending(date))
		{
			return Status.PENDING;
		}
		if (termsAndConditions.isExpired(date))
		{
			return Status.EXPIRED;
		}
		return Status.INVALID;
	}
	
    public enum Status { PENDING, ACTIVE, EXPIRED, INVALID }
	
    private List<Claim> Claims = new ArrayList<Claim>();

    public void add(Claim Claim)
    {
        Claims.add(Claim);
    }

    public List<Claim> getClaims()
    {
        return Claims; 
    }

    public void remove(Claim Claim)
    {
        Claims.remove(Claim);
    }
    
    public void extendAnnualSubscription() throws ParseException
    {
    		this.termsAndConditions = this.termsAndConditions.annuallyExtended();
    }
    
    public double limitOfLiability()
    {
		double claimTotal = 0;
		List<Claim> claims = new ArrayList<Claim>();
		claims.addAll(this.getClaims());
		
		for (Claim claim:claims)
		{
			claimTotal += claim.amount;
		}
		
		return (this.purchasePrice - claimTotal) * 0.8;
    }
}
