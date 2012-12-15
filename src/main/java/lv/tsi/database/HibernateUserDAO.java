package lv.tsi.database;

import lv.tsi.entities.User;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class HibernateUserDAO 
{
    public static List<User> getByLoginAndPassword(String login, StringBuffer hexPassword)
    {        
        Session session = DatabaseHandler.getSession();
        session.beginTransaction();
    
        Criteria criteria = session.createCriteria(User.class).add(
                Restrictions.and(
                        Restrictions.like("login", login), 
                        Restrictions.eq("password", hexPassword.toString())
                ));
        @SuppressWarnings("unchecked")
        List<User> user = criteria.list();
        session.getTransaction().commit();
        return user;
    }
}
