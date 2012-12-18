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
        if (userId == 0) {
        	//if userId is null I need all ratings for movieId
            criteria = session.createCriteria(Rating.class).add(Restrictions.eq("movie_id", movieId));
        } else {
        	//if userId is not null I need to check if this user have rated the movie
            criteria = session.createCriteria(Rating.class).add(
                    Restrictions.and(
                            Restrictions.eq("movie_id", movieId), 
                            Restrictions.eq("user_id", userId)
                    ));
        }
        @SuppressWarnings("unchecked")
        List<Rating> results = criteria.list();
        session.getTransaction().commit();
        return results;
    }
}