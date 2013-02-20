package Classes;
import java.util.LinkedList;
import java.util.List;

public class User {
	
	private String login;
	private String senha;
	private String nome;
	private String email;

	private List<Musica> perfilMusical;
	
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
	
	public User(String login, String senha, String nome, String email) {
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
		perfilMusical = new LinkedList<Musica>();
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
		perfilMusical.add(musica);
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

}