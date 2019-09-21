public class Point{
   private double varX;
   private double varY;

   public Point(double x, double y)
   {
      varX = x;
      varY = y;
   }
   public double getX()
   {
      return varX;
   }
   public double getY()
   {
      return varY;
   }
   public double getRadius()
   {      
      double radius;
      radius = Math.sqrt(varX*varX + varY*varY);
      return radius;
   }    
   public double getAngle()
   {
      double angleR;
      angleR = Math.atan2(varX, varY);
      return angleR;
   }
   public Point rotate90()
   {
      double newX, newY;
      newX = -(this.varY);
      newY = this.varX;
      return (new Point(newX,newY));
   }
 }

