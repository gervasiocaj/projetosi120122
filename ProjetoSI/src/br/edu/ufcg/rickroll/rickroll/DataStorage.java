package br.edu.ufcg.rickroll.rickroll;

import java.util.*;

public class DataStorage {

	private Map<String, Usuario> userList;
	private Map<String, Musica> musicList;

	public DataStorage() {

		userList = new HashMap<String, Usuario>();
		musicList = new HashMap<String, Musica>();

	}

	public void addUsuario(Usuario user) {
		userList.put(user.getId(), user);
	}
	
	public void addMusic( Musica music ){
		musicList.put(music.getID(), music);
	}

	public boolean hasUserEmail(String email) {
		Set<String> keySet = userList.keySet();
		Iterator<String> i = keySet.iterator();
		while (i.hasNext()) {
			String key = i.next();
			if (userList.get(key).getEmail().equals(email))
				return true;
		}
		return false;
	}

	public String getUserID(String login) {
		Set<String> keySet = userList.keySet();
		Iterator<String> i = keySet.iterator();
		while (i.hasNext()) {
			String key = i.next();
			Usuario user = userList.get(key);
			if (user.getLogin().equals(login)) {
				return user.getId();
			}
		}
		return null;
	}

	public Usuario getUser(String userID) {
		return userList.get(userID);
	}

	public Musica getMusica(String somID) {
		return musicList.get(somID);
	}

	/**
	 * public Usuario getUsuarioDesejado(String login) throws
	 * EntradaInvalidaException{ for (Usuario user : users) { if(user.getLogin()
	 * == login){ return user; } throw new
	 * EntradaInvalidaException("Login inv�lido"); }
	 **/
}
