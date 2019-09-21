import java.util.*;

public class Polygon {
   private ArrayList<Point> vertices;

   public Polygon(List<Point> points)
   {
      vertices = new ArrayList<Point>();
      for (int i=0; i<points.size(); i++) {
         vertices.add(points.get(i));
      }
   }
   public List<Point> getPoints()
   {
      return vertices;

   }
   public double perimeter() {
      int size = vertices.size();
      if (size < 2)
         return 0.0;
      double peri = 0;
      for (int i=0; i < size -1; i++)
      { 
         peri += vertices.get(i).getDistance(vertices.get(i+1));
      }
      peri += vertices.get(0).getDistance(vertices.get(size-1));
      return peri;
   }
} 
