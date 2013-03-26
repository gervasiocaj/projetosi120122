package remake.fachada;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import remake.regras.OrdenadorRegra;
import remake.regras.OrdenadorRegraDefaut;
import remake.regras.OrdenadorRegraFavoritado;
import remake.regras.OrdenadorRegraMaisFavoritos;
import remake.sistema.SistemaAPI;

import remake.entidades.Musica;
import remake.entidades.Usuario;
import remake.excecao.*;
import br.edu.ufcg.rickroll.rickroll.Favorito;
import br.edu.ufcg.rickroll.rickroll.Verificador;

public class FachadaEasyAccept {

	private SistemaAPI sistema;

	public void zerarSistema() {
		sistema = new SistemaAPI();
	}

	public void criarUsuario(String login, String senha, String nome,
			String email) throws CriacaoUserException, AtributoException {
		
		sistema.criaNovoUsuario(login, senha, nome, email);
	}

	public String abrirSessao(String login, String senha)
			throws LoginException, UsuarioNaoCadastradoException {
		
		return sistema.abrirSessao(login, senha);
	}

	public String getAtributoUsuario(String login, String atributo)
			throws AtributoException, UsuarioNaoCadastradoException,
			LoginException {
		
		if (!Verificador.verificaStringValida(login))
			throw new LoginException("Login inválido");
		
		Usuario usuario = sistema.getUsuarioByLogin(login);
		if ( !Verificador.verificaStringValida(atributo) ) 
			throw new AtributoException("Atributo inválido");
		else if ( atributo.equals("nome") ) 
			return usuario.getLogin();
		else if ( atributo.equals("email") )
			return usuario.getEmail();
		else
			throw new AtributoException("Atributo inexistente");
		
	}

	public String getPerfilMusical(String idSessao)
			throws UsuarioNaoCadastradoException, SessaoIDException {
		List<String> musicas = sistema.getPerfilMusical(idSessao);
		return convertCollection(musicas);
	}

	public String postarSom(String idSessao, String link, String dataCriacao)
			throws SessaoIDException, LinkInvalidoException,
			DataInvalidaException {
		
		return sistema.postarSom(idSessao, link, dataCriacao);
	}

	public String getAtributoSom(String somID, String atributo)
			throws UsuarioNaoCadastradoException, AtributoException,
			SomInexistenteException {
		
		Musica musica = sistema.getMusica(somID);
		
		if ( !Verificador.verificaStringValida(atributo) ) 
			throw new AtributoException("Atributo inválido");
		else if ( atributo.equals("dataCriacao") ){
			Date date = musica.getDataDeCriacao().getTime();
			return new SimpleDateFormat("dd/MM/yyyy").format(date);
		}else
			throw new AtributoException("Atributo inexistente");
	}

	public void encerrarSessao(String login) throws SessaoIDException {
		sistema.encerrarSessao(login);
	}

	public void encerrarSistema() {
		sistema = null;
	}

	public String getIDUsuario(String idSessao) throws LoginException, UsuarioNaoCadastradoException {
		
		Usuario usuario = sistema.getUsuario(idSessao);
		return usuario.getID();
	}

	public int getNumeroDeSeguidores(String idSessao) throws Exception {
		return sistema.getListaDeSeguidores(idSessao).size();
	}

	public void seguirUsuario(String idSessao, String login)
			throws SessaoIDException, LoginException {
		sistema.seguirUsuario(idSessao, login);
	}

	public String getFontesDeSons(String idSessao) throws SessaoIDException {
		Set<String> fonteDeSons = sistema.getListaSeguindo(idSessao);
		return convertCollection(fonteDeSons);
	}

	public String getVisaoDosSons(String idSessao) throws SessaoIDException {
		List<String> sons = new LinkedList<String>();
		for (String user : sistema.getListaSeguindo(idSessao)) {
			sons.addAll(sistema.getPerfilMusical(idSessao, user));
		}
		return convertCollection(sons); // TODO
	}

	public String getListaDeSeguidores(String idSessao) throws Exception {
		Set<String> seguidores = sistema.getListaDeSeguidores(idSessao);
		return convertCollection(seguidores);
	}

	public void favoritarSom(String idSessao, String idSom)
			throws SessaoIDException, SomInexistenteException, LoginException, UsuarioNaoCadastradoException {
		
		sistema.addFavorito(idSessao, idSom);
	}

	public String getSonsFavoritos(String idSessao) throws SessaoIDException {
		List<String> favoritos = sistema.getFavoritos(idSessao);
		return convertCollection(favoritos);
	}

	public String getFeedExtra(String idSessao) throws SessaoIDException {
		List<String> feed = new LinkedList<String>();
		for (Favorito m : sistema.getFeedExtra(idSessao))
			feed.add(m.getIdMusica());
		return convertCollection(feed);
	}

	public String getFirstCompositionRule() {
		return "regraDefault";
	}

	public String getSecondCompositionRule() {
		return "regraFavoritado";
	}

	public String getThirdCompositionRule() {
		return "regraMaisFavoritado";
	}

	public String getMainFeed(String idSessao) throws SessaoIDException {
		List<String> feed = new LinkedList<String>();
		for (String m : sistema.getMainFeed(idSessao))
			feed.add(m);
		return convertCollection(feed);
	}

	public void setMainFeedRule(String sessaoID, String rule)
			throws SessaoIDException, RegraDeComposicaoException, LoginException, UsuarioNaoCadastradoException {

		String usuarioID = sistema.getUsuario(sessaoID).getID();
		OrdenadorRegra<String> regra = new OrdenadorRegraDefaut();;
		
		if ( rule.equals( getSecondCompositionRule() ) )
			regra = new OrdenadorRegraFavoritado();
		else if ( rule.equals( getThirdCompositionRule() ) )
			regra = new OrdenadorRegraMaisFavoritos(usuarioID);
			
		sistema.setRegraDeComposicao(sessaoID, regra);
	}

	private String convertCollection(Collection<?> c) {
		String str = "{";
		Iterator<?> it = c.iterator();
		while (it.hasNext()) {
			str += it.next().toString();
			if (it.hasNext()) {
				str += ",";
			}
		}
		str += "}";
		return str;
	}

}
