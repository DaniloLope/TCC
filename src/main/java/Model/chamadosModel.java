package Model;

import java.sql.Date;

public class chamadosModel {
	private int usuario_id,tecnico_id,chamado_id;
	private String descricao,prioridade,status;
	private Date data_abertura;
	private Date data_fechamento;

	public chamadosModel(int chamado_id, int usuario_id, int tecnico_id, String descricao, String prioridade, String status,
			Date data_abertura, Date data_fechamento) {
		super();
		
		this.chamado_id = chamado_id;
		this.usuario_id = usuario_id;
		this.tecnico_id = tecnico_id;
		this.descricao = descricao;
		this.prioridade = prioridade;
		this.status = status;
		this.data_abertura = data_abertura;
		this.data_fechamento = data_fechamento;
	}
	public int getChamado_id() {
		return chamado_id;
	}
	public void setChamado_id(int chamado_id) {
		this.chamado_id = chamado_id;
	}
	public int getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(int usuario_id) {
		this.usuario_id = usuario_id;
	}

	public int getTecnico_id() {
		return tecnico_id;
	}

	public void setTecnico_id(int tecnico_id) {
		this.tecnico_id = tecnico_id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getData_abertura() {
		return data_abertura;
	}

	public void setData_abertura(Date data_abertura) {
		this.data_abertura = data_abertura;
	}

	public Date getData_fechamento() {
		return data_fechamento;
	}

	public void setData_fechamento(Date data_fechamento) {
		this.data_fechamento = data_fechamento;
	}
	
	
	public String getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
