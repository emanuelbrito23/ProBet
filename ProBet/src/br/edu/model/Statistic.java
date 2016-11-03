package br.edu.model;

import br.edu.util.Constantes;

public class Statistic {

	private Integer qtdMatches;
	private Integer qtdGoalsPro;
	private Integer qtdGoalsContra;
	
	public Statistic() {
		
	}

	public Integer getQtdMatches() {
		return qtdMatches;
	}

	public void setQtdMatches(Integer qtdMatches) {
		this.qtdMatches = qtdMatches;
	}

	public Integer getQtdGoalsPro() {
		return qtdGoalsPro;
	}

	public void setQtdGoalsPro(Integer qtdGoalsPro) {
		this.qtdGoalsPro = qtdGoalsPro;
	}

	public Integer getQtdGoalsContra() {
		return qtdGoalsContra;
	}

	public void setQtdGoalsContra(Integer qtdGoalsContra) {
		this.qtdGoalsContra = qtdGoalsContra;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("----------------------");
		sb.append(Constantes.PULA_LINHA);
		sb.append("Quantidade de Jogos: ");
		sb.append(this.getQtdMatches());
		sb.append(Constantes.PULA_LINHA);
		sb.append("Quantidade de Gols Pró: ");
		sb.append(this.getQtdGoalsPro());
		sb.append(Constantes.PULA_LINHA);
		sb.append("Quantidade de Gols Sofridos: ");
		sb.append(this.getQtdGoalsContra());
		sb.append(Constantes.PULA_LINHA);
		sb.append("----------------------");
		
		return sb.toString(); 
	}
}
