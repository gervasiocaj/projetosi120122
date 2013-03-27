package remake.regras;

import remake.sistema.CentralDeDados;

public class OrdenadorRegraDefaut implements OrdenadorRegra<String> {
	
	CentralDeDados data = CentralDeDados.getInstance();

	@Override
	public int compare(String id1, String id2) {
		return data.getMusica(id2).getDataDeCriacao().compareTo(data.getMusica(id1).getDataDeCriacao());
	}

}
