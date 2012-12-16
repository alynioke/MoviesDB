package lv.tsi.pages;

import lv.tsi.database.HibernateDAO;
import lv.tsi.entities.Movie;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.PageParameters;

public class MoviesList extends Homepage
{
    DataView<Movie> dataView;
    final MoviesPanel p;
    private static final int MOVIES_PER_PAGE = 8;
	HibernateDAO hibernateDAO = new HibernateDAO();
    
    public MoviesList() 
    {
        final List<Movie> moviesList = hibernateDAO.selectAll(Movie.class);

        p = new MoviesPanel("moviesPanel");        
        dataView = new DataView<Movie>("pageable", new ListDataProvider<Movie>(moviesList))
        	{
                private static final long serialVersionUID = 1L;
                @Override
                public void populateItem(final Item<Movie> item)
                {
                    final Movie mov = item.getModelObject();
                    
                    item.add(new Label("title", mov.getTitle()));
                    item.add(new Label("year", String.valueOf(mov.getYear())));
                    PageParameters params = new PageParameters();
                    params.add("movieId", Integer.toString(mov.getId()));
                    BookmarkablePageLink bookmarkablePageLink = 
                    		new BookmarkablePageLink("link", MoviePreview.class , params);
                    bookmarkablePageLink.add(new WebImage("img", mov.getImg()));
                    item.add(bookmarkablePageLink);
                }
            };

        dataView.setItemsPerPage(MOVIES_PER_PAGE);
        dataView.setOutputMarkupId(true);
        p.add(dataView);    

        AjaxFallbackLink<MoviesList> orderByTitle = new AjaxFallbackLink<MoviesList>("orderByTitle") 
        	{
	            @Override
	            public void onClick(AjaxRequestTarget target) 
	            {
	                MovieTitleComparator movieTitleComparator = new MovieTitleComparator();
	                Collections.sort(moviesList, movieTitleComparator);
	                if (target != null) {
	                    target.addComponent(p); 
	                }
	                //ajax can't update repeater but can update component which stores repeater
	            }
        	};
        	
        AjaxFallbackLink<MoviesList> orderByYear = new AjaxFallbackLink<MoviesList>("orderByYear") 
        	{
	            @Override
	            public void onClick(AjaxRequestTarget target) 
	            {
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
        add(new PagingNavigator("navigatorTop", dataView));
        add(new PagingNavigator("navigatorBottom", dataView));
    }
}
