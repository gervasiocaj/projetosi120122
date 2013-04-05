package remake.regras;

import remake.sistema.CentralDeDados;

public class OrdenadorRegraDefaut implements OrdenadorRegra<String> {
	
	CentralDeDados data = CentralDeDados.getInstance();
	private final String regra = "PRIMEIRO OS SONS POSTADOS MAIS RECENTEMENTE PELAS FONTES DE SONS";

	@Override
	public int compare(String id1, String id2) {
		return 0;
	}
	
	public String getRegra(){
		return regra;
	}

}
