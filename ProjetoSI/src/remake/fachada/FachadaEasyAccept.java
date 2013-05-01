package remake.fachada;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import remake.regras.*;
import remake.sistema.*;
import remake.util.Favorito;
import remake.entidades.*;
import remake.excecao.*;

public class FachadaEasyAccept {

	private SistemaAPI sistema;

	public void zerarSistema() throws Exception {
		sistema = new SistemaAPI();
		sistema.zerarSistema();
	}

	public void criarUsuario(String login, String senha, String nome,
			String email) throws CriacaoUserException, AtributoException, IOException {
		
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
			return usuario.getNome();
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
			DataInvalidaException, IOException {
		
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

	public void encerrarSistema() throws IOException {
		sistema.encerrarSistema();
	}

	public String getIDUsuario(String idSessao) throws LoginException, UsuarioNaoCadastradoException, SessaoIDException {
		
		Usuario usuario = sistema.getUsuario(idSessao);
		return usuario.getID();
	}

	public int getNumeroDeSeguidores(String idSessao) throws Exception {
		return sistema.getListaDeSeguidores(idSessao).size();
	}

	public void seguirUsuario(String idSessao, String login)
			throws SessaoIDException, LoginException, IOException {
		sistema.seguirUsuario(idSessao, login);
	}

	public String getFontesDeSons(String idSessao) throws SessaoIDException {
		Set<String> fonteDeSons = sistema.getListaSeguindo(idSessao);
		return convertCollection(fonteDeSons);
	}

	public String getVisaoDosSons(String sessaoID) throws SessaoIDException {
		List<String> sons = sistema.getVisaoDosSons(sessaoID);
		return convertCollection(sons);
	}

	public String getListaDeSeguidores(String idSessao) throws Exception {
		List<String> seguidores = sistema.getListaDeSeguidores(idSessao);
		return convertCollection(seguidores);
	}

	public void favoritarSom(String idSessao, String idSom)
			throws SessaoIDException, SomInexistenteException, LoginException, UsuarioNaoCadastradoException, IOException {
		
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
		return sistema.getPrimeiraRegraDeComposicao();
	}

	public String getSecondCompositionRule() {
		return sistema.getSegundaRegraDeComposicao();
	}

	public String getThirdCompositionRule() {
		return sistema.getTerceiraRegraDeComposicao();
	}

	public String getMainFeed(String idSessao) throws SessaoIDException {
		List<String> feed = new LinkedList<String>();
		List<String> mainFeed = sistema.getMainFeed(idSessao);
		for (String m : mainFeed)
			feed.add(m);
		return convertCollection(feed);
	}

	public void setMainFeedRule(String sessaoID, String rule)
			throws SessaoIDException, RegraDeComposicaoException, LoginException, UsuarioNaoCadastradoException, IOException{

		String usuarioID = sistema.getUsuario(sessaoID).getID();
		OrdenadorRegra<String> regra = new OrdenadorRegraDefault();
		
		if(rule != null){
			if ( rule.equals( getSecondCompositionRule() ) )
				regra = new OrdenadorRegraFavoritado();
			else if ( rule.equals( getThirdCompositionRule() ) )
				regra = new OrdenadorRegraMaisFavoritos(usuarioID);
		}
		sistema.setRegraDeComposicao(sessaoID, regra, rule);

	}
	
	public String criarLista(String idSessao, String nome) throws SessaoIDException, ListaPersonalizadaException, IOException{
		sistema.criaLista(idSessao, nome);
		return nome;
	}
	
	public void adicionarUsuario(String idSessao, String idLista, String idUsuario) throws SessaoIDException, ListaPersonalizadaException, UsuarioNaoCadastradoException, IOException{
		sistema.adicionarUsuarioALista(idSessao, idLista, idUsuario);
	}
	
	public String getSonsEmLista(String idSessao, String idLista) throws ListaPersonalizadaException, LoginException, UsuarioNaoCadastradoException, SessaoIDException{
		return convertCollection(sistema.getSonsEmLista(idSessao, idLista));
	}
	
	public String getNumFavoritosEmComum(String idSessao, String idUsuario) throws SessaoIDException, UsuarioNaoCadastradoException{
		return "" + sistema.getNumFavoritosEmComum(idSessao, idUsuario);
	}
	
	public String getNumFontesEmComum(String idSessao, String idUsuario) throws SessaoIDException, UsuarioNaoCadastradoException{
		return "" + sistema.getNumFontesEmComum(idSessao, idUsuario);
	}
	
	public String getFontesDeSonsRecomendadas(String idSessao) throws SessaoIDException{
		return convertCollection(sistema.getFontesDeSonsRecomendadas(idSessao));
	}
	
	public void reiniciarSistema() throws ClassNotFoundException, IOException{
		sistema.reiniciarSistema();
	}
	
	public String criarTag(String idSessao, String tag) throws LoginException, SessaoIDException, UsuarioNaoCadastradoException {
		Usuario user = sistema.getUsuario(idSessao);
		return user.addTag(tag);
	}
	
	public void adicionarTagASom(String idSessao, String tag, String som) throws SomInexistenteException, SessaoIDException, UsuarioNaoCadastradoException, LoginException {
		Usuario user = sistema.getUsuario(idSessao);
		Musica mus = sistema.getMusica(som);
		if (user.getPerfilMusical().contains(mus.getID()) && user.getTags().contains(tag))
			mus.addTag(tag);
	}
	
	public String getListaTagsEmSom(String idSessao, String som) throws SomInexistenteException {
		return convertCollection(sistema.getMusica(som).getTags());
	}
	
	public String getNomeTag(String idSessao, String tag) throws LoginException, UsuarioNaoCadastradoException, SessaoIDException {
		Usuario user = sistema.getUsuario(idSessao);
		if (user.getTags().contains(tag))
			return tag;
		return null;
	}
	
	public String getTagsDisponiveis(String idSessao) throws LoginException, UsuarioNaoCadastradoException, SessaoIDException {
		return convertCollection(sistema.getUsuario(idSessao).getTags());
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
