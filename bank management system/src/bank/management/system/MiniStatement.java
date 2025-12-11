package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MiniStatement extends JFrame implements ActionListener {

    JButton closeButton;
    JTextArea textArea;
    String pin;

    MiniStatement(String pin) {
        super("Mini Statement");
        this.pin = pin;
        
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        
        // Title Panel - GRAY COLOR
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.DARK_GRAY);
        titlePanel.setPreferredSize(new Dimension(600, 50));
        
        JLabel title = new JLabel("LGY BANK - MINI STATEMENT");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        titlePanel.add(title);
        add(titlePanel, BorderLayout.NORTH);
        
        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Text Area for Mini Statement
        textArea = new JTextArea(20, 50);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Recent Transactions (Last 10)"));
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createTitledBorder("Account Summary"));
        
        JLabel dateLabel = new JLabel();
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        infoPanel.add(dateLabel);
        infoPanel.add(balanceLabel);
        
        contentPanel.add(infoPanel, BorderLayout.SOUTH);
        add(contentPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        
        closeButton = new JButton("CLOSE");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.addActionListener(this);
        
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Load mini statement
        loadMiniStatement(dateLabel, balanceLabel);
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void loadMiniStatement(JLabel dateLabel, JLabel balanceLabel) {
        StringBuilder statement = new StringBuilder();
        double totalBalance = 0;
        
        // Set statement date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDate = sdf.format(new Date());
        dateLabel.setText("Statement Date: " + currentDate);
        
        try {
            // Get account holder name and card number
            Conn conn = new Conn();
            
            // Get customer name from signup table
            String customerName = "Customer";
            ResultSet rs = conn.s.executeQuery(
                "SELECT s.name, l.cardno " +
                "FROM signup s " +
                "JOIN login l ON s.formno = l.formno " +
                "WHERE l.pin = '" + pin + "'"
            );
            
            String cardNumber = "Not Available";
            if (rs.next()) {
                customerName = rs.getString("name");
                cardNumber = rs.getString("cardno");
            }
            
            // Header
            statement.append("ACCOUNT HOLDER: " + customerName + "\n");
            statement.append("CARD NUMBER: " + maskCardNumber(cardNumber) + "\n");
            statement.append("========================================\n\n");
            
            // Show only LAST 10 transactions (most recent first)
            rs = conn.s.executeQuery(
                "SELECT * FROM bank " +
                "WHERE pin = '" + pin + "' " +
                "ORDER BY date DESC LIMIT 10"
            );
            
            if (!rs.isBeforeFirst()) {
                statement.append("No recent transactions found.\n");
            } else {
                // Add table header
                statement.append("DATE       | TYPE       | AMOUNT (BDT.)\n");
                statement.append("----------------------------------------\n");
                
                int transactionCount = 0;
                while (rs.next()) {
                    transactionCount++;
                    String dateTime = rs.getString("date");
                    String mode = rs.getString("mode");
                    String amount = rs.getString("amount");
                    
                    // Shorten date format
                    String displayDate = formatDate(dateTime);
                    
                    // Add transaction to statement
                    statement.append(String.format("%-10s | %-10s | %s\n", 
                        displayDate, 
                        mode.toUpperCase(), 
                        amount));
                }
                
                statement.append("----------------------------------------\n");
                statement.append("Showing last " + transactionCount + " transaction(s)\n");
            }
            
            // Calculate current balance
            rs = conn.s.executeQuery("SELECT * FROM bank WHERE pin = '" + pin + "'");
            while (rs.next()) {
                String mode = rs.getString("mode");
                String amount = rs.getString("amount");
                
                try {
                    double amt = Double.parseDouble(amount.replace(",", ""));
                    if (mode.equals("Deposit")) {
                        totalBalance += amt;
                    } else if (mode.equals("Withdrawl")) {
                        totalBalance -= amt;
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing amount: " + amount);
                }
            }
            
            // Display balance
            balanceLabel.setText("Current Balance: BDT. " + String.format("%.2f", totalBalance));
            
        } catch (Exception e) {
            statement.append("Error loading mini statement: " + e.getMessage() + "\n");
            e.printStackTrace();
        }
        
        textArea.setText(statement.toString());
    }
    
    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 16) {
            return "XXXX-XXXX-XXXX-XXXX";
        }
        return cardNumber.substring(0, 4) + "-XXXX-XXXX-" + cardNumber.substring(12);
    }
    
    private String formatDate(String dateTime) {
        if (dateTime == null || dateTime.isEmpty()) {
            return "N/A";
        }
        // Return only date part (YYYY-MM-DD)
        return dateTime.split(" ")[0];
    }

    public void actionPerformed(ActionEvent ae) {
        dispose();
    }

    public static void main(String[] args) {
        // Test with a valid PIN
        new MiniStatement("1234").setVisible(true);
    }
}