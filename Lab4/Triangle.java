import java.awt.Color;
import java.awt.Point;
import java.lang.Math;

public class Triangle
{
    private Point a, b, c;
    private Color color;

    public Triangle(Point a, Point b, Point c, Color color)
    {
         this.a = a;
         this.b = b;
         this.c = c;
         this.color = color;
    }
    public Point getVertexA()
    {
         return a;
    }
    public Point getVertexB()
    {
         return b;
    }
    public Point getVertexC()
    {
         return c;
    }  
    public Color getColor()
    {
         return color;
    }
    public void setColor(Color c)
    {
         this.color = c;
    }
    public double getArea()
    {
         double area = 0.5*Math.abs(a.x*(b.y-c.y)+b.x*(c.y-a.y)+c.x*(a.y-b.y));
         return area;
    }
    public double getPerimeter()
    {
         double peri = a.distance(b) + b.distance(c) + c.distance(a);
         return peri;
    }
    public void translate(Point p)
    {
         a.translate(p.x, p.y);
         b.translate(p.x, p.y);
         c.translate(p.x, p.y);
    }
}
