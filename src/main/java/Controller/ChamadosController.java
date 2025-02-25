package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.sql.DataSource;

import Model.ChamadoDAO;
import Model.GeradorPDF;
import Model.MenssagensModel;
import Model.chamadosModel;
import Model.clienteModel;
import Model.emailDAO;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ChamadosController")
public class ChamadosController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ChamadoDAO cDAO;

	
	@Resource(name = "bancoTechsolutions")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		cDAO = new ChamadoDAO(dataSource);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao").toLowerCase();

        switch (acao) {

        case "vertickets":
        	listarChamadosCliente(request, response);
            break;
        case "listarchamados":
        	listarChamados(request, response);
            break;
            
        case "listarchamadostecnico":
        	listarChamadosTecnico(request, response);
        	break;
        	
        case "listarchamadosresolvidos":
        	listarChamadosResolvidos(request, response);
        	break;

        case "assumirchamado":
        	assumirChamados(request, response);
            break;
            
        case "gerarrelatorio":
			gerarRelatorios(request, response);
            break;
            
		case "gerarrelatoriopdf":
            gerarRelatorioPDF(request, response);
            break; 
        
        case "comentar":
        	
        	comentar(request, response);
        	break;
        case "recuperarcomentario":
        	
        	recuperarComentario(request, response);
        	break;
        
        case "recuperarcomentariotecnico":
        	
        	recuperarComentarioTecnico(request, response);
        	break;
        	
        case "responder":
        	
        	comentarTecnico(request, response);
        	break;
		default:
			System.out.println("Erro! - Operacao nao encontrada");
        }
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao").toLowerCase();

        switch (acao) {

		case "registrar":
			cadastrarChamado(request, response);
		break;
		case "atualizar":
			atualizarStatusChamado(request, response);
			break;
		default:
			System.out.println("Erro! - Operacao nao encontrada");
        }
    }
	
	private void recuperarComentario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		ArrayList<MenssagensModel> listaMenssagens = cDAO.recuperarMenssagens(id);
		request.setAttribute("listaMenssagens", listaMenssagens);	
		
		ArrayList<MenssagensModel> listaMenssagensTecnico = cDAO.recuperarMenssagensTecnico(id);
		request.setAttribute("listaMenssagensTecnico", listaMenssagensTecnico);	
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/verComentario.jsp");	
		dispatcher.forward(request, response);
		
	}

	private void recuperarComentarioTecnico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		ArrayList<MenssagensModel> listaMenssagens = cDAO.recuperarMenssagens(id);
		request.setAttribute("listaMenssagens", listaMenssagens);	
		
		ArrayList<MenssagensModel> listaMenssagensTecnico = cDAO.recuperarMenssagensTecnico(id);
		request.setAttribute("listaMenssagensTecnico", listaMenssagensTecnico);	
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/verComentarioTecnico.jsp");	
		dispatcher.forward(request, response);
		
	}


	private void comentar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		String comentario = request.getParameter("msg");
		MenssagensModel msg = new MenssagensModel(comentario, id, "cliente");
		boolean inserido = cDAO.comentar(id, comentario);
		request.getRequestDispatcher("/statusComentario.jsp").forward(request, response);
		
	}
	private void comentarTecnico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		String comentario = request.getParameter("msg");
		MenssagensModel msg = new MenssagensModel(comentario, id, "cliente");
		boolean inserido = cDAO.comentarTecnico(id, comentario);
		request.getRequestDispatcher("/statusComentarioTecnico.jsp").forward(request, response);
		
	}

	private void cadastrarChamado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		clienteModel cliente = (clienteModel) session.getAttribute("usuarioLogado");
		
		int usuarioId = cliente.getId();
        String prioridade = request.getParameter("prioridade");
        String descricao = request.getParameter("descricao");
        String status = "aberto";
        Date dataAbertura = new Date();
		

		boolean inserido = cDAO.registrarChamado(usuarioId, prioridade, status, descricao, dataAbertura);

		request.setAttribute("status", inserido);
		request.setAttribute("operacao", "cadastrada");
				
		request.getRequestDispatcher("/status.jsp").forward(request, response);
	}
	 
	 public void listarChamadosCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();
		 clienteModel cliente = (clienteModel) session.getAttribute("usuarioLogado");
		 ArrayList<chamadosModel> listaChamadosCliente = cDAO.listaChamadosCliente(cliente.getId());
		 
		 request.setAttribute("listaChamadosCliente", listaChamadosCliente);
			
		 RequestDispatcher dispatcher = request.getRequestDispatcher("/exibeChamadosCliente.jsp");
			
		 dispatcher.forward(request, response);
	    
	    }
		private void listarChamados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			ArrayList<chamadosModel> lista = cDAO.listarChamados();
			request.setAttribute("listaChamadosAbertos", lista);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/ChamadosAbertosTecnico.jsp");
			dispatcher.forward(request, response);
				
		}
		 public void listarChamadosTecnico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			 HttpSession session = request.getSession();
			 clienteModel tecnico = (clienteModel) session.getAttribute("usuarioLogado");
			 ArrayList<chamadosModel> listaChamadosTecnico = cDAO.buscarChamadosAtribuidos(tecnico.getId());
			 
			 request.setAttribute("listaChamadosAtribuidos", listaChamadosTecnico);
			 
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/ChamadosAtribuidos.jsp");
			 
			 dispatcher.forward(request, response);
			 
		 }
		 
		 public void assumirChamados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			 HttpSession session = request.getSession();
			 clienteModel cliente = (clienteModel) session.getAttribute("usuarioLogado");
				
			  int idChamado = Integer.parseInt(request.getParameter("id"));
			  int tecnicoId = cliente.getId();

		      cDAO.assumirChamados(idChamado, tecnicoId);
		        
		      request.getRequestDispatcher("/ChamadosAtribuidos.jsp").forward(request, response);;

		 }
		 
		 public void atualizarStatusChamado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        int id = Integer.parseInt(request.getParameter("id"));
		        int id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
		        String novoStatus = request.getParameter("statusChamado");
		        String destinatario = cDAO.procurarEmail(id_usuario);
		        String assunto = "TechSolutions - Alteração de status do seu ticket";
		        String menssagem = "O status do seu ticket foi atualizado, confira no sistema!";
		        
		        cDAO.atualizarStatusChamado(id, novoStatus); 
		        emailDAO eDAO = new emailDAO();
		        eDAO.enviarEmail(destinatario, assunto , menssagem);
			    request.getRequestDispatcher("/DashboardTecnico.jsp").forward(request, response);;

		 }
		 
		 public void listarChamadosResolvidos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			 HttpSession session = request.getSession();
			 clienteModel tecnico = (clienteModel) session.getAttribute("usuarioLogado");
			 ArrayList<chamadosModel> listaChamadosResolvidos = cDAO.buscarChamadosResolvidosTec(tecnico.getId());
			 
			 request.setAttribute("listaChamadosResolvidos", listaChamadosResolvidos);
			 
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/ChamadosResolvidos.jsp");
			 
			 dispatcher.forward(request, response);
			 
		 }
		 public void gerarRelatorios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
				double tempoMedio = cDAO.calcularTempoMedioSistema();
				Map<String, Integer> problemasFrequentes = cDAO.calcularProblemasMaisFrequentes();
				Map<String, Integer> chamadosStatus = cDAO.calcularChamadosPorStatus();
				Map<String, Integer> chamadosPrioridade = cDAO.calcularChamadosPorStatus();
			    
				request.setAttribute("tempoMedio", tempoMedio);
				request.setAttribute("problemasFrequentes", problemasFrequentes);
				request.setAttribute("chamadosStatus", chamadosStatus);
				request.setAttribute("chamadosPrioridade", chamadosPrioridade);

				request.getRequestDispatcher("/relatorio.jsp").forward(request, response);
				;

			}
		 	public void gerarRelatorioPDF(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        double tempoMedio = cDAO.calcularTempoMedioSistema();
		        Map<String, Integer> problemasFrequentes = cDAO.calcularProblemasMaisFrequentes();
		        Map<String, Integer> chamadosStatus = cDAO.calcularChamadosPorStatus();
		        Map<String, Integer> chamadosPrioridade = cDAO.calcularChamadosPorStatus();

		        GeradorPDF.gerarRelatorioCompleto(tempoMedio, problemasFrequentes, chamadosStatus,chamadosPrioridade, response);
		    }
}
