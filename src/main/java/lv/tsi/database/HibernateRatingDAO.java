package lv.tsi.database;

import lv.tsi.entities.Rating;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class HibernateRatingDAO 
{
	DatabaseHandler dbHandler = DatabaseHandler.instance;
	
	 public List<Rating> getByMovieIdAndUserId(int movieId, int userId) 
	 {
        Criteria criteria;
        Session session = dbHandler.getSession();
        session.beginTransaction();
        criteria = session.createCriteria(Rating.class).add(
                Restrictions.and(
                        Restrictions.eq("movie_id", movieId), 
                        Restrictions.eq("user_id", userId)
            ));
        @SuppressWarnings("unchecked")
        List<Rating> results = criteria.list();
        session.getTransaction().commit();
        return results;
	 } 
	 
	 public List<Rating> getRatingForMovie(int movieId) 
	 {
        Criteria criteria;
        Session session = dbHandler.getSession();
        session.beginTransaction();
        criteria = session.createCriteria(Rating.class).add(Restrictions.eq("movie_id", movieId));
        
        @SuppressWarnings("unchecked")
        List<Rating> results = criteria.list();
        session.getTransaction().commit();
        return results;
	 }
}