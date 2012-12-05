package index;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
public final class SignInSession extends AuthenticatedWebSession
{
    private String user;


    public SignInSession(Request request)
    {
        super(request);
    }

    /**
     * Checks the given username and password, returning a User object if if the username and
     * password identify a valid user.
     * 
     * @param username
     *            The username
     * @param password
     *            The password
     * @return True if the user was authenticated
     */
    @Override
    public final boolean authenticate(final String username, final String password)
    {
        final String WICKET = "wicket";
        String usernameDB;
        String passwordHashDB;
        
		DatabaseManager db = DatabaseManager.getInstance();
    	ResultSet rs = db.select("SELECT COUNT(*) FROM movie");
    	try {
			while (rs.next()) {
				usernameDB = rs.getString("login");
				passwordHashDB = rs.getString("password");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
        
        
        if (user == null)
        {
            // Trivial password "db"
            if (WICKET.equalsIgnoreCase(username) && WICKET.equalsIgnoreCase(password))
            {
                user = username;
            }
            
            //session.get.getUser - надо реализовать
        }

        return user != null;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(final String user)
    {
        this.user = user;
    }

    @Override
    public Roles getRoles()
    {
        return null;
    }
}