package Classes;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import Exceptions.AtributoInvalidoException;
import Exceptions.LoginException;
import Exceptions.UsuarioNaoCadastradoException;



public class System {

	
	DataStorage storage = new DataStorage();
	
	public void criaNovoUsuario(String login, String senha, String nome,
			String email) {
		
		if ( !Verificador.verificaStringValida(login) ) {
			// TODO: falta exception
		}else if ( !Verificador.verificaStringValida(senha) ){
			// TODO: falta exception
		}else if ( !Verificador.verificaStringValida(nome) ){
			// TODO: falta exception
		}else if ( !Verificador.verificaStringValida(email) ){
			// TODO: falta exception
		}
		
		if ( storage.hasUser(login, email) ) {
			// TODO: falta exception
		}
		User usr = new User(login, senha, nome, email);
		storage.addUsuario(usr);
	}

	public String procuraUsuario(String userLogin) throws UsuarioNaoCadastradoException, LoginException {
		if ( userLogin == null || userLogin == "" ) throw new  LoginException("Login inválido");
		
		String userID = storage.getUserID( userLogin );
		if ( userID==null ) throw new UsuarioNaoCadastradoException("Usuário inexistente");
		return userID;
	}

	public String getAtributoUsuario(String login, String attr) throws UsuarioNaoCadastradoException, AtributoInvalidoException {
		String userID = storage.getUserID( login );
		if ( userID==null ) throw new UsuarioNaoCadastradoException("Usuário inexistente");
		User user = storage.getUser(userID);
		
		if ( attr == null || attr == "" ) {
			throw new AtributoInvalidoException("Atributo inválido");
		}else if ( attr == "nome" ){
			return user.getNome();
		}else if ( attr == "email" ) {
			return user.getEmail();
		}else
			throw new AtributoInvalidoException("Atributo inexistente");
	}

	public String abrirSessao(String login, String senha) throws UsuarioNaoCadastradoException {
		String userID = storage.getUserID( login );
		if ( userID==null ) throw new UsuarioNaoCadastradoException("Usuário inexistente");
		
		// TODO: sessaoID vai ser oq?
		// TODO: falta tratar exception
		return userID;
	}

	public List<Musica> getPerfilMusical(String userID) throws UsuarioNaoCadastradoException {
		User usr = storage.getUser( userID );
		if ( usr==null ) throw new UsuarioNaoCadastradoException("Usuário inexistente");
		
		return usr.getPerfilMusical();
	}

	public void postarSom(String sessaoID, String link,
			GregorianCalendar dataCriacao) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 	TODO: colocar todos os metodos para serem autenticados, ou seja,
	 * passar o sessaoID como parametro para verificar se existe um usuario 
	 * com essa sessaoID logado para poder realizar os metodos!
	 * 
	 *  TODO: verificar se existe uma lista melhor que uma linkedList para guardar
	 * o sessaoID dos usuarios logados.
	 *  
	 *  TODO: realizar testes nao funcionais. Verificar se o sistema nao faz aquilo
	 * que nao eh para fazer.
	 * 
	 *  TODO: 
	 */
	
}
