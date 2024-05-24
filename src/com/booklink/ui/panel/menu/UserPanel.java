package com.booklink.ui.panel.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPanel extends JPanel {
    private JButton loginButton;
    private JButton signUpButton;

    public UserPanel() {
        setPreferredSize(new Dimension(400, 50));
        setLayout(null);

        loginButton = new JButton("Login");
        loginButton.setSize(200, 50);

        signUpButton = new JButton("Sign Up");
        signUpButton.setSize(200, 50);

        add(loginButton).setBounds(0, 0, 100, 50);
        add(signUpButton).setBounds(100, 0, 100, 50);

        // Add ActionListener to loginButton
        loginButton.addActionListener(e -> {
            new LoginForm(); // Open the LoginForm
        });

    }
}