package lv.tsi.database;

import lv.tsi.entities.Rating;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class HibernateDAO 
{
	DatabaseHandler dbHandler = DatabaseHandler.instance;
	//чтоб не работать с базой - нам надо вернуть мок базы
	
    @SuppressWarnings("unchecked")
    public <T> T selectById(Class<T> itemClass, int id)
    {
    	if (itemClass == null) {
    		throw new NullPointerException("parameter is null at method selectById");
    	}
    	//тут надо чтоб вернул НАШУ сессию
        Session session = dbHandler.getSession();
        session.beginTransaction();
        T object = (T) session.get(itemClass, id);
        session.getTransaction().commit();
        return object;
    }
    
    public <T> void insert(T object)
    {
        Session session = dbHandler.getSession();
        session.beginTransaction();
        session.save(object);
        session.getTransaction().commit();
    }
    
    @SuppressWarnings("unchecked")
    public <T> List<T> selectAll(Class<T> itemClass)
    {
    	if (itemClass == null) {
    		throw new NullPointerException("parameter is null at method selectAll");
    	}
        Session session = dbHandler.getSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(itemClass);
        List<T> objects = criteria.list();
        session.getTransaction().commit();
        return objects;
    }
}
