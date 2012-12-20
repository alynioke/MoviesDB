package lv.tsi.pages;

import lv.tsi.database.HibernateUserDAO;
import lv.tsi.entities.User;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;
import org.apache.wicket.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SignInSessionTest
{	
	List<User> users;
	User user;
	HibernateUserDAO mockUserDAO;
	SignInSession session;

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before 
	public void setUp() 
	{
		users = new ArrayList<User>();
		user = new User("alinka", SignInSession.getHash("alina"), "Alina", "Karpelceva");
		user.setId(8);
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
	
	@Test
	public void testGetHash()
	{
		//if getHash function works right it will return hash of
		//a same size to a different size passwords
		String passwordFromOneChar = "1";
		String passwordFromVariousChars = "1234567890";
		String passwordEmpty = "";
		String passwordNull = null;

		String passwordFromOneCharHash = session.getHash(passwordFromOneChar);
		String passwordFromVariousCharsHash = session.getHash(passwordFromVariousChars);
		String passwordEmptyHash = session.getHash(passwordEmpty);

		assertEquals(passwordFromOneCharHash.length(), passwordFromVariousCharsHash.length());
		assertEquals(passwordFromVariousCharsHash.length(), passwordEmptyHash.length());
		
		exception.expect(NullPointerException.class);
		String passwordNullHash = session.getHash(passwordNull);
	}
}