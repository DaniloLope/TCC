<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="jakarta.servlet.http.*, Model.clienteModel"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Avalie o Sistema</title>
<link rel="stylesheet" type="text/css" href="CSS/avalieSistemaCSS.css">
</head>
<body>

	<%
		clienteModel cliente = (clienteModel) session.getAttribute("usuarioLogado");
		String tipoUsuario = "";
		if (cliente != null) {
			tipoUsuario = cliente.getTipo();
			request.setAttribute("tipoUsuario", tipoUsuario);
		}
	
		int id = cliente.getId();
	%>
	<%
	
		if (request.getParameter("action") == null) {
			
			response.sendRedirect("ControllerADM?action=verificarAvaliacao&id="+id); 
		}
	
	%>
	<header>
        <div class="header-container">
            <div class="logo">TechSolutions</div>
            <nav>
                <ul>
                    <li><a href="<%= tipoUsuario.equals("tecnico") ? "DashboardTecnico.jsp" : tipoUsuario.equals("cliente") ? "DashboardCliente.jsp" : "dashboard.jsp" %>">Painel Inicial</a></li>
                </ul>
            </nav>
        </div>
    </header>

	<h2>Avalie o sistema!</h2>

	<fieldset>
		<form action="ControllerADM" method="get">
			
			<input type="hidden" value=<%= id %> name="id">
			<div>
				<label for="nome">Nome: </label>
				<input type="text" id="nome" name="nome" required>
			</div>
				<div>
				<select name="nota">
					<option>Qual sua nota para o sistema?</option>
					<option value="1">1 - Péssimo</option>
					<option value="2">2 - Ruim</option>
					<option value="3">3 - Regular</option>
					<option value="4">4 - Bom</option>
					<option value="5">5 - Excelente</option>

				</select>
				
			</div>
			<div>
				<select name="utilidade">
					<option>Acha o sistema útil?</option>
					<option value="sim">Sim</option>
					<option value="nao">Não</option>
				</select>
				
			</div>
			<div>
				<label for="comentario">Deixe um comentário sobre o sistema: </label>
				<textarea name="comentario" id="comentario"></textarea>
			</div>
			
			<input type="submit" id="bt2" value="Avaliar" name="action">
		</form>
	</fieldset>
	<footer>
		<div class="footer-container">
			<p>&copy; 2024 TechSolutions. Todos os direitos reservados.</p>
			<nav>
				
				<a href="Faq.jsp">Perguntas Frequentes</a>
				<a href="tutoriais.jsp">Tutoriais</a>
				<p id="suporte">Suporte: (19)981431118</p>	
			</nav>
		</div>
	</footer>
</body>
</html>