<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="Model.clienteModel, java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Atualizar Usuário</title>
<link rel="stylesheet" type="text/css" href="CSS/cadastrarUsuarioCSS.css">
<script>
	    function confirmarCadastro() {
	        // Exibe a caixa de confirmação
	       return confirm("Você tem certeza que deseja editar o cadastro?");
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
	
	<%
		 String nome = "";
		 String setor = "";
		 String cargo = "";
		 String login = "";
		 String senha = "";
		 String tipo = "";
		
		ArrayList<clienteModel> clientes = (ArrayList<clienteModel>) request.getAttribute("status");
		int id = (int) Integer.parseInt(request.getParameter("id"));
		
		 if (clientes != null) {
             for (clienteModel cliente : clientes) {
            	 
            	 nome = cliente.getNome();
            	 setor = cliente.getSetor();
            	 cargo = cliente.getCargo();
            	 login = cliente.getLogin();
            	 senha = cliente.getSenha();
            	 tipo = cliente.getTipo();
            	 
            	 	
             }
		 }
	%>
	 <h2>Editar Usuário</h2>
	<fieldset>
		<form action="ControllerADM" method="GET" onsubmit="return confirmarCadastro();">
		
			<input type="hidden" name="id" value="<%= id %>">
			<div>
				<label>Nome: </label>
				<input type="text" name="nome" id="nome" value="<%= nome %>"required autofocus>
			</div>
			<div>
				<label>Setor: </label>
				<input type="text" name="setor" id="setor" value="<%= setor%>" required>
			</div>
			<div>
				<label>Cargo: </label>
				<input type="text" name="cargo" id="cargo" value="<%= cargo%>" required>
			</div>
			<div>
				<label>Login: </label>
				<input type="email" name="login" id="login" value="<%= login%>" required>
			</div>
			<div>
				<label>Senha: </label>
				<input type="password" value="<%= senha%>"  name="senha" id="password" pattern="(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&#])[A-Za-z\d@$!%*?&#]{8,}" title="A senha deve conter pelo menos 1 letra maiúscula, 1 número, 1 caractere especial e ter no mínimo 8 caracteres." required><span class="toggle-password" id="togglePassword">&#128065;</span>
			</div>
			<div>
				<label> Confirmar Senha: </label>
				<input type="password"  value="<%= senha%>"  name="confirmaSenha" id="confirmPassword" required><span class="toggle-password" id="confirmTogglePassword">&#128065;</span>
			</div>
			
			<div>
				<select name="usuario">
					<option><%= tipo%></option>
				</select>
				
			</div>
			
			<span id="message"></span><br><br>
			
			<input type="submit" disabled name="action" value="Editar" id="bt2">
		</form>
	</fieldset>
	<script>
	        const passwordField = document.getElementById("password");
	        const togglePassword = document.getElementById("togglePassword");
	
	        togglePassword.addEventListener("click", function () {
	            const type = passwordField.getAttribute("type") === "password" ? "text" : "password";
	            passwordField.setAttribute("type", type);
	
	          
	        });
    </script>
    <script>
	        const confirmPasswordField = document.getElementById("confirmPassword");
	        const confirmTogglePassword = document.getElementById("confirmTogglePassword");
	
	        confirmTogglePassword.addEventListener("click", function () {
	            const typeConfirm = confirmPasswordField.getAttribute("type") === "password" ? "text" : "password";
	            confirmPasswordField.setAttribute("type", typeConfirm);
	
	          
	        });
    </script> 
    <script>
        const password = document.getElementById('password');
        const confirmPassword = document.getElementById('confirmPassword');
        const message = document.getElementById('message');
        const submitButton = document.getElementById('bt2');

        function checkPasswords() {
            // Verifica se os campos de senha e confirmar senha estão preenchidos
            if (password.value === '' || confirmPassword.value === '') {
                message.textContent = '';
                submitButton.disabled = true;
                return;
            }

            // Verifica se as senhas coincidem
            if (password.value === confirmPassword.value) {
                message.textContent = 'As senhas coincidem!';
                message.className = 'success';
                submitButton.disabled = false; // Habilita o botão de envio
            } else {
                message.textContent = 'As senhas não coincidem!';
                message.className = 'error';
                submitButton.disabled = true; // Desabilita o botão de envio
            }
        }

        // Adiciona evento 'input' aos campos de senha para verificar em tempo real
        password.addEventListener('input', checkPasswords);
        confirmPassword.addEventListener('input', checkPasswords);
    </script>
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