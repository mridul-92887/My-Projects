package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Withdrawl extends JFrame implements ActionListener {
    
    JTextField t1;
    JButton b1, b2;
    JLabel l1, l2, backgroundLabel;
    String pin;
    
    Withdrawl(String pin) {
        this.pin = pin;
        
        // Load background image
        ImageIcon i1 = null;
        try {
            // Try to load image (update path as needed)
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
        
        // Create labels
        l1 = new JLabel("MAXIMUM WITHDRAWAL IS BDT. 50,000");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        
        l2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("System", Font.BOLD, 16));
        
        // Create text field
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));
        
        // Create buttons
        b1 = new JButton("WITHDRAW");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Raleway", Font.BOLD, 14));
        
        b2 = new JButton("BACK");
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.setFont(new Font("Raleway", Font.BOLD, 14));
        
        // Set layout
        setLayout(null);
        
        // Add components to background
        l1.setBounds(190, 350, 400, 20);
        backgroundLabel.add(l1);
        
        l2.setBounds(190, 400, 400, 20);
        backgroundLabel.add(l2);
        
        t1.setBounds(190, 450, 330, 30);
        backgroundLabel.add(t1);
        
        b1.setBounds(390, 588, 150, 35);
        backgroundLabel.add(b1);
        
        b2.setBounds(390, 633, 150, 35);
        backgroundLabel.add(b2);
        
        // Add action listeners
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        // Frame settings
        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);
    }
    
   public void actionPerformed(ActionEvent ae) {
    try {        
        String amountStr = t1.getText().trim();
        
        if (ae.getSource() == b1) {
            // Validate input
            if (amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the amount you want to withdraw");
                return;
            }
            
            // Parse amount
            int amount;
            try {
                amount = Integer.parseInt(amountStr);
                
                // ✅ CONDITION 1: Minimum withdrawal amount is 500 TK
                if (amount < 500) {
                    JOptionPane.showMessageDialog(null, "Minimum withdrawal amount is  500 Tk");
                    return;
                }
                
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive amount");
                    return;
                }
                
                // Keep existing maximum check
                if (amount > 50000) {
                    JOptionPane.showMessageDialog(null, "Maximum withdrawal is BDT. 10,000");
                    return;
                }
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number without decimals");
                return;
            }
            
            Conn c1 = new Conn();
            
            // Calculate current balance
            ResultSet rs = c1.s.executeQuery("SELECT * FROM bank WHERE pin = '" + pin + "'");
            int balance = 0;
            while (rs.next()) {
                String mode = rs.getString("mode");
                String amountFromDB = rs.getString("amount");
                
                try {
                    int transactionAmount = Integer.parseInt(amountFromDB.replace(",", "").split("\\.")[0]);
                    if (mode.equals("Deposit")) {
                        balance += transactionAmount;
                    } else if (mode.equals("Withdrawl")) {
                        balance -= transactionAmount;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing amount: " + amountFromDB);
                }
            }
            
            // ✅ CONDITION 2: Cannot withdraw if account balance is below 500 TK
            if (balance < 500) {
                JOptionPane.showMessageDialog(null, "Cannot withdraw. Your account balance is below 500 Tk. Current balance: BDT. " + balance);
                return;
            }
            
            // Check if sufficient balance for this withdrawal
            if (balance < amount) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance. Available: Tk. " + balance);
                return;
            }
            
            // ✅ BONUS CONDITION: Check if after withdrawal, balance will be at least 500 TK
            // (if you want to maintain minimum balance after withdrawal)
            if ((balance - amount) < 500) {
                JOptionPane.showMessageDialog(null, "Cannot withdraw. After withdrawal, your balance would be below 500 TK. Maximum you can withdraw: BDT. " + (balance - 500));
                return;
            }
            
            // Format date for SQL
            java.util.Date date = new java.util.Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = sdf.format(date);
            
            // Insert withdrawal transaction
            String query = "INSERT INTO bank (pin, date, mode, amount) VALUES ('" + pin + "', '" + 
                          formattedDate + "', 'Withdrawl', '" + amount + "')";
            c1.s.executeUpdate(query);
            
            JOptionPane.showMessageDialog(null, "BDT. " + amount + " Debited Successfully");
            
            setVisible(false);
            new Transactions(pin).setVisible(true);
            
        } else if (ae.getSource() == b2) {
            setVisible(false);
            new Transactions(pin).setVisible(true);
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}
    public static void main(String[] args) {
        new Withdrawl("").setVisible(true);
    }
}