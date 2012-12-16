package lv.tsi.pages;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lv.tsi.database.HibernateUserDAO;
import lv.tsi.entities.User;

import static org.mockito.Mockito.*;
import org.apache.wicket.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SignInSessionTest
{
	List<User> users;
	User user;
	HibernateUserDAO mockUserDAO;
	SignInSession session;
	
	@Before 
	public void setUp() 
	{
		users = new ArrayList<User>();
		user = new User();
		user.setId(8);
		user.setFirstname("Alina");
		user.setLastname("Karpelceva");
		user.setLogin("alinka");
		user.setPassword("c542a5c75409b4c42c96dea4fb6ddd50bf591c22");
		users.add(user);
		
		Request mockRequest = mock(Request.class);
		when(mockRequest.getLocale()).thenReturn(new Locale(""));
		session = new SignInSession(mockRequest);
		mockUserDAO = mock(HibernateUserDAO.class);
		session.hibernateUserDAO = mockUserDAO;
	}

	@Test
	public void testAuthenticateCorrect()
	{
		//test correct user and password
		when(mockUserDAO.getByLoginAndPassword(
				user.getLogin(), 
				user.getPassword())
			).thenReturn(users); 
		assertTrue(session.authenticate(user.getLogin(), "alina"));
	}

	@Test
	public void testAuthenticateWrongPassword()
	{
		when(mockUserDAO.getByLoginAndPassword(
				eq(user.getLogin()), 
				anyString())
			).thenReturn(new ArrayList<User>());
		assertFalse(session.authenticate(user.getLogin(), "wrongPassword"));
	}
	
	@Test
	public void testAuthenticateWrongUser()
	{
		when(mockUserDAO.getByLoginAndPassword(
				anyString(), 
				anyString())
			).thenReturn(new ArrayList<User>());
		assertFalse(session.authenticate("wrongUser", "wrongPassword"));
	}
}