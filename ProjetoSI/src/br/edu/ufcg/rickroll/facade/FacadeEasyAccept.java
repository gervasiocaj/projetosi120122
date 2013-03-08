/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufcg.rickroll.facade;

import br.edu.ufcg.rickroll.rickroll.*;
import br.edu.ufcg.rickroll.exceptions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author Guilherme
 */
public class FacadeEasyAccept {

	private RickRoll sistema;

	public void zerarSistema() {
		sistema = new RickRoll();
	}

	public void criarUsuario(String login, String senha, String nome,
			String email) throws CriacaoUserException, AtributoException {
		sistema.criaNovoUsuario(login, senha, nome, email);
	}

	public String abrirSessao(String login, String senha)
			throws LoginException, UsuarioNaoCadastradoException {
		return sistema.abrirSessao(login, senha);
	}

	public String getAtributoUsuario(String login, String atributo)
			throws AtributoException, UsuarioNaoCadastradoException, LoginException {
		if (!Verificador.verificaStringValida(login))
			throw new LoginException("Login inválido");
		return sistema.getAtributoUsuario(login, atributo);
	}

	public String getPerfilMusical(String idSessao)
			throws UsuarioNaoCadastradoException, SessaoIDException {
		List<String> musicas = sistema.getPerfilMusical(idSessao);
		Iterator<String> it = musicas.iterator();
		
		String mus = "{";
		while (it.hasNext()) {
			mus += it.next();
			if (it.hasNext())
				mus += ",";
		}
		mus += "}";
		return mus;

	}

	public String postarSom(String idSessao, String link, String dataCriacao)
			throws SessaoIDException, LinkInvalidoException, DataInvalidaException {
		return sistema.postarSom(idSessao, link, dataCriacao);
	}

	public String getAtributoSom(String idSom, String atributo)
			throws UsuarioNaoCadastradoException, AtributoException, SomInexistenteException {
		return sistema.getAtributoSom(idSom, atributo);
	}

	public void encerrarSessao(String login) throws SessaoIDException {
		sistema.encerrarSessao(login);
	}

	public void encerrarSistema() {
		sistema = null;
	}

	public String getIDUsuario(String idSessao) {
		return sistema.getIDUsuario(idSessao);
	}

	public int getNumeroDeSeguidores(String idSessao) throws Exception {
		return sistema.getListaDeSeguidores(idSessao).size();
	}

	public void seguirUsuario(String idSessao, String login)
			throws SessaoIDException, LoginException {
		sistema.seguirUsuario(idSessao, login);
	}

	public String getFontesDeSons(String idSessao)
			throws SessaoIDException {
		String str = "{";
		Set<String> fonteDeSons = sistema.getListaSeguindo(idSessao);
		Iterator<String> it = fonteDeSons.iterator();
		while(it.hasNext()){
			str += it.next();
			if(it.hasNext()){
				str += ",";
			}
		}
		str += "}";
		return str;
		
	}

	public String getListaDeSeguidores(String idSessao)
			throws Exception {
		String str = "{";
		Set<String> seguidores = sistema.getListaDeSeguidores(idSessao);
		Iterator<String> it = seguidores.iterator();
		while(it.hasNext()){
			str += it.next();
			if(it.hasNext()){
				str += ",";
			}
		}
		str += "}";
		return str;
	}
	
	public void favoritarSom(String idSessao, String idSom) throws SessaoIDException{
		sistema.addFavorito(idSessao, idSom);
	}
	
	public String getSonsFavoritos(String idSessao) throws SessaoIDException{
		String str = "{";
		List<String> favoritos = sistema.getFavoritos(idSessao);
		Iterator<String> it = favoritos.iterator();
		while(it.hasNext()){
			str += it.next();
			if(it.hasNext()){
				str += ",";
			}
		}
		str += "}";
		return str;
	}
	
	public String getFeedExtra(String idSessao){
		String str = "{";
		List<Favorito> feed = sistema.getFeedExtra(idSessao);
		Iterator<Favorito> it = feed.iterator();
		while(it.hasNext()){
			str += it.next().getIdMusica();
			if(it.hasNext()){
				str += ",";
			}
		}
		str += "}";
		return str;
	}


}