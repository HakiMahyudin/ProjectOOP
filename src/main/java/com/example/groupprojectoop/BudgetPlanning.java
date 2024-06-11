package com.example.groupprojectoop;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class BudgetPlanning {
    private static String FILE_PATH = null;
    private double totalBudget;
    private StringBuilder[] budgetSB = new StringBuilder[8];
    private double income = 0;
    private double[] spendBudget = {0, 0, 0, 0, 0, 0, 0, 0};
    private double[] expenses = {0, 0, 0, 0, 0, 0, 0, 0};
    private double total_expenses = 0;
    public String[] categories = {"Groceries", "Utilities", "Entertainment", "Bills", "Education", "Food", "Medical", "Clothing"};
    private double multiplier = 0;

    public BudgetPlanning(String name) {
        for (int i = 0; i < budgetSB.length; i++) {
            budgetSB[i] = new StringBuilder();
        }
        setFile(name);
        readData();
        getExpenses();
    }

    public BudgetPlanning() {
        for (int i = 0; i < budgetSB.length; i++) {
            budgetSB[i] = new StringBuilder();
        }
        readData();
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
    
    public double getTotalExpenses() {
        getExpenses();
        return total_expenses;
    }

    public void getExpenses() {
        total_expenses = 0;
        for (int x = 0; x < expenses.length; x++) {
            total_expenses += expenses[x];
        }
    }
    
    public void calcExpense(double value, String category) {
        int index = -1;
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equalsIgnoreCase(category)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            expenses[index] += value;
            System.out.println("Expense added: " + value + " to category " + category);
            writeData(); // Ensure data is written back to the file
        } else {
            System.out.println("Category not found: " + category);
        }
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income += income;
        writeData();
    }

    public double getBalance() {
        return income - total_expenses;
    }

    public void setFile(String name) {
        FILE_PATH = name + ".txt";
    }

    public void createFile(String name) {
        String filename1 = name + ".txt";
        try {
            new File(filename1).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData() {
        if (FILE_PATH == null) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String tempRead = reader.readLine();
            if (tempRead != null) {
                String[] values = tempRead.split(",");
                if (values.length >= 10) { // Ensure correct length for both budgets and expenses
                    income = Double.parseDouble(values[0]);
                    total_expenses = Double.parseDouble(values[1]);
                    for (int i = 0; i < 8; i++) {
                            spendBudget[i] = Double.parseDouble(values[i+2]);
                            expenses[i] = Double.parseDouble(values[i+10]);
                        System.out.println(categories[i] + ": " + expenses[i]);
                    }
                    System.out.print(expenses[7] + "  " +  values[2]);
                }
            }
            //reset the stringBuilder
            for (StringBuilder budget : budgetSB) {
                budget.setLength(0);
            }
            //initialize again
            for (int i = 0; i < budgetSB.length; i++) {
                budgetSB[i].append(spendBudget[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeData() {
        if (FILE_PATH == null) return;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            StringBuilder sb = new StringBuilder();
            sb.append(income).append(",").append(total_expenses);
            for (double value : spendBudget) {
                sb.append(",").append(value);
                //System.out.print(sb.toString());
            }
            for(double value: expenses) {
                sb.append(",").append(value);
                //System.out.print("     "+sb.toString());
            }
            //System.out.print(sb.toString());
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // live display and will auto update in file
    public String setBudget(KeyEvent ke, String category) {

        //searching which category
        int index = -1;
        for (int i = 0; i < categories.length; i++) {
            if (categories[i].equalsIgnoreCase(category)) {
                index = i;
                break;
            }
        }

        //backspace
        if (index != -1) {
            if (ke.getCode().equals(KeyCode.BACK_SPACE) && !budgetSB[index].isEmpty()) {
                budgetSB[index].deleteCharAt(budgetSB[index].length() - 1);
            } else {
                budgetSB[index].append(ke.getText());
            }
        }

        //save the value into spendBudget
        for (int i = 0; i < budgetSB.length; i++) {
            try {
                spendBudget[i] = Double.parseDouble(budgetSB[i].toString());
            } catch (NumberFormatException e) {
                spendBudget[i] = 0;  // or handle the error as needed
            }
        }

        //calculate total budget
        totalBudget = 0;
        for(double budget : spendBudget)
            totalBudget += budget;
        //multiply 4 if weekly
        totalBudget *= multiplier;
        writeData();

        return String.valueOf(totalBudget);
    }
    public String showRecentInput(String category) {

        int index = -1;
        for(int i = 0; i<categories.length; i++){
            if(category.equalsIgnoreCase(categories[i])){
                index =  i;
                break;
            }
        }
        return "" + spendBudget[index];
    }
    public String proceedValidator(){
        System.out.print(totalBudget + "  " + income);
        if(totalBudget > income){
            return "Error: Budget exceeds income!";
        }
        else{
            return "Budget set successfully.";
        }
    }
    public void updateExpense(double deletedValue, String category){
        int index = -1;
        for(int i = 0; i<categories.length; i++){
            if(category.equalsIgnoreCase(categories[i])){
                index =  i;
                break;
            }
        }
        expenses[index] -= deletedValue;
        total_expenses -=  deletedValue;
        writeData();
    }

}
