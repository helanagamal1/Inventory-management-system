import java.sql.*;
//print rows or data in rs
public class ItemPrinter {
    private static final String URL = "jdbc:mysql://localhost/inventory_db";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASS);
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM items");
        ) {
            System.out.println(" Items in Inventory:");
            while (rs.next()) {
                int id = rs.getInt("ItemID");
                String name = rs.getString("Name");
                int quantity = rs.getInt("Quantity");
                double price = rs.getDouble("Price");
                String category = rs.getString("Category");

                System.out.printf("ID: %d | Name: %s | Qty: %d | Price: %.2f | Category: %s%n",
                        id, name, quantity, price, category);
            }
        } catch (SQLException e) {
            System.err.println(" Database error: " + e.getMessage());
        }
    }
}
