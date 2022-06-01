package mx.relations.jpa.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@ComponentScan(basePackages = {"relations.jpa"})
@PropertySource(value = "classpath:database.properties")
public class DataBaseConfig {
	
	/*
	 * NOTA: NO se usara @Transations, por lo que no declaramos Class JpaTransactionManager y 
	 * @EnableTransactionManagement, se usaran las transacciones a través del EntityManager
	 * para lograr tener un mejor entendimiento de las mismas. 
	 */
	
	
	/*
	 * -Environment: Interfaz que representa el entorno en el que se ejecuta una aplicación actual.
	 *  Se pueden obtener perfiles y propiedades del entorno de la aplicación. 
	 */
	@Autowired
	private Environment env; 
	
	
	/*
	 * -Class DataSource: Retorna contexto de conexión al base de datos. 
	 * -setDriverClassName(): Requiere driver de la base de datos.
	 * -setUrl(): Requiere URL de conexión de la base de datos, se usa como JDBC
	 * -setUsername(): Requiere usuario de la base de datos. 
	 * -setPassword(): Requiere contraseña del usuario de la base de datos. 
	 */
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("javax.persistence.jdbc.driver"));
		dataSource.setUrl(env.getProperty("javax.persistence.jdbc.url"));
		dataSource.setUsername(env.getProperty("javax.persistence.jdbc.user"));
		dataSource.setPassword(env.getProperty("javax.persistence.jdbc.password"));
		return dataSource;
	}
	
	
	/*
	 * -Class Properties: Retorna las propiedades del ORM Hibernate.
	 * -put(): Requiere Key y Valor. Entonces: 
	 * 		hibernate.dialect: Genera SQL apropidado segun la base de datos elegida. 
	 * 		hibernate.show_sql: Escribe todas las declaraciones SQL a la consola. Segun el dialecto.   
	 * 		hibernate.format_sql: Imprimi el SQL con formato de espacios y enters.
	 * 		hibernate.hbm2ddl.auto: Exporta o valida automaticamente DDL de esquina a la base de datos.
	 * Para más opciones personalidas: https://www.tutorialspoint.com/hibernate/hibernate_configuration.htm
	 */
	public Properties hibernateProperties(){
		Properties properties = new Properties(); 
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
		return properties; 
		
	}
	
	/*
	 * Para más información: https://docs.spring.io/spring-framework/docs/3.0.0.M4/reference/html/ch13s05.html
	 * 
	 * -EntityManagerFactory entityManagerFactory(): Retorna EntityManagerFactory la cual crea y gestiona las
	 * instancias EntityManager.
	 * -Recordar que el EntityManager: Es la interfaz que gestiona la persistencia de objetos y funciona como
	 * instancia de consultas.
	 * -LocalContainerEntityManagerFactoryBean: Spring ORM nos brinda control TOTAL sobre la configuración
	 * EntityManagerFactory, crea una instancia PersistenceUnitInfo basada en el archivo "persistencen.xml". 
	 * -setDataSource(DataSource dataSource): Especifica el origen de datos JDBC que usara el proveedor de 
	 * persistencia de JPA. 
	 * -setPackagesToScan(String... packagesToScan): Establese si usar el escaneo basado en Spring para las clases
	 * de entidad en el classpath en lugar de usar el escaneo estandar de JPA del archivo persitence.xml.
	 * -Class HibernateJpaVendorAdapter: Implementación para configucación Hibernate EntityManager
	 * -setJpaVendorAdapter(JpaVendorAdapter jpaVendorAdapter): Especifica que la implementación.
	 * -setJpaProperties(Properties jpaProperties): Especifica las propiedades de JPA.
	 * -afterPropertiesSet(): Invocado por el contenedor BeanFactory después haber establecido todas las propiedades
	 * -getObject(): devuelve el EntityManagerFactory
	 */
	@Bean
	public EntityManagerFactory entityManagerFactory(){
		LocalContainerEntityManagerFactoryBean lcemf = new LocalContainerEntityManagerFactoryBean(); 
		lcemf.setDataSource(dataSource());
		lcemf.setPackagesToScan("mx.relations.jpa.entities");
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter(); 
		lcemf.setJpaVendorAdapter(jpaVendorAdapter);
		lcemf.setJpaProperties(hibernateProperties());
		lcemf.afterPropertiesSet();
		return lcemf.getObject();
	}



}
