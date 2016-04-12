package lp2.lab6.jogo;

public class X2P {

	private int pontos;

	public X2P() {
		pontos = 0;
	}

	public int getPontos() {
		return pontos;
	}

	/**
	 * Adiciona uma quantidade de pontos que se convertem a X2P
	 * 
	 * @param pontos
	 *            quantidade de pontos a serem adicionados
	 */
	public void adicionaPontos(int pontos) {
		this.pontos += pontos;
	}

	public void removePontos(int pontos) {
		this.pontos -= pontos;
	}
}
