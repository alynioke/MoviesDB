package lv.tsi.database;

import lv.tsi.entities.Rating;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Test;
import org.mockito.Mockito;

public class HibernateRatingDAOTest 
{
	@Test
	public void testGetByMovieIdAndUserId() 
	{
		HibernateRatingDAO hibernateRatingDAO = new HibernateRatingDAO();
		DatabaseHandler dbHandlerMock = mock(DatabaseHandler.class);
		Session sessionMock = mock(Session.class);
		Criteria criteriaMock = mock(Criteria.class);
		
		List<Rating> ratingList = new ArrayList<Rating>();
		ratingList.add(new Rating());
		ratingList.add(new Rating());
		
		when(criteriaMock.add(Mockito.<SimpleExpression>anyObject())).thenReturn(criteriaMock);
		when(sessionMock.getTransaction()).thenReturn(mock(Transaction.class));
		when(sessionMock.createCriteria(Rating.class)).thenReturn(criteriaMock);
		when(criteriaMock.list()).thenReturn(ratingList);
		when(dbHandlerMock.getSession()).thenReturn(sessionMock);
		
		hibernateRatingDAO.dbHandler = dbHandlerMock;

		List<Rating> results = hibernateRatingDAO.getByMovieIdAndUserId(5, 0);
		assertNotNull(results);
		assertEquals(results.size(), 2);
		
		results = hibernateRatingDAO.getByMovieIdAndUserId(5, 2);
		assertNotNull(results);
		assertEquals(results.size(), 2);
	}
}