<!DOCTYPE html>
<html>
<head>	
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Chamado</title>
    <link rel="stylesheet" type="text/css" href="CSS/registrarMenssagemCSS.css">
    <script>
	    function confirmarCadastro() {
	        // Exibe a caixa de confirma��o
	       return confirm("Voc� tem certeza que deseja enviar o coment�rio?");
	    }
    </script>
</head>
<body>
    
 	<header>
	        <div class="header-container">
	            <div class="logo">TechSolutions</div>
	            <nav>
	                <ul>
	                    <li><a href="DashboardTecnico.jsp" > Painel Inicial</a></li>	
						<li ><a href="ChamadosController?acao=listarChamados" >Chamados em aberto</a></li>
						<li ><a href="ChamadosController?acao=listarChamadosTecnico">Chamados a resolver</a></li>
						<li ><a href="ChamadosController?acao=listarChamadosResolvidos">Chamados resolvidos</a></li>
						<li ><a href="AvalieSistema.jsp" >Avalie o Sistema</a></li>
		            </ul>
	            </nav>
	        </div>
	    </header>
		<h2>Enviar Coment�rio</h2>
	<%
	
		int id = (int) Integer.parseInt(request.getParameter("id"));
	
	
	%>
    <fieldset>
	    <form action="ChamadosController" method="get" onsubmit="return confirmarCadastro();">
	        
			<input type="hidden" value=<%= id %> name="id">
			
	        <label for="descricao">Menssagem:</label><br>
	        <input type="text" name="msg" id="descricao" required>
	
	        <input type="submit" id="bt2" name="acao" value="Responder">
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