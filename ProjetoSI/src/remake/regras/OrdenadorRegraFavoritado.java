package remake.regras;

import java.util.Comparator;

import remake.sistema.CentralDeDados;

public class OrdenadorRegraFavoritado implements OrdenadorRegra<String> {

	CentralDeDados data = CentralDeDados.getInstance();

	@Override
	public int compare(String arg0, String arg1) {
		return data.getMusica(arg1).getNumeroFavoritos()
				- data.getMusica(arg0).getNumeroFavoritos();
	}

}
