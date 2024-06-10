//semua takde back button
//masukkan income choice in transaction
//expense attribute change into transaction class
//validation for expense exceed budget

package com.example.groupprojectoop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
    private Scene scene;
    private final Image icon = new Image("file:///C:/Users/SAFWAN/Desktop/DEGREE/YEAR%201/SEM%201/ELEMENT%20OF%20PROGRAMMING/GroupProject/src/image/icon.jpeg");
    private final Alert msg = new Alert(Alert.AlertType.NONE);
    private String password;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("MyMoney");

        // Create main scene
        VBox mainLayout = createMainLayout(primaryStage);
        mainLayout.setStyle("-fx-background-color: rgba(199, 184, 234, 1.0); -fx-background-radius: 30; -fx-padding: 15; -fx-border-width: 10; -fx-border-color: black; -fx-border-radius: 30;");//set color and shape

        scene = new Scene(mainLayout, 300, 300, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private VBox createMainLayout(Stage stage) {
        ImageView logo = createLogo(150, 150);

        Label optionLabel = new Label("Choose an option");
        optionLabel.setFont(Font.font("Verdana", 18));

        Button logInButton = createButton("LogIn", 100, e -> logInPage(stage));
        Button signUpButton = createButton("SignUp", 100, e -> signUpPage(stage));

        logInButton.setStyle("-fx-background-color: none;");
        signUpButton.setStyle("-fx-background-color: none;");

        // Change the button color to green when hovered
        logInButton.setOnMouseEntered(e -> logInButton.setStyle("-fx-background-color: green;"));
        logInButton.setOnMouseExited(e -> logInButton.setStyle("-fx-background-color: none;"));
        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle("-fx-background-color: green;"));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle("-fx-background-color: none;"));

        HBox buttonBox = new HBox(15, logInButton, signUpButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, logo, optionLabel, buttonBox);
        layout.setAlignment(Pos.CENTER);

        return layout;
    }

    private void logInPage(Stage stage) {
        ImageView logo = createLogo(300, 300, 0.5);

        Label usernameLabel = createLabel("Username:", 18, 100);
        TextField usernameField = createTextField(100);
        usernameField.setPromptText("username");

        HBox usernameBox = new HBox(10, usernameLabel, usernameField);
        usernameBox.setAlignment(Pos.CENTER);

        Label passwordLabel = createLabel("Password:", 18, 100);
        PasswordField passwordField = createPasswordField(100);
        passwordField.setPromptText("password");

        HBox passwordBox = new HBox(10, passwordLabel, passwordField);
        passwordBox.setAlignment(Pos.CENTER);
        Login login = new Login();
        Button loginButton = createButton("Login", 100, e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (login.checkUser(username, password)) {
                goMainPage(stage);
            } else {
                showErrorMessage("Incorrect username and password.");
            }
        });
        loginButton.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: darkgreen;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: green;"));

        VBox layout = new VBox(10,  usernameBox, passwordBox, loginButton);
        layout.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(logo,layout);
        root.setStyle("-fx-background-color: rgba(199, 184, 234, 1.0); -fx-background-radius: 30; -fx-padding: 15; -fx-border-width: 10; -fx-border-color: black; -fx-border-radius: 30;");//set color and shape

        scene = new Scene(root, 300, 300, Color.BLACK);
        stage.setScene(scene);
    }

    private void signUpPage(Stage stage) {
        ImageView logo = createLogo(300, 300, 0.5);

        Label usernameLabel = createLabel("Username:", 18, 100);
        TextField usernameField = createTextField(100);
        usernameField.setPromptText("username");
        HBox usernameBox = new HBox(10, usernameLabel, usernameField);
        usernameBox.setAlignment(Pos.CENTER);

        Label passwordLabel = createLabel("Password:", 18, 100);
        PasswordField passwordField = createPasswordField(100);
        passwordField.setPromptText("password");

// Add validation to password field
        ValidationPassword passwordValidator = new ValidationPassword ();
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!passwordValidator.isValidPassword(newValue)) {
                // Display error message to user
                System.out.println("Invalid password. Password must be at least 8 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character.");
            }
        });

        HBox passwordBox = new HBox(10, passwordLabel, passwordField);
        passwordBox.setAlignment(Pos.CENTER);
        Login login = new Login();
        Button signUpButton = createButton("SignUp", 100, e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty() || password.isEmpty()) {
                showErrorMessage("Please fill in all the information.");
            }
            else if (!passwordValidator.isValidPassword(password)) {
                // Create an alert dialog to display the error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Password");
                alert.setHeaderText("Password is not valid");
                alert.setContentText("Password must be at least 8 characters long and contain at least one lowercase letter, one uppercase letter, one digit, and one special character.");
                alert.showAndWait();

            }else {
                login.createAccount(username, password);
                showInfoMessage("Sign Up Successful!");
                logInPage(stage);
            }

        });

        VBox layout = new VBox(10,  usernameBox, passwordBox, signUpButton);
        layout.setAlignment(Pos.CENTER);
        StackPane root = new StackPane(logo,layout);
        scene = new Scene(root, 300, 300, Color.BLACK);
        stage.setScene(scene);
    }

    private VBox createSidePanel(Stage stage) {
        Button dashboardButton = createButton("Dashboard", 100, e -> goMainPage(stage));
        Button analysisButton = createButton("Analysis", 100, e -> showAnalysisWindows ());
        Button categoriesButton = createButton("Categories", 100, e -> showInfoMessage("Categories clicked"));

        VBox sidePanel = new VBox(10, dashboardButton, analysisButton, categoriesButton);
        sidePanel.setAlignment(Pos.CENTER_LEFT);

        return sidePanel;
    }

    private void goMainPage(Stage stage) {
        Login login = new Login();
        BudgetPlanning budget = new BudgetPlanning(login.getName());
        Dashboard dashboard = new Dashboard(login.getName());

        Label welcome = createLabel("Hi " + login.getName() + ", Welcome to MyMoney", 20);
        welcome.setAlignment(Pos.TOP_CENTER);

        Label expensesLabel = createLabel("Expenses", 16, 60);
        Label valueExpenses = new Label(String.valueOf(budget.getTotalExpenses()));
        VBox box1 = new VBox(expensesLabel, valueExpenses);

        Label incomeLabel = createLabel("Income", 16, 60);
        Label valueIncome = new Label(String.valueOf(budget.getIncome()));
        VBox box2 = new VBox(incomeLabel, valueIncome);

        Label balanceLabel = createLabel("Balance", 16, 60);
        Label valueBalance = new Label(String.valueOf(budget.getBalance()));
        VBox box3 = new VBox(balanceLabel, valueBalance);

        HBox boxDisplay = new HBox(10, box2, box1, box3);
        boxDisplay.setSpacing(20);
        boxDisplay.setAlignment(Pos.CENTER);

        Button close = createButton("Close", 60, e -> System.exit(0));
        Button logOut = createButton("LogOut", 60, e -> start(stage));
        Button income = createButton("Set Income", 60, e -> income(stage));
        Button setBudget = createButton("Set Budget", 60, e -> setBudget(stage));
        Button addExpenses = createButton("Add Expenses", 60, e -> setExpenses(stage));
        Button editExpenses = createButton("Edit Expenses",60,e->edit(stage,getIndex(stage)));

        FlowPane buttonPane = new FlowPane(income, setBudget, addExpenses,editExpenses, logOut, close);
        buttonPane.setHgap(15);
        buttonPane.setAlignment(Pos.CENTER);

        VBox centerContent = new VBox(10, welcome, boxDisplay);
        centerContent.setAlignment(Pos.CENTER);

        HBox topDisplay = new HBox(10,createSidePanel(stage),centerContent);
        topDisplay.setSpacing(100);
        VBox dataBox = new VBox(10, dashboard.createDashboard());
        centerContent.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();

        root.setTop(topDisplay);
        root.setCenter(dataBox);
        root.setBottom(buttonPane);
        Image image = new Image(getClass().getResourceAsStream("/Images/ESO-VLT-Laser-phot-33a-07 (1).jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        root.setBackground (new Background (backgroundImage));
        root.setStyle ("-fx-padding: 10 20 10 20");
//        root.setStyle("-fx-background-color: rgba(199, 184, 234, 1.0); -fx-background-radius: 30; -fx-padding: 15; -fx-border-width: 10; -fx-border-color: black; -fx-border-radius: 30;");

        BorderPane.setAlignment(buttonPane, Pos.CENTER);

        scene = new Scene(root, 800, 500, Color.BLACK);
        stage.setScene(scene);
        stage.show();
    }

    private int getIndex(Stage stage) {
        Label indexLabel = createLabel("Enter No. to edit :", 16);
        TextField indexField = createTextField(80);
        Button proceed = createButton("Proceed", 16, e -> edit(stage, Integer.parseInt(indexField.getText())));
        VBox indexBox = new VBox(10, indexLabel, indexField, proceed);
        Scene scene = new Scene(indexBox, 300, 300, Color.BLACK);
        stage.setScene(scene);
        String text = indexField.getText().trim(); // Remove leading and trailing whitespaces
        if (!text.isEmpty()) {
            return Integer.parseInt(text);
        } else {
            showErrorMessage("Please enter a valid index.");
            return -1; // Or any other default value indicating an error
        }

    }

    private void edit(Stage stage, int index) {
        // Get the selected transaction record from the transaction list
        Transaction transaction = new Transaction();
        BudgetPlanning budget = new BudgetPlanning();
        List<TransactionRecord> transactions = transaction.getTransactions();

        if (index >= 0 && index < transactions.size()) {
            TransactionRecord selectedTransaction = transactions.get(index);

            // Display a form to edit the transaction
            Label valueLabel = createLabel("Value:", 15, 75);
            TextField valueField = createTextField(100);
            valueField.setText(String.valueOf(selectedTransaction.getValue()));

            Label categoryLabel = createLabel("Category:", 15, 75);
            TextField categoryField = createTextField(100);
            categoryField.setText(selectedTransaction.getCategory());

            Label dateLabel = createLabel("Date:", 15, 75);
            DatePicker datePicker = new DatePicker();
            datePicker.setValue(selectedTransaction.getDate());

            Label noteLabel = createLabel("Note:", 15, 75);
            TextField noteField = createTextField(100);
            noteField.setText(selectedTransaction.getNote());

            Button saveButton = createButton("Save", 100, e -> {
                try {
                    double value = Double.parseDouble(valueField.getText());
                    String category = categoryField.getText();
                    LocalDate date = datePicker.getValue();
                    String note = noteField.getText();

                    // Update the selected transaction
                    transaction.editExpense(index, value, category, date, note);
                    budget.calcExpense(value, category);
                    // Perform any additional operations like updating a file or database here
                    showInfoMessage("Transaction edited successfully.");
                    goMainPage(stage); // Go back to the main page
                } catch (NumberFormatException ex) {
                    showErrorMessage("Invalid value.");
                }
            });

            VBox editForm = new VBox(10, valueLabel, valueField, categoryLabel, categoryField, dateLabel, datePicker, noteLabel, noteField, saveButton);
            editForm.setAlignment(Pos.CENTER);

            Scene editScene = new Scene(editForm, 400, 300);
            stage.setScene(editScene);
            stage.show();
        } else {
            showErrorMessage("Invalid index.");
        }
    }

    private void income(Stage stage){
        BudgetPlanning budget = new BudgetPlanning();
        Label incomeLabel = createLabel("Income: ", 15, 75);
        TextField incomeField = createTextField(100);
        Button setIncomeButton = createButton("Set Income", 100, e -> {
            try {
                double income = Double.parseDouble(incomeField.getText());
                budget.setIncome(income);
                showInfoMessage("Income Set Successfully");
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid income value.");
            }
            goMainPage(stage);
        });

        HBox incomeBox = new HBox(10, incomeLabel, incomeField, setIncomeButton);
        incomeBox.setAlignment(Pos.CENTER);
        scene = new Scene(incomeBox, 300, 300, Color.BLACK);
        stage.setScene(scene);
        stage.show();
    }


    private void setBudget(Stage stage) {
        BudgetPlanning budget = new BudgetPlanning();

        // Create UI elements
        Label categoryLabel = createLabel("Category:", 15, 75);
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(budget.categories);
        categoryComboBox.setMinWidth(100);

        Label budgetLabel = createLabel("Budget: ", 15, 50);
        TextField budgetField = createTextField(100);

        Label periodLabel = createLabel("Period: ", 15, 50);
        ComboBox<String> periodComboBox = new ComboBox<>();
        periodComboBox.getItems().addAll("Monthly", "Weekly"); // Add options for budget periods
        periodComboBox.setMinWidth(100);

        Button setBudgetButton = createButton("Set Budget", 100, e -> {
            try {
                double budgetAmount = Double.parseDouble(budgetField.getText());
                String category = categoryComboBox.getValue();
                String period = periodComboBox.getValue(); // Get selected period
                String result = budget.setBudget(budgetAmount, category, period);
                if (result.startsWith("Error:")) {
                    showErrorMessage(result);
                } else {
                    showInfoMessage(result);
                }
                goMainPage(stage);
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid budget value.");
            }
        });

        // Create layout
        HBox categoryBox = new HBox(10, categoryLabel, categoryComboBox);
        categoryBox.setAlignment(Pos.CENTER);
        HBox budgetBox = new HBox(10, budgetLabel, budgetField);
        budgetBox.setAlignment(Pos.CENTER);
        HBox periodBox = new HBox(10, periodLabel, periodComboBox);
        periodBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, categoryBox, budgetBox, periodBox, setBudgetButton);
        root.setAlignment(Pos.CENTER);

        // Display the scene
        scene = new Scene(root, 600, 300, Color.BLACK);
        stage.setScene(scene);
        stage.show();
    }

    private void setExpenses(Stage stage) {
        Login login = new Login();
        BudgetPlanning budget = new BudgetPlanning(login.getName());
        Transaction trans = new Transaction();

        Label categoryLabel = createLabel("Category:", 15, 75);
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(budget.categories);
        categoryComboBox.setMinWidth(100);

        Label expensesLabel = createLabel("Expenses:", 15, 50);
        TextField budgetField = createTextField(100);

        Label noteLabel = createLabel("Note:", 15, 50);
        TextField noteField = createTextField(100);

        DatePicker datePicker = new DatePicker();

        Button proceedButton = createButton("Proceed", 100, e -> {
            if (datePicker.getValue() == null) {
                showErrorMessage("Please choose a date.");
                return;
            }
            try {
                double expensesAmount = Double.parseDouble(budgetField.getText());
                String category = categoryComboBox.getValue();
                String note = noteField.getText();
                LocalDate date = datePicker.getValue();
                // Update expenses in BudgetPlanning
                budget.calcExpense(expensesAmount, category);
                // Add the expense to the transaction list
                trans.addExpense(expensesAmount, category, date, note);
                goMainPage(stage);
            } catch (NumberFormatException ex) {
                showErrorMessage("Invalid expenses value.");
            }
        });

        HBox box1 = new HBox(10, categoryLabel, categoryComboBox, expensesLabel, budgetField);
        box1.setSpacing(20);
        box1.setAlignment(Pos.CENTER);
        HBox box2 = new HBox(10, datePicker, noteLabel, noteField);
        box2.setSpacing(20);
        box2.setAlignment(Pos.CENTER);
        VBox root = new VBox(10, box1, box2, proceedButton);
        root.setAlignment(Pos.CENTER);

        scene = new Scene(root, 600, 300, Color.BLACK);
        stage.setScene(scene);
        stage.show();
    }

    private ImageView createLogo(double width, double height) {
        return createLogo(width, height, 1.0);
    }

    private ImageView createLogo(double width, double height, double opacity) {
        ImageView logo = new ImageView(icon);
        logo.setFitWidth(width);
        logo.setFitHeight(height);
        logo.setOpacity(opacity);
        return logo;
    }

    private Label createLabel(String text, int fontSize) {
        return createLabel(text, fontSize, -1);
    }

    private Label createLabel(String text, int fontSize, double width) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, fontSize));
        if (width > 0) {
            label.setMinWidth(width);
        }
        return label;
    }

    private TextField createTextField(double width) {
        TextField textField = new TextField();
        textField.setMinWidth(width);
        return textField;
    }

    private PasswordField createPasswordField(double width) {
        PasswordField passwordField = new PasswordField();
        passwordField.setMinWidth(width);
        return passwordField;
    }

    private Button createButton(String text, double width, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 14; -fx-text-fill: #FFFFFF; -fx-background-color: #2196F3;");
        button.setMinWidth(width);
        button.setOnAction(eventHandler);
        return button;
    }

    private void showErrorMessage(String message) {
        msg.setAlertType(Alert.AlertType.ERROR);
        msg.setContentText(message);
        msg.show();
    }

    private void showInfoMessage(String message) {
        msg.setAlertType(Alert.AlertType.INFORMATION);
        msg.setContentText(message);
        msg.show();
    }

    private void showAnalysisWindows(){
        var list = readData ("./transhakim.txt");
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
        stage   .setScene (scene);
        stage.show ();
    }

    public List<Expense> readData(String FILE_PATH) {
        if (FILE_PATH == null) return new ArrayList<> ();
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
}
