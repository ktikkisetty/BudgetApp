package ui;

// This class references code from the TellerAppNotRobust example and the Ferry debugger example also provided
//  in CPSC 210.
// Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
// Link: https://github.students.cs.ubc.ca/CPSC210/DataAbstractionLectureStarters.git

//This class references code from the JsonSerializationDemo provided in the phase 2 outline
//Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

//This class references code from the FormattedTextFieldDemo from Oracle Java Documentation
//Link: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/FormattedTextFieldDemoProject/src/components/FormattedTextFieldDemo.java

//This class references code from LabelChanger
//Link: https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application

//This class references code from RadioButtonDemo
//Link: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/RadioButtonDemoProject/src/components/RadioButtonDemo.java

//This class references code from ButtonDemo
//Link: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/BorderDemoProject/src/components/BorderDemo.java

//This class references code from IconDemoApp
//https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/IconDemoProject/src/components/IconDemoApp.java

//This class references code from AlarmSystem from CPSC 210
//Link: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

import model.Event;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BudgetApp extends JFrame implements ActionListener {
    private static final String JSON_STORE = "./data/budget.json";
    private Scanner input;
    private Budget budget;
    private ListOfExpense allExpenses;
    private static double savingsFraction = 0.3;
    private static double groceriesFraction = 0.2;
    private static double entertainmentFraction = 0.1;
    private static double restaurantFraction = 0.08;
    private static double clothingFraction = 0.05;
    private static double fitnessFraction = 0.06;
    private JRadioButton savings;
    private JRadioButton groceries;
    private JRadioButton entertainment;
    private JRadioButton restaurant;
    private JRadioButton clothing;
    private JRadioButton fitness;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JLabel label;
    private JFormattedTextField incomeField;
    private JButton addExpenseButton;
    private JButton finish;
    private JButton addFixedExpenseButton;
    private JButton addFlexibleExpenseButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton next;
    private JButton incomeButton;
    private JTextField fixedExpenseName;
    private JTextField deleteField;
    private JButton deleteButton;
    private JButton finishDeleteButton;
    private JButton printButton;
    private JFormattedTextField fixedExpensePrice;
    private List<JLabel> printedExpenses;
    private JButton remove;

    //EFFECTS: runs the budget maker
    public BudgetApp() {
        super("The title");
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        allExpenses = new ListOfExpense("My budget");
        runBudget();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runBudget() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);
        addExpenseButton = new JButton("Add Expense");
        openWindow();

//        System.out.println("Welcome to your personal budget calculator! Please enter your monthly income");
//        Double income = input.nextDouble();


        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
//                processCommand(command);
            }
        }


        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays menu of options to user
    //          only display delete option if there is at least one expense in the list
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add expense");
        if (allExpenses.size() > 0) {
            System.out.println("\td -> delete expense");
        }
        System.out.println("\ts -> save budget to file");
        System.out.println("\tl -> load budget from file");
        System.out.println("\tp -> print budget");
        System.out.println("\tq -> quit");
    }

//    // MODIFIES: this
//    // EFFECTS: processes user command
//    private void processCommand(String command) {
//        if (command.equals("a")) {
//            String choice = null;
//            chooseTypeOfExpense();
//            choice = input.next();
//            choice = choice.toLowerCase();
//            processExpenseChoice(choice);
//        } else if (command.equals("d")) {
//            delete();
//        } else if (command.equals("s")) {
//            saveBudget();
//        } else if (command.equals("l")) {
//            loadBudget();
//        } else if (command.equals("p")) {
//            printBudget();
//        } else {
//            System.out.println("Selection not valid...");
//        }
//    }

    //EFFECT: Delete selected expense
    private void delete(String choice) {
//        String choice = null;
//        System.out.println("Please enter the name of the title of the expense you want to remove."
//                +
//                " If you do not enter the name of the expense correctly, nothing will happen");
//        System.out.println("Here are the expenses currently in your budget");
//        input.nextLine();
//        choice = input.nextLine();
        allExpenses.deleteExpenseByTitle(choice);
    }

    //EFFECT:Display each expense with its title and cost if income is sufficient. Otherwise, suggests user
    // deletes an expense
    private void printBudget() {
        double totalCost = allExpenses.calculateTotalCost();
        boolean reasonable = budget.totalCostVersusIncome(totalCost);
        printedExpenses = new ArrayList<>();
        if (reasonable) {
            remove.setEnabled(true);
            for (int i = 0; i < allExpenses.size(); i++) {
                Expense next = allExpenses.get(i);
                JLabel expense = new JLabel();
                expense.setText(next.getName() + ":" + next.getCost() + "dollars");
                add(expense);
                printedExpenses.add(expense);
            }
        } else {
            System.out.println("Insufficient income! Please consider deleting an expense");
        }
    }

    //EFFECTS: When remove button is clicked, printed budget is removed
    private void remove() {
        for (JLabel next: printedExpenses) {
            next.setText("hello");
            printedExpenses.remove(next);
        }
    }

    //MODIFIES: this
    //EFFECTS: Disable all buttons except for the continue button and the delete field
    private void commenceDelete() {
        disableSaveAndLoad();
        addExpenseButton.setEnabled(false);
        deleteField.setEditable(true);
        printBudget();
        deleteButton.setEnabled(false);
        finishDeleteButton.setEnabled(true);
    }

    //MODIFIES: this
    //EFFECTS: expense is removed from the expense list
    private void finishDelete() {
        deleteField.setEditable(false);
        finishDeleteButton.setEnabled(false);
        delete(deleteField.getText());
        addExpenseButton.setEnabled(true);
        enableSaveAndLoad();
    }

    //EFFECT: prints out Budget
    private void printBudgetAsReminder() {
        for (int i = 0; i < allExpenses.size(); i++) {
            Expense next = allExpenses.get(i);
            System.out.println(next.getName() + ":" + next.getCost() + "dollars");
        }
    }

    //EFFECT: user chooses between fixed expense and flexible expense
    private void chooseTypeOfExpense() {
        System.out.println("\nSelect from:");
        System.out.println("\tfixed -> add fixed expense");
        System.out.println("\tflexible -> add flexible expense");
    }


    //EFFECTS: makes an expense with name entered by the user and a cost
    //         entered by the user or calculated
    private void processExpenseChoice(String choice) {
        if (choice.equals("fixed")) {
            //System.out.println("\nPlease enter the name of your expense.");
            processFixed();

//            input.nextLine();
//            String name = input.nextLine();
//            System.out.println("Please enter the cost of this expense");
//            Double cost = input.nextDouble();
//            Expense expense = new FixedExpense(name);
//            expense.setCost(cost);
//            allExpenses.addExpense(expense);

        } else if (choice.equals("flexible")) {
//            System.out.println("\nPlease select from");
//            System.out.println("\tsavings");
//            System.out.println("\tgroceries");
//            System.out.println("\tentertainment");
//            System.out.println("\trestaurants");
//            System.out.println("\tclothing");
//            System.out.println("\tfitness");
//            String flex = input.next();
//            processFlexible(flex);

            savings.setEnabled(true);
            groceries.setEnabled(true);
            fitness.setEnabled(true);
            clothing.setEnabled(true);
            entertainment.setEnabled(true);
            restaurant.setEnabled(true);

        } else {
            System.out.println("Selection not valid...");
        }
    }

    //EFFECTS: Make a fixed expense and add it to the list of expenses with user entered price
    //         and name
    private void processFixed() {
        addFixedExpenseButton.setEnabled(false);
        addFlexibleExpenseButton.setEnabled(false);
        next.setEnabled(true);
        label.setText("Please enter the name of your expense and then press continue.");
        fixedExpenseName.setEditable(true);
    }

    //EFFECTS: Collect price for the fixed expense
    private void processFixedCont() {
        next.setEnabled(false);
        finish.setEnabled(true);
        fixedExpenseName.setEditable(false);
        label.setText("Please enter the price of your expense and then press Finish.");
        fixedExpensePrice.setEditable(true);
    }

    //EFFECTS: make the fixed expense
    private void processFixedFinish() {
        finish.setEnabled(false);
        String name = fixedExpenseName.getText();
        Expense expense = new FixedExpense(name);
        Double cost = ((Number)fixedExpensePrice.getValue()).doubleValue();
        expense.setCost(cost);
        allExpenses.addExpense(expense);
        addExpenseButton.setEnabled(true);
        fixedExpensePrice.setEditable(false);
    }

    //EFFECT: Make a flexible expense and add it to the list of expenses based on user input
    private void processFlexible(String flex) {
        double income = budget.getIncome();
        double fraction = 0;
        Expense expense = new FlexibleExpense(flex);
        if (flex.equals("savings")) {
            fraction = savingsFraction;
        } else if (flex.equals("groceries")) {
            fraction = groceriesFraction;
        } else if (flex.equals("entertainment")) {
            fraction = entertainmentFraction;
        } else if (flex.equals("restaurant")) {
            fraction = restaurantFraction;
        } else if (flex.equals("clothing")) {
            fraction = clothingFraction;
        } else if (flex.equals("fitness")) {
            fraction = fitnessFraction;
        } else {
            System.out.println("Selection not valid...");
        }
        double cost = income * fraction;
        expense.setCost(cost);
        allExpenses.addExpense(expense);
    }

    //This class method references code from the JsonSerializationDemo provided in the phase 2 outline
    //Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    // EFFECTS: saves the budget to file
    private void saveBudget() {
        try {
            jsonWriter.open();
            jsonWriter.write(budget);
            jsonWriter.close();
            ListOfExpense list = budget.getList();
//            System.out.println("Saved " + list.getName() + " to " + JSON_STORE);
            label.setText("Saved " + list.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //This method references code from the JsonSerializationDemo provided in the phase 2 outline
    //Link: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    // MODIFIES: this
    // EFFECTS: loads budget from file
    private void loadBudget() {
        try {
            budget = jsonReader.read();
            allExpenses = budget.getList();
//            System.out.println("Loaded " + allExpenses.getName() + " from " + JSON_STORE);
            label.setText("Loaded " + allExpenses.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //EFFECTS: Sets up and displays GUI
    public void openWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
            }
        });
        setPreferredSize(new Dimension(800, 800));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        instantiateButtons();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        ImageIcon logo = createImageIcon("BudgetAppLogo.jpg");
        JButton appLogo = new JButton(logo);
        add(appLogo);
        remove = new JButton("remove printed items");
        remove.setActionCommand("remove");
        add(remove);
    }

    //EFFECTS: Displays BudgetApp Logo
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = BudgetApp.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    //EFFECT: Instantiates buttons
    public void instantiateButtons() {
        incomeButton = new JButton("Enter Income");
        next = new JButton("Continue adding fixed expense");
        next.setActionCommand("next");
        next.addActionListener(this);
        addExpenseButton.setActionCommand("addButton");
        addExpenseButton.addActionListener(this);
        incomeField = new JFormattedTextField(0);
        incomeField.setColumns(10);
        incomeButton.setActionCommand("incomeButton");
        incomeButton.addActionListener(this);
        label = new JLabel(" ");
        finish = new JButton("Finish");
        finish.setActionCommand("finish");
        finish.addActionListener(this);
        add(incomeField);
        add(label);
        add(incomeButton);
        add(addExpenseButton);
        add(finish);
        addExpenseButton.setEnabled(false);
        instantiateExpenseTypes();
        instantiateSaveAndLoad();
        instantiateDeleteButtonsAndField();
    }

    //EFFECTS: Instantiates components need to delete
    public void instantiateDeleteButtonsAndField() {
        deleteButton = new JButton("Delete");
        deleteButton.setActionCommand("delete");
        deleteField = new JTextField();
        deleteField.setColumns(10);
        finishDeleteButton = new JButton("Finish Delete");
        finishDeleteButton.setActionCommand("finish delete");

        deleteButton.addActionListener(this);
        finishDeleteButton.addActionListener(this);

        add(deleteButton);
        add(finishDeleteButton);
        add(deleteField);

        disableDeleteButtons();
    }

    //EFFECTS: Disables Components related to deleting expenses
    public void disableDeleteButtons() {
        deleteButton.setEnabled(false);
        finishDeleteButton.setEnabled(false);
        deleteField.setEditable(false);
    }

    //EFFECTS: instantiate buttons related to saving, loading and deleting
    public void instantiateSaveAndLoad() {
        saveButton = new JButton("Save Expense");
        loadButton = new JButton("Load Expense");
        printButton = new JButton("Print All Expenses");

        saveButton.setActionCommand("save");
        loadButton.setActionCommand("load");
        printButton.setActionCommand("print");

        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        printButton.addActionListener(this);

        add(saveButton);
        add(loadButton);
        add(printButton);

        saveButton.setEnabled(false);
        loadButton.setEnabled(false);
        printButton.setEnabled(false);
    }

    //EFFECTS: disable save, load and print button
    public void disableSaveAndLoad() {
        saveButton.setEnabled(false);
        loadButton.setEnabled(false);
        printButton.setEnabled(false);
    }

    //EFFECTS: Enable save, load, print buttons
    public void enableSaveAndLoad() {
        saveButton.setEnabled(true);
        loadButton.setEnabled(true);
        printButton.setEnabled(true);
    }

    //Instantiate all radio buttons for flexible expense options
    public void instantiateRadioButtons() {
        savings = new JRadioButton("savings");
        savings.setActionCommand("savings");
        groceries = new JRadioButton("groceries");
        groceries.setActionCommand("groceries");
        fitness = new JRadioButton("fitness");
        fitness.setActionCommand("fitness");
        clothing = new JRadioButton("clothing");
        clothing.setActionCommand("clothing");
        entertainment = new JRadioButton("entertainment");
        entertainment.setActionCommand("entertainment");
        restaurant = new JRadioButton("restaurant");
        restaurant.setActionCommand("restaurant");

        groupAndListenerRadioButtons();
    }

    //EFFECTS: Add Radio Buttons
    public void groupAndListenerRadioButtons() {
        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(savings);
        group.add(groceries);
        group.add(fitness);
        group.add(clothing);
        group.add(entertainment);
        group.add(restaurant);

        //Register a listener for the radio buttons.
        savings.addActionListener(this);
        groceries.addActionListener(this);
        fitness.addActionListener(this);
        clothing.addActionListener(this);
        entertainment.addActionListener(this);
        restaurant.addActionListener(this);

        add(savings);
        add(groceries);
        add(fitness);
        add(clothing);
        add(entertainment);
        add(restaurant);

        disableRadioButtons();
    }

    //EFFECTS: disable RadioButtons
    public void disableRadioButtons() {
        savings.setEnabled(false);
        groceries.setEnabled(false);
        fitness.setEnabled(false);
        clothing.setEnabled(false);
        entertainment.setEnabled(false);
        restaurant.setEnabled(false);
    }

    //EFFECT: display all items in the budget
    public void printPanel() {
        double totalCost = allExpenses.calculateTotalCost();
        boolean reasonable = budget.totalCostVersusIncome(totalCost);
        if (reasonable) {
            for (int i = 0; i < allExpenses.size(); i++) {
                Expense next = allExpenses.get(i);
//                System.out.println(next.getName() + ":" + next.getCost() + "dollars");
                JLabel expense = new JLabel();
                add(expense);
                expense.setText(next.getName() + ": " + next.getCost() + " dollars");
            }
        } else {
            System.out.println("Insufficient income! Please consider deleting an expense");
        }
    }

    //EFFECTS: flexible and fixed expense buttons are displayed when the add expense button is clicked
    public void instantiateExpenseTypes() {
        addFixedExpenseButton = new JButton("Add Fixed expense");
        addFlexibleExpenseButton = new JButton("Add Flexible expense");
        addFixedExpenseButton.setActionCommand("addFixedButton");
        addFixedExpenseButton.addActionListener(this);
        addFlexibleExpenseButton.setActionCommand("addFlexibleButton");
        addFlexibleExpenseButton.addActionListener(this);
        add(addFixedExpenseButton);
        addFixedExpenseButton.setEnabled(false);
        add(addFlexibleExpenseButton);
        addFlexibleExpenseButton.setEnabled(false);
        fixedExpenseName = new JTextField();
        fixedExpenseName.setColumns(10);
        fixedExpensePrice = new JFormattedTextField(0);
        fixedExpensePrice.setColumns(10);
        instantiateExpenseContinued();
    }

    public void instantiateExpenseContinued() {
        fixedExpenseName.setEditable(false);
        fixedExpensePrice.setEditable(false);
        add(fixedExpenseName);
        add(fixedExpensePrice);
        instantiateRadioButtons();
        add(next);
        next.setEnabled(false);
        finish.setEnabled(false);
        instantiateDeleteButtonsAndField();
        disableDeleteButtons();
    }

    //EFFECTS: Disables and Enables all the appropriate buttons after adding expense to all expenses
    public void flexibleChoice(String command) {
        disableRadioButtons();
        processFlexible(command);
        addFixedExpenseButton.setEnabled(false);
        addFlexibleExpenseButton.setEnabled(false);
        addExpenseButton.setEnabled(true);
        enableSaveAndLoad();
        deleteButton.setEnabled(true);
    }

    //EFFECTS: Provide functionality for income
    public void income() {
        label.setText(incomeField.getText());
        Double income = ((Number)incomeField.getValue()).doubleValue();
        budget = new Budget(income, allExpenses);
        incomeField.setEditable(false);
        incomeButton.setEnabled(false);
        enableSaveAndLoad();
        addExpenseButton.setEnabled(true);
    }

    //EFFECT:processes add button functionality
    public void add() {
        addExpenseButton.setEnabled(false);
        addFixedExpenseButton.setEnabled(true);
        addFlexibleExpenseButton.setEnabled(true);
        disableSaveAndLoad();
    }

    //EFFECTS: Functionality for finish button
    public void finishButton() {
        label.setText("Expense added");
        processFixedFinish();
        enableSaveAndLoad();
        deleteButton.setEnabled(true);
    }

    //EFFECTS: Provides functionality to all buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addButton")) {
            add();
        } else if (e.getActionCommand().equals("addFixedButton")) {
            processExpenseChoice("fixed");
        } else if (e.getActionCommand().equals("addFlexibleButton")) {
            processExpenseChoice("flexible");
        } else if (e.getActionCommand().equals("incomeButton")) {
            income();
        } else if (e.getActionCommand().equals("next")) {
            label.setText("Processing...");
            processFixedCont();
        } else if (e.getActionCommand().equals("finish")) {
            finishButton();
        } else if (e.getActionCommand().equals(savings.getActionCommand())) {
            flexibleChoice(savings.getActionCommand());
        } else if (e.getActionCommand().equals(remove.getActionCommand())) {
            remove();
        } else {
            continueListening(e);
        }
    }

    //EFFECTS: Handles several buttons
    public void continueListening(ActionEvent e) {
        if (e.getActionCommand().equals(groceries.getActionCommand())) {
            flexibleChoice(groceries.getActionCommand());
        } else if (e.getActionCommand().equals(restaurant.getActionCommand())) {
            flexibleChoice(restaurant.getActionCommand());
        } else if (e.getActionCommand().equals(fitness.getActionCommand())) {
            flexibleChoice(fitness.getActionCommand());
        } else if (e.getActionCommand().equals(clothing.getActionCommand())) {
            flexibleChoice(clothing.getActionCommand());
        } else if (e.getActionCommand().equals(entertainment.getActionCommand())) {
            flexibleChoice(entertainment.getActionCommand());
        } else if (e.getActionCommand().equals("save")) {
            saveBudget();
        } else if (e.getActionCommand().equals("load")) {
            loadBudget();
        } else if (e.getActionCommand().equals(printButton.getActionCommand())) {
            printPanel();
        } else if (e.getActionCommand().equals("delete")) {
            label.setText("Enter the name of your expense and then press finish delete button");
            commenceDelete();
        } else if (e.getActionCommand().equals("finish delete")) {
            finishDelete();
        }
    }

    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println("\n " + next);
        }

        repaint();
    }
}
