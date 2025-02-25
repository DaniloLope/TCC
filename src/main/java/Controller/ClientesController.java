package Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.sql.DataSource;

import Model.AdministradorDAO;
import Model.ClienteDAO;
import Model.chamadosModel;
import Model.clienteModel;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ClientesController")
public class ClientesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ClienteDAO cDAO;

	
	@Resource(name = "bancoTechsolutions")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		cDAO = new ClienteDAO(dataSource);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao").toLowerCase();

        switch (acao) {
        case "login":
            loginUsuario(request, response);
            break;
		
		default:
			System.out.println("Erro! - Operacao nao encontrada");
        }
    }
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action").toLowerCase();

        switch (action) {
        case "recuperarchamados":
        	
            recuperarChamados(request, response);
            break;
            
        case "recuperarchamadoscliente":
        	
        	recuperarChamadosCliente(request, response);
        	break;
        	
		default:
			System.out.println("Erro! - Operacao nao encontrada");
        }
    }
	
	
	 private void recuperarChamadosCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 	HttpSession session = request.getSession();
		 	clienteModel cliente = (clienteModel) session.getAttribute("usuarioLogado");
			
		 	int chamadosClientes = cDAO.listarChamadosClientes(cliente.getId());
			request.setAttribute("listaChamados", chamadosClientes);
			
			int chamadosFechadosClientes = cDAO.listarChamadosFechadosClientes(cliente.getId());
			request.setAttribute("listaFechados", chamadosFechadosClientes);
			
			int baixaCliente = cDAO.baixaCliente(cliente.getId());
			request.setAttribute("listaBaixa", baixaCliente);
			
			int mediaCliente = cDAO.mediaCliente(cliente.getId());
			request.setAttribute("listaMedia", mediaCliente);
			
			int altaCliente = cDAO.altaCliente(cliente.getId());
			request.setAttribute("listaAlta", altaCliente);
			
			int muitoAltaCliente = cDAO.muitoAltaCliente(cliente.getId());
			request.setAttribute("listaMuitoAlta", muitoAltaCliente);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/DashboardCliente.jsp");
			dispatcher.forward(request, response);
			
	}

	private void recuperarChamados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 	HttpSession session = request.getSession();
			clienteModel tecnico = (clienteModel) session.getAttribute("usuarioLogado");
			
		 	int chamados = cDAO.listarChamados();
			request.setAttribute("listaChamados", chamados);
			
			int chamadosFechados = cDAO.listarChamadosFechados(tecnico.getId());
			request.setAttribute("listaFechados", chamadosFechados);
			
			int baixa = cDAO.baixa();
			request.setAttribute("listaBaixa", baixa);
			
			int media = cDAO.media();
			request.setAttribute("listaMedia", media);
			
			int alta = cDAO.alta();
			request.setAttribute("listaAlta", alta);
			
			int muitoAlta = cDAO.muitoAlta();
			request.setAttribute("listaMuitoAlta", muitoAlta);
			
			
			int pendentes = cDAO.pendentes(tecnico.getId());
			request.setAttribute("listaPendentes", pendentes);
			
			int assumidos = cDAO.assumidos(tecnico.getId());
			request.setAttribute("listaPendentes", assumidos);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/DashboardTecnico.jsp");
			dispatcher.forward(request, response);
		
	}

	private void loginUsuario(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
			String login = request.getParameter("login");
	        String senha = request.getParameter("senha");
	    	Boolean logou;

	        clienteModel cliente = cDAO.autenticarUsuario(login, senha);

	        if (cliente != null) {
	            HttpSession session = request.getSession();
	            session.setAttribute("usuarioLogado",cliente );

	            if ("cliente".equals(cliente.getTipo())) {
		        	logou = true;

		            request.getRequestDispatcher("/DashboardCliente.jsp").forward(request, response);
		            
	            } else if ("tecnico".equals(cliente.getTipo())) {
	            	
		        	logou = true;
		            request.getRequestDispatcher("/DashboardTecnico.jsp").forward(request, response);
	            }
	        } else {
	        	logou= false;
				request.setAttribute("status", logou);
	            request.getRequestDispatcher("/loginUsuarios.jsp").forward(request, response);
	          }		
	}
}
