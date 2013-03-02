package br.edu.ufcg.rickroll.rickroll;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import br.edu.ufcg.rickroll.exceptions.AtributoInvalidoException;
import br.edu.ufcg.rickroll.exceptions.DataInvalidaException;
import br.edu.ufcg.rickroll.exceptions.LinkInvalidoException;
import br.edu.ufcg.rickroll.exceptions.LoginException;
import br.edu.ufcg.rickroll.exceptions.SessaoIDException;
import br.edu.ufcg.rickroll.exceptions.UsuarioNaoCadastradoException;


public class SystemAPI {

	RickRoll sistema = new RickRoll();
	
	/** Cria um novo usuario e adiciona ele ao sistema
	 * 
	 * @param login
	 * 		login do novo usuario
	 * @param senha
	 * 		senha do novo usuario
	 * @param nome
	 * 		nome do novo usuario
	 * @param email
	 * 		email do novo usuario
	 */
	
	public void criaNovoUsuario( String login, String senha, String nome, String email ) {
		sistema.criaNovoUsuario( login, senha, nome, email);
	}

	/** Procura por um usuario no sistema
	 * 
	 * @param usuarioLogin
	 * 		login do usuario a ser procurado
	 * @return
	 * 		id do usuario procurado
	 * @throws UsuarioNaoCadastradoException
	 * 		joga excessao caso o usuario nao esteja cadastrado
	 * @throws LoginException
	 * 		joga excessao caso o login dado esteja incorreto
	 */
	
	
	public String procuraUsuario(String usuarioLogin) throws UsuarioNaoCadastradoException, LoginException {
		return sistema.procuraUsuario( usuarioLogin );
	}
	
	
	/** Procura por um atributo de um determinado usuario
	 * 
	 * @param login
	 * 		login do usuario desejado
	 * @param attr
	 * 		atributo desejado ( nome ou email)
	 * @return
	 * 		o atributo desejado
	 * @throws UsuarioNaoCadastradoException
	 * 		joga excessao caso o usuario nao esteja cadastrado
	 * @throws AtributoInvalidoException
	 * 		joga excessao caso o atributo seja invalido.
	 */
	
	
	public String getAtributoUsuario( String login, String attr ) throws UsuarioNaoCadastradoException, AtributoInvalidoException {
		return sistema.getAtributoUsuario( login, attr );
	}
	 
	
	/** Abre uma nova sessao com login e senha do usuario
	 * 
	 * @param login
	 * 		login do usuario que ira abrir a sessao
	 * @param senha
	 * 		senha do usuario que ira abrir a sessao
	 * @return
	 * 		o id do usuario que esta logado
	 * @throws UsuarioNaoCadastradoException
	 * 		joga excessao caso o usuario nao esteja cadastrado
	 * @throws LoginException
	 * 		joga excessao caso o login dado esteja incorreto
	 */
	
	
	public String abrirSessao( String login, String senha ) throws UsuarioNaoCadastradoException, LoginException {
		return sistema.abrirSessao( login, senha );
	}
	
	
	/** Pega o perfil musical do usuario, utilizando somente o sessao id
	 * 
	 * @param sessaoID
	 * 		id da sessao do usuario
	 * @return
	 * 		uma lista contendo as musicas do usuario, seu perfil musical
	 * @throws UsuarioNaoCadastradoException
	 * 		joga excessao caso o usuario nao esteja cadastrado
	 */
	
	public List<Musica> getPerfilMusical( String sessaoID ) throws UsuarioNaoCadastradoException {
		return sistema.getPerfilMusical( sessaoID );
	}
	
	/** Pega o perfil musical de outro usuario, desde que eles sejam amigos( um possua o outro como contato)
	 * 
	 * @param sessaoID
	 * 		id da sessao do usuario corrente
	 * @param userID
	 * 		id do usuario que se procura o perfil musical
	 * @return
	 * 		perfil musical do amigo
	 */
	
	public List<Musica> getPerfilMusical(String sessaoID, String userID) {
		return sistema.getPerfilMusical( sessaoID, userID );
	}
	
	/**	Posta um som, e o adiciona ao perfil musical do postador
	 * 
	 * @param sessaoID
	 * 		id da sessao do usuario que esta postando o som
	 * @param link
	 * 		link do som
	 * @param dataCriacao
	 * 		data de criacao do novo som
	 * @throws SessaoIDException
	 * 		joga excessao caso o id da sessao seja invalido
	 * @throws LinkInvalidoException
	 * 		joga excessao caso o lnik seja invalido
	 * @throws DataInvalidaException
	 * 		joga excessao caso a data seja invalida
	 * 
	 */
	
	
	public void postarSom( String sessaoID, String link, GregorianCalendar dataCriacao ) throws SessaoIDException, LinkInvalidoException, DataInvalidaException {
		sistema.postarSom( sessaoID, link, dataCriacao );
	}
	
	/** Encerra a sessao corrente
	 * 
	 * @param sessaoID
	 * 		id da sessao a ser encerrada
	 */
	
	public void encerrarSessao(String sessaoID) {
		sistema.encerrarSessao(sessaoID);
	}
	
	public void encerrarSistema() {
		// TODO Auto-generated method stub
	}

	/** Pega a lista de amigos do usuario
	 * 
	 * @param sessaoID
	 * 		id da sessao do usuario que se deseja a lista de amigos
	 * 		
	 * @return
	 * 		todos os amigos
	 */
	public Set<String> getListaAmigos(String sessaoID) {
		return sistema.getListaAmigos(sessaoID);
		
	}

	/** Envia uma solicitacao de amizade de um usuario a outro
	 * 
	 * @param sessaoID
	 * 		id da sessao corrente
	 * @param userID
	 * 		id do usuario que se deseja adicionar como amigo
	 */
	public void enviaSolicitacaoDeAmizade(String sessaoID, String userID) {
		sistema.enviaSolicitacaoDeAmizade( sessaoID, userID );
		
	}
	
	/** Pega a lista com as solicitacoes de amizade do usuario
	 * 
	 * @param sessaoID
	 * 		id da sessao do usuario
	 * @return
	 * 		lista contendo as solicitacoes de amizade
	 */

	public List<String> getListaDeSolicitacaoDeAmizade(String sessaoID) {
		return sistema.getSolicitacaoDeAmizade( sessaoID );
	}

	/** Pega a lista de solicitacoes feitas pelo usuario
	 * 
	 * @param sessaoID
	 * 		id da sessao do usuario
	 * @return
	 * 		lista com as solicitacoes pendentes
	 */
	
	
	public List<String> getListaDeSolicitacaoDeAmizadePendente(String sessaoID) {
		return sistema.getListaDeSolicitacaoDeAmizadePendente(sessaoID);
	}
	
	/** Aceita uma solicitacao de amizade de outro usuario
	 * 
	 * @param sessaoID
	 * 		id da sessao do usuario
	 * @param userID
	 * 		id do usuario o qual se aceitara a requisicao
	 */

	public void aceitaSolicitacaoDeAmizade(String sessaoID, String userID) {
		sistema.enviaSolicitacaoDeAmizade( sessaoID, userID );
		
	}
	
	public List<Musica> getTimeLine(String sessaoID) {
		return sistema.getTimeLine(sessaoID);
	}
	
}
