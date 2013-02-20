package Classes;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import Exceptions.AtributoInvalidoException;
import Exceptions.CriacaoUsrException;
import Exceptions.LoginException;
import Exceptions.PostaSomException;
import Exceptions.SessaoIDException;
import Exceptions.UsuarioNaoCadastradoException;


public class SystemAPI {

	System sistema = new System();
	
	public void criaNovoUsuario( String login, String senha, String nome, String email ) {
		sistema.criaNovoUsuario( login, senha, nome, email);
	}

	public String procuraUsuario(String usuarioLogin) throws UsuarioNaoCadastradoException, LoginException {
		return sistema.procuraUsuario( usuarioLogin );
	}
	
	public String getAtributoUsuario( String login, String attr ) throws UsuarioNaoCadastradoException, AtributoInvalidoException {
		return sistema.getAtributoUsuario( login, attr );
	}
	
	public String abrirSessao( String login, String senha ) throws UsuarioNaoCadastradoException, LoginException {
		return sistema.abrirSessao( login, senha );
	}
	
	// TODO: verificar se realmente será o sessaoID ao invez do userID
	public List<Musica> getPerfilMusical( String userID ) throws UsuarioNaoCadastradoException {
		return sistema.getPerfilMusical( userID );
	}
	
	public void postarSom( String sessaoID, String link, GregorianCalendar dataCriacao ) throws SessaoIDException, PostaSomException {
		sistema.postarSom( sessaoID, link, dataCriacao );
	}
	
	public void encerrarSessao( String login ) {
		// TODO Auto-generated method stub
	}
	
	public void encerrarSistema() {
		// TODO Auto-generated method stub
	}
	
}
