public class Buy {
    private String productID;
    private int price;
    private int quantity;
    public Buy(String productID, int price, int quantity)
    {
        this.productID = productID;
        this.price = price;
        this.quantity = quantity;
    }
    public String getProduct()
    {
        return productID;
    }
    public int getPrice()
    {
        return price;
    }
    public int getQuantity()
    {
        return quantity;
    }
}
