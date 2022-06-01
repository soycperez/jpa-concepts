package mx.relations.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Empleado implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "salario")
	private BigDecimal salario;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name= "id_parking")
	private Parking parking; 
	
	public Empleado() {

	}
	
	public Empleado(String nombre, BigDecimal salario, Parking parking) {
		this.nombre = nombre;
		this.salario = salario;
		this.parking = parking;
	}


	public Empleado(String nombre, BigDecimal salario) {
		this.nombre = nombre;
		this.salario = salario;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public BigDecimal getSalario() {
		return salario;
	}


	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}
	
	
	
}
