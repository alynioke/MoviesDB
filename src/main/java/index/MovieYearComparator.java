package index;

import java.util.Comparator;

public class MovieYearComparator implements Comparator<Movie> {

	@Override
	public int compare(Movie mov1, Movie mov2) {
		if (mov1.getYear() > mov2.getYear())
		{ 
			return 1;
		} else if (mov1.getYear() > mov2.getYear()) { 
			return -1;
		} 
		return 0;
	}

}
