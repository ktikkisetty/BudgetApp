package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfExpenseTest {
    private ListOfExpense l1;
    private ListOfExpense l2;
    private ListOfExpense l3;
    private FixedExpense fix1;
    private FixedExpense fix2;
    private FixedExpense fix3;
    private FixedExpense fix4;
    private FixedExpense fix5;
    private FlexibleExpense flex1;
    private FlexibleExpense flex2;
    private FlexibleExpense flex3;
    private FlexibleExpense flex4;
    private FlexibleExpense flex5;

    @BeforeEach
    public void setup() {
        l1 = new ListOfExpense("a");
        l2 = new ListOfExpense("b");
        l3 = new ListOfExpense("c");

        fix1 = new FixedExpense("Rent");
        fix2 = new FixedExpense("Tuition");
        fix3 = new FixedExpense("Electricity");
        fix4 = new FixedExpense("Heat");
        fix5 = new FixedExpense("Insurance");

        flex1 = new FlexibleExpense("Savings");
        flex2 = new FlexibleExpense("Entertainment");
        flex3 = new FlexibleExpense("Groceries");
        flex4 = new FlexibleExpense("Transportation");
        flex5 = new FlexibleExpense("Clothing");

        l2.addExpense(fix1);

        l3.addExpense(fix1);
        l3.addExpense(fix3);
        l3.addExpense(fix5);
        l3.addExpense(flex3);
        l3.addExpense(flex2);
        l3.addExpense(flex1);
        l3.addExpense(flex5);
    }

    @Test
    public void testConstructor() {
        assertTrue(l1.isEmpty());
    }

    @Test
    public void testAddExpenseFromSetUp() {
        assertEquals(0, l1.size());
        assertEquals(1, l2.size());
        assertEquals(7, l3.size());
    }

    @Test
    public void testAddingToEmptyList() {
        l1.addExpense(flex2);
        assertEquals(1, l1.size());
        assertTrue(l1.contains(flex2));
    }

    @Test
    public void testAddingExpensesToL3() {
        assertTrue(l3.contains(fix1));
        assertTrue(l3.contains(fix3));
        assertTrue(l3.contains(fix5));
        assertTrue(l3.contains(flex3));
        assertTrue(l3.contains(flex2));
        assertTrue(l3.contains(flex1));
        assertTrue(l3.contains(flex5));
    }

    @Test
    public void testAddingExpensesAgainToL3() {
        l3.addExpense(fix2);

        assertTrue(l3.contains(fix1));
        assertTrue(l3.contains(fix2));
        assertTrue(l3.contains(fix3));
        assertTrue(l3.contains(fix5));
        assertTrue(l3.contains(flex3));
        assertTrue(l3.contains(flex2));
        assertTrue(l3.contains(flex1));
        assertTrue(l3.contains(flex5));
        assertEquals(8, l3.size());
    }

    @Test
    public void testAddingExpenseAddingADuplicate() {
        l3.addExpense(fix3);
        l3.addExpense(flex3);

        assertTrue(l3.contains(fix1));
        assertTrue(l3.contains(fix3));
        assertTrue(l3.contains(fix5));
        assertTrue(l3.contains(flex3));
        assertTrue(l3.contains(flex2));
        assertTrue(l3.contains(flex1));
        assertTrue(l3.contains(flex5));
        assertEquals(7, l3.size());
    }

    @Test
    public void testDeleteExpenseOnListWithOneItem() {
        l2.deleteExpenseByTitle("Rent");
        assertEquals(0, l2.size());
        assertFalse(l2.contains(fix1));
    }

    @Test
    public void testDeleteExpenseOnListWithMoreThanOneItem() {
        l3.deleteExpenseByTitle("Entertainment");
        l3.deleteExpenseByTitle("Clothing");
        l3.deleteExpenseByTitle("Insurance");

        assertTrue(l3.contains(fix1));
        assertTrue(l3.contains(fix3));
        assertFalse(l3.contains(fix5));
        assertTrue(l3.contains(flex3));
        assertFalse(l3.contains(flex2));
        assertTrue(l3.contains(flex1));
        assertFalse(l3.contains(flex5));
        assertEquals(4, l3.size());
    }

    @Test
    public void testCalculateTotalCostOfEmptyList() {
        assertEquals(0, l1.calculateTotalCost());
    }

    @Test
    public void testCalculateTotalCostListsWithNoCosts() {
        assertEquals(0, l2.calculateTotalCost());
        assertEquals(0, l3.calculateTotalCost());
    }

    @Test
    public void testCalculateTotalCostOneItem() {
        fix1.setCost(200);
        assertEquals(200, l2.calculateTotalCost());
    }

    @Test
    public void testCalculateTotalCostMultipleItem() {
        fix1.setCost(2000);
        fix3.setCost(200);
        fix5.setCost(55);
        flex3.setCost(700);
        flex2.setCost(300);
        flex1.setCost(50);
        flex5.setCost(300);

        assertEquals(2000 + 200 + 55 + 700 + 300 + 50 + 300, l3.calculateTotalCost());
    }

    @Test
    public void testGetOneItemList() {
        Expense expense = l2.get(0);
        fix1.setCost(200);
        assertEquals(200, expense.getCost());
        assertEquals("Rent", expense.getName());
    }

    @Test
    public void testGetMultipleItemList() {
        Expense expense = l3.get(6);
        flex5.setCost(2000);
        assertEquals(2000, expense.getCost());
        assertEquals("Clothing", expense.getName());
    }

    @Test public void testGetter() {
        assertEquals(fix1, l2.get(0));
        assertEquals(fix1, l3.get(0));
        assertEquals(flex5, l3.get(6));
        assertEquals(flex3, l3.get(3));
    }

    @Test
    public void testSize() {
        assertEquals(0, l1.size());
        assertEquals(1, l2.size());
        assertEquals(7, l3.size());
    }

    @Test
    public void testContains() {
        assertTrue(l3.contains(fix1));
        assertTrue(l3.contains(fix3));
        assertTrue(l3.contains(fix5));
        assertTrue(l3.contains(flex3));
        assertTrue(l3.contains(flex2));
        assertTrue(l3.contains(flex1));
        assertTrue(l3.contains(flex5));
        assertFalse(l3.contains(fix2));
        assertFalse(l3.contains(fix4));
        assertFalse(l3.contains(flex4));

        assertTrue(l2.contains(fix1));
        assertFalse(l2.contains(fix3));
        assertFalse(l2.contains(fix5));
        assertFalse(l2.contains(flex3));
        assertFalse(l2.contains(flex2));
        assertFalse(l2.contains(flex1));
        assertFalse(l2.contains(flex5));
        assertFalse(l2.contains(fix2));
        assertFalse(l2.contains(fix4));
        assertFalse(l2.contains(flex4));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(l1.isEmpty());
        assertFalse(l2.isEmpty());
        assertFalse(l3.isEmpty());
    }
}
