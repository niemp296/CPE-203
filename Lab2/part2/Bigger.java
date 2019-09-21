public class Bigger {
     private final Circle c;
     private final Rectangle r;
     private final Polygon p;
     public Bigger(Circle cir, Rectangle rec, Polygon poly)
     {
           this.c = new Circle(cir.getCenter(), cir.getRadius());
           this.r = new Rectangle(rec.getTopLeft(), rec.getBottomRight());
           this.p = new Polygon(poly.getPoints());
     }
     public  double WhichIsBigger(Circle c, Rectangle r, Polygon p) {
           double circle = c.perimeter();
           double rectangle = r.perimeter();
           double polygon = p.perimeter();
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
