package index;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.wicket.IClusterable;

@Entity
@Table (name="movie")
public class Movie implements IClusterable
{
	@Id
	@GeneratedValue
	@Column(name = "id")
    private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "year")
    private int year;

	@Column(name = "img")
    private String img;

	@Column(name = "genre")
    private String genre;

	@Column(name = "actors")
    private String actors;

	@Column(name = "description")
    private String description;

    public Movie()  
    {  
    	
    }


    public Movie(int id, String title, int year, String img, String genre)
    {
        this.title = title;
        this.year = year;
        this.img = img;
        this.id = id;
        this.genre = genre;
    }

    public Movie(int id, String title, int year, String img, 
    		String genre, String actors, String description)
    {
        this.title = title;
        this.year = year;
        this.img = img;
        this.id = id;
        this.genre = genre;
        this.setActors(actors);
        this.setDescription(description);
    }


    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public String getImg() {
		return img;
	}


	public void setImg(String img) {
		this.img = img;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public String getActors() {
		return actors;
	}


	public void setActors(String actors) {
		this.actors = actors;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


}