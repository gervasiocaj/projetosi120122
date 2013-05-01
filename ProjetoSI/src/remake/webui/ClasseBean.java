package remake.webui;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
// or import javax.inject.Named;
import javax.faces.bean.SessionScoped;

// or import javax.enterprise.context.SessionScoped;

@ManagedBean(name = "classe")
// or @Named("user")
@SessionScoped
public class ClasseBean implements Serializable {

	private static final long serialVersionUID = 6322672411701456627L;

	public ClasseBean() throws ClassNotFoundException, IOException {
		Conversador.tentaIniciarRepositorio();
	}

	public String registrar() {
		try {
			if (!PropriedadesBean.password.equals(
					PropriedadesBean.confirmPassword())) {
				return "";
			}
			Conversador.sistema.criaNovoUsuario(PropriedadesBean.login(),
					PropriedadesBean.password(), PropriedadesBean.name(),
					PropriedadesBean.email());
			SessionBean.sessaoID = Conversador.sistema.abrirSessao(
					PropriedadesBean.login, PropriedadesBean.password);
			return "principal";
		} catch (Exception ex) {
			return "";
		}
	}

	public String logar() {
		try {
			SessionBean.sessaoID = Conversador.sistema.abrirSessao(
					PropriedadesBean.login(), PropriedadesBean.password());
			return "principal";
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}
}