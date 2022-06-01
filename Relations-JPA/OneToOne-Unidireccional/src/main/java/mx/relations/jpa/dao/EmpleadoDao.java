package mx.relations.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import mx.relations.jpa.entities.Empleado;

@Component
public class EmpleadoDao {
	 
	@PersistenceUnit(name= "entityManagerFactory")
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	public List<Empleado> findAllEmpleados(){
		em = emf.createEntityManager();
		Query query = em.createQuery("FROM Empleado");
		@SuppressWarnings("unchecked")
		List<Empleado> listEmpleados = query.getResultList();
		return listEmpleados;
	}
	public void addEmpleado(Empleado empleado){
		try {
			em = emf.createEntityManager(); 
			em.getTransaction().begin();
			em.persist(empleado);
			em.getTransaction().commit(); 
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		em.close();
	}
	public void deleteByIdEmpleado(int id){
		em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Empleado deleteEmpleado = em.find(Empleado.class, id);
			if(deleteEmpleado != null){
				em.remove(deleteEmpleado);
				em.getTransaction().commit();
			}else System.out.println(id + "ID no encontrada" );
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		em.close();		
	}
	
	
}
