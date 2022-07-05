<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Pizza a modo tuo</title>
</head>

<body>
	<div style="text-align: center">
		<h1 style="margin: 2em 1em">Effettua il login per creare le tue pizze preferite</h1>
		<form action="LoginServlet" method="post">			
			<label>Username: <input type="text" name="username" required></label><br/><br/>
			<label>Password: <input type="password" name="password" required></label><br/><br/>
			<input type="submit" name="loginOrRegister" value="Login" style="margin: 1em 4em 0 0">
			<span><input type="submit" name="loginOrRegister" value="Register"></span>
		</form>
	</div>
	<%
		if (request.getAttribute("utenteErrato") != null) {
	%>
			<h4 style="color: red; text-align: center">
				Utente <%=request.getAttribute("utenteErrato") %> non trovato o password errata!
			</h4>		
	<%
		}
	%>
	<%
		if (request.getAttribute("problemaInGestionePizze") != null) {
	%>
			<h4 style="color: red; text-align: center">
				C'è stato un errore durante l'esecuzione del programma! Effettua nuovamente il login per proseguire.
			</h4>		
	<%
		}
	%>
	<%
		if (request.getAttribute("usernameNonDisponibile") != null) {
	%>
			<h4 style="color: red; text-align: center">
				Username <%=request.getAttribute("usernameNonDisponibile") %> non disponibile!
			</h4>		
	<%
		}
	%>
</body>
</html>