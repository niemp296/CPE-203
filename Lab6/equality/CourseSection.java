import java.time.LocalTime;
import java.util.Objects;
import java.util.Comparator;

class CourseSection
{
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
      final int enrollment, final LocalTime startTime, final LocalTime endTime)
   {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   // additional likely methods not defined since they are not needed for testing
   public boolean equals(Object o)
   {
      if(o == this)
         return true;
      if (!(o instanceof CourseSection))
         return false;
      CourseSection cs = (CourseSection) o;
      return this.prefix.equals(cs.prefix)
	     && this.number.equals(cs.number)
             && (this.enrollment == cs.enrollment)
             && this.startTime.equals(cs.startTime)
             && this.endTime.equals(cs.endTime);   
   }
   public int hashCode()
   {
      int result;
      result = prefix.hashCode() + number.hashCode() + enrollment + startTime.hashCode() + endTime.hashCode();
      return result;
   }
}
