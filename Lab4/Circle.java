import java.awt.Color;
import java.awt.Point;
import java.lang.Math;

public class Circle implements Shape
{
    private Point center;
    private double radius;
    private Color color;

    public Circle(double r, Point p, Color c)
    {
        this.radius = r;
        this.center = p;
        this.color = c;
    }
    public double getRadius()
    {
        return radius;
    }
    public void setRadius(double r)
    {
        this.radius = r;
    }
    public Point getCenter()
    {
        return center;
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
        double area = Math.PI*radius*radius;
        return area;
    }
    public double getPerimeter()
    {
        double peri = 2*Math.PI*radius;
        return peri;
    }
    public void translate(Point p)
    {  
        center.translate(p.x, p.y);
    }
}
