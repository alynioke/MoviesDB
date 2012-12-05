package index;
import org.apache.wicket.IClusterable;

public class Movie implements IClusterable
{

	private static final long serialVersionUID = 1L;
	private String title;
    private int year;
    private String img;
    private int id;
    private String genre;
    private String actors;
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