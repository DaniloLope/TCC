package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.sql.DataSource;

import Model.AdministradorDAO;
import Model.avaliacoesModel;
import Model.chamadosModel;
import Model.clienteModel;
import Model.emailDAO;
import Model.tecnicoModel;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ControllerADM")
public class ControllerADM extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private AdministradorDAO aDAO;
	
	@Resource(name = "bancoTechsolutions")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		aDAO = new AdministradorDAO(dataSource);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		String setor = request.getParameter("setor");
		String cargo = request.getParameter("cargo");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
        String tipo = request.getParameter("usuario");
        tipo.toLowerCase();
        
       
        if("cliente".equals(tipo)) {
        	
        	clienteModel cliente = new clienteModel(0, nome, setor, cargo, login, senha, tipo);
        	boolean inseriu = aDAO.cadastrarCliente(nome, setor, cargo, login, senha, tipo);
        	request.setAttribute("status", inseriu);
    		request.setAttribute("operacao", "cadastrado");
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/SaidaCadastroCliente.jsp");
    		dispatcher.forward(request, response);
        	
        }else{
        	
        	System.out.print(tipo);
        	tecnicoModel tecnico = new tecnicoModel(nome, setor, cargo, login, senha, tipo);
        	boolean inseriu = aDAO.cadastrarTecnico(nome, setor, cargo, login, senha, tipo);
        	request.setAttribute("status", inseriu);
    		request.setAttribute("operacao", "cadastrado");
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/SaidaCadastroTecnico.jsp");
    		dispatcher.forward(request, response);
        }
          
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
		String action = request.getParameter("action");
		
		if("listarUsuariosClientes".equals(action)) {
			
			listarUsuariosClientes(request, response);
		
		}else if("listarChamados".equals(action)) {
			
			listarChamados(request, response);
			
		}else if("listarChamadosFechados".equals(action)) {
			
			listarChamadosFechados(request, response);
			
		}else if("listarChamados".equals(action)) {
			
			listarChamados(request, response);
			
		}else if("recuperarChamados".equals(action)) {
			recuperarChamados(request, response);
		
		}else if("listarClientes".equals(action)) {
			
			listarClientes(request, response);
		}else if("listarTecnicos".equals(action)) {
			
			listarTecnicos(request, response);
		}else if("Atualizar".equals(action)) {
			
			mudarPrioridade(request, response);
			
		}else if("excluirUsuario".equals(action)) {
			
			excluirUsuario(request, response);
			
		}else if("editarUsuario".equals(action)) {
			
			editarUsuario(request, response);
			
		}else if("Editar".equals(action)) {
			
			alterarDadosUsuario(request, response);
		
		}else if("Filtrar".equals(action)) {
			
			Filtrar(request, response);
			
		}else if("Enviar".equals(action)){
			comunicacaoUsuario(request, response);
			
		}else if("Avaliar".equals(action)){
			avaliacao(request, response);
			
		}else if("verificarAvaliacao".equals(action)){
			
			verificarAvaliacao(request, response);
			
		}else if ("recuperarAvaliacoes".equals(action)) {
			
			recuperarAvaliacoes(request, response);
		}
		
	}	
	
	private void recuperarAvaliacoes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<avaliacoesModel> listaAvaliacoes = aDAO.listaAvaliacoes();
		request.setAttribute("listaAvaliacoes", listaAvaliacoes);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/verAvaliacoes.jsp");
		dispatcher.forward(request, response);
	
	}
	private void verificarAvaliacao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		int avaliou = aDAO.jaAvaliou(id);
		
		if(avaliou == 0) {
			
			 request.getRequestDispatcher("/AvalieSistema.jsp").forward(request, response);
			
		}else {
			
			 request.getRequestDispatcher("/statusAvaliacao.jsp").forward(request, response);
			
		}
		
	}
	private void avaliacao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		String nota = request.getParameter("nota");
		String utilidade = request.getParameter("utilidade");
		String comentario = request.getParameter("comentario");
		int id_usuario = Integer.parseInt(request.getParameter("id"));
		
		aDAO.avaliar(id_usuario, nome, nota, utilidade, comentario);
		
		request.getRequestDispatcher("/statusAvaliado.jsp").forward(request, response);;
	}
	private void comunicacaoUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
	
		String destinatario = aDAO.procurarEmail(id);
        String assunto = request.getParameter("assunto");
        String menssagem = request.getParameter("msg");
        emailDAO eDAO = new emailDAO();
        eDAO.enviarEmail(destinatario, assunto , menssagem);
	    request.getRequestDispatcher("/statusEmail.jsp").forward(request, response);
		
	}
	private void Filtrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String filtrar = request.getParameter("filtrar");

		if(filtrar.equals("fechados")) {
			
			ArrayList<chamadosModel> listaFechados = aDAO.filtrarFechados();
			request.setAttribute("fechados", listaFechados);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/chamadosFechados.jsp");
    		dispatcher.forward(request, response);
			
		}else if(filtrar.equals("em andamento")) {
			
			ArrayList<chamadosModel> listaAndamento = aDAO.filtrarAndamento();
			request.setAttribute("andamento", listaAndamento);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/chamadosAndamento.jsp");
    		dispatcher.forward(request, response);
			
		}else if(filtrar.equals("prioridade baixa")) {
			
			ArrayList<chamadosModel> listaBaixa = aDAO.filtrarBaixa();
			request.setAttribute("baixa", listaBaixa);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/chamadosBaixa.jsp");
    		dispatcher.forward(request, response);
			
		}else if(filtrar.equals("prioridade media")) {
			
			ArrayList<chamadosModel> listaMedia = aDAO.filtrarMedia();
			request.setAttribute("media", listaMedia);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/chamadosMedia.jsp");
    		dispatcher.forward(request, response);
    		
		}else if(filtrar.equals("prioridade alta")) {
			
			ArrayList<chamadosModel> listaAlta = aDAO.filtrarAlta();
			request.setAttribute("alta", listaAlta);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/chamadosAlta.jsp");
    		dispatcher.forward(request, response);
    		
		}else if(filtrar.equals("prioridade muito alta")) {
			
			ArrayList<chamadosModel> listaMuitoAlta = aDAO.filtrarMuitoAlta();
			request.setAttribute("muitoAlta", listaMuitoAlta);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/chamadosMuitoAlta.jsp");
    		dispatcher.forward(request, response);
    		
		}else if(filtrar.equals("abertos")) {
			
			ArrayList<chamadosModel> listaAbertos = aDAO.filtrarAberta(); 
			request.setAttribute("abertos", listaAbertos);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/chamadosAbertos.jsp");
    		dispatcher.forward(request, response);
    		
		}
		
		
	}
	private void alterarDadosUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		String nome = request.getParameter("nome");
		String setor = request.getParameter("setor");
		String cargo = request.getParameter("cargo");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
        String tipo = request.getParameter("usuario");
        tipo.toLowerCase();
        
       
        if("cliente".equals(tipo)) {
        	
        	clienteModel cliente = new clienteModel(0, nome, setor, cargo, login, senha, tipo);
        	boolean inseriu = aDAO.atualizarCliente(nome, setor, cargo, login, senha, tipo, id);
        	request.setAttribute("status", inseriu);
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/saidaAtualizar.jsp");
    		dispatcher.forward(request, response);
        	
        }else{
        	
        	tecnicoModel tecnico = new tecnicoModel(nome, setor, cargo, login, senha, tipo);
        	boolean inseriu = aDAO.atualizarTecnico(nome, setor, cargo, login, senha, tipo, id);
        	request.setAttribute("status", inseriu);
    		RequestDispatcher dispatcher = request.getRequestDispatcher("/saidaAtualizar.jsp");
    		dispatcher.forward(request, response);
        }
		
	}
	private void editarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		ArrayList<clienteModel> editar = aDAO.editarUsuario(id);

		request.setAttribute("status", editar);
		request.setAttribute("id", id);		
		request.getRequestDispatcher("/editarUsuario.jsp").forward(request, response);
		
	}
	private void excluirUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		boolean inserido = aDAO.excluirUsuario(id);

		request.setAttribute("status", inserido);
		request.setAttribute("operacao", "Excluído");
				
		request.getRequestDispatcher("/statusExluido.jsp").forward(request, response);
		
	}
	private void mudarPrioridade(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String prioridade = request.getParameter("prioridade");
		 int id = Integer.parseInt(request.getParameter("id"));
		 boolean inserido = aDAO.atualizarChamado(prioridade, id);
		 request.setAttribute("status", inserido);
		 request.setAttribute("operacao", "cadastrada");
				
		 request.getRequestDispatcher("/statusAtualizado.jsp").forward(request, response);
		
	}
	private void listarTecnicos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<clienteModel> listaTecnicos = aDAO.qtdUsuariosTecnicos();
		request.setAttribute("listaTecnicos", listaTecnicos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/GerenciarUsuariosTecnicos.jsp");
		dispatcher.forward(request, response);
		
	}
	private void listarClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<clienteModel> listaCliente = aDAO.qtdUsuariosClientes();
		request.setAttribute("listaCliente", listaCliente);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/GerenciarUsuariosClientes.jsp");
		dispatcher.forward(request, response);
		
	}
	private void recuperarChamados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int fechados = (int) aDAO.chamadosFechados();
		request.setAttribute("listaFechados", fechados);
		
		int abertos = (int) aDAO.chamadosAbertos();
		request.setAttribute("listaAbertos", abertos);
		
		int todos = (int) aDAO.todosChamados();
		request.setAttribute("listaTodos", todos);
		
		int andamento = (int) aDAO.chamadosAndamento();
		request.setAttribute("listaAndamento", andamento);
		
		int baixo = (int) aDAO.baixo();
		request.setAttribute("listaBaixa", baixo);
		
		int medio = (int) aDAO.medio();
		request.setAttribute("listaMedia", medio);
		
		int alto = (int) aDAO.alto();
		request.setAttribute("listaAlta", alto);
		
		int muitoAlto = (int) aDAO.muitoAlto();
		request.setAttribute("listaMuitoAlta", muitoAlto);
		
		int usuarios = (int) aDAO.listaUsuarios();
		request.setAttribute("listaUsuarios", usuarios);
		
		int usuariosCliente = (int) aDAO.listaUsuariosClientes();
		request.setAttribute("listaCliente", usuariosCliente);
		
		int usuariosTecnico = (int) aDAO.listaUsuariosTecnicos();
		request.setAttribute("listaTecnico", usuariosTecnico);
		
		int eficiencia = (int) aDAO.eficiencia(abertos, usuariosTecnico, fechados, usuariosCliente);
		request.setAttribute("eficiencia", eficiencia);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard.jsp");
		dispatcher.forward(request, response);
	}

	private void listarChamadosFechados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<chamadosModel> lista = aDAO.listarChamadosFechados();
		
		request.setAttribute("listaChamadosFechados", lista);
		RequestDispatcher dispatcher = request.getRequestDispatcher("");
		dispatcher.forward(request, response);
		
	}
	private void listarChamados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<chamadosModel> lista = aDAO.listarChamados();
		
		request.setAttribute("listaChamados", lista);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/GerenciarChamados.jsp");
		dispatcher.forward(request, response);
		
	}
	private void listarUsuariosClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ArrayList<clienteModel> listaCliente = aDAO.listarCliente();
		
		request.setAttribute("listaCliente", listaCliente);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/GerenciarUsuarios.jsp");
		dispatcher.forward(request, response);
		
	}
	
	@SuppressWarnings("unused")
	private void recuperarChamadosAbertos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int abertos = (int) aDAO.chamadosAbertos();
		request.setAttribute("listaAbertos", abertos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard.jsp");
		dispatcher.forward(request, response);
		
	}
}
