package com.example.groupprojectoop;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Transaction {
    private static String FILE_PATH = null;
    private List<TransactionRecord> transactions;

    public Transaction(String name) {
        setFileTrans(name);
        createFile(name);
        transactions = new ArrayList<>();
        readDataTrans();
        System.out.println(transactions);
    }

    public Transaction() {
        readDataTrans();
    }

    public void setFileTrans(String name) {
        FILE_PATH = "trans" + name + ".txt";
    }

    public void createFile(String name) {
        Path path = Paths.get("trans" + name + ".txt");
        try {
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readDataTrans() {
        if (FILE_PATH == null) return;

        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            transactions = lines.stream()
                    .map(line -> {
                        String[] lineData = line.split(",");
                        if (lineData.length == 4) {
                            double value = Double.parseDouble(lineData[0]);
                            String category = lineData[1];
                            LocalDate date = LocalDate.parse(lineData[2]);
                            String note = lineData[3];
                            return new TransactionRecord(value, category, date, note);
                        }
                        return null;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataTrans(TransactionRecord record) {
        if (FILE_PATH == null) return;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(record.getValue()).append(",")
                    .append(record.getCategory()).append(",")
                    .append(record.getDate()).append(",")
                    .append(record.getNote()).append("\n");
            Files.write(Paths.get(FILE_PATH), sb.toString().getBytes(), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<TransactionRecord> getTransactions() {
        return transactions;
    }

    public void addExpense(double amount, String category, LocalDate date, String note) {
        // Implementation to add the expense to the transaction list or file
        // Example: save to a file or update an in-memory list
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
//            var id = generateId ();
            String fmt = String.format("%s,%s,%s,%s",amount,category,date,note);
            writer.write(fmt);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void editExpense(int index, double value, String category, LocalDate date, String note) {
        if (index >= 0 && index < transactions.size()) {
            transactions.set(index, new TransactionRecord(value, category, date, note));
            updateFile();
        }
    }

    public void updateFile() {
        if (FILE_PATH == null) return;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (TransactionRecord record : transactions) {
                StringBuilder sb = new StringBuilder();
                sb.append(record.getValue()).append(",")
                        .append(record.getCategory()).append(",")
                        .append(record.getDate()).append(",")
                        .append(record.getNote()).append("\n");
                writer.write(sb.toString());
            }
        } catch (IOException e)
        {
           e.printStackTrace();
        }
    }
    public static String generateId() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            char c = (char) (random.nextInt(26) + 'A'); // generate a random uppercase letter
            id.append(c);
        }

        return id.toString();
    }


    public void updateTransaction(int index, double value, String category, LocalDate date, String note) {
    if (index >= 0 && index < transactions.size()) {
        TransactionRecord transactionRecord = transactions.get(index);
        transactionRecord.setValue(value);
        transactionRecord.setCategory(category);
        transactionRecord.setDate(date);
        transactionRecord.setNote(note);
        updateFile(); // Update the file after modifying the transaction
    }
    }

}
