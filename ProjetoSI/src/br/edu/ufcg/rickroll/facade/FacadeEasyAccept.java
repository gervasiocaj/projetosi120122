/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufcg.rickroll.facade;

import br.edu.ufcg.rickroll.rickroll.*;
import br.edu.ufcg.rickroll.exceptions.*;

import java.util.*;

import remake.util.Favorito;

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
			throws AtributoException, UsuarioNaoCadastradoException,
			LoginException {
		if (!Verificador.verificaStringValida(login))
			throw new LoginException("Login inválido");
		return sistema.getAtributoUsuario(login, atributo);
	}

	public String getPerfilMusical(String idSessao)
			throws UsuarioNaoCadastradoException, SessaoIDException {
		List<String> musicas = sistema.getPerfilMusical(idSessao);
		return convertCollection(musicas);
	}

	public String postarSom(String idSessao, String link, String dataCriacao)
			throws SessaoIDException, LinkInvalidoException,
			DataInvalidaException {
		return sistema.postarSom(idSessao, link, dataCriacao);
	}

	public String getAtributoSom(String idSom, String atributo)
			throws UsuarioNaoCadastradoException, AtributoException,
			SomInexistenteException {
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

	public String getFontesDeSons(String idSessao) throws SessaoIDException {
		Set<String> fonteDeSons = sistema.getListaSeguindo(idSessao);
		return convertCollection(fonteDeSons);
	}

	public String getVisaoDosSons(String idSessao) throws SessaoIDException {
		List<String> sons = new LinkedList<String>();
		for (String user : sistema.getListaSeguindo(idSessao)) {
			sons.addAll(sistema.getPerfilMusical(idSessao, user));
		}
		return convertCollection(sons); // TODO
	}

	public String getListaDeSeguidores(String idSessao) throws Exception {
		Set<String> seguidores = sistema.getListaDeSeguidores(idSessao);
		return convertCollection(seguidores);
	}

	public void favoritarSom(String idSessao, String idSom)
			throws SessaoIDException, SomInexistenteException {
		sistema.addFavorito(idSessao, idSom);
	}

	public String getSonsFavoritos(String idSessao) throws SessaoIDException {
		List<String> favoritos = sistema.getFavoritos(idSessao);
		return convertCollection(favoritos);
	}

	public String getFeedExtra(String idSessao) throws SessaoIDException {
		List<String> feed = new LinkedList<String>();
		for (Favorito m : sistema.getFeedExtra(idSessao))
			feed.add(m.getIdMusica());
		return convertCollection(feed);
	}
	
	public String getFirstCompositionRule(){
		return sistema.getPrimeiraRegraDeComposicao();
	}
	
	public String getSecondCompositionRule(){
		return sistema.getSegundaRegraDeComposicao();
	}
	
	public String getThirdCompositionRule(){
		return sistema.getTerceiraRegraDeComposicao();
	}
	
	public String getMainFeed(String idSessao) throws SessaoIDException{
		List<String> feed = new LinkedList<String>();
		for (String m : sistema.getMainFeed(idSessao))
			feed.add(m);
		return convertCollection(feed);
	}
	
	public void setMainFeedRule(String idSessao ,String rule) throws SessaoIDException, RegraDeComposicaoException{
		sistema.setRegraDeComposicao(idSessao, rule);
	}

	private String convertCollection(Collection<?> c) {
		String str = "{";
		Iterator<?> it = c.iterator();
		while (it.hasNext()) {
			str += it.next().toString();
			if (it.hasNext()) {
				str += ",";
			}
		}
		str += "}";
		return str;
	}
	

}