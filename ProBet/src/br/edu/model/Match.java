package br.edu.model;

import java.util.Date;

public class Match {

	private String dateMatch;
	private String nameCompetition;
	private String nameTeamA;
	private String nameTeamB;
	private Integer scoreTeamA;
	private Integer scoreTeamB;

	public Match() {
		
	}
	
	public Match(String dateMatch, String nameCompetition, String nameTeamA, String nameTeamB, Integer scoreTeamA,
			Integer scoreTeamB) {
		this.dateMatch = dateMatch;
		this.nameCompetition = nameCompetition;
		this.nameTeamA = nameTeamA;
		this.nameTeamB = nameTeamB;
		this.scoreTeamA = scoreTeamA;
		this.scoreTeamB = scoreTeamB;
	}

	public String getDateMatch() {
		return dateMatch;
	}

	public void setDateMatch(String dateMatch) {
		this.dateMatch = dateMatch;
	}

	public String getNameCompetition() {
		return nameCompetition;
	}

	public void setNameCompetition(String nameCompetition) {
		this.nameCompetition = nameCompetition;
	}

	public String getNameTeamA() {
		return nameTeamA;
	}

	public void setNameTeamA(String nameTeamA) {
		this.nameTeamA = nameTeamA;
	}

	public String getNameTeamB() {
		return nameTeamB;
	}

	public void setNameTeamB(String nameTeamB) {
		this.nameTeamB = nameTeamB;
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
