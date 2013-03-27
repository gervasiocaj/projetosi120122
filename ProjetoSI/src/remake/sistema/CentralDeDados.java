package remake.sistema;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import remake.entidades.Musica;
import remake.entidades.Usuario;

public final class CentralDeDados {

	final static CentralDeDados INSTANCE = new CentralDeDados();
	private Map<String, Usuario> listagemDeUsuarios;
	private Map<String, Musica> listagemDeMusicas;

	private CentralDeDados() {

		listagemDeUsuarios = new HashMap<String, Usuario>();
		listagemDeMusicas = new HashMap<String, Musica>();

	}

	public static CentralDeDados getInstance() {
		return INSTANCE;
	}
	
	protected void zerarSistema(){
		listagemDeUsuarios = new HashMap<String, Usuario>();
		listagemDeMusicas = new HashMap<String, Musica>();
	}
	
	/**
	 * Adiciona um novo usuario na listagem de usuarios do sistema
	 * 
	 * @param user
	 *            Objeto usuario a ser cadastrado
	 */
	public void addUsuario(Usuario user) {
		listagemDeUsuarios.put(user.getID(), user);
	}

	/**
	 * Adiciona uma nova musica na listagem de musicas do sistema
	 * 
	 * @param user
	 *            Objeto musica a ser cadastrado
	 */
	public void addMusic(Musica music) {
		listagemDeMusicas.put(music.getID(), music);
	}

	/**
	 * Realiza uma busca na listagem de usuarios a partir do email
	 * 
	 * @param user
	 *            Objeto usuario a ser cadastrado
	 * @return user retorna usuario caso o mesmo tenha sido encontrado ou null
	 *         caso nÃ£o exista um usuario com esse email
	 */
	public boolean hasUserEmail(String email) {
		Set<String> keySet = listagemDeUsuarios.keySet();
		Iterator<String> i = keySet.iterator();
		while (i.hasNext()) {
			String key = i.next();
			if (listagemDeUsuarios.get(key).getEmail().equals(email))
				return true;
		}
		return false;
	}

	/**
	 * Realiza uma busca na listagem de usuarios a partir do login
	 * 
	 * @param user
	 *            Objeto usuario a ser cadastrado
	 * @return user retorna usuario caso o mesmo tenha sido encontrado ou null
	 *         caso nÃ£o exista um usuario com esse login
	 */
	public String getUsuarioID(String login) {
		Set<String> keySet = listagemDeUsuarios.keySet();
		Iterator<String> i = keySet.iterator();
		while (i.hasNext()) {
			String key = i.next();
			Usuario usuario = listagemDeUsuarios.get(key);
			if (usuario.getLogin().equals(login)) {
				return usuario.getID();
			}
		}
		return null;
	}

	public boolean hasUsuarioLogin(String login) {

		String usuarioID = getUsuarioID(login);

		if (usuarioID == null)
			return false;
		return true;
	}

	public Usuario getUser(String userID) {
		return listagemDeUsuarios.get(userID);
	}

	public Musica getMusica(String somID) {
		return listagemDeMusicas.get(somID);
	}
}
