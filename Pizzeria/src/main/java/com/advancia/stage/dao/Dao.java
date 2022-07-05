package com.advancia.stage.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.advancia.stage.model.Impasto;
import com.advancia.stage.model.Ingrediente;
import com.advancia.stage.model.Pizza;
import com.advancia.stage.model.Utente;

public class Dao {
	private static final String PERSISTANCE_UNIT_NAME = "Pizzeria";
	private static EntityManagerFactory factory;
	private static EntityManager entityManager;
	
	// Methods for management of connection to database and transactions
	private static EntityManagerFactory getEntityManagerFactory() {
		if (factory == null) factory = Persistence.createEntityManagerFactory(PERSISTANCE_UNIT_NAME);
		return factory;
	}	
	private static void beginTransaction() {
		if (entityManager == null) {
			entityManager = getEntityManagerFactory().createEntityManager();
		}
		entityManager.getTransaction().begin();
	}
	private static void commit() {
		entityManager.getTransaction().commit();
	}
	private static void rollback() {
		entityManager.getTransaction().rollback();
	}
	public static void shutdown() {
		if (entityManager != null) entityManager.close();
		if (factory != null) factory.close();
	}
	
	// Operations versus database
	public static Utente validateUtente(String username, String password) {
		try {
			beginTransaction();
			Query query = entityManager.createQuery("from Utente u "
					+ "where u.username = :username and u.password= :password");
			query.setParameter("username", username);
			query.setParameter("password", password);
			List<Utente> utenti = query.getResultList();
			if (utenti == null || utenti.isEmpty()) return null;
			Utente utente = utenti.get(0);
			commit();
			return utente;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			return null;
		}
	}
		
	public static Utente getUtenteById(Long idUtente) {
		try {
			beginTransaction();
			Utente utente = entityManager.find(Utente.class, idUtente);
			commit();
			return utente;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			return null;
		}
	}
	
	public static Utente insertUtente(String username, String password) {
		try {
			beginTransaction();
			Query query = entityManager.createQuery("from Utente u where u.username = :username");
			query.setParameter("username", username);
			List<Utente> utenti = query.getResultList();
			if (utenti == null || utenti.isEmpty()) {
				Utente utente = new Utente(username, password);		
				entityManager.persist(utente);
				commit();
				return utente;
			} else {
				commit();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			return null;
		}
	}

	public static Impasto insertImpasto(String nomeImpasto) {
		try {
			beginTransaction();
			Query query = entityManager.createQuery("from Impasto i where i.nome= :nome");
			query.setParameter("nome", nomeImpasto);
			List<Impasto> listaImpasti = (List<Impasto>) query.getResultList();
			if (listaImpasti == null || listaImpasti.isEmpty()) {
				Impasto impasto = new Impasto(nomeImpasto);
				entityManager.persist(impasto);
				commit();
				return impasto;
			}
			commit();
			return listaImpasti.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			return null;
		}
	}

	public static Pizza insertPizza(String nomePizza, Utente utente, Impasto impasto) {
		try {
			beginTransaction();
			Query query = entityManager.createQuery("from Pizza p where p.nome= :nome "
					+ "and p.utente= :utente");
			query.setParameter("nome", nomePizza);
			query.setParameter("utente", utente);
			List<Pizza> listaPizze = query.getResultList();
			if (listaPizze == null || listaPizze.isEmpty()) {
				Pizza pizza = new Pizza(nomePizza, utente, impasto);		
				entityManager.persist(pizza);
				commit();
				return pizza;
			} else {
				commit();
				return listaPizze.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			return null;
		}
	}
	
	public static Ingrediente insertIngrediente(String nomeIngrediente) {
		try {
			beginTransaction();
			Query query = entityManager.createQuery("from Ingrediente i where i.nome = :nome");
			query.setParameter("nome", nomeIngrediente);
			List<Ingrediente> listaIngredienti = query.getResultList();			
			if (listaIngredienti == null || listaIngredienti.isEmpty()) {
				Ingrediente ingrediente = new Ingrediente(nomeIngrediente);		
				entityManager.persist(ingrediente);
				commit();
				return ingrediente;
			} else {
				commit();
				return listaIngredienti.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			return null;
		}
	}
	
	public static void addIngredientiToPizza(Pizza pizza, List<Ingrediente> listaIngredienti) {
		try {
			beginTransaction();
			pizza.setIngredienti(listaIngredienti);
			entityManager.merge(pizza);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
	}

	public static void refreshUtente(Utente utente) {
		try {
			beginTransaction();
			entityManager.refresh(utente);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}		
	}
		
	public static void removePizza(Utente utente, Pizza pizza) {
		try {
			beginTransaction();
			Impasto impasto = pizza.getImpasto();			
			pizza.removeIngredienti();
			impasto.removePizza(pizza);
			utente.removePizza(pizza);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
	}

	public static Pizza getPizzaById(Long idPizza) {
		try {
			beginTransaction();
			Pizza pizza = entityManager.find(Pizza.class, idPizza);
			commit();
			return pizza;
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
			return null;
		}
	}
	
	public static boolean toBeChecked(List<Ingrediente> listaIngredientiDaAggiornare, String nomeIngrediente) {
		for (Ingrediente ingrediente : listaIngredientiDaAggiornare) {
			if (ingrediente.getNome().equals(nomeIngrediente)) return true;
		}
		return false;
	}
	
	public static void updatePizza(Long idPizzaModificata, String nuovoImpasto, String nuovoNome,String[] nomiNuoviIngredienti) {
		try {
			beginTransaction();
			Pizza pizzaModificata = entityManager.find(Pizza.class, idPizzaModificata);
			pizzaModificata.removeIngredienti();
			pizzaModificata.getImpasto().removePizza(pizzaModificata);
			pizzaModificata.setNome(nuovoNome);
			Impasto impasto = getImpastoByName(nuovoImpasto);
			List<Ingrediente> nuoviIngredienti = new ArrayList<>();
			for (String nomeIngrediente : nomiNuoviIngredienti) {
				Ingrediente ingrediente = getIngredienteByName(nomeIngrediente);
				nuoviIngredienti.add(ingrediente);
			}			
			impasto.addPizza(pizzaModificata);
			pizzaModificata.setIngredienti(nuoviIngredienti);
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
	}	

	private static Ingrediente getIngredienteByName(String nomeIngrediente) {				
			Query query = entityManager.createQuery("from Ingrediente i where i.nome= :nome");
			query.setParameter("nome", nomeIngrediente);
			Ingrediente ingrediente = (Ingrediente) query.getSingleResult();
			return ingrediente;		
	}
	
	private static Impasto getImpastoByName(String nuovoImpasto) {
			Query query = entityManager.createQuery("from Impasto i where i.nome= :nome");
			query.setParameter("nome", nuovoImpasto);
			Impasto impasto = (Impasto) query.getSingleResult();
			return impasto;
	}
	
	
}

















