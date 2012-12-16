package lv.tsi.pages;

import lv.tsi.database.HibernateDAO;
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
	HibernateUserDAO hibernateUserDAO = new HibernateUserDAO();
	
    public SignInSession(Request request)
    {
        super(request);
    }

    @Override
    public final boolean authenticate(final String username, final String password)
    {
        //������ �������� ��������, ��� ��� � ������� �������� ������ 
    	//(�� ���� ������������ ��� ����), � ��� ���� ���-�� ����� ���� ��� 
    	//��������� (user != null). 
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
            users = hibernateUserDAO.getByLoginAndPassword(username, hexPassword.toString());
        }
        
        if (users.size() != 0) {
        	user = users.get(0);
        	return true;
        }
        return false;
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