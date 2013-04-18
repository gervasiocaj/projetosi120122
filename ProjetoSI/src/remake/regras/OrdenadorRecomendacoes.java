package remake.regras;

import java.util.Comparator;
import remake.sistema.CentralDeDados;


public class OrdenadorRecomendacoes implements Comparator<String> {
	
	
	private CentralDeDados central;
	private String usuario;

	public OrdenadorRecomendacoes(CentralDeDados central, String usuario) {
		this.central = central;
		this.usuario = usuario;
	}

	@Override
	public int compare(String arg0, String arg1) {
		int saida = -((getNumFavoritosEmComum(arg0) + getNumFontesEmComum(arg0)) - (getNumFavoritosEmComum(arg1) + getNumFontesEmComum(arg1)));
		if(saida == 0){
			saida = -(central.getUser(arg0).vezesFavoritado() - central.getUser(arg1).vezesFavoritado());
		}
		return saida;
	}

	private int getNumFavoritosEmComum(String idUsuario){
		int contador = 0;
		for (String favorito : central.getUser(usuario).getFavoritos()) {
			if(central.getUser(idUsuario).getFavoritos().contains(favorito)) contador++;
		}
		return contador;
	}
	
	private int getNumFontesEmComum(String idUsuario){
		int contador = 0;
		for (String fonte : central.getUser(usuario).getSeguindo()) {
			if(central.getUser(idUsuario).getSeguindo().contains(fonte)) contador++;
		}
		return contador;
	}
	
}
