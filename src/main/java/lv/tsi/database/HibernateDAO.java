package lv.tsi.database;


import index.Rating;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


public class HibernateDAO {

	@SuppressWarnings("unchecked")
	public static <T> T selectById(Class<T> itemClass, int id){
		//DatabaseHandler.connect();
		Session session = DatabaseHandler.getSession();
		session.beginTransaction();
		T object = (T) session.get(itemClass, id);
		session.getTransaction().commit();
		return object;
	}
	public static <T> void insert(T object){
		Session session = DatabaseHandler.getSession();
		session.beginTransaction();
		session.save(object);
		session.getTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> selectAll(Class<T> itemClass){
		Session session = DatabaseHandler.getSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(itemClass);
		List<T> objects = criteria.list();
		session.getTransaction().commit();
		return objects;
	}
	

}
