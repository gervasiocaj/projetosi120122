package br.edu.ufcg.rickroll.rickroll;

import java.text.SimpleDateFormat;
import java.util.*;

import br.edu.ufcg.rickroll.exceptions.*;

public class RickRoll {

	Map<String, String> usuarioLogados;

	DataStorage storage;

	public RickRoll() {
		usuarioLogados = new HashMap<String, String>();
		storage = new DataStorage();
	}

	public void criaNovoUsuario(String login, String senha, String nome,
			String email) throws AtributoException, CriacaoUserException {

		if (storage.getUserID(login) != null)
			throw new CriacaoUserException(
					"Já existe um usuário com este login");
		else if (storage.hasUserEmail(email))
			throw new CriacaoUserException(
					"Já existe um usuário com este email");

		Usuario usr = new Usuario(login, senha, nome, email);
		storage.addUsuario(usr);
	}

	public String procuraUsuario(String userLogin)
			throws UsuarioNaoCadastradoException, LoginException {
		if (userLogin == null || userLogin.equals(""))
			throw new LoginException("Login inv�lido");

		String userID = storage.getUserID(userLogin);
		if (userID == null)
			throw new UsuarioNaoCadastradoException("Usuário inexistente");
		return userID;
	}

	public String getAtributoUsuario(String login, String attr)
			throws UsuarioNaoCadastradoException, AtributoException {
		String userID = storage.getUserID(login);
		if (userID == null)
			throw new UsuarioNaoCadastradoException("Usuário inexistente");

		Usuario user = storage.getUser(userID);

		if (attr == null || attr.equals("")) {
			throw new AtributoException("Atributo inválido");
		} else if (attr.equals("nome")) {
			return user.getNome();
		} else if (attr.equals("email")) {
			return user.getEmail();
		} else
			throw new AtributoException("Atributo inexistente");
	}

	public String abrirSessao(String login, String senha)
			throws UsuarioNaoCadastradoException, LoginException {

		if (!Verificador.verificaStringValida(login))
			throw new LoginException("Login inválido");

		String userID = storage.getUserID(login);
		if (userID == null)
			throw new UsuarioNaoCadastradoException("Usuário inexistente");

		// TODO: sessaoID vai ser oq?
		// TODO: falta tratar exception

		if (!storage.getUser(userID).getSenha().equals(senha))
			throw new LoginException("Login inválido");

		String sessaoID = login + Calendar.getInstance().hashCode();

		usuarioLogados.put(sessaoID, userID);

		return sessaoID;
	}

	public List<String> getPerfilMusical(String sessaoID)
			throws UsuarioNaoCadastradoException {
		Usuario usr = storage.getUser(usuarioLogados.get(sessaoID));
		if (usr == null)
			throw new UsuarioNaoCadastradoException("Usuário inexistente");

		return usr.getPerfilMusical();
	}

	public List<String> getPerfilMusical(String sessaoID, String userID) {
		Usuario usrLogado = storage.getUser(usuarioLogados.get(sessaoID));
		if (!usrLogado.getListaMeusSeguidores().contains(userID)) {

		}
		Usuario amigo = storage.getUser(userID);
		;
		return amigo.getPerfilMusical();
	}

	public String postarSom(String sessaoID, String link,
			GregorianCalendar dataCriacao) throws SessaoIDException,
			LinkInvalidoException, DataInvalidaException {

		String loginUser = usuarioLogados.get(sessaoID);
		if (loginUser == null)
			throw new SessaoIDException("SessaoID invalida");
		if (dataCriacao.before(GregorianCalendar.getInstance()))
			throw new DataInvalidaException("Data de Criação inválida");

		Musica musica = new Musica(loginUser, link, dataCriacao);
		storage.addMusic(musica);
		storage.getUser(usuarioLogados.get(sessaoID)).addMusica(musica.getID());
		return musica.getID();
	}

	public void encerrarSessao(String sessaoID) {
		usuarioLogados.remove(sessaoID);
	}

	public Set<String> getListaSeguindo(String sessaoID) {
		return storage.getUser(usuarioLogados.get(sessaoID)).getSeguindo();
	}

	public void seguirUsuario(String sessaoID, String loginFollowed) {
		String userIDAtual = usuarioLogados.get(sessaoID); 
		String userIDSeguido = storage.getUserID(loginFollowed);
		storage.getUser(userIDAtual).seguir(userIDSeguido);
		storage.getUser(userIDSeguido).addMeuSeguidor(userIDAtual);
	}

	/*
	 * public List<String> getSolicitacaoDeAmizade(String sessaoID) { return
	 * storage.getUser(usuarioLogados.get(sessaoID)).getMinhasSolicitacoes(); }
	 * 
	 * public List<String> getListaDeSolicitacaoDeAmizadePendente(String
	 * sessaoID) { return
	 * storage.getUser(usuarioLogados.get(sessaoID)).getMinhasSolicitacoesPendentes
	 * (); }
	 */
	public List<String> getTimeLine(String sessaoID)
			throws UsuarioNaoCadastradoException {
		Set<String> minhaLista = getListaSeguindo(sessaoID);
		List<String> timeLine = new LinkedList<String>();
		timeLine.addAll(getPerfilMusical(usuarioLogados.get(sessaoID)));
		Iterator<String> i = minhaLista.iterator();
		while (i.hasNext()) {
			String amigoID = i.next();
			Usuario user = storage.getUser(amigoID);
			timeLine.addAll(user.getPerfilMusical());
		}
		Collections.sort(timeLine);
		return timeLine;
	}

	public String getAtributoSom(String idSom, String atributo)
			throws UsuarioNaoCadastradoException, AtributoException,
			SomInexistenteException {
		Musica musica = storage.getMusica(idSom);

		if (musica == null)
			throw new SomInexistenteException("Som inválido");

		if (atributo == null || atributo.equals("")) {
			throw new AtributoException("Atributo inválido");
		} else if (atributo.equals("link")) {
			return musica.getLink();
		} else if (atributo.equals("dataCriacao")) {
			Date date = musica.getDataDeCriacao().getTime();
			return new SimpleDateFormat("dd/MM/yyyy").format(date);
		} else
			throw new AtributoException("Atributo inexistente");

	}

	public String getIDUsuario(String idSessao) {
		return usuarioLogados.get(idSessao);
	}

	public Set<String> getListaDeSeguidores(String idSessao) {
		
		return storage.getUser(usuarioLogados.get(idSessao)).getListaMeusSeguidores();
	}

	/**
	 * TODO: colocar todos os metodos para serem autenticados, ou seja, passar o
	 * sessaoID como parametro para verificar se existe um usuario com essa
	 * sessaoID logado para poder realizar os metodos!
	 * 
	 * TODO: realizar testes nao funcionais. Verificar se o sistema nao faz
	 * aquilo que nao eh para fazer.
	 * 
	 * TODO:
	 */

}