package index;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lv.tsi.database.DatabaseHandler;
import lv.tsi.database.HibernateDAO;
import lv.tsi.database.HibernateRatingDAO;

import org.apache.wicket.IClusterable;
import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.rating.RatingPanel;
import org.apache.wicket.markup.html.PackageResourceReference;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;


@SuppressWarnings("deprecation")
public class MoviePreview extends Homepage{
	private Boolean hasVoted = false;
	private int movieId;
	
    public static class RatingModel implements IClusterable
    {
		private static final long serialVersionUID = 1L;
		private int nrOfVotes = 0;
        private int sumOfRatings = 0;
        private double rating = 0;

        public void setRating(double rating){
        	this.rating = rating; 
        }
        public void setSumOfRatings(int sumOfRatings){
        	this.sumOfRatings = sumOfRatings; 
        }
        public void setNrOfVotes(int nrOfVotes){
        	this.nrOfVotes = nrOfVotes; 
        }
        
        public boolean isActive(int star)
        {
            return star < ((int)(rating + 0.5));
        }
        public Integer getNrOfVotes()
        {
            return nrOfVotes;
        }
        
        public void addRating(int nrOfStars)
        {
            nrOfVotes++;
            sumOfRatings += nrOfStars;
            rating = sumOfRatings / (1.0 * nrOfVotes);
        }

        public Double getRating()
        {
            return rating;
        }

        public int getSumOfRatings()
        {
            return sumOfRatings;
        }
    }
    
    private RatingModel rating = new RatingModel();
    
	public MoviePreview(PageParameters params){
		addComponents(params);
	}

	
	public void addComponents(PageParameters params) {
		final int movieId = params.getInt("movieId");

		Movie movie = HibernateDAO.selectById(Movie.class, movieId);
		

        add(new Label("title", movie.getTitle()));
        add(new Label("year", Integer.toString(movie.getYear())));
        add(new Label("genre", movie.getGenre().getTitle()));
        add(new Label("description",movie.getDescription()));
        add(new Label("actors", movie.getActors()));
        add(new WebImage("img", movie.getImg()));


        List<Rating> results = HibernateRatingDAO.getByMovieIdAndUserId(movieId, 0);
		

		int nrOfVotes = results.size(), sumOfRatings = 0;
		for (Rating res: results) {
			sumOfRatings += res.getRating();
		}
		float ratingValue = ((float)sumOfRatings) / ((float)nrOfVotes);
        
		
		rating.setNrOfVotes(nrOfVotes);
        rating.setRating(ratingValue);
        rating.setSumOfRatings(sumOfRatings);
        
        add(new RatingPanel("rating", 
        		new PropertyModel<Integer>(rating, "rating"), 
        		new Model<Integer>(10),
                new PropertyModel<Integer>(rating, "nrOfVotes"), 
                new Model<Boolean>(this.getHasVoted()),
                false)
            {
				private static final long serialVersionUID = 1L;

				@Override
				protected String getActiveStarUrl(int iteration)
	            {
	                return "http://yuiblog.com/assets/starTutorial/one-star.png";
	            }
				@Override
				protected String getInactiveStarUrl(int iteration)
	            {
	                return "http://yuiblog.com/assets/starTutorial/no-star.png";
	            }
				@Override
				public boolean onIsStarActive(int star)
                {
                    return rating.isActive(star);
                }

				@Override
                public void onRated(int ratingValue, AjaxRequestTarget target)
                {
                	if  (!getHasVoted()) {
                		Rating newRating = new Rating(((SignInSession)Session.get()).getUser().getId(), 
                				movieId, ratingValue);   
                		HibernateDAO.insert(newRating);
	                	rating.addRating(ratingValue);
                	}
                }
         });	
        
        
	}
	
    public boolean getHasVoted()
    {
    	
    	if (((SignInSession)Session.get()).getUser() == null) {
    		return true;
    	} else {
    		int userId = ((SignInSession)Session.get()).getUser().getId();
    		List<Rating> existingRating = HibernateRatingDAO.getByMovieIdAndUserId(movieId, userId);

    		for (Rating er: existingRating) {
    			if(er.getId() > 0) 
			    {
		    	    this.setHasVoted(true);
			    } else {
			    	this.setHasVoted(false);
			    }
        	    System.out.println("has voted="+hasVoted+" and ID="+er.getId());
    		}
    		return hasVoted;
    	}
    }
    public void setHasVoted(boolean b)
    {
    	hasVoted = b;
    }


}
