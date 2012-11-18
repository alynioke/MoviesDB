package index;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;


import org.apache.wicket.PageParameters;

public class MoviesList extends Homepage{

	public MoviesList(PageParameters params) {
		DatabaseManager db = DatabaseManager.getInstance();
    	String order = params.getString("order");
    	int moviesPerPage = 10;
    	//paging begin
    	int totalPages = 0;
    	String page;
    	if (params.getString("page") == null)
    		page = "0";
    	else
    		page = Integer.toString((params.getInt("page")-1)*moviesPerPage);
    	ResultSet rs = db.select("SELECT COUNT(*) FROM movie");
    	try {
			while (rs.next()) {
			    totalPages = (int) Math.ceil(rs.getFloat("COUNT(*)")/moviesPerPage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		add(new Label("pages", Integer.toString(totalPages)));	
		//paging end
		
    	rs = db.select("SELECT title, year, img FROM movie ORDER BY "+order+" ASC LIMIT "+page+","+Integer.toString(moviesPerPage)+"");
    	
        Movie movie = null;
        final List<Movie> moviesList = new ArrayList<Movie>();

        
	    try {
			while (rs.next()) {
			    int y = rs.getInt("year");
			    String t = rs.getString("title");
			    String i = rs.getString("img");
			    System.out.println(t);
		        movie = new Movie(t, y, i);
		        moviesList.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
        

        final DataView<Movie> dataView =
            new DataView<Movie>("simple", new ListDataProvider<Movie>(moviesList)){

                private static final long serialVersionUID = 1L;

                @Override
                public void populateItem(final Item<Movie> item){
                    final Movie mov = item.getModelObject();
                    item.add(new Label("title", mov.getTitle()));
                    item.add(new Label("year", String.valueOf(mov.getYear())));
                    item.add(new WebImage("img", mov.getImg()));
                }
            };

        //dataView.setItemsPerPage(10);

        add(dataView);	    

	}

}
