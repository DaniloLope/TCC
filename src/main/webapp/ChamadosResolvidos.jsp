<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="Model.chamadosModel, java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
    <title>Chamados Resolvidos</title>
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
						<li ><a href="AvalieSistema.jsp" >Avalie o Sistema</a></li>
		                </ul>
	            </nav>
	        </div>
	    </header>
	<div id="head">
	    <h2>Chamados Resolvidos</h2>
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
		                <th>Data de Fechamento</th>
		            </tr>
		        </thead>
		        <tbody>
		            <% 
		                ArrayList<chamadosModel> chamadosResolvidos = (ArrayList<chamadosModel>) request.getAttribute("listaChamadosResolvidos");
		                
		                if (chamadosResolvidos != null && !chamadosResolvidos.isEmpty()) {
		                    for (chamadosModel c : chamadosResolvidos) {
		                        out.println(
		                            "<tr>" + 
		                            "<td>"+ c.getChamado_id() + "</td>" + 
		                            "<td>"+ c.getPrioridade() + "</td>" +
		                            "<td>"+ c.getStatus() + "</td>" +
		                            "<td>"+c.getDescricao() + "</td>" +
		                            "<td>"+ c.getData_abertura() + "</td>" +
		                            "<td>"+ c.getData_fechamento() + "</td>" +
		                            "</tr>");
		                    }
		                } else {
		                    out.println("<tr><td colspan='8'>Nenhum chamado resolvido.</td></tr>");
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