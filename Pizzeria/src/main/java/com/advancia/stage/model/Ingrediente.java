package com.advancia.stage.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ingrediente")
public class Ingrediente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@ManyToMany(mappedBy = "ingredienti")
	private List<Pizza> pizzas = new ArrayList<>();
	
	// Constructors, getters, setters
	public Ingrediente() {
		super();
	}

	public Ingrediente(String nome) {
		super();
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
	}

	// Equals and hashCode
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Ingrediente)) return false;
		return id != null && id.equals(((Ingrediente) o).getId());
	}
	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
	@Override
	public String toString() {
		return getNome();
	}
	


	
	
	
	
	
}
