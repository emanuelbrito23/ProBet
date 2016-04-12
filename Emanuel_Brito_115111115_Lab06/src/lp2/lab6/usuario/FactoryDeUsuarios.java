package lp2.lab6.usuario;

import lp2.exceptions.DadosInvalidosException;

public class FactoryDeUsuarios {

	public Usuario criaUsuario(String nomeUsuario, String loginUsuario, String tipoUsuario)
			throws DadosInvalidosException {
		Usuario retorno = null;

		/*if (tipoUsuario.equalsIgnoreCase("NOOB")) {
			retorno = new Noob(nomeUsuario, loginUsuario);
		} else if (tipoUsuario.equalsIgnoreCase("VETERANO")) {
			retorno = new Veterano(nomeUsuario, loginUsuario);
		}*/
		
		retorno = new Noob(nomeUsuario, loginUsuario);

		return retorno;
	}
}
