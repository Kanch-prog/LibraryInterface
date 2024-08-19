package com.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import com.example.database.DatabaseManager;

public class LibraryInterface {
    private JPanel mainPanelLib;
    private JLabel welcomeLabel;
    private JTextField searchField;
    private JButton searchButton;
    private JList<String> searchResultsList;
    private JTextField titleField;
    private JTextField authorField;
    private JButton addButton;
    private JLabel statusLabel;
    private JLabel searchLabel;
    private JLabel titleLable;
    private JLabel authorLabel;

    public LibraryInterface(String username) {
        System.out.println("Username received: " + username);
        welcomeLabel.setText("Welcome, " + username + "!");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText();
                List<String> results = DatabaseManager.searchBooks(searchQuery);
                updateSearchResults(results);
                if (results.isEmpty()) {
                    statusLabel.setText("No books found.");
                    statusLabel.setForeground(Color.RED);
                } else {
                    statusLabel.setText("Search completed.");
                    statusLabel.setForeground(Color.BLUE);
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                DatabaseManager.addBook(title, author);
                statusLabel.setText("Book added successfully!");
                statusLabel.setForeground(Color.GREEN);
            }
        });
    }

    private void updateSearchResults(List<String> results) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String book : results) {
            listModel.addElement(book);
        }
        searchResultsList.setModel(listModel);
    }

    public JPanel getMainPanelLib() {
        return mainPanelLib;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Library Interface");
        frame.setContentPane(new LibraryInterface("User").getMainPanelLib());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
