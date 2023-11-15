package edu.sandbox.javadatabasetools.hibernate.config;

import edu.sandbox.javadatabasetools.hibernate.model.Author;
import edu.sandbox.javadatabasetools.hibernate.model.Book;
import edu.sandbox.javadatabasetools.hibernate.model.Comment;
import edu.sandbox.javadatabasetools.hibernate.model.Genre;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    @Bean
    public org.hibernate.cfg.Configuration configuration() {
        var configuration = new org.hibernate.cfg.Configuration();
        configuration.configure(HIBERNATE_CFG_FILE);
        return configuration;
    }

    @Bean
    public ServiceRegistry serviceRegistry(org.hibernate.cfg.Configuration configuration) {
        return new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(ServiceRegistry serviceRegistry) {
        var metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClasses(Author.class, Book.class, Comment.class, Genre.class);
        var metadata = metadataSources.buildMetadata();
        return metadata.buildSessionFactory();
    }
}
