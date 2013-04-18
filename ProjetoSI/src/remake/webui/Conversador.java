/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remake.webui;

import java.io.Serializable;
import remake.sistema.*;

/**
 *
 * @author Guilherme
 */
public class Conversador implements Serializable{
    
	private static final long serialVersionUID = -8772607007262223094L;
	protected static SistemaAPI sistema;
    
    
    public static void tentaIniciarRepositorio(){
        if(sistema == null){
            sistema = new SistemaAPI();
            //falta fazer a parte da persistencia
        }
        
    }
    
    public static String getNameUsuarioLogado(String login){
        try{
            return sistema.getUsuarioByLogin(login).getNome();
        }catch(Exception ex){
            return "";
        }
    }
    
}
