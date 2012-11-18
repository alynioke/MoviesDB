package index;
import org.apache.wicket.IClusterable;

public class Movie implements IClusterable
{

    private String title;
    private int year;
    private String img;

    public Movie()  
    {  
    	
    }


    public Movie(String title, int year, String img)
    {
        this.title = title;
        this.year = year;
        this.img = img;
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


}