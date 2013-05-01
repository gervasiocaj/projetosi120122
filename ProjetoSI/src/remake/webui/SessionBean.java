package remake.webui;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import remake.entidades.Usuario;
import remake.excecao.*;
import remake.regras.OrdenadorRegraDefault;
import remake.regras.OrdenadorRegraFavoritado;
import remake.regras.OrdenadorRegraMaisFavoritos;
import remake.util.Favorito;

@ManagedBean(name = "sessao")
@SessionScoped
public class SessionBean implements Serializable {

	private static final long serialVersionUID = 130772112220421211L;
	protected static String sessaoID;
	private String musica;
	private String seguir;
	private String ordenacao;

	public SessionBean() throws ClassNotFoundException, IOException {
		Conversador.tentaIniciarRepositorio();
		ordenacao = "Mais Recentes Primeiro";
	}

	public String getOrdenacao() {
		return ordenacao;
	}

	public void setOrdenacao(String ord) {
		this.ordenacao = ord;
	}

	public String nomePostador(String idMusica) throws LoginException,
			UsuarioNaoCadastradoException, SomInexistenteException {
		String id = Conversador.sistema.getMusica(idMusica).getIDCriador();
		return Conversador.sistema.getUsuarioByID(id).getNome();
	}

	public String linkMusica(String idMusica) throws SomInexistenteException {
		return Conversador.sistema.getMusica(idMusica).getLink();
	}

	public String getNameUsuarioLogado() throws SessaoIDException,
			LoginException, UsuarioNaoCadastradoException {
		return Conversador.sistema.getUsuario(sessaoID).getNome();
	}

	public String getSessaoID() {
		return sessaoID;
	}

	public String deslogar() {
		try {
			Conversador.sistema.encerrarSessao(sessaoID);
			return "index?faces-redirect=true";
		} catch (SessaoIDException ex) {
			return "";
		}
	}

	public String getMusica() {
		return musica;
	}

	public void setMusica(String newValue) {
		this.musica = newValue;
	}

	public String getSeguir() {
		return seguir;
	}

	public void setSeguir(String newValue) {
		seguir = newValue;
	}

	public void mudarOrdenacao() throws SessaoIDException,
			RegraDeComposicaoException, IOException {
		if (ordenacao.equals("Mais Recentes Primeiro")) {
			Conversador.sistema.setRegraDeComposicao(sessaoID,
					new OrdenadorRegraDefault(),
					Conversador.sistema.getPrimeiraRegraDeComposicao()); // o
		}
		if (ordenacao.equals("Mais favoritos Primeiro")) {
			Conversador.sistema.setRegraDeComposicao(sessaoID,
					new OrdenadorRegraFavoritado(),
					Conversador.sistema.getSegundaRegraDeComposicao());
		} else {
			Conversador.sistema.setRegraDeComposicao(sessaoID,
					new OrdenadorRegraMaisFavoritos(),
					Conversador.sistema.getTerceiraRegraDeComposicao());
		}
	}

	public void postarMusica() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Conversador.sistema.postarSom(sessaoID, musica,
				format.format(new Date()));
		reiniciaCampos();
	}

	public void seguirUsuario() {
		try {
			Conversador.sistema.seguirUsuario(sessaoID, seguir);
		} catch (SessaoIDException ex) { // TODO tratar essas excecoes
		} catch (LoginException ex) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reiniciaCampos() {
		seguir = "";
		limpaMusica();
	}

	public void limpaMusica() {
		musica = "";
	}

	public List<String> getMusicas() throws SessaoIDException {
		return Conversador.sistema.getVisaoDosSons(sessaoID);
	}

	public List<String> getChoicesRadioButton() {
		List<String> choices = new LinkedList<String>();
		choices.add("Mais Recentes Primeiro");
		choices.add("Mais favoritos Primeiro");
		choices.add("Primeiro de quem eu ja favoritei");
		return choices;
	}

	public Collection<String> getSeguidores() throws Exception {
		return Conversador.sistema.getListaDeSeguidores(sessaoID); // FIXME
																	// converter
																	// set pra
																	// list
	}

	public Collection<String> getSeguindo() throws Exception {
		return Conversador.sistema.getListaSeguindo(sessaoID); // FIXME
	}

	public void favoritarMusica(String mus) throws Exception {
		Conversador.sistema.addFavorito(sessaoID, mus);
	}

	public List<String> getFavoritos() throws Exception {
		return Conversador.sistema.getFavoritos(sessaoID);
	}

	public List<String> getFeedExtra() throws Exception {
		List<String> ids = new LinkedList<String>();
		for (Favorito fav : Conversador.sistema.getFeedExtra(sessaoID))
			ids.add(fav.getIdMusica());
		return ids;
	}

	public String nomeUsuario(String str) throws LoginException,
			UsuarioNaoCadastradoException {
		return "";
	}

	public List<Usuario> followers() throws SessaoIDException {
		return Conversador.sistema.getFollowers(sessaoID);
	}

	public List<Usuario> following() throws SessaoIDException {
		return Conversador.sistema.getFollowing(sessaoID);
	}

	public List<Usuario> fontesRecomendadas() throws SessaoIDException {
		List<Usuario> result = new LinkedList<Usuario>();
		for (String u : Conversador.sistema.getFontesDeSonsRecomendadas(sessaoID))
			result.add(Conversador.sistema.getUsuarioByID(u));
		return result;
	}

}
