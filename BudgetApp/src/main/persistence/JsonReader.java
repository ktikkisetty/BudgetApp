package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

//This class references code from the JsonSerializationDemo provided in the phase 2 outline
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ListOfExpense from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Budget read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBudget(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses list of expenses from JSON object and returns it
    private Budget parseBudget(JSONObject jsonObject) {
        String name = jsonObject.getString("budget name");
        Double income = jsonObject.getDouble("income");
        ListOfExpense list = new ListOfExpense(name);
        Budget budget = new Budget(income, list);
        addExpenses(list, jsonObject);
        return budget;
    }

    // MODIFIES: list
    // EFFECTS: parses expenses from JSON object and adds them to the list of expenses
    private void addExpenses(ListOfExpense list, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("budget");
        for (Object json : jsonArray) {
            JSONObject nextExpense = (JSONObject) json;
            addExpense(list, nextExpense);
        }
    }

    // MODIFIES: list
    // EFFECTS: parses expense from JSON object and adds it to the list of expenses
    private void addExpense(ListOfExpense list, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double cost = jsonObject.getDouble("cost");
        String category = jsonObject.getString("category");
        Expense expense;
        if (category.equals("fixed")) {
            expense = new FixedExpense(name);
        } else {
            expense = new FlexibleExpense(name);
        }
        expense.setCost(cost);
        list.addExpense(expense);
    }
}
