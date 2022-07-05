<%@page import="com.advancia.stage.model.Impasto"%>
<%@page import="com.advancia.stage.dao.Dao"%>
<%@page import="com.advancia.stage.model.Ingrediente"%>
<%@page import="com.advancia.stage.model.Pizza"%>
<%@page import="java.util.List"%>
<%@page import="com.advancia.stage.model.Utente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Pizza a modo tuo</title>
</head>
<body>
	<%
		Utente utente = (Utente) request.getAttribute("utente");			
	%>
	<a href="login.jsp" style="position: absolute; right: 2rem; top:2rem"><button style="padding:1em">Logout</button></a>
	<div style="padding: 0 0 4rem 0; border-bottom: 1px solid black">
		<div style="max-width: 35%; display: inline-flex; flex-direction: column; margin: 2rem 5rem 2rem 2rem;">
			<h1 style="margin: 2rem 0">
				Ciao <%=utente.getUsername() %>,<br>
				benvenuto su <span style="font-wheight: bold; font-size: 1.4em">Pizza a modo tuo</span>, 
				dove puoi creare le tue pizze preferite.
			</h1>
			<p>Per iniziare seleziona un impasto, gli ingredienti più buoni e dai un nome con stile alla tua pizza!</p>
		</div>
		<div style="display: inline-flex; align-items: start; max-width: 50%">
			<form action="HomeServlet" method="post" style="display: flex; flex-wrap: wrap">
				<%
					if (request.getAttribute("pizzaDaAggiornare") != null) {	
						Pizza pizzaDaAggiornare = (Pizza) request.getAttribute("pizzaDaAggiornare");
						List<Ingrediente> ingredientiDaAggiornare = pizzaDaAggiornare.getIngredienti();
						Impasto impastoDaAggiornare = pizzaDaAggiornare.getImpasto();
						String nomeDaAggiornare = pizzaDaAggiornare.getNome();
				%>
						<div style="margin: 3rem 3rem">
							<h3>Scegli un impasto:</h3>
							<label><input type="radio" name="impasto" value="Farina 00" required <% if (impastoDaAggiornare.getNome().equals("Farina 00")) out.print("checked"); %>>Farina 00</label><br>
							<label><input type="radio" name="impasto" value="Integrale" required <% if (impastoDaAggiornare.getNome().equals("Integrale")) out.print("checked"); %>>Integrale</label><br>
							<label><input type="radio" name="impasto" value="Kamut" required <% if (impastoDaAggiornare.getNome().equals("Kamut")) out.print("checked"); %>>Kamut</label><br>
							<label><input type="radio" name="impasto" value="5 Cereali" required <% if (impastoDaAggiornare.getNome().equals("5 Cereali")) out.print("checked"); %>>5 cereali</label>
						</div>
						<div style="display: inline-block; margin: 3rem 3rem">
							<h3>Scegli gli ingredienti:</h3>
							<div style="display: flex; flex-direction: column; flex-wrap: wrap">						
								<label><input type="checkbox" name="ingredienti" value="Pomodoro" <% if (Dao.toBeChecked(ingredientiDaAggiornare, "Pomodoro")) out.print("checked"); %>>Pomodoro</label>
								<label><input type="checkbox" name="ingredienti" value="Mozzarella" <% if (Dao.toBeChecked(ingredientiDaAggiornare, "Mozzarella")) out.print("checked"); %>>Mozzarella</label>
								<label><input type="checkbox" name="ingredienti" value="Wurstel" <% if (Dao.toBeChecked(ingredientiDaAggiornare, "Wurstel")) out.print("checked"); %>>Wurstel</label>
								<label><input type="checkbox" name="ingredienti" value="Crudo" <% if (Dao.toBeChecked(ingredientiDaAggiornare, "Crudo")) out.print("checked"); %>>Crudo</label>
								<label><input type="checkbox" name="ingredienti" value="Cotto" <% if (Dao.toBeChecked(ingredientiDaAggiornare, "Cotto")) out.print("checked"); %>>Cotto</label>
								<label><input type="checkbox" name="ingredienti" value="Burrata" <% if (Dao.toBeChecked(ingredientiDaAggiornare, "Burrata")) out.print("checked"); %>>Burrata</label>
								<label><input type="checkbox" name="ingredienti" value="Scamorza" <% if (Dao.toBeChecked(ingredientiDaAggiornare, "Scamorza")) out.print("checked"); %>>Scamorza</label>
								<label><input type="checkbox" name="ingredienti" value="Salame" <% if (Dao.toBeChecked(ingredientiDaAggiornare, "Salame")) out.print("checked"); %>>Salame</label>
								<label><input type="checkbox" name="ingredienti" value="Salsiccia" <% if (Dao.toBeChecked(ingredientiDaAggiornare, "Salsiccia")) out.print("checked"); %>>Salsiccia</label>
							</div>				
						</div>
						<div style="margin: 0 3rem">
							<h3>Dai un nome alla tua pizza:</h3>
							<input type="text" name="nomePizza" value="<%=pizzaDaAggiornare.getNome() %>" required>
							<span style="margin: 0 4em; padding: 2em"><input type="submit" value="Modifica pizza" style="padding: 0.6em"></span>
						</div>
						<input type="hidden" name="idUtente" value="<%=utente.getId() %>">
						<input type="hidden" name="idPizzaModificata" value="<%=pizzaDaAggiornare.getId() %>">
						<input type="hidden" name="tipoModifica" value="Aggiornata">
				 <%
				 	} else {
				 %>
				 		<div style="margin: 3rem 3rem">
							<h3>Scegli un impasto:</h3>
							<label><input type="radio" name="impasto" value="Farina 00" required>Farina 00</label><br>
							<label><input type="radio" name="impasto" value="Integrale" required>Integrale</label><br>
							<label><input type="radio" name="impasto" value="Kamut" required>Kamut</label><br>
							<label><input type="radio" name="impasto" value="5 Cereali" required>5 cereali</label>
						</div>
						<div style="display: inline-block; margin: 3rem 3rem">
							<h3>Scegli gli ingredienti:</h3>
							<div style="display: flex; flex-direction: column; flex-wrap: wrap">						
								<label><input type="checkbox" name="ingredienti" value="Pomodoro">Pomodoro</label>
								<label><input type="checkbox" name="ingredienti" value="Mozzarella">Mozzarella</label>
								<label><input type="checkbox" name="ingredienti" value="Wurstel">Wurstel</label>
								<label><input type="checkbox" name="ingredienti" value="Crudo">Crudo</label>
								<label><input type="checkbox" name="ingredienti" value="Cotto">Cotto</label>
								<label><input type="checkbox" name="ingredienti" value="Burrata">Burrata</label>
								<label><input type="checkbox" name="ingredienti" value="Scamorza">Scamorza</label>
								<label><input type="checkbox" name="ingredienti" value="Salame">Salame</label>
								<label><input type="checkbox" name="ingredienti" value="Salsiccia">Salsiccia</label>
							</div>				
						</div>
						<div style="margin: 0 3rem">
							<h3>Dai un nome alla tua pizza:</h3>
							<input type="text" name="nomePizza" placeholder="Inserisci il nome" required>
							<span style="margin: 0 4em; padding: 2em"><input type="submit" value="Aggiungi alle tue pizze" style="padding: 0.6em"></span>
						</div>
						<input type="hidden" name="idUtente" value="<%=utente.getId() %>">
						<input type="hidden" name="tipoModifica" value="Inserisci">
				 <%
				 	}
				 %>
			</form>
		</div>
	</div>
	<div>
		<h2>Le tue pizze</h2>
		<table style="border-collapse: collapse">
			<tr>
				<th>Nome Pizza</th>
				<th>Impasto</th>
				<th>Ingredienti</th>
			</tr>
			<%				
				List<Pizza> listaPizze = utente.getPizzas();
				for (Pizza pizza : listaPizze) {
			%>
					<tr>
						<td style="border: 1px solid black; padding: 1em"><%=pizza.getNome() %></td>
						<td style="border: 1px solid black; padding: 1em"><%=pizza.getImpasto() %></td>
						<td style="border: 1px solid black; padding: 1em">
							<%
								List<Ingrediente> ingredienti = pizza.getIngredienti();
								for (Ingrediente ingrediente : ingredienti) {
							%>
									<p style="display: inline-block; margin: 0 1em"><%=ingrediente.getNome() %> </p>
							<% 
								}
							%>
						</td>
						<td style="border: 1px solid black; padding: 1em">
							<form action="HomeServlet" method="post" style="display: inline-block; margin: 0 1em">
								<input type="submit" name="tipoModifica" value="Aggiorna">
								<input type="hidden" name="idUtente" value="<%=utente.getId() %>">
								<input type="hidden" name="pizzaDaModificare" value="<%=pizza.getId() %>">
							</form>
							<form action="HomeServlet" method="post" style="display: inline-block; margin: 0 1em">
								<input type="submit" name="tipoModifica" value="Elimina">
								<input type="hidden" name="idUtente" value="<%=utente.getId() %>">
								<input type="hidden" name="pizzaDaModificare" value="<%=pizza.getId() %>">
							</form>
						</td>
					</tr>
			<%					
				}
			%>
		</table>
	</div>	
</body>
</html>