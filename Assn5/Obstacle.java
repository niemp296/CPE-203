import processing.core.PImage;

import java.util.List;

public class Obstacle extends Entity {
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    public Obstacle(String id, Point position,
                      List<PImage> images)
    {

        super(id, position, images,0,0,0,0);
    }

}
