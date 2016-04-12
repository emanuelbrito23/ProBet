package lp2.lab6.jogo;

import java.util.ArrayList;
import java.util.List;

import lp2.exceptions.DadosInvalidosException;
import lp2.exceptions.NomeJogoInvalidoException;
import lp2.exceptions.ValorPrecoInvalidoException;

public abstract class Jogo {

	private String nome;
	private double preco;
	private int maiorPontuacao;
	private int quantidadeVezesJogado;
	private int quantidadeVezesZerado;
	private List<EstiloJogoEnum> estilosJogo;

	public Jogo(String nome, double preco) throws DadosInvalidosException {

		if ((nome != null) && (nome.trim().length() > 0)) {
			this.nome = nome;
		} else {
			throw new NomeJogoInvalidoException();
		}

		if (preco > 0) {
			this.preco = preco;
		} else {
			throw new ValorPrecoInvalidoException();
		}

		this.maiorPontuacao = 0;
		this.quantidadeVezesJogado = 0;
		this.quantidadeVezesZerado = 0;
		this.estilosJogo = new ArrayList<EstiloJogoEnum>();
	}

	public int getMaiorPontuacao() {
		return maiorPontuacao;
	}

	public void setMaiorPontuacao(int maiorPontuacao) {
		this.maiorPontuacao = maiorPontuacao;
	}

	public int getQuantidadeVezesJogado() {
		return quantidadeVezesJogado;
	}

	public void setQuantidadeVezesJogado(int quantidadeVezesJogado) {
		this.quantidadeVezesJogado = quantidadeVezesJogado;
	}

	public int getQuantidadeVezesZerado() {
		return quantidadeVezesZerado;
	}

	public void setQuantidadeVezesZerado(int quantidadeVezesZerado) {
		this.quantidadeVezesZerado = quantidadeVezesZerado;
	}

	public String getNome() {
		return nome;
	}

	public double getPreco() {
		return preco;
	}

	public List<EstiloJogoEnum> getEstilosJogo() {
		return estilosJogo;
	}

	/**
	 * Adiciona um Estilo de Jogo ao jogo em questão
	 * 
	 * @param estiloJogo
	 * @return booleano informando se a operação teve êxito ou não
	 */
	public boolean adicionaEstiloJogo(EstiloJogoEnum estiloJogo) {
		boolean retorno = false;

		if (!estilosJogo.contains(estiloJogo)) {
			estilosJogo.add(estiloJogo);
			retorno = true;
		}

		return retorno;
	}

	/**
	 * Registra uma jogada no jogo informando quantos pontos fez e se zerou o
	 * jogo ou não
	 * 
	 * @param score
	 *            a pontuação obtida na jogada
	 * @param zerou
	 *            <i>true</i> para Jogo zerado e <i>false</i> para Jogo não
	 *            zerado
	 * @return quantidade de XP2 equivalente a jogada
	 */
	public abstract int registraJogada(int score, boolean zerou);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getEstilosJogo() == null) ? 0 : getEstilosJogo().hashCode());
		result = prime * result + ((getNome() == null) ? 0 : getNome().hashCode());
		return result;
	}

}
