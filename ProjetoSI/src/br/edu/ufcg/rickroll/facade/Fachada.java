/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufcg.rickroll.facade;

import br.edu.ufcg.rickroll.rickroll.*;
import br.edu.ufcg.rickroll.exceptions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author Guilherme
 */
public class Fachada {

	private RickRoll sistema;

	public void zerarSistema() {
		sistema = new RickRoll();
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
			throws AtributoException, UsuarioNaoCadastradoException {
		return sistema.getAtributoUsuario(login, atributo);
	}

	public String getPerfilMusical(String idSessao)
			throws UsuarioNaoCadastradoException {
		List<String> musicas = sistema.getPerfilMusical(idSessao);
		Iterator<String> it = musicas.iterator();
		
		String mus = "{";
		while (it.hasNext()) {
			mus += it.next();
			if (it.hasNext())
				mus += ",";
		}
		mus += "}";
		return mus;

	}

	public String postarSom(String idSessao, String link, String dataCriacao)
			throws SessaoIDException, LinkInvalidoException, DataInvalidaException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		GregorianCalendar cal = new GregorianCalendar();
		try {
			cal.setTime(sdf.parse(dataCriacao));
		} catch (ParseException e) {
			throw new DataInvalidaException("Data de Criação inválida");
		}
		return sistema.postarSom(idSessao, link, cal);
	}

	public String getAtributoSom(String idSom, String atributo)
			throws UsuarioNaoCadastradoException, AtributoException {
		return sistema.getAtributoSom(idSom, atributo);
	}

	public void encerrarSessao(String login) {
		sistema.encerrarSessao(login);
	}

	public void encerrarSistema() {
		sistema = null;
	}

	public String getIDUsuario(String idSessao) {
		return "";
	}

	public int getNumeroDeSeguidores(String idSessao) throws SessaoIDException {
		return 0;
	}

	public void seguirUsuario(String idSessao, String login)
			throws SessaoIDException, LoginException {

	}

	public List<String> getFonteDeSons(String idSessao)
			throws SessaoIDException {

		return null;
	}

	public List<String> getListaDeSeguidores(String idSessao)
			throws SessaoIDException {

		return null;
	}

}