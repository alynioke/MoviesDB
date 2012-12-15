package lv.tsi.entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table (name="rating")
public class Rating
{
    @Id
    @GeneratedValue
    private int id;
    
    private int user_id;
    private int movie_id;
    private int rating;
    
    public Rating()  
    {  
        
    }
    public Rating(int user_id, int movie_id, int rating) 
    {
        this.user_id = user_id;
        this.movie_id = movie_id;
        this.rating = rating;
    }

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public int getUser_id() 
    {
        return user_id;
    }

    public void setUser_id(int user_id) 
    {
        this.user_id = user_id;
    }

    public int getMovie_id() 
    {
        return movie_id;
    }

    public void setMovie_id(int movie_id) 
    {
        this.movie_id = movie_id;
    }

    public int getRating() 
    {
        return rating;
    }

    public void setRating(int rating) 
    {
        this.rating = rating;
    }
}