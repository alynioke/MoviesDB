package lv.tsi.database;
import java.util.List;

import lv.tsi.entities.Genre;
import lv.tsi.entities.Movie;
import lv.tsi.entities.User;
import lv.tsi.pages.SignInSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class DatabaseHandler {

    static Session session;
    //если не статический - то смогу подменять объект для тестов
    
    public static DatabaseHandler instance = new DatabaseHandler();
    
    public void databaseInit() 
    {
    	connect();
    	HibernateDAO hibernateDAO = new HibernateDAO();

    	List<User> users = hibernateDAO.selectAll(User.class);
    	if (users.size() == 0) {
    		String password = SignInSession.getHash("admin");
			User admin = new User("admin", password , "Admin", "Admin");
    		hibernateDAO.insert(admin);
    	}
    	Genre genre;
    	Movie movie;
    	List<Genre> genres = hibernateDAO.selectAll(Genre.class);
    	if (genres.size() == 0) {
			genre = new Genre("Drama");
    		hibernateDAO.insert(genre);
    		movie = new Movie(
    				"The Godfather", 
    				1972, 
    				"http://ia.media-imdb.com/images/M/MV5BMTIyMTIxNjI5NF5BMl5BanBnXkFtZTcwNzQzNDM5MQ@@._V1._SY317_CR2,0,214,317_.jpg", 
    				genre, 
    				"Marlon Brando, Al Pacino and James Caan", 
    				"The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."
    			);
    		hibernateDAO.insert(movie);

			genre = new Genre("Action");
    		hibernateDAO.insert(genre);
    		movie = new Movie(
    				"Batman", 
    				2010, 
    				"http://ia.media-imdb.com/images/M/MV5BMTk4ODQzNDY3Ml5BMl5BanBnXkFtZTcwODA0NTM4Nw@@._V1._SY317_.jpg", 
    				genre, 
    				"Christian Bale, Heath Ledger and Aaron Eckhart", 
    				"When Batman, Gordon and Harvey Dent launch an assault on the mob, they let the clown out of the box, the Joker, bent on turning Gotham on itself and bringing any heroes down to his level."
    			);
    		hibernateDAO.insert(movie);

			genre = new Genre("Fantasy");
    		hibernateDAO.insert(genre);
    		movie = new Movie(
    				"The Lord of The Rings: Return Of The King", 
    				2003, 
    				"http://ia.media-imdb.com/images/M/MV5BMjE4MjA1NTAyMV5BMl5BanBnXkFtZTcwNzM1NDQyMQ@@._V1._SY317_.jpg", 
    				genre, 
    				"Elijah Wood, Viggo Mortensen and Ian McKellen", 
    				"Aragorn leads the World of Men against Sauron''s army to draw the dark lord''s gaze from Frodo and Sam who are on the doorstep of Mount Doom with the One Ring."
    			);
    		hibernateDAO.insert(movie);	
    	}
    }
    
    public void connect() 
    {
        SessionFactory factory;
        ServiceRegistry serviceRegistry;
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry(); 
        factory = configuration.buildSessionFactory(serviceRegistry);
        session = factory.openSession();        
    }

    public Session getSession() 
    {
        return session;
    }

    public void close() 
    {
        session.close();
        session = null;
    }
}