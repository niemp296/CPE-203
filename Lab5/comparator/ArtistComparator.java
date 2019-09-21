import java.util.Comparator;
public class ArtistComparator implements Comparator<Song>
{
     public int compare(Song s1, Song s2)
     {
	if (s1.getArtist().equals(s2.getArtist()))
	     return 0;
        if (s1.getArtist() == null)
             return 1;
        if (s2.getArtist() == null)
             return -1;
        return s1.getArtist().compareTo(s2.getArtist());
     }
}
