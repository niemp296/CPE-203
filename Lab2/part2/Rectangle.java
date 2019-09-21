public class Rectangle{
   private Point topLeft;
   private Point bottomRight;

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
   public double perimeter() {
      double peri,l, w;
      Point p1 = new Point(bottomRight.getX(), topLeft.getY());
      Point p2 = new Point(topLeft.getX(), bottomRight.getY());
      l = topLeft.getDistance(p1);
      w = topLeft.getDistance(p2);
      peri = 2*(l+w);
      return peri;
   }
}
