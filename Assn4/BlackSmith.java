import processing.core.PImage;

import java.util.List;

public class BlackSmith extends Entity{

    public BlackSmith(String id, Point position,
                      List<PImage> images)
    {
        super(id, position, images,0,0,0,0);
    }
    /*
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

        throw new UnsupportedOperationException(
                String.format("getAnimationPeriod not supported for %s BlackSmith"));

    }
    public void nextImage()
    {
        imageIndex = (imageIndex + 1) % images.size();
    }
    public PImage getCurrentImage()
    {

        return images.get(imageIndex);
    }*/
}
