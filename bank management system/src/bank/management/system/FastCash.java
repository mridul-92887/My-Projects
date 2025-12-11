package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    JLabel l1, l2;
    JButton b1, b2, b3, b4, b5, b6, b7;
    String pin;

    FastCash(String pin) {
        this.pin = pin;
        
        // Load background image
        ImageIcon i1 = null;
        try {
            i1 = new ImageIcon("D:/Apache/bank management system/src/icons/atm.jpg");
        } catch (Exception e) {
            System.out.println("Image not found");
        }
        
        // Create background label
        JLabel backgroundLabel;
        if (i1 != null && i1.getIconWidth() > 0) {
            Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_SMOOTH);
            ImageIcon i3 = new ImageIcon(i2);
            backgroundLabel = new JLabel(i3);
        } else {
            backgroundLabel = new JLabel();
            backgroundLabel.setBackground(Color.DARK_GRAY);
            backgroundLabel.setOpaque(true);
        }
        backgroundLabel.setBounds(0, 0, 960, 1080);
        add(backgroundLabel);

        l1 = new JLabel("SELECT WITHDRAWAL AMOUNT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        // ✅ CHANGED: Updated button amounts
        b1 = new JButton("BDT. 500");
        b2 = new JButton("BDT. 1000");
        b3 = new JButton("BDT. 2000");
        b4 = new JButton("BDT. 5000");
        b5 = new JButton("BDT. 10000");
        b6 = new JButton("BDT. 20000");
        b7 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(235, 400, 700, 35);
        backgroundLabel.add(l1);

        b1.setBounds(170, 499, 150, 35);
        backgroundLabel.add(b1);

        b2.setBounds(390, 499, 150, 35);
        backgroundLabel.add(b2);

        b3.setBounds(170, 543, 150, 35);
        backgroundLabel.add(b3);

        b4.setBounds(390, 543, 150, 35);
        backgroundLabel.add(b4);

        b5.setBounds(170, 588, 150, 35);
        backgroundLabel.add(b5);

        b6.setBounds(390, 588, 150, 35);
        backgroundLabel.add(b6);

        b7.setBounds(390, 633, 150, 35);
        backgroundLabel.add(b7);

        // Add action listeners
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);

        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            Conn c = new Conn();
            
            if (ae.getSource() == b7) {
                // BACK button
                setVisible(false);
                new Transactions(pin).setVisible(true);
                return;
            }
            
            // Get the button that was clicked
            JButton clickedButton = (JButton) ae.getSource();
            String buttonText = clickedButton.getText();
            
            // Extract amount from button text
            String[] parts = buttonText.split(" ");
            if (parts.length < 2) {
                JOptionPane.showMessageDialog(null, "Invalid button configuration");
                return;
            }
            
            String amountStr = parts[1].trim();
            int amount = 0;
            
            try {
                amount = Integer.parseInt(amountStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid amount format: " + amountStr);
                return;
            }
            
            // Calculate current balance
            ResultSet rs = c.s.executeQuery("SELECT * FROM bank WHERE pin = '" + pin + "'");
            int balance = 0;
            while (rs.next()) {
                String mode = rs.getString("mode");
                String amountFromDB = rs.getString("amount");
                
                try {
                    // Remove any commas or decimal points
                    String cleanAmount = amountFromDB.replace(",", "").replace(".00", "");
                    int transactionAmount = Integer.parseInt(cleanAmount);
                    
                    if (mode.equals("Deposit")) {
                        balance += transactionAmount;
                    } else if (mode.equals("Withdrawl")) {
                        balance -= transactionAmount;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing amount from DB: " + amountFromDB);
                }
            }
            
            // ✅ ADDED: Check if account has minimum balance of 500 TK
            if (balance < 500) {
                JOptionPane.showMessageDialog(null, "Cannot withdraw. Your account balance is below 500 TK. Current balance: BDT. " + balance);
                return;
            }
            
            // Check if sufficient balance for withdrawal
            if (balance < amount) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance. Available: BDT. " + balance);
                return;
            }
            
            // ✅ ADDED: Check if after withdrawal, balance will be at least 500 TK
            // This ensures 500 TK must stay in the account
            if ((balance - amount) < 500) {
                int maximumWithdrawable = balance - 500;
                JOptionPane.showMessageDialog(null, 
                    "Cannot withdraw Rs. " + amount + 
                    ". After withdrawal, your balance would be below 500 TK." +
                    "\nMaximum you can withdraw: Rs. " + maximumWithdrawable);
                return;
            }
            
            // Format date for SQL
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = sdf.format(date);
            
            // Insert withdrawal transaction
            String query = "INSERT INTO bank (pin, date, mode, amount) VALUES ('" + pin + "', '" + 
                          formattedDate + "', 'Withdrawl', '" + amount + "')";
            
            int rowsAffected = c.s.executeUpdate(query);
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "BDT. " + amount + " Debited Successfully");
                setVisible(false);
                new Transactions(pin).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Transaction failed. Please try again.");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new FastCash("").setVisible(true);
    }
}