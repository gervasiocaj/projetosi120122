package br.edu.ufcg.rickroll.rickroll;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.sql.Date;

import br.edu.ufcg.rickroll.exceptions.DataInvalidaException;
import br.edu.ufcg.rickroll.exceptions.LinkInvalidoException;


public class Musica implements Comparable<Musica> {

	private String loginCriador;
	private String link;
	private GregorianCalendar dataDeCriacao;


	public Musica(String loginCriador,String link, GregorianCalendar dataDeCriacao) throws LinkInvalidoException, DataInvalidaException {
		if (!linkValido(link)) throw new LinkInvalidoException("Link inv�lido");
//		else if(!dataValida(dataDeCriacao)) throw new DataInvalidaException("Data inv�lida");
		this.loginCriador = loginCriador;
		this.link = link;
		this.dataDeCriacao = dataDeCriacao;
	}


	public String getLink(){
		return link;
	}

	/**
	 * Altera o link da musica
	 * 
	 * @param newLink
	 * 		Novo link
	 * @throws LinkInvalidoException
	 * 		Joga excessao caso o link nao seja valido.
	 */

	public void setLink(String newLink) throws LinkInvalidoException {
		if (!linkValido(newLink))
			throw new LinkInvalidoException("Link inv�lido");
		this.link = newLink;
	}

	/**
	 * Retorna a data de criacao da musica.
	 * 
	 * @return
	 * 		Data de criacao.
	 */

	public GregorianCalendar getDataDeCriacao(){
		return dataDeCriacao;
	}

	/**
	 * Retorna o id do criador da musica.
	 * 
	 * @return
	 * 		Id do criador.
	 */

	public String getIdSessao(){
		return loginCriador;
	}

	/**
	 * Verifica se o link utilizado eh valido ou nao.
	 * 
	 * @param link
	 * 		Link a ser verificado
	 * @return
	 * 		true caso o link seja valido, false caso contrario.
	 */

	public static boolean linkValido(String link) {
		return (link.startsWith("http://") && link.length() > "http://".length()) ||
				(link.startsWith("https://") && link.length() > "https://".length());
	}

	/**
	 * Verifica se a data eh valida ou nao.
	 * 
	 * @param link
	 * 		Data a ser verificada
	 * @return
	 * 		true caso a data seja valida, false caso contrario.
	 */

	public static boolean dataValida(String data) {
		return (new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis())) == data);
	}

	/**
	 * Retorna uma string representando a musica
	 * @return
	 * 		String de representacao.
	 */



//	@Override
//	public String toString() {
//		return link + " - " + dataDeCriacao;
//	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataDeCriacao == null) ? 0 : dataDeCriacao.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result
				+ ((loginCriador == null) ? 0 : loginCriador.hashCode());
		return result;
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
		if (loginCriador == null) {
			if (other.loginCriador != null)
				return false;
		} else if (!loginCriador.equals(other.loginCriador))
			return false;
		return true;
	}

	@Override
	public int compareTo(Musica musica2) {
		return getDataDeCriacao().compareTo(musica2.getDataDeCriacao());
	}

}