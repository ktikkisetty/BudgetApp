package persistence;

import model.Expense;

import static org.junit.jupiter.api.Assertions.assertEquals;

//This class references code from the JsonSerializationDemo provided in the phase 2 outline
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonTest {
    protected void checkExpense(String name, double cost, Expense expense, String category) {
        assertEquals(name, expense.getName());
        assertEquals(cost, expense.getCost());
        assertEquals(category, expense.getCategory());
    }
}
