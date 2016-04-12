package lp2.exceptions;

public class UpgradeIndisponivelException extends ErrosLogicaException {

	private static final long serialVersionUID = -5513479740406719204L;

	public UpgradeIndisponivelException() {
		super("Usuario já é um Veterano");
	}

}
