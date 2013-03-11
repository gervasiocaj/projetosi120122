package br.edu.ufcg.rickroll.rickroll;

import java.util.Comparator;

public class OrdenadorRegraFavoritado implements Comparator<String>{
	
	DataStorage data;
	
	public OrdenadorRegraFavoritado(DataStorage data) {
		this.data = data;
	}

	@Override
	public int compare(String arg0, String arg1) {
		return data.getMusica(arg1).getNumeroFavoritos() - data.getMusica(arg0).getNumeroFavoritos();
	}

}
