package index;

import java.sql.ResultSet;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.rating.RatingPanel;

import java.sql.SQLException;
import java.net.*;
import java.io.*;


import org.apache.wicket.IClusterable;
import org.apache.wicket.PageParameters;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.PackageResourceReference;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SuppressWarnings("deprecation")
public class MoviePreview extends Homepage{
	private Boolean hasVoted = false;
	
    public static class RatingModel implements IClusterable
    {
		private static final long serialVersionUID = 1L;
		private int nrOfVotes = 0;
        private int sumOfRatings = 0;
        private double rating = 0;

        /**
         * Returns whether the star should be rendered active.
         * 
         * @param star
         *            the number of the star
         * @return true when the star is active
         */
        public boolean isActive(int star)
        {
            return star < ((int)(rating + 0.5));
        }

        /**
         * Gets the number of cast votes.
         * 
         * @return the number of cast votes.
         */
        public Integer getNrOfVotes()
        {
            return nrOfVotes;
        }

        /**
         * Adds the vote from the user to the total of votes, and calculates the rating.
         * 
         * @param nrOfStars
         *            the number of stars the user has cast
         */
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
    
    private static RatingModel rating = new RatingModel();
    
	public MoviePreview(){
	}
	public MoviePreview(PageParameters params){
		addComponents(params);
	}

	
	
	public void addComponents(PageParameters params) {
		DatabaseManager db = DatabaseManager.getInstance();
		String movieId = params.getString("movieId");
		//syso + ctrl + space
		String q = "SELECT movie.*, genres.title as genre FROM movie " +
				" LEFT JOIN genres ON movie.genre_id = genres.id" +
				" WHERE movie.id = "+movieId;
		ResultSet rs = db.select(q);

        Movie movie = null;        
	    try {
			while (rs.next()) {
			    int y = rs.getInt("year");
			    String t = rs.getString("title");
			    String i = rs.getString("img");
			    String g = rs.getString("genre");
			    int id = rs.getInt("id");
			    String d = rs.getString("description");
			    String a = rs.getString("actors");
		        movie = new Movie(id, t, y, i, g, a, d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

        add(new Label("title", movie.getTitle()));
        add(new Label("year", Integer.toString(movie.getYear())));
        add(new Label("genre", movie.getGenre()));
        add(new Label("description", movie.getDescription()));
        add(new Label("actors", movie.getActors()));
        
        add(new WebImage("img", movie.getImg()));
        
        add(new RatingPanel("rating", 
        		new PropertyModel<Integer>(rating, "rating"), 
        		new Model<Integer>(10),
                new PropertyModel<Integer>(rating, "nrOfVotes"), 
                new Model<Boolean>(this.getHasVoted()),
                false)
            {
				private static final long serialVersionUID = 1L;

				protected String getActiveStarUrl(int iteration)
	            {
	                return "http://yuiblog.com/assets/starTutorial/one-star.png";
	            }
				protected String getInactiveStarUrl(int iteration)
	            {
	                return "http://yuiblog.com/assets/starTutorial/no-star.png";
	            }
				public boolean onIsStarActive(int star)
                {
                    return MoviePreview.rating.isActive(star);
                }

                public void onRated(int ratingValue, AjaxRequestTarget target)
                {
                	setHasVoted(true);
                	MoviePreview.rating.addRating(ratingValue);
                }
         });	
        
	}
    public boolean getHasVoted()
    {
    	/*
    	 * Если пользователь незалогинен - возвращать ВСЕГДА TRUE
    	 * Если пользователь залогинен, то
    	 * ---если он проголосовал, т.е. его голос есть в базе - возвращать TRUE,
    	 * ---иначе, если не проголосовал, т.е. голоса нет в базе - возвращать FALSE. 
    	 */
    	if (((SignInSession)Session.get()).getUser() == null) {
    		return true;
    	} else {
    		return hasVoted;
    	}
    }
    public void setHasVoted(boolean b)
    {
    	hasVoted = b;
    }

}
