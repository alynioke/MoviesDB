package lv.tsi.database;


import index.Rating;

import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class HibernateRatingDAO {
	public static List<Rating> getByMovieIdAndUserId(int movieId, int userId){ //use vararg or what? there are no default parameters in Java
		Criteria criteria;
		
		Session session = DatabaseHandler.getSession();
		session.beginTransaction();
		if (userId == 0) {
			criteria = session.createCriteria(Rating.class).add(Restrictions.eq("movie_id", movieId));
		} else {
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
