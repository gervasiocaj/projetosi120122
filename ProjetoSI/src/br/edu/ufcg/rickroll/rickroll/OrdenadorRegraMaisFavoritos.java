package br.edu.ufcg.rickroll.rickroll;

import java.util.Comparator;

public class OrdenadorRegraMaisFavoritos implements Comparator<String>{
	
	DataStorage data;
	String idUser;
	
	public OrdenadorRegraMaisFavoritos(DataStorage data, String idUser) {
		this.data = data;
		this.idUser = idUser;
	}

	@Override
	public int compare(String arg0, String arg1) {
		if(data.getUser(idUser).getNumeroDeFavoritos(data.getMusica(arg1).getIDCriador())
				== data.getUser(idUser).getNumeroDeFavoritos(data.getMusica(arg0).getIDCriador())){
			// Se o numero de favoritos for igual, compara pela ordem em que comecou a ser seguido
			return data.getUser(idUser).getOrdemSeguidor(data.getMusica(arg1).getIDCriador()) 
			- data.getUser(idUser).getOrdemSeguidor(data.getMusica(arg0).getIDCriador());
		}
		return data.getUser(idUser).getNumeroDeFavoritos(data.getMusica(arg1).getIDCriador())
				- data.getUser(idUser).getNumeroDeFavoritos(data.getMusica(arg0).getIDCriador());
	}

}
