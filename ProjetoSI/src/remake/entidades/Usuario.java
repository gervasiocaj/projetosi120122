package remake.entidades;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.*;

import remake.excecao.*;
import remake.regras.*;
import remake.sistema.Verificador;
import remake.util.Favorito;

public class Usuario implements Serializable{

	private static final long serialVersionUID = 7443373461713020044L;
	private String login;
	private String senha;
	private String nome;
	private String email;
	private Comparator<String> regraDeComposicao;
	private final String id;

	private List<String> perfilMusical;
	private Set<String> listaMyFollowers;
	private Set<String> listaFollowing;
	private List<String> tags;
	private List<String> listaFavorite;
	private List<Favorito> feedExtra;
	private List<String> mainFeed;
	private Map<String, Integer> ordemSeguidor;
	private Map<String, Integer> numeroDeFavoritos;
	private int ordem;
	private int vezesFavoritado;
	private Map<String, List<String>> listasPersonilizadas;
	
	/**
	 * Cria um novo usuario.
	 * 
	 * @param login
	 *            Login do usuario.
	 * @param senha
	 *            Senha do usuario.
	 * @param nome
	 *            Nome do usuario.
	 * @param email
	 *            Email do usuario.
	 */

	public Usuario(String login, String senha, String nome, String email)
			throws AtributoException {
		if (Verificador.verificaStringValida(login))
			this.login = login;
		else
			throw new AtributoException("Login inválido");

		if (Verificador.verificaStringValida(senha))
			this.senha = senha;
		else
			throw new AtributoException("Senha inválida");

		if (Verificador.verificaStringValida(nome))
			this.nome = nome;
		else
			throw new AtributoException("Nome inválido");

		if (Verificador.verificaStringValida(email))
			this.email = email;
		else
			throw new AtributoException("Email inválido");
		
		this.id = login + ";" + UUID.randomUUID();
		regraDeComposicao = new OrdenadorRegraTempo();
		perfilMusical = new LinkedList<String>();
		listaMyFollowers = new TreeSet<String>();
		listaFollowing = new LinkedHashSet<String>();
		listaFavorite = new LinkedList<String>();
		feedExtra = new LinkedList<Favorito>();
		mainFeed = new LinkedList<String>();
		tags = new LinkedList<String>();
		numeroDeFavoritos = new HashMap<String, Integer>();
		ordemSeguidor = new HashMap<String, Integer>();
		ordem = 1;
		listasPersonilizadas = new HashMap<String, List<String>>();
		vezesFavoritado = 0;
	}

	/**
	 * Retorna o login do usuario.
	 * 
	 * @return Login do usuario.
	 */

	public String getLogin() {
		return login;
	}

	/**
	 * Retorna a senha do usuario.
	 * 
	 * @return Senha do usuario.
	 */

	public String getSenha() {
		return senha;
	}

	/**
	 * Altera a senha do usuario.
	 * 
	 * @param senha
	 *            Nova senha desejada.
	 */

	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * Retorna o nome do usuario.
	 * 
	 * @return Nome do usuario.
	 */

	public String getNome() {
		return nome;
	}

	/**
	 * Altera o nome do usuario.
	 * 
	 * @param nome
	 *            Novo nome desejado.
	 */

	public void setName(String nome) {
		this.nome = nome;
	}

	/**
	 * Returna o email do usuario.
	 * 
	 * @return Email do usuario.
	 */

	public String getEmail() {
		return email;
	}

	/**
	 * Altera o email do usuario.
	 * 
	 * @param email
	 *            Novo email desejado.
	 */

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returna o id do usuario.
	 * 
	 * @return Id do usuario.
	 */

	public String getID() {
		return this.id;
	}

	/**
	 * Adiciona uma musica ao perfil musical do usuario.
	 * 
	 * @param musica
	 *            Musica que se deseja adicionar.
	 */

	public void addMusica(String musicaID) throws LinkInvalidoException,
			DataInvalidaException {
		perfilMusical.add(0, musicaID);
	}

	/**
	 * Retorna o perfil musical do usuario.
	 * 
	 * @return Lista de musicas.
	 */

	public List<String> getPerfilMusical() {
		return perfilMusical;
	}

	/**
	 * Retorna um conjunto com todos os seguidores deste Usuario
	 * 
	 * @return Os usuarios que seguem este Usuario
	 */
	public Set<String> getListaMeusSeguidores() {
		return listaMyFollowers;
	}

	/**
	 * Adiciona um seguidor para este usuario
	 * 
	 * @param userID
	 *            O ID do usuario que ira seguir este usuario
	 */
	public void addMeuSeguidor(String userID) {
		listaMyFollowers.add(userID);
	}

	/**
	 * Retorna a lista de todos os usuarios que eu sigo
	 * 
	 * @return Os usuarios que eu sigo
	 */
	public Set<String> getSeguindo() {
		return listaFollowing;
	}

	/**
	 * Segue um determinado usuario
	 * 
	 * @param userID
	 *            O ID do usuario que este usuario ira seguir
	 */
	public void seguir(String userID) {
		((LinkedHashSet<String>)listaFollowing).add(userID);
		//TODO add na lista my followers
	}
	
	/** Adiciona um post favorito
	 * 
	 * @param idMusica
	 * 		Id do post a ser favoritado
	 */
	
	public void addFavorito(String idMusica){
		listaFavorite.add(0, idMusica);
	}

	/** Pega os posts favoritos do usuario
	 * 
	 * @return
	 * 		Lista de posts favoritos
	 */
	
	public List<String> getFavoritos() {
		return listaFavorite;
	}

	
	/** Adiciona um post a feed extra do usuario (lista com os posts favoritados dos meus seguidos)
	 * 
	 * @param idMusica
	 * 		Id do post favoritado pelo meu seguido.
	 */
		
	public void addFeedExtra(String idMusica, String idSeguido) {
		feedExtra.add(0, new Favorito(idMusica, idSeguido));
	}
	
	/** Retorna a feed extra contendo os posts favoritados de meus seguidos.
	 * 
	 * @return
	 * 		A feed extra
	 */

	public List<Favorito> getFeedExtra() {
		return feedExtra;
	}

	/** Vefirica se possui o seguido
	 * 
	 * @param amigoID
	 * 		Id do amigo
	 * @return
	 * 		true caso possua o amigo, false caso contrario
	 */
	
	public boolean hasAmigo( String amigoID ){
		return listaFollowing.contains(amigoID);
	}
	
	/** Adiciona um post ao main feed
	 * 
	 * @param idMusica
	 * 		id do post
	 */
	
	public void addMainFeed(String idMusica){
		mainFeed.add(0, idMusica); // essa é a mainFeed default
	}
	
	
	/** Muda a regra de composicao da main feed
	 * 
	 * @param regra
	 * 		nova regra
	 */
	
	public void setRegraDeComposicao(Comparator<String> comparador){
		regraDeComposicao = comparador;
	}
	
	/** Pega a regra de composicao corrente
	 * 
	 * @return
	 * 		A regra
	 */
	
	public Comparator<String> getRegraDeComposicao(){
		return regraDeComposicao;
	}
	
	/** Retorna a main feed do usuario
	 * 
	 * @return
	 * 		a main feed
	 */
	
	public List<String> getMainFeed(){
		List<String> copiaDeMainFeed = new LinkedList<String>();
		copiaDeMainFeed.addAll(mainFeed);
		Collections.sort(copiaDeMainFeed, regraDeComposicao);
		return copiaDeMainFeed; 
	}
	
	/** Retorna o numero de vezes que um post meu foi favoritado
	 * 
	 * @return
	 * 		vezes que foi favoritado
	 */
	public int vezesFavoritado(){
		return vezesFavoritado;
	}
	
	/** Adiciona um novo favorito para um usuario
	 * 
	 * @param idUser
	 * 		Id do usuario a adicionar um favorito
	 */
	public void addNumeroDeFavoritos(String idUser){
		if(!numeroDeFavoritos.containsKey(idUser)) numeroDeFavoritos.put(idUser, 1);
		else numeroDeFavoritos.put(idUser, numeroDeFavoritos.get(idUser) + 1);
	}
	
	/** Retorna o numero de vezes que um post do usuario foi favoritado
	 * 
	 * @param idUser
	 * 		Id do usuario que se deseja o numero de favoritos
	 * @return
	 * 		O numero de favoritos
	 */
	public Integer getNumeroDeFavoritos(String idUser){
		return numeroDeFavoritos.get(idUser);
	}
	
	/** Adiciona o ordem em que o usuario seguio outro
	 * 
	 * @param idUser
	 * 		o usuario a ser seguido
	 */
	
	public void addOrdemSeguidor(String idUser){
		ordemSeguidor.put(idUser, ordem);
		ordem++;
	}
	
	/**	Pega a ordem em que dado usuario foi seguido
	 * 
	 * @param idUser
	 * 		id do usuario desejado
	 * @return
	 */
	public Integer getOrdemSeguidor(String idUser){
		return ordemSeguidor.get(idUser);
	}

	public String fazLogin( String senha ) throws LoginException{
		if ( !senha.equals(getSenha()) ) 
			throw new LoginException("Login inválido");
		String sessaoID = getLogin() + Calendar.getInstance().hashCode();
		return sessaoID;
	}
	
	/**	
	 * 
	 * @param nomeDaLista
	 * @throws ListaPersonalizadaException
	 */
	public void criarListaPersonalizada(String nomeDaLista) throws ListaPersonalizadaException{
		if(!listasPersonilizadas.containsKey(nomeDaLista)){
			listasPersonilizadas.put(nomeDaLista, new LinkedList<String>());
		} else {
			throw new ListaPersonalizadaException("Nome já escolhido");
		}
	}
	
	/**	Adiciona um usuario a uma lista personalizada
	 * 
	 * @param nomeDaLista
	 * 		nome da lista personalizada
	 * @param idUser
	 * 		id do usuario a ser adicionado
	 * @throws ListaPersonalizadaException
	 */
	public void adicinarUsuarioALista(String nomeDaLista, String idUser) throws ListaPersonalizadaException{
		if (listasPersonilizadas.containsKey(nomeDaLista)) {
			if (!listasPersonilizadas.get(nomeDaLista).contains(idUser)) {
				if (idUser.equals(id)) {
					throw new ListaPersonalizadaException("Usuário não pode adicionar-se a própria lista");
				}
				listasPersonilizadas.get(nomeDaLista).add(idUser);
			} else throw new ListaPersonalizadaException("Usuário já existe nesta lista");
		} else {
			throw new ListaPersonalizadaException("Lista inexistente");
		}
	}
	
	/** Pega os usuarios de uma determinada lista personalizada
	 * 
	 * @param nomeLista
	 * 		nome da lista personalizada
	 * @return
	 * 		a lista de usuarios
	 * @throws ListaPersonalizadaException
	 */
	public List<String> getListasPersonalizadas(String nomeLista) throws ListaPersonalizadaException{
		if(!listasPersonilizadas.containsKey(nomeLista)) 
			throw new ListaPersonalizadaException("Lista inexistente");
		return listasPersonilizadas.get(nomeLista);
	}
	
	/** Adiciona um favoritado a este usuario
	 * 
	 */
	public void adicionaFavorito() {
		vezesFavoritado++;
	}

	public String addTag(String tag) {
		if (!Verificador.verificaStringValida(tag) || tags.contains(tag))
			throw new InvalidParameterException("Tag inválida");
		tags.add(tag);
		return tag;
	}
	
	public List<String> getTags() {
		return tags;
	}

}