package br.edu.ufcg.rickroll.rickroll;

import br.edu.ufcg.rickroll.exceptions.*;

import java.util.*;

public class Usuario {

	private String login;
	private String senha;
	private String nome;
	private String email;
	private final String id;

	private List<String> perfilMusical;
	private Set<String> listaMyFollowers;
	private Set<String> listaFollowing;
	private List<String> listaFavorite;
	private List<Favorito> feedExtra;
	
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
		perfilMusical = new LinkedList<String>();
		listaMyFollowers = new TreeSet<String>();
		listaFollowing = new LinkedHashSet<String>();
		listaFavorite = new LinkedList<String>();
		feedExtra = new LinkedList<Favorito>();
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

	public String getId() {
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
	

}