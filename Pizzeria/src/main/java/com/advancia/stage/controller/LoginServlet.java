package com.advancia.stage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.advancia.stage.dao.Dao;
import com.advancia.stage.model.Pizza;
import com.advancia.stage.model.Utente;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String loginOrRegister = request.getParameter("loginOrRegister");
		if (loginOrRegister.equals("Register")) {
			Utente utente = Dao.insertUtente(username, password);
			if (utente != null) {
				request.setAttribute("utente", utente);
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("usernameNonDisponibile", username);
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			}
		} else  {
			Utente utente = Dao.validateUtente(username, password);		
			if (utente != null) {
				List<Pizza> listaPizze = utente.getPizzas();
				request.setAttribute("utente", utente);
				request.setAttribute("listaPizze", listaPizze);
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("utenteErrato", username);
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
			}
		}		
	}

}




