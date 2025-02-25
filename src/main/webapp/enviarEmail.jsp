<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Enviar E-mail</title>
<link rel="stylesheet" type="text/css" href="CSS/registrarMenssagemCSS.css">
    <script>
	    function confirmarCadastro() {
	       
	       return confirm("Você tem certeza que deseja enviar o E-mail?");
	    }
    </script>
</head>
<body>
	<%
	
		int id = Integer.parseInt(request.getParameter("id"));
		
	%> 
 <header>
        <div class="header-container">
            <div class="logo">TechSolutions</div>
            <nav>
                <ul>
                    <li><a href="dashboard.jsp">Dashboard</a></li>
           			<li><a href="GerenciarChamados.jsp">Gerenciar chamados</a></li>
           			<li><a href="GerenciarUsuarios.jsp">Gerenciar usuário</a></li>
           			<li><a href="ChamadosController?acao=gerarrelatorio">Gerar
							Relatorio</a></li>
                </ul>
            </nav>
        </div>
    </header>
    <h2>Enviar E-mail</h2>
    <fieldset>
	    <form action="ControllerADM" method="get" onsubmit="return confirmarCadastro();">
	        
			<input type="hidden" value=<%= id %> name="id">
			
	        <label for="descricao">Assunto:</label><br>
	        <input type="text" name="assunto" id="descricao" required>
	        
	        <label for="descricao">Menssagem:</label><br>
	        <textarea name="msg" id="descricao" ></textarea>
	
	        <input type="submit" id="bt2" name="action" value="Enviar">
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