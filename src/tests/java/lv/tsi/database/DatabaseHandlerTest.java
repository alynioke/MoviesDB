package lv.tsi.database;

import static org.junit.Assert.*;
import lv.tsi.database.DatabaseHandler;

import org.junit.Test;

public class DatabaseHandlerTest 
{
	DatabaseHandler dbHandler = DatabaseHandler.instance;
	@Test
	public void testDatabaseHandler() 
	{
		assertEquals(dbHandler.getSession(), null);
		dbHandler.connect();
		assertFalse(dbHandler.getSession() == null);
		dbHandler.close();
		assertEquals(dbHandler.getSession(), null);
	}
}