package com.example.groupprojectoop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnalysisWindows {

    private String FILE_PATH = null;

    public void showAnalysisWindows(){
        Login login = new Login();
        FILE_PATH = setFile(login.getName());
        var list = readData (FILE_PATH);
        PieChart pieChart = new PieChart();

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for(Expense item : list ){
            var datum = new PieChart.Data (item.Category,item.Amount);
            pieChartData.add (datum);
        }
        // Create an ObservableList to store the pie chart data
        pieChart.setData (pieChartData);
        Scene scene = new Scene (pieChart, 400,400);
        Stage stage = new Stage ();
        stage.setTitle("Expenses Pie Chart");
        stage   .setScene (scene);
        stage.show ();
    }

    public List<Expense> readData(String FILE_PATH) {
        if (FILE_PATH == null) return new ArrayList<>();
        ArrayList<Expense> expenseArrayList = new ArrayList<> ();
        try (FileReader reader = new FileReader (FILE_PATH)) {
            Scanner scanner = new Scanner (reader);
            while(scanner.hasNextLine ()){
                var column = scanner.nextLine ().split (",");
                Expense expense = new Expense ();
                expense.Amount = Double.parseDouble (column[0]);
                expense.Category = column[1];
                expense.Date = column[2];
                expense.Description = column[3];
                expenseArrayList.add (expense);
            }


            scanner.close ();

            return expenseArrayList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<> ();
    }

    private String setFile(String name){
        return "trans" + name + ".txt";
    }
}
