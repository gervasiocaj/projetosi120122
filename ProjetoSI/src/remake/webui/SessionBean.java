package remake.webui;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import remake.excecao.*;
import remake.util.Favorito;

@ManagedBean(name = "sessao")
@SessionScoped
public class SessionBean {

	protected static String sessaoID;
	private String musica;
	private String seguir;
	private String ordenacao;

	public SessionBean() {
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
		// TODO FIXME não há getusuario por id
		String id = Conversador.sistema.getMusica(idMusica).getIDCriador();
		return Conversador.sistema.getUsuario(id).getNome();
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
			return "index";
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
			RegraDeComposicaoException {
		if (ordenacao.equals("Mais Recentes Primeiro")) {
			Conversador.sistema.setRegraDeComposicao(sessaoID, null,
					Conversador.sistema.getPrimeiraRegraDeComposicao()); // FIXME o que é esse comparador?
		}
		if (ordenacao.equals("Mais favoritos Primeiro")) {
			Conversador.sistema.setRegraDeComposicao(sessaoID, null,
					Conversador.sistema.getSegundaRegraDeComposicao());
		} else {
			Conversador.sistema.setRegraDeComposicao(sessaoID, null,
					Conversador.sistema.getTerceiraRegraDeComposicao());
		}
	}

	public void postarMusica() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Conversador.sistema.postarSom(sessaoID, musica,
				format.format(new Date()));
		reiniciaCampos();
		System.out.println(Conversador.sistema.getMainFeed(sessaoID).isEmpty());
	}

	public void seguirUsuario() {
		try {
			Conversador.sistema.seguirUsuario(sessaoID, seguir);
		} catch (SessaoIDException ex) { // TODO tratar essas excecoes
		} catch (LoginException ex) {
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
		return Conversador.sistema.getListaDeSeguidores(sessaoID); // FIXME converter set pra list
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

}
