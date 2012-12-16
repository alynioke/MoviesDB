package lv.tsi.pages;

import lv.tsi.entities.Movie;
import java.util.Comparator;

public class MovieTitleComparator implements Comparator<Movie> 
{
    @Override
    public int compare(Movie mov1, Movie mov2) 
    {
        return mov1.getTitle().compareTo(mov1.getTitle());
    }
}