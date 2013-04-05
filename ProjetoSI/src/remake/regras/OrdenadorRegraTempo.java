package remake.regras;

import java.util.Comparator;

import remake.sistema.CentralDeDados;

public class OrdenadorRegraTempo implements Comparator<String> {

	CentralDeDados data = CentralDeDados.getInstance();

	@Override
	public int compare(String id1, String id2) {
		return data.getMusica(id2).getDataDeCriacao().compareTo(data.getMusica(id1).getDataDeCriacao());
	}

}
