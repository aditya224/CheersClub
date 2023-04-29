package com.project.CheersClub.util;

import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.springframework.stereotype.Component;

import com.project.CheersClub.pojo.Address;
import com.project.CheersClub.pojo.User;
import com.project.CheersClub.pojo.Post;
import com.project.CheersClub.pojo.Likes;
import com.project.CheersClub.pojo.Page;
import com.project.CheersClub.pojo.*;

@Component
public class HibernateUtil {
	
	  private static SessionFactory sessionFactory;
	    public static SessionFactory getSessionFactory() {
	        if (sessionFactory == null) {
	            try {
	                Configuration configuration = new Configuration();

	                // Hibernate settings equivalent to hibernate.cfg.xml's properties
	                Properties settings = new Properties();
	                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
	                settings.put(Environment.URL, "jdbc:mysql://127.0.0.1:3306/cheersclub_db?createDatabaseIfNotExist=true");
	                settings.put(Environment.USER, "root");
	                settings.put(Environment.PASS, "Pass1234");
	                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

	                settings.put(Environment.SHOW_SQL, "true");

	                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

	                settings.put(Environment.HBM2DDL_AUTO, "update");

	                configuration.setProperties(settings);

	                configuration.addAnnotatedClass(User.class);
	                configuration.addAnnotatedClass(Address.class);
	                configuration.addAnnotatedClass(Post.class);
	                configuration.addAnnotatedClass(Likes.class);
	                configuration.addAnnotatedClass(Page.class);
	                configuration.addAnnotatedClass(Userpages.class);
	                configuration.addAnnotatedClass(Topic.class);
	                configuration.addAnnotatedClass(Comment.class);

	                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
	                    .applySettings(configuration.getProperties()).build();

	                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        return sessionFactory;
	    }
}
