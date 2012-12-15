package lv.tsi.pages;

import lv.tsi.database.HibernateUserDAO;
import lv.tsi.entities.User;

import java.security.MessageDigest;
import java.util.List;
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
        List<User> users = null;
        if (user == null) {
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
            } catch (Exception e) {
                e.printStackTrace();
            } 
            //catching generic exception to avoid multiple catch blocks
            users = HibernateUserDAO.getByLoginAndPassword(username, hexPassword);
        }
        return users.size() != 0;
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