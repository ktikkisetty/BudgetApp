package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

//This class references code from the JsonSerializationDemo provided in the phase 2 outline
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public class Budget implements Writable {
    //The Budget class represents how much money is available to spend monthly
    private double income;
    private ListOfExpense list;

    public Budget(double income, ListOfExpense list) {
        this.income = income;
        this.list = list;
    }

    public ListOfExpense getList() {
        return list;
    }

    //EFFECTS: returns income
    public double getIncome() {
        return income;
    }

    //EFFECTS: Determines if expenses can be sustained with given income
    public boolean totalCostVersusIncome(double totalCost) {
        if (income >= totalCost) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        String name = list.getName();
        json.put("budget name", name);
        json.put("budget", budgetToJson());
        json.put("income", income);
        return json;
    }

    // EFFECTS: returns Expenses in this Budget as a JSON array
    private JSONArray budgetToJson() {
        JSONArray jsonArray = list.expensesToJsonArray();

        return jsonArray;
    }
}
