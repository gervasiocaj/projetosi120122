/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remake.webui;

import remake.sistema.*;

/**
 *
 * @author Guilherme
 */
public class Conversador {
    
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
