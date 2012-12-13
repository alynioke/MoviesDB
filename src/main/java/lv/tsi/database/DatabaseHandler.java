package lv.tsi.database;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class DatabaseHandler {

	static Session session;

	public static void connect() {
		SessionFactory factory;
		ServiceRegistry serviceRegistry;
		Configuration configuration = new Configuration();
		configuration.configure();
		serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry(); 
		factory = configuration.buildSessionFactory(serviceRegistry);
		session = factory.openSession();		
	}

	public static Session getSession() {
		return session;
	}

	public static void close() {
		session.close();
		session = null;
	}

}

