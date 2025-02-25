package Model;

public class avaliacoesModel {

		private int id_usuario;
		private String nome;
		private String nota;
		private String utilidade;
		private String comentario;
		
		public avaliacoesModel(int id_usuario, String nome, String nota, String utilidade, String comentario) {
			super();
			this.id_usuario = id_usuario;
			this.nome = nome;
			this.nota = nota;
			this.utilidade = utilidade;
			this.comentario = comentario;
		}

		public int getId_usuario() {
			return id_usuario;
		}

		public void setId_usuario(int id_usuario) {
			this.id_usuario = id_usuario;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getNota() {
			return nota;
		}

		public void setNota(String nota) {
			this.nota = nota;
		}

		public String getUtilidade() {
			return utilidade;
		}

		public void setUtilidade(String utilidade) {
			this.utilidade = utilidade;
		}

		public String getComentario() {
			return comentario;
		}

		public void setComentario(String comentario) {
			this.comentario = comentario;
		}
		
		
}
