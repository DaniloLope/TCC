
package Model;

public class tecnicoModel extends usuariosModel {

	private String setor, cargo;
	private String tipo;
	
	public tecnicoModel(String nome, String login, String senha, String setor, String cargo, String tipo) {
		super(nome, login, senha);
		
		this.setor = setor;
		this.cargo = cargo;
		this.tipo = tipo;
		
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
}
