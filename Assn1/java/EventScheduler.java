import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.LinkedList;

final class EventScheduler
{
   public PriorityQueue<Event> eventQueue;
   public Map<Entity, List<Event>> pendingEvents;
   public double timeScale;
   private final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

   public EventScheduler(double timeScale)
   {
      this.eventQueue = new PriorityQueue<>(new EventComparator());
      this.pendingEvents = new HashMap<>();
      this.timeScale = timeScale;
   }
   public void scheduleEvent(Entity entity, Action action, long afterPeriod)
   {
      long time = System.currentTimeMillis() +
              (long)(afterPeriod * this.timeScale);
      Event event = new Event(action, time, entity);

      this.eventQueue.add(event);

      // update list of pending events for the given entity
      List<Event> pending = this.pendingEvents.getOrDefault(entity,
              new LinkedList<>());
      pending.add(event);
      this.pendingEvents.put(entity, pending);
   }
   public void unscheduleAllEvents(Entity entity)
   {
      List<Event> pending = pendingEvents.remove(entity);

      if (pending != null)
      {
         for (Event event : pending)
         {
            eventQueue.remove(event);
         }
      }
   }
   public void removePendingEvent(Event event)
   {
      List<Event> pending = pendingEvents.get(event.entity);

      if (pending != null)
      {
         pending.remove(event);
      }
   }
   public void updateOnTime(EventScheduler scheduler,long time)
   {
      while (!eventQueue.isEmpty() &&
              eventQueue.peek().time < time)
      {
         Event next = scheduler.eventQueue.poll();

         removePendingEvent(next);

         next.action.executeAction(scheduler);
      }
   }
   public void scheduleActions(Entity entity, WorldModel world, ImageStore imageStore)
   {
      switch (entity.kind)
      {
         case MINER_FULL:
            scheduleEvent( entity, world.createActivityAction(entity, world, imageStore), entity.actionPeriod);
            scheduleEvent(entity, Functions.createAnimationAction(entity, 0), entity.getAnimationPeriod());
            break;

         case MINER_NOT_FULL:
            scheduleEvent( entity, world.createActivityAction(entity, world, imageStore), entity.actionPeriod);
            scheduleEvent(entity, Functions.createAnimationAction(entity, 0), entity.getAnimationPeriod());
            break;

         case ORE:
            scheduleEvent(entity, world.createActivityAction(entity, world, imageStore), entity.actionPeriod);
            break;

         case ORE_BLOB:
            scheduleEvent(entity, world.createActivityAction(entity, world, imageStore), entity.actionPeriod);
            scheduleEvent(entity, Functions.createAnimationAction(entity, 0), entity.getAnimationPeriod());
            break;

         case QUAKE:
            scheduleEvent(entity, world.createActivityAction(entity, world, imageStore), entity.actionPeriod);
            scheduleEvent(entity, Functions.createAnimationAction(entity, QUAKE_ANIMATION_REPEAT_COUNT), entity.getAnimationPeriod());
            break;

         case VEIN:
            scheduleEvent(entity, world.createActivityAction(entity, world, imageStore), entity.actionPeriod);
            break;

         default:
      }
   }
}
