
public class Item {
    private int itemID;
    private String name;
    private int quantity;
    private double price;
    private String category;


    //defult constructor by me .. hence java default constructor has no existence
   /* Item item = new Item();
      System.out.println(item.getQuantity()); // prints 0
      System.out.println(item.getName());    //prints null
    */
    public Item() {}
    // this is default conrtuctor (as it takes no-arguments)  by me
    // Andd it prints default values since it is default (#default values are assigned to all fields by java )

    //#that’s exactly what a default constructor does when no values are explicitly set

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
