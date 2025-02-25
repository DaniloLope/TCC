package Controller;

import java.io.IOException;

import javax.sql.DataSource;

import Model.AdministradorDAO;
import Model.administradorModel;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AdministradorDAO aDAO;
	
	@Resource(name = "bancoTechsolutions")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		aDAO = new AdministradorDAO(dataSource);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        administradorModel admin = aDAO.autenticar(email, senha);

        if (admin != null) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
    			
    		request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } else {
    		request.getRequestDispatcher("/login.jsp").forward(request, response);

        }
        
	}
}
