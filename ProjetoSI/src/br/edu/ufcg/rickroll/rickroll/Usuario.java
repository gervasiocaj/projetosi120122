package br.edu.ufcg.rickroll.rickroll;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Usuario {

	private String login;
	private String senha;
	private String nome;
	private String email;

	private List<Musica> perfilMusical;
	private Set<String> listaAmigos;
	private List<String> listaDeSolicitacoesDeAmizade;
	private List<String> listaDeSolicitacoesDeAmizadePendente;

	/**
	 * Cria um novo usuario.
	 * 
	 * @param login
	 * 		Login do usuario.
	 * @param senha
	 * 		Senha do usuario.
	 * @param nome
	 * 		Nome do usuario.
	 * @param email
	 * 		Email do usuario.
	 */

	public Usuario(String login, String senha, String nome, String email) {
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
		perfilMusical = new LinkedList<Musica>();
		listaAmigos = new HashSet<String>();
		listaDeSolicitacoesDeAmizade = new LinkedList<String>();
		listaDeSolicitacoesDeAmizadePendente = new LinkedList<String>();
	}

	/**
	 * Retorna o login do usuario.
	 * 
	 * @return
	 * 		Login do usuario.
	 */

	public String getLogin() {
		return login;
	}

	/**
	 * Retorna a senha do usuario.
	 * 
	 * @return
	 * 		Senha do usuario.
	 */

	public String getSenha() {
		return senha;
	}

	/**
	 * Altera a senha do usuario.
	 * 
	 * @param senha
	 * 		Nova senha desejada.
	 */

	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * Retorna o nome do usuario.
	 * 
	 * @return
	 * 		Nome do usuario.
	 */

	public String getNome() {
		return nome;
	}

	/**
	 * Altera o nome do usuario.
	 * 
	 * @param nome
	 * 		Novo nome desejado.
	 */

	public void setName(String nome) {
		this.nome = nome;
	}

	/**
	 * Returna o email do usuario.
	 * 
	 * @return
	 * 		Email do usuario.
	 */

	public String getEmail() {
		return email;
	}

	/**
	 * Altera o email do usuario.
	 * 
	 * @param email
	 * 		Novo email desejado.
	 */

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returna o id do usuario.
	 * 
	 * @return
	 * 		Id do usuario.
	 */

	public String getId(){
		return login;
	}

	/**
	 * Adiciona uma musica ao perfil musical do usuario.
	 * 
	 * @param musica
	 * 		Musica que se deseja adicionar.
	 */

	public void addMusica(Musica musica){
		perfilMusical.add(0, musica);
}

	/**
	 * Retorna o perfil musical do usuario.
	 * 
	 * @return
	 * 		Lista de musicas.
	 */

	public List<Musica> getPerfilMusical(){
		return perfilMusical;
	}

	public Set<String> getListaAmigos() {
		return listaAmigos;
	}

	public void addSolicitacaoDeAmizade(String userID) {
		listaDeSolicitacoesDeAmizade.add(userID);
	}

	public void addSolicitacaoDeAmizadePendente(String userID) {
		listaDeSolicitacoesDeAmizadePendente.add(userID);
	}

	public List<String> getMinhasSolicitacoes() {
		return listaDeSolicitacoesDeAmizade;
	}

	public List<String> getMinhasSolicitacoesPendentes() {
		return listaDeSolicitacoesDeAmizadePendente;
	}

	public boolean temSolicitacaoDoAmigo(String userID) {
		return listaDeSolicitacoesDeAmizadePendente.contains(userID);
	}

	public void removeSolicitacaoDeAmizade(String userID) {
		listaDeSolicitacoesDeAmizade.remove(userID);
	}

	public void removeSolicitacaoDeAmizadePendente(String userID) {
		listaDeSolicitacoesDeAmizadePendente.remove(userID);
	}

	public void addAmigo(String userID) {
		listaAmigos.add(userID);
	}

}