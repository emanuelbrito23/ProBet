package lp2.lab6.jogo;

import lp2.exceptions.DadosInvalidosException;

public class Plataforma extends Jogo {

	public Plataforma(String nome, double preco) throws DadosInvalidosException {
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
	 * @return um valor equivalente a 20 - X2P caso tenha zerado o jogo nessa jogada
	 */
	@Override
	public int registraJogada(int score, boolean zerou) {
		int retorno = 0;

		if (score > getMaiorPontuacao()) {
			setMaiorPontuacao(score);
		}

		if (zerou) {
			setQuantidadeVezesZerado(getQuantidadeVezesZerado() + 1);
			retorno = 20;
		}

		setQuantidadeVezesJogado(getQuantidadeVezesJogado() + 1);

		return retorno;
	}

	@Override
	public String toString() {
		return "+ " + getNome() + " - Plataforma\n" + "==> Jogou " + getQuantidadeVezesJogado() + " vez(es)\n"
				+ "==> Zerou " + getQuantidadeVezesZerado() + " vez(es)\n" + "==>Maior score: " + getMaiorPontuacao()
				+ "\n";
	}

	@Override
	public boolean equals(Object obj) {
		boolean retorno = false;

		if (obj instanceof Plataforma) {
			Plataforma jogo = (Plataforma) obj;

			if ((jogo.getNome().equalsIgnoreCase(getNome())) && (jogo.getEstilosJogo().equals(getEstilosJogo()))) {
				retorno = true;
			}
		}

		return retorno;
	}

}
