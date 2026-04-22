
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

        frame.add(GUI.label("Username:")); frame.add(username); //Make label and add in GUI
        frame.add(GUI.label("Password:")); frame.add(password);//Make label and add in GUI

        JButton login = GUI.button("Login", new Color(90, 162, 222));//Make button
        login.addActionListener(e -> { //do what happens when you click on login
            try {
                if (db.validateUser(username.getText(), new String(password.getPassword())))
                { /*                  read from user.
                     getText() in Swing is conceptually similar to next() or nextInt()
                     from the Scanner class but used in a GUI context.    */
                    frame.dispose();//: Closes the frame(GUI page) and frees up memory tied to it.
                    new InventoryList(db);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, ex.toString());
            }
        });
        frame.add(login); //add login in GUI .. to make it appear //or  attach button to frame.
        //-Without add(), the component exists in memory but isn't part of the visual hierarchy.
        //- note: you can Add listener BEFORE adding loginButton to frame


        //connect login page to register
        //1- make button sign up (Register class/GUI form is created before )
        //2- open login : new CreateAcc(db)
        JButton signUp = GUI.button("SignUp", new Color(90, 162, 222));//Make button
        signUp.addActionListener(e ->
        {
            try {
                Register registerPage= new Register(db);
                frame.dispose();
            }
            catch (Exception ex)
            {            JOptionPane.showMessageDialog(frame, ex.toString());     }
        } );
        frame.add(signUp);

        frame.setVisible(true);// Render frame and its contents
    }
}
