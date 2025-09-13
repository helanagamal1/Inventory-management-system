
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class OpenCategory {

    public OpenCategory(InventoryDatabase db) {
        JFrame frame = new JFrame("Add Category");
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(2, 2, 10, 10));
        frame.setLocationRelativeTo(null);

        JTextField nameField = GUI.textField("");
        frame.add(GUI.label("Category Name:"));
        frame.add(nameField);

        JButton save = GUI.button("Save", new Color(88, 179, 88));
        save.addActionListener(e -> {
            try {
                db.insertCategory(new Category(nameField.getText()));
                JOptionPane.showMessageDialog(frame, "Category added.");
                frame.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, ex.toString());
            }
        });
        frame.add(save);

        frame.setVisible(true);
    }
}
