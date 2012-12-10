package index;

import org.apache.wicket.IClusterable;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.PackageResourceReference;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.Session;


@SuppressWarnings("deprecation")
public class MoviePreview extends Homepage{
	private Boolean hasVoted = false;
	private DatabaseManager db = DatabaseManager.getInstance();
	private String movieId;
	
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
    
    private RatingModel rating = new RatingModel();
    
	public MoviePreview(){
	}
	public MoviePreview(PageParameters params){
		movieId = params.getString("movieId");
		addComponents(params);
	}

	
	
	public void addComponents(PageParameters params) {
		final int movieId = params.getInt("movieId");

		
		DatabaseHandler.connect();
		org.hibernate.Session session = DatabaseHandler.getSession();
		session.beginTransaction();
		Movie mov = (Movie)session.get(Movie.class, movieId);
		session.getTransaction().commit();
		
		//mov = hDBmanager.select(87)

        add(new Label("title", mov.getTitle()));
        add(new Label("year", Integer.toString(mov.getYear())));
        add(new Label("genre", mov.getGenre().getTitle()));
        add(new Label("description",mov.getDescription()));
        add(new Label("actors", mov.getActors()));
        add(new WebImage("img", mov.getImg()));
        
		
		/*
		String q = "SELECT movie.*, genres.title as genre FROM movie " +
				" LEFT JOIN genres ON movie.genre_id = genres.id" +
				" WHERE movie.id = "+Integer.toString(movieId);
		//MoviesDatabaseManager
		ResultSet rs = db.select(q);

        Movie movie = null;        
	    try {
			rs.next();
			int y = rs.getInt("year");
			String t = rs.getString("title");
			String i = rs.getString("img");
			String g = rs.getString("genre");
			int id = rs.getInt("id");
			String d = rs.getString("description");
			String a = rs.getString("actors");
		    movie = new Movie(id, t, y, i, g, a, d);
		} catch (SQLException e) {
			e.printStackTrace();
		}

        add(new Label("title", movie.getTitle()));
        add(new Label("year", Integer.toString(movie.getYear())));
        add(new Label("genre", movie.getGenre()));
        add(new Label("description", movie.getDescription()));
        add(new Label("actors", movie.getActors()));
        add(new WebImage("img", movie.getImg()));
        

        String q = "SELECT movie.*, genres.title as genre FROM movie " +
				" LEFT JOIN genres ON movie.genre_id = genres.id" +
				" WHERE movie.id = "+movieId;
		//MoviesDatabaseManager
		ResultSet rs = db.select(q);
		
        Movie movie = null;        
	    try {
			rs.next();
			int y = rs.getInt("year");
			String t = rs.getString("title");
			String i = rs.getString("img");
			String g = rs.getString("genre");
			int id = rs.getInt("id");
			String d = rs.getString("description");
			String a = rs.getString("actors");
		    movie = new Movie(id, t, y, i, g, a, d);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
                	//тут пишем голос в базу
                	if  (!getHasVoted()) {
	                	String q = "INSERT INTO rating VALUES (NULL, "
	                	+((SignInSession)Session.get()).getUser().getId()+"," +
	                			" "+Integer.toString(movieId)+", "+ratingValue+")";
	                	
	            		db.insert(q);            		
	                	rating.addRating(ratingValue);
                	}
                }
         });	
        */
        
	}
	/*
    public boolean getHasVoted()
    {
    	
    	if (((SignInSession)Session.get()).getUser() == null) {
    		return true;
    	} else {
    		String q = "SELECT * FROM rating WHERE movie_id = "+movieId+" AND user_id = "+((SignInSession)Session.get()).getUser().getId();
    		ResultSet rs = db.select(q);
    
    	    try {
    			while (rs.next()) {
    			    if(rs.getInt("id") > 0) 
    			    {
    		    	    this.setHasVoted(true);
    			    } else {
    			    	this.setHasVoted(false);
    			    }	}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	    System.out.println("has voted="+hasVoted);
    		return hasVoted;
    	}
    }
    public void setHasVoted(boolean b)
    {
    	hasVoted = b;
    }

*/
}
