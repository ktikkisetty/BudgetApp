package model;

import org.json.JSONObject;
import persistence.Writable;

//This class references code from the JsonSerializationDemo provided in the phase 2 outline
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

public abstract class Expense implements Writable {
    //The Expense class represents each monthly expense
    protected double cost;
    protected String name;
    protected String category;


    public Expense(String title) {
        this.cost = 0;
        this.name = title;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double c) {
        cost = c;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("cost", cost);
        json.put("category", category);
        return json;
    }

}
