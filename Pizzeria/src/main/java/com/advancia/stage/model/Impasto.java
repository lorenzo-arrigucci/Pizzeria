package com.advancia.stage.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "impasto")
public class Impasto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@OneToMany(mappedBy = "impasto",
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)
	private List<Pizza> pizzas = new ArrayList<>();

	// Constructors, getters, setters
	public Impasto() {
		super();
	}

	public Impasto(String nome) {
		super();
		this.nome = nome;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(long id) {
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
	
	// Add/Remove a pizza
	public void addPizza(Pizza pizza) {
		pizzas.add(pizza);
		pizza.setImpasto(this);
	}
	public void removePizza(Pizza pizza) {
		pizzas.remove(pizza);
		pizza.setImpasto(null);
	}

	@Override
	public String toString() {
		return getNome();
	}

	
	
}
