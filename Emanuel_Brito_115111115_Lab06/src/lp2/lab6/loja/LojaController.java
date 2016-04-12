package lp2.lab6.loja;

import java.util.ArrayList;
import java.util.List;

import lp2.exceptions.DadosInvalidosException;
import lp2.exceptions.ErrosLogicaException;
import lp2.exceptions.UpgradeIndisponivelException;
import lp2.exceptions.UsuarioNaoCadastradoException;
import lp2.exceptions.UsuarioX2PInsuficienteException;
import lp2.lab6.jogo.EstiloJogoEnum;
import lp2.lab6.jogo.FactoryDeJogos;
import lp2.lab6.jogo.Jogo;
import lp2.lab6.usuario.FactoryDeUsuarios;
import lp2.lab6.usuario.Noob;
import lp2.lab6.usuario.Usuario;
import lp2.lab6.usuario.Veterano;

public class LojaController implements LojaInterface {

	private List<Usuario> listaUsuarios;

	public LojaController() {
		listaUsuarios = new ArrayList<Usuario>();
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	private Usuario criaUsuario(String nomeUsuario, String loginUsuario,
			String tipoUsuario) throws DadosInvalidosException {
		FactoryDeUsuarios factoryDeUsuarios = new FactoryDeUsuarios();

		return factoryDeUsuarios.criaUsuario(nomeUsuario, loginUsuario,
				tipoUsuario);
	}

	/**
	 * Adiciona um Usuário na Loja
	 * 
	 * @param usuario
	 * @return void
	 * @throws DadosInvalidosException
	 */
	@Override
	public void adicionaUsuario(String nomeUsuario, String loginUsuario,
			String tipoUsuario) throws DadosInvalidosException {
		listaUsuarios.add(criaUsuario(nomeUsuario, loginUsuario, tipoUsuario));
	}

	/**
	 * Busca o usuário na lista via login, e retornar o índice onde o mesmo está
	 * localidado
	 * 
	 * @param login
	 *            o campo identificador de um Usuário
	 * @return índice onde está localizado o usuário na lista. Retorna -1 caso o
	 *         usuário não esteja cadastrado na lista
	 */
	public int buscaIndexUsuario(String login) {
		int index = -1;

		for (int i = 0; i < listaUsuarios.size(); i++) {
			if (listaUsuarios.get(i).getLogin().equalsIgnoreCase(login)) {
				index = i;
				break;
			}
		}

		return index;
	}

	/**
	 * Adiciona uma quantidade de dinheiro a uma determinada conta existente na
	 * loja
	 * 
	 * @param login
	 *            o campo identificador de um Usuário
	 * @param dinheiro
	 *            o valor a ser adicionado na conta do Usuário
	 * @return booleano informando se a operação teve êxito ou não
	 * @throws ErrosLogicaException
	 * @throws FachadaException
	 *             Caso usuário não esteja cadastrado na loja
	 */
	@Override
	public boolean adicionaDinheiroUsuario(String login, double dinheiro)
			throws ErrosLogicaException {
		boolean retorno = false;

		int index = buscaIndexUsuario(login);

		if (index != -1) {
			retorno = listaUsuarios.get(index).adicionaDinheiro(dinheiro);
		} else {
			throw new UsuarioNaoCadastradoException();
		}

		return retorno;
	}

	private Jogo criaJogo(String nomeJogo, double precoJogo, String tipoJogo,
			List<EstiloJogoEnum> estilosJogo) throws DadosInvalidosException {

		FactoryDeJogos factoryDeJogos = new FactoryDeJogos();

		return factoryDeJogos.criaJogo(nomeJogo, precoJogo, tipoJogo,
				estilosJogo);

	}

	/**
	 * Vende um determinado jogo a um determinado usuário cadastrado na loja
	 * 
	 * @param loginUsuario
	 *            o campo identificador de um Usuário
	 * @param jogo
	 *            o Jogo a ser comprado
	 * @return booleano informando se a operação teve êxito ou não
	 * @throws FachadaException
	 *             Caso usuário não esteja cadastrado na loja
	 * @throws ErrosLogicaException
	 *             Caso usuário não possua dinheiro suficiente para comprar o
	 *             jogo
	 */
	@Override
	public boolean vendeJogoUsuario(String loginUsuario, String nomeJogo,
			double precoJogo, String tipoJogo, List<EstiloJogoEnum> estilosJogo)
			throws ErrosLogicaException {
		boolean retorno = false;

		int index = buscaIndexUsuario(loginUsuario);

		if (index != -1) {
			try {
				retorno = listaUsuarios.get(index).compraJogo(
						criaJogo(nomeJogo, precoJogo, tipoJogo, estilosJogo));
			} catch (ErrosLogicaException ele) {
				System.err.println(ele.getMessage());
			} catch (DadosInvalidosException die) {
				System.err.println(die.getMessage());
			}
		} else {
			throw new UsuarioNaoCadastradoException();
		}

		return retorno;
	}

	/**
	 * Retorna as informações de um determinado usuário cadastrado na loja
	 * 
	 * @param login
	 *            o campo identificador de um Usuário
	 * @return todas as informações a respeito do Usuário
	 */
	@Override
	public String informacaoUsuario(String login) {
		String retorno = "=== Central P2-CG ===\n\n";

		int index = buscaIndexUsuario(login);

		if (index != -1) {
			retorno += listaUsuarios.get(index).toString();
		}

		retorno += "\n--------------------------------------------";

		return retorno;
	}

	/**
	 * Transporta todos os valores dos atributos do usuário Noob para o usuário
	 * Veterano
	 * 
	 * @param usuario
	 *            um usuário Noob
	 * @return um usuário Veterano
	 * @throws ErrosLogicaException
	 *             Caso o nome ou login forem inválidos
	 */
	private Veterano noobParaVeterano(Usuario usuario) throws Exception {
		Veterano retorno = new Veterano(usuario.getNome(), usuario.getLogin());

		retorno.setDinheiroRestante(usuario.getDinheiroRestante());
		retorno.setListaJogos(usuario.getListaJogos());
		retorno.setPontuacao(usuario.getPontuacao());
		retorno.setDinheiroGastoComJogos(usuario.getDinheiroGastoComJogos());

		return retorno;
	}

	/**
	 * Evolui um determinado usuário cadastrado na loja de Noob para Veterano
	 * caso o mesmo esteja apto
	 * 
	 * @param login
	 *            o campo identificador de um Usuário
	 * @return booleano informando se a operação teve êxito ou não
	 * @throws FachadaException
	 *             Caso o usuário não possua X2P suficiente ou já seja um
	 *             Veterano ou não esteja cadastrado na loja
	 * @throws ErrosLogicaException
	 *             Caso o nome ou login forem inválidos
	 */
	@Override
	public boolean upgrade(String login) throws Exception {
		boolean retorno = false;

		int index = buscaIndexUsuario(login);

		if (index != -1) {
			if (listaUsuarios.get(index) instanceof Noob) {
				if (listaUsuarios.get(index).getPontuacao().getPontos() >= 1000) {
					Veterano usuarioVeterano = noobParaVeterano(listaUsuarios
							.get(index));
					listaUsuarios.remove(index);
					listaUsuarios.add(index, usuarioVeterano);
					retorno = true;
				} else {
					throw new UsuarioX2PInsuficienteException();
				}
			} else {
				throw new UpgradeIndisponivelException();
			}
		} else {
			throw new UsuarioNaoCadastradoException();
		}
		return retorno;
	}

}
