<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="Model.chamadosModel, java.util.*, java.util.Date, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="CSS/gerenciarChamadosCSS.css">
<script>
	    function confirmarCadastro() {
	        // Exibe a caixa de confirmação
	       return confirm("Você tem certeza que deseja atualizar o status?");
	    }
    </script>
</head>
<body>
		<header>
        <div class="header-container">
            <div class="logo">TechSolutions</div>
            <nav>
                <ul>
                    <li><a href="dashboard.jsp">Dashboard</a></li>
           			<li><a href="GerenciarChamados.jsp">Gerenciar chamados</a></li>
           			<li><a href="GerenciarUsuarios.jsp">Gerenciar usuário</a></li>
           			<li><a href="AvalieSistema.jsp">Avalie o sistema</a></li>
                </ul>
            </nav>
        </div>
    </header>
	<div>
		<h2>Todos Chamados</h2>
	
			<form action="ControllerADM" method="GET">
				<select id='filtrar' name='filtrar'>
					
	          		<option value='fechados'>Fechados</option>
		            <option value='em andamento'>Em andamento</option>
		            <option value='abertos'>Abertos</option>
		            <option value='prioridade baixa'>Prioridade Baixa</option>
		            <option value='prioridade media'>Prioridade Média</option>
		            <option value='prioridade alta'>Prioridade Alta</option>
		            <option value='prioridade muito alta'>Prioridade Muito Alta</option>
	     		</select>
	     		<input type='submit' value='Filtrar' name='action' class='linksAcaoFiltrar'>
			</form>
			<table border="1">
		    <tr>
		    	<th>Prioridade</th>
		        <th>Descrição</th>
		        <th>Status</th>
		        <th>Data de abertura</th>
		        <th>Data de fechamento</th>
		        <th id="th1">Alterar Prioridade <div class="icon-container"><img src="imagens/interrogacao.jpg"/><span class="tooltip-text">Avalie se a prioridade do ticket foi colocada de maneira fiel a realidade e altere-a se necessário.</span></div></th>
		    </tr>
	    <%
	   
	      	ArrayList<chamadosModel> listaChamados = (ArrayList<chamadosModel>) request.getAttribute("alta");
	        String data = "";
	        if (listaChamados != null) {
	            for (chamadosModel chamados : listaChamados) {
	            	
	            	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					String dataAbertura = formato.format(chamados.getData_abertura());
					
	            	if(chamados.getData_fechamento() == null){
	            		data = "Ticket não atendido";
	            	}else{
	            		SimpleDateFormat formatado = new SimpleDateFormat("dd/MM/yyyy");
						data = formatado.format(chamados.getData_fechamento());
	            	
	            	}
	            	out.println("<tr>");
	            	out.println("<td>" + chamados.getPrioridade() + "</td>");
	       			out.println("<td>" + chamados.getDescricao() + "</td>");
	       			out.println("<td>" + chamados.getStatus() + "</td>");
	       			out.println("<td>" + dataAbertura + "</td>");
	       			out.println("<td>" + data + "</td>");
	       			out.println("<td>"+
	       							"<form action='ControllerADM' method='get' onsubmit='return confirmarCadastro();'>" +
	       								"<input type='hidden' name='id' value='"+ chamados.getChamado_id() +"'>" +
		       							"<select id='prioridade' name='prioridade'>"+
		       		            			"<option value='baixa'>Baixa</option>"+
					       		            "<option value='media'>Média</option>"+
					       		            "<option value='alta'>Alta</option>"+
					       		            "<option value='muito alta'>Muito Alta</option>"+
		       		       				 "</select>"+
		       		       				"<input type='submit' value='Atualizar' name='action' class='linksAcaoStatus'>" +
		                            "</form>" +
	       		       			"</td>" );
	       			out.println("</tr>");
	  
	            }
	        } else {
	    %>
	    <tr>
	        <td colspan="3">Nenhum chamado encontrado.</td>
	    </tr>
	    <%
	        }
	    %>
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