/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remake.testes;

import java.io.IOException;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import remake.entidades.Usuario;
import remake.sistema.SistemaAPI;

/**
 *
 * @author Tatiana
 */
public class SistemaAPITest {
    
    private SistemaAPI sistema;
    
    
    
    public SistemaAPITest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws ClassNotFoundException, IOException {
        this.sistema = new SistemaAPI();
        
    }
    
    @After
    public void tearDown() {
    }

    
    
    @Test
    public void testaCriaNovoUsuario(){
        
        
        
        try{
            sistema.criaNovoUsuario("gesn", "tararatata", "guilherme", "guilherme1.0emmanuel@gmail.com");
            
        }catch(Exception ex){
            Assert.fail();
        }
        
        
        try{
            sistema.criaNovoUsuario("gbl", "tararatata", "gabriel", "gabriel@gmail.com");
            
        }catch(Exception ex){
            Assert.fail();
        }
        
        
        
        
    }

    @Test
    public void testaLogaUsuario(){
        try{
            sistema.criaNovoUsuario("gesn2", "aaaaa", "guilherme", "guilherme2.0emmanuel@gmail.com");
        }catch(Exception ex){
            Assert.fail();
        }
        
        String sessaoId = null;
        
        try {
            sessaoId = sistema.abrirSessao("gesn2", "aaaaa");
            
        } catch (Exception ex){
            Assert.fail();
        }
        
        Assert.assertNotNull(sessaoId);
        
        
        
    }
    
    
    @Test
    public void testaSeguir(){
        try {
            sistema.criaNovoUsuario("gerv", "1234", "gervasio", "gervasio@gmail.com");
            sistema.criaNovoUsuario("antNY", "1243", "antonio dudu", "antonio@gmail.com");
            String sessaoId = sistema.abrirSessao("gerv", "1234");
            sistema.seguirUsuario(sessaoId, "antNY");
            assertEquals(1, sistema.getFollowing(sessaoId).size());
            assertEquals(0, sistema.getFollowers(sessaoId).size());
            String sessaoId2 = sistema.abrirSessao("antNY", "1243");
            assertEquals(1, sistema.getFollowers(sessaoId2).size());
            assertEquals(0, sistema.getFollowing(sessaoId2).size());
            sistema.criaNovoUsuario("pedr", "1212", "pedro", "pedro@gmail.com");
            String sessaoId3 = sistema.abrirSessao("pedr", "1212");
            sistema.seguirUsuario(sessaoId, "pedr");
            assertEquals(2, sistema.getFollowing(sessaoId).size());
            assertEquals(0, sistema.getFollowers(sessaoId).size());
            assertEquals(0, sistema.getFollowing(sessaoId3).size());
            assertEquals(1, sistema.getFollowers(sessaoId3).size());
            
            
            
        } catch (Exception ex){
            fail();
        }
    }
        
    
    @Test
    public void visualisarSons(){
        try {
            sistema.criaNovoUsuario("joaquina", "jojo", "joaquina", "joaquina@hotmail.com");
            sistema.criaNovoUsuario("josefina", "jojojo", "josefina", "josefina@hotmail.com");
            String sessaoId1 = sistema.abrirSessao("joaquina", "jojo");
            String sessaoId2 = sistema.abrirSessao("josefina", "jojojo");
            sistema.seguirUsuario(sessaoId1, "josefina");
            sistema.postarSom(sessaoId2, "https://www.google.com/minhasMusicas/musica.mp3");
            assertEquals(1, sistema.getMainFeed(sessaoId1).size());
            assertEquals(0, sistema.getMainFeed(sessaoId2).size());
            sistema.postarSom(sessaoId2, "https://www.google.com/minhasMusicas/musica.mp3");
            assertEquals(2, sistema.getMainFeed(sessaoId1).size());
            assertEquals(0, sistema.getMainFeed(sessaoId2).size());
            //agora joaquina posta um som porem josefina nao ve pois nao segue ela
            String musicaPostada3 = sistema.postarSom(sessaoId1, "https://www.google.com/minhasMusicas/musica.mp3");
            assertEquals(2, sistema.getMainFeed(sessaoId1).size());
            assertEquals(0, sistema.getMainFeed(sessaoId2).size());
            sistema.seguirUsuario(sessaoId2, "joaquina");
            
            
            
            sistema.addFavorito(sessaoId1, musicaPostada3);
            assertEquals(1, sistema.getFavoritos(sessaoId1).size());
            assertEquals(0, sistema.getFavoritos(sessaoId2).size());
            
            
            
            sistema.criaLista(sessaoId1, "minhas amigas lindas");
            assertEquals(0, sistema.getSonsEmLista(sessaoId1, "minhas amigas lindas").size());
            sistema.adicionarUsuarioALista(sessaoId1, "minhas amigas lindas", sistema.getUsuarioID("josefina"));
            assertEquals(2, sistema.getSonsEmLista(sessaoId1, "minhas amigas lindas").size());
            
            sistema.criaNovoUsuario("sabrina", "sasa", "sabrina", "sabrina@comida.com.br");
            String sessaoId3 = sistema.abrirSessao("sabrina", "sasa");
            sistema.postarSom(sessaoId3, "http://www.google.com/musiquinha.wav");
            
            
            assertEquals(1, sistema.getFontesDeSonsRecomendadas(sessaoId1).size());
            
            for (Usuario user : sistema.getFollowing(sessaoId1)) {
                assertFalse(sistema.getFontesDeSonsRecomendadas(sessaoId1).contains(user.getID()));
            }
            
            
            
            
            
            
            
        } catch (Exception ex){
            fail();
        }
    }
    
    
    
    
    
    
    
    
    
    
}
