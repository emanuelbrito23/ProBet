package lp2.lab6.usuario;

import lp2.exceptions.DadosInvalidosException;
import lp2.exceptions.DinheiroInsuficienteException;
import lp2.exceptions.ErrosLogicaException;
import lp2.lab6.jogo.Jogo;

public class Veterano extends Usuario {

	public Veterano(String nome, String login) throws DadosInvalidosException {
		super(nome, login);
	}

	/**
	 * Compra o jogo com 20% de desconto caso tenha dinheiro suficiente e
	 * adiciona na lista de jogos do usuário juntamente com um valor resultante
	 * (Preço do jogo * 15) de X2P obtidos pela compra
	 * 
	 * @param jogo
	 * @return booleano informando se a operação teve êxito ou não
	 * @throws ErrosLogicaException
	 *             Caso não tenha dinheiro suficiente para realizar a compra
	 */
	@Override
	public boolean compraJogo(Jogo jogo) throws ErrosLogicaException {
		boolean retorno = false;
		double valorTotalJogo = jogo.getPreco() * 0.8;

		if (getDinheiroRestante() >= valorTotalJogo) {
			getListaJogos().add(jogo);
			setDinheiroRestante(getDinheiroRestante() - valorTotalJogo);
			setDinheiroGastoComJogos(getDinheiroGastoComJogos() + valorTotalJogo);
			getPontuacao().adicionaPontos(((int) jogo.getPreco()) * 15);
			retorno = true;
		} else {
			throw new DinheiroInsuficienteException();
		}

		return retorno;
	}

	@Override
	public String toString() {
		String retorno = "Jogador Veterano: " + getLogin() + "\n" + getNome() + " - " + getPontuacao().getPontos()
				+ " x2p\n" + "Lista de Jogos: \n";

		for (int i = 0; i < getListaJogos().size(); i++) {
			retorno += getListaJogos().get(i).toString() + "\n";
		}

		retorno += "Total de preço dos jogos: R$ " + getDinheiroGastoComJogos();

		return retorno;
	}

	@Override
	public boolean equals(Object obj) {
		boolean retorno = false;

		if (obj instanceof Veterano) {
			Veterano usuario = (Veterano) obj;

			if (usuario.getLogin().equalsIgnoreCase(getLogin())) {
				retorno = true;
			}
		}

		return retorno;
	}
}
