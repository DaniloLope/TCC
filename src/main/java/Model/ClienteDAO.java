package Model;
import java.sql.*;
import java.util.ArrayList;

import javax.sql.DataSource;

public class ClienteDAO {
	private DataSource dataSource;
	
	public ClienteDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public clienteModel autenticarUsuario(String login, String senha) {
	        
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultado = null;
		clienteModel cliente = null;

	    try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM usuarios WHERE login = ? AND senha = ?");
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, senha);
			resultado = preparedStatement.executeQuery();

            if (resultado.next()) {
            	int id = resultado.getInt("id");
            	String setor = resultado.getString("setor");
            	String tipo = resultado.getString("tipo");
            	String cargo = resultado.getString("cargo");
            	String nome = resultado.getString("nome");
            	login = resultado.getString("login");
            	senha = resultado.getString("senha");

            	cliente = new clienteModel(id, nome, login, senha, setor, cargo, tipo);
            }
	    	
	    }catch (SQLException e) {
            e.printStackTrace();
        }finally {
			fecharConexao(connection, preparedStatement, null);
		}
	    return cliente;
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
	public int listarChamados() {
		
		int chamados= 0;
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
			chamados = resultado.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(conexao,statement,resultado);
		}
		return chamados;
		
	}
	public int listarChamadosFechados(int id) {
		
		int fechados = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT count(status) FROM chamados WHERE tecnico_id = ? AND status = 'Fechado'");
			preparedStatement.setInt(1, id);
	
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			fechados = resultSet.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(connection,preparedStatement,null);
		}
		return fechados;
		
	}
	public int baixa() {
		int baixo = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT count(prioridade) from chamados where prioridade = 'baixa' AND status ='aberto'");
	
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			baixo = resultSet.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(connection,preparedStatement,null);
		}
		return baixo;
	}
	public int media() {
		int media = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT count(prioridade) from chamados where prioridade = 'media' AND status ='aberto'");
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			media = resultSet.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(connection,preparedStatement,null);
		}
		return media;
	}
	public int alta() {
		int alta = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT count(prioridade) from chamados where prioridade = 'alta' AND status ='aberto'");
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			alta = resultSet.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(connection,preparedStatement,null);
		}
		return alta;
	}
	public int muitoAlta() {
		int muitoAlta = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT count(prioridade) from chamados where prioridade = 'muito alta' AND status ='aberto'");
	
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			muitoAlta = resultSet.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(connection,preparedStatement,null);
		}
		return muitoAlta;
	}
	public int pendentes(int id) {
		int pendentes = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT * FROM chamados WHERE tecnico_id = ? AND status != 'Fechado'");
			preparedStatement.setInt(1, id);
	
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			pendentes = resultSet.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(connection,preparedStatement,null);
		}
		return pendentes;
	}
	public int assumidos(int id) {
		int assumidos = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT count(status) FROM chamados WHERE tecnico_id = ? AND status = 'Em Andamento'");
			preparedStatement.setInt(1, id);
	
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			assumidos = resultSet.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(connection,preparedStatement,null);
		}
		return assumidos;
	}
	public int listarChamadosClientes(int id) {
		int abertos = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT count(usuario_id) from chamados where usuario_id = ? AND status='aberto'");
			preparedStatement.setInt(1, id);
	
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			abertos = resultSet.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(connection,preparedStatement,null);
		}
		return abertos;
	}
	public int listarChamadosFechadosClientes(int id) {
		int fechados = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT count(usuario_id) from chamados where usuario_id = ? AND status='fechado'");
			preparedStatement.setInt(1, id);
	
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			fechados = resultSet.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(connection,preparedStatement,null);
		}
		return fechados;
	}
	public int baixaCliente(int id) {
		int baixa = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT count(prioridade) from chamados where prioridade = 'baixa' AND status ='aberto' AND usuario_id = ? ");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			baixa = resultSet.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(connection,preparedStatement,null);
		}
		return baixa;
	}
	public int mediaCliente(int id) {
		int media = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT count(prioridade) from chamados where prioridade = 'media' AND status ='aberto' AND usuario_id = ? ");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			media = resultSet.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(connection,preparedStatement,null);
		}
		return media;
	}
	public int altaCliente(int id) {
		int alta = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT count(prioridade) from chamados where prioridade = 'alta' AND status ='aberto' AND usuario_id = ? ");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			alta = resultSet.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(connection,preparedStatement,null);
		}
		return alta;
	}
	public int muitoAltaCliente(int id) {
		int muitoAlta = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT count(prioridade) from chamados where prioridade = 'muito alta' AND status ='aberto' AND usuario_id = ? ");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			muitoAlta = resultSet.getInt(1);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}finally {
			fecharConexao(connection,preparedStatement,null);
		}
		return muitoAlta;
	}


}
