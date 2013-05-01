package remake.regras;

import java.io.Serializable;
import java.util.Comparator;

import remake.sistema.CentralDeDados;

public class OrdenadorRegraTempo implements Comparator<String>, Serializable {

	private static final long serialVersionUID = 5537621344955613230L;
	CentralDeDados data = CentralDeDados.getInstance();

	@Override
	public int compare(String id1, String id2) {
		return data.getMusica(id2).getDataDeCriacao().compareTo(data.getMusica(id1).getDataDeCriacao());
	}

}
