package index;


import lv.tsi.database.DatabaseHandler;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;

	
public class Homepage extends WebPage implements AuthenticatedWebPage{

	private static final long serialVersionUID = 1L;

    public Homepage() {

		DatabaseHandler.connect();
		
    	if ( ((SignInSession)getSession()).getUser() != null) {
    		System.out.println("email=" + ((SignInSession)getSession()).getUser().getLogin());
   	    }

    }
 
}
