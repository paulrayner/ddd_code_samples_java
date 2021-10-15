package warranty;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TermsAndConditionsTest {

    @Test
    public void termsAndConditionsStatus() {
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        // Should be pending prior to effective date
        assertEquals(Contract.Status.PENDING, termsAndConditions.status(new Date(2010, 5, 7)));
        // Should be active if between effective and expiration dates (inclusively)
        assertEquals(Contract.Status.ACTIVE, termsAndConditions.status(new Date(2010, 5, 8)));
        assertEquals(Contract.Status.ACTIVE, termsAndConditions.status(new Date(2013, 5, 8)));
        // Should be expired after to expiration date
        assertEquals(Contract.Status.EXPIRED, termsAndConditions.status(new Date(2013, 5, 9)));
    }

    // Moved from Contract
    @Test
    public void termsAndConditionsLimitOfLiability() {
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));
        assertEquals(80.0, termsAndConditions.limitOfLiability(100.0));
    }

    @Test
    public void termsAndConditionsExtendAnnually() {
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        TermsAndConditions extendedTermsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2014, 5, 8));

        assertEquals(extendedTermsAndConditions, termsAndConditions.annuallyExtended());
    }

// Could write tests to prevent invalid states
//    @Test
//    public void TestTermsAndConditionsRejectsInvalidDates() {
//        assertThrows(Exception.class, () -> {
//            new TermsAndConditions(new Date(2010, 5, 9), new Date(2010, 5, 8), new Date(2013, 5, 8));
//        });
//    }

    // TermsAndConditions is an example of a value object. See https://martinfowler.com/bliki/ValueObject.html for more details
    @Test
    public void termsAndConditionsEquality() {
        // A value object must be created whole
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        // Demonstrate equality by property - uses custom "equals" method in this example
        assertEquals(new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8)), termsAndConditions);
    }

    // TermsAndConditions is an example of a value object. See https://martinfowler.com/bliki/ValueObject.html for more details
    @Test
    public void termsAndConditionsInequality() {
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        // Demonstrate equality by property - uses custom "equals" method in this example
        assertNotEquals(new TermsAndConditions(new Date(2010, 5, 6), new Date(2010, 5, 8), new Date(2013, 5, 8)), termsAndConditions);
        assertNotEquals(new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 5), new Date(2013, 5, 8)), termsAndConditions);
        assertNotEquals(new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 4)), termsAndConditions);
    }
}
