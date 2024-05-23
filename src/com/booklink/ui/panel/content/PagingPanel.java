package com.booklink.ui.panel.content;

import javax.swing.*;
import java.awt.*;

public class PagingPanel extends JPanel {
    public PagingPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new FlowLayout());
        setBackground(Color.GRAY);

        for (int i = 1; i <= 10; i++) {
            JButton pageButton = new JButton(String.valueOf(i));
            pageButton.setPreferredSize(new Dimension(50, 30));
            add(pageButton);
        }
        //
    }
}
