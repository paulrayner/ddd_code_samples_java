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
    public UUID                       id = UUID.randomUUID();;
	public double                     purchasePrice;
    public Product                    coveredProduct;
	public Status                     status;
    public TermsAndConditions         termsAndConditions;
    public ArrayList                  events;

    public enum Status { PENDING, ACTIVE, EXPIRED, FULFILLED }

    private final List<Claim> Claims = new ArrayList<Claim>();

    public Contract(double purchasePrice, Product product, TermsAndConditions termsAndConditions) {
        this.purchasePrice      = purchasePrice;
        this.coveredProduct     = product;
        this.status             = Status.PENDING;
        this.termsAndConditions = termsAndConditions;
        this.events             = new ArrayList();
    }

    public void add(Claim Claim)
    {
        Claims.add(Claim);
    }

    public List<Claim> getClaims()
    {
        return Claims; 
    }

    public boolean covers(Claim claim) {
        return  inEffectFor(claim.failureDate) &&
                withinLimitOfLiability(claim.amount);
    }

    public boolean inEffectFor(Date failureDate) {
        return termsAndConditions.status(failureDate) == Status.ACTIVE &&
               status == Status.ACTIVE;
    }

    public boolean withinLimitOfLiability(double claimAmount) {
        return claimAmount < remainingLiability();
    }

    private double remainingLiability() {
        return termsAndConditions.limitOfLiability(purchasePrice) - claimTotal();
    }

    public double claimTotal() {
        return this.getClaims().stream().mapToDouble(c -> c.amount).sum();
    }

    public void extendAnnualSubscription() {
        termsAndConditions = termsAndConditions.annuallyExtended();
        events.add(new SubscriptionRenewed(id, "Automatic Annual Renewal"));
    }

    public void terminate(String rep_name, String reason) {
        this.status = Status.FULFILLED;
        this.events.add(new CustomerReimbursementRequested(id, rep_name, reason));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return id.equals(contract.id);
    }
}
