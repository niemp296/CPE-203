import processing.core.PApplet;
import processing.core.PImage;

import java.util.*;

final class Action {
   public ActionKind kind;
   public Entity entity;
   public WorldModel world;
   public ImageStore imageStore;
   public int repeatCount;
   private final String BLOB_KEY = "blob";
   private final String BLOB_ID_SUFFIX = " -- blob";
   private final int BLOB_PERIOD_SCALE = 4;
   private final int BLOB_ANIMATION_MIN = 50;
   private final int BLOB_ANIMATION_MAX = 150;

   private final String ORE_ID_PREFIX = "ore -- ";
   private final int ORE_CORRUPT_MIN = 20000;
   private final int ORE_CORRUPT_MAX = 30000;
   private final String QUAKE_KEY = "quake";
   private final String ORE_KEY = "ore";


   public Action(ActionKind kind, Entity entity, WorldModel world,
                 ImageStore imageStore, int repeatCount) {
      this.kind = kind;
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
      this.repeatCount = repeatCount;

   }
   public void executeAction(EventScheduler scheduler)
   {
      switch (kind)
      {
         case ACTIVITY:
            executeActivityAction(scheduler);
            break;

         case ANIMATION:
            executeAnimationAction(scheduler);
            break;
      }
   }

   public void executeAnimationAction(EventScheduler scheduler)
   {
      this.entity.nextImage();

      if (this.repeatCount != 1)
      {
         scheduler.scheduleEvent(this.entity,
                 Functions.createAnimationAction(this.entity,
                         Math.max(this.repeatCount - 1, 0)),
                 this.entity.getAnimationPeriod());
      }
   }

   public void executeActivityAction(EventScheduler scheduler)
   {
      switch (this.entity.kind)
      {
         case MINER_FULL:
            executeMinerFullActivity(scheduler);
            break;

         case MINER_NOT_FULL:
            executeMinerNotFullActivity(scheduler);
            break;

         case ORE:
            executeOreActivity(scheduler);
            break;

         case ORE_BLOB:
            executeOreBlobActivity(scheduler);
            break;

         case QUAKE:
            executeQuakeActivity(scheduler);
            break;

         case VEIN:
            executeVeinActivity(scheduler);
            break;

         default:
            throw new UnsupportedOperationException(
                    String.format("executeActivityAction not supported for %s",
                            this.entity.kind));
      }
   }

   public void executeMinerFullActivity(EventScheduler scheduler)
   {
      Optional<Entity> fullTarget = world.findNearest(entity.position,
              EntityKind.BLACKSMITH);

      if (fullTarget.isPresent() &&
              moveToFull(entity, fullTarget.get(), scheduler))
      {
         transformFull(scheduler);
      }
      else
      {
         scheduler.scheduleEvent(entity,
                 world.createActivityAction(entity, world, imageStore),
                 entity.actionPeriod);
      }
   }

   public void executeMinerNotFullActivity(EventScheduler scheduler)
   {
      Optional<Entity> notFullTarget = world.findNearest(entity.position,
              EntityKind.ORE);

      if (!notFullTarget.isPresent() ||
              !moveToNotFull(entity, notFullTarget.get(), scheduler) ||
              !transformNotFull(scheduler))
      {
         scheduler.scheduleEvent(entity,
                 world.createActivityAction(entity, world, imageStore),
                 entity.actionPeriod);
      }
   }

   public void executeOreActivity(EventScheduler scheduler)
   {
      Point pos = entity.position;  // store current position before removing

      world.removeEntity(entity);
      scheduler.unscheduleAllEvents(entity);

      Entity blob = world.createOreBlob(entity.id + BLOB_ID_SUFFIX,
              pos, entity.actionPeriod / BLOB_PERIOD_SCALE,
              BLOB_ANIMATION_MIN +
                      Functions.rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
              imageStore.getImageList(BLOB_KEY));

      world.addEntity(blob);
      scheduler.scheduleActions(entity,world, imageStore);
   }

   public void executeOreBlobActivity(EventScheduler scheduler)
   {
      Optional<Entity> blobTarget = world.findNearest(entity.position, EntityKind.VEIN);
      long nextPeriod = entity.actionPeriod;

      if (blobTarget.isPresent())
      {
         Point tgtPos = blobTarget.get().position;

         if (moveToOreBlob(entity, blobTarget.get(), scheduler))
         {
            Entity quake = world.createQuake(tgtPos,
                    imageStore.getImageList(QUAKE_KEY));

            world.addEntity(quake);
            nextPeriod += entity.actionPeriod;
            scheduler.scheduleActions(entity, world, imageStore);
         }
      }

      scheduler.scheduleEvent(entity,
              world.createActivityAction(entity, world, imageStore),
              nextPeriod);
   }

   public void executeQuakeActivity(EventScheduler scheduler)
   {
      scheduler.unscheduleAllEvents(entity);
      world.removeEntity(entity);
   }

   public void executeVeinActivity(EventScheduler scheduler)
   {
      Optional<Point> openPt = world.findOpenAround(entity.position);

      if (openPt.isPresent())
      {
         Entity ore = world.createOre(ORE_ID_PREFIX + entity.id,
                 openPt.get(), ORE_CORRUPT_MIN +
                         Functions.rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                 imageStore.getImageList(ORE_KEY));
         world.addEntity(ore);
         scheduler.scheduleActions(entity, world, imageStore);
      }

      scheduler.scheduleEvent(entity,
              world.createActivityAction(entity, world, imageStore),
              entity.actionPeriod);
   }

   public boolean transformNotFull(EventScheduler scheduler)
   {
      if (entity.resourceCount >= entity.resourceLimit)
      {
         Entity miner = world.createMinerFull(entity.id, entity.resourceLimit,
                 entity.position, entity.actionPeriod, entity.animationPeriod,
                 entity.images);

         world.removeEntity(entity);
         scheduler.unscheduleAllEvents(entity);

         world.addEntity(miner);
         scheduler.scheduleActions(entity,world,imageStore);

         return true;
      }

      return false;
   }

   public void transformFull(EventScheduler scheduler)
   {
      Entity miner = world.createMinerNotFull(entity.id, entity.resourceLimit,
              entity.position, entity.actionPeriod, entity.animationPeriod,
              entity.images);

      world.removeEntity(entity);
      scheduler.unscheduleAllEvents(entity);

      world.addEntity(miner);
      scheduler.scheduleActions(entity, world, imageStore);
   }
   public boolean moveToNotFull(Entity miner, Entity target, EventScheduler scheduler)
   {
      if (miner.position.adjacent(target.position))
      {
         miner.resourceCount += 1;
         world.removeEntity(target);
         scheduler.unscheduleAllEvents(target);

         return true;
      }
      else
      {
         Point nextPos = miner.nextPositionMiner(world, target.position);

         if (!miner.position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(miner, nextPos);
         }
         return false;
      }
   }

   public boolean moveToFull(Entity miner, Entity target, EventScheduler scheduler)
   {
      if (miner.position.adjacent(target.position))
      {
         return true;
      }
      else
      {
         Point nextPos = miner.nextPositionMiner(world, target.position);

         if (!miner.position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(miner, nextPos);
         }
         return false;
      }
   }

   public boolean moveToOreBlob(Entity blob, Entity target, EventScheduler scheduler)
   {
      if (blob.position.adjacent(target.position))
      {
         world.removeEntity(target);
         scheduler.unscheduleAllEvents(target);
         return true;
      }
      else
      {
         Point nextPos = blob.nextPositionOreBlob(world, target.position);

         if (!blob.position.equals(nextPos))
         {
            Optional<Entity> occupant = world.getOccupant(nextPos);
            if (occupant.isPresent())
            {
               scheduler.unscheduleAllEvents(occupant.get());
            }

            world.moveEntity(blob, nextPos);
         }
         return false;
      }
   }

}

