package remake.regras;

import java.io.Serializable;

import remake.sistema.CentralDeDados;

public class OrdenadorRegraFavoritado implements OrdenadorRegra<String>, Serializable {

	private static final long serialVersionUID = 1967455555606433090L;
	CentralDeDados data = CentralDeDados.getInstance();
	private final String regra = "PRIMEIRO OS SONS COM MAIS FAVORITOS";

	
	@Override
	public int compare(String arg0, String arg1) {
		return data.getMusica(arg1).getNumeroFavoritos()
				- data.getMusica(arg0).getNumeroFavoritos();
	}

	public String getRegra(){
		return regra;
	}

}
