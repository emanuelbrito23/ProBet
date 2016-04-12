package lp2.lab6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import lp2.exceptions.DadosInvalidosException;
import lp2.exceptions.ErrosLogicaException;
import lp2.lab6.jogo.EstiloJogoEnum;
import lp2.lab6.jogo.Luta;
import lp2.lab6.jogo.RPG;
import lp2.lab6.loja.LojaController;
import lp2.lab6.usuario.Noob;
import lp2.lab6.usuario.Veterano;

public class FachadaTest {

	@Test
	public void testAdicionaDinheiroUsuario() {
		try {
			LojaController loja = new LojaController();

			Noob usuario1 = new Noob("Emanuel José Guimarães Brito", "emanuel.brito");

			Veterano usuario2 = new Veterano("Anonymous da Silva", "anonymous.silva");

			loja.adicionaUsuario(usuario1);
			loja.adicionaUsuario(usuario2);

			assertEquals(true, loja.adicionaDinheiroUsuario("emanuel.brito", 100));
			assertEquals(true, loja.adicionaDinheiroUsuario("anonymous.silva", 100));

		} catch (ErrosLogicaException ue) {
			fail();
		} catch (DadosInvalidosException e) {
			fail();
		}
	}

	@Test
	public void testAdicionaDinheiroUsuarioInvalido() {
		try {
			LojaController loja = new LojaController();

			Noob usuario1 = new Noob("Emanuel José Guimarães Brito", "emanuel.brito");

			loja.adicionaUsuario(usuario1);

			assertEquals(true, loja.adicionaDinheiroUsuario("emanuel.brito", 100));
			assertEquals(false, loja.adicionaDinheiroUsuario("anonymous.silva", 100));
			fail();
		} catch (ErrosLogicaException ue) {
			fail();
		} catch (DadosInvalidosException die) {
			assertEquals("Usuario não cadastrado na loja", die.getMessage());
		}
	}

	@Test
	public void testVendaJogoUsuario() {
		try {
			LojaController loja = new LojaController();

			Noob usuario1 = new Noob("Emanuel José Guimarães Brito", "emanuel.brito");

			Veterano usuario2 = new Veterano("Anonymous da Silva", "anonymous.silva");

			loja.adicionaUsuario(usuario1);
			loja.adicionaUsuario(usuario2);

			assertEquals(true, loja.adicionaDinheiroUsuario("emanuel.brito", 100));
			assertEquals(true, loja.adicionaDinheiroUsuario("anonymous.silva", 100));

			RPG game1 = new RPG("Paper Mario", 75.0);

			assertEquals(true, game1.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));

			assertEquals(true, loja.vendeJogoUsuario("emanuel.brito", game1));
			assertEquals(true, loja.vendeJogoUsuario("anonymous.silva", game1));

			assertEquals(750, usuario1.getPontuacao().getPontos());
			assertEquals(1125, usuario2.getPontuacao().getPontos());

		} catch (ErrosLogicaException ue) {
			fail();
		} catch (DadosInvalidosException je) {
			fail();
		}
	}

	@Test
	public void testVendaJogoUsuarioInvalido() {
		try {
			LojaController loja = new LojaController();

			Noob usuario1 = new Noob("Emanuel José Guimarães Brito", "emanuel.brito");

			Veterano usuario2 = new Veterano("Anonymous da Silva", "anonymous.silva");

			loja.adicionaUsuario(usuario1);

			assertEquals(true, loja.adicionaDinheiroUsuario("emanuel.brito", 100));

			RPG game1 = new RPG("Paper Mario", 75.0);
			assertEquals(true, game1.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));

			assertEquals(true, loja.vendeJogoUsuario("emanuel.brito", game1));

			try {
				assertEquals(false, loja.vendeJogoUsuario("anonymous.silva", game1));
				fail();
			} catch (ErrosLogicaException ele) {
				assertEquals("Usuario não cadastrado na loja", ele.getMessage());
			}

			Luta game3 = new Luta("Guilty Gears", 80.0);
			assertEquals(true, game3.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));
			assertEquals(true, game3.adicionaEstiloJogo(EstiloJogoEnum.MULTIPLAYER));

			try {
				assertEquals(false, loja.vendeJogoUsuario("emanuel.brito", game3));
				fail();
			} catch (ErrosLogicaException ue) {
				assertEquals("Usuario não possui dinheiro suficiente", ue.getMessage());
			}

			assertEquals(750, usuario1.getPontuacao().getPontos());
		} catch (ErrosLogicaException ue) {
			fail();
		} catch (DadosInvalidosException je) {
			fail();
		}
	}

	@Test
	public void testInformacaoUsuario() {
		try {
			LojaController loja = new LojaController();

			Noob usuario1 = new Noob("Emanuel José Guimarães Brito", "emanuel.brito");

			Veterano usuario2 = new Veterano("Anonymous da Silva", "anonymous.silva");

			loja.adicionaUsuario(usuario1);
			loja.adicionaUsuario(usuario2);

			assertEquals(true, loja.adicionaDinheiroUsuario("emanuel.brito", 100));
			assertEquals(true, loja.adicionaDinheiroUsuario("anonymous.silva", 200));

			RPG game1 = new RPG("Paper Mario", 75.0);
			assertEquals(true, game1.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));

			Luta game3 = new Luta("Guilty Gears", 80.0);
			assertEquals(true, game3.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));
			assertEquals(true, game3.adicionaEstiloJogo(EstiloJogoEnum.MULTIPLAYER));

			assertEquals(true, loja.vendeJogoUsuario("emanuel.brito", game1));

			assertEquals(true, loja.vendeJogoUsuario("anonymous.silva", game1));
			assertEquals(true, loja.vendeJogoUsuario("anonymous.silva", game3));

			String saidaUsuario1 = "=== Central P2-CG ===\n\nemanuel.brito\nEmanuel José Guimarães Brito - Jogador Noob\nLista de Jogos: \n+ Paper Mario - RPG\n==> Jogou 0 vez(es)\n==> Zerou 0 vez(es)\n==>Maior score: 0\n\nTotal de preço dos jogos: R$ 67.5\n--------------------------------------------";
			String saidaUsuario2 = "=== Central P2-CG ===\n\nanonymous.silva\nAnonymous da Silva - Jogador Veterano\nLista de Jogos: \n+ Paper Mario - RPG\n==> Jogou 0 vez(es)\n==> Zerou 0 vez(es)\n==>Maior score: 0\n\n+ Guilty Gears - Luta\n==> Jogou 0 vez(es)\n==> Zerou 0 vez(es)\n==>Maior score: 0\n\nTotal de preço dos jogos: R$ 124.0\n--------------------------------------------";

			assertEquals(saidaUsuario1, loja.informacaoUsuario("emanuel.brito"));
			assertEquals(saidaUsuario2, loja.informacaoUsuario("anonymous.silva"));

		} catch (ErrosLogicaException ue) {
			fail();
		} catch (DadosInvalidosException je) {
			fail();
		}
	}

	@Test
	public void testUpgradeUsuario() {
		try {
			LojaController loja = new LojaController();

			Noob usuario1 = new Noob("Emanuel José Guimarães Brito", "emanuel.brito");

			loja.adicionaUsuario(usuario1);

			assertEquals(true, loja.adicionaDinheiroUsuario("emanuel.brito", 200));

			RPG game1 = new RPG("Paper Mario", 75.0);
			assertEquals(true, game1.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));

			Luta game3 = new Luta("Guilty Gears", 80.0);
			assertEquals(true, game3.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));
			assertEquals(true, game3.adicionaEstiloJogo(EstiloJogoEnum.MULTIPLAYER));

			assertEquals(true, loja.vendeJogoUsuario("emanuel.brito", game1));
			assertEquals(true, loja.vendeJogoUsuario("emanuel.brito", game3));

			usuario1.registraJogada("Guilty Gears", 80000, true);

			assertEquals(1630, usuario1.getPontuacao().getPontos());

			assertEquals(true, loja.getListaUsuarios().get(loja.buscaIndexUsuario("emanuel.brito")) instanceof Noob);

			String saidaUsuario1 = "=== Central P2-CG ===\n\nemanuel.brito\nEmanuel José Guimarães Brito - Jogador Noob\nLista de Jogos: \n+ Paper Mario - RPG\n==> Jogou 0 vez(es)\n==> Zerou 0 vez(es)\n==>Maior score: 0\n\n+ Guilty Gears - Luta\n==> Jogou 1 vez(es)\n==> Zerou 1 vez(es)\n==>Maior score: 80000\n\nTotal de preço dos jogos: R$ 139.5\n--------------------------------------------";

			assertEquals(saidaUsuario1, loja.informacaoUsuario("emanuel.brito"));

			try {
				assertEquals(true, loja.upgrade("emanuel.brito"));
			} catch (Exception e) {
				fail();
			}

			assertEquals(true,
					loja.getListaUsuarios().get(loja.buscaIndexUsuario("emanuel.brito")) instanceof Veterano);

			saidaUsuario1 = "=== Central P2-CG ===\n\nemanuel.brito\nEmanuel José Guimarães Brito - Jogador Veterano\nLista de Jogos: \n+ Paper Mario - RPG\n==> Jogou 0 vez(es)\n==> Zerou 0 vez(es)\n==>Maior score: 0\n\n+ Guilty Gears - Luta\n==> Jogou 1 vez(es)\n==> Zerou 1 vez(es)\n==>Maior score: 80000\n\nTotal de preço dos jogos: R$ 139.5\n--------------------------------------------";

			assertEquals(saidaUsuario1, loja.informacaoUsuario("emanuel.brito"));

		} catch (ErrosLogicaException ue) {
			fail();
		} catch (DadosInvalidosException je) {
			fail();
		}
	}

	@Test
	public void testUpgradeUsuarioInvalido() {
		try {
			LojaController loja = new LojaController();

			Noob usuario1 = new Noob("Emanuel José Guimarães Brito", "emanuel.brito");

			Veterano usuario2 = new Veterano("Anonymous da Silva", "anonymous.silva");

			loja.adicionaUsuario(usuario1);
			loja.adicionaUsuario(usuario2);

			assertEquals(true, loja.adicionaDinheiroUsuario("emanuel.brito", 200));

			RPG game1 = new RPG("Paper Mario", 75.0);
			assertEquals(true, game1.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));

			assertEquals(true, loja.vendeJogoUsuario("emanuel.brito", game1));

			assertEquals(750, usuario1.getPontuacao().getPontos());

			try {
				assertEquals(false, loja.upgrade("emanuel.brito"));
				fail();
			} catch (Exception e) {
				assertEquals("Usuario não possui X2P suficiente", e.getMessage());
			}

			try {
				assertEquals(false, loja.upgrade("anonymous.silva"));
				fail();
			} catch (Exception e) {
				assertEquals("Usuario já é um Veterano", e.getMessage());
			}

		} catch (ErrosLogicaException ue) {
			fail();
		} catch (DadosInvalidosException je) {
			fail();
		}
	}

}
