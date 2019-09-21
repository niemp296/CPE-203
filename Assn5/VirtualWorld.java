import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import processing.core.*;


public final class VirtualWorld
   extends PApplet
{
   public static final int TIMER_ACTION_PERIOD = 100;

   public static final int VIEW_WIDTH = 1280;
   public static final int VIEW_HEIGHT = 960;
   public static final int TILE_WIDTH = 32;
   public static final int TILE_HEIGHT = 32;
   public static final int WORLD_WIDTH_SCALE = 1;
   public static final int WORLD_HEIGHT_SCALE = 1;

   public static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
   public static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
   public static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
   public static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

   public static final String IMAGE_LIST_FILE_NAME = "imagelist";
   public static final String DEFAULT_IMAGE_NAME = "background_default";
   public static final int DEFAULT_IMAGE_COLOR = 0x808080;

   public static final String LOAD_FILE_NAME = "gaia.sav";

   public static final String FAST_FLAG = "-fast";
   public static final String FASTER_FLAG = "-faster";
   public static final String FASTEST_FLAG = "-fastest";
   public static final double FAST_SCALE = 0.5;
   public static final double FASTER_SCALE = 0.25;
   public static final double FASTEST_SCALE = 0.10;


   public static final String ICE_ID = "ice";
   public static final String BABYWALK_ID = "walk";
   public static final String WYVERN_ID = "wyvern";


   public static double timeScale = 1.0;

   public ImageStore imageStore;
   public WorldModel world;
   public WorldView view;
   public EventScheduler scheduler;

   public long next_time;


   public void settings()
   {
      size(VIEW_WIDTH, VIEW_HEIGHT);
   }

   /*
      Processing entry point for "sketch" setup.
   */

   public void setup()
   {
      this.imageStore = new ImageStore(
         createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
         createDefaultBackground(imageStore));
      this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
         TILE_WIDTH, TILE_HEIGHT);
      this.scheduler = new EventScheduler(timeScale);

      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
      loadWorld(world, LOAD_FILE_NAME, imageStore);

      scheduleActions(world, scheduler, imageStore);

      next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
   }

   public void draw()
   {
      long time = System.currentTimeMillis();
      if (time >= next_time)
      {
         this.scheduler.updateOnTime(this.scheduler, time);
         next_time = time + TIMER_ACTION_PERIOD;
      }

      view.drawViewport();
   }

   public void keyPressed()
   {
      if (key == CODED)
      {
         int dx = 0;
         int dy = 0;

         switch (keyCode)
         {
            case UP:
               dy = -1;
               break;
            case DOWN:
               dy = 1;
               break;
            case LEFT:
               dx = -1;
               break;
            case RIGHT:
               dx = 1;
               break;
         }
         view.shiftView(dx, dy);
      }
   }

   private static Background createDefaultBackground(ImageStore imageStore)
   {
      return new Background(DEFAULT_IMAGE_NAME,
         imageStore.getImageList(DEFAULT_IMAGE_NAME));
   }

   private static PImage createImageColored(int width, int height, int color)
   {
      PImage img = new PImage(width, height, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }

   private static void loadImages(String filename, ImageStore imageStore,
      PApplet screen)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         Functions.loadImages(in, imageStore, screen);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private static void loadWorld(WorldModel world, String filename,
      ImageStore imageStore)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         Functions.load(in, world, imageStore);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   private static void scheduleActions(WorldModel world,
      EventScheduler scheduler, ImageStore imageStore)
   {
      for (Entity entity : world.entities)
      {
          if (entity instanceof ActionEntity)
              ((ActionEntity)entity).scheduleActions(scheduler, world, imageStore);
      }
   }

   private static void parseCommandLine(String [] args)
   {
      for (String arg : args)
      {
         switch (arg)
         {
            case FAST_FLAG:
               timeScale = Math.min(FAST_SCALE, timeScale);
               break;
            case FASTER_FLAG:
               timeScale = Math.min(FASTER_SCALE, timeScale);
               break;
            case FASTEST_FLAG:
               timeScale = Math.min(FASTEST_SCALE, timeScale);
               break;
         }
      }
   }




   public void mousePressed() {
       mousePressedExecute();
       redraw();

   }

   private void mousePressedExecute()
   {
       Point mousePoint = new Point(mouseX/TILE_WIDTH ,mouseY/TILE_HEIGHT);
       Wyvern wyvern = new Wyvern(WYVERN_ID, 0, mousePoint,1000,100,imageStore.getImageList(WYVERN_ID));
       world.addEntity(wyvern);
       wyvern.scheduleActions(scheduler,world,imageStore);
       Background ice = new Background(ICE_ID, imageStore.getImageList(ICE_ID));
       Consumer<Point> changeBackGround = p->  {
           world.setBackground(p, ice);


           //if Miner is within ice,
           if ((world.getOccupancyCell(p) instanceof MinerNotFull)
               && (world.getOccupant(p).isPresent()))
           {
                MinerNotFull minerNotFull = (MinerNotFull) world.getOccupant(p).get();
                minerNotFull.setId(BABYWALK_ID);
                minerNotFull.setImageIndex(0);
                minerNotFull.setImages(imageStore.getImageList(BABYWALK_ID));
                minerNotFull.setActionPeriod(400);
                minerNotFull.setAnimationPeriod(10);
                minerNotFull.setBaby(true);


           }
           if ((world.getOccupancyCell(p) instanceof MinerFull)
                   && (world.getOccupant(p).isPresent()))
           {
               MinerFull minerFull = (MinerFull) world.getOccupant(p).get();
               minerFull.setId(BABYWALK_ID);
               minerFull.setImageIndex(0);
               minerFull.setImages(imageStore.getImageList(BABYWALK_ID));
               minerFull.setActionPeriod(400);
               minerFull.setAnimationPeriod(10);

           }

       };
       mousePressedList(mousePoint)
               .forEach(p-> changeBackGround.accept(p));

       System.out.println("Point: X:  " + mouseX/TILE_WIDTH+ " Y: " + mouseY/TILE_HEIGHT);
   }


   private List<Point> mousePressedList(Point point)
   {
       int x = point.getX();
       int y = point.getY();
       List<Point> mouseList = new ArrayList <>();
       for(int i = 0 ; i < 6; i++)
       {
           int newX = x + i;
           for(int j = 0; j < 4; j++) {
               int newY = y + j;
               if ((newX < WORLD_COLS) && (newY < WORLD_ROWS)) {
                   mouseList.add(new Point(newX, newY));
               }
           }
       }


       return mouseList;

   }



   public static void main(String [] args)
   {
      parseCommandLine(args);
      PApplet.main(VirtualWorld.class);
   }


}
