public class Activity extends Action
{


    public Activity(Entity entity, WorldModel world, ImageStore imageStore)
    {

        super(entity, world, imageStore, 0);
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
       else if (entity instanceof Wyvern)
           ((Wyvern)entity).executeActivity(world, imageStore, scheduler);
       else
       {
           throw new UnsupportedOperationException(
                   String.format("executeActivityAction not supported for %s", entity.getClass()));
        }
    }
}
