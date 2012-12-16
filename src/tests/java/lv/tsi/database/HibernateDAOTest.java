package lv.tsi.database;

import static org.junit.Assert.*;

import javax.transaction.Synchronization;

import lv.tsi.database.DatabaseHandler;
import lv.tsi.database.HibernateDAO;
import lv.tsi.entities.Movie;

import static org.mockito.Mockito.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.transaction.spi.LocalStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HibernateDAOTest 
{
	DatabaseHandler dbHandler = DatabaseHandler.instance;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before public void setUp() 
	{
		dbHandler.connect();
	}

	@After public void tearDown() 
	{
		dbHandler.close();
	}
	
	@Test
	public void testSelectById() 
	{
		HibernateDAO hibernateDAO = new HibernateDAO();
		//select with invalid id
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
		HibernateDAO hibernateDAO = new HibernateDAO();
		DatabaseHandler dbHandlerMock = mock(DatabaseHandler.class);
		Session sessionMock = mock(Session.class);

		when(sessionMock.get(Movie.class, 18)).thenReturn(new Movie());
		when(sessionMock.getTransaction()).thenReturn(mock(Transaction.class));
		when(dbHandlerMock.getSession()).thenReturn(sessionMock);
		
		hibernateDAO.dbHandler = dbHandlerMock;
		
		//select with valid id 
		Movie movie = hibernateDAO.selectById(Movie.class, 18);
		assertNotNull(movie);
	}
	
	@Test
	public void testInsert()
	{
		
	}

	@Test 
	public void testSelectAll() 
	{
		
	}
}