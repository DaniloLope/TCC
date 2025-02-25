<!DOCTYPE html>
<%@ page import="jakarta.servlet.http.*,Model.clienteModel"%>
<%
	clienteModel cliente = (clienteModel) session.getAttribute("usuarioLogado");
	if (cliente == null) {
	    response.sendRedirect("loginUsuarios.jsp");
	    return;
	}
	if(request.getParameter("action") == null){	
		
		response.sendRedirect("ClientesController?action=recuperarChamadosCliente");		
		
	}
%>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Dashboard do Cliente</title>
    <link rel="stylesheet" type="text/css" href="CSS/dashboardClienteCSS.css">
  
</head>
<body>
	<header>
        <div class="header-container">
            <div class="logo">TechSolutions</div>
            <nav>
                <ul>
                    <li><a href="DashboardCliente.jsp">Painel Inicial</a></li>
           			<li><a href="registrarChamado.html">Cadastrar Chamados</a></li>
           			<li><a href="ChamadosController?acao=verTickets">Meus Chamados</a></li>
           			<li><a href="AvalieSistema.jsp">Avalie o Sistema</a></li>
                </ul>
            </nav>
        </div>
    </header>
	
	<h2>Bem-vindo, ${usuarioLogado.nome}!</h2>
	
	<div id="chamados">
		    <div id="chamadosAbertos">
		    	<h3> Meus Chamados Abertos</h3>
				<h1><%= request.getAttribute("listaChamados") %></h1>
		    </div>
		     <div id="chamadosFechados">
		    	<h3> Meus Chamados Resolvidos</h3>
				<h1><%= request.getAttribute("listaFechados") %></h1>
		    </div>
	</div>
	<div id="graficosDiv">
	 <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	    <script type="text/javascript">
	
	      google.charts.load('current', {'packages':['corechart']});
	
	      google.charts.setOnLoadCallback(drawChart);
	
	      function drawChart() {
	
	        var data = new google.visualization.DataTable();
	        data.addColumn('string', 'Topping');
	        data.addColumn('number', 'Slices');
	        data.addRows([
	          ['Abertos', <%= request.getAttribute("listaChamados") %>],
	          ['Resolvidos', <%= request.getAttribute("listaFechados") %>],
	       
	        ]);
	
	        // Set chart options
	        var options = {'title':'Chamados',
	                       'width':450,
	                       'height':450,
	         };
	
	        // Instantiate and draw our chart, passing in some options.
	        var chart = new google.visualization.PieChart(document.getElementById('div_usuarios'));
	        chart.draw(data, options);
	      }
	    </script>
	    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	    <script type="text/javascript">
	
	      google.charts.load('current', {'packages':['corechart']});
	
	      google.charts.setOnLoadCallback(drawChart);
	
	      function drawChart() {
	
	        var data = new google.visualization.DataTable();
	        data.addColumn('string', 'Topping');
	        data.addColumn('number', 'Slices');
	        data.addRows([
	          ['Baixa',  <%= request.getAttribute("listaBaixa") %>],
	          ['Média', <%= request.getAttribute("listaMedia") %>],
	          ['Alta', <%= request.getAttribute("listaAlta") %>],
	          ['Muito Alta', <%= request.getAttribute("listaMuitoAlta") %>],
	       
	        ]);
	
	        // Set chart options
	        var options = {'title':'Meus Chamados Abertos Por Prioridade',
	                       'width':450,
	                       'height':450,}; 
	
	        // Instantiate and draw our chart, passing in some options.
	        var chart = new google.visualization.PieChart(document.getElementById('div_prioridade'));
	        chart.draw(data, options);
	      }
	    </script>
	    </div>
	    <div id="graficos">
		    <div id="div_usuarios"></div>
		    <div id="div_prioridade"></div>
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