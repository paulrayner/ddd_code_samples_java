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
    public UUID               id = UUID.randomUUID();;
	public double             purchasePrice;
    public Product            coveredProduct;
	public Status             status;
    public TermsAndConditions termsAndConditions;

    public enum Status { PENDING, ACTIVE, EXPIRED }

    private final List<Claim> Claims = new ArrayList<Claim>();

    public Contract(double purchasePrice, Product product, TermsAndConditions termsAndConditions) {
        this.purchasePrice      = purchasePrice;
        this.coveredProduct     = product;
        this.status             = Status.PENDING;
        this.termsAndConditions = termsAndConditions;
    }

    public void add(Claim Claim)
    {
        Claims.add(Claim);
    }

    public List<Claim> getClaims()
    {
        return Claims; 
    }

    public boolean InEffectFor(Date failureDate) {
        return (status == Status.ACTIVE) &&
               (failureDate.compareTo(termsAndConditions.effectiveDate) >= 0) &&
               (failureDate.compareTo(termsAndConditions.expirationDate) <= 0);
    }

    public double LimitOfLiability() {
        final double liability_percentage = 0.8;
        return (purchasePrice * liability_percentage) - ClaimTotal();
    }

    public double ClaimTotal() {
        return this.getClaims().stream().mapToDouble(c -> c.amount).sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return id.equals(contract.id);
    }
}
