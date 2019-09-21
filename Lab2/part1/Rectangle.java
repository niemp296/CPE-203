public class Rectangle{
   private final Point topLeft;
   private final Point bottomRight;

   public Rectangle(Point tLeft, Point bRight) {
      this.topLeft = new Point(tLeft.getX(), tLeft.getY());
      this.bottomRight = new Point(bRight.getX(), bRight.getY());
   }
   public Point getTopLeft(){
      return topLeft;
   }   
   public Point getBottomRight(){
      return bottomRight;
   }
}

