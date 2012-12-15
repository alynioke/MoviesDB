package lv.tsi.pages;

import lv.tsi.database.DatabaseHandler;
import lv.tsi.entities.User;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class Homepage extends WebPage implements AuthenticatedWebPage 
{
    public Homepage() 
    {
        DatabaseHandler.connect();
        
        String welcome = "";
        User user = ((SignInSession)Session.get()).getUser();
        if (user != null) {
        	welcome = "Welcome, "+user.getFirstname()+"!";
        }
        add(new Label("welcome", welcome));
    }
}