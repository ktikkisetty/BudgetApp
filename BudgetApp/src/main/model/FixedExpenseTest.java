package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FixedExpenseTest {
    private FixedExpense e1;

    @BeforeEach
    public void setup() {
        e1 = new FixedExpense("Expense 1");
        assertEquals("fixed", e1.getCategory());
    }

    @Test
    public void testCalculateCost() {
        e1.calculateCost(22);
        assertEquals(22, e1.getCost());
    }

}
