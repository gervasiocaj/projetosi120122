package br.edu.ufcg.rickroll.webui;

import br.edu.ufcg.rickroll.rickroll.Musica;
import br.edu.ufcg.rickroll.rickroll.Usuario;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

   // or import javax.enterprise.context.SessionScoped;

@ManagedBean(name="sessao") // or @Named("user")
@SessionScoped
public class SessionBean {

    protected static String sessaoID;
    protected static String login;
    protected static String musica;
    protected static String amigoAdd;
    protected static String amigoAcc;
    private int posicaoMusicas;
    private String[] ultimasMusicas;
    private int posicaoAmigos;
    private String[] ultimosAmigos;
    
    public SessionBean() {
        Conversador.tentaIniciarRepositorio();
        posicaoMusicas = 0;
        ultimasMusicas = new String[6];
        posicaoAmigos = 0;
        ultimosAmigos = new String[6];
        preencherEspacoDeMusicas();
        preencherEspacoDeAmigos();
    }
    
    
    private void preencherEspacoDeMusicas(){
        List<Musica> musicas = null;
        try{
            musicas = Conversador.sistema.getTimeLine(sessaoID);
        }catch(Exception ex){
            
        }
        for (int i = 0; i < 6; i++) {
            try{
                ultimasMusicas[i] = musicas.get(i).getLink();
            }catch(Exception ex){
                ultimasMusicas[i] = "";
            }
                
        }
    }
    
    private void preencherEspacoDeAmigos(){
        Set<Usuario> amigos = null;
        try{
            amigos = Conversador.sistema.getListaAmigos(sessaoID);
        }catch(Exception ex){
        	
        }
        Iterator<Usuario> it = null;
        try{
            it = amigos.iterator();
        }catch(Exception ex){
            
        }
        if(it != null){    
            int posAmigos = 0;
            while(it.hasNext()){
                if(posAmigos == posicaoAmigos){
                    for (int i = 0; i < 6; i++) {
                        if(it.hasNext()){
                            ultimosAmigos[i] = it.next().getNome();
                        }
                        else{
                            ultimosAmigos[i] = "";
                        }
                    }
                    break;
                }
                posAmigos++;
            }
        }
    }
    
    
    public String getNameUsuarioLogado(){
        return Conversador.getNameUsuarioLogado(login);
    }
    
    public String getSessaoID(){
        return sessaoID;
    }
        
    public String deslogar(){
        Conversador.sistema.encerrarSessao(sessaoID);
        return "index";
    }

    public String getMusica(){
        return musica;
    }
        
    public void setMusica(String newValue){
        this.musica = newValue;
    }

    public String getAmigoAdd() {
        return amigoAdd;
    }

    public void setAmigoAdd(String newValue) {
        amigoAdd = newValue;
    }
    
    public String getAmigoAcc() {
        try{
            amigoAcc = Conversador.sistema.getListaDeSolicitacaoDeAmizade(sessaoID).get(0);
        }catch(Exception ex){
            
        }
        return amigoAcc;
    }
    
    public void setAmidoAcc(String newValue) {
        amigoAcc = newValue;
    }
    
    
    public void postarMusica() throws Exception{
        try{
            Conversador.sistema.postarSom(sessaoID, musica, (GregorianCalendar)GregorianCalendar.getInstance());
            reiniciaCampos();
            preencherEspacoDeMusicas();
        }catch(Exception ex){
            
        }
            
    }
    
    
    public void adicionaAmigo(){
        Conversador.sistema.enviaSolicitacaoDeAmizade(sessaoID, amigoAcc);
        reiniciaCampos();
    }
    
    public void aceitaAmigo(){
        Conversador.sistema.aceitaSolicitacaoDeAmizade(sessaoID, amigoAcc);
        reiniciaCampos();
    }
    
    public void reiniciaCampos(){
        amigoAdd = "";
        limpaMusica();
    }
        
    
    
    public void limpaMusica(){
        musica = "";
    } 
    
    public List<Musica> getMusicas(){
        if (Conversador.sistema.getTimeLine(sessaoID).size()< 6)
            return Conversador.sistema.getTimeLine(sessaoID);
        return Conversador.sistema.getTimeLine(sessaoID).subList(posicaoMusicas, posicaoMusicas + 6);
    }
    
    public String getMusica0() {
        return ultimasMusicas[0];    
    }
    
    public String getMusica1(){
        return ultimasMusicas[1];
    }
    
    public String getMusica2(){
        return ultimasMusicas[2];
    }
      
      
      
    public String getMusica3(){
        return ultimasMusicas[3];
        
    }
      
    public String getMusica4(){
        return ultimasMusicas[4];    
    }
      
      
    public String getMusica5(){
        return ultimasMusicas[5];
    }
    
    
    public boolean hasMusicasMaisNovas(){
        return false;
    }
      
    public boolean hasMusicasMaisAntigas(){
        return false;
    }
      
      
    public boolean hasAmigosMaisNovos(){
        return false;
    }
    
    public boolean hasAmigosMaisVelhos(){
        return false;
    }

    
    
    
    
        
    public String getAmigo0(){
        return ultimosAmigos[0];
        
            
    }
    
    public String getAmigo1(){
        return ultimosAmigos[1];
            
    }
    
    public String getAmigo2(){
        return ultimosAmigos[2];
    }
      
      
      
    public String getAmigo3(){
        return ultimosAmigos[3];
            
        
        
    }
      
    public String getAmigo4(){
        return ultimosAmigos[4];
            
    }
      
      
    public String getAmigo5(){
        return ultimosAmigos[5];
            
    }
    
    
    
    
    
    public void pegaMusicasMaisNovas(){
        posicaoMusicas -= 6;
        preencherEspacoDeMusicas();
    }
    
    public void pegaMusicasMaisAntigas(){
        posicaoMusicas += 6;
        preencherEspacoDeMusicas();
    }
    
    
    public void pegaAmigosMaisNovos(){
        posicaoAmigos -= 6;
        preencherEspacoDeAmigos();
    }
    
    public void pegaAmigosMaisAntigos(){
        posicaoAmigos += 6;
        preencherEspacoDeAmigos();
    }
    
    
    /*
   public String getLogin() { 
       return login; 
   }
   public void setLogin(String newValue) { 
       login = newValue; 
   }

   public static String login() { 
       return login; 
   }
   public static void login(String newValue) { 
       login = newValue; 
   }
   
   public String getPassword() {
       return password; 
   }
   public void setPassword(String newValue) {
       password = newValue; 
   }

   public static String password() {
       return password; 
   }
   public static void password(String newValue) {
       password = newValue; 
   }
   
   public String getName() {
       return name; 
   }
   public void setName(String newValue) {
       name = newValue; 
   }

   public static String name() {
       return name; 
   }
   public static void name(String newValue) {
       name = newValue; 
   }
   
   public String getConfirmPassword() {
       return confirmPassword; 
   }
   public void setConfirmPassword(String newValue) {
       confirmPassword = newValue; 
   }

   public static String confirmPassword() {
       return confirmPassword; 
   }
   public static void confirmPassword(String newValue) {
       confirmPassword = newValue; 
   }

   
   public String getEmail() {
       return email; 
   }
   public void setEmail(String newValue) {
       email = newValue; 
   }

   public static String email() {
       return email; 
   }
   public static void email(String newValue) {
   
       email = newValue;
   }
   

    */
    
    
}
