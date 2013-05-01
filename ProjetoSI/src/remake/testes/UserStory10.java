package remake.testes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import remake.fachada.FachadaEasyAccept;

public class UserStory10 {

	Cenario cenario;

	@Before
	public void init() throws Exception {
		cenario = new Cenario();
		cenario.populaCenario();

		cenario.abrirSessoes();
	}

	@Test
	public void testCriarTag() throws Exception {
		FachadaEasyAccept fachada = cenario.getFachada();
		String sessaoMark = cenario.getSessaoID("mark");

		String[] tagNames = { "aaa", "bbb", "ccc" };
		// Adiciona as tags aos usuarios
		String[] listaComTagID = cenario.populaTags(sessaoMark, tagNames);

		// Verifica ao criar uma tag o retorno dela é o id da mesma e que seu
		// retorno está linkado a propria tag.
		for (int i = 0; i < listaComTagID.length; i++) {
			Assert.assertNotNull(listaComTagID[i]);
			Assert.assertEquals(tagNames[i], fachada.getNomeTag(sessaoMark, listaComTagID[i]));
		}

		// Adiciona 3 sons ao usuario.
		String[] listagemSomID = cenario.populaListaDeSons(sessaoMark);

		// Adiciona uma tag a cada som.
		for (int i = 0; i < listagemSomID.length; i++) {
			fachada.adicionarTagASom(sessaoMark, tagNames[i], listagemSomID[i]);
		}
		
		for (int i = 0; i < listagemSomID.length; i++) {
			String tagID = fachada.getListaTagsEmSom(sessaoMark, listagemSomID[i]);
			Assert.assertTrue(tagID.contains(listaComTagID[i]));
		}
	}
	
	@Test
	public void testCriarTagInvalido() {
		
	}
}
