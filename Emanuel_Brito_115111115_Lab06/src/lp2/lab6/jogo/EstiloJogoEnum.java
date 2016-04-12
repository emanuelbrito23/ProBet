package lp2.lab6.jogo;

public enum EstiloJogoEnum {

	ONLINE(1), OFFLINE(2), MULTIPLAYER(3), COOPERATIVO(4), COMPETITIVO(5);

	private int valorEstiloJogo;

	EstiloJogoEnum(int valorEstiloJogo) {
		this.valorEstiloJogo = valorEstiloJogo;
	}

	public int getValorEstiloJogo() {
		return valorEstiloJogo;
	}
}
