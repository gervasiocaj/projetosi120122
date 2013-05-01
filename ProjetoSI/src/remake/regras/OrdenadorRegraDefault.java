package remake.regras;

import java.io.Serializable;

import remake.sistema.CentralDeDados;

public class OrdenadorRegraDefault implements OrdenadorRegra<String>, Serializable{
	
	private static final long serialVersionUID = -7548317205193553547L;
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
