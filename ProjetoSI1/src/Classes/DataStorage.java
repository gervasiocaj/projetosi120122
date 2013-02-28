package Classes;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;




public class DataStorage {


	private Map<String, Usuario> userList ;
	
	/** Instancia um DataStorage
	 *
	 */
	public DataStorage() {

		userList = new TreeMap<String, Usuario>();

	}

	/** Adiciona um usuario ao banco de dados
	 * 
	 * @param user
	 * 		usuario a ser adicionado
	 */
	public void addUsuario(Usuario user){
		userList.put(user.getId(), user);
	}
	

	/** Verifica se o usuario existe no banco de dados
	 * 
	 * @param login
	 * 		login do usuario
	 * @param email
	 * 		email do usuario
	 * @return
	 * 		true caso exista, false caso contrario
	 */
	public boolean hasUser(String login, String email) {
		Set keySet = userList.keySet();
		Iterator<String> i = keySet.iterator();
		while ( i.hasNext() ) {
			String key = i.next();
			Usuario user = userList.get(key);
			if ( user.getLogin() == login || user.getEmail() == email ) {
				return true;
			}
		}
		return false;
	}
	
	/** Pega o id do usuario
	 * 
	 * @param login
	 * 		login do usuario
	 * @return
	 * 		id do usuario
	 */

	public String getUserID(String login) {
		Set keySet = userList.keySet();
		Iterator<String> i = keySet.iterator();
		while ( i.hasNext() ) {
			String key = i.next();
			Usuario user = userList.get(key);
			if ( user.getLogin() == login ) {
				return user.getId();
			}
		}
		return null;
	}
	
	/** Pega um Usuario especifico
	 * 
	 * @param userID
	 * 		id do usuario
	 * @return
	 * 		o Usuario
	 */

	public Usuario getUser(String userID) {
		return userList.get(userID);
	}

	/**
	public Usuario getUsuarioDesejado(String login) throws EntradaInvalidaException{
		for (Usuario user : users) {
			if(user.getLogin() == login){
				return user;
			}
		throw new EntradaInvalidaException("Login inválido");
	}
	**/
}