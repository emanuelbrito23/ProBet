package lp2.lab6.usuario;

import lp2.exceptions.DadosInvalidosException;
import lp2.exceptions.DinheiroInsuficienteException;
import lp2.exceptions.ErrosLogicaException;
import lp2.lab6.jogo.EstiloJogoEnum;
import lp2.lab6.jogo.Jogo;

public class Noob extends Usuario {

	public Noob(String nome, String login) throws DadosInvalidosException {
		super(nome, login);
	}

	@Override
	public void recompensar(String nomeJogo, int scoreObtido, boolean zerou) {
		int index = buscaJogo(nomeJogo);
		int pontuacaoRecompensa = 0;

		if (index != -1) {
			if (getListaJogos().get(index).getEstilosJogo()
					.contains(EstiloJogoEnum.OFFLINE)) {
				pontuacaoRecompensa += 30;
			}

			if (getListaJogos().get(index).getEstilosJogo()
					.contains(EstiloJogoEnum.MULTIPLAYER)) {
				pontuacaoRecompensa += 10;
			}

			getPontuacao().adicionaPontos(pontuacaoRecompensa);
			getPontuacao().adicionaPontos(
					getListaJogos().get(index).registraJogada(scoreObtido,
							zerou));
		}

	}

	@Override
	public void punir(String nomeJogo, int scoreObtido, boolean zerou) {
		int index = buscaJogo(nomeJogo);
		int pontuacaoPunicao = 0;

		if (index != -1) {
			if (getListaJogos().get(index).getEstilosJogo()
					.contains(EstiloJogoEnum.ONLINE)) {
				pontuacaoPunicao += 10;
			}

			if (getListaJogos().get(index).getEstilosJogo()
					.contains(EstiloJogoEnum.COMPETITIVO)) {
				pontuacaoPunicao += 20;
			}

			if (getListaJogos().get(index).getEstilosJogo()
					.contains(EstiloJogoEnum.COOPERATIVO)) {
				pontuacaoPunicao += 50;
			}

			getPontuacao().removePontos(pontuacaoPunicao);
			getPontuacao().adicionaPontos(
					getListaJogos().get(index).registraJogada(scoreObtido,
							zerou));
		}

	}

	/**
	 * Compra o jogo com 10% de desconto caso tenha dinheiro suficiente e
	 * adiciona na lista de jogos do usuário juntamente com um valor resultante
	 * (Preço do jogo * 10) de X2P obtidos pela compra
	 * 
	 * @param jogo
	 * @return booleano informando se a operação teve êxito ou não
	 * @throws ErrosLogicaException
	 *             Caso não tenha dinheiro suficiente para realizar a compra
	 */
	@Override
	public boolean compraJogo(Jogo jogo) throws ErrosLogicaException {
		boolean retorno = false;
		double valorTotalJogo = jogo.getPreco() * 0.9;

		if (getDinheiroRestante() >= valorTotalJogo) {
			getListaJogos().add(jogo);
			setDinheiroRestante(getDinheiroRestante() - valorTotalJogo);
			setDinheiroGastoComJogos(getDinheiroGastoComJogos()
					+ valorTotalJogo);
			getPontuacao().adicionaPontos(((int) jogo.getPreco()) * 10);
			retorno = true;
		} else {
			throw new DinheiroInsuficienteException();
		}

		return retorno;
	}

	@Override
	public String toString() {
		String retorno = "Jogador Noob: " + getLogin() + "\n" + getNome()
				+ " - " + getPontuacao().getPontos() + " x2p\n"
				+ "Lista de Jogos: \n";

		for (int i = 0; i < getListaJogos().size(); i++) {
			retorno += getListaJogos().get(i).toString() + "\n";
		}

		retorno += "Total de preço dos jogos: R$ " + getDinheiroGastoComJogos();

		return retorno;
	}

	@Override
	public boolean equals(Object obj) {
		boolean retorno = false;

		if (obj instanceof Noob) {
			Noob usuario = (Noob) obj;

			if (usuario.getLogin().equalsIgnoreCase(getLogin())) {
				retorno = true;
			}
		}

		return retorno;
	}
}
