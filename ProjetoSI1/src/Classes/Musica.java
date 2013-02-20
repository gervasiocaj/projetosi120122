package Classes;

import java.util.GregorianCalendar;

import Exceptions.LinkInvalidoException;

public class Musica {
	
	private String idSessao;
	private String link;
	private String dataDeCriacao;
	
	public Musica(String idSessao,String link, String dataDeCriacao) throws LinkInvalidoException {
		if (!linkValido(link))
			throw new LinkInvalidoException("Link invalido.");
		this.idSessao = idSessao;
		this.link = link;
		this.dataDeCriacao = dataDeCriacao;
	}
	
	public Musica(String idSessao,String link, GregorianCalendar dataDeCriacao) throws LinkInvalidoException {
		if (!linkValido(link))
			throw new LinkInvalidoException("Link invalido.");
		this.idSessao = idSessao;
		this.link = link;
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
			throw new LinkInvalidoException("Link invalido.");
		this.link = newLink;
	}
	
	/**
	 * Retorna a data de criacao da musica.
	 * 
	 * @return
	 * 		Data de criacao.
	 */
	
	public String getDataDeCriacao(){
		return dataDeCriacao;
	}
	
	/**
	 * Retorna o id do criador da musica.
	 * 
	 * @return
	 * 		Id do criador.
	 */
	
	public String getIdSessao(){
		return idSessao;
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
	 * Retorna uma string representando a musica
	 * @return
	 * 		String de representacao.
	 */
	
	@Override
	public String toString() {
		return link + " - " + dataDeCriacao;
	}

}
