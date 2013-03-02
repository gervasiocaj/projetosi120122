package br.edu.ufcg.rickroll.tests;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.edu.ufcg.rickroll.exceptions.LoginException;
import br.edu.ufcg.rickroll.exceptions.UsuarioNaoCadastradoException;
import br.edu.ufcg.rickroll.rickroll.Musica;
import br.edu.ufcg.rickroll.rickroll.SystemAPI;


public class TesteUS03 {

	SystemAPI api;
	
	@Before
	public void init(){
		 api = new SystemAPI();
		 api.criaNovoUsuario( "eduardo", "123456", "nome", "email@gmail.com" );
		 api.criaNovoUsuario( "gabriel", "minhasenhaeh", "nome", "email" );
		 api.criaNovoUsuario( "gervasio", "senha", "nome", "email" );
		 api.criaNovoUsuario( "guilherme", "senha?", "nome", "email" );
	}

	@Test
	public void getTimeLine() throws Exception {
		
		String gabrielID = "gabriel";
		String sessaoGabriel = api.abrirSessao("gabriel", "minhasenhaeh");
		Set amigosDeGabriel = api.getListaAmigos( sessaoGabriel );
		
		Assert.assertEquals( new LinkedList<Musica>(), api.getTimeLine(sessaoGabriel));
		
		String eduardoID = "eduardo";
		String sessaoEduardo = api.abrirSessao("eduardo", "123456");
		Set amigosDeEduardo = api.getListaAmigos( sessaoEduardo );
		
		String gervasioID = "gervasio";
		String sessaoGervasio= api.abrirSessao("gervasio", "senha");
		Set amigosDeGervasio = api.getListaAmigos( sessaoEduardo );
		
		String guilhermeID = "guilherme";
		String sessaoGuilherme= api.abrirSessao("guilherme", "senha?");
		Set amigosDeGuilherme = api.getListaAmigos( sessaoEduardo );
		
		api.enviaSolicitacaoDeAmizade( sessaoGabriel, eduardoID );
		api.aceitaSolicitacaoDeAmizade( sessaoEduardo, gabrielID );
		
		api.enviaSolicitacaoDeAmizade( sessaoEduardo, gervasioID );
		api.enviaSolicitacaoDeAmizade( sessaoEduardo, guilhermeID);
		api.aceitaSolicitacaoDeAmizade( sessaoGervasio, eduardoID );
		api.aceitaSolicitacaoDeAmizade( sessaoGuilherme, eduardoID );
		System.out.println(api.getListaAmigos( sessaoEduardo ));
		Assert.assertEquals( new LinkedList<Musica>(), api.getTimeLine(sessaoGabriel));
		
		GregorianCalendar data = new GregorianCalendar();
		List<Musica> lista = new LinkedList<Musica>();
		List<Musica> lista2 = new LinkedList<Musica>();
		Assert.assertEquals(lista, api.getPerfilMusical(gabrielID));
		api.postarSom( sessaoGabriel , "http://youtu.be/IXcruXXLy8E", new GregorianCalendar(data.YEAR, data.MONTH, data.DAY_OF_MONTH) );
		
		Assert.assertEquals( lista, api.getTimeLine(sessaoGabriel));
		
		api.postarSom( sessaoEduardo , "http://youtu.be/IXcruXXLy8E", new GregorianCalendar(data.YEAR, data.MONTH, data.DAY_OF_MONTH) );
		
		lista.add(new Musica(eduardoID, "http://youtu.be/IXcruXXLy8E", new GregorianCalendar(data.YEAR, data.MONTH, data.DAY_OF_MONTH) ));
		lista2.add(new Musica(gabrielID, "http://youtu.be/IXcruXXLy8E", new GregorianCalendar(data.YEAR, data.MONTH, data.DAY_OF_MONTH) ));
		Assert.assertEquals( lista, api.getTimeLine(sessaoGabriel));
		
		api.postarSom( gervasioID , "http://youtu.be/IXcruXXLy8E", new GregorianCalendar(data.YEAR, data.MONTH, data.DAY_OF_MONTH) );
		api.postarSom( guilhermeID , "http://youtu.be/IXcruXXLy8E", new GregorianCalendar(data.YEAR, data.MONTH, data.DAY_OF_MONTH) );
		api.postarSom( gervasioID , "http://youtu.be/IXcruXXLy8E", new GregorianCalendar(data.YEAR, data.MONTH, data.DAY_OF_MONTH) );
		api.postarSom( gervasioID , "http://youtu.be/IXcruXXLy8E", new GregorianCalendar(data.YEAR, data.MONTH, data.DAY_OF_MONTH) );
		
		lista2.addAll(api.getPerfilMusical(sessaoEduardo, guilhermeID));
		lista2.addAll(api.getPerfilMusical(sessaoEduardo, gervasioID));
//		lista2.addAll(api.getPerfilMusical(sessaoEduardo, guilhermeID));
		Collections.sort(lista2);
		
		Assert.assertEquals( lista, api.getTimeLine(sessaoGabriel));
		
//		Collections.reverse(lista2);
		System.out.println(api.getTimeLine(sessaoEduardo).toString());
		System.out.println(lista2.toString());
		Assert.assertEquals( lista2, api.getTimeLine(sessaoEduardo));
		
	}
	
}
