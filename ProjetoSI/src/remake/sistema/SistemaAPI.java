package remake.sistema;

import java.text.SimpleDateFormat;
import java.util.*;

import remake.excecao.*;
import remake.entidades.Musica;
import remake.entidades.Usuario;
import remake.regras.*;
import remake.util.Favorito;

public class SistemaAPI {

	CentralDeDados centralDeDados;
	Map<String, String> usuarioLogados;

	private enum Regras {

		REGRA1("PRIMEIRO OS SONS POSTADOS MAIS RECENTEMENTE PELAS FONTES DE SONS"),
		REGRA2("PRIMEIRO OS SONS COM MAIS FAVORITOS"),
		REGRA3("PRIMEIRO SONS DE FONTES DAS QUAIS FAVORITEI SONS NO PASSADO");

		private String regra;

		Regras(String regra) {
			this.regra = regra;
		}

		public String getRegra() {
			return this.regra;
		}
	}

	public SistemaAPI() {
		usuarioLogados = new HashMap<String, String>();
		centralDeDados = CentralDeDados.getInstance();
	}

	/**
	 * Criação de novo usuario!
	 * 
	 * @param login
	 *            -> login do usuario
	 * @param senha
	 *            -> senha do usuario
	 * @param nome
	 *            -> nome do usuario
	 * @param email
	 *            -> email do usuario
	 * @throws AtributoException
	 *             -> exceção lancada caso algum dos atributos sejam invalidos
	 * @throws CriacaoUserException
	 */
	public void criaNovoUsuario(String login, String senha, String nome,
			String email) throws AtributoException, CriacaoUserException {

		if (centralDeDados.hasUsuarioLogin(login))
			throw new CriacaoUserException(
					"Já existe um usuário com este login");
		else if (centralDeDados.hasUserEmail(email))
			throw new CriacaoUserException(
					"Já existe um usuário com este email");

		Usuario usuario = new Usuario(login, senha, nome, email);
		centralDeDados.addUsuario(usuario);
	}

	/**
	 * Busca por usuarios
	 * 
	 * @param usuarioLogin
	 *            -> login do usuario
	 * @return -> retorna o usuario de login requisitado
	 * @throws UsuarioNaoCadastradoException
	 *             -> usuario inexistente
	 * @throws LoginException
	 *             -> login invalido
	 */
	public String getUsuarioID(String usuarioLogin)
			throws UsuarioNaoCadastradoException, LoginException {

		if (!Verificador.verificaStringValida(usuarioLogin))
			throw new LoginException("Login inválido");

		String usuarioID = centralDeDados.getUsuarioID(usuarioLogin);
		if (!Verificador.verificaStringValida(usuarioID))
			throw new UsuarioNaoCadastradoException("Usuário inexistente");

		return usuarioID;
	}

	/**
	 * Retorna o usuario requisitado
	 * 
	 * @param login
	 *            -> login do usuario requisitado
	 * @return Usuario correspondete ao atributo login
	 * @throws LoginException
	 *             login invalido
	 * @throws UsuarioNaoCadastradoException
	 *             Usuario inesistente
	 */
	public Usuario getUsuarioByLogin(String login) throws LoginException,
			UsuarioNaoCadastradoException {

		String usuarioID = getUsuarioID(login);

		return centralDeDados.getUser(usuarioID);
	}

	/**
	 * Retorna o usuario requisitado
	 * 
	 * @param sessaoID
	 *            -> sessaoID do usuario requisitado
	 * @return Usuario correspondete ao atributo login
	 * @throws LoginException
	 *             login invalido
	 * @throws UsuarioNaoCadastradoException
	 *             Usuario inesistente
	 */
	public Usuario getUsuario(String sessaoID) throws LoginException,
			UsuarioNaoCadastradoException {
		String usuarioID = usuarioLogados.get(sessaoID);
		return centralDeDados.getUser(usuarioID);
	}

	/**
	 * 
	 * @param userID
	 * @return Usuario correspondete ao ID passado
	 */
	public Usuario getUsuarioByID(String userID) {
		return centralDeDados.getUser(userID);
	}

	/**
	 * Retorna musica requisitada
	 * 
	 * @param somID
	 *            -> id da musica
	 * @return musica
	 * @throws SomInexistenteException
	 *             musica inexistente
	 */
	public Musica getMusica(String somID) throws SomInexistenteException {

		if (!Verificador.verificaStringValida(somID))
			throw new SomInexistenteException("Som inválido");

		Musica musica = centralDeDados.getMusica(somID);
		if (musica == null)
			throw new SomInexistenteException("Som inválido");

		return musica;
	}

	/**
	 * Abre nova sessao com usuario
	 * 
	 * @param login
	 *            -> Login do usuario a ser logado
	 * @param senha
	 *            -> Senha do usuario a ser logado
	 * @return SessaoID
	 * @throws LoginException
	 *             Login invalido
	 * @throws UsuarioNaoCadastradoException
	 *             usuario nao cadastrado
	 */
	public String abrirSessao(String login, String senha)
			throws LoginException, UsuarioNaoCadastradoException {

		Usuario usuario = getUsuarioByLogin(login);
		String sessaoID = usuario.fazLogin(senha);// login +
													// Calendar.getInstance().hashCode();
		usuarioLogados.put(sessaoID, usuario.getID());

		return sessaoID;
	}

	/**
	 * finaliza a sessao do usuario
	 * 
	 * @param sessaoID
	 *            -> sessaoID do usuario logado
	 * @throws SessaoIDException
	 */
	public void encerrarSessao(String sessaoID) throws SessaoIDException {
		usuarioLogados.remove(sessaoID);
	}

	/**
	 * Verifica se a sessaoID é valida
	 * 
	 * @param sessaoID
	 *            -> sessaoID a ser verificada
	 * @return ID do Usuario
	 * @throws SessaoIDException
	 *             sessaoID é invalida
	 */
	private String getUserID(String sessaoID) throws SessaoIDException {
		if (!Verificador.verificaStringValida(sessaoID))
			throw new SessaoIDException("Sessão inválida");

		String retorno = usuarioLogados.get(sessaoID);
		if (retorno == null)
			throw new SessaoIDException("Sessão inexistente");

		return retorno;
	}

	/**
	 * Verifica se o usuario logado tem permissao para visualizar postagens de
	 * um outro usuario
	 * 
	 * @param sessaoID
	 *            -> sessaoID do usuario logado
	 * @param amigoID
	 *            -> ID do usuario que deseja verificar se tem permissao para
	 *            ver postagens
	 * @return true - caso haja permissao ( usuario logado segue o outro usuario
	 *         ) false - caso não haja permissao
	 * @throws SessaoIDException
	 *             sessaoID invalido
	 */
	private boolean hasPermissao(String sessaoID, String amigoID)
			throws SessaoIDException {

		String meuID = getUserID(sessaoID);

		return centralDeDados.getUser(meuID).hasAmigo(amigoID);

	}

	/**
	 * Perfil musicao do usuario
	 * 
	 * @param sessaoID
	 *            -> sessaoID do usuario logado
	 * @return
	 * @throws SessaoIDException
	 * @throws UsuarioNaoCadastradoException
	 */
	public List<String> getPerfilMusical(String sessaoID)
			throws SessaoIDException, UsuarioNaoCadastradoException {

		String usuarioID = usuarioLogados.get(sessaoID);

		return getPerfilMusical(sessaoID, usuarioID);
	}

	/**
	 * Perfil musicao do usuario requisitado
	 * 
	 * @param sessaoID
	 * @param userID
	 * @return
	 * @throws SessaoIDException
	 */
	public List<String> getPerfilMusical(String sessaoID, String userID)
			throws SessaoIDException {

		getUserID(sessaoID);
		if (!hasPermissao(sessaoID, userID)) {
			// TODO: codigo que lança exceçao
		}
		Usuario amigo = centralDeDados.getUser(userID);

		return amigo.getPerfilMusical();
	}

	public List<String> getVisaoDosSons(String sessaoID)
			throws SessaoIDException {
		List<String> sons = new LinkedList<String>();
		String meuID = getUserID(sessaoID);
		for (String seguido : centralDeDados.getUser(meuID).getSeguindo()) {
			sons.addAll(centralDeDados.getUser(seguido).getPerfilMusical());
		}
		Collections.sort(sons, new OrdenadorRegraTempo());
		return sons;
	}

	/**
	 * Realiza a postagem de um som
	 * 
	 * @param sessaoID
	 *            -> sessaoID do usuario que deseja postar o novo som
	 * @param link
	 *            -> link do som
	 * @return somID
	 * @throws SessaoIDException
	 *             sessao invalida
	 * @throws DataInvalidaException
	 *             data invalida
	 * @throws LinkInvalidoException
	 *             link invalido
	 */
	public String postarSom(String sessaoID, String link)
			throws SessaoIDException, DataInvalidaException,
			LinkInvalidoException {

		Calendar dataAtual = Calendar.getInstance();
		Date data = dataAtual.getTime();
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		String dataCriacao = formatador.format(data);
		return postarSom(sessaoID, link, dataCriacao);
	}

	/**
	 * Realiza a postagem de um som
	 * 
	 * @param sessaoID
	 *            -> sessaoID do usuario que deseja postar o novo som
	 * @param link
	 *            -> link do som
	 * @param dataCriacao
	 *            -> data de criação
	 * @return somID
	 * @throws SessaoIDException
	 *             sessao invalida
	 * @throws DataInvalidaException
	 *             data invalida
	 * @throws LinkInvalidoException
	 *             link invalido
	 */
	public String postarSom(String sessaoID, String link, String dataCriacao)
			throws SessaoIDException, DataInvalidaException,
			LinkInvalidoException {

		String meuID = getUserID(sessaoID);
		Usuario usuarioAtual = centralDeDados.getUser(meuID);

		Musica musica = new Musica(meuID, link, dataCriacao);
		centralDeDados.addMusic(musica);
		usuarioAtual.addMusica(musica.getID());

		// Aqui ele adiciona o post criado na main feed dos seguidos.

		for (String seguidor : usuarioAtual.getListaMeusSeguidores())
			centralDeDados.getUser(seguidor).addMainFeed(musica.getID());

		return musica.getID();
	}

	/**
	 * Retorna a lista de segidores
	 * 
	 * @param sessaoID
	 *            -> sessaoID do usuario logado
	 * @return lista de segidores do usuario logado
	 * @throws SessaoIDException
	 *             sessao invalida
	 */
	public Set<String> getListaSeguindo(String sessaoID)
			throws SessaoIDException {

		String meuID = getUserID(sessaoID);
		return centralDeDados.getUser(meuID).getSeguindo();
	}

	/**
	 * Seguir usuario
	 * 
	 * @param sessaoID
	 *            -> sessaoID do usuario logado
	 * @param loginFollowed
	 *            -> login do usuario a ser seguido
	 * @throws SessaoIDException
	 *             -> sessapID invalido
	 * @throws LoginException
	 *             -> login invalido
	 */
	public void seguirUsuario(String sessaoID, String loginFollowed)
			throws SessaoIDException, LoginException {

		if (loginFollowed == null || loginFollowed.equals(""))
			throw new LoginException("Login inválido");

		String meuID = getUserID(sessaoID);
		String userIDSeguido = centralDeDados.getUsuarioID(loginFollowed);

		if (userIDSeguido == null)
			throw new LoginException("Login inexistente");
		if (meuID.equals(userIDSeguido))
			throw new LoginException("Login inválido");

		centralDeDados.getUser(meuID).seguir(userIDSeguido);
		centralDeDados.getUser(userIDSeguido).addMeuSeguidor(meuID);
		centralDeDados.getUser(meuID).addOrdemSeguidor(userIDSeguido);
	}

	/**
	 * Retorna a lista de seguidores do usuario logado
	 * 
	 * @param sessaoID
	 *            -> sessaoID do usario logado
	 * @return lista de seguidores
	 * @throws SessaoIDException
	 *             sessaoID invalida
	 */
	public Set<String> getListaDeSeguidores(String sessaoID)
			throws SessaoIDException {

		String meuID = getUserID(sessaoID);
		return centralDeDados.getUser(meuID).getListaMeusSeguidores();
	}

	public void addFavorito(String sessaoID, String musicaID)
			throws SessaoIDException, SomInexistenteException, LoginException,
			UsuarioNaoCadastradoException {

		if (!Verificador.verificaStringValida(musicaID))
			throw new SomInexistenteException("Som inválido");

		else if (centralDeDados.getMusica(musicaID) == null)
			throw new SomInexistenteException("Som inexistente");

		String meuID = getUserID(sessaoID);

		Usuario usuarioAtual = centralDeDados.getUser(meuID);
		Musica musicaAtual = centralDeDados.getMusica(musicaID);

		usuarioAtual.addFavorito(musicaID);
		musicaAtual.addFavoritado(meuID);

		usuarioAtual.addNumeroDeFavoritos(musicaAtual.getIDCriador());
		centralDeDados.getUser(
				centralDeDados.getMusica(musicaID).getIDCriador())
				.adicionaFavorito();

		// Aqui ele adiciona o post favoritado a todos os que o seguem.

		for (String seguidor : usuarioAtual.getListaMeusSeguidores()) {
			centralDeDados.getUser(seguidor).addFeedExtra(musicaID, meuID);
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

		String meuID = getUserID(sessaoID);
		return centralDeDados.getUser(meuID).getFavoritos();
	}

	/**
	 * Retorna o feed extra deste usuario(os favoritos dos meu seguidos)
	 * 
	 * @param idSessao
	 *            Id do usuario desejado.
	 * @return Lista do Feed extra.
	 * @throws SessaoIDException
	 */
	public List<Favorito> getFeedExtra(String idSessao)
			throws SessaoIDException {
		Usuario user = centralDeDados.getUser(getUserID(idSessao));
		return user.getFeedExtra();
	}

	// -------------------------------------------------------------------------------------------

	/**
	 * Muda a regra de composicao da feed principal
	 * 
	 * @param sessaoID
	 *            id do usuario que ira alterar a regra
	 * @param regra
	 *            a regra de composicao
	 * @throws SessaoIDException
	 * @throws RegraDeComposicaoException
	 */
	public void setRegraDeComposicao(String sessaoID,
			OrdenadorRegra<String> comparador, String regra)
			throws SessaoIDException, RegraDeComposicaoException {

		String meuID = getUserID(sessaoID);
		verificaRegra(regra);
		centralDeDados.getUser(meuID).setRegraDeComposicao(comparador);

	}

	/**
	 * Retorna o feed principal do usuario
	 * 
	 * @param sessaoID
	 *            ID da sessao do usuario
	 * @return
	 * 
	 * @throws SessaoIDException
	 */

	public List<String> getMainFeed(String sessaoID) throws SessaoIDException {
		String meuID = getUserID(sessaoID);
		Usuario usuario = centralDeDados.getUser(meuID);
		return usuario.getMainFeed();
	}

	// TODO: mudar pacote de fachada para toranar esse metodo protected!
	public void zerarSistema() {
		centralDeDados.zerarSistema();
	}

	/**
	 * Retorna a primeira forma de composição (os sons postados mais
	 * recentemente pelas fontes de som)
	 * 
	 * @return a regra
	 */
	public String getPrimeiraRegraDeComposicao() {
		return new OrdenadorRegraDefault().getRegra();
	}

	/**
	 * Retorna a segunda forma de composição (os sons com mais favoritos)
	 * 
	 * @return a regra
	 */
	public String getSegundaRegraDeComposicao() {
		return new OrdenadorRegraFavoritado().getRegra();
	}

	/**
	 * Retorna a terceira forma de composição (os sons das fontes do qual
	 * favoritei no passado)
	 * 
	 * @return a regra
	 */
	public String getTerceiraRegraDeComposicao() {
		return new OrdenadorRegraMaisFavoritos().getRegra();
	}

	private void verificaRegra(String regra) throws RegraDeComposicaoException {

		if (regra == null || regra.equals(""))
			throw new RegraDeComposicaoException("Regra de composição inválida");

		if (!regra.equals(Regras.REGRA1.getRegra())
				&& !regra.equals(Regras.REGRA2.getRegra())
				&& !regra.equals(Regras.REGRA3.getRegra()))
			throw new RegraDeComposicaoException(
					"Regra de composição inexistente");

	}

	/**
	 * Cria uma lista personalizada
	 * 
	 * @param sessaoID
	 *            id do usuario
	 * @param nomeLista
	 *            nome da lista a ser criada
	 * @throws SessaoIDException
	 * @throws ListaPersonalizadaException
	 */
	public void criaLista(String sessaoID, String nomeLista)
			throws SessaoIDException, ListaPersonalizadaException {
		String meuID = getUserID(sessaoID);
		if (!Verificador.verificaStringValida(nomeLista))
			throw new ListaPersonalizadaException("Nome inválido");
		centralDeDados.getUser(meuID).criarListaPersonalizada(nomeLista);
	}

	/**
	 * Adiciona um usuario a uma lista personalizada
	 * 
	 * @param sessaoID
	 *            id do usuario
	 * @param nomeLista
	 *            nome da lista no qual o usuario sera adicionado
	 * @param userID
	 *            id do usuario a ser adicionado
	 * @throws SessaoIDException
	 * @throws ListaPersonalizadaException
	 * @throws UsuarioNaoCadastradoException
	 */
	public void adicionarUsuarioALista(String sessaoID, String nomeLista,
			String userID) throws SessaoIDException,
			ListaPersonalizadaException {
		String meuID = getUserID(sessaoID);
		if (!Verificador.verificaStringValida(nomeLista))
			throw new ListaPersonalizadaException("Lista inválida");
		centralDeDados.getUser(meuID).adicinarUsuarioALista(nomeLista, userID);
	}

	public List<String> getSonsEmLista(String sessaoID, String nomeLista)
			throws SessaoIDException, ListaPersonalizadaException {
		if (!Verificador.verificaStringValida(nomeLista))
			throw new ListaPersonalizadaException("Lista inválida");

		String meuID = getUserID(sessaoID);
		List<String> lista = new LinkedList<String>();
		for (String user : centralDeDados.getUser(meuID)
				.getListasPersonalizadas(nomeLista))
			lista.addAll(centralDeDados.getUser(user).getPerfilMusical());
		Collections.sort(lista, new OrdenadorRegraTempo());

		return lista;
	}

	/**
	 * Pega o numero de sons favoritos em comum entre dois usuarios
	 * 
	 * @param idSessao
	 *            id do do usuario corrente
	 * @param idUsuario
	 *            id do usuario a se comparar
	 * @return numero de favoritos em comum
	 * @throws SessaoIDException
	 * @throws UsuarioNaoCadastradoException
	 */
	public int getNumFavoritosEmComum(String idSessao, String idUsuario)
			throws SessaoIDException, UsuarioNaoCadastradoException {
		String meuID = getUserID(idSessao);
		if (!Verificador.verificaStringValida(idUsuario))
			throw new UsuarioNaoCadastradoException("Usuário inválido");
		int contador = 0;
		for (String favorito : centralDeDados.getUser(meuID).getFavoritos()) {
			if (centralDeDados.getUser(idUsuario).getFavoritos()
					.contains(favorito))
				contador++;
		}
		return contador;
	}

	/**
	 * Pega o numero de fontes de som em comum entre dois usuarios
	 * 
	 * @param idSessao
	 *            id do do usuario corrente
	 * @param idUsuario
	 *            id do usuario a se comparar
	 * @return o numero de fontes em comum
	 * @throws SessaoIDException
	 * @throws UsuarioNaoCadastradoException
	 */
	public int getNumFontesEmComum(String idSessao, String idUsuario)
			throws SessaoIDException, UsuarioNaoCadastradoException {
		String meuID = getUserID(idSessao);
		if (!Verificador.verificaStringValida(idUsuario))
			throw new UsuarioNaoCadastradoException("Usuário inválido");
		int contador = 0;
		for (String fonte : centralDeDados.getUser(meuID).getSeguindo()) {
			if (centralDeDados.getUser(idUsuario).getSeguindo().contains(fonte))
				contador++;
		}
		return contador;
	}

	/**
	 * Pega as fontes de smo recomendadas para o usuario
	 * 
	 * @param idSessao
	 *            Id do usuario
	 * @return Lista dos usuario recomendados
	 * @throws SessaoIDException
	 */
	public List<String> getFontesDeSonsRecomendadas(String idSessao)
			throws SessaoIDException {
		String meuID = getUserID(idSessao);
		List<String> recomendados = new LinkedList<String>();
		for (String usuario : centralDeDados.getUsuarios()) {
			if (!meuID.equals(usuario)
					&& !centralDeDados.getUser(meuID).getSeguindo()
							.contains(usuario)
					&& (centralDeDados.getUser(usuario).getSeguindo().size() != 0 || centralDeDados
							.getUser(usuario).getFavoritos().size() != 0))
				recomendados.add(usuario);
		}
		Collections.sort(recomendados, new OrdenadorRecomendacoes(
				centralDeDados, meuID));
		return recomendados;
	}

	public List<Usuario> getFollowers(String sessaoID) throws SessaoIDException {
		String meuID = getUserID(sessaoID);
		List<Usuario> result = new LinkedList<Usuario>();
		for (String u : centralDeDados.getUser(meuID).getListaMeusSeguidores())
			result.add(centralDeDados.getUser(u));
		return result;
	}

	public List<Usuario> getFollowing(String sessaoID) throws SessaoIDException {
		String meuID = getUserID(sessaoID);
		List<Usuario> result = new LinkedList<Usuario>();
		for (String u : centralDeDados.getUser(meuID).getSeguindo())
			result.add(centralDeDados.getUser(u));
		return result;
	}

}
