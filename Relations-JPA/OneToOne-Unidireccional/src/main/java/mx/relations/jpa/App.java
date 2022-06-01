package mx.relations.jpa;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import mx.relations.jpa.config.DatabaseConfig;
import mx.relations.jpa.dao.EmpleadoDao;
import mx.relations.jpa.entities.Empleado;
import mx.relations.jpa.entities.Parking;

public class App {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
		
		EmpleadoDao empleadoDao = context.getBean(EmpleadoDao.class);
		Scanner tecla = new Scanner(System.in);
		int option = 0; 
		do {
			System.out.println("1.- Listar Empleados y parking");
			System.out.println("2.- Agregar Empleado");
			System.out.println("3.- Eliminar Empleado");
			System.out.println("4.- Salir");
			System.out.print("Opcion: ");
			option = Integer.parseInt(tecla.next());
			switch (option) {
			case 1:
				tecla.nextLine();
				List<Empleado> empleados = empleadoDao.findAllEmpleados();
				System.out.println("Listado de empleados");
				for(Empleado emp : empleados){
					System.out.println(emp.getId());
					System.out.println(emp.getNombre());
					System.out.println(emp.getSalario());
					System.out.println(emp.getParking().getSeccion());
					System.out.println(emp.getParking().getLote());
				}
				break;
			case 2: 
				tecla.nextLine();
				System.out.println("Nombre:");
				String nombre = tecla.nextLine(); 
				System.out.println("Salario:");
				BigDecimal salario = tecla.nextBigDecimal();
				System.out.println("Lote:");
				int lote = tecla.nextInt();
				tecla.nextLine();
				System.out.println("Seccion:");
				String seccion = tecla.nextLine();
				Parking nuevoParking = new Parking(lote, seccion);
				Empleado nuevoEmpleado = new Empleado(nombre, salario);
				nuevoEmpleado.setParking(nuevoParking);
				empleadoDao.addEmpleado(nuevoEmpleado);
				break;
			case 3: 
				tecla.nextLine();
				System.out.println("ID:");
				int id = tecla.nextInt();
				empleadoDao.deleteByIdEmpleado(id);
				break;
			default:
				break;
			}
		} while (option!=4);
		tecla.close();		
		context.close();

	}

}
