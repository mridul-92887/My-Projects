/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Deposit extends JFrame implements ActionListener {
    
    JTextField t1;
    JButton b1, b2;
    JLabel l1, l3;
    String pin;
    
    Deposit(String pin) {
        this.pin = pin;
        
        // Fixed: Use classpath resource or relative path
        ImageIcon i1 = null;
        try {
            // Try to load from resources first
            java.net.URL imgURL = getClass().getResource("/icons/atm.jpg");
            if (imgURL != null) {
                i1 = new ImageIcon(imgURL);
            } else {
                // Fallback to file path (you should update this to your actual path)
                i1 = new ImageIcon("icons/atm.jpg");
            }
        } catch (Exception e) {
            System.out.println("Image not found, using default background");
        }
        
        JLabel background;
        if (i1 != null) {
            Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_SMOOTH);
            ImageIcon i3 = new ImageIcon(i2);
            background = new JLabel(i3);
        } else {
            // Create a plain background if image fails to load
            background = new JLabel();
            background.setBackground(Color.DARK_GRAY);
            background.setOpaque(true);
        }
        
        background.setBounds(0, 0, 960, 1080);
        add(background);
        
        l1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 22));
        
        b1 = new JButton("DEPOSIT");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Raleway", Font.BOLD, 14));
        
        b2 = new JButton("BACK");
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.setFont(new Font("Raleway", Font.BOLD, 14));
        
        setLayout(null);
        
        l1.setBounds(170, 350, 450, 35);
        background.add(l1);
        
        t1.setBounds(170, 420, 350, 35);
        background.add(t1);
        
        b1.setBounds(390, 588, 150, 35);
        background.add(b1);
        
        b2.setBounds(390, 633, 150, 35);
        background.add(b2);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        setSize(960, 1080);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == b1) {
        String amount = t1.getText().trim();
        
        if (amount.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the amount you want to deposit");
            return;
        }
        
        try {
            // Validate amount is a positive number
            int depositAmount = Integer.parseInt(amount);
            
            // ✅ ADDED: Minimum deposit check
            if (depositAmount < 500) {
                JOptionPane.showMessageDialog(null, "Minimum deposit amount is 500 TK");
                return;
            }
            
            if (depositAmount <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a positive amount");
                return;
            }
            
            // Format date for SQL
            java.util.Date currentDate = new java.util.Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = sdf.format(currentDate);
            
            Conn c1 = new Conn();
            
            // Check connection
            if (c1.s == null || c1.s.isClosed()) {
                JOptionPane.showMessageDialog(null, "Database connection failed");
                return;
            }
            
            // Insert deposit transaction
            String query = "INSERT INTO bank (pin, date, mode, amount) VALUES (?, ?, ?, ?)";
            
            try {
                // Use PreparedStatement to avoid SQL injection and handle data types properly
                PreparedStatement pstmt = c1.c.prepareStatement(query);
                pstmt.setString(1, pin);
                pstmt.setString(2, formattedDate);
                pstmt.setString(3, "Deposit");
                pstmt.setInt(4, depositAmount);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "BDT. " + depositAmount + " Deposited Successfully");
                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Deposit failed. Please try again.");
                }
                
                pstmt.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        
    } else if (ae.getSource() == b2) {
        setVisible(false);
        new Transactions(pin).setVisible(true);
    }
}
    
    public static void main(String[] args) {
        new Deposit("").setVisible(true);
    }
}