package warranty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Contract represents an extended warranty for a covered product.
 * A contract is in a PENDING state prior to the effective date,
 * ACTIVE between effective and expiration dates, and EXPIRED after
 * the expiration date.
 */

public class Contract {
	public UUID    id = UUID.randomUUID();;
	public double  purchasePrice;
    public Product coveredProduct;
    public Date    purchaseDate;
	public Date    effectiveDate;
	public Date    expirationDate;
	public Status  status;

    public enum Status { PENDING, ACTIVE, EXPIRED }

    private final List<Claim> Claims = new ArrayList<Claim>();

    public Contract(double purchasePrice, Product product, Date purchaseDate, Date effectiveDate, Date expirationDate) {
        this.purchasePrice   = purchasePrice;
        this.coveredProduct  = product;
        this.status          = Status.PENDING;
        this.purchaseDate    = purchaseDate;
        this.effectiveDate   = effectiveDate;
        this.expirationDate  = expirationDate;
    }

    public void add(Claim Claim)
    {
        Claims.add(Claim);
    }

    public List<Claim> getClaims()
    {
        return Claims; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return id.equals(contract.id);
    }
}
