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

	public List<String> getPerfilMusical(String sessaoID) throws SessaoIDException, UsuarioNaoCadastradoException
			 {
		
		String meuID = isAutenticado( sessaoID );
		Usuario usr = storage.getUser(meuID);
		
		if (usr == null)
			throw new UsuarioNaoCadastradoException("Usuário inexistente");

		return usr.getPerfilMusical();
	}

	public List<String> getPerfilMusical(String sessaoID, String userID) throws SessaoIDException  {
		
		String meuID = isAutenticado( sessaoID );
		Usuario usrLogado = storage.getUser(meuID);
		if (!usrLogado.getListaMeusSeguidores().contains(userID)) {

		}
		Usuario amigo = storage.getUser(userID);
		;
		return amigo.getPerfilMusical();
	}

	public String postarSom(String sessaoID, String link,
			String dataCriacao) throws SessaoIDException, DataInvalidaException, LinkInvalidoException  {

		String meuID = isAutenticado( sessaoID );

		Musica musica = new Musica(meuID, link, dataCriacao);
		storage.addMusic(musica);
		storage.getUser(meuID).addMusica(musica.getID());
		return musica.getID();
	}

	public void encerrarSessao(String login) throws SessaoIDException {
		for (String u : usuarioLogados.keySet()) {
			if (storage.getUser(usuarioLogados.get(u)).getLogin().equals(login)) {
				usuarioLogados.remove(u);
				break;
			}
		}
	}

	public Set<String> getListaSeguindo(String sessaoID) throws SessaoIDException {
		
		String meuID = isAutenticado( sessaoID );
		return storage.getUser(meuID).getSeguindo();
	}

	public void seguirUsuario(String sessaoID, String loginFollowed) throws SessaoIDException {
		
		String meuID = isAutenticado( sessaoID );
		String userIDSeguido = storage.getUserID(loginFollowed);
		storage.getUser(meuID).seguir(userIDSeguido);
		storage.getUser(userIDSeguido).addMeuSeguidor(meuID);
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
			throws SessaoIDException, UsuarioNaoCadastradoException {
		
		String meuID = isAutenticado( sessaoID );
		Set<String> minhaLista = getListaSeguindo(sessaoID);
		List<String> timeLine = new LinkedList<String>();
		timeLine.addAll(getPerfilMusical(meuID));
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

	/** Pega a lista de seguidores de um usuario
	 * 
	 * @param idSessao
	 * 		Id do usuario que se deseja a lista de seguidores.
	 * @return
	 * 		Lista de seguidores.
	 * @throws Exception 
	 */
	
	public Set<String> getListaDeSeguidores(String sessaoID) throws Exception {
		
		String meuID = isAutenticado( sessaoID );
		return storage.getUser(meuID).getListaMeusSeguidores();
	}
	
	/** Adiciona um post favorito
	 * 
	 * @param idSessao
	 * 		Id do usuario que adicionou o post.
	 * @param idMusica
	 * 		Id do post.
	 * @throws SessaoIDException 
	 */
	
	public void addFavorito(String sessaoID, String idMusica) throws SessaoIDException{
		
		String meuID = isAutenticado( sessaoID );
		storage.getUser(meuID).addFavorito(idMusica);
		storage.getMusica(idMusica).addFavoritado(meuID);
		
		// Aqui ele adiciona o post favoritado a todos os que o seguem.
		
		for (String seguidor : storage.getUser(meuID).getListaMeusSeguidores()) {
			storage.getUser(seguidor).addFeedExtra(idMusica, meuID);
		}
	}
	
	/** Pega os favoritos de um usuario.
	 * 
	 * @param idSessao
	 * 		Id do usuario desejado
	 * @return
	 * 		Lista dos favoritos.
	 * @throws SessaoIDException 
	 */
	
	public List<String> getFavoritos(String sessaoID) throws SessaoIDException{
		
		String meuID = isAutenticado( sessaoID );
		return storage.getUser(meuID).getFavoritos();
	}
	
	
	/** Retorna o feed extra deste usuario(os favoritos dos meu seguidos)
	 * 
	 * @param idSessao
	 * 		Id do usuario desejado.
	 * @return
	 * 		Lista do Feed extra.
	 */
	
	public List<Favorito> getFeedExtra(String idSessao){
		return 	storage.getUser(usuarioLogados.get(idSessao)).getFeedExtra();
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
	
	private String isAutenticado( String sessaoID ) throws SessaoIDException {
		
		if ( sessaoID == null || sessaoID.equals("") )
			throw new SessaoIDException("Sessão inválida");
			
		String retorno = usuarioLogados.get(sessaoID);
		if ( retorno == null )
			throw new SessaoIDException("Sessão inexistente");
			
		return retorno;
	}

	private boolean hasPermicao( String sessaoID, String amigoID ) throws SessaoIDException {
		
		String meuID = isAutenticado( sessaoID );
		
		return storage.getUser(meuID).hasAmigo(amigoID);
		
	}

}