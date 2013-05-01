package remake.util;

import java.io.Serializable;

public class Favorito implements Serializable {
	
	private static final long serialVersionUID = -2716564189826559140L;
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
