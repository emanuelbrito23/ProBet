package lp2.exceptions;

public class LoginInvalidoException extends DadosInvalidosException {

	private static final long serialVersionUID = 8000758923359432659L;

	public LoginInvalidoException() {
		super("Login do usuario nao pode ser nulo ou vazio");
	}

}
