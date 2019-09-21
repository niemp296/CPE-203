import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerNotFull implements Moveable{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;
    private final String QUAKE_KEY = "quake";

    public MinerNotFull(String id, int resourceLimit,
                        Point position, int actionPeriod, int animationPeriod,
                        List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = 0;
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
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore),
                actionPeriod);
        scheduler.scheduleEvent(this,
                new Animation(this, 0), getAnimationPeriod());
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
            Point nextPos = nextPosition(world, target.position());

            if (position().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
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
