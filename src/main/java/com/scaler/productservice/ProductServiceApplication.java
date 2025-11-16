package com.scaler.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = {FlywayAutoConfiguration.class})
public class ProductServiceApplication {

    public static void main(String[] args) {
//        List<Integer> list = new ArrayList<>();
//        List<String> list2 = new ArrayList<>();
//        list.add(1);
//        System.out.println(list.size());
//        System.out.println(list.getClass());
//        System.out.println(list2.getClass());
        SpringApplication.run(ProductServiceApplication.class, args);
    }

}

//What is the use of ProductServiceApplication in a Spring Boot project?
//The ProductServiceApplication class is the main entry point of your Spring Boot application.
// It is required because it boots up the Spring framework and starts the application.
//
//1️⃣ Where is this class located?
//It is usually found in the src/main/java/com/example/ProductServiceApplication.java file.
//
//A typical ProductServiceApplication class looks like this:
//
//@SpringBootApplication
//public class ProductServiceApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(ProductServiceApplication.class, args);
//    }
//}
//2️⃣ Why is this class required?
//✅ 1. It starts the Spring Boot application
//The main method runs the SpringApplication.run(...), which boots up the entire Spring Boot application.
//It initializes the Spring Context (Application Context), which is responsible for managing beans and handling dependency injection.
//✅ 2. It enables Spring Boot auto-configuration
//The @SpringBootApplication annotation automatically configures many things, like:
//Setting up embedded Tomcat (for running the web server).
//Scanning for @RestController, @Service, @Repository, and other beans.
//Loading application properties (application.properties or application.yml).
//✅ 3. It performs component scanning
//The @SpringBootApplication annotation tells Spring Boot to scan the package and sub-packages for
// components (@Controller, @Service, @Repository, etc.).
//Without this class, Spring wouldn't know which classes to initialize.
//3️⃣ Why do we need this if we already have controllers and services?
//Your controller classes (@RestController) handle HTTP requests, but they do not start the Spring Boot application.
//Your service classes (@Service) contain business logic, but they do not initialize the Spring framework.
//ProductServiceApplication brings everything together by:
//Starting the web server.
//Scanning and registering beans.
//Managing dependencies.
//4️⃣ What happens if I delete ProductServiceApplication?
//If you delete this class: ❌ The application won't start because there is no main method to launch Spring Boot.
//❌ Spring won't scan for controllers, services, or repositories.
//❌ You won't be able to access APIs since the web server won't run.
//
//5️⃣ What is @SpringBootApplication doing?
//This annotation is a shortcut for three annotations:
//@SpringBootApplication  // Equivalent to these three:
//@ComponentScan  // Finds and registers beans
//@EnableAutoConfiguration  // Configures dependencies automatically
//@Configuration  // Allows defining custom beans
//Without @SpringBootApplication, you would have to write:
//@ComponentScan
//@EnableAutoConfiguration
//@Configuration
//public class ProductServiceApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(ProductServiceApplication.class, args);
//    }
//}

//🔥 Key Takeaways
//✅ ProductServiceApplication is the entry point of your Spring Boot application.
//✅ It boots up Spring and initializes the Spring Context.
//✅ It enables auto-configuration and component scanning.
//✅ Without it, your controllers and services won’t work because Spring won’t start.