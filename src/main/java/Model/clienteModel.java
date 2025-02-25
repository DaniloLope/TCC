package Model;

public class clienteModel extends usuariosModel{

	private int id;
	private String setor;
	private String cargo;
	private String tipo;
	
	public clienteModel( int id, String nome, String login, String senha, String setor, String cargo, String tipo) {
		super(nome, login, senha);
		this.id = id;
		this.setor = setor;
		this.cargo = cargo;
		this.tipo = tipo;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
