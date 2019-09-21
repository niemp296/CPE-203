public class Bigger {
     public static double WhichIsBigger(Circle c, Rectangle r, Polygon p) {
           double circle = Util.perimeter(c);
           double rectangle = Util.perimeter(r);
           double polygon = Util.perimeter(p);
           double largest = circle;
           if (circle < rectangle)
           {
               if (rectangle < polygon)
                   {largest = polygon;}
               else if (rectangle > polygon)
                   {largest = rectangle;}
           }
           else if (circle > rectangle)
           {
               if (circle < polygon)
                   {largest = polygon;}
           }
           return largest;
     }
}
