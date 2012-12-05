package index;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
//import org.apache.wicket.request.mapper.parameter.PageParameters;

public class MoviesList extends Homepage{
	DataView<Movie> dataView;
	final MyPanel p;

	public MoviesList() {
		DatabaseManager db = DatabaseManager.getInstance();
    	String order = "title";
    	int moviesPerPage = 8;
    	int totalPages = 0;
    	String page = "0";
    	ResultSet rs = db.select("SELECT COUNT(*) FROM movie");
    	try {
			while (rs.next()) {
			    totalPages = (int) Math.ceil(rs.getFloat("COUNT(*)")/moviesPerPage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
    	rs = db.select("SELECT id, title, year, img FROM movie");//  ORDER BY "+order+" ASC LIMIT "+page+","+Integer.toString(moviesPerPage)+"
    	
        Movie movie = null;
        final List<Movie> moviesList = new ArrayList<Movie>();
        
	    try {
			while (rs.next()) {
			    int y = rs.getInt("year");
			    String t = rs.getString("title");
			    String i = rs.getString("img");
			    int id = rs.getInt("id");
		        movie = new Movie(id, t, y, i, null);
		        moviesList.add(movie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
	    p = new MyPanel("moviesPanel");	    
        dataView = new DataView<Movie>("pageable", new ListDataProvider<Movie>(moviesList)){

                private static final long serialVersionUID = 1L;
                @Override
                public void populateItem(final Item<Movie> item){
                	String order = "title";
                    final Movie mov = item.getModelObject();
                    item.add(new Label("title", mov.getTitle()));
                    PageParameters params = new PageParameters();
                    params.add("movieId", Integer.toString(mov.getId()));
                    
                    BookmarkablePageLink bookmarkablePageLink = new BookmarkablePageLink("link", MoviePreview.class , params);
                    bookmarkablePageLink.add(new WebImage("img", mov.getImg()));
					item.add(bookmarkablePageLink);
					
                    item.add(new Label("year", String.valueOf(mov.getYear())));
                    
                }
            };

        dataView.setItemsPerPage(8);
        dataView.setOutputMarkupId(true);
        p.add(dataView);	
        
	    AjaxFallbackLink<MoviesList> link = new AjaxFallbackLink<MoviesList>("orderByTitle") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				MovieTitleComparator movieTitleComparator = new MovieTitleComparator();
				Collections.sort(moviesList, movieTitleComparator);
				if (target != null)  {
					target.addComponent(p); 
				}
				//ajax'ом нельзя обновить repeater, а надо обновить компонент, в котором он лежит
			}
		};
		p.add(link);
	    add(p);
	    
	    add(new PagingNavigator("navigator", dataView));
	    

	}

}
