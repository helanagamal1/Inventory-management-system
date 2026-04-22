import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryList {

    public CategoryList(InventoryDatabase db) {
        JFrame frame = new JFrame("Manage Categories");
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> categoryList = new JList<>(model);
        JScrollPane scroll = new JScrollPane(categoryList);
        frame.add(scroll, BorderLayout.CENTER);

        try {
            ArrayList<Category> categories = db.getCategories();
            for (Category cat : categories) model.addElement(cat.getName());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error loading categories: " + ex.getMessage());
        }

        JButton delete = GUI.button("Delete", new Color(208, 11, 3));
        delete.addActionListener(e -> {
            String selected = categoryList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(frame, "Select a category to delete.");
                return;
            }

            try {
                if (db.isCategoryUsed(selected)) {
                    JOptionPane.showMessageDialog(frame, "Cannot delete. Category is used by items.");
                    return;
                }

                db.deleteCategory(selected);
                model.removeElement(selected);
                JOptionPane.showMessageDialog(frame, "Category deleted.");

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        frame.add(delete, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
