package com.mohamadelhusseiny;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class ATMGUI extends JFrame {
    private JTextField displayField;
    private JButton[] numberButtons;
    private JButton withdrawButton, depositButton, balanceButton;
    private double balance = 1000.0;

    public ATMGUI() {
        setTitle("ATM Simulator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        displayField = new JTextField();
        displayField.setFont(new Font("Arial", Font.PLAIN, 24));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setEditable(false);
        mainPanel.add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 3, 10, 10));

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(Integer.toString(i));
            numberButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            numberButtons[i].addActionListener(new NumberButtonListener());
            buttonPanel.add(numberButtons[i]);
        }

        withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        balanceButton = new JButton("Balance");

        withdrawButton.addActionListener(new WithdrawButtonListener());
        depositButton.addActionListener(new DepositButtonListener());
        balanceButton.addActionListener(new BalanceButtonListener());

        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(balanceButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private class NumberButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            String currentText = displayField.getText();
            String newText = currentText + source.getText();
            displayField.setText(newText);
        }
    }

    private class WithdrawButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String amountText = displayField.getText();
            if (!amountText.isEmpty()) {
                double amount = Double.parseDouble(amountText);
                if (amount > 0 && amount <= balance) {
                    balance -= amount;
                    updateDisplay();
                    JOptionPane.showMessageDialog(ATMGUI.this, "Withdrawal successful. Remaining balance: $" +
                            formatBalance(balance));
                } else {
                    JOptionPane.showMessageDialog(ATMGUI.this, "Invalid withdrawal amount. Please check your balance.");
                }
            }
        }
    }

    private class DepositButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String amountText = displayField.getText();
            if (!amountText.isEmpty()) {
                double amount = Double.parseDouble(amountText);
                if (amount > 0) {
                    balance += amount;
                    updateDisplay();
                    JOptionPane.showMessageDialog(ATMGUI.this, "Deposit successful. New balance: $" +
                            formatBalance(balance));
                } else {
                    JOptionPane.showMessageDialog(ATMGUI.this, "Invalid deposit amount. Please enter a valid amount.");
                }
            }
        }
    }

    private class BalanceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(ATMGUI.this, "Your current balance is: $" + formatBalance(balance));
        }
    }

    private void updateDisplay() {
        displayField.setText("");
    }

    private String formatBalance(double balance) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(balance);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATMGUI atmGUI = new ATMGUI();
            atmGUI.setVisible(true);
        });
    }
}

