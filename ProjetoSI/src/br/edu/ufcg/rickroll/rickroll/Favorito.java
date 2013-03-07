package br.edu.ufcg.rickroll.rickroll;

public class Favorito {
	
	private String idUsuario;
	private String idMusica;
	
	public Favorito(String idMusica, String idUsuario) {
		
		this.idUsuario = idUsuario;
		this.idMusica = idMusica;
		
	}
	
	public String getIdUsuario(){
		return idUsuario;
	}
	
	public String getIdMusica(){
		return idMusica;
	}

}
