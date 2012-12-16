package lv.tsi.pages;

import lv.tsi.database.DatabaseHandler;
import lv.tsi.entities.User;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class Homepage extends WebPage implements AuthenticatedWebPage 
{
	DatabaseHandler dbHandler = DatabaseHandler.instance;
	
    public Homepage() 
    {
    	dbHandler.connect();
        
        String welcome = "";
        User user = ((SignInSession)Session.get()).getUser();
        if (user != null) {
            System.out.println("Login - "+user.getLogin());
        	welcome = "Welcome, "+user.getFirstname()+"!";
        }
        add(new Label("welcome", welcome));
    }
}