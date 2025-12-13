package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.util.*;

public class Signup extends JFrame implements ActionListener{
    
    JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15,l16;
    JTextField t1,t2,t3,t4,t5,t6,t7,t8;
    JRadioButton r1,r2,r3,r4,r5;
    JButton b;
    JDateChooser dateChooser;
    
    String formno; // Store form number as string
    
    Signup(){
        
        setTitle("NEW ACCOUNT APPLICATION FORM");
        
        ImageIcon i1 = new ImageIcon(("D:/Apache/bank management system/src/icons/lgy.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l11 = new JLabel(i3);
        l11.setBounds(150, 0, 100, 100);
        add(l11);
        
        // Calculate next form number from database
        formno = getNextFormNumber();
        
        l1 = new JLabel("APPLICATION FORM NO. "+ formno);
        l1.setFont(new Font("Raleway", Font.BOLD, 22));
        l1.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
      
        l2 = new JLabel("Page 1: Personal Details");
        l2.setFont(new Font("Raleway", Font.BOLD, 22));
        
        l3 = new JLabel("Name:");
        l3.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l4 = new JLabel("Father's Name:");
        l4.setFont(new Font("Raleway", Font.BOLD, 20));
        
        // ADDED: Mother's Name label
        l16 = new JLabel("Mother's Name:");
        l16.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l5 = new JLabel("Date of Birth:");
        l5.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l6 = new JLabel("Gender:");
        l6.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l7 = new JLabel("Email Address:");
        l7.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l8 = new JLabel("Marital Status:");
        l8.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l9 = new JLabel("Address:");
        l9.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l10 = new JLabel("City:");
        l10.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l12 = new JLabel("Zip Code:");
        l12.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l13 = new JLabel("State:");
        l13.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l14 = new JLabel("Date");
        l14.setFont(new Font("Raleway", Font.BOLD, 14));
        
        l15 = new JLabel("Year");
        l15.setFont(new Font("Raleway", Font.BOLD, 14));
        
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 14));
        
        t2 = new JTextField();
        t2.setFont(new Font("Raleway", Font.BOLD, 14));
        
        // ADDED: Mother's Name text field
        t8 = new JTextField();
        t8.setFont(new Font("Raleway", Font.BOLD, 14));
        
        t3 = new JTextField();
        t3.setFont(new Font("Raleway", Font.BOLD, 14));
        
        t4 = new JTextField();
        t4.setFont(new Font("Raleway", Font.BOLD, 14));
        
        t5 = new JTextField();
        t5.setFont(new Font("Raleway", Font.BOLD, 14));
        
        t6 = new JTextField();
        t6.setFont(new Font("Raleway", Font.BOLD, 14));
        
        t7 = new JTextField();
        t7.setFont(new Font("Raleway", Font.BOLD, 14));
        
        b = new JButton("Next");
        b.setFont(new Font("Raleway", Font.BOLD, 14));
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        
        r1 = new JRadioButton("Male");
        r1.setFont(new Font("Raleway", Font.BOLD, 14));
        r1.setBackground(Color.WHITE);
        
        r2 = new JRadioButton("Female");
        r2.setFont(new Font("Raleway", Font.BOLD, 14));
        r2.setBackground(Color.WHITE);
        
        ButtonGroup groupgender = new ButtonGroup();
        groupgender.add(r1);
        groupgender.add(r2);
        
        r3 = new JRadioButton("Married");
        r3.setFont(new Font("Raleway", Font.BOLD, 14));
        r3.setBackground(Color.WHITE);
        
        r4 = new JRadioButton("Unmarried");
        r4.setFont(new Font("Raleway", Font.BOLD, 14));
        r4.setBackground(Color.WHITE);
        
        r5 = new JRadioButton("Other");
        r5.setFont(new Font("Raleway", Font.BOLD, 14));
        r5.setBackground(Color.WHITE);
        
        ButtonGroup groupstatus = new ButtonGroup();
        groupstatus.add(r3);
        groupstatus.add(r4);
        groupstatus.add(r5);
        
        dateChooser = new JDateChooser();
        dateChooser.setForeground(new Color(105, 105, 105));
        
        setLayout(null);
        
        // Center the form number at the top middle
        l1.setBounds(0, 20, 850, 40);
        add(l1);
        
        // Center "Page 1: Personal Details" below the form number
        l2.setBounds(0, 80, 850, 30);
        l2.setHorizontalAlignment(SwingConstants.CENTER);
        add(l2);
        
        l3.setBounds(100,140,100,30);
        add(l3);
        
        t1.setBounds(300,140,400,30);
        add(t1);
        
        l4.setBounds(100,190,200,30);
        add(l4);
        
        t2.setBounds(300,190,400,30);
        add(t2);
        
        // ADDED: Mother's Name
        l16.setBounds(100,240,200,30);
        add(l16);
        
        t8.setBounds(300,240,400,30);
        add(t8);
        
        l5.setBounds(100,290,200,30);
        add(l5);
        
        dateChooser.setBounds(300, 290, 400, 30);
        add(dateChooser);
        
        l6.setBounds(100,340,200,30);
        add(l6);
        
        r1.setBounds(300,340,60,30);
        add(r1);
        
        r2.setBounds(450,340,90,30);
        add(r2);
        
        l7.setBounds(100,390,200,30);
        add(l7);
        
        t3.setBounds(300,390,400,30);
        add(t3);
        
        l8.setBounds(100,440,200,30);
        add(l8);
        
        r3.setBounds(300,440,100,30);
        add(r3);
        
        r4.setBounds(450,440,100,30);
        add(r4);
        
        r5.setBounds(635,440,100,30);
        add(r5);
        
        l9.setBounds(100,490,200,30);
        add(l9);
        
        t4.setBounds(300,490,400,30);
        add(t4);
        
        l10.setBounds(100,540,200,30);
        add(l10);
        
        t5.setBounds(300,540,400,30);
        add(t5);
        
        l12.setBounds(100,590,200,30);
        add(l12);
        
        t6.setBounds(300,590,400,30);
        add(t6);
        
        l13.setBounds(100,640,200,30);
        add(l13);
        
        t7.setBounds(300,640,400,30);
        add(t7);
        
        b.setBounds(620,700,80,30);
        add(b);
        
        b.addActionListener(this); 
        
        getContentPane().setBackground(Color.WHITE);
        
        setSize(850,800);
        setLocation(500,120);
        setVisible(true);
    }
    
    // Method to get next form number from database
    private String getNextFormNumber() {
        try {
            Conn c = new Conn();
            // Get the maximum form number from signup table
            ResultSet rs = c.s.executeQuery("SELECT MAX(formno) FROM signup");
            int nextFormNo = 1; // Start from 1 if no records exist
            
            if (rs.next()) {
                int maxFormNo = rs.getInt(1);
                if (maxFormNo > 0) {
                    nextFormNo = maxFormNo + 1;
                }
            }
            return String.valueOf(nextFormNo);
        } catch (Exception e) {
            e.printStackTrace();
            return "1"; // Default to 1 if there's an error
        }
    }
    
    public void actionPerformed(ActionEvent ae){
        
        String name = t1.getText();
        String fname = t2.getText();
        String mname = t8.getText(); // ADDED: Mother's name
        String dob = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
        String gender = null;
        if(r1.isSelected()){ 
            gender = "Male";
        }else if(r2.isSelected()){ 
            gender = "Female";
        }
            
        String email = t3.getText();
        String marital = null;
        if(r3.isSelected()){ 
            marital = "Married";
        }else if(r4.isSelected()){ 
            marital = "Unmarried";
        }else if(r5.isSelected()){ 
            marital = "Other";
        }
           
        String address = t4.getText();
        String city = t5.getText();
        String zipcode = t6.getText();
        String state = t7.getText();
        
        try{
            if(t6.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Fill all the required fields");
            }else{
                Conn c1 = new Conn();
                // Insert with the calculated form number
                String q1 = "insert into signup values('"+formno+"','"+name+"','"+fname+"','"+mname+"','"+dob+"','"+gender+"','"+email+"','"+marital+"','"+address+"','"+city+"','"+zipcode+"','"+state+"')";
                c1.s.executeUpdate(q1);
                
                new Signup2(formno).setVisible(true);
                setVisible(false);
            }
            
        }catch(Exception e){
             e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args){
        new Signup().setVisible(true);
    }
}