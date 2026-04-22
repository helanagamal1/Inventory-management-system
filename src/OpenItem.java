

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class OpenItem {
//    private InventoryDatabase db;
//    private JComboBox<String> categoryBox;

    public OpenItem(Item item, String mode, InventoryDatabase db, InventoryList list) throws SQLException {

//        this.db = db;
//        categoryBox = new JComboBox<>();
     //   refreshCategories(); //  refrsh dropdown


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
        //form.add(categoryBox);


        //last update : ### to fix the add categoty as it  didn't appear
        JButton addCategory = GUI.button("+", new Color(39, 82, 163));
        addCategory.setToolTipText("Add new category");
        addCategory.addActionListener(e -> new OpenCategory(db));

        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.add(categoryBox, BorderLayout.CENTER);
        categoryPanel.add(addCategory, BorderLayout.EAST);
        form.add(categoryPanel);
// last update end

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
            addCategory.setEnabled(false);
        }

        frame.setVisible(true);
    }



//    private void refreshCategories() {
//        categoryBox.removeAllItems();
//        try {
//            ArrayList<Category> categories = db.getCategories();
//            for (Category cat : categories) categoryBox.addItem(cat.getName());
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Failed to load categories: " + ex.getMessage());
//        }
//    }

}
