public class Point {
   private double x;
   private double y;

   public Point(double x, double y) {
      this.x = x;
      this.y = y;
   }

   public double getX() {
      return x;
   }

   public double getY() {
      return y;
   }
   public double getRadius()
   {
      double radius;
      radius = Math.sqrt(x*x + y*y);
      return radius;
   }
   public double getAngle()
   {
      double angleR;
      angleR = Math.atan2(x,y);
      return angleR;
   }
   public Point rotate90()
   {
      double x,y;
      x = -(this.y);
      y = this.x;
      return (new Point(x,y));
   }
   public double  getDistance(Point p)
   {
      double x1 = p.getX();
      double y1 = p.getY();
      double distance;
      distance = Math.sqrt(Math.pow(x1-this.x,2)+Math.pow(y1-this.y,2));
      return distance;
   }

}

   
