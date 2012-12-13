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

import lv.tsi.database.HibernateUserDAO;

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

    @Override
    public final boolean authenticate(final String username, final String password)
    {
        //почему возможна ситуация, что нам в функцию передают пароль 
        //(то есть пользователь его ввел), и при этом кто-то может быть еще 
        //залогинен (user != null). 
        
        if (user == null)
        {
            byte[] bytesOfMessage;
            StringBuffer hexPassword = new StringBuffer();
            MessageDigest md;
			try {
				bytesOfMessage = password.getBytes("UTF-8");
				md = MessageDigest.getInstance("SHA");
	            byte[] digest = md.digest(bytesOfMessage);
	            for (byte character:digest) {
		            String hex = Integer.toHexString(0xFF & character);
		            if (hex.length() == 1) {
		                hexPassword.append('0');
		            }
		            hexPassword.append(hex);
	            }
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
            
        	List<User> users = HibernateUserDAO.getByLoginAndPassword(username, hexPassword);
        	for (User u:users){
        		user = u;        		
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