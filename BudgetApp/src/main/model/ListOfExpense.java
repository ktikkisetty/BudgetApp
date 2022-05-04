package model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

//This class references code from the JsonSerializationDemo provided in the phase 2 outline
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

//This class references code from AlarmSystem from CPSC 210
//Link: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

public class ListOfExpense {
    private List<Expense> allExpenses;
    private String name;
    //The ListOfExpense class represents all the expenses the user has in one month

    public ListOfExpense(String name) {
        allExpenses = new ArrayList<>();
        this.name = name;
    }

    //MODIFIES: this
    //EFFECTS: inserts an Expense into the list of expenses as long as it is not the same expense (i.e., same
    // product/service and the same price)
    public void addExpense(Expense expense) {
        boolean duplicate = contains(expense);
        if (duplicate == false) {
            allExpenses.add(expense);
            EventLog.getInstance().logEvent(new Event(expense.getCategory() + " expense added to list"));
        }
    }

    //REQUIRES: The list must already contain an expense with the given name
    //MODIFIES: this
    //EFFECTS: removes the given item from the list
    public void deleteExpenseByTitle(String name) {
        allExpenses.removeIf(next -> next.getName().equals(name));
        EventLog.getInstance().logEvent(new Event("An expense removed from list"));
    }

    //EFFECTS: Returns the total cost of all expenses in the list
    public double calculateTotalCost() {
        double total = 0;
        if (allExpenses.size() == 0) {
            return 0;
        } else {
            for (Expense next: allExpenses) {
                total = total + next.getCost();
            }
            return total;
        }
    }

    //TODO: Tests and specification

    //EFFECTS: returns the number of expenses in the list
    public int size() {
        return allExpenses.size();
    }

    //EFFECTS: returns true if there are no expenses; false otherwise
    public boolean isEmpty() {
        return allExpenses.isEmpty();
    }

    //EFFECTS: return true if expense is in the list; false otherwise
    public boolean contains(Expense e) {
        return allExpenses.contains(e);
    }

    //REQUIRES: A list with at least one element and integer 'i' is must be less than the size of the list
    //EFFECTS: returns the expense with the index
    public Expense get(int i) {
        return allExpenses.get(i);
    }

    //TODO: Finish specification and update tests for this method
    //EFFECTS
    public String getName() {
        return name;
    }

    public JSONArray expensesToJsonArray() {
        JSONArray jsonArray = new JSONArray();

        for (Expense expense: allExpenses) {
            jsonArray.put(expense.toJson());
        }
        return jsonArray;
    }
}
