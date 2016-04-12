package lp2.lab6.jogo;

import lp2.exceptions.DadosInvalidosException;

public class Luta extends Jogo {

	public Luta(String nome, double preco) throws DadosInvalidosException {
		super(nome, preco);
	}

	/**
	 * Registra uma jogada no jogo informando quantos pontos fez e se zerou o
	 * jogo ou não
	 * 
	 * @param score
	 *            a pontuação obtida na jogada
	 * @param zerou
	 *            <i>true</i> para Jogo zerado e <i>false</i> para Jogo não
	 *            zerado
	 * @return um valor resultante (maior pontuação / 1000) de X2P, caso a
	 *         pontuação obtida na jogada seja um novo recorde
	 */
	@Override
	public int registraJogada(int score, boolean zerou) {
		int retorno = 0;

		if (score > getMaiorPontuacao()) {
			setMaiorPontuacao(score);
			retorno = getMaiorPontuacao() / 1000;
		}

		if (zerou) {
			setQuantidadeVezesZerado(getQuantidadeVezesZerado() + 1);
		}

		setQuantidadeVezesJogado(getQuantidadeVezesJogado() + 1);

		return retorno;
	}

	@Override
	public String toString() {
		return "+ " + getNome() + " - Luta\n" + "==> Jogou " + getQuantidadeVezesJogado() + " vez(es)\n" + "==> Zerou "
				+ getQuantidadeVezesZerado() + " vez(es)\n" + "==>Maior score: " + getMaiorPontuacao() + "\n";
	}

	@Override
	public boolean equals(Object obj) {
		boolean retorno = false;

		if (obj instanceof Luta) {
			Luta jogo = (Luta) obj;

			if ((jogo.getNome().equalsIgnoreCase(getNome())) && (jogo.getEstilosJogo().equals(getEstilosJogo()))) {
				retorno = true;
			}
		}

		return retorno;
	}
}
