package com.example.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.example.database.DatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

public class UserForm {
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton saveButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPanel mainPanel;
    private JButton loginButton;

    public UserForm() {
        //Register user
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                char[] password = passwordField.getPassword();

                if(DatabaseManager.userExists(username)){
                    JOptionPane.showMessageDialog(mainPanel,"User already Exists.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    DatabaseManager.saveUser(username, password);
                    JOptionPane.showMessageDialog(mainPanel,"User registered successfully");
                }

            }
        });

        //Login user
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                char[] password = passwordField.getPassword();

                if(authenticateUser(username, password)){
                    JOptionPane.showMessageDialog(mainPanel, "Login successful!");
                    openLibraryInterface(username);
                }else{
                    JOptionPane.showMessageDialog(mainPanel,"Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean authenticateUser(String username, char[] password){
        String storedHashedPassword= DatabaseManager.getHashedPassword(username);
        return storedHashedPassword != null && BCrypt.checkpw(new String(password), storedHashedPassword);
    }

    private void openLibraryInterface(String username) {
        JFrame libraryFrame = new JFrame("Library Interface");
        libraryFrame.setContentPane(new LibraryInterface(username).getMainPanelLib());
        libraryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        libraryFrame.pack();
        libraryFrame.setVisible(true);

        SwingUtilities.getWindowAncestor(mainPanel).dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("User Form");
        frame.setContentPane(new UserForm().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
