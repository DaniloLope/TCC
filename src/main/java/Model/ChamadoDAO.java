package Model;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public class ChamadoDAO {
	private DataSource dataSource;
	
	public ChamadoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public boolean registrarChamado(int usuario_id, String prioridade,String status, String descricao, java.util.Date data_abertura) {
		int resultado;

       
        Connection connection = null;
		PreparedStatement preparedStatement = null;
        try {
        	connection = dataSource.getConnection();
        	preparedStatement = connection.prepareStatement("INSERT INTO chamados (usuario_id, prioridade, status, descricao, data_abertura) VALUES (?, ?, ?, ?, ?)");
        	preparedStatement.setInt(1, usuario_id);
        	preparedStatement.setString(2, prioridade);
        	preparedStatement.setString(3, status);
        	preparedStatement.setString(4, descricao);
        	
        	java.sql.Date dataSQL = new java.sql.Date(data_abertura.getTime());
        	
            preparedStatement.setDate(5, dataSQL);
              
			resultado = preparedStatement.executeUpdate();
        }catch (SQLException e) {
    	    e.printStackTrace();
    	    System.err.println("Erro ao inserir chamado: " + e.getMessage());
    	    resultado = 0;
    	}finally {
    		fecharConexao(connection, preparedStatement, null);
    	}
    	
    	return resultado == 1;
    }
	
	public ArrayList<chamadosModel> listaChamadosCliente(int clienteID){
		ArrayList<chamadosModel> chamados = new ArrayList<chamadosModel>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			 
			preparedStatement = connection.prepareStatement("SELECT * FROM chamados WHERE usuario_id = ?");
			preparedStatement.setInt(1, clienteID);

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				
				int chamado_id = resultSet.getInt("id");
				int usuario_id = resultSet.getInt("usuario_id");
				int tecnico_id = resultSet.getInt("tecnico_id");
				String prioridade = resultSet.getString("prioridade");
				String status = resultSet.getString("status");
				String descricao = resultSet.getString("descricao");
				Date data_abertura = resultSet.getDate("data_abertura");
				Date data_fechamento= resultSet.getDate("data_fechamento");
				
				chamados.add(new chamadosModel(chamado_id, usuario_id, tecnico_id, descricao, prioridade, status, data_abertura, data_fechamento));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			fecharConexao(connection, preparedStatement, resultSet);
		}
		
		return chamados;
	}
	
	public ArrayList<chamadosModel> listarChamados() {
		
		ArrayList<chamadosModel> lista= new ArrayList<>();
		Connection conexao = null;
		PreparedStatement statement = null;
		ResultSet resultado = null;

		try {
			conexao = dataSource.getConnection();
			String sql = "SELECT `id`, `usuario_id`, `tecnico_id`, `prioridade`, `status`, `descricao`, `data_abertura`, `data_fechamento` FROM `chamados` where status = 'aberto' ORDER BY FIELD(`prioridade`, 'muito alta', 'alta', 'média', 'baixa');";
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
	
	public void assumirChamados(int idChamado, int tecnicoId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int resultado;
		
	    try{
	    	connection = dataSource.getConnection();
	    	preparedStatement = connection.prepareStatement("UPDATE chamados SET tecnico_id = ?, status = 'Em Andamento' WHERE id = ?");

	    	preparedStatement.setInt(1, tecnicoId);
	    	preparedStatement.setInt(2, idChamado);
	    	preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }finally {
			fecharConexao(connection, preparedStatement, null);
		}
	}

	public ArrayList<chamadosModel> buscarChamadosAtribuidos(int tecnicoId) {
		ArrayList<chamadosModel> chamados = new ArrayList<chamadosModel>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT * FROM chamados WHERE tecnico_id = ? AND status != 'Fechado'");
			preparedStatement.setInt(1, tecnicoId);
	
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				
				int chamado_id = resultSet.getInt("id");
				int usuario_id = resultSet.getInt("usuario_id");
				int tecnico_id = resultSet.getInt("tecnico_id");
				String prioridade = resultSet.getString("prioridade");
				String status = resultSet.getString("status");
				String descricao = resultSet.getString("descricao");
				Date data_abertura = resultSet.getDate("data_abertura");
				Date data_fechamento= resultSet.getDate("data_fechamento");
				
				chamados.add(new chamadosModel(chamado_id, usuario_id, tecnico_id, descricao, prioridade, status, data_abertura, data_fechamento));
			}
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
			fecharConexao(connection, preparedStatement, null);
		}
	    return chamados;
	}

	public void atualizarStatusChamado(int id, String novoStatus) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE chamados SET status = ? WHERE id = ?";
		
		if(novoStatus.equals("fechado")) {
			sql = "UPDATE chamados SET status = ?, data_fechamento = NOW() WHERE id = ?";
		}
	    try {
	    	connection = dataSource.getConnection();
	    	preparedStatement = connection.prepareStatement(sql);
	    	
	    	preparedStatement.setString(1, novoStatus);
	    	preparedStatement.setInt(2, id);

	    	preparedStatement.executeUpdate();
	    	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
			fecharConexao(connection, preparedStatement, null);
		}
	}
	
	public ArrayList<chamadosModel> buscarChamadosResolvidosTec(int tecnicoId) {
		ArrayList<chamadosModel> chamados = new ArrayList<chamadosModel>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT * FROM chamados WHERE tecnico_id = ? AND status = 'Fechado'");
			preparedStatement.setInt(1, tecnicoId);
	
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				
				
				int chamado_id = resultSet.getInt("id");
				int usuario_id = resultSet.getInt("usuario_id");
				int tecnico_id = resultSet.getInt("tecnico_id");
				String prioridade = resultSet.getString("prioridade");
				String status = resultSet.getString("status");
				String descricao = resultSet.getString("descricao");
				Date data_abertura = resultSet.getDate("data_abertura");
				Date data_fechamento= resultSet.getDate("data_fechamento");
				
				chamados.add(new chamadosModel(chamado_id, usuario_id, tecnico_id, descricao, prioridade, status, data_abertura, data_fechamento));
			}
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
			fecharConexao(connection, preparedStatement, null);
		}
	    return chamados;
	}
	
	public boolean comentar(int id, String msg) {
		
		int resultado;   
        Connection connection = null;
		PreparedStatement preparedStatement = null;
        try {
        	connection = dataSource.getConnection();
        	preparedStatement = connection.prepareStatement("INSERT INTO `comentarios`(`id_chamado`, `tipo`, `menssagem`) VALUES ( ?, 'cliente', ?)");
        	preparedStatement.setInt(1, id);
        	preparedStatement.setString(2, msg);
              
			resultado = preparedStatement.executeUpdate();
        }catch (SQLException e) {
    	    e.printStackTrace();
    	    System.err.println("Erro ao inserir comentário: " + e.getMessage());
    	    resultado = 0;
    	}finally {
    		fecharConexao(connection, preparedStatement, null);
    	}
    	
    	return resultado == 1;
		
	}

	public ArrayList<MenssagensModel> recuperarMenssagens(int id) {
		ArrayList<MenssagensModel> menssagens = new ArrayList<MenssagensModel>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT `id_chamado`, `tipo`, `menssagem` FROM `comentarios` WHERE id_chamado = ? AND tipo = 'cliente'");
			preparedStatement.setInt(1, id);			
			resultSet = preparedStatement.executeQuery();
				
			while(resultSet.next()) {
				
				String menssagem = resultSet.getString("menssagem");
				String tipo = resultSet.getString("tipo");
				int id_chamado = resultSet.getInt("id_chamado");
				
				menssagens.add(new MenssagensModel(menssagem, id_chamado, tipo));
			}
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
			fecharConexao(connection, preparedStatement, null);
		}
	    return menssagens;
	}

	public ArrayList<MenssagensModel> recuperarMenssagensTecnico(int id) {
		ArrayList<MenssagensModel> menssagensTecnico = new ArrayList<MenssagensModel>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;		 
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
	
			preparedStatement = connection.prepareStatement("SELECT `id_chamado`, `tipo`, `menssagem` FROM `comentarios` WHERE id_chamado = ? AND tipo = 'tecnico'");
			preparedStatement.setInt(1, id);			
			resultSet = preparedStatement.executeQuery();
				
			while(resultSet.next()) {
				
				String menssagem = resultSet.getString("menssagem");
				String tipo = resultSet.getString("tipo");
				int id_chamado = resultSet.getInt("id_chamado");
				
				menssagensTecnico.add(new MenssagensModel(menssagem, id_chamado, tipo));
			}
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }finally {
			fecharConexao(connection, preparedStatement, null);
		}
	    return menssagensTecnico;
	}

	public boolean comentarTecnico(int id, String comentario) {
		int resultado;   
        Connection connection = null;
		PreparedStatement preparedStatement = null;
        try {
        	connection = dataSource.getConnection();
        	preparedStatement = connection.prepareStatement("INSERT INTO `comentarios`(`id_chamado`, `tipo`, `menssagem`) VALUES ( ?, 'tecnico', ?)");
        	preparedStatement.setInt(1, id);
        	preparedStatement.setString(2, comentario);
              
			resultado = preparedStatement.executeUpdate();
        }catch (SQLException e) {
    	    e.printStackTrace();
    	    System.err.println("Erro ao inserir comentário: " + e.getMessage());
    	    resultado = 0;
    	}finally {
    		fecharConexao(connection, preparedStatement, null);
    	}
    	
    	return resultado == 1;
	}
	public double calcularTempoMedioSistema() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int resultado;
		String sql = "SELECT AVG(TIMESTAMPDIFF(HOUR, data_abertura, data_fechamento)) AS tempo_medio_horas "
				+ "FROM chamados WHERE status = 'Resolvido' AND data_fechamento IS NOT NULL";

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				return rs.getDouble("tempo_medio_horas");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao(connection, preparedStatement, null);
		}
		return 0.0;
	}

	public Map<String, Integer> calcularProblemasMaisFrequentes() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "SELECT descricao, COUNT(*) AS frequencia FROM chamados GROUP BY descricao ORDER BY frequencia DESC LIMIT 10";
		Map<String, Integer> resultados = new HashMap<>();

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				resultados.put(rs.getString("descricao"), rs.getInt("frequencia"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			fecharConexao(connection, preparedStatement, null);
		}
		return resultados;
	}

	public Map<String, Integer> calcularChamadosPorStatus() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "SELECT status, COUNT(*) AS quantidade FROM chamados GROUP BY status";
		Map<String, Integer> resultados = new HashMap<>();

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				resultados.put(rs.getString("status"), rs.getInt("quantidade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			fecharConexao(connection, preparedStatement, null);
		}
		return resultados;
	}
	
	public Map<String, Integer> calcularChamadosPorPrioridade() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "SELECT prioridade, COUNT(*) AS quantidade FROM chamados GROUP BY prioridade";
		Map<String, Integer> resultados = new HashMap<>();

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				resultados.put(rs.getString("prioridade"), rs.getInt("quantidade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			fecharConexao(connection, preparedStatement, null);
		}
		return resultados;
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
}
