package lp2.exceptions;

public class UsuarioX2PInsuficienteException extends ErrosLogicaException {

	private static final long serialVersionUID = -8506047740673643224L;

	public UsuarioX2PInsuficienteException() {
		super("Usuario não possui X2P suficiente");
	}

}
