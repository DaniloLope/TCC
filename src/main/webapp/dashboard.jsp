<%@ page import=" jakarta.servlet.http.*,Model.administradorModel ,Controller.ControllerADM"%>
<%
	
	administradorModel admin = (administradorModel) session.getAttribute("admin");
	if (admin == null) {
	    response.sendRedirect("login.jsp");
	    return;
	}	
	
	if(request.getParameter("action") == null){	
		
		response.sendRedirect("ControllerADM?action=recuperarChamados");		
		
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Administrador</title>
    <link rel="stylesheet" type="text/css" href="CSS/DashboardADMCSS.css">
    <script>
    	
    function showAlert(message) {
        const alertPanel = document.getElementById('alertPanel');
        const alertMessage = document.getElementById('alertMessage');

        alertMessage.textContent = message; // Atualiza a mensagem
        alertPanel.classList.add('show'); // Mostra o painel
    }

    function closeAlert() {
        const alertPanel = document.getElementById('alertPanel');
        alertPanel.classList.remove('show'); // Esconde o painel
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
           			<li><a href="ChamadosController?acao=gerarrelatorio">Gerar
							Relatorio</a></li>
           		
           			<li><button onclick="showAlert('Chamados com prioridade muito alta: <%= request.getAttribute("listaMuitoAlta") %>!')">Exibir Alertas</button></li>
                </ul>
            </nav>
        </div>
    </header>
	<div id="head">
	    <h2>Bem-vindo, <%= admin.getNome() %>!</h2>
    </div>
    <div class="alert-panel" id="alertPanel">
        <span class="alert-message" id="alertMessage">Esta é uma mensagem de alerta!</span>
        <button class="close-btn" onclick="closeAlert()">Fechar</button>
    </div>
    
    <div id="main">
	    <div id="chamados">
		    <div id="chamadosAbertos">
		    	<h3> Chamados Abertos</h3>
				<h1><%= request.getAttribute("listaAbertos") %></h1>
		    </div>
		    <div id="chamadosFechados">
		    	<h3> Chamados Fechados</h3>
				<h1><%= request.getAttribute("listaFechados") %></h1>
		    </div>
		    <div id="chamadosTotal">
		    	<h3>Todos Chamados </h3>
				<h1><%= request.getAttribute("listaTodos") %></h1>
		    </div>
		    <div id="usuarios">
		    	<h3>Usuários Cadastrados </h3>
				<h1><%= request.getAttribute("listaUsuarios") %></h1>
		    </div>
	    </div>
	    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	    <script type="text/javascript">
	
	      google.charts.load('current', {'packages':['corechart']});
	
	      google.charts.setOnLoadCallback(drawChart);
	
	      function drawChart() {
	
	        var data = new google.visualization.DataTable();
	        data.addColumn('string', 'Topping');
	        data.addColumn('number', 'Slices');
	        data.addRows([
	          ['Abertos',  <%= request.getAttribute("listaAbertos") %>],
	          ['Fechados', <%= request.getAttribute("listaFechados") %>],
	          ['Em Andamento', <%= request.getAttribute("listaAndamento") %>],
	       
	        ]);
	
	        // Set chart options
	        var options = {'title':'Chamados Por Status',
	                       'width':450,
	                       'height':450};
	
	        // Instantiate and draw our chart, passing in some options.
	        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
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
	        var options = {'title':'Chamados Por Prioridade',
	                       'width':450,
	                       'height':450,}; 
	
	        // Instantiate and draw our chart, passing in some options.
	        var chart = new google.visualization.PieChart(document.getElementById('div_prioridade'));
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
	          ['Clientes',  <%= request.getAttribute("listaCliente") %>],
	          ['Técnicos', <%= request.getAttribute("listaTecnico") %>],
	       
	        ]);
	
	        // Set chart options
	        var options = {'title':'Tipos De Usuários',
	                       'width':450,
	                       'height':450};
	
	        // Instantiate and draw our chart, passing in some options.
	        var chart = new google.visualization.PieChart(document.getElementById('div_usuarios'));
	        chart.draw(data, options);
	      }
	    </script>
	    
	    <div id="graficos">
		    <div id="chart_div"></div>
		    <div id="div_prioridade"></div>
		    <div id="div_usuarios"></div>
		</div>
    </div>
    
    <div id="eficiencia">
		<div id="chamadosAbertos">
			<h3> Eficiência da equipe </h3>
			<h1><%= request.getAttribute("eficiencia") %>%</h1>
		</div>
		
	</div>
	<footer>
		<div class="footer-container">
			<p>&copy; 2024 TechSolutions. Todos os direitos reservados.</p>
			<nav>
				
				<a href="Faq.jsp">Perguntas Frequentes</a>
				<a href="tutoriais.jsp">Tutoriais</a>
				<a href="verAvaliacoes.jsp">Ver avaliações da equipe</a>
				<p id="suporte">Suporte: (19)981431118</p>	
			</nav>
		</div>
	</footer>
</body>
</html>
