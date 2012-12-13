package index;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lv.tsi.database.HibernateDAO;

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
	final MoviesPanel p;

	public MoviesList() {
		
		final List<Movie> moviesList = HibernateDAO.selectAll(Movie.class);
		
    	
	    p = new MoviesPanel("moviesPanel");	    
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

	    AjaxFallbackLink<MoviesList> orderByTitle = new AjaxFallbackLink<MoviesList>("orderByTitle") {
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
	    AjaxFallbackLink<MoviesList> orderByYear = new AjaxFallbackLink<MoviesList>("orderByYear") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				MovieYearComparator movieYearComparator = new MovieYearComparator();
				Collections.sort(moviesList, movieYearComparator);
				if (target != null)  {
					target.addComponent(p); 
				}
			}
		};
		p.add(orderByTitle);
		p.add(orderByYear);
	    add(p);
	    
	    add(new PagingNavigator("navigator", dataView));
	    

	}

}
