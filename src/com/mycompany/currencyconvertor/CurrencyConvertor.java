package com.mycompany.currencyconvertor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CurrencyConvertor extends JFrame {

    private static final String[] CURRENCIES = {"USD ($)","EGP (ج.م)","EUR (€)","GBP (£)","CNY (¥)","AED (د.إ)","KWD (د.ك)","CHF (Fr)"};
    private static final double[] RATES = {1.0, 50.86, 0.96, 0.80, 7.30, 3.67, 0.31, 0.90};

    public CurrencyConvertor() {
        setLayout(new BorderLayout(2, 2));

        JPanel panel = new JPanel(new GridLayout(6, 4, 40, 40));
        panel.setBackground(new Color(242, 241, 228));
        panel.setSize(new Dimension(600, 500));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel jlblFrom = new JLabel("From Currency:");
        jlblFrom.setForeground(new Color(6, 70, 66));
        jlblFrom.setFont(new Font("Arial", Font.BOLD, 15));
        JComboBox jcboFrom = new JComboBox(CURRENCIES);
        jcboFrom.setBounds(200, 50, 150, 30);
        
        JButton jbtSwitch = new JButton();
        jbtSwitch.setBounds(370, 50, 50, 30);
        
        
          try {
            ImageIcon switchIcon = new ImageIcon("C:\\Users\\ALATTAL\\Documents\\NetBeansProjects\\currencyconvertor\\arrow.png");
            Image scaledImage = switchIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            jbtSwitch.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.out.println("Error loading icon: " + e.getMessage());
        }


        JLabel jlblTo = new JLabel("To Currency:");
        jlblTo.setForeground(new Color(6, 70, 66));
        jlblTo.setFont(new Font("Arial", Font.BOLD, 15));
        
        JComboBox jcboTo = new JComboBox(CURRENCIES);
        jcboTo.setBounds(600, 50, 150, 30);

        
        JLabel jlblAmount = new JLabel("Amount:");
        jlblAmount.setForeground(new Color(6, 70, 66));
        jlblAmount.setFont(new Font("Arial", Font.BOLD, 15));
        
        JTextField jtfAmount = new JTextField();
        jtfAmount.setBackground(new Color(216, 226, 236));
        jtfAmount.setFont(new Font("Arial", Font.PLAIN, 18));
        jtfAmount.setBounds(200, 120, 150, 30);


        
        jtfAmount.addKeyListener(new KeyAdapter() {
         
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                }

                if (jtfAmount.getText().length() >= 7) {
                    e.consume();
                }
            }
        });

        JButton jbtConvert = new JButton("Convert");
        jbtConvert.setForeground(new Color(6, 70, 66));
        jbtConvert.setFont(new Font("Arial", Font.BOLD, 15));
        jbtConvert.setSize(new Dimension(100, 30));

       
        JLabel jlblResult = new JLabel("Converted Amount ");
        jlblResult.setForeground(new Color(6, 70, 66));
        jlblResult.setFont(new Font("Arial", Font.BOLD, 15));

        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(jlblFrom);
        panel.add(jcboFrom);
        panel.add(new JLabel());
         panel.add(new JLabel());
        panel.add(jbtSwitch);
        panel.add(new JLabel());
        panel.add(jlblTo);
        panel.add(jcboTo);
        panel.add(new JLabel());
        panel.add(jlblAmount);
        panel.add(jtfAmount);
        panel.add(new JLabel());
        panel.add(jbtConvert);
        panel.add(jlblResult);
        add(panel, BorderLayout.CENTER);

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 160));
        westPanel.setSize(new Dimension(150, 0));
        westPanel.setBackground(new Color(6, 70, 66));
        JLabel jlblHome = new JLabel("Currency Converter");
        jlblHome.setForeground(Color.WHITE);
        westPanel.add(jlblHome, BorderLayout.NORTH);

        add(westPanel, BorderLayout.WEST);

        jbtSwitch.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent e) {
                int fromIndex = jcboFrom.getSelectedIndex();
                int toIndex = jcboTo.getSelectedIndex();
                jcboFrom.setSelectedIndex(toIndex);
                jcboTo.setSelectedIndex(fromIndex);
            }
        });

        jbtConvert.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                String fromCurrency = (String) jcboFrom.getSelectedItem();
                String toCurrency = (String) jcboTo.getSelectedItem();
                String amountText = jtfAmount.getText();

                try {
                    double amount = Double.parseDouble(amountText);

                    if (amount == 0) {
                        JOptionPane.showMessageDialog(null, "Please enter a value.");
                        return;
                    }

                    double convertedAmount = convertCurrency(fromCurrency, toCurrency, amount);
                    jlblResult.setText(" " + Math.round(convertedAmount * 100.0) / 100.0 + " " + toCurrency);

                  
                    jcboFrom.setSelectedIndex(0);
                    jcboTo.setSelectedIndex(0);
                    jtfAmount.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });
    }

    private double convertCurrency(String fromCurrency, String toCurrency, double amount) {
          fromCurrency = fromCurrency.split(" ")[0];
        toCurrency = toCurrency.split(" ")[0];

        int fromIndex = -1;
        int toIndex = -1;
         for (int i = 0; i < CURRENCIES.length; i++) {
            if (CURRENCIES[i].startsWith(fromCurrency)) {
                fromIndex = i;
            }
            if (CURRENCIES[i].startsWith(toCurrency)) {
                toIndex = i;
            }
        }
        double rate = RATES[toIndex] / RATES[fromIndex];
        return amount * rate;
    }


    public static void main(String[] args) {
        CurrencyConvertor frame = new CurrencyConvertor();
        frame.setTitle("Currency Convertor");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
}