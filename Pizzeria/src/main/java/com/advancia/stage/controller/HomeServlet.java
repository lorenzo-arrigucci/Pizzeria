package com.advancia.stage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.advancia.stage.dao.Dao;
import com.advancia.stage.model.Impasto;
import com.advancia.stage.model.Ingrediente;
import com.advancia.stage.model.Pizza;
import com.advancia.stage.model.Utente;

public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HomeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utente utente =  Dao.getUtenteById(Long.parseLong(request.getParameter("idUtente")));
		String tipoModifica = request.getParameter("tipoModifica");		
		switch (tipoModifica) {
			case "Inserisci":
				Impasto impasto = Dao.insertImpasto(request.getParameter("impasto"));
				Pizza pizza = Dao.insertPizza(request.getParameter("nomePizza"), utente, impasto);
				String[] nomiIngredienti = request.getParameterValues("ingredienti");
				List<Ingrediente> listaIngredienti = new ArrayList<>();
				for (String nomeIngrediente : nomiIngredienti) {
					Ingrediente ingrediente = Dao.insertIngrediente(nomeIngrediente);
					listaIngredienti.add(ingrediente);
				}
				Dao.addIngredientiToPizza(pizza, listaIngredienti);
				break;
			case "Elimina":
				Pizza pizzaDaEliminare = Dao.getPizzaById(Long.parseLong(request.getParameter("pizzaDaModificare")));
				Dao.removePizza(utente, pizzaDaEliminare);
				break;
			case "Aggiorna":
				Pizza pizzaDaAggiornare = Dao.getPizzaById(Long.parseLong(request.getParameter("pizzaDaModificare")));
				request.setAttribute("pizzaDaAggiornare", pizzaDaAggiornare);
				break;
			case "Aggiornata":
				Long idPizzaModificata = Long.parseLong(request.getParameter("idPizzaModificata"));
				String nuovoImpasto = request.getParameter("impasto");
				String nuovoNome = request.getParameter("nomePizza");
				String[] nuoviIngredienti = request.getParameterValues("ingredienti");
				Dao.updatePizza(idPizzaModificata, nuovoImpasto, nuovoNome, nuoviIngredienti);
				break;
			default:
				request.setAttribute("problemaInGestionePizze", true);
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
				break;
		}		
		Dao.refreshUtente(utente);
		request.setAttribute("utente", utente);
		RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
		rd.forward(request, response);
	}
}





