package Testes;
import static org.junit.Assert.*;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import Classes.Musica;
import Classes.SystemAPI;
import Exceptions.AtributoInvalidoException;
import Exceptions.DataInvalidaException;
import Exceptions.LinkInvalidoException;
import Exceptions.LoginException;
import Exceptions.PostaSomException;
import Exceptions.SessaoIDException;
import Exceptions.UsuarioNaoCadastradoException;


public class TesteUS01 {

	SystemAPI api;
	
	@Before
	public void init(){
		 api = new SystemAPI();
	}
	
	// TODO: TESTES NAO FUNCIONAIS!
	
	@Test
	public void criaNovoUsu�rio() throws Exception {
		
		String idUser;
		try {
			idUser = api.procuraUsuario( "login" );
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		} catch (UsuarioNaoCadastradoException e) {
			Assert.assertEquals( "Usu�rio inexistente", e.getMessage() );
		} catch (Exception e){
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		}
		
		// TODO: Decidir de userID vai ser mesmo o login do usr
		api.criaNovoUsuario( "login", "senha", "nome", "email" );
		idUser = api.procuraUsuario( "login" );
		Assert.assertEquals( "login", idUser );
		
	}
	
	@Test
	public void getAtributoUsuario() throws Exception {
		
		try {
			api.getAtributoUsuario( "login", "" );
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		} catch (UsuarioNaoCadastradoException e) {
			Assert.assertEquals( "Usu�rio inexistente", e.getMessage() );
		} catch (Exception e){
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		}
		
		api.criaNovoUsuario( "login", "senha", "nome", "email" );

		Assert.assertEquals( "nome", api.getAtributoUsuario( "login", "nome" ) );
		Assert.assertEquals( "email", api.getAtributoUsuario( "login", "email" ) );
		
	}
	
	@Test
	public void abrirSessao() throws Exception{
		
		try {
			api.abrirSessao("login", "senha");
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		} catch ( UsuarioNaoCadastradoException e) {
			Assert.assertEquals( "Usu�rio inexistente", e.getMessage() );
		} catch (Exception e) {
			Assert.fail( "Deveria lancar uma UsuarioNaoCadastradoException" );
		}
		
		api.criaNovoUsuario( "login", "senha", "nome", "email" );
		String sessaoID = api.abrirSessao("login", "senha");
		
		// TODO: Decidir de sessaoID vai ser mesmo o login do usr logado
		Assert.assertEquals( "login", sessaoID );
		
	}

	@Test
	public void getPerfilMusical() throws Exception{
		api.criaNovoUsuario( "login", "senha", "nome", "email" );
		String sessaoID = api.abrirSessao("login", "senha");
		
		List<Musica> list = new LinkedList<Musica>();
		Assert.assertEquals(list, api.getPerfilMusical(sessaoID));
	}
	
	@Test
	public void postarSom() throws Exception {

		try {
			api.postarSom( "idSessao" , "link", new GregorianCalendar() );
			Assert.fail( "Deveria lancar uma SessaoIDException" );
		} catch ( SessaoIDException e) {
			Assert.assertEquals( "SessaoID invalida", e.getMessage() );
		} catch (Exception e) {
			Assert.fail( "Deveria lancar uma SessaoIDException" );
		}
		
		api.criaNovoUsuario( "login", "senha", "nome", "email" );
		String sessaoID = api.abrirSessao("login", "senha");
		
		GregorianCalendar data = new GregorianCalendar();
		List<Musica> lista = new LinkedList<Musica>();
		Assert.assertEquals(lista, api.getPerfilMusical("login"));
		api.postarSom( sessaoID , "http://youtu.be/IXcruXXLy8E", new GregorianCalendar(data.YEAR, data.MONTH, data.DAY_OF_MONTH) );
		
		lista.add(new Musica(sessaoID, "http://youtu.be/IXcruXXLy8E", new GregorianCalendar(data.YEAR, data.MONTH, data.DAY_OF_MONTH) ));
		
		System.out.println(lista.get(0));
		System.out.println(api.getPerfilMusical("login").get(0));
		
		Assert.assertEquals(lista, api.getPerfilMusical("login"));

	}
	
	@Test
	public void encerrarSessao() throws Exception {
		
		api.criaNovoUsuario( "login", "senha", "nome", "email" );
		String sessaoID = api.abrirSessao("login", "senha");
		api.encerrarSessao("login");
		
		try {
			api.postarSom( "idSessao" , "link", new GregorianCalendar() );
			Assert.fail( "Deveria lancar uma SessaoIDException" );
		} catch ( SessaoIDException e) {
			Assert.assertEquals( "SessaoID invalida", e.getMessage() );
		} catch (Exception e) {
			Assert.fail( "Deveria lancar uma SessaoIDException" );
		}
	}
	
}