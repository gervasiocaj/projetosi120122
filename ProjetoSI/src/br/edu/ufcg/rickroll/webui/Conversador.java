/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufcg.rickroll.webui;

import br.edu.ufcg.rickroll.rickroll.RickRoll;

/**
 *
 * @author Guilherme
 */
public class Conversador {
    
    protected static RickRoll sistema;
    
    
    public static void tentaIniciarRepositorio(){
        if(sistema == null){
            sistema = new RickRoll();
            //falta fazer a parte da persistencia
        }
        
    }
    
    public static String getNameUsuarioLogado(String login){
        try{
            return sistema.getAtributoUsuario(login, "nome");
        }catch(Exception ex){
            return "";
        }
    }
    
}