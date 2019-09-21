import processing.core.PImage;

public interface Entity{
   Point position();
   void setPosition(Point position);
   PImage getCurrentImage();
   int getAnimationPeriod();
   void nextImage();

}