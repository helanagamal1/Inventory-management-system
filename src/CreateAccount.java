
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class CreateAccount {
    public CreateAccount(InventoryDatabase db) {
        JFrame frame = new JFrame("Login");
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(3, 2, 10, 10));
        frame.setLocationRelativeTo(null);

       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField username = GUI.textField("");
        JPasswordField password = new JPasswordField();

        frame.add(GUI.label("Username:")); frame.add(username);
        frame.add(GUI.label("Password:")); frame.add(password);

        JButton login = GUI.button("Login", new Color(63, 134, 196));
        login.addActionListener(e -> {
            try {
                if (db.validateUser(username.getText(), new String(password.getPassword()))) {
                    frame.dispose();
                    new InventoryList(db);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, ex.toString());
            }
        });
        frame.add(login);

        frame.setVisible(true);
    }
}
