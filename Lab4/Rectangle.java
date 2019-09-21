import java.awt.Color;
import java.awt.Point;
import java.lang.Math;

public class Rectangle implements Shape
{
   private double width;
   private double height;
   private Point topLeftCorner;
   private Color color;

   public Rectangle(double w, double h, Point topLeft, Color c)
   {
       this.width = w;
       this.height = h;
       this.topLeftCorner = topLeft;
       this.color = c;
   }
   public double getWidth()
   {
       return width;
   }
   public void setWidth(double w)
   {
       this.width = w;
   }  
   public double getHeight()
   {
       return height;
   }
   public void setHeight(double h)
   {
       this.height = h;
   }
   public Point getUpperLeft()
   {
       return topLeftCorner;
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
       double area = width*height;
       return area; 
   }
   public double getPerimeter()
   {
       double peri = 2*(width+height);
       return peri;
   }
   public void translate(Point p)
   {
       topLeftCorner.translate(p.x, p.y);
   }
} 
