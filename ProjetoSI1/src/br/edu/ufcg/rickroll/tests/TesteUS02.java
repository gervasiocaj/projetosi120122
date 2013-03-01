package Testes;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import Classes.SystemAPI;
import Exceptions.LoginException;
import Exceptions.UsuarioNaoCadastradoException;

public class TesteUS02 {

	SystemAPI api;
	
	@Before
	public void init(){
		 api = new SystemAPI();
		 api.criaNovoUsuario( "eduardo", "123456", "nome", "email@gmail.com" );
		 api.criaNovoUsuario( "gabriel", "minhasenhaeh", "nome", "email" );
	}

	@Test
	public void testaGetListaAmigos() throws Exception{
		
		String gabrielID = "gabriel";
		String sessaoGabriel = api.abrirSessao("gabriel", "minhasenhaeh");
		Set amigosDeGabriel = api.getListaAmigos( sessaoGabriel );
		
		String eduardoID = "eduardo";
		String sessaoEduardo = api.abrirSessao("eduardo", "123456");
		Set amigosDeEduardo = api.getListaAmigos( sessaoEduardo );
		
		Assert.assertEquals(new HashSet<String>(), amigosDeGabriel);
		Assert.assertEquals(new HashSet<String>(), amigosDeEduardo);
		
		api.enviaSolicitacaoDeAmizade( sessaoGabriel, eduardoID );
		
		Assert.assertEquals(new HashSet<String>(), amigosDeGabriel);
		Assert.assertEquals(new HashSet<String>(), amigosDeEduardo);
		
		List<String> listaDeSolicitacaoDeAmizadeGabriel = new LinkedList<String>();
		listaDeSolicitacaoDeAmizadeGabriel.add(eduardoID);
		List<String> listaDeSolicitacaoDeAmizadePendentesEduardo= new LinkedList<String>();
		listaDeSolicitacaoDeAmizadePendentesEduardo.add(gabrielID);
		Assert.assertEquals(listaDeSolicitacaoDeAmizadeGabriel, api.getListaDeSolicitacaoDeAmizade( sessaoGabriel ));
		Assert.assertEquals(new LinkedList<String>(), api.getListaDeSolicitacaoDeAmizadePendente( sessaoGabriel ));
		Assert.assertEquals(new LinkedList<String>(), api.getListaDeSolicitacaoDeAmizade(sessaoEduardo));
		Assert.assertEquals(listaDeSolicitacaoDeAmizadePendentesEduardo, api.getListaDeSolicitacaoDeAmizadePendente( sessaoEduardo ));
		
		api.aceitaSolicitacaoDeAmizade( sessaoEduardo, gabrielID );
		
		Assert.assertEquals(new LinkedList<String>(), api.getListaDeSolicitacaoDeAmizade( sessaoGabriel ));
		Assert.assertEquals(new LinkedList<String>(), api.getListaDeSolicitacaoDeAmizadePendente( sessaoGabriel ));
		Assert.assertEquals(new LinkedList<String>(), api.getListaDeSolicitacaoDeAmizade(sessaoEduardo));
		Assert.assertEquals(new LinkedList<String>(), api.getListaDeSolicitacaoDeAmizadePendente( sessaoEduardo ));
		
		amigosDeGabriel.add(eduardoID);
		amigosDeEduardo.add(gabrielID);
		Assert.assertEquals(amigosDeGabriel, api.getListaAmigos( sessaoGabriel ));
		Assert.assertEquals(amigosDeEduardo, api.getListaAmigos( sessaoEduardo ));
	}
	
}
