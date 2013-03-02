package br.edu.ufcg.rickroll.rickroll;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.edu.ufcg.rickroll.exceptions.AtributoInvalidoException;
import br.edu.ufcg.rickroll.exceptions.DataInvalidaException;
import br.edu.ufcg.rickroll.exceptions.LinkInvalidoException;
import br.edu.ufcg.rickroll.exceptions.LoginException;
import br.edu.ufcg.rickroll.exceptions.SessaoIDException;
import br.edu.ufcg.rickroll.exceptions.UsuarioNaoCadastradoException;



public class RickRoll {

	Map<String, String> usuarioLogados;

	DataStorage storage;

	public RickRoll() {
		usuarioLogados = new HashMap<String, String>();
		storage = new DataStorage();
	}

	public void criaNovoUsuario(String login, String senha, String nome,
			String email) {

		if ( !Verificador.verificaStringValida(login) ) {
			// TODO: falta exception
		}else if ( !Verificador.verificaStringValida(senha) ){
			// TODO: falta exception
		}else if ( !Verificador.verificaStringValida(nome) ){
			// TODO: falta exception
		}else if ( !Verificador.verificaStringValida(email) ){
			// TODO: falta exception
		}

		if ( storage.hasUser(login, email) ) {
			// TODO: falta exception
		}
		Usuario usr = new Usuario(login, senha, nome, email);
		storage.addUsuario(usr);
	}

	public String procuraUsuario(String userLogin) throws UsuarioNaoCadastradoException, LoginException {
		if ( userLogin == null || userLogin == "" ) throw new  LoginException("Login inv�lido");

		String userID = storage.getUserID( userLogin );
		if ( userID==null ) throw new UsuarioNaoCadastradoException("Usu�rio inexistente");
		return userID;
	}

	public String getAtributoUsuario(String login, String attr) throws UsuarioNaoCadastradoException, AtributoInvalidoException {
		String userID = storage.getUserID( login );
		if ( userID==null ) throw new UsuarioNaoCadastradoException("Usu�rio inexistente");

		Usuario user = storage.getUser(userID);

		if ( attr == null || attr == "" ) {
			throw new AtributoInvalidoException("Atributo inv�lido");
		}else if ( attr == "nome" ){
			return user.getNome();
		}else if ( attr == "email" ) {
			return user.getEmail();
		}else
			throw new AtributoInvalidoException("Atributo inexistente");
	}

	public String abrirSessao(String login, String senha) throws UsuarioNaoCadastradoException {
		String userID = storage.getUserID( login );
		if ( userID==null ) throw new UsuarioNaoCadastradoException("Usu�rio inexistente");

		// TODO: sessaoID vai ser oq?
		// TODO: falta tratar exception
		usuarioLogados.put(userID, login);
		return userID;
	}

	public List<Musica> getPerfilMusical(String sessaoID) throws UsuarioNaoCadastradoException {
		Usuario usr = storage.getUser( usuarioLogados.get(sessaoID) );
		if ( usr==null ) throw new UsuarioNaoCadastradoException("Usu�rio inexistente");

		return usr.getPerfilMusical();
	}

	public List<Musica> getPerfilMusical(String sessaoID, String userID) {
		Usuario usrLogado = storage.getUser( usuarioLogados.get(sessaoID) );
		if ( !usrLogado.getListaAmigos().contains(userID) ) {

		}
		Usuario amigo = storage.getUser( userID );;
		return amigo.getPerfilMusical();
	}

	public void postarSom(String sessaoID, String link, GregorianCalendar dataCriacao) throws SessaoIDException, LinkInvalidoException, DataInvalidaException {
		String loginUser = usuarioLogados.get(sessaoID);
		if(loginUser == null){
			throw new SessaoIDException("SessaoID invalida");
		}
		storage.getUser(usuarioLogados.get(sessaoID)).addMusica(new Musica(sessaoID, link, dataCriacao));

	}

	public void encerrarSessao(String sessaoID) {
		usuarioLogados.remove(sessaoID);
	}

	public Set<String> getListaAmigos(String sessaoID) {
		return storage.getUser(usuarioLogados.get(sessaoID)).getListaAmigos();
	}

	public void enviaSolicitacaoDeAmizade(String sessaoID, String userID) {
		if ( storage.getUser(usuarioLogados.get(sessaoID)).temSolicitacaoDoAmigo(userID) ) {
			storage.getUser(userID).removeSolicitacaoDeAmizade( usuarioLogados.get(sessaoID) );
			storage.getUser(usuarioLogados.get(sessaoID)).removeSolicitacaoDeAmizadePendente( userID );
			storage.getUser(usuarioLogados.get(sessaoID)).addAmigo(userID);
			storage.getUser(userID).addAmigo( usuarioLogados.get(sessaoID) );
		}else{
			storage.getUser(usuarioLogados.get(sessaoID)).addSolicitacaoDeAmizade(userID);
			storage.getUser(userID).addSolicitacaoDeAmizadePendente( usuarioLogados.get(sessaoID) );
		}
	}

	public List<String> getSolicitacaoDeAmizade(String sessaoID) {
		return storage.getUser(usuarioLogados.get(sessaoID)).getMinhasSolicitacoes();
	}

	public List<String> getListaDeSolicitacaoDeAmizadePendente(String sessaoID) {
		return storage.getUser(usuarioLogados.get(sessaoID)).getMinhasSolicitacoesPendentes();
	}

	public List<Musica> getTimeLine(String sessaoID) {
		Set<String> minhaLista = getListaAmigos(sessaoID);
		LinkedList<Musica> timeLine = new LinkedList<Musica>();
		Iterator<String> i = minhaLista.iterator();
		while ( i.hasNext() ) {
			String amigoID = i.next();
			Usuario user = storage.getUser(amigoID);
			timeLine.addAll(user.getPerfilMusical());
		}
		Collections.sort(timeLine);
		return timeLine;
	}

	/**
	 * 	TODO: colocar todos os metodos para serem autenticados, ou seja,
	 * passar o sessaoID como parametro para verificar se existe um usuario 
	 * com essa sessaoID logado para poder realizar os metodos!
	 *  
	 *  TODO: realizar testes nao funcionais. Verificar se o sistema nao faz aquilo
	 * que nao eh para fazer.
	 * 
	 *  TODO: 
	 */

}