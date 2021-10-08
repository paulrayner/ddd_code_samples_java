package warranty;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    // Product is an example of a value object. See https://martinfowler.com/bliki/ValueObject.html for more details
    @Test
    public void TestProductEquality() {
        // A value object must be created whole
        Product product = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");

        // Demonstrate equality by property - uses custom "equals" method in this example
        assertEquals(new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0"), product);
    }
    // Product is an example of a value object. See https://martinfowler.com/bliki/ValueObject.html for more details
    @Test
    public void TestProductInequality() {
        Product product = new Product("dishwasher", "OEUOEU23", "Whirlpool", "7DP840CWDB0");

        // Demonstrate equality by property - uses custom "equals" method in this example
        assertNotEquals(new Product("stove", "OEUOEU23", "Whirlpool", "7DP840CWDB0"), product);
        assertNotEquals(new Product("dishwasher", "BEUOEU23", "Whirlpool", "7DP840CWDB0"), product);
        assertNotEquals(new Product("dishwasher", "OEUOEU23", "Maytag", "7DP840CWDB0"), product);
        assertNotEquals(new Product("dishwasher", "OEUOEU23", "Whirlpool", "9999999"), product);
    }
}
