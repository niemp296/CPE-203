import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Wyvern extends Moveable {

    private final String QUAKE_KEY = "quake";

    public Wyvern(String id, int resourceLimit,
                  Point position, int actionPeriod, int animationPeriod,
                  List<PImage> images)
    {

        super(id, position,images,resourceLimit,resourceLimit,actionPeriod,animationPeriod);
    }


    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {

        Optional<Entity> WyvernTarget1 = world.findNearest(position(), BlackSmith.class);
        Optional<Entity> WyvernTarget2 = world.findNearest(position(), Vein.class);


        long nextPeriod = actionPeriod;

        if (WyvernTarget1.isPresent())
        {
            Point tgtPos = WyvernTarget1.get().position();

            if (moveTo(world, WyvernTarget1.get(), scheduler))
            {
                Quake quake = new Quake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += actionPeriod;
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }


        else if (WyvernTarget2.isPresent() )
        {
            Point tgtPos = WyvernTarget2.get().position();

            if (moveTo(world, WyvernTarget2.get(), scheduler))
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
    public boolean moveTo(WorldModel world,
                          Entity target, EventScheduler scheduler)
    {
        if (position().adjacent(target.position()))
        {
            resourceCount += 1;
            //System.out.println("Move to protester");
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            return super.moveTo(world, target, scheduler);
        }
    }
    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        super.scheduleActions(scheduler,world,imageStore);
        scheduler.scheduleEvent(this, new Animation(this, 0),
                getAnimationPeriod());
    }
    public Point nextPosition(WorldModel world, Point destPos)
    {
        return super.nextPosition(world, destPos);

    }

}
