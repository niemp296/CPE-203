public class View {
    private String sessionID;
    private String productID;
    private int price;
    public View(String productID, int price)
    {
        //this.sessionID = sessionID;
        this.productID = productID;
        this.price = price;
    }
/*
    public String getSession()
    {
        return sessionID;
    }
*/
    public String getProduct()
    {
        return productID;
    }
    public int getPrice()
    {
        return price;
    }

}
