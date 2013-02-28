package Classes;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import Exceptions.CriacaoUsrException;
import Exceptions.LoginException;
import Exceptions.UsuarioExistenteException;
import Exceptions.UsuarioNaoCadastradoException;

public class FachadaEasyAcpt {
	
	SystemAPI system = new SystemAPI();
	
	public void criaUsuario(String login, String senha, String nome, String email){
			system.criaNovoUsuario(login, senha, nome, email);

	}
	
	public String abrirSessao(String login, String senha){
		return "";
	}
	
	public List<Musica> getPerfilMusical(String idSessao){
		return system.getPerfilMusical(idSessao);
	}
	
//	public Musica postarSom(String idSessao, String link, String dataDeCriacao){
//		
//		GregorianCalendar calendar = new GregorianCalendar(Integer.parseInt(dataDeCriacao.substring(3,5)), 
//				Integer.parseInt(dataDeCriacao.substring(0,2)), Integer.parseInt(dataDeCriacao.substring(6,8)));
//		system.postarSom(idSessao, link, calendar);
//	}
	
	public getAtributoSom(Musica idSom, String atributo){
		
	}
	
	public getIdUsuario(String idSessao){
		
	}
	
	public int getNumeroDeSeguidores(String idSessao){
		return system.getListaAmigos(idSessao).size();
	}
	
	public void seguirUsuario(String idSessao, String login){
		
	}
	
	public Set<Usuario> getFontesDeSom(String idSessao){
		return system.getListaAmigos(idSessao);
	}
	
	public Set<Usuario> getListaDeSeguidores(String idSessao){
		return system.getListaAmigos(idSessao);
	}
	
	public List<Musica> getVisaoDosSons(String idSessao){
		return system.getTimeLine(idSessao);
	}
	
	
	

}
