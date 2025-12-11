package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;
import java.util.*;

class BalanceEnquiry extends JFrame implements ActionListener {

    JTextField t1, t2;
    JButton b1, b2, b3;
    JLabel l1, l2, l3, backgroundLabel;
    String pin;

    BalanceEnquiry(String pin) {
        this.pin = pin;

        ImageIcon i1 = new ImageIcon(("D:/Apache/bank management system/src/icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        backgroundLabel = new JLabel(i3);
        backgroundLabel.setBounds(0, 0, 960, 1080);
        add(backgroundLabel);

        l1 = new JLabel();
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));

        b1 = new JButton("BACK");

        setLayout(null);

        l1.setBounds(190, 350, 400, 35);
        backgroundLabel.add(l1);

        b1.setBounds(390, 633, 150, 35);
        backgroundLabel.add(b1);
        
        double balance = 0;
        try {
            Conn c1 = new Conn();
            ResultSet rs = c1.s.executeQuery("select * from bank where pin = '" + pin + "'");
            
            if (!rs.isBeforeFirst()) {
                l1.setText("No transactions found. Balance: BDT. 0");
            } else {
                while (rs.next()) {
                    String mode = rs.getString("mode");
                    String amountStr = rs.getString("amount");
                    
                    if (mode != null && amountStr != null) {
                        // Remove commas and parse as double
                        amountStr = amountStr.replace(",", "");
                        
                        try {
                            double amount = Double.parseDouble(amountStr);
                            
                            if (mode.equals("Deposit")) {
                                balance += amount;
                            } else if (mode.equals("Withdrawl")) {
                                balance -= amount;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid amount format: " + amountStr);
                        }
                    }
                }
                l1.setText("Your Current Account Balance is BDT. " + String.format("%.2f", balance));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            l1.setText("Error retrieving balance");
        }

        b1.addActionListener(this);

        setSize(960, 1080);
        setUndecorated(true);
        setLocation(500, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transactions(pin).setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEnquiry("").setVisible(true);
    }
}