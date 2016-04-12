package lp2.exceptions;

public class NomeUsuarioInvalidoException extends DadosInvalidosException {

	private static final long serialVersionUID = -3572976605204823942L;

	public NomeUsuarioInvalidoException() {
		super("Nome do usuario nao pode ser nulo ou vazio");
	}

}
