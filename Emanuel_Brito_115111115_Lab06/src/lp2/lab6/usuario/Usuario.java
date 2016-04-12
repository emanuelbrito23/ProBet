package lp2.lab6.usuario;

import java.util.ArrayList;
import java.util.List;

import lp2.exceptions.DadosInvalidosException;
import lp2.exceptions.ErrosLogicaException;
import lp2.exceptions.LoginInvalidoException;
import lp2.exceptions.NomeUsuarioInvalidoException;
import lp2.lab6.jogo.Jogo;
import lp2.lab6.jogo.X2P;

public abstract class Usuario {

	private String nome;
	private String login;
	private List<Jogo> listaJogos;
	private double dinheiroRestante;
	private double dinheiroGastoComJogos;
	private X2P pontuacao;

	public Usuario(String nome, String login) throws DadosInvalidosException {

		if ((nome != null) && (nome.trim().length() > 0)) {
			this.nome = nome;
		} else {
			throw new NomeUsuarioInvalidoException();
		}

		if ((login != null) && (login.trim().length() > 0)) {
			this.login = login;
		} else {
			throw new LoginInvalidoException();
		}

		this.listaJogos = new ArrayList<Jogo>();
		this.dinheiroRestante = 0;
		this.pontuacao = new X2P();
	}

	public String getNome() {
		return nome;
	}

	public String getLogin() {
		return login;
	}

	public List<Jogo> getListaJogos() {
		return listaJogos;
	}

	public void setListaJogos(List<Jogo> listaJogos) {
		this.listaJogos = listaJogos;
	}

	public double getDinheiroRestante() {
		return dinheiroRestante;
	}

	public void setDinheiroRestante(double dinheiroRestante) {
		this.dinheiroRestante = dinheiroRestante;
	}

	public X2P getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(X2P pontuacao) {
		this.pontuacao = pontuacao;
	}

	public double getDinheiroGastoComJogos() {
		return dinheiroGastoComJogos;
	}

	public void setDinheiroGastoComJogos(double dinheiroGastoComJogos) {
		this.dinheiroGastoComJogos = dinheiroGastoComJogos;
	}

	/**
	 * Adiciona dinheiro a conta do usuário
	 * 
	 * @param dinheiro
	 *            - valor a ser adicionado a conta do usuário
	 * @return booleano informando se a operação teve êxito ou não
	 */
	public boolean adicionaDinheiro(double dinheiro) {
		this.dinheiroRestante += dinheiro;

		return true;
	}

	/**
	 * Busca o jogo na lista via nome do jog, e retornar o índice onde o mesmo
	 * está localidado
	 * 
	 * @param nomeJogo
	 *            o campo identificador de um Usuário
	 * @return índice onde está localizado o jogo na lista. Retorna -1 caso o
	 *         jogo não exista na lista
	 */
	public int buscaJogo(String nomeJogo) {
		int retorno = -1;

		for (int i = 0; i < listaJogos.size(); i++) {
			if (listaJogos.get(i).getNome().equalsIgnoreCase(nomeJogo)) {
				retorno = i;
				break;
			}
		}

		return retorno;
	}

	public abstract void recompensar(String nomeJogo, int score, boolean zerou);

	public abstract void punir(String nomeJogo, int scoreObtido, boolean zerou);

	/**
	 * Compra o jogo caso tenha dinheiro suficiente e adiciona na lista de jogos
	 * do usuário juntamente com os X2P obtidos pela compra
	 * 
	 * @param jogo
	 * @return booleano informando se a operação teve êxito ou não
	 * @throws ErrosLogicaException
	 *             Caso não tenha dinheiro suficiente para realizar a compra
	 */
	public abstract boolean compraJogo(Jogo jogo) throws ErrosLogicaException;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((getLogin() == null) ? 0 : getLogin().hashCode());
		return result;
	}
}
