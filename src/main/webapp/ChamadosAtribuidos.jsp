<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="Model.chamadosModel, java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
    <title>Chamados Atribuídos</title>
    <link rel="stylesheet" type="text/css" href="CSS/ChamadosAbertosTecnico.css">
</head>
<body>
	<header>
	        <div class="header-container">
	            <div class="logo">TechSolutions</div>
	            <nav>
	                <ul>
	                    <li>
						<a href="DashboardTecnico.jsp"> Painel Inicial</a></li>
							
						<li ><a href="ChamadosController?acao=listarChamados" >Chamados em aberto</a></li>
						
						<li ><a href="ChamadosController?acao=listarChamadosTecnico" >Chamados a resolver</a></li>
						
						<li ><a href="ChamadosController?acao=listarChamadosResolvidos" >Chamados resolvidos</a></li>
						<li ><a href="AvalieSistema.jsp">Avalie o Sistema</a></li>
		                </ul>
	            </nav>
	        </div>
	    </header>
	<div id="head">
	    <h2>Chamados a resolver</h2>
    </div>
	
	    
	    <div class="conteudo">
		    <table>
		        <thead>
		            <tr>
		                <th>ID</th>
		                <th>Prioridade</th>
		                <th>Status</th>
		                <th>Descrição</th>
		                <th>Data de Abertura</th>
		                <th>Mudar Status</th>
		                <th>Comunicação</th>
		                <th>Comunicação</th>
		            </tr>
		        </thead>
		        <tbody>
		            <% 
		            	
		                ArrayList<chamadosModel> chamados = (ArrayList<chamadosModel>) request.getAttribute("listaChamadosAtribuidos");
		                
		                if (chamados != null && !chamados.isEmpty()) {
			                for (chamadosModel c : chamados) {
			                    out.println(
			                        "<tr>" + 
			                        "<td>"+ c.getChamado_id() + "</td>" +
			                        "<td>"+ c.getPrioridade() + "</td>" +
			                        "<td>"+ c.getStatus() + "</td>" +
			                        "<td>"+c.getDescricao() + "</td>" +
			                        "<td>"+ c.getData_abertura() + "</td>" +
			                        "<td>" +
			                            "<form action='ChamadosController' method='post'>" +
			                                "<input type='hidden' name='id' value='"+ c.getChamado_id() +"'>" +
			                                "<input type='hidden' name='id_usuario' value='"+ c.getUsuario_id() +"'>" +
			                                "<select name='statusChamado'>" +
			                                    "<option value='Em Andamento'>Em Andamento</option>" +
			                                    "<option value='fechado'>fechado</option>" +
			                                "</select>" +
			                                "<input type='submit' value='Atualizar' name='acao' class='linksAcaoStatus'>" +
			                            "</form>" +
			                        "</td>" +
			                        "<td class='comunicacao'><p><a href='enviarComentarioTecnico.jsp?id="+c.getChamado_id()+"'class='linksAcaoStatus'> Enviar Comentário </a> </td>" +
		                    		"<td class='comunicacao'><p><a href='verComentarioTecnico.jsp?id="+c.getChamado_id()+"'class='linksAcaoStatus'> Ver Comentários </a> </td>" +
			                        "</tr>");
		                	}	
		                }else {
		                    out.println("<tr><td colspan='8'>Nenhum chamado atribuido a você.</td></tr>");
		                }
		            %>
		        </tbody>
		    </table>
		</div>

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