import java.util.Map;

public class Activity implements Action
{
    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Activity(Entity entity, WorldModel world, ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = 0;
    }
    public void executeAction(EventScheduler scheduler)
    {
       if (entity instanceof MinerFull)
           ((MinerFull)entity).executeActivity(world, imageStore, scheduler);
       else if (entity instanceof MinerNotFull)
           ((MinerNotFull)entity).executeActivity(world, imageStore, scheduler);
       else if (entity instanceof Ore)
           ((Ore)entity).executeActivity(world, imageStore, scheduler);
       else if (entity instanceof OreBlob)
           ((OreBlob)entity).executeActivity(world, imageStore, scheduler);

       else if (entity instanceof Quake)
           ((Quake)entity).executeActivity(world, imageStore, scheduler);
       else if (entity instanceof Vein)
           ((Vein)entity).executeActivity(world, imageStore, scheduler);
       else
       {
           throw new UnsupportedOperationException(
                   String.format("executeActivityAction not supported for %s", entity.getClass()));
        }
    }
}
