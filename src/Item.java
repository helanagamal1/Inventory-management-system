
public class Item {
    private int itemID;
    private String name;
    private int quantity;
    private double price;
    private String category;

    public Item() {}

    public int getItemID() { return itemID; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }

    public void setItemID(int itemID) { this.itemID = itemID; }
    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
}
