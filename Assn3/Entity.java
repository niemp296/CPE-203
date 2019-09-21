import processing.core.PImage;

import java.util.List;

public abstract class Entity{

   protected String id;
   protected Point position;
   protected List<PImage> images;
   protected int imageIndex;
   protected int resourceLimit;
   protected int resourceCount;
   protected int actionPeriod;
   protected int animationPeriod;

   public Entity(String id, Point position,
                 List<PImage> images, int resourceLimit, int resourceCount,
                 int actionPeriod, int animationPeriod)
   {
      this.id = id;
      this.position = position;
      this.images = images;
      this.imageIndex = 0;
      this.resourceLimit = resourceLimit;
      this.resourceCount = resourceCount;
      this.actionPeriod = actionPeriod;
      this.animationPeriod = animationPeriod;
   }

   public Point position()
   {
      return position;
   }
   public void setPosition(Point position)
   {
      this.position = position;
   }
   public int getAnimationPeriod()
   {
      if ((this instanceof MinerFull)
         || (this instanceof MinerNotFull)
         || (this instanceof OreBlob)
         || (this instanceof Quake))
            return animationPeriod;
      else
      {
            throw new UnsupportedOperationException(
                    String.format("getAnimationPeriod not supported for %s",
                            getClass().toString()));
      }
   }
   public void nextImage()
   {
      imageIndex = (imageIndex + 1) % images.size();
   }
   public PImage getCurrentImage()
   {
      return images.get(imageIndex);
   }

}