package lp2.exceptions;

public class ValorPrecoInvalidoException extends DadosInvalidosException {

	private static final long serialVersionUID = 9168509986045982213L;

	public ValorPrecoInvalidoException() {
		super("Valor do jogo não pode ser menor ou igual a zero");
	}

}
