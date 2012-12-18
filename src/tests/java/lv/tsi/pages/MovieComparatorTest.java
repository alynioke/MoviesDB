package lv.tsi.pages;

import lv.tsi.entities.Movie;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class MovieComparatorTest 
{
	MovieTitleComparator movieTitleComparator;
	MovieYearComparator movieYearComparator;
	Movie mov1Mock, mov2Mock;
	
	@Before
	public void setUp()
	{
		movieTitleComparator = new MovieTitleComparator();
		movieYearComparator = new MovieYearComparator();
		mov1Mock = mock(Movie.class);
		mov2Mock = mock(Movie.class);
	}

	@Test
	public void testMovieYearComparator() 
	{
		when(mov1Mock.getYear()).thenReturn(1998);
		when(mov2Mock.getYear()).thenReturn(2000);
		assertTrue(movieYearComparator.compare(mov1Mock, mov2Mock) == -1);

		when(mov1Mock.getYear()).thenReturn(2000);
		when(mov2Mock.getYear()).thenReturn(1998);
		assertTrue(movieYearComparator.compare(mov1Mock, mov2Mock) == 1);

		when(mov1Mock.getYear()).thenReturn(2005);
		when(mov2Mock.getYear()).thenReturn(2005);
		assertTrue(movieYearComparator.compare(mov1Mock, mov2Mock) == 0);
	}
	
	@Test
	public void testMovieTitleComparator() 
	{
		when(mov1Mock.getTitle()).thenReturn("title1");
		when(mov2Mock.getTitle()).thenReturn("title2");
		assertTrue(movieTitleComparator.compare(mov1Mock, mov2Mock) == -1);
		
		when(mov1Mock.getTitle()).thenReturn("title2");
		when(mov2Mock.getTitle()).thenReturn("title1");
		assertTrue(movieTitleComparator.compare(mov1Mock, mov2Mock) == 1);
		
		when(mov1Mock.getTitle()).thenReturn("title");
		when(mov2Mock.getTitle()).thenReturn("title");
		assertTrue(movieTitleComparator.compare(mov1Mock, mov2Mock) == 0);
	}
}