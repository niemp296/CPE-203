import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class OreBlob extends Moveable {

    private final String QUAKE_KEY = "quake";

    public OreBlob(String id, Point position,
                   int actionPeriod, int animationPeriod, List<PImage> images)
    {

        super(id,position,images,0,0,actionPeriod,animationPeriod);
    }
    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> blobTarget = world.findNearest(position(), Vein.class);
        long nextPeriod = actionPeriod;

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().position();

            if (moveTo(world, blobTarget.get(), scheduler))
            {
                Quake quake = new Quake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += actionPeriod;
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                nextPeriod);
    }
    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        super.scheduleActions(scheduler, world, imageStore);
        scheduler.scheduleEvent(this,
                new Animation(this, 0), getAnimationPeriod());
    }
    public boolean moveTo(WorldModel world,
                                 Entity target, EventScheduler scheduler)
    {
        if (position().adjacent(target.position()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            return super.moveTo(world, target, scheduler);
        }
    }

    public Point nextPosition(WorldModel world, Point destPos)
    {
        return super.nextPosition(world,destPos);

    }

}
