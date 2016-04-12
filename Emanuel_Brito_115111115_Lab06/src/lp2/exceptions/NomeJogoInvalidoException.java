package lp2.exceptions;

public class NomeJogoInvalidoException extends DadosInvalidosException {

	private static final long serialVersionUID = 3086756640299151578L;

	public NomeJogoInvalidoException() {
		super("Nome do jogo nao pode ser nulo ou vazio");
	}

}
