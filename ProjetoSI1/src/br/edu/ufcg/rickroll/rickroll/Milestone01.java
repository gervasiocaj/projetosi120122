package Classes;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import Exceptions.AtributoInvalidoException;
import Exceptions.CriacaoUsrException;
import Exceptions.LoginException;
import Exceptions.PostaSomException;
import Exceptions.SolicitacaoInvalidaException;


public class Milestone01 {
	
/////////////////////////////////////////////////////////////////////////// US01 - Cria��o de conta
	
	private void zerarSistema() {
		// TODO Auto-generated method stub
	}
	
	private void criarUsuario( String login, String senha, String nome, String email) throws CriacaoUsrException {
		// TODO Auto-generated method stub
	}
	
	private String getAtributoUsuario( String login, String attr ) throws AtributoInvalidoException {
		// TODO Auto-generated method stub
		String str = "attr pedido do usr";
		return str;
	}
	
	private String abrirSessao( String login, String senha ) throws LoginException {
		// TODO Auto-generated method stub
		String str = "ID da sessao";
		return str;
	}
	
	private List<Musica> getPerfilMusical( String sessaoID ) {
		// TODO Auto-generated method stub
		// Lista dos sons do usr pedido? ou 
		return new LinkedList<Musica>();
	}
	
	// TODO decidir se realmente vai passar o som ou outra coisa
	private String getAtributoSom( Musica somID, String attr ) throws AtributoInvalidoException {
		// TODO Auto-generated method stub
		String str = "attr pedido do som";
		return str;
	}

	private void postarSom( String idSessao, String link, GregorianCalendar dataCriacao ) throws PostaSomException {
		// TODO Auto-generated method stub
	}
	
	private void encerrarSessao( String login ) {
		// TODO Auto-generated method stub
	}
	
	private void encerrarSistema() {
		// TODO Auto-generated method stub
	}
	
/////////////////////////////////////////////////////////////////////////// US02 - Fazendo amigos e visualizando fonte de sons
	
	private String getIDUsuario( String idSessao ) {
		// TODO Auto-generated method stub
		String str = "ID usuario";
		return str;
	}
	
	private String enviarSolicitacaoAmizade( String idSessao, String loginAmigo ) throws SolicitacaoInvalidaException {
		// TODO Auto-generated method stub
		String str = "ID solicita��o";
		return str;
	}
	
	private void aceitarSolicitacaoAmizade( String idSessao, String solicitacaoID ) throws SolicitacaoInvalidaException {
		// TODO Auto-generated method stub
		
		/**
		 * DISCURSSAO:
		 * 			Fazer que com que o amigo, que recebeu a solicitacao, ao aceitar, 
		 * 		envie uma solicitacao pra pessoa que enviou a solicita��o e configurar 
		 * 		o sistema pra quando receber a solicita��o de amizade de alguem que vc 
		 * 		ja fez a solicita��o passar ele para a sua lista de amigos. Em resumo,  
		 * 		Usu�rio passa a ter uma lista de solicita�oes, que ele fez/recebeu, e 
		 * 		quando uma nova solicitacao de amizade eh feita o sistema passa a pessoa
		 * 		pra lista de amigos se o amigo ja tiver feito solicitacao pra vc.
		 * 
		 * Dessa forma enviarSolicitacaoAmizade e aceitarSolicitacaoAmizade passam a ser
		 * a mesma coisa!
		 */
		
	}
	
	private ArrayList<String> getFontesDeSons( String idSessao ) {
		// TODO Auto-generated method stub
		/**
		 * DISCURSSAO:
		 * 			Acredito que fazer que seja uma lista com os id's dos usur�rios seria
		 * 		o mais correto para o sistema que foi discutido inicialmente
		 */
		return new ArrayList<String>();
	}
	
/////////////////////////////////////////////////////////////////////////// US03 - Fazendo amigos e visualizando os sons
}