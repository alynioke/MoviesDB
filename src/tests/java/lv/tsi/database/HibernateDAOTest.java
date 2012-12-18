package lv.tsi.database;

import lv.tsi.database.DatabaseHandler;
import lv.tsi.database.HibernateDAO;
import lv.tsi.entities.Movie;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HibernateDAOTest 
{
	DatabaseHandler dbHandler = DatabaseHandler.instance;
	HibernateDAO hibernateDAO;
	Session sessionMock;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before public void setUp() 
	{
		dbHandler.connect();
		hibernateDAO = new HibernateDAO();
		DatabaseHandler dbHandlerMock = mock(DatabaseHandler.class);
		sessionMock = mock(Session.class);
		hibernateDAO.dbHandler = dbHandlerMock;
		
		when(sessionMock.getTransaction()).thenReturn(mock(Transaction.class));
		when(dbHandlerMock.getSession()).thenReturn(sessionMock);
	}

	@After public void tearDown() 
	{
		dbHandler.close();
	}
	
	@Test
	public void testSelectById() 
	{
		//select with invalid id
		when(sessionMock.get(Movie.class, -5)).thenReturn(null);
		Movie movie = hibernateDAO.selectById(Movie.class, -5);
		assertTrue(movie == null);
		
		//select without any data. 
		//But it's impossible to pass a 'null' class, therefore I expect an exception
	    exception.expect(NullPointerException.class);
		movie = hibernateDAO.selectById(null, 0);
	}
	
	@Test
	public void testSelectByValidId()
	{
		when(sessionMock.get(Movie.class, 18)).thenReturn(new Movie());
		Movie movie = hibernateDAO.selectById(Movie.class, 18);
		assertNotNull(movie);
	}
	
	@Test 
	public void testSelectAll() 
	{		
		Criteria criteriaMock = mock(Criteria.class);
		List<Movie> moviesList = new ArrayList<Movie>();
		moviesList.add(new Movie());
		moviesList.add(new Movie());
		when(criteriaMock.list()).thenReturn(moviesList);
		when(sessionMock.createCriteria(Movie.class)).thenReturn(criteriaMock);
	
		List<Movie> movies = hibernateDAO.selectAll(Movie.class);
		assertNotNull(movies);
		assertTrue(movies.size() != 0);

	    exception.expect(NullPointerException.class);
		movies = hibernateDAO.selectAll(null);
	}
}