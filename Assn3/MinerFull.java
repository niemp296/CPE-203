import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerFull extends Moveable{



    public MinerFull(String id, int resourceLimit,
                   Point position, int actionPeriod, int animationPeriod,
                   List<PImage> images)
    {

        super(id, position,images,resourceLimit,resourceLimit,actionPeriod,animationPeriod);
    }

    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(position(),
                BlackSmith.class);

        if (fullTarget.isPresent() &&
                moveTo(world, fullTarget.get(), scheduler))
        {
            transform(world, scheduler, imageStore);
        }
        else
        {
            scheduler.scheduleEvent(this,
                    new Activity(this, world, imageStore),
                    actionPeriod);
        }
    }
    public void scheduleActions(EventScheduler scheduler,
                                WorldModel world, ImageStore imageStore)
    {
        super.scheduleActions(scheduler,world,imageStore);
        scheduler.scheduleEvent(this, new Animation(this, 0),
                getAnimationPeriod());
    }
    public boolean moveTo(WorldModel world,
                              Entity target, EventScheduler scheduler)
    {
        if (position().adjacent(target.position()))
        {
            return true;
        }
        else
        {
            return super.moveTo(world,target,scheduler);
        }
    }
    public Point nextPosition(WorldModel world,
                                  Point destPos)
    {
        int horiz = Integer.signum(destPos.x - position().x);
        Point newPos = new Point(position().x + horiz,
                position().y);

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.y - position().y);
            newPos = new Point(position().x,
                    position().y + vert);

            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = position();
            }
        }

        return newPos;
    }

    public void transform(WorldModel world,
                              EventScheduler scheduler, ImageStore imageStore)
    {
        MinerNotFull miner = new MinerNotFull(id, resourceLimit,
                position, actionPeriod, animationPeriod, images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }
}
