package com.advancia.stage.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pizza")
public class Pizza {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "id_utente")
	private Utente utente;
	
	@ManyToOne
	@JoinColumn(name = "id_impasto")
	private Impasto impasto;
	
	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE})
	@JoinTable(name = "pizza_ingrediente",
			joinColumns = @JoinColumn(name = "id_pizza"),
			inverseJoinColumns = @JoinColumn(name = "id_ingrediente"))
	private List<Ingrediente> ingredienti = new ArrayList<>();
	
	// Constructors, getters, setters	
	public Pizza() {
		super();
	}
	
	public Pizza(String nome, Utente utente, Impasto impasto) {
		super();
		this.nome = nome;
		this.utente = utente;
		this.impasto = impasto;
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

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public Impasto getImpasto() {
		return impasto;
	}

	public void setImpasto(Impasto impasto) {
		this.impasto = impasto;
	}

	public List<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
		for (Ingrediente ingrediente : ingredienti) {			
			ingrediente.getPizzas().add(this);
		}
	}
	
	public void removeIngredienti() {
		for (Ingrediente ingrediente : ingredienti) {
			ingrediente.getPizzas().remove(this);			
		}
		this.ingredienti.clear();
	}
	
	
	// Equals and hashCode
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Pizza)) return false;
		return id != null && id.equals(((Pizza) o).getId());
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










