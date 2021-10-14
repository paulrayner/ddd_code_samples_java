package warranty;

import java.util.Date;
import java.util.Objects;

/**
 * Terms and Conditions represents the legal obligations for the contract, such as the coverage period
 * based on the effective and expiration dates, the limit of liability, and how to handle extensions on
 * subscription-based warranties.
 */
public class TermsAndConditions {
    public final Date purchaseDate;
    public final Date effectiveDate;
    public final Date expirationDate;

    public TermsAndConditions(Date purchaseDate, Date effectiveDate, Date expirationDate) {
        this.purchaseDate = purchaseDate;
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
    }

    public Contract.Status Status(Date date) {
        if (date.compareTo(effectiveDate) < 0) return Contract.Status.PENDING;
        if (date.compareTo(expirationDate) > 0) return Contract.Status.EXPIRED;
        return Contract.Status.ACTIVE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TermsAndConditions that = (TermsAndConditions) o;
        return purchaseDate.equals(that.purchaseDate) && effectiveDate.equals(that.effectiveDate) && expirationDate.equals(that.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseDate, effectiveDate, expirationDate);
    }
}
