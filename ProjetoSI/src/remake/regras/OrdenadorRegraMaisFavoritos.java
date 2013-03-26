package remake.regras;

import java.util.Comparator;

import remake.sistema.CentralDeDados;

public class OrdenadorRegraMaisFavoritos implements OrdenadorRegra<String>{
	
	CentralDeDados data = CentralDeDados.getInstance();
	String usuarioID;
	
	public OrdenadorRegraMaisFavoritos(String usuarioID) {
		this.usuarioID = usuarioID;
	}

	@Override
	public int compare(String arg0, String arg1) {
		if(data.getUser(usuarioID).getNumeroDeFavoritos(data.getMusica(arg1).getIDCriador())
				== data.getUser(usuarioID).getNumeroDeFavoritos(data.getMusica(arg0).getIDCriador())){
			// Se o numero de favoritos for igual, compara pela ordem em que comecou a ser seguido
			return data.getUser(usuarioID).getOrdemSeguidor(data.getMusica(arg1).getIDCriador()) 
			- data.getUser(usuarioID).getOrdemSeguidor(data.getMusica(arg0).getIDCriador());
		}
		return data.getUser(usuarioID).getNumeroDeFavoritos(data.getMusica(arg1).getIDCriador())
				- data.getUser(usuarioID).getNumeroDeFavoritos(data.getMusica(arg0).getIDCriador());
	}

}
