package Classes;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Exceptions.AtributoInvalidoException;
import Exceptions.CriacaoUsrException;
import Exceptions.DataInvalidaException;
import Exceptions.LinkInvalidoException;
import Exceptions.LoginException;
import Exceptions.PostaSomException;
import Exceptions.SessaoIDException;
import Exceptions.UsuarioNaoCadastradoException;


public class SystemAPI {

	SystemSound sistema = new SystemSound();
	
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
	
	public List<Musica> getPerfilMusical( String sessaoID ) throws UsuarioNaoCadastradoException {
		return sistema.getPerfilMusical( sessaoID );
	}
	
	public List<Musica> getPerfilMusical(String sessaoID, String userID) {
		return sistema.getPerfilMusical( sessaoID, userID );
	}
	
	public void postarSom( String sessaoID, String link, GregorianCalendar dataCriacao ) throws SessaoIDException, PostaSomException, LinkInvalidoException, DataInvalidaException {
		sistema.postarSom( sessaoID, link, dataCriacao );
	}
	
	public void encerrarSessao(String sessaoID) {
		sistema.encerrarSessao(sessaoID);
	}
	
	public void encerrarSistema() {
		// TODO Auto-generated method stub
	}

	public Set getListaAmigos(String sessaoID) {
		return sistema.getListaAmigos(sessaoID);
		
	}

	public void enviaSolicitacaoDeAmizade(String sessaoID, String userID) {
		sistema.enviaSolicitacaoDeAmizade( sessaoID, userID );
		
	}

	public List<String> getListaDeSolicitacaoDeAmizade(String sessaoID) {
		return sistema.getSolicitacaoDeAmizade( sessaoID );
	}

	public List<String> getListaDeSolicitacaoDeAmizadePendente(String sessaoID) {
		return sistema.getListaDeSolicitacaoDeAmizadePendente(sessaoID);
	}

	public void aceitaSolicitacaoDeAmizade(String sessaoID, String userID) {
		sistema.enviaSolicitacaoDeAmizade( sessaoID, userID );
		
	}

	public List getTimeLine(String sessaoID) {
		return sistema.getTimeLine(sessaoID);
	}
	
}
