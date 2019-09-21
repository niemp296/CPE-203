import java.util.List;
import java.util.Objects;
import java.util.Comparator;

class Student
{
   private final String surname;
   private final String givenName;
   private final int age;
   private final List<CourseSection> currentCourses;

   public Student(final String surname, final String givenName, final int age,
      final List<CourseSection> currentCourses)
   {
      this.surname = surname;
      this.givenName = givenName;
      this.age = age;
      this.currentCourses = currentCourses;
   }
   public boolean equals(Object o)
   {
      if (o == this)
          return true;
      if (!(o instanceof Student))
         return false;
      Student st = (Student) o;
      return this.surname.equals(st.surname)
             && this.givenName.equals(st.givenName)
             && (this.age == st.age)
             && this.currentCourses.equals(st.currentCourses);
   }
   public int hashCode()
   {
      return Objects.hash(surname, givenName, currentCourses) + age;
   }
  
}
