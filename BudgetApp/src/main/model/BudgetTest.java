package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetTest {
    private Budget b1;
    private Budget b2;
    private ListOfExpense l1;
    private ListOfExpense l2;
    private ListOfExpense l3;

    @BeforeEach
    public void setup() {
        l1 = new ListOfExpense("a");
        l2 = new ListOfExpense("b");
        l3 = new ListOfExpense("c");
        b1 = new Budget(0, l1);
        b2 = new Budget(1000, l2);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, b1.getIncome());
        assertEquals(1000, b2.getIncome());
        assertEquals(l1, b1.getList());
        assertEquals(l2, b2.getList());
    }

    @Test
    public void testCostVersusIncomeWithNoIncomeAndNoCosts() {
        boolean result = b1.totalCostVersusIncome(0);
        assertTrue(result);
    }

    @Test
    public void testCostVersusIncomeWithNoIncomeAndCosts() {
        boolean result = b1.totalCostVersusIncome(1);
        assertFalse(result);
    }

    @Test
    public void testCostVersusIncomeWithIncomeAndNoCosts() {
        boolean result = b2.totalCostVersusIncome(0);
        assertTrue(result);
    }

    @Test
    public void testCostVersusIncomeWithIncomeAndManageableCosts() {
        boolean result = b2.totalCostVersusIncome(800);
        assertTrue(result);
    }

    @Test
    public void testCostVersusIncomeWithIncomeAndCostsEqualsIncome() {
        boolean result = b2.totalCostVersusIncome(1000);
        assertTrue(result);
    }

    @Test
    public void testCostVersusIncomeWithIncomeAndCostsMoreThanIncome() {
        boolean result = b2.totalCostVersusIncome(1001);
        assertFalse(result);
    }

    @Test
    public void testCostVersusIncomeWithIncomeAndCostsWayMoreThanIncome() {
        boolean result = b2.totalCostVersusIncome(10010);
        assertFalse(result);
    }

    @Test
    public void testGetIncome() {
        assertEquals(0, b1.getIncome());
        assertEquals(1000, b2.getIncome());
    }
}
