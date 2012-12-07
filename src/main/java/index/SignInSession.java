package index;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
public final class SignInSession extends AuthenticatedWebSession
{
    private User user;


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
        
        //почему возможна ситуация, что нам в функцию передают пароль 
        //(то есть пользователь его ввел), и при этом кто-то может быть еще 
        //залогинен (user != null). 
        
        if (user == null)
        {
            

            byte[] bytesOfMessage;
            StringBuffer hexString = new StringBuffer();
            MessageDigest md;
			try {
				bytesOfMessage = password.getBytes("UTF-8");
				md = MessageDigest.getInstance("SHA");
	            byte[] thedigest = md.digest(bytesOfMessage);
	            for (byte character:thedigest) {
		            String hex = Integer.toHexString(0xFF & character);
		            if (hex.length() == 1) {
		                hexString.append('0');
		            }
		            hexString.append(hex);
	            }
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
            
            
    		DatabaseManager db = DatabaseManager.getInstance();
        	ResultSet rs = db.select("SELECT * FROM user WHERE email LIKE '"+username+"' " +
        			" AND password = '"+hexString+"'");
        
            try {
				if (rs.next())
				{
				    user = new User(rs.getInt("id"), rs.getString("email"), 
				    		rs.getString("firstname"), rs.getString("lastname"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }

        return user != null;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(final User user)
    {
        this.user = user;
    }

    @Override
    public Roles getRoles()
    {
        return null;
    }
}