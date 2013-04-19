package remake.regras;

import remake.entidades.Usuario;
import remake.sistema.CentralDeDados;

public class OrdenadorRegraMaisFavoritos implements OrdenadorRegra<String>{
	
	CentralDeDados data = CentralDeDados.getInstance();
	String usuarioID;
	private final String regra = "PRIMEIRO SONS DE FONTES DAS QUAIS FAVORITEI SONS NO PASSADO";
	
	public OrdenadorRegraMaisFavoritos() {
		usuarioID = "default";
	}
	
	public OrdenadorRegraMaisFavoritos(String usuarioID) {
		this.usuarioID = usuarioID;
	}

	@Override
	public int compare(String mus1, String mus2) {
		Usuario userAtual = data.getUser(usuarioID);
		if(userAtual.getNumeroDeFavoritos(data.getMusica(mus2).getIDCriador())
				== userAtual.getNumeroDeFavoritos(data.getMusica(mus1).getIDCriador())){
			// Se o numero de favoritos for igual, compara pela ordem em que comecou a ser seguido
			return userAtual.getOrdemSeguidor(data.getMusica(mus2).getIDCriador()) 
			- userAtual.getOrdemSeguidor(data.getMusica(mus1).getIDCriador());
		}
		return userAtual.getNumeroDeFavoritos(data.getMusica(mus2).getIDCriador())
				- userAtual.getNumeroDeFavoritos(data.getMusica(mus1).getIDCriador());
	}
	
	public String getRegra(){
		return regra;
	}


}
