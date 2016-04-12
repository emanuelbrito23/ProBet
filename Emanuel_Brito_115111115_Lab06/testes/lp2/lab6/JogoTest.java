package lp2.lab6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import lp2.exceptions.DadosInvalidosException;
import lp2.lab6.jogo.EstiloJogoEnum;
import lp2.lab6.jogo.Luta;
import lp2.lab6.jogo.Plataforma;
import lp2.lab6.jogo.RPG;

public class JogoTest {

	@Test
	public void testJogo() {
		try {
			RPG game1 = new RPG("Paper Mario", 75.0);

			assertEquals(true, game1.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));

			Plataforma game2 = new Plataforma("Super Mario World", 30.0);

			assertEquals(true, game2.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));
			assertEquals(true, game2.adicionaEstiloJogo(EstiloJogoEnum.MULTIPLAYER));

			Luta game3 = new Luta("Guilty Gears", 80.0);

			assertEquals(true, game3.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));
			assertEquals(true, game3.adicionaEstiloJogo(EstiloJogoEnum.MULTIPLAYER));

			assertEquals(10, game1.registraJogada(5000, true));

			assertEquals(20, game2.registraJogada(5000, true));

			assertEquals(5, game3.registraJogada(5000, true));

			RPG copiaGame1 = new RPG("Paper Mario", 75.0);
			assertEquals(true, copiaGame1.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));

			assertEquals(true, game1.equals(copiaGame1));
			assertEquals(false, game1.equals(game2));

			assertEquals(10, game1.registraJogada(7000, true));

			String saida = "+ Paper Mario - RPG\n==> Jogou 2 vez(es)\n==> Zerou 2 vez(es)\n==>Maior score: 7000\n";

			assertEquals(saida, game1.toString());

		} catch (DadosInvalidosException je) {
			fail();
		}
	}

	@Test
	public void testJogoInvalido() {
		try {
			RPG game1 = new RPG(null, 75.0);
			fail();
		} catch (DadosInvalidosException je) {
			assertEquals("Nome do jogo nao pode ser nulo ou vazio", je.getMessage());
		}

		try {
			RPG game1 = new RPG("Paper Mario", 0);
			fail();
		} catch (DadosInvalidosException je) {
			assertEquals("Valor do jogo não pode ser menor ou igual a zero", je.getMessage());
		}

		try {
			RPG game1 = new RPG("Paper Mario", 75.0);

			assertEquals(true, game1.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));
			assertEquals(false, game1.adicionaEstiloJogo(EstiloJogoEnum.COMPETITIVO));

		} catch (DadosInvalidosException je) {
			fail();
		}
	}

}
