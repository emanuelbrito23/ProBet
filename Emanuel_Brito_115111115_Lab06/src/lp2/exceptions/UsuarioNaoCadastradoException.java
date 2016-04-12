package lp2.exceptions;

public class UsuarioNaoCadastradoException extends ErrosLogicaException {

	private static final long serialVersionUID = 7313849504849006927L;

	public UsuarioNaoCadastradoException() {
		super("Usuario não possui X2P suficiente");
	}
}