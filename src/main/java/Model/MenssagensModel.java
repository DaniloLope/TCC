package Model;

public class MenssagensModel {
	
	private String menssagem;
	private int id_chamado;
	private String tipo;

	public MenssagensModel(String menssagem, int id_chamado, String tipo) {
		super();
		this.menssagem = menssagem;
		this.id_chamado = id_chamado;
		this.tipo = tipo;
	}

	public String getMenssagem() {
		return menssagem;
	}

	public void setMenssagem(String menssagem) {
		this.menssagem = menssagem;
	}

	public int getId_chamado() {
		return id_chamado;
	}

	public void setId_chamado(int id_chamado) {
		this.id_chamado = id_chamado;
	}
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
