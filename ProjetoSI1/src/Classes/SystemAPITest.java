package Classes;
import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import Exceptions.AtributoInvalidoException;
import Exceptions.LinkInvalidoException;
import Exceptions.LoginException;
import Exceptions.PostaSomException;
import Exceptions.SessaoIDException;
import Exceptions.UsuarioNaoCadastradoException;


public class SystemAPITest {

	SystemAPI api;
	
	@Before
	public void init(){
		 api = new SystemAPI();
	}
	
	// TODO: TESTES NAO FUNCIONAIS!
	
	@Test
	public void criaNovoUsuário() throws UsuarioNaoCadastradoException, LoginException {
		
		String idUser;
		try {
			idUser = api.procuraUsuario( "login" );
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		} catch (UsuarioNaoCadastradoException e) {
			Assert.assertEquals( "Usuário inexistente", e.getMessage() );
		} catch (Exception e){
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		}
		
		// TODO: Decidir de userID vai ser mesmo o login do usr
		api.criaNovoUsuario( "login", "senha", "nome", "email" );
		idUser = api.procuraUsuario( "login" );
		Assert.assertEquals( "login", idUser );
		
	}
	
	@Test
	public void getAtributoUsuario() throws UsuarioNaoCadastradoException, AtributoInvalidoException {
		
		try {
			api.getAtributoUsuario( "login", "" );
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		} catch (UsuarioNaoCadastradoException e) {
			Assert.assertEquals( "Usuário inexistente", e.getMessage() );
		} catch (Exception e){
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		}
		
		api.criaNovoUsuario( "login", "senha", "nome", "email" );

		Assert.assertEquals( "nome", api.getAtributoUsuario( "login", "nome" ) );
		Assert.assertEquals( "email", api.getAtributoUsuario( "login", "email" ) );
		
	}
	
	@Test
	public void abrirSessao() throws LoginException, UsuarioNaoCadastradoException{
		
		try {
			api.abrirSessao("login", "senha");
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		} catch ( UsuarioNaoCadastradoException e) {
			Assert.assertEquals( "Usuário inexistente", e.getMessage() );
		} catch (Exception e) {
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		}
		
		api.criaNovoUsuario( "login", "senha", "nome", "email" );
		String sessaoID = api.abrirSessao("login", "senha");
		
		// TODO: Decidir de sessaoID vai ser mesmo o login do usr logado
		Assert.assertEquals( "login", sessaoID );
		
	}

	@Test
	public void getPerfilMusical() throws UsuarioNaoCadastradoException{
		
		try {
			api.getPerfilMusical("login");
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		} catch ( UsuarioNaoCadastradoException e) {
			Assert.assertEquals( "Usuário inexistente", e.getMessage() );
		} catch (Exception e) {
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		}
		
		api.criaNovoUsuario( "login", "senha", "nome", "email" );
		
		List<Musica> list = new LinkedList<Musica>();
		Assert.assertEquals(list, api.getPerfilMusical("login"));
	}
	
	@Test
	public void postarSom() throws UsuarioNaoCadastradoException, LoginException, SessaoIDException, PostaSomException, LinkInvalidoException {

		try {
			api.postarSom( "idSessao" , "link", new GregorianCalendar() );
			Assert.fail( "Deveria lancar uma SessaoIDException" );
		} catch ( SessaoIDException e) {
			Assert.assertEquals( "SessaoID invalido!", e.getMessage() );
		} catch (Exception e) {
			Assert.fail( "Deveria lancar uma SessaoIDException" );
		}
		
		api.criaNovoUsuario( "login", "senha", "nome", "email" );
		String sessaoID = api.abrirSessao("login", "senha");
		
		List<Musica> list = new LinkedList<Musica>();
		Assert.assertEquals(list, api.getPerfilMusical("login"));
		api.postarSom( sessaoID , "http://youtu.be/IXcruXXLy8E", new GregorianCalendar() );
		
		Assert.assertEquals(1, api.getPerfilMusical("login").size());
		Musica som = new Musica( sessaoID , "http://youtu.be/IXcruXXLy8E", new GregorianCalendar() );
		Assert.assertEquals(som, api.getPerfilMusical(sessaoID).get(1));
	}
	
	
	@Test
	public void encerrarSessao() throws UsuarioNaoCadastradoException, LoginException{
		
		api.criaNovoUsuario( "login", "senha", "nome", "email" );
		String sessaoID = api.abrirSessao("login", "senha");
		api.encerrarSessao("login");
		
		try {
			api.postarSom( "idSessao" , "link", new GregorianCalendar() );
			Assert.fail( "Deveria lancar uma SessaoIDException" );
		} catch ( SessaoIDException e) {
			Assert.assertEquals( "SessaoID invalido!", e.getMessage() );
		} catch (Exception e) {
			Assert.fail( "Deveria lancar uma SessaoIDException" );
		}
	}
	
}
