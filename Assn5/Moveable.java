import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public abstract class Moveable extends ActionEntity {

    private PathingStrategy strategy = new AStarPathingStragety();

    public Moveable(String id, Point position,
                    List<PImage> images, int resourceLimit, int resourceCount,
                    int actionPeriod, int animationPeriod)
        {
        super(id, position, images, resourceLimit, resourceCount,actionPeriod, animationPeriod);
        }
    boolean moveTo(WorldModel world, Entity target, EventScheduler scheduler){
        Point nextPos = nextPosition(world, target.position());

        if (!position().equals(nextPos))
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
    public Point nextPosition(WorldModel world, Point destPos)
    {
        List<Point> points = strategy.computePath(position(), destPos,
                p -> !world.isOccupied(p) && world.withinBounds(p), (p1, p2) -> p1.adjacent(p2), PathingStrategy.CARDINAL_NEIGHBORS);
        if (points.size() == 0)
            return position();

        return points.get(0);

    }


}
