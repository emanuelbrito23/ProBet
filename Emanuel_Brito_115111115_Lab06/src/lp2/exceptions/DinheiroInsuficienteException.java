package lp2.exceptions;

public class DinheiroInsuficienteException extends ErrosLogicaException {

	private static final long serialVersionUID = -8560660009259377650L;

	public DinheiroInsuficienteException() {
		super("Usuario não possui dinheiro suficiente");
	}

}
