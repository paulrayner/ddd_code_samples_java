package warranty;

import java.util.Date;
import java.util.Objects;

public class TermsAndConditions {
    public final Date purchaseDate;
    public final Date effectiveDate;
    public final Date expirationDate;

    public TermsAndConditions(Date purchaseDate, Date effectiveDate, Date expirationDate) {
        this.purchaseDate = purchaseDate;
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
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
