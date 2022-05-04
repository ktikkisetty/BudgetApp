package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//This class references code from the JsonSerializationDemo provided in the phase 2 outline
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

class ExpenseTest {
    public FixedExpense e1;
    public FlexibleExpense e2;

    @BeforeEach
    public void setup() {
        e1 = new FixedExpense("expense 1");
        e2 = new FlexibleExpense("expense 2");
    }

    @Test
    public void testMakingAnExpense() {
        assertEquals(0, e1.getCost());
        assertEquals(0, e2.getCost());
        assertEquals("fixed", e1.getCategory());
        assertEquals("flexible", e2.getCategory());
    }

    @Test
    public void testSetCost() {
        e1.setCost(100);
        e2.setCost(1);
        assertEquals(100, e1.getCost());
        assertEquals(1, e2.getCost());
    }

    @Test
    public void testGetCost() {
        assertEquals(0, e1.getCost());
        assertEquals(0, e2.getCost());
    }

    @Test
    public void testGetCategory() {
        assertEquals("fixed", e1.getCategory());
        assertEquals("flexible", e2.getCategory());
    }

    @Test
    public void testGetName() {
        assertEquals("expense 1", e1.getName());
        assertEquals("expense 2", e2.getName());
    }
}