package remake.testes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import remake.excecao.*;
import remake.fachada.FachadaEasyAccept;

public class Cenario {

	private FachadaEasyAccept fachada;
	Map<String,String> sessoes;
	List<String[]> listagemUsuarios;
	
	
	public Cenario() throws Exception {
		fachada = new FachadaEasyAccept();
		fachada.zerarSistema();
		sessoes = new HashMap<String,String>();
		inicializaInfoUsuario();
	}
	
	public FachadaEasyAccept getFachada() {
		return fachada;
	}

	public void setFachada(FachadaEasyAccept fachada) {
		this.fachada = fachada;
	}
	
	public String getSessaoID(String login){
		return sessoes.get(login);
	}
	
	public void inicializaInfoUsuario() {
		listagemUsuarios = new ArrayList<String[]>();
		listagemUsuarios.add( new String[] {"mark","m@rk","Mark Zuckerberg","mark@facebook.com"});
		listagemUsuarios.add( new String[] {"steve","5t3v3","Steven Paul Jobs","istevao@msn.com"});
		listagemUsuarios.add( new String[] {"bill","severino","William Henry Gates III","billzin@msn.com"});
	}
	
	public void populaCenario() throws CriacaoUserException, AtributoException, IOException {
		for (String[] usuario : listagemUsuarios) {
			fachada.criarUsuario(usuario[0], usuario[1], usuario[2], usuario[3]);
		}
	}
	
	public void abrirSessoes() throws LoginException, UsuarioNaoCadastradoException {
		String sessaoID;
		for (String[] usuario : listagemUsuarios) {
			sessaoID = fachada.abrirSessao(usuario[0], usuario[1]);
			sessoes.put(usuario[0], sessaoID);
		}
	}
	
	public String[] populaListaDeSons(String sessaoID) throws SessaoIDException, LinkInvalidoException, DataInvalidaException, IOException {
		String[] retorno = new String[3];
		String[][] sons = new String[3][2];
		sons[0] = new String[] {"http://youtube.com/seiLa", "25/12/2020"};
		sons[1] = new String[] {"http://vimeo.com/seiLa", "23/06/2021"};
		sons[2] = new String[] {"http://blabla.com/seiLa", "10/04/2022"};
		
		for (int i = 0; i < retorno.length; i++) {
			retorno[i] = fachada.postarSom(sessaoID, sons[i][0], sons[i][1]);
		}
		
		return retorno;
	}

	public String[] populaTags(String sessaoID, String[] tagName) throws SessaoIDException, LoginException, UsuarioNaoCadastradoException {
		String[] retorno = new String[3];
		
		for (int i = 0; i < retorno.length; i++) {
			retorno[i] = fachada.criarTag(sessaoID, tagName[i]);
		}
		
		return retorno;
	}
}