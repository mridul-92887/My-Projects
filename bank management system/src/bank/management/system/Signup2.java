package bank.management.system;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Signup2 extends JFrame implements ActionListener {
    
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13;
    JButton b;
    JRadioButton r1, r2, r3, r4;
    JTextField t1, t2, t3;
    JTextArea addressArea;
    JComboBox c1, c2, c3, c4;
    String formno;
    
    Signup2(String formno) {
        
        ImageIcon i1 = new ImageIcon(("D:/Apache/bank management system/src/icons/lgy.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l14 = new JLabel(i3);
        l14.setBounds(150, 0, 100, 100);
        add(l14);
        
        this.formno = formno;
        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 2");
        
        l1 = new JLabel("Page 2: Additional Details");
        l1.setFont(new Font("Raleway", Font.BOLD, 22));
        
        l2 = new JLabel("Religion:");
        l2.setFont(new Font("Raleway", Font.BOLD, 18));
        
        // REMOVED: Category field
        
        l3 = new JLabel("Income:");
        l3.setFont(new Font("Raleway", Font.BOLD, 18));
        
        l4 = new JLabel("Educational");
        l4.setFont(new Font("Raleway", Font.BOLD, 18));
        
        l11 = new JLabel("Qualification:");
        l11.setFont(new Font("Raleway", Font.BOLD, 18));
        
        l5 = new JLabel("Occupation:");
        l5.setFont(new Font("Raleway", Font.BOLD, 18));
        
        // CHANGED: PAN to NID
        l6 = new JLabel("NID Number:");
        l6.setFont(new Font("Raleway", Font.BOLD, 18));
        
        // CHANGED: Aadhar to Phone Number
        l7 = new JLabel("Phone Number:");
        l7.setFont(new Font("Raleway", Font.BOLD, 18));
        
        // ADDED: Permanent Address
        l8 = new JLabel("Permanent Address:");
        l8.setFont(new Font("Raleway", Font.BOLD, 18));
        
        l9 = new JLabel("Senior Citizen:");
        l9.setFont(new Font("Raleway", Font.BOLD, 18));
        
        l10 = new JLabel("Existing Account:");
        l10.setFont(new Font("Raleway", Font.BOLD, 18));
        
        l12 = new JLabel("Form No:");
        l12.setFont(new Font("Raleway", Font.BOLD, 13));
        
        l13 = new JLabel(formno);
        l13.setFont(new Font("Raleway", Font.BOLD, 13));
        
        b = new JButton("Next");
        b.setFont(new Font("Raleway", Font.BOLD, 14));
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        
        // NID Number field (was PAN)
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 14));
        
        // Phone Number field (was Aadhar)
        t2 = new JTextField();
        t2.setFont(new Font("Raleway", Font.BOLD, 14));
        
        // Permanent Address field (NEW)
        addressArea = new JTextArea(3, 20);
        addressArea.setFont(new Font("Raleway", Font.PLAIN, 14));
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        JScrollPane addressScroll = new JScrollPane(addressArea);
        addressScroll.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        r1 = new JRadioButton("Yes");
        r1.setFont(new Font("Raleway", Font.BOLD, 14));
        r1.setBackground(Color.WHITE);
        
        r2 = new JRadioButton("No");
        r2.setFont(new Font("Raleway", Font.BOLD, 14));
        r2.setBackground(Color.WHITE);
        
        r3 = new JRadioButton("Yes");
        r3.setFont(new Font("Raleway", Font.BOLD, 14));
        r3.setBackground(Color.WHITE);
        
        r4 = new JRadioButton("No");
        r4.setFont(new Font("Raleway", Font.BOLD, 14));
        r4.setBackground(Color.WHITE);
        
        // Religion options for Bangladesh
        String religion[] = {"Islam", "Hinduism", "Buddhism", "Christianity", "Other"};
        c1 = new JComboBox(religion);
        c1.setBackground(Color.WHITE);
        c1.setFont(new Font("Raleway", Font.BOLD, 14));
        
        // REMOVED: Category combo box
        
        // Income options updated for Bangladesh
        String income[] = {"Null", "<30,000 TK", "<50,000 TK", "<70,000 TK", 
                          "Upto 1,00,000 TK", "Above 2,00,000 TK"};
        c2 = new JComboBox(income);
        c2.setBackground(Color.WHITE);
        c2.setFont(new Font("Raleway", Font.BOLD, 14));
        
        String education[] = {"SSC/HSC", "Diploma", "Graduate", "Post-Graduate", 
                             "Masters", "PhD", "Others"};
        c3 = new JComboBox(education);
        c3.setBackground(Color.WHITE);
        c3.setFont(new Font("Raleway", Font.BOLD, 14));
        
        String occupation[] = {"Student", "Job Holder", "Business", "Freelancer", 
                              "Housewife", "Retired", "Others"};
        c4 = new JComboBox(occupation);
        c4.setBackground(Color.WHITE);
        c4.setFont(new Font("Raleway", Font.BOLD, 14));
       
        setLayout(null);
        
        l12.setBounds(700, 10, 60, 30);
        add(l12);
        
        l13.setBounds(760, 10, 60, 30);
        add(l13);
        
        l1.setBounds(280, 30, 600, 40);
        add(l1);
        
        // Religion
        l2.setBounds(100, 120, 100, 30);
        add(l2);
        
        c1.setBounds(350, 120, 320, 30);
        add(c1);
        
        // Income (moved up since category removed)
        l3.setBounds(100, 170, 100, 30);
        add(l3);
        
        c2.setBounds(350, 170, 320, 30);
        add(c2);
        
        // Education
        l4.setBounds(100, 220, 150, 30);
        add(l4);
        
        c3.setBounds(350, 220, 320, 30);
        add(c3);
        
        l11.setBounds(100, 240, 150, 30);
        add(l11);
        
        // Occupation
        l5.setBounds(100, 290, 150, 30);
        add(l5);
        
        c4.setBounds(350, 290, 320, 30);
        add(c4);
        
        // NID Number
        l6.setBounds(100, 340, 150, 30);
        add(l6);
        
        t1.setBounds(350, 340, 320, 30);
        add(t1);
        
        // Phone Number
        l7.setBounds(100, 390, 180, 30);
        add(l7);
        
        t2.setBounds(350, 390, 320, 30);
        add(t2);
        
        // Permanent Address
        l8.setBounds(100, 440, 200, 30);
        add(l8);
        
        addressScroll.setBounds(350, 440, 320, 60);
        add(addressScroll);
        
        // Senior Citizen
        l9.setBounds(100, 520, 150, 30);
        add(l9);
        
        r1.setBounds(350, 520, 100, 30);
        add(r1);
        
        r2.setBounds(460, 520, 100, 30);
        add(r2);
        
        // Existing Account
        l10.setBounds(100, 570, 180, 30);
        add(l10);
        
        r3.setBounds(350, 570, 100, 30);
        add(r3);
        
        r4.setBounds(460, 570, 100, 30);
        add(r4);
        
        b.setBounds(570, 640, 100, 30);
        add(b);
        
        b.addActionListener(this);
        
        getContentPane().setBackground(Color.WHITE);
        
        setSize(850, 750);
        setLocation(500, 120);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        String religion = (String)c1.getSelectedItem(); 
        String income = (String)c2.getSelectedItem();
        String education = (String)c3.getSelectedItem();
        String occupation = (String)c4.getSelectedItem();
        
        String nid = t1.getText().trim();
        String phone = t2.getText().trim();
        String permanentAddress = addressArea.getText().trim();
        
        String scitizen = "";
        if(r1.isSelected()) { 
            scitizen = "Yes";
        } else if(r2.isSelected()) { 
            scitizen = "No";
        }
           
        String eaccount = "";
        if(r3.isSelected()) { 
            eaccount = "Yes";
        } else if(r4.isSelected()) { 
            eaccount = "No";
        }
        
        try {
            // Validation: Check if phone and address are filled
            if(phone.equals("") || permanentAddress.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill Phone Number and Permanent Address");
                return;
            }
            
            // Validate phone number (Bangladeshi format: 01XXXXXXXXX)
            if(!phone.matches("01[3-9][0-9]{8}")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid Bangladeshi phone number (01XXXXXXXXX)");
                return;
            }
            
            // Validate NID (Bangladeshi NID: 10 or 17 digits)
            if(!nid.equals("") && !nid.matches("\\d{10}|\\d{17}")) {
                JOptionPane.showMessageDialog(null, "NID must be 10 or 17 digits");
                return;
            }
            
            Conn c1 = new Conn();
            String q1 = "insert into signup2 values('" + formno + "','" + religion + "','" + 
                       income + "','" + education + "','" + occupation + "','" + 
                       nid + "','" + phone + "','" + scitizen + "','" + eaccount + "','" + 
                       permanentAddress + "')";
            c1.s.executeUpdate(q1);
            
            new Signup3(formno).setVisible(true);
            setVisible(false);
                
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        new Signup2("").setVisible(true);
    }
}