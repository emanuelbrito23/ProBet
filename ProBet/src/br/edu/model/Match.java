package br.edu.model;

import java.util.Date;

import br.edu.util.Constantes;

public class Match {

	private Date dateMatch;
	private String nameCompetition;
	private String yearCompetition;
	private String nameTeamA;
	private String nameTeamB;
	private Integer scoreTeamA;
	private Integer scoreTeamB;

	public Match() {
		
	}
	
	public Match(Date dateMatch, String nameCompetition, String yearCompetition, String nameTeamA, String nameTeamB, Integer scoreTeamA,
			Integer scoreTeamB) {
		this.dateMatch = dateMatch;
		this.nameCompetition = nameCompetition;
		this.yearCompetition = yearCompetition;
		this.nameTeamA = nameTeamA;
		this.nameTeamB = nameTeamB;
		this.scoreTeamA = scoreTeamA;
		this.scoreTeamB = scoreTeamB;
	}

	public Date getDateMatch() {
		return dateMatch;
	}

	public void setDateMatch(Date dateMatch) {
		this.dateMatch = dateMatch;
	}

	public String getNameCompetition() {
		return nameCompetition;
	}

	public void setNameCompetition(String nameCompetition) {
		this.nameCompetition = nameCompetition.substring(0, (nameCompetition.length() - 6)).toUpperCase()
				.replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO)
				.replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO);
	}

	public String getYearCompetition() {
		return yearCompetition;
	}

	public void setYearCompetition(String yearCompetition) {
		this.yearCompetition = yearCompetition.substring((yearCompetition.length() - 6), (yearCompetition.length() - 1))
				.trim();
	}

	public String getNameTeamA() {
		return nameTeamA;
	}

	public void setNameTeamA(String nameTeamA) {
		this.nameTeamA = nameTeamA.toUpperCase().replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO)
				.replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO);
	}

	public String getNameTeamB() {
		return nameTeamB;
	}

	public void setNameTeamB(String nameTeamB) {
		this.nameTeamB = nameTeamB.toUpperCase().replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO)
				.replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO);
	}

	public Integer getScoreTeamA() {
		return scoreTeamA;
	}

	public void setScoreTeamA(Integer scoreTeamA) {
		this.scoreTeamA = scoreTeamA;
	}

	public Integer getScoreTeamB() {
		return scoreTeamB;
	}

	public void setScoreTeamB(Integer score) {
		this.scoreTeamB = score;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(this.dateMatch);
		sb.append("	");
		sb.append(this.nameCompetition);
		sb.append("	");
		sb.append(this.yearCompetition);
		sb.append("	");
		sb.append(this.nameTeamA);
		sb.append("	");
		sb.append(this.scoreTeamA);
		sb.append("	");
		sb.append(this.scoreTeamB);
		sb.append("	");
		sb.append(this.nameTeamB);
		
		return sb.toString();
	}
}
