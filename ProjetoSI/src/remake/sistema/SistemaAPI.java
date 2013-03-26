package remake.sistema;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.edu.ufcg.rickroll.rickroll.Favorito;

import remake.entidades.Musica;
import remake.entidades.Usuario;
import remake.excecao.*;
import remake.regras.*;

public class SistemaAPI {

	CentralDeDados centralDeDados;
	Map<String, String> usuarioLogados;

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
			;
		// TODO: execao?

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
		// TODO: fazer metodo da fachada equivalente a encerrar sessao com o
		// login!
		isAutenticado(sessaoID);
		usuarioLogados.remove(sessaoID);
	}

	/**
	 * Verifica se a sessaoID é valida
	 * 
	 * @param sessaoID
	 *            -> sessaoID a ser verificada
	 * @return true - exite sessaoID false - nao existe sessaoID
	 * @throws SessaoIDException
	 *             sessaoID é invalida
	 */
	private String isAutenticado(String sessaoID) throws SessaoIDException {

		if (sessaoID == null || sessaoID.equals(""))
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
	private boolean hasPermicao(String sessaoID, String amigoID)
			throws SessaoIDException {

		String meuID = isAutenticado(sessaoID);

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

		isAutenticado(sessaoID);
		if (!hasPermicao(sessaoID, userID)) {
			// TODO: codigo que laca exeçao
		}
		Usuario amigo = centralDeDados.getUser(userID);

		return amigo.getPerfilMusical();
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
		String dataCriacao = SimpleDateFormat("dd/MM/yyyy").format( data );
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

		String meuID = isAutenticado(sessaoID);

		Musica musica = new Musica(meuID, link, dataCriacao);
		centralDeDados.addMusic(musica);
		centralDeDados.getUser(meuID).addMusica(musica.getID());

		// Aqui ele adiciona o post criado na main feed dos seguidos.

		for (String seguidor : centralDeDados.getUser(meuID)
				.getListaMeusSeguidores()) {
			centralDeDados.getUser(seguidor).addMainFeed(musica.getID());
		}
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

		String meuID = isAutenticado(sessaoID);
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

		String meuID = isAutenticado(sessaoID);
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

		String meuID = isAutenticado(sessaoID);
		return centralDeDados.getUser(meuID).getListaMeusSeguidores();
	}

	public void addFavorito(String sessaoID, String musicaID)
			throws SessaoIDException, SomInexistenteException, LoginException,
			UsuarioNaoCadastradoException {

		if (!Verificador.verificaStringValida(musicaID))
			throw new SomInexistenteException("Som inválido");

		else if (centralDeDados.getMusica(musicaID) == null)
			throw new SomInexistenteException("Som inexistente");

		String meuID = isAutenticado(sessaoID);
		Usuario usuario = getUsuarioByLogin(meuID);
		usuario.addFavorito(musicaID);
		// storage.getUser(meuID).addFavorito(idMusica);

		Musica musica = getMusica(musicaID);
		musica.addFavoritado(meuID);
		// storage.getMusica(musicaID).addFavoritado(meuID);

		usuario.addNumeroDeFavoritos(musica.getIDCriador());
		// storage.getUser(meuID).addNumeroDeFavoritos(storage.getMusica(musicaID).getIDCriador());

		// Aqui ele adiciona o post favoritado a todos os que o seguem.

		for (String seguidor : centralDeDados.getUser(meuID)
				.getListaMeusSeguidores()) {
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

		String meuID = isAutenticado(sessaoID);
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
		Usuario user = centralDeDados.getUser(isAutenticado(idSessao));
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
	public void setRegraDeComposicao(String sessaoID, OrdenadorRegra comparador)
			throws SessaoIDException, RegraDeComposicaoException {

		String meuID = isAutenticado(sessaoID);
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
		String meuID = isAutenticado(sessaoID);
		Usuario usuario = centralDeDados.getUser(meuID);

		return usuario.getMainFeed();
	}

}
