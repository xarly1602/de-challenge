package com.walmart.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Create your own Microservice here, You are free to create all the folders you want
 * We give you the Maven Archetype with the Hello World :)
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.walmart.org")
@EnableJpaRepositories("com.walmart.org.model.repositories")
public class App 
{
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
