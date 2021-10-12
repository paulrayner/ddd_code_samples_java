package warranty;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TermsAndConditionsTest {

    // TermsAndConditions is an example of a value object. See https://martinfowler.com/bliki/ValueObject.html for more details
    @Test
    public void TestTermsAndConditionsEquality() {
        // A value object must be created whole
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        // Demonstrate equality by property - uses custom "equals" method in this example
        assertEquals(new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8)), termsAndConditions);
    }

    // TermsAndConditions is an example of a value object. See https://martinfowler.com/bliki/ValueObject.html for more details
    @Test
    public void TestTermsAndConditionsInequality() {
        TermsAndConditions termsAndConditions = new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 8));

        // Demonstrate equality by property - uses custom "equals" method in this example
        assertNotEquals(new TermsAndConditions(new Date(2010, 5, 6), new Date(2010, 5, 8), new Date(2013, 5, 8)), termsAndConditions);
        assertNotEquals(new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 5), new Date(2013, 5, 8)), termsAndConditions);
        assertNotEquals(new TermsAndConditions(new Date(2010, 5, 7), new Date(2010, 5, 8), new Date(2013, 5, 4)), termsAndConditions);
    }
}
