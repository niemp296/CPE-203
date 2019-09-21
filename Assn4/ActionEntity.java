import processing.core.PImage;

import java.util.List;

public abstract class ActionEntity extends Entity{

    public ActionEntity(String id, Point position,
                        List<PImage> images, int resourceLimit, int resourceCount,
                        int actionPeriod, int animationPeriod)
    {
        super(id, position, images, resourceLimit, resourceCount,actionPeriod, animationPeriod);
    }
    protected abstract void executeActivity(WorldModel world,
                         ImageStore imageStore, EventScheduler scheduler);
    void scheduleActions(EventScheduler scheduler,
                        WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                actionPeriod);
    }
}
