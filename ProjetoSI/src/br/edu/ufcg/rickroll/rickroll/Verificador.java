package br.edu.ufcg.rickroll.rickroll;

public class Verificador {

	public static boolean verificaStringValida( String str ) {
		if (str == null)
			return false;
		return !str.equals("");
	}
	
}