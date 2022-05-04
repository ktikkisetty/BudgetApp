package persistence;

import model.Budget;
import model.ListOfExpense;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

//This class references code from the JsonSerializationDemo provided in the phase 2 outline
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Budget budget = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBudget() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBudgetList.json");
        try {
            Budget budget = reader.read();
            ListOfExpense list = budget.getList();
            assertEquals("My empty budget", list.getName());
            assertEquals(0, budget.getIncome());
            assertTrue(list.isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralBudget() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBudgetList.json");
        try {
            Budget budget = reader.read();
            ListOfExpense list = budget.getList();
            assertEquals(3, list.size());
            checkExpense("rent", 1000, list.get(0), "fixed");
            checkExpense("cell", 100, list.get(1), "fixed");
            checkExpense("savings", 200, list.get(2), "flexible");
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
