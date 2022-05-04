package model;

public class FlexibleExpense extends Expense {
    //The FlexibleExpense class represents the portion of the budget that the application needs to calculate

    public FlexibleExpense(String title) {
        super(title);
        category = "flexible";
    }

    //REQUIRES: Expense is in the user's list of Expenses
    //MODIFIES: this
    //EFFECTS: Calculates the cost of the given expense
    public void calculateCost(double fraction, double income) {
        cost = income * fraction;
        setCost(cost);
    }

    //EFFECTS: return category of expense
    public String getCategory() {
        return category;
    }
}
