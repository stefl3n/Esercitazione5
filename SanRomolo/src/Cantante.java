//LENTINI STEFANO 0000826388

public class Cantante {
	String nome;
	String Categoria;
	int nVoti;
	String fileAudio;
	
	public Cantante(){
		nome = "L";
		Categoria = "L";
		nVoti = -1;
		fileAudio = "L";
	}
	
	public int addVote(){
		return this.nVoti++;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return Categoria;
	}

	public void setCategoria(String categoria) {
		Categoria = categoria;
	}

	public int getnVoti() {
		return nVoti;
	}

	public void setnVoti(int nVoti) {
		this.nVoti = nVoti;
	}

	public String getFileAudio() {
		return fileAudio;
	}

	public void setFileAudio(String fileAudio) {
		this.fileAudio = fileAudio;
	}
	
	
}
