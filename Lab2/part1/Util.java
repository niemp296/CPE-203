public class Util{
   public static double perimeter(Circle c){
      double peri;
      peri = 2*Math.PI*(c.getRadius());
      return peri;
   }
   public static double perimeter(Rectangle rec) {
      double peri,l,w;
      Point p1 = new Point(rec.getBottomRight().getX(), rec.getTopLeft().getY());
      Point p2 = new Point(rec.getTopLeft().getX(), rec.getBottomRight().getY());
      l = rec.getTopLeft().getDistance(p1);
      w = rec.getTopLeft().getDistance(p2);
      peri = 2*(l+w);
      return peri;
   }
   public static double perimeter(Polygon poly) {
      int size = poly.getPoints().size();
  
      if (size < 2)
         return 0.0;
      double peri = 0;
      for(int i = 0; i < size - 1; i++)
      {
          peri += poly.getPoints().get(i).getDistance(poly.getPoints().get(i+1));
      }
      peri += poly.getPoints().get(0).getDistance(poly.getPoints().get(size-1));
         
      return peri;
      
  }



}
