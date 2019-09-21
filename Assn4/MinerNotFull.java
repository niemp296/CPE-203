import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerNotFull extends Moveable{

    //private PathingStrategy strategy = new SingleStepPathingStrategy();
    //private PathingStrategy strategy = new AStarPathingStragety();

    public MinerNotFull(String id, int resourceLimit,
                        Point position, int actionPeriod, int animationPeriod,
                        List<PImage> images)
    {
        super(id, position,images,resourceLimit,0,actionPeriod,animationPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget = world.findNearest(position(),
                Ore.class);
        if (!notFullTarget.isPresent() ||
                !moveTo(world, notFullTarget.get(), scheduler) ||
                !transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    actionPeriod);
        }
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
            resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
           return super.moveTo(world, target, scheduler);
        }
    }
/*
    public Point nextPosition(WorldModel world,
                              Point destPos)
    {
        int horiz = Integer.signum(destPos.x - position().x);
        Point newPos = new Point(position().x + horiz,
                position().y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - position().y);
            newPos = new Point(position().x,
                    position().y + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = position();
            }
        }

        return newPos;
    }
*/
    public Point nextPosition(WorldModel world, Point destPos)
    {
        return super.nextPosition(world, destPos);
    }


    public boolean transform(WorldModel world,
                                    EventScheduler scheduler, ImageStore imageStore)
    {
        if (resourceCount >= resourceLimit)
        {
            MinerFull miner = new MinerFull(id, resourceLimit,
                    position, actionPeriod, animationPeriod,
                    images);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

}
