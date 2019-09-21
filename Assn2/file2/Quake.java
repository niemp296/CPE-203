import processing.core.PImage;

import java.util.List;

public class Quake implements AnimationEntity{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;
    private final String QUAKE_ID = "quake";
    private final int QUAKE_ACTION_PERIOD = 1100;
    private final int QUAKE_ANIMATION_PERIOD = 100;
    private final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake(Point position,
                    List<PImage> images)
    {
        this.id = QUAKE_ID;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = 0;
        this.resourceCount = 0;
        this.actionPeriod = QUAKE_ACTION_PERIOD;
        this.animationPeriod = QUAKE_ANIMATION_PERIOD;
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
        return animationPeriod;
    }
    public void nextImage()
    {
        imageIndex = (imageIndex + 1) % images.size();
    }
    public PImage getCurrentImage()
    {
        return images.get(imageIndex);
    }
    public void executeActivity(WorldModel world,
                                     ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }
    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                actionPeriod);
        scheduler.scheduleEvent(this,
                new Animation(this, QUAKE_ANIMATION_REPEAT_COUNT),
                getAnimationPeriod());
    }
}
