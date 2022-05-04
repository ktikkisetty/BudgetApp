package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//This class references code from the JsonSerializationDemo provided in the phase 2 outline
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class JsonWriterTest extends JsonTest {
    Expense e1;
    Expense e2;
    Expense e3;

    @BeforeEach
    void setup() {
        e1 = new FixedExpense("rent");
        e1.setCost(1000);
        e2 = new FixedExpense("cell");
        e2.setCost(100);
        e3 = new FlexibleExpense("savings");
        e3.setCost(200);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            ListOfExpense list = new ListOfExpense("this list");
            Budget budget = new Budget(0,list);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBudget() {
        try {
            ListOfExpense list = new ListOfExpense("My empty budget");
            Budget budget = new Budget(0, list);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBudgetList.json");
            writer.open();
            writer.write(budget);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBudgetList.json");
            budget = reader.read();
            assertEquals("My empty budget", list.getName());
            assertEquals(0, list.size());
            assertEquals(0, budget.getIncome());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralBudget() {
        try {
            ListOfExpense list = new ListOfExpense("My general budget");
            Budget budget = new Budget(2000, list);
            list.addExpense(e1);
            list.addExpense(e2);
            list.addExpense(e3);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBudgetList.json");
            writer.open();
            writer.write(budget);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBudgetList.json");
            Budget budgetRead = reader.read();
            ListOfExpense listOfRead = budgetRead.getList();
            assertEquals(2000, budgetRead.getIncome());
            assertEquals("My general budget", listOfRead.getName());
            assertEquals(3, listOfRead.size());
            checkExpense("rent", 1000, listOfRead.get(0), "fixed");
            checkExpense("cell", 100, listOfRead.get(1), "fixed");
            checkExpense("savings", 200, listOfRead.get(2), "flexible");
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
