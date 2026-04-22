
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Register {
    public Register(InventoryDatabase db) {
        JFrame frame = new JFrame("Register");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2, 10, 10));
        frame.setLocationRelativeTo(null);

       // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField username = GUI.textField("");
        JPasswordField password = new JPasswordField();
        JPasswordField confirm = new JPasswordField();

        frame.add(GUI.label("Username:")); frame.add(username);
        frame.add(GUI.label("Password:")); frame.add(password);
        frame.add(GUI.label("Confirm Password:")); frame.add(confirm);

        JButton register = GUI.button("Register", new Color(88, 179, 88));
        register.addActionListener(e -> {

            //add this 5 lines to prevent empty strings
            String usernameText = username.getText().trim();
            String passwordText = new String(password.getPassword()).trim();
            if (usernameText.isEmpty() || passwordText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Username and password cannot be empty.");
                return;
            }

            if (!new String(password.getPassword()).equals(new String(confirm.getPassword()))) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match.");
                return;
            }
            User user = new User();
            user.setUsername(username.getText());
            user.setPassword(new String(password.getPassword()));

                try {
                    db.insertUser(user);
                    JOptionPane.showMessageDialog(frame, "Account created.");
                    frame.dispose();
                    CreateAccount loginPage = new CreateAccount(db);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, ex.toString());
                }

        });
        frame.add(register);




      //add button goes to Login Page
        JButton loginButton = new JButton("Already have an account?");
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setForeground(Color.BLUE);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginButton.addActionListener(e -> {
                    frame.dispose();//close regier frame (register form/page)
                    CreateAccount loginForm = new CreateAccount(db);

                });
        frame.add(loginButton);


        frame.setVisible(true);

    }
}
