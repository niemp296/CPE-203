import java.util.*;

public class Circle{
   private double radius;
   private Point center;
   
   public Circle(Point c, double r){
       this.center = new Point(c.getX(), c.getY());
       this.radius = r;
   }
   public Point getCenter(){
       return center;
   }
   public double getRadius(){
       return radius;
   }
   public double perimeter() {
       return 2*Math.PI*radius;
   }
}
