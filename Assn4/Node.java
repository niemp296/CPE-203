public class Node {
    //Distance from start (g)
    //Heuristic distance (h)
    //Total distance (f = g + h)
    private int h,g,f;
    private Node PriorSquare;
    private Point point;

    public Node(Point point, int g, int h, Node PriorSquare)
    {
        this.point = point;
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.PriorSquare = PriorSquare;
    }
    public Point getPoint()
    {
        return this.point;
    }
    public int getG()
    {
        return g;
    }
    public void setG(int g)
    {
        this.g = g;
    }
    public int getH()
    {
        return h;
    }
    public void setH(int h)
    {
        this.h = h;
    }
    public int getF()
    {
        return f;
    }
    public void setF(int f)
    {
        this.f = f;
    }
    public Node getPriorSquare()
    {
        return PriorSquare;
    }
    public String toString(){return "( " + point.x + "," + point.y + " )"; }

}
