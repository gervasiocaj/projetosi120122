package br.edu.ufcg.rickroll.rickroll;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.sql.Date;
import br.edu.ufcg.rickroll.exceptions.*;

public class Musica implements Comparable<Musica> {

	private String IDCriador;
	private String link;
	private GregorianCalendar dataDeCriacao;
	private final String id;
	private List<String> favoritaram;

	public Musica(String IDCriador, String link, GregorianCalendar dataDeCriacao)
			throws LinkInvalidoException, DataInvalidaException {

		if (!linkValido(link))
			throw new LinkInvalidoException("Som inválido");

		this.IDCriador = IDCriador;
		this.link = link;
		this.dataDeCriacao = dataDeCriacao;
		this.id = IDCriador + ";" + link + ";" + UUID.randomUUID();
		favoritaram = new LinkedList<String>();
		// this.dataDeCriacao = new GregorianCalendar();
		// this.dataDeCriacao.setTime(dataDeCriacao.getTime());
	}

	// public String getMusicaId() {
	// return (getIDCriador() + " " + getLink());
	// }

	public String getLink() {
		return link;
	}

	/**
	 * Altera o link da musica
	 * 
	 * @param newLink
	 *            Novo link
	 * @throws LinkInvalidoException
	 *             Joga excessao caso o link nao seja valido.
	 */

	public void setLink(String newLink) throws LinkInvalidoException {
		if (!linkValido(newLink))
			throw new LinkInvalidoException("Link inválido");
		this.link = newLink;
	}

	/**
	 * Retorna a data de criacao da musica.
	 * 
	 * @return Data de criacao.
	 */

	public GregorianCalendar getDataDeCriacao() {
		return dataDeCriacao;
	}

	/**
	 * Verifica se o link utilizado eh valido ou nao.
	 * 
	 * @param link
	 *            Link a ser verificado
	 * @return true caso o link seja valido, false caso contrario.
	 */

	public static boolean linkValido(String link) {
		if (link == null || link.equals(""))
			return false;
		return (link.startsWith("http://") && link.length() > "http://"
				.length())
				|| (link.startsWith("https://") && link.length() > "https://"
						.length());
	}

	/**
	 * Verifica se a data eh valida ou nao.
	 * 
	 * @param link
	 *            Data a ser verificada
	 * @return true caso a data seja valida, false caso contrario.
	 */

	public static boolean dataValida(String data) {
		return (new SimpleDateFormat("dd/MM/yyyy").format(new Date(System
				.currentTimeMillis())).equals(data));
	}

	/**
	 * Retorna uma string representando a musica
	 * 
	 * @return String de representacao.
	 */

	// @Override
	// public String toString() {
	// return getLoginCriador() + " postou: " + link + " - " + dataDeCriacao;
	// }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataDeCriacao == null) ? 0 : dataDeCriacao.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result
				+ ((IDCriador == null) ? 0 : IDCriador.hashCode());
		return result;
	}

	public String getIDCriador() {
		return IDCriador;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Musica other = (Musica) obj;
		if (dataDeCriacao == null) {
			if (other.dataDeCriacao != null)
				return false;
		} else if (!dataDeCriacao.equals(other.dataDeCriacao))
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (IDCriador == null) {
			if (other.IDCriador != null)
				return false;
		} else if (!IDCriador.equals(other.IDCriador))
			return false;
		return true;
	}

	@Override
	public int compareTo(Musica musica2) {
		return musica2.getDataDeCriacao().compareTo(getDataDeCriacao());
	}

	public String getID() {
		return id;
	}
	
	/** Adicina usuarios que favoritaram a musica
	 * 
	 * @param idSessao
	 * 		Id do usuario que a favoritou
	 */
	
	public void addFavoritado(String idSessao){
		favoritaram.add(idSessao);
	}

}