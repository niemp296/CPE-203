import java.util.*;

public class Circle{
   private final double radius;
   private final Point center;
   
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
}
