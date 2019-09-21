import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestCases
{
   @Test
   public void testExercise1()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertTrue(one.equals(two));
      assertTrue(two.equals(one));
   }

   @Test
   public void testExercise2()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(1, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExercise3()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertEquals(one.hashCode(), two.hashCode());
   }

   @Test
   public void testExercise4()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0));

      assertNotEquals(one.hashCode(), two.hashCode());
   }
   @Test
   public void testExercise5()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      List<CourseSection> currentCourses = new ArrayList<CourseSection>();
      currentCourses.add(one);
      currentCourses.add(two);
      final Student st1 = new Student("Phuong", "Nguyen", 23, currentCourses);
      final Student st2 = new Student("Phuong", "Nguyen", 23, currentCourses);
      assertEquals(st1.hashCode(), st2.hashCode());
   }
   @Test
   public void testExercise6()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      List<CourseSection> currentCourses = new ArrayList<CourseSection>();
      currentCourses.add(one);
      currentCourses.add(two);
      final Student st1 = new Student("Phuong", "Nguyen", 22, currentCourses);
      final Student st2 = new Student("Phuong", "Nguyen", 23, currentCourses);
      assertNotEquals(st1.hashCode(), st2.hashCode());
   }
    @Test
   public void testExercise7()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      List<CourseSection> currentCourses = new ArrayList<CourseSection>();
      currentCourses.add(one);
      currentCourses.add(two);
      final Student st1 = new Student("Phuong", "Nguyen", 23, currentCourses);
      final Student st2 = new Student("Phuong", "Nguyen", 23, currentCourses);
      assertTrue(st1.equals(st2));
      assertTrue(st2.equals(st1));
   }
    @Test
   public void testExercise8()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      List<CourseSection> currentCourses = new ArrayList<CourseSection>();
      currentCourses.add(one);
      currentCourses.add(two);
      final Student st1 = new Student("Phuong", "Le", 23, currentCourses);
      final Student st2 = new Student("Phuong", "Nguyen", 23, currentCourses);
      assertFalse(st1.equals(st2));
      assertFalse(st2.equals(st1));
   }


}
