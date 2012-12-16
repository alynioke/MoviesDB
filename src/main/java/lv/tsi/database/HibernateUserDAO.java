package lv.tsi.database;

import lv.tsi.entities.User;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class HibernateUserDAO 
{
	DatabaseHandler dbHandler = DatabaseHandler.instance;
	
    public List<User> getByLoginAndPassword(String login, String hexPassword)
    {        
        Session session = dbHandler.getSession();
        session.beginTransaction();
    
        Criteria criteria = session.createCriteria(User.class).add(
                Restrictions.and(
                        Restrictions.like("login", login), 
                        Restrictions.eq("password", hexPassword)
                ));
        @SuppressWarnings("unchecked")
        List<User> user = criteria.list();
        session.getTransaction().commit();
        return user;
    }
}
