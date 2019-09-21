import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.Before;

public class TestCases
{
   private static final Song[] songs = new Song[] {
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005),
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Gerry Rafferty", "Baker Street", 1978)
      };

   @Test
   public void testArtistComparator1()
   {
	Comparator<Song> comp = new ArtistComparator();
	int result1 = comp.compare(songs[0], songs[1]);
	boolean  result = (result1 < 0);
	assertEquals(true, result);
   }
   @Test
   public void testArtistComparator2()
   {
        Comparator<Song> comp = new ArtistComparator();
        int result1 = comp.compare(songs[2], songs[3]);
        boolean  result = (result1 < 0);
        assertEquals(true, result);
   }
   @Test
   public void testLambdaTitleComparator1()
   {
	Comparator<Song> lambda = (Song s1, Song s2)->s1.getTitle().compareTo( s2.getTitle());
	int result1 = lambda.compare(songs[0], songs[1]);
	boolean result = (result1 > 0);
	assertEquals(true, result);
   }
    @Test
   public void testLambdaTitleComparator2()
   {
        Comparator<Song> lambda = (Song s1, Song s2)->s1.getTitle().compareTo(s2.getTitle());
        int result1 = lambda.compare(songs[3], songs[5]);
        boolean result = (result1 ==  0);
        assertEquals(true, result);
   }

   @Test
   public void testYearExtractorComparator1()
   {
 	Comparator<Song> keyExtract = Comparator.comparing(Song::getYear);
	keyExtract = keyExtract.reversed();
	int result1 = keyExtract.compare(songs[1], songs[2]);
        boolean result = (result1 >  0);
        assertEquals(true, result);
   }
   @Test
   public void testYearExtractorComparator2()
   {
        Comparator<Song> keyExtract = Comparator.comparing(Song::getYear);
        keyExtract = keyExtract.reversed();
        int result1 = keyExtract.compare(songs[2], songs[3]);
        boolean result = (result1 <  0);
        assertEquals(true, result);
   }

   @Test
   public void testComposedComparator1()
   {
	Comparator<Song> c1 = Comparator.comparing(Song::getArtist);
	Comparator<Song> c2 = Comparator.comparing(Song::getYear);
	Comparator<Song> comp = new ComposedComparator(c1, c2);
	int result1 = comp.compare(songs[3], songs[7]);
	boolean result = (result1 > 0);
	assertEquals(true, result);
   }
    @Test
   public void testComposedComparator2()
   {
        Comparator<Song> c1 = Comparator.comparing(Song::getArtist);
        Comparator<Song> c2 = Comparator.comparing(Song::getYear);
        Comparator<Song> comp = new ComposedComparator(c1, c2);
        int result1 = comp.compare(songs[0], songs[1]);
        boolean result = (result1 < 0);
        assertEquals(true, result);
   }

   @Test
   public void testThenComparing1()
   {
	Comparator<Song> comp = Comparator.comparing(Song::getTitle).thenComparing(Song::getArtist);
	int result1 = comp.compare(songs[3], songs[5]);
	boolean result = (result1 > 0 );
	assertEquals(true, result);
   }
    @Test
   public void testThenComparing2()
   {
        Comparator<Song> comp = Comparator.comparing(Song::getTitle).thenComparing(Song::getArtist);
        int result1 = comp.compare(songs[0], songs[1]);
        boolean result = (result1 > 0 );
        assertEquals(true, result);
   }


   @Test
   public void runSort()
   {
      List<Song> songList = new ArrayList<>(Arrays.asList(songs));
      List<Song> expectedList = Arrays.asList(
         new Song("Avett Brothers", "Talk on Indolence", 2006),
         new Song("City and Colour", "Sleeping Sickness", 2007),
         new Song("Decemberists", "The Mariner's Revenge Song", 2005),
         new Song("Foo Fighters", "Baker Street", 1997),
         new Song("Gerry Rafferty", "Baker Street", 1978),
         new Song("Gerry Rafferty", "Baker Street", 1998),
         new Song("Queen", "Bohemian Rhapsody", 1975),
         new Song("Rogue Wave", "Love's Lost Guarantee", 2005)
         );
      Comparator<Song> comp = Comparator.comparing(Song::getArtist).thenComparing(Song::getTitle).thenComparing(Song::getYear);
      songList.sort(comp );

      assertEquals(songList, expectedList);
   }

}
