package com.booklink.ui.panel.content.book.bookregister;

import com.booklink.controller.BookController;
import com.booklink.controller.CategoryController;
import com.booklink.model.book.BookRegisterDto;
import com.booklink.model.categories.Categories;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpinnerDateModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class BookRegisterDialog extends JDialog {

    private JLabel imagePreviewLabel;
    private JComboBox<String> categoryComboBox1;
    private JComboBox<String> categoryComboBox2;
    private CategoryController controller = new CategoryController();
    private BookController bookController = new BookController();
    private List<String> categories;

    public BookRegisterDialog(JFrame parent) {
        super(parent, "책 등록", true);
        setSize(1440, 800);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        categories = controller.findAllCategoryNames();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(15, 2, 10, 10)); // Adjusted for the number of fields

        panel.add(new JLabel("Title:"));
        JTextField titleField = new JTextField();
        panel.add(titleField);

        panel.add(new JLabel("Author:"));
        JTextField authorField = new JTextField();
        panel.add(authorField);

        panel.add(new JLabel("Publication Date:"));
        JSpinner dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        panel.add(dateSpinner);

        panel.add(new JLabel("Summary:"));
        JTextArea summaryField = new JTextArea(3, 20);
        panel.add(new JScrollPane(summaryField));

        panel.add(new JLabel("Description:"));
        JTextArea descriptionField = new JTextArea(3, 20);
        panel.add(new JScrollPane(descriptionField));

        panel.add(new JLabel("Price:"));
        JTextField priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel("Publisher:"));
        JTextField publisherField = new JTextField();
        panel.add(publisherField);


        String[] array = categories.stream()
                .toArray(i -> new String[i]);
        panel.add(new JLabel("Category 1:"));
        categoryComboBox1 = new JComboBox<>(array);
        panel.add(categoryComboBox1);

        panel.add(new JLabel("Category 2:"));
        categoryComboBox2 = new JComboBox<>(array);
        panel.add(categoryComboBox2);

        // Add image URL field and preview
        panel.add(new JLabel("Image URL:"));
        JTextField imageUrlField = new JTextField();
        panel.add(imageUrlField);

        imagePreviewLabel = new JLabel();
        imagePreviewLabel.setHorizontalAlignment(JLabel.CENTER);
        imagePreviewLabel.setVerticalAlignment(JLabel.CENTER);
        imagePreviewLabel.setPreferredSize(new Dimension(200, 200));
        panel.add(new JLabel("Image Preview:"));
        panel.add(imagePreviewLabel);

        // Add a submit button
        JButton submitButton = new JButton("등록");
        submitButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            LocalDate publicationDate = LocalDate.parse(dateEditor.getFormat().format(dateSpinner.getValue()));
            String summary = summaryField.getText();
            String description = descriptionField.getText();
            Integer price = Integer.parseInt(priceField.getText());
            String publisher = publisherField.getText();
            String category1 = categoryComboBox1.getSelectedItem().toString();
            String category2 = categoryComboBox2.getSelectedItem().toString();
            String imageUrl = imageUrlField.getText();

            if (category1.equals(category2)) {
                JOptionPane.showMessageDialog(this, "카테고리가 중복되었습니다. 다른 카테고리를 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
                return; // Stop further execution
            }

            BookRegisterDto dto = new BookRegisterDto(title, author,
                    publicationDate, summary, description, price,
                    publisher, category1, category2, imageUrl);
            try {
                bookController.registerBookWithCategories(dto);
                JOptionPane.showMessageDialog(this, "도서 등록이 성공적으로 완료되었습니다.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                JOptionPane.showMessageDialog(this, "이미 같은 제목의 도서가 있습니다.",
                        "Fail", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(submitButton);

        // Add a preview button
        JButton previewButton = new JButton("미리보기");
        previewButton.addActionListener(e -> updateImagePreview(imageUrlField.getText()));
        panel.add(previewButton);

        add(panel);
    }

    private void updateImagePreview(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            ImageIcon imageIcon = new ImageIcon(url);
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(imagePreviewLabel.getWidth(), imagePreviewLabel.getHeight(),
                    Image.SCALE_SMOOTH);
            imagePreviewLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            imagePreviewLabel.setIcon(null);
            JOptionPane.showMessageDialog(this, "Invalid image URL", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

