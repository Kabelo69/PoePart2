/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registration;

/**
 *
 * @author RC_Student_lab
 */
import javax.swing.JOptionPane;

public class Registration {
    public static void main(String[] args) {
        Login login = new Login();
        MessageStorage storage = new MessageStorage();

        // Registration
        login.firstName = JOptionPane.showInputDialog("Enter First Name:");
        login.surname = JOptionPane.showInputDialog("Enter Last Name:");
        login.userName = JOptionPane.showInputDialog("Enter Username:");
        login.password = JOptionPane.showInputDialog("Enter Password:");
        login.cellPhone = JOptionPane.showInputDialog("Enter cellphone number:");
        JOptionPane.showMessageDialog(null, login.registerUser());

        while (!login.checkUsername() || !login.checkPasswordComplexity()) {
            JOptionPane.showMessageDialog(null, "Try to register again!");
            login.userName = JOptionPane.showInputDialog("Enter Username:");
            login.password = JOptionPane.showInputDialog("Enter Password:");
            login.cellPhone = JOptionPane.showInputDialog("Enter cellphone number:");
            JOptionPane.showMessageDialog(null, login.registerUser());
        }

        // Login
        login.enteredUserName = JOptionPane.showInputDialog("Enter Username:");
        login.enteredPassword = JOptionPane.showInputDialog("Enter Password:");
        login.enteredCell = JOptionPane.showInputDialog("Enter cellphone number:");
        JOptionPane.showMessageDialog(null, login.returnLoginStatus());

        while (!login.loginUser()) {
            JOptionPane.showMessageDialog(null, "Try to login again.");
            login.enteredUserName = JOptionPane.showInputDialog("Enter Username:");
            login.enteredPassword = JOptionPane.showInputDialog("Enter Password:");
            login.enteredCell = JOptionPane.showInputDialog("Enter cellphone number:");
            JOptionPane.showMessageDialog(null, login.returnLoginStatus());
        }

        // Menu
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");
        int total = Integer.parseInt(JOptionPane.showInputDialog("How many messages would you like to send?"));
        int count = 0;

        while (true) {
            String menu = JOptionPane.showInputDialog("""
                Please select an option:
                1) Send Messages
                2) Show Recently Sent Messages
                3) Quit
            """);

            switch (menu) {
                case "1":
                    if (count >= total) {
                        JOptionPane.showMessageDialog(null, "Message limit reached.");
                        break;
                    }
                    String recipient = JOptionPane.showInputDialog("Enter recipient number (include + country code):");
                    if (!Message.isValidRecipient(recipient)) {
                        JOptionPane.showMessageDialog(null, "Invalid recipient format.");
                        break;
                    }

                    String msg = JOptionPane.showInputDialog("Enter message (max 250 chars):");
                    if (!Message.isValidMessage(msg)) {
                        JOptionPane.showMessageDialog(null, "Message too long.");
                        break;
                    }

                    Message m = new Message(recipient, msg);
                    String action = JOptionPane.showInputDialog("""
                        Choose message option:
                        1) Send
                        2) Disregard
                        3) Store for later
                    """);

                    switch (action) {
                        case "1" -> {
                            m.send();
                            storage.addMessage(m);
                            count++;
                            JOptionPane.showMessageDialog(null, "Message sent!\n" + m.getMessageDetails());
                        }
                        case "2" -> {
                            m.disregard();
                            JOptionPane.showMessageDialog(null, "Message disregarded.");
                        }
                        case "3" -> {
                            m.store();
                            storage.addMessage(m);
                            count++;
                            JOptionPane.showMessageDialog(null, "Message stored.\n" + m.getMessageDetails());
                        }
                        default -> JOptionPane.showMessageDialog(null, "Invalid action.");
                    }
                    break;

                case "2":
                    JOptionPane.showMessageDialog(null, "Coming Soon.");
                    break;

                case "3":
                    JOptionPane.showMessageDialog(null, "Exiting. Total messages: " + storage.getTotalMessages());
                    storage.saveMessagesToJson("messages.json");
                    return;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid menu option.");
            }
        }
    }
}