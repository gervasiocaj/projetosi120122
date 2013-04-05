package br.edu.ufcg.rickroll.rickroll;

import java.text.SimpleDateFormat;
import java.util.*;

import remake.util.Favorito;

import br.edu.ufcg.rickroll.exceptions.*;

public class RickRoll {
	
	protected enum Regras{
		
		REGRA1("PRIMEIRO OS SONS POSTADOS MAIS RECENTEMENTE PELAS FONTES DE SONS"),
		REGRA2("PRIMEIRO OS SONS COM MAIS FAVORITOS"),
		REGRA3("PRIMEIRO SONS DE FONTES DAS QUAIS FAVORITEI SONS NO PASSADO");
		
		private String regra;
		
		Regras(String regra){
			this.regra = regra;
			}
		
		public String getRegra(){
			return this.regra;
			}
		}

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

		if (!storage.getUser(userID).getSenha().equals(senha))
			throw new LoginException("Login inválido");

		String sessaoID = login + Calendar.getInstance().hashCode();

		usuarioLogados.put(sessaoID, userID);

		return sessaoID;
	}

	public List<String> getPerfilMusical(String sessaoID)
			throws SessaoIDException, UsuarioNaoCadastradoException {

		String meuID = isAutenticado(sessaoID);
		Usuario usr = storage.getUser(meuID);

		if (usr == null)
			throw new UsuarioNaoCadastradoException("Usuário inexistente");

		return usr.getPerfilMusical();
	}

	public List<String> getPerfilMusical(String sessaoID, String userID)
			throws SessaoIDException {

		String meuID = isAutenticado(sessaoID);
		Usuario usrLogado = storage.getUser(meuID);
		if (!usrLogado.getListaMeusSeguidores().contains(userID)) {

		}
		Usuario amigo = storage.getUser(userID);
		;
		return amigo.getPerfilMusical();
	}

	public String postarSom(String sessaoID, String link, String dataCriacao)
			throws SessaoIDException, DataInvalidaException,
			LinkInvalidoException {

		String meuID = isAutenticado(sessaoID);

		Musica musica = new Musica(meuID, link, dataCriacao);
		storage.addMusic(musica);
		storage.getUser(meuID).addMusica(musica.getID());
		
		// Aqui ele adiciona o post criado na main feed dos seguidos.
		
		for (String seguidor : storage.getUser(meuID).getListaMeusSeguidores()) {
			storage.getUser(seguidor).addMainFeed(musica.getID());
		}
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

	public Set<String> getListaSeguindo(String sessaoID)
			throws SessaoIDException {

		String meuID = isAutenticado(sessaoID);
		return storage.getUser(meuID).getSeguindo();
	}

	public void seguirUsuario(String sessaoID, String loginFollowed)
			throws SessaoIDException, LoginException {
		if (loginFollowed == null || loginFollowed.equals(""))
			throw new LoginException("Login inválido");

		String meuID = isAutenticado(sessaoID);
		String userIDSeguido = storage.getUserID(loginFollowed);

		if (userIDSeguido == null)
			throw new LoginException("Login inexistente");
		if (meuID.equals(userIDSeguido))
			throw new LoginException("Login inválido");

		storage.getUser(meuID).seguir(userIDSeguido);
		storage.getUser(userIDSeguido).addMeuSeguidor(meuID);
		storage.getUser(meuID).addOrdemSeguidor(userIDSeguido);
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

	public List<String> getTimeLine(String sessaoID) throws SessaoIDException,
			UsuarioNaoCadastradoException {

		String meuID = isAutenticado(sessaoID);
		Set<String> minhaLista = getListaSeguindo(sessaoID);
		List<String> timeLine = new LinkedList<String>();
		timeLine.addAll(getPerfilMusical(sessaoID));
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

	/**
	 * Pega a lista de seguidores de um usuario
	 * 
	 * @param idSessao
	 *            Id do usuario que se deseja a lista de seguidores.
	 * @return Lista de seguidores.
	 * @throws Exception
	 */

	public Set<String> getListaDeSeguidores(String sessaoID) throws Exception {

		String meuID = isAutenticado(sessaoID);
		return storage.getUser(meuID).getListaMeusSeguidores();
	}

	/**
	 * Adiciona um post favorito
	 * 
	 * @param idSessao
	 *            Id do usuario que adicionou o post.
	 * @param idMusica
	 *            Id do post.
	 * @throws SessaoIDException
	 * @throws SomInexistenteException
	 */

	public void addFavorito(String sessaoID, String idMusica)
			throws SessaoIDException, SomInexistenteException {
		if (idMusica == null || idMusica.equals(""))
			throw new SomInexistenteException("Som inválido");
		
		else if( storage.getMusica(idMusica) == null) throw new SomInexistenteException("Som inexistente");

		String meuID = isAutenticado(sessaoID);
		storage.getUser(meuID).addFavorito(idMusica);
		
		storage.getMusica(idMusica).addFavoritado(meuID);
		
		storage.getUser(meuID).addNumeroDeFavoritos(storage.getMusica(idMusica).getIDCriador());

		// Aqui ele adiciona o post favoritado a todos os que o seguem.

		for (String seguidor : storage.getUser(meuID).getListaMeusSeguidores()) {
			storage.getUser(seguidor).addFeedExtra(idMusica, meuID);
		}
	}

	/**
	 * Pega os favoritos de um usuario.
	 * 
	 * @param idSessao
	 *            Id do usuario desejado
	 * @return Lista dos favoritos.
	 * @throws SessaoIDException
	 */

	public List<String> getFavoritos(String sessaoID) throws SessaoIDException {

		String meuID = isAutenticado(sessaoID);
		return storage.getUser(meuID).getFavoritos();
	}

	/**
	 * Retorna o feed extra deste usuario(os favoritos dos meu seguidos)
	 * 
	 * @param idSessao
	 *            Id do usuario desejado.
	 * @return Lista do Feed extra.
	 * @throws SessaoIDException 
	 */

	public List<Favorito> getFeedExtra(String idSessao) throws SessaoIDException {
		Usuario user = storage.getUser(isAutenticado(idSessao));
		return user.getFeedExtra();
	}
	
	/** Muda a regra de composicao da feed principal
	 * 
	 * @param sessaoID
	 * 		id do usuario que ira alterar a regra
	 * @param regra
	 * 		a regra de composicao
	 * @throws SessaoIDException
	 * @throws RegraDeComposicaoException 
	 */
	public void setRegraDeComposicao(String sessaoID, String regra) throws SessaoIDException, RegraDeComposicaoException{
		String meuID = isAutenticado(sessaoID);
		if(regra == null || regra.equals("")) 
			throw new RegraDeComposicaoException("Regra de composição inválida");
		
		else if(!regra.equals(Regras.REGRA1.getRegra()) && !regra.equals(Regras.REGRA2.getRegra()) 
				&& !regra.equals(Regras.REGRA3.getRegra())) 
				throw new RegraDeComposicaoException("Regra de composição inexistente");
		
		storage.getUser(meuID).setRegraDeComposicao(regra);
	}
	
	/** Retorna a primeira forma de composição (os sons postados mais recentemente pelas fontes de som)
	 * 
	 * @return
	 * 		a regra
	 */
	public String getPrimeiraRegraDeComposicao(){
		return Regras.REGRA1.getRegra();
	}
	
	/** Retorna a segunda forma de composição (os sons com mais favoritos)
	 * 
	 * @return
	 * 		a regra
	 */
	public String getSegundaRegraDeComposicao(){
		return Regras.REGRA2.getRegra();
	}
	
	/** Retorna a terceira forma de composição (os sons das fontes do qual favoritei no passado)
	 * 
	 * @return
	 * 		a regra
	 */
	public String getTerceiraRegraDeComposicao(){
		return Regras.REGRA3.getRegra();
	}
	
	
	/** Retorna o feed principal do usuario
	 * 
	 * @param sessaoID
	 * 		ID da sessao do usuario
	 * @return
	 * 		
	 * @throws SessaoIDException
	 */
	
	public List<String> getMainFeed(String sessaoID) throws SessaoIDException{
		String meuID = isAutenticado(sessaoID);
		if(storage.getUser(meuID).getRegraDeComposicao().equals(Regras.REGRA2.getRegra())){
			// Cria o comparator
			Comparator<String> ordenador = new OrdenadorRegraFavoritado(storage);
			// Cria nova lista
			List<String> newList =  new LinkedList<String>();
			// Clona lista mainFeed
			newList.addAll( storage.getUser(meuID).getMainFeed());
			// Da um sort com o comparator da regra 2
			Collections.sort(newList, ordenador);
			
			return newList;
			
		} else if (storage.getUser(meuID).getRegraDeComposicao().equals(Regras.REGRA3.getRegra())){
			// Cria o comparator
			Comparator<String> ordenador = new OrdenadorRegraMaisFavoritos(storage, meuID);
			// Cria nova lista
			List<String> newList =  new LinkedList<String>();
			// Clona lista mainFeed
			newList.addAll( storage.getUser(meuID).getMainFeed());
			// Da um sort com o comparator da regra 3
			Collections.sort(newList, ordenador);
						
			return newList;
			
		} return storage.getUser(meuID).getMainFeed();
	}

	private String isAutenticado(String sessaoID) throws SessaoIDException {

		if (sessaoID == null || sessaoID.equals(""))
			throw new SessaoIDException("Sessão inválida");

		String retorno = usuarioLogados.get(sessaoID);
		if (retorno == null)
			throw new SessaoIDException("Sessão inexistente");

		return retorno;
	}

	@SuppressWarnings("unused")
	private boolean hasPermicao(String sessaoID, String amigoID)
			throws SessaoIDException {

		String meuID = isAutenticado(sessaoID);

		return storage.getUser(meuID).hasAmigo(amigoID);

	}
	
	public String getUserID(String sessaoID) throws SessaoIDException {
		Usuario u = storage.getUser(isAutenticado(sessaoID));
		return u.getId();
	}
	
	public Musica getMusica(String idMusica) {
		return storage.getMusica(idMusica);
	}
        
        
        public Usuario getUsuario(String userID){
            return storage.getUser(userID);
        }

}