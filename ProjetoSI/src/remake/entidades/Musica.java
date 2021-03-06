package remake.entidades;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import remake.excecao.DataInvalidaException;
import remake.excecao.LinkInvalidoException;
import remake.sistema.Verificador;

public class Musica implements Serializable, Comparable<Musica> {
	
	private static final long serialVersionUID = 5737577736835958735L;
	private List<String> tags;
	private String IDCriador;
	private String link;
	private GregorianCalendar dataDeCriacao;
	private final String id;
	private List<String> favoritaram;

	@SuppressWarnings("deprecation")
	public Musica(String IDCriador, String link, String dataDeCriacao)
			throws LinkInvalidoException, DataInvalidaException {

		if (!linkValido(link))
			throw new LinkInvalidoException("Som inválido");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		GregorianCalendar cal = new GregorianCalendar();
		Date temp;
		try {
			sdf.setLenient(false);
			temp = sdf.parse(dataDeCriacao);
			cal.setTime(new Date());
			cal.set(temp.getYear()+1900, temp.getMonth(), temp.getDate());
		} catch (ParseException e) {
			throw new DataInvalidaException("Data de Criação inválida");
		}
		if (GregorianCalendar.getInstance().getTimeInMillis() - cal.getTimeInMillis() > 60000)
			throw new DataInvalidaException("Data de Criação inválida");

		this.IDCriador = IDCriador;
		this.link = link;
		this.dataDeCriacao = cal;
		this.tags = new LinkedList<String>();
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
		String[] prefixes = { "http://", "https://", "ftp://", "www" };
		for (String p : prefixes)
			if (link.startsWith(p) && (link.length() > p.length()))
				return true;
		return false;
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
		return getDataDeCriacao().compareTo(musica2.getDataDeCriacao());
//		return musica2.getDataDeCriacao().compareTo(getDataDeCriacao());
	}

	public String getID() {
		return id;
	}

	/**
	 * Adicina usuarios que favoritaram a musica
	 * 
	 * @param idSessao
	 *            Id do usuario que a favoritou
	 */

	public void addFavoritado(String idSessao) {
		favoritaram.add(idSessao);
	}
	
	/** Retorna o numero de usuario que favoritaram a musica
	 * 
	 * @return
	 * 		numero de favoritos
	 */
	public int getNumeroFavoritos(){
		return favoritaram.size();
	}

	public void addTag(String tag) {
		if (Verificador.verificaStringValida(tag))
			tags.add(tag);
	}

	public List<String> getTags() {
		return tags;
	}

}
