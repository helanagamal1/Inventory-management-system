
import java.sql.*;
import java.util.ArrayList;

public class InventoryDatabase {
    private String url = "jdbc:mysql://localhost/inventory_db";
    private String user = "root";
    private String pass = "";
    private Statement statement;

    public InventoryDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, pass);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    public ArrayList<Item> getItems() throws SQLException {
        ArrayList<Item> items = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM items;");
        while (rs.next()) {//if true : return values from current row ( rs.next() moves down one row.)
            Item item = new Item();//read the values (in 1 row ) and store it in item object.
            item.setItemID(rs.getInt("ItemID"));
            item.setName(rs.getString("Name"));
            item.setQuantity(rs.getInt("Quantity"));
            item.setPrice(rs.getDouble("Price"));
            item.setCategory(rs.getString("Category"));
            items.add(item);
        }
        return items;
    }

    public void insertItem(Item item) throws SQLException {
        String sql = "INSERT INTO items (ItemID, Name, Quantity, Price, Category) VALUES ('" +
                item.getItemID() + "','" + item.getName() + "','" + item.getQuantity() + "','" +
                item.getPrice() + "','" + item.getCategory() + "')";
        statement.execute(sql);
    }

    public void updateItem(Item item) throws SQLException {
        String sql = "UPDATE items SET Name='" + item.getName() + "', Quantity='" + item.getQuantity() +
                "', Price='" + item.getPrice() + "', Category='" + item.getCategory() +
                "' WHERE ItemID=" + item.getItemID();
        statement.execute(sql);
    }

    public void deleteItem(Item item) throws SQLException {
        statement.execute("DELETE FROM items WHERE ItemID=" + item.getItemID());
    }


    //Purpose : Generates the next available ItemID for inserting a new item.
    public int getNextItemID() throws SQLException { //used for Add Item Button
        ResultSet rs = statement.executeQuery("SELECT MAX(ItemID) AS maxID FROM items;");
        //If the table has rows :return the highest ItemID + 1.
        if (rs.next()) return rs.getInt("maxID") + 1;
       // If the table is empty (rs.next() is false): return 1 (1 is the id of first row)
        return 1;

    }

    public void insertUser(User user) throws SQLException {
        String sql = "INSERT INTO users (Username, Password) VALUES ('" +
                user.getUsername() + "','" + user.getPassword() + "')";
        statement.execute(sql);
    }

    //Checks if a user exists with the given username and password.
    public boolean validateUser(String username, String password) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE Username='" +
                username + "' AND Password='" + password + "'");
        return rs.next();//rs.next() acts as a boolean gate for authentication.
    }

    public ArrayList<Category> getCategories() throws SQLException {
        ArrayList<Category> categories = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT * FROM categories;");
        while (rs.next()) {
            categories.add(new Category(rs.getString("Name")));
        }
        return categories;
    }

    public void insertCategory(Category category) throws SQLException {
        statement.execute("INSERT INTO categories (Name) VALUES ('" + category.getName() + "')");
    }

    public boolean isCategoryUsed(String name) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT * FROM items WHERE Category='" + name + "'");
        return rs.next();
    }

    public void deleteCategory(String name) throws SQLException {
        statement.executeUpdate("DELETE FROM categories WHERE Name='" + name + "'");
    }


}
