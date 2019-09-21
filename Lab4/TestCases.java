import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

import java.awt.Color;
import java.awt.Point;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
   public static final double DELTA = 0.00001;

   @Test
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getRadius", "setRadius", "getCenter");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0]);

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getWidth", "setWidth", "getHeight", "setHeight", "getUpperLeft");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, double.class, void.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0], new Class[] {double.class}, new Class[0]);

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testTriangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getVertexA", "getVertexB", "getVertexC");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         Point.class, Point.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[0], new Class[0]);

      verifyImplSpecifics(Triangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testConvexPolygonImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getVertex");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[] {int.class});

      verifyImplSpecifics(ConvexPolygon.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   private static void verifyImplSpecifics(
      final Class<?> clazz,
      final List<String> expectedMethodNames,
      final List<Class> expectedMethodReturns,
      final List<Class[]> expectedMethodParameters)
      throws NoSuchMethodException
   {
      assertEquals("Unexpected number of public fields",
         0, clazz.getFields().length);

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertEquals("Unexpected number of public methods",
         expectedMethodNames.size(), publicMethods.size());

      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodReturns.size());
      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodParameters.size());

      for (int i = 0; i < expectedMethodNames.size(); i++)
      {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
            expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }
   }
   /* put your tests here */
   @Test
   public void testPerimCircle() {
      Circle c = new Circle(5, new Point(2,1), Color.GREEN);
      double d = c.getPerimeter();
      assertEquals(Math.PI*10, d, DELTA);
   }
   @Test
   public void testAreaCircle() {
      Circle c = new Circle(5, new Point(2,1), Color.RED);
      double d = c.getArea();
      assertEquals(Math.PI*25, d, DELTA);
   }
   @Test
   public void testPerimRectangle() {
      Rectangle r = new Rectangle(5,10, new Point(1,3), Color.PINK);
      double d = r.getPerimeter();
      assertEquals(30, d, DELTA); 
   }
   @Test
   public void testAreaRectangle() {
      Rectangle r = new Rectangle(5,10, new Point(1,3), Color.PINK);
      double d = r.getArea();
      assertEquals(50, d, DELTA);
   }
   @Test
   public void testPerimTriangle() {
      Triangle t = new Triangle(new Point(0,0), new Point(3,0), new Point(3,4), Color.PINK);
      double d = t.getPerimeter();
      assertEquals(12, d, DELTA);
   }
   @Test
   public void testAreaTriangle() {
      Triangle t = new Triangle(new Point(-8,-7), new Point(4,-7), new Point(4,-2), Color.PINK);
      double d = t.getArea();
      assertEquals(30, d, DELTA);
   }
/*   @Test
   public void testPerimConvexPolygon(){
      Point [] arr = new Point[] {new Point(2,8), new Point(4,4), new Point(2,0)};
      ConvexPolygon poly = new ConvexPolygon(arr, Color.GREEN);
      double d = poly.getPerimeter();
      System.out.println(" d: " + d);
      assertEquals(8+4*Math.sqrt(5), d, DELTA);
   }
*/
   @Test
   public void testWorkSpace() {
      WorkSpace work = new WorkSpace();
      work.add(new Circle(5, new Point(1,2), Color.GREEN));
      work.add(new Rectangle(3, 4, new Point(0,1), Color.GREEN));
      int count = work.size();
      assertEquals(2, count, DELTA);
   }
   @Test
   public void testWorkSpace2() {
     WorkSpace work = new WorkSpace();
     Circle c1 = new Circle(5, new Point(1,2), Color.PINK);
     work.add(new Rectangle(3,4, new Point(0,1), Color.PINK));
     work.add(c1);
     assertEquals(c1, work.getCircles().get(0));
   }
   /* HINT - comment out implementation tests for the classes that you have not yet implemented */

}
