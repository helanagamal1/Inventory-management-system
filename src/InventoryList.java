
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryList {

    private GridLayout gridLayout;
    private JPanel table;
    private InventoryDatabase database;

    public InventoryList(InventoryDatabase database) {
        this.database = database;

        JFrame frame = new JFrame("Inventory Management System");
        frame.setLayout(new BorderLayout());
        frame.setSize(1200, 700);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//when you press the close tap, the project will stop running

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(50, 50, 20, 50));
        top.setBackground(null);

        JLabel title = new JLabel("Inventory Dashboard");
        title.setFont(new Font("Calibri", Font.BOLD, 35));
        title.setHorizontalAlignment(JLabel.CENTER);
        top.add(title, BorderLayout.CENTER);

        JButton newItem = GUI.button("Add Item", new Color(88, 179, 88));
        newItem.addActionListener(e -> {
            try {
                new OpenItem(new Item(), "new", database, InventoryList.this);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        });
        top.add(newItem, BorderLayout.EAST);

        frame.add(top, BorderLayout.NORTH);


        JButton manageCategories = GUI.button("Manage Categories", new Color(255, 165, 0));
        manageCategories.addActionListener(e -> new CategoryList(database));
        top.add(manageCategories, BorderLayout.WEST);


        gridLayout = new GridLayout(8, 1, 0, 0);
        table = new JPanel(gridLayout);
        table.setBackground(Color.white);

        try {
            refresh(database.getItems());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
        frame.add(sp, BorderLayout.CENTER);

        frame.setVisible(true);



//        JButton addCategory = GUI.button("Add Category", new Color(255, 165, 0));
//        addCategory.addActionListener(e -> new OpenCategory(db, () -> refreshCategories()));
//        top.add(addCategory, BorderLayout.WEST);



    }




    public void refresh(ArrayList<Item> items) {
        table.removeAll();
        table.repaint();
        table.revalidate();

        int rows = Math.max(items.size(), 8);
        gridLayout.setRows(rows);

        for (Item item : items) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
            panel.setBackground(item.getItemID() % 2 == 0 ? Color.decode("#e5e5e5") : null);
            panel.setPreferredSize(new Dimension(100, 55));

            panel.add(GUI.label(item.getName()));
            panel.add(GUI.label("Qty: " + item.getQuantity()));
            panel.add(GUI.label("Price: $" + item.getPrice()));
            panel.add(GUI.label("Category: " + item.getCategory()));

            JButton view = GUI.button("View", new Color(88, 179, 99));
            view.addActionListener(e -> {
                try {
                    new OpenItem(item, "view", database, InventoryList.this);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            });
            panel.add(view);

            JButton edit = GUI.button("Edit", new Color(63, 134, 196));
            edit.addActionListener(e -> {
                try {
                    new OpenItem(item, "edit", database, InventoryList.this);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            });
            panel.add(edit);

            JButton delete = GUI.button("Delete", new Color(208, 11, 3));
            delete.addActionListener(e -> {
                try {
                    database.deleteItem(item);
                    refresh(database.getItems());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            });
            panel.add(delete);

            table.add(panel);
        }
    }
}
