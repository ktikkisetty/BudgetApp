package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlexibleExpenseTest {
    private FlexibleExpense e1;

    @BeforeEach
    public void setup() {
        e1 = new FlexibleExpense("Expense 2");
        assertEquals("flexible", e1.getCategory());
    }

    @Test
    public void testCalculateCost() {
        e1.calculateCost(0.2, 5000);
        assertEquals(e1.getCost(), 1000);
    }
}
