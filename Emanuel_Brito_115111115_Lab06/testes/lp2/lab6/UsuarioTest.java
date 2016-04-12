package lp2.lab6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import lp2.exceptions.DadosInvalidosException;
import lp2.exceptions.ErrosLogicaException;
import lp2.lab6.jogo.EstiloJogo;
import lp2.lab6.jogo.Jogo;
import lp2.lab6.jogo.RPG;
import lp2.lab6.usuario.Noob;
import lp2.lab6.usuario.Veterano;

public class UsuarioTest {

	@Test
	public void testUsuario() {
		try {
			Noob usuario1 = new Noob("Emanuel José Guimarães Brito", "emanuel.brito");

			Veterano usuario2 = new Veterano("Anonymous da Silva", "anonymous.silva");

			assertEquals(true, usuario1.adicionaDinheiro(100));
			assertEquals(true, usuario2.adicionaDinheiro(100));

			RPG game1 = new RPG("Paper Mario", 75.0);

			assertEquals(true, game1.adicionaEstiloJogo(EstiloJogo.COMPETITIVO));

			assertEquals(true, usuario1.compraJogo(game1));
			assertEquals(true, usuario2.compraJogo(game1));

			assertEquals(750, usuario1.getPontuacao().getPontos());
			assertEquals(1125, usuario2.getPontuacao().getPontos());

			usuario1.registraJogada("Paper Mario", 5000, true);
			usuario2.registraJogada("Paper Mario", 5000, true);

			assertEquals(760, usuario1.getPontuacao().getPontos());
			assertEquals(1135, usuario2.getPontuacao().getPontos());

		} catch (ErrosLogicaException ue) {
			fail();
		} catch (DadosInvalidosException je) {
			fail();
		}
	}

	@Test
	public void testUsuarioInvalido() {
		try {
			Noob usuario1 = new Noob(null, "emanuel.brito");
		} catch (DadosInvalidosException die) {
			assertEquals("Nome do usuario nao pode ser nulo ou vazio", die.getMessage());
		}

		try {
			Noob usuario1 = new Noob("Emanuel José Guimarães Brito", null);
		} catch (DadosInvalidosException die) {
			assertEquals("Login do usuario nao pode ser nulo ou vazio", die.getMessage());
		}

		try {
			Veterano usuario1 = new Veterano(null, "emanuel.brito");
		} catch (DadosInvalidosException die) {
			assertEquals("Nome do usuario nao pode ser nulo ou vazio", die.getMessage());
		}

		try {
			Veterano usuario1 = new Veterano("Emanuel José Guimarães Brito", null);
		} catch (DadosInvalidosException die) {
			assertEquals("Login do usuario nao pode ser nulo ou vazio", die.getMessage());
		}
	}

	@Test
	public void testCompraJogoInvalida() {
		Noob usuario1;
		try {
			usuario1 = new Noob("Emanuel José Guimarães Brito", "emanuel.brito");
			Veterano usuario2 = new Veterano("Anonymous da Silva", "anonymous.silva");

			assertEquals(true, usuario1.adicionaDinheiro(60));
			assertEquals(true, usuario2.adicionaDinheiro(60));

			RPG game1 = new RPG("Paper Mario", 75.0);
			assertEquals(true, game1.adicionaEstiloJogo(EstiloJogo.COMPETITIVO));

			try {
				assertEquals(false, usuario1.compraJogo(game1));
				fail();
			} catch (ErrosLogicaException ue) {
				assertEquals("Usuario não possui dinheiro suficiente", ue.getMessage());
			}

			try {
				assertEquals(true, usuario2.compraJogo(game1));
			} catch (ErrosLogicaException ue) {
				fail();
			}

			List<Jogo> listaTest = new ArrayList<Jogo>();

			assertEquals(listaTest, usuario1.getListaJogos());

			listaTest.add(game1);

			assertEquals(listaTest, usuario2.getListaJogos());
		} catch (DadosInvalidosException je) {
			fail();
		}
	}
}
