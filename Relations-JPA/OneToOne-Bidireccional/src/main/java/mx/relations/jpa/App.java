package mx.relations.jpa;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import mx.relations.jpa.config.DataBaseConfig;



public class App {

	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataBaseConfig.class);
		context.close();
	}

}
