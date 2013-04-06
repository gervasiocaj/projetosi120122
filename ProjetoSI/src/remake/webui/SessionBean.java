package remake.webui;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import remake.excecao.*;
import remake.util.Favorito;

   // or import javax.enterprise.context.SessionScoped;

@ManagedBean(name="sessao") // or @Named("user")
@SessionScoped
public class SessionBean {

    protected static String sessaoID;
    private String musica;
    private int posicaoMusicas;
    private int posicaoSeguidores;
    private int posicaoSigo;
    private int posicaoFavoritos;
    private int posicaoFeed;
    private String seguir;
    private String ordenacao;
    
    
    public SessionBean() {
        Conversador.tentaIniciarRepositorio();
        posicaoMusicas = 0;
        posicaoSeguidores = 0;
        posicaoSigo = 0;
        posicaoFavoritos = 0;
        posicaoFeed = 0;
        ordenacao = "Mais Recentes Primeiro";
        
        
    }
    
    public String getOrdenacao(){
        return ordenacao;
    }
    
    public void setOrdenacao(String ord) {
        this.ordenacao = ord;
    }
    
    
    public String nomePostador(String idMusica) throws LoginException, UsuarioNaoCadastradoException, SomInexistenteException{
        //TODO
        return Conversador.sistema.getUsuario(Conversador.sistema.getMusica(idMusica).getIDCriador()).getNome();
    }
    
    public String linkMusica(String idMusica) throws SomInexistenteException{
        return Conversador.sistema.getMusica(idMusica).getLink();
    }
    
    
    public String getNameUsuarioLogado() throws SessaoIDException, LoginException, UsuarioNaoCadastradoException{
        return Conversador.sistema.getUsuario(sessaoID).getNome();
    }
    
    public String getSessaoID(){
        return sessaoID;
    }
        
    public String deslogar(){
        try {
            Conversador.sistema.encerrarSessao(sessaoID);
            return "index";
        } catch (SessaoIDException ex) {
            return "";
        }
    }

    public String getMusica(){
        return musica;
    }
        
    public void setMusica(String newValue){
        this.musica = newValue;
    }

    public String getSeguir() {
        return seguir;
    }

    public void setSeguir(String newValue) {
        seguir = newValue;
    }
    
    
    public void mudarOrdenacao() throws SessaoIDException, RegraDeComposicaoException{
        if(ordenacao.equals("Mais Recentes Primeiro")){
            Conversador.sistema.setRegraDeComposicao(sessaoID, null, Conversador.sistema.getPrimeiraRegraDeComposicao()); //FIXME 
        }
        if(ordenacao.equals("Mais favoritos Primeiro")){
            Conversador.sistema.setRegraDeComposicao(sessaoID, null, Conversador.sistema.getSegundaRegraDeComposicao());
        }
        else{
            Conversador.sistema.setRegraDeComposicao(sessaoID, null, Conversador.sistema.getTerceiraRegraDeComposicao());
        }
    }
        
    
    public void postarMusica() throws Exception{
        //try{
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Conversador.sistema.postarSom(sessaoID, musica, format.format(new Date()));
            reiniciaCampos();
          /*  
        }catch(Exception ex){
            
        }
        */
            
    }
    
    
    
    
    
    public void seguirUsuario(){
        try {
            Conversador.sistema.seguirUsuario(sessaoID, seguir);
            
        } catch (SessaoIDException ex) {
            
        } catch (LoginException ex) {
                
        }
    }
    
    public void reiniciaCampos(){
        seguir = "";
        limpaMusica();
    }
        
    
    
    public void limpaMusica(){
        musica = "";
    } 
    
    public List<String> getMusicas(){
        List<String> ids = null;
        try {
            int tamanho = Conversador.sistema.getVisaoDosSons(sessaoID).size();
            if ( tamanho < posicaoMusicas + 6){
                ids = Conversador.sistema.getVisaoDosSons(sessaoID).subList(posicaoMusicas, tamanho);
            }
            else{
                ids = Conversador.sistema.getVisaoDosSons(sessaoID).subList(posicaoMusicas, posicaoMusicas + 6);
            }
        } catch (SessaoIDException ex) {
            return new LinkedList<String>();
//        } catch (UsuarioNaoCadastradoException ex) {
//            return new LinkedList<String>();
        }
        return ids;
        /*
        try{
            List<String> mus = new LinkedList<String>();
            for (String id : ids) {
                mus.add(Conversador.sistema.getAtributoSom(id, "link"));
            }
            return mus;
        }catch(Exception ex){
            return new LinkedList<String>();
        }
        */
        
    }
    
    
    
    
    public boolean hasMusicasMaisNovas(){
        return posicaoMusicas != 0;
    }
      
    public boolean hasMusicasMaisAntigas(){
        try {
            return posicaoMusicas + 6 < Conversador.sistema.getVisaoDosSons(sessaoID).size();
        } catch (SessaoIDException ex) {
        
        }
        return false;
    }
      
      
    public boolean hasSeguidoresMaisNovos(){
        return posicaoSeguidores != 0;
        
    }
    
    public boolean hasSeguidoresMaisVelhos(){
        try {
            return posicaoSeguidores + 6 < Conversador.sistema.getListaDeSeguidores(sessaoID).size();
        } catch (Exception ex) {
            return false;
        }
    }
    
    
    
     public boolean hasSeguindoMaisNovos(){
        return posicaoSigo != 0;
        
    }
    
    public boolean hasSeguindoMaisVelhos(){
        try {
            return posicaoSigo + 6 < Conversador.sistema.getListaSeguindo(sessaoID).size();
        } catch (Exception ex) {
            return false;
        }
    }
    
   
    
    public boolean hasFavoritosMaisNovos(){
        return posicaoFavoritos != 0;
        
    }
    
    public boolean hasFavoritosMaisAntigos(){
        try {
            return posicaoFavoritos + 6 < Conversador.sistema.getFavoritos(sessaoID).size();
        } catch (Exception ex) {
            return false;
        }
    }
    
   
    
    
    
     public boolean hasFeedMaisNovas(){
        return posicaoFeed != 0;
        
    }
    
    public boolean hasFeedMaisAntigas(){
        try {
            return posicaoFeed + 6 < Conversador.sistema.getFeedExtra(sessaoID).size();
        } catch (Exception ex) {
            return false;
        }
    }
    
    
    
   public List<String> getChoicesRadioButton(){
       List<String> choices = new LinkedList<String>();
       choices.add("Mais Recentes Primeiro");
       choices.add("Mais favoritos Primeiro");
       choices.add("Primeiro de quem eu ja favoritei");
       return choices;
       
       
       
   } 
    
   public List<String> getSeguidores() throws Exception {
       Set<String> ids = null; 
       List<String> seguidores = new LinkedList<String>();
       //try {
            
           ids = Conversador.sistema.getListaDeSeguidores(sessaoID);
            
           int tamanho = ids.size();
            
           int indice = 0;
           if(tamanho < posicaoSeguidores + 6){
               for (String string : ids) {
                   if(indice >= posicaoSeguidores && indice < tamanho){
                       seguidores.add(string);
                   }
                   indice++;
               }
           }
           else{
               for (String string : ids) {
                   if(indice >= posicaoSeguidores && indice < posicaoSeguidores + 6){
                       seguidores.add(string);
                   }
                   indice++;
               }
           }
           return seguidores;
         /*  
       } catch (Exception ex) {
           return new LinkedList<String>();
       }
       * */
   }
    
        
   
   
    public List<String> getSeguindo() throws Exception {
       Set<String> ids = null; 
       List<String> sigo = new LinkedList<String>();
       //try {
            
           ids = Conversador.sistema.getListaSeguindo(sessaoID);
            
           int tamanho = ids.size();
            
           int indice = 0;
           if(tamanho < posicaoSigo + 6){
               for (String string : ids) {
                   if(indice >= posicaoSigo && indice < tamanho){
                       sigo.add(string);
                   }
                   indice++;
               }
           }
           else{
               for (String string : ids) {
                   if(indice >= posicaoSigo && indice < posicaoSigo + 6){
                       sigo.add(string);
                   }
                   indice++;
               }
           }
           return sigo;
         /*  
       } catch (Exception ex) {
           return new LinkedList<String>();
       }
       * */
   }
    
    
    
    
    
    public void pegaMusicasMaisNovas(){
        posicaoMusicas -= 6;
        
    }
    
    public void pegaMusicasMaisAntigas(){
        posicaoMusicas += 6;
        
    }
    
    
    public void pegaAmigosMaisNovos(){
        posicaoSeguidores -= 6;
        
    }
    
    public void pegaAmigosMaisAntigos(){
        posicaoSeguidores += 6;
        
    }
    
    public void pegaSeguindoMaisNovos(){
        posicaoSigo -= 6;
    }
    
    public void pegaSeguindoMaisAntigos(){
        posicaoSigo += 6;
    }
    
    
    public void pegaFavoritosMaisNovos(){
        posicaoFavoritos -= 6;
    }
    
    public void pegaFavoritosMaisAntigos(){
        posicaoFavoritos += 6;
    }
    
    public void pegaFeedMaisNovas(){
        posicaoFeed -= 6;
    }
    
    public void pegaFeedMaisAntigas(){
        posicaoFeed += 6;
    }
    
    
    
    public void favoritarMusica(String mus) throws Exception{
        Conversador.sistema.addFavorito(sessaoID, mus);
    }
    
      public List<String> getFavoritos() throws Exception {
       List<String> ids = null; 
       List<String> fav = new LinkedList<String>();
       //try {
            
           ids = Conversador.sistema.getFavoritos(sessaoID);
            
           int tamanho = ids.size();
            
           int indice = 0;
           if(tamanho < posicaoFavoritos + 6){
               for (String string : ids) {
                   if(indice >= posicaoFavoritos && indice < tamanho){
                       fav.add(string);
                   }
                   indice++;
               }
           }
           else{
               for (String string : ids) {
                   if(indice >= posicaoFavoritos && indice < posicaoFavoritos + 6){
                       fav.add(string);
                   }
                   indice++;
               }
           }
           return fav;
         /*  
       } catch (Exception ex) {
           return new LinkedList<String>();
       }
       * */
   }
    
    
      
      public List<String> getFeedExtra() throws Exception {
       List<Favorito> ids = null; 
       List<String> feed = new LinkedList<String>();
       //try {
            
           ids = Conversador.sistema.getFeedExtra(sessaoID);
            
           int tamanho = ids.size();
            
           int indice = 0;
           if(tamanho < posicaoFeed + 6){
               for (Favorito fav : ids) {
                   if(indice >= posicaoFeed && indice < tamanho){
                       feed.add(fav.getIdMusica());
                   }
                   indice++;
               }
           }
           else{
               for (Favorito fav : ids) {
                   if(indice >= posicaoFeed && indice < posicaoFeed + 6){
                       feed.add(fav.getIdMusica());
                   }
                   indice++;
               }
           }
           return feed;
         /*  
       } catch (Exception ex) {
           return new LinkedList<String>();
       }
       * */
   }
      
      
      
      
      public String nomeUsuario(String str) throws LoginException, UsuarioNaoCadastradoException{
          
          return Conversador.sistema.getUsuario(str).getNome();
      }
      
      
      
    
}
