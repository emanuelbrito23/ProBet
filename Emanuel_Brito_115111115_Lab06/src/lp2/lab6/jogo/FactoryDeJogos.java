package lp2.lab6.jogo;

import java.util.List;

import lp2.exceptions.DadosInvalidosException;

public class FactoryDeJogos {

	public Jogo criaJogo(String nomeJogo, double precoJogo, String tipoJogo, List<EstiloJogoEnum> estilosJogo)
			throws DadosInvalidosException {
		Jogo retorno = null;

		if (tipoJogo.equalsIgnoreCase("RPG")) {
			retorno = new RPG(nomeJogo, precoJogo);
		} else if (tipoJogo.equalsIgnoreCase("PLATAFORMA")) {
			retorno = new Plataforma(nomeJogo, precoJogo);
		} else if (tipoJogo.equalsIgnoreCase("LUTA")) {
			retorno = new Luta(nomeJogo, precoJogo);
		}
		return retorno;
	}
}
