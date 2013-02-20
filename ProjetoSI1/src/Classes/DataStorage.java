package Classes;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;




public class DataStorage {

	
	private Map<String, User> userList ;
	public DataStorage() {
		
		userList = new TreeMap<String, User>();
	}
	
	public void addUsuario(User user){
		userList.put(user.getId(), user);
	}

	public boolean hasUser(String login, String email) {
		Set keySet = userList.keySet();
		Iterator<String> i = keySet.iterator();
		while ( i.hasNext() ) {
			String key = i.next();
			User user = userList.get(key);
			if ( user.getLogin() == login || user.getEmail() == email ) {
				return true;
			}
		}
		return false;
	}
	
	public String getUserID(String login) {
		Set keySet = userList.keySet();
		Iterator<String> i = keySet.iterator();
		while ( i.hasNext() ) {
			String key = i.next();
			User user = userList.get(key);
			if ( user.getLogin() == login ) {
				return user.getId();
			}
		}
		return null;
	}

	public User getUser(String userID) {
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
