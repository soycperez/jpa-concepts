package mx.relations.jpa;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import mx.relations.jpa.config.DatabaseConfig;





public class App {

	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
		context.close();
	}

}
