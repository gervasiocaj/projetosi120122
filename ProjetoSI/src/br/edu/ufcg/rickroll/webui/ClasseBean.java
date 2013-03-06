package br.edu.ufcg.rickroll.webui;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
// or import javax.inject.Named;
import javax.faces.bean.SessionScoped;

// or import javax.enterprise.context.SessionScoped;

@ManagedBean(name = "classe")
// or @Named("user")
@SessionScoped
public class ClasseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3880440144087011597L;

	public ClasseBean() {

		Conversador.tentaIniciarRepositorio();

	}

	public String registrar() {
		try {
			if (!PropriedadesBean.password().equals(
					PropriedadesBean.confirmPassword())) {
				return "";
			}
			Conversador.sistema.criaNovoUsuario(PropriedadesBean.login(),
					PropriedadesBean.password(), PropriedadesBean.name(),
					PropriedadesBean.email());
			SessionBean.sessaoID = Conversador.sistema.abrirSessao(
					PropriedadesBean.login, PropriedadesBean.password);
			SessionBean.login = PropriedadesBean.login;
			return "principal";
		} catch (Exception ex) {
			return ex.getMessage();
		}

	}

	public String logar() {
		try {
			SessionBean.sessaoID = Conversador.sistema.abrirSessao(
					PropriedadesBean.login(), PropriedadesBean.password());
			SessionBean.login = PropriedadesBean.login;
			return "principal";
		} catch (Exception ex) {
			return ex.getMessage();
		}

	}

}