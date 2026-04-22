
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        InventoryDatabase db = new InventoryDatabase();
        new CreateAccount(db); // or new Register(db); //username: admin  pass:123
       // new Register(db);
    }
}
