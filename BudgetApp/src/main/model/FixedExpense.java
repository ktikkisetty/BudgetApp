package model;

public class FixedExpense extends Expense {
    //The FixedExpense Class represents expenses that the application does not need to calculate

    public FixedExpense(String title) {
        super(title);
        category = "fixed";
    }

    //REQUIRES: Expense is in the user's list of Expenses
    //MODIFIES: this
    //EFFECTS: Calculates the cost of the given expense
    public void calculateCost(double price) {
        setCost(price);
    }

    //EFFECTS: return category of expense
    public String getCategory() {
        return category;
    }
}
