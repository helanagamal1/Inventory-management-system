

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class OpenItem {

    public OpenItem(Item item, String mode, InventoryDatabase db, InventoryList list) throws SQLException {
        JFrame frame = new JFrame("Item Details");
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);


        JPanel form = new JPanel(new GridLayout(6, 2, 15, 15));
        form.setBackground(Color.white);
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        form.add(GUI.label("Item ID:"));
        JLabel idLabel = GUI.label(String.valueOf(item.getItemID()));
        form.add(idLabel);

        form.add(GUI.label("Name:"));
        JTextField nameField = GUI.textField(item.getName());
        form.add(nameField);

        form.add(GUI.label("Quantity:"));
        JTextField qtyField = GUI.textField(String.valueOf(item.getQuantity()));
        form.add(qtyField);

        form.add(GUI.label("Price:"));
        JTextField priceField = GUI.textField(String.valueOf(item.getPrice()));
        form.add(priceField);

        form.add(GUI.label("Category:"));
        JComboBox<String> categoryBox = new JComboBox<>();
        ArrayList<Category> categories = db.getCategories();
        for (Category cat : categories) categoryBox.addItem(cat.getName());
        categoryBox.setSelectedItem(item.getCategory());
        form.add(categoryBox);

        JButton cancel = GUI.button("Cancel", new Color(208, 11, 3));
        cancel.addActionListener(e -> frame.dispose());
        form.add(cancel);

        JButton save = GUI.button("Save", new Color(88, 179, 88));
        form.add(save);

        frame.add(form, BorderLayout.CENTER);

        if (mode.equals("new")) {
            idLabel.setText(String.valueOf(db.getNextItemID()));
            save.addActionListener(e -> {
                try {
                    item.setItemID(Integer.parseInt(idLabel.getText()));
                    item.setName(nameField.getText());
                    item.setQuantity(Integer.parseInt(qtyField.getText()));
                    item.setPrice(Double.parseDouble(priceField.getText()));
                    item.setCategory((String) categoryBox.getSelectedItem());
                    db.insertItem(item);
                    frame.dispose();
                    list.refresh(db.getItems());
                } catch (SQLException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            });
        } else if (mode.equals("edit")) {
            save.addActionListener(e -> {
                try {
                    item.setName(nameField.getText());
                    item.setQuantity(Integer.parseInt(qtyField.getText()));
                    item.setPrice(Double.parseDouble(priceField.getText()));
                    item.setCategory((String) categoryBox.getSelectedItem());
                    db.updateItem(item);
                    frame.dispose();
                    list.refresh(db.getItems());
                } catch (SQLException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            });
        } else if (mode.equals("view")) {
            save.setVisible(false);
            cancel.setVisible(false);
            nameField.setEditable(false);
            qtyField.setEditable(false);
            priceField.setEditable(false);
            categoryBox.setEnabled(false);
        }

        frame.setVisible(true);
    }
}
