package Model;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class AdministradorDAO {
	private DataSource dataSource;
	
	public AdministradorDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	  public administradorModel autenticar(String email, String senha) {
	        
	        Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultado = null;
			administradorModel admin = null;

	        try {
				connection = dataSource.getConnection();
				preparedStatement = connection.prepareStatement("SELECT * FROM administradores WHERE email = ? AND senha = ?");
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, senha);
				resultado = preparedStatement.executeQuery();

	            if (resultado.next()) {
	            	int id = resultado.getInt("id");
	            	String nome = resultado.getString("nome");
	            	email = resultado.getString("email");
	            	senha = resultado.getString("senha");

	            	admin = new administradorModel(nome, email, senha);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }finally {
				fecharConexao(connection, preparedStatement, null);
			}

	        return admin;
	    }
	 

	private void fecharConexao(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		try {
			if(connection != null)
			connection.close();
				
			if(preparedStatement != null)
			preparedStatement.close();
			
			if(resultSet != null)
			resultSet.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean cadastrarCliente(String nome, String setor, String cargo, String login, String senha, String tipo) {
		
		Connection conexao = null;
		PreparedStatement statement = null;
		int resultado;
		try {
			conexao = dataSource.getConnection();
			String sql = "INSERT INTO `usuarios`(`nome`, `setor`, `cargo`, `login`, `senha`, `tipo`) VALUES (?,?,?,?,?,?)";
			statement = conexao.prepareStatement(sql);
			statement.setString(1, nome);
			statement.setString(2, setor);
			statement.setString(3, cargo);
			statement.setString(4, login);
			statement.setString(5, senha);
			statement.setString(6, tipo);

			resultado = statement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			resultado = 0;
		}
		finally {
			fecharConexao(conexao,statement,null);
		}
		return resultado == 1;
		
	}

	public boolean cadastrarTecnico(String nome, String setor, String cargo, String login, String senha, String tipo) {
		Connection conexao = null;
		PreparedStatement statement = null;
		int resultado;
		try {
			conexao = dataSource.getConnection();
			String sql = "INSERT INTO `usuarios`(`nome`, `setor`, `cargo`, `login`, `senha`, `tipo`) VALUES (?,?,?,?,?,?)";
			statement = conexao.prepareStatement(sql);
			statement.setString(1, nome);
			statement.setString(2, setor);
			statement.setString(3, cargo);
			statement.setString(4, login);
			statement.setString(5, senha);
			statement.setString(6, tipo);

			resultado = statement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			resultado = 0;
		}
		finally {
			fecharConexao(conexao,statement,null);
		}
		return resultado == 1;
		
	}

	public ArrayList<clienteModel> listarCliente() {
		ArrayList<clienteModel> listaCliente = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `nome`, `setor`, `cargo`, `login`, `senha`, `tipo` FROM `usuarios` WHERE 1";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				int id = resultado.getInt("id");
				String nome = resultado.getString("nome");
				String setor = resultado.getString("setor");
				String cargo = resultado.getString("cargo");
				String login = resultado.getString("login");
				String senha = resultado.getString("senha");
				String tipo = resultado.getString("tipo");
				
				clienteModel cliente = new clienteModel(id, nome, login, senha, setor, cargo, tipo);
				listaCliente.add(cliente);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return listaCliente;
	}

	public ArrayList<chamadosModel> listarChamados() {
		
		ArrayList<chamadosModel> lista= new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `usuario_id`, `tecnico_id`, `prioridade`, `status`, `descricao`, `data_abertura`, `data_fechamento` FROM `chamados`";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				int chamado_id = resultado.getInt("id");
				int usuario_id = resultado.getInt("usuario_id");
				int tecnico_id = resultado.getInt("tecnico_id");
				String prioridade = resultado.getString("prioridade");
				String status = resultado.getString("status");
				String descricao = resultado.getString("descricao");
				Date data_abertura = resultado.getDate("data_abertura");
				Date data_fechamento = resultado.getDate("data_fechamento");
				
				chamadosModel chamados = new chamadosModel(chamado_id, usuario_id, tecnico_id, descricao, prioridade, status, data_abertura, data_fechamento);
				lista.add(chamados);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return lista;
		
	}

	public ArrayList<chamadosModel> listarChamadosFechados() {
		
		ArrayList<chamadosModel> lista= new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `usuario_id`, `tecnico_id`, `prioridade`, `status`, `descricao`, `data_abertura`, `data_fechamento` FROM `chamados` WHERE status = \"fechado\"";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				int chamado_id = resultado.getInt("id");
				int usuario_id = resultado.getInt("usuario_id");
				int tecnico_id = resultado.getInt("tecnico_id");
				String prioridade = resultado.getString("prioridade");
				String status = resultado.getString("status");
				String descricao = resultado.getString("descricao");
				Date data_abertura = resultado.getDate("data_abertura");
				Date data_fechamento = resultado.getDate("data_fechamento");
				
				chamadosModel chamados = new chamadosModel(chamado_id, usuario_id, tecnico_id, prioridade, status, descricao, data_abertura, data_fechamento);
				lista.add(chamados);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return lista;
		
	}

	public int chamadosAbertos() {
		
		int abertos= 0;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT count(status)\n"
					+ "from chamados where status = 'aberto'";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			resultado.next();
			abertos = resultado.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return abertos;
		
	}

	public int chamadosFechados() {
		int fechados= 0;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT count(status)\n"
					+ "from chamados where status = 'fechado'";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			resultado.next();
			fechados = resultado.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return fechados;
	}

	public int todosChamados() {
		int todos= 0;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT count(status)\n"
					+ "from chamados";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			resultado.next();
			todos = resultado.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return todos;
	}

	public int baixo() {
		int baixo= 0;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT count(prioridade)\n"
					+ "from chamados where prioridade = 'baixa'";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			resultado.next();
			baixo = resultado.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return baixo;
	}

	public int medio() {
		int medio= 0;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT count(prioridade)\n"
					+ "from chamados where prioridade = 'media'";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			resultado.next();
			medio = resultado.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return medio;
	}

	public int alto() {
		int alto= 0;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT count(prioridade)\n"
					+ "from chamados where prioridade = 'alta'";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			resultado.next();
			alto = resultado.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return alto;
	}

	public int muitoAlto() {
		int muitoAlto = 0;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT count(prioridade)\n"
					+ "from chamados where prioridade = 'muito alta'";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			resultado.next();
			muitoAlto = resultado.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return muitoAlto;
	}

	public int listaUsuarios() {
		int usuarios = 0;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT count(tipo)\n"
					+ "from usuarios";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			resultado.next();
			usuarios = resultado.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return usuarios;
	}

	public int listaUsuariosClientes() {
		int usuariosClientes = 0;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT count(tipo)\n"
					+ "from usuarios where tipo='cliente'";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			resultado.next();
			usuariosClientes = resultado.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return usuariosClientes;
	}

	public int listaUsuariosTecnicos() {
		int usuariosTecnico = 0;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT count(tipo)\n"
					+ "from usuarios where tipo='tecnico'";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			resultado.next();
			usuariosTecnico = resultado.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return usuariosTecnico;
	}

	public ArrayList<clienteModel> qtdUsuariosClientes() {
		ArrayList<clienteModel> listaCliente = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `nome`, `setor`, `cargo`, `login`, `senha`, `tipo` FROM `usuarios` WHERE tipo='cliente'";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				int id = resultado.getInt("id");
				String nome = resultado.getString("nome");
				String setor = resultado.getString("setor");
				String cargo = resultado.getString("cargo");
				String login = resultado.getString("login");
				String senha = resultado.getString("senha");
				String tipo = resultado.getString("tipo");
				
				clienteModel cliente = new clienteModel(id, nome, login, senha, setor, cargo, tipo);
				listaCliente.add(cliente);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return listaCliente;
	}

	public int eficiencia(int abertos, int usuariosTecnico, int usuariosCliente ,int fechados) {
		
		int eficiencia = 100;
		
		if(abertos > fechados) {
			
			eficiencia = eficiencia - 30;
			
		} else if( abertos == fechados) {
			
			eficiencia = eficiencia - 20;
		}
		if(usuariosCliente > (5 * usuariosTecnico)) {
			
			eficiencia = eficiencia - 30;
			
		}
		if(abertos > (5* usuariosTecnico)) {
			
			eficiencia = eficiencia - 30;
			
		} else if(abertos == (5*usuariosTecnico)) {
			
			eficiencia = eficiencia - 20;
		}
		
		
		return eficiencia;
	}

	public ArrayList<clienteModel> qtdUsuariosTecnicos() {
		ArrayList<clienteModel> listaTecnicos = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `nome`, `setor`, `cargo`, `login`, `senha`, `tipo` FROM `usuarios` WHERE tipo='tecnico'";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				int id = resultado.getInt("id");
				String nome = resultado.getString("nome");
				String setor = resultado.getString("setor");
				String cargo = resultado.getString("cargo");
				String login = resultado.getString("login");
				String senha = resultado.getString("senha");
				String tipo = resultado.getString("tipo");
				
				clienteModel cliente = new clienteModel(id, nome, login, senha, setor, cargo, tipo);
				listaTecnicos.add(cliente);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return listaTecnicos;
	}
	
	public boolean atualizarChamado(String prioridade, int id) {
		
		Connection conexao = null;
		PreparedStatement statement = null;
		int resultado;
		try {
			conexao = dataSource.getConnection();
			String sql = "UPDATE `chamados` SET `prioridade` = ? WHERE id = ?";
			statement = conexao.prepareStatement(sql);
			statement.setString(1, prioridade);
			statement.setInt(2, id);

			resultado = statement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			resultado = 0;
		}
		finally {
			fecharConexao(conexao,statement,null);
		}
		return resultado == 1;
		
	}

	public boolean excluirUsuario(int id) {
		Connection conexao = null;
		PreparedStatement statement = null;
		int resultado;
		try {
			conexao = dataSource.getConnection();
			String sql = "DELETE FROM `usuarios` WHERE id = ?";
			statement = conexao.prepareStatement(sql);
			statement.setInt(1, id);

			resultado = statement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			resultado = 0;
		}
		finally {
			fecharConexao(conexao,statement,null);
		}
		return resultado == 1;
		
	}

	public ArrayList<clienteModel> editarUsuario(int id) {
		ArrayList<clienteModel> editar = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `nome`, `setor`, `cargo`, `login`, `senha`, `tipo` FROM `usuarios` WHERE id = ?";
			statement = conexao.prepareStatement(sql);
			statement.setInt(1, id);
			resultado = statement.executeQuery();
			
			
			while(resultado.next()) {
				
				String nome = resultado.getString("nome");
				String setor = resultado.getString("setor");
				String cargo = resultado.getString("cargo");
				String login = resultado.getString("login");
				String senha = resultado.getString("senha");
				String tipo = resultado.getString("tipo");
				
				clienteModel cliente = new clienteModel(id, nome, login, senha, setor, cargo, tipo);
				editar.add(cliente);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return editar;
	}
	
	public boolean atualizarCliente(String nome, String setor, String cargo, String login, String senha, String tipo, int id) {
		Connection conexao = null;
		PreparedStatement statement = null;
		int resultado;
		try {
			conexao = dataSource.getConnection();
			String sql = "UPDATE `usuarios` SET `nome`= ?,`setor`= ?,`cargo`= ?,`login`= ?,`senha`= ?,`tipo`= ? WHERE id = ? ";
			statement = conexao.prepareStatement(sql);
			statement.setString(1, nome);
			statement.setString(2, setor);
			statement.setString(3, cargo);
			statement.setString(4, login);
			statement.setString(5, senha);
			statement.setString(6, tipo);
			statement.setInt(7, id);

			resultado = statement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			resultado = 0;
		}
		finally {
			fecharConexao(conexao,statement,null);
		}
		return resultado == 1;
	}

	public boolean atualizarTecnico(String nome, String setor, String cargo, String login, String senha, String tipo,
			int id) {
		Connection conexao = null;
		PreparedStatement statement = null;
		int resultado;
		try {
			conexao = dataSource.getConnection();
			String sql = "UPDATE `usuarios` SET `nome`= ?,`setor`= ?,`cargo`= ?,`login`= ?,`senha`= ?,`tipo`= ? WHERE id = ? ";
			statement = conexao.prepareStatement(sql);
			statement.setString(1, nome);
			statement.setString(2, setor);
			statement.setString(3, cargo);
			statement.setString(4, login);
			statement.setString(5, senha);
			statement.setString(6, tipo);
			statement.setInt(7, id);

			resultado = statement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			resultado = 0;
		}
		finally {
			fecharConexao(conexao,statement,null);
		}
		return resultado == 1;
	}

	public int chamadosAndamento() {
		int andamento= 0;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT count(status)\n"
					+ "from chamados where status = 'Em Andamento'";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			resultado.next();
			andamento = resultado.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return andamento;
	}

	public ArrayList<chamadosModel> filtrarFechados() {
		ArrayList<chamadosModel> listaFechados = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `usuario_id`, `tecnico_id`, `prioridade`, `status`, `descricao`, `data_abertura`, `data_fechamento` FROM `chamados` WHERE status = \"fechado\"";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				int chamado_id = resultado.getInt("id");
				int usuario_id = resultado.getInt("usuario_id");
				int tecnico_id = resultado.getInt("tecnico_id");
				String prioridade = resultado.getString("prioridade");
				String status = resultado.getString("status");
				String descricao = resultado.getString("descricao");
				Date data_abertura = resultado.getDate("data_abertura");
				Date data_fechamento = resultado.getDate("data_fechamento");
				
				chamadosModel chamados = new chamadosModel(chamado_id, usuario_id, tecnico_id, descricao, prioridade, status, data_abertura, data_fechamento);
				listaFechados.add(chamados);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return listaFechados;
		
	}

	public ArrayList<chamadosModel> filtrarAberta() {
		ArrayList<chamadosModel> listaAbertos = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `usuario_id`, `tecnico_id`, `prioridade`, `status`, `descricao`, `data_abertura`, `data_fechamento` FROM `chamados` WHERE status = \"aberto\"";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				int chamado_id = resultado.getInt("id");
				int usuario_id = resultado.getInt("usuario_id");
				int tecnico_id = resultado.getInt("tecnico_id");
				String prioridade = resultado.getString("prioridade");
				String status = resultado.getString("status");
				String descricao = resultado.getString("descricao");
				Date data_abertura = resultado.getDate("data_abertura");
				Date data_fechamento = resultado.getDate("data_fechamento");
				
				chamadosModel chamados = new chamadosModel(chamado_id, usuario_id, tecnico_id, descricao, prioridade, status, data_abertura, data_fechamento);
				listaAbertos.add(chamados);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return listaAbertos;
		
	}

	public ArrayList<chamadosModel> filtrarMuitoAlta() {
		ArrayList<chamadosModel> listaMuitoAlta = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `usuario_id`, `tecnico_id`, `prioridade`, `status`, `descricao`, `data_abertura`, `data_fechamento` FROM `chamados` WHERE prioridade = \"muito alta\"";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				int chamado_id = resultado.getInt("id");
				int usuario_id = resultado.getInt("usuario_id");
				int tecnico_id = resultado.getInt("tecnico_id");
				String prioridade = resultado.getString("prioridade");
				String status = resultado.getString("status");
				String descricao = resultado.getString("descricao");
				Date data_abertura = resultado.getDate("data_abertura");
				Date data_fechamento = resultado.getDate("data_fechamento");
				
				chamadosModel chamados = new chamadosModel(chamado_id, usuario_id, tecnico_id, descricao, prioridade, status, data_abertura, data_fechamento);
				listaMuitoAlta.add(chamados);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return listaMuitoAlta;
		
	}

	public ArrayList<chamadosModel> filtrarAlta() {
		ArrayList<chamadosModel> listaAlta = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `usuario_id`, `tecnico_id`, `prioridade`, `status`, `descricao`, `data_abertura`, `data_fechamento` FROM `chamados` WHERE prioridade = \"alta\"";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				int chamado_id = resultado.getInt("id");
				int usuario_id = resultado.getInt("usuario_id");
				int tecnico_id = resultado.getInt("tecnico_id");
				String prioridade = resultado.getString("prioridade");
				String status = resultado.getString("status");
				String descricao = resultado.getString("descricao");
				Date data_abertura = resultado.getDate("data_abertura");
				Date data_fechamento = resultado.getDate("data_fechamento");
				
				chamadosModel chamados = new chamadosModel(chamado_id, usuario_id, tecnico_id, descricao, prioridade, status, data_abertura, data_fechamento);
				listaAlta.add(chamados);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return listaAlta;
	}

	public ArrayList<chamadosModel> filtrarMedia() {
		ArrayList<chamadosModel> listaMedia = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `usuario_id`, `tecnico_id`, `prioridade`, `status`, `descricao`, `data_abertura`, `data_fechamento` FROM `chamados` WHERE prioridade = \"media\"";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				int chamado_id = resultado.getInt("id");
				int usuario_id = resultado.getInt("usuario_id");
				int tecnico_id = resultado.getInt("tecnico_id");
				String prioridade = resultado.getString("prioridade");
				String status = resultado.getString("status");
				String descricao = resultado.getString("descricao");
				Date data_abertura = resultado.getDate("data_abertura");
				Date data_fechamento = resultado.getDate("data_fechamento");
				
				chamadosModel chamados = new chamadosModel(chamado_id, usuario_id, tecnico_id, descricao, prioridade, status, data_abertura, data_fechamento);
				listaMedia.add(chamados);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return listaMedia;
	}

	public ArrayList<chamadosModel> filtrarBaixa() {
		ArrayList<chamadosModel> listaBaixa = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `usuario_id`, `tecnico_id`, `prioridade`, `status`, `descricao`, `data_abertura`, `data_fechamento` FROM `chamados` WHERE prioridade = \"baixa\"";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				int chamado_id = resultado.getInt("id");
				int usuario_id = resultado.getInt("usuario_id");
				int tecnico_id = resultado.getInt("tecnico_id");
				String prioridade = resultado.getString("prioridade");
				String status = resultado.getString("status");
				String descricao = resultado.getString("descricao");
				Date data_abertura = resultado.getDate("data_abertura");
				Date data_fechamento = resultado.getDate("data_fechamento");
				
				chamadosModel chamados = new chamadosModel(chamado_id, usuario_id, tecnico_id, descricao, prioridade, status, data_abertura, data_fechamento);
				listaBaixa.add(chamados);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return listaBaixa;
	}

	public ArrayList<chamadosModel> filtrarAndamento() {
		ArrayList<chamadosModel> listaAndamento = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `usuario_id`, `tecnico_id`, `prioridade`, `status`, `descricao`, `data_abertura`, `data_fechamento` FROM `chamados` WHERE status = \"em andamento\"";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				int chamado_id = resultado.getInt("id");
				int usuario_id = resultado.getInt("usuario_id");
				int tecnico_id = resultado.getInt("tecnico_id");
				String prioridade = resultado.getString("prioridade");
				String status = resultado.getString("status");
				String descricao = resultado.getString("descricao");
				Date data_abertura = resultado.getDate("data_abertura");
				Date data_fechamento = resultado.getDate("data_fechamento");
				
				chamadosModel chamados = new chamadosModel(chamado_id, usuario_id, tecnico_id, descricao, prioridade, status, data_abertura, data_fechamento);
				listaAndamento.add(chamados);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return listaAndamento;
		
	}
	public String procurarEmail(int id) {
		
	    String destinatario = "";
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();

			preparedStatement = connection.prepareStatement("SELECT `login` FROM `usuarios` WHERE id = ?");
			preparedStatement.setInt(1, id);			
			resultSet = preparedStatement.executeQuery();
				
			while(resultSet.next()) {
				
				destinatario = resultSet.getString("login");

			}
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
			fecharConexao(connection, preparedStatement, null);
		}
	    return destinatario;
	}

	public int jaAvaliou(int id) {
		
		int avaliou= 0;
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT count(*) FROM avaliacoes WHERE usuario_id = ?";	
			statement = conexao.prepareStatement(sql);
			statement.setInt(1, id);
			resultado = statement.executeQuery();
			resultado.next();
			avaliou = resultado.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return avaliou;
	}

	public boolean avaliar(int id_usuario, String nome, String nota, String utilidade, String comentario) {
		
		Connection conexao = null;
		PreparedStatement statement = null;
		int resultado;
		try {
			conexao = dataSource.getConnection();
			String sql = "INSERT INTO `avaliacoes`( `usuario_id`, `nome`, `nota`, `utilidade`, `comentario`) VALUES ( ?, ?, ?, ?, ?)";
			statement = conexao.prepareStatement(sql);
			statement.setInt(1,id_usuario);
			statement.setString(2, nome);
			statement.setString(3, nota);
			statement.setString(4, utilidade);
			statement.setString(5, comentario);


			resultado = statement.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
			resultado = 0;
		}
		finally {
			fecharConexao(conexao,statement,null);
		}
		return resultado == 1;
	}

	public ArrayList<avaliacoesModel> listaAvaliacoes() {
		ArrayList<avaliacoesModel> lista = new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;
		
		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `usuario_id`, `nome`, `nota`, `utilidade`, `comentario` FROM `avaliacoes` WHERE 1";
			statement = conexao.prepareStatement(sql);
			resultado = statement.executeQuery();
			
			while(resultado.next()) {
				
				int usuario_id = resultado.getInt("usuario_id");
				String nome = resultado.getString("nome");
				String nota = resultado.getString("nota");
				String utilidade = resultado.getString("utilidade");
				String comentario = resultado.getString("comentario");
				
				avaliacoesModel avaliacoes = new avaliacoesModel(usuario_id, nome, nota, utilidade, comentario);
				lista.add(avaliacoes);
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return lista;
		
	}

}
