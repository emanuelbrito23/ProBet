package br.edu.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import br.edu.model.Match;
import br.edu.model.Statistic;
import br.edu.util.Constantes;

public class ConnectionJDBC {
	
	private Connection connection = new ConnectionFactory().getConnection();
	
	private int getTeamByName(String nameTeam) {
		StringBuffer sb = new StringBuffer();
		int idTeam = Constantes.VALOR_INVALIDO;
		
		sb.append("SELECT \"ID_TEAM\" FROM \"TEAM\" ");
		sb.append("WHERE \"NM_TEAM\" = ?			");
		
		try {
			PreparedStatement psmt = connection.prepareStatement(sb.toString());
			psmt.setString(1, nameTeam);
		
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				idTeam = rs.getInt("ID_TEAM");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return idTeam;
	}

	private int getCompetitionByName(String nameCompetition, String year) {
		StringBuffer sb = new StringBuffer();
		int idCompetition = Constantes.VALOR_INVALIDO;
		
		sb.append("SELECT \"ID_COMPETITION\" FROM \"COMPETITION\" 	");
		sb.append("WHERE \"NM_COMPETITION\" = ? AND \"NM_YEAR\" = ?	");
		
		try {
			PreparedStatement psmt = connection.prepareStatement(sb.toString());
			psmt.setString(1, nameCompetition);
			psmt.setString(2, year);
			
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				idCompetition = rs.getInt("ID_COMPETITION");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return idCompetition;
	}
	
	private boolean alreadyInsertedMatch(Match match) {
		boolean verification = false;
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT \"ID_MATCH\" FROM \"MATCH\" 											");
		sb.append("WHERE \"DT_MATCH\" = ? AND \"ID_COMPETITION\" = ? 							");
		sb.append("AND \"ID_HOME\" = ? AND \"NR_SCORE_HOME\" = ? AND \"NR_SCORE_VISITOR\" = ?	");
		sb.append("AND \"ID_VISITOR\" = ?														");
		
		int idCompetition = insertCompetition(match.getNameCompetition(), match.getYearCompetition());
		int idTeamHome = insertTeam(match.getNameTeamA());
		int idTeamVisitor = insertTeam(match.getNameTeamB());
		
		try {
			PreparedStatement psmt = connection.prepareStatement(sb.toString());
			psmt.setDate(1, new Date(match.getDateMatch().getTime()));
			psmt.setInt(2, idCompetition);
			psmt.setInt(3, idTeamHome);
			psmt.setInt(4, match.getScoreTeamA());
			psmt.setInt(5, match.getScoreTeamB());
			psmt.setInt(6, idTeamVisitor);
			
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				verification = rs.getInt("ID_MATCH") != Constantes.VALOR_INVALIDO;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return verification;
	}
	
	public int insertTeam(String nameTeam) {
		int resultVerification = getTeamByName(nameTeam);
		
		if (resultVerification == Constantes.VALOR_INVALIDO) {
			StringBuffer sb = new StringBuffer();
			
			sb.append("INSERT INTO \"TEAM\" (\"NM_TEAM\") 	");
			sb.append("VALUES (?)							");
			
			try {
				PreparedStatement psmt = connection.prepareStatement(sb.toString());
				psmt.setString(1, nameTeam);
				
				psmt.execute();
				
				resultVerification = getTeamByName(nameTeam);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		return resultVerification;
	}
	
	public int insertCompetition(String nameCompetition, String year) {
		int resultVerification = getCompetitionByName(nameCompetition, year);
		
		if (resultVerification == Constantes.VALOR_INVALIDO) {
			StringBuffer sb = new StringBuffer();
			
			sb.append("INSERT INTO \"COMPETITION\" 			");
			sb.append("(\"NM_COMPETITION\", \"NM_YEAR\")	");
			sb.append("VALUES (?, ?)					");
			
			try {
				PreparedStatement psmt = connection.prepareStatement(sb.toString());
				psmt.setString(1, nameCompetition);
				psmt.setString(2, year);
				
				psmt.execute();
				
				resultVerification = getCompetitionByName(nameCompetition, year);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		
		return resultVerification;
	}
	
	public void insertMatch(Match match) {
		if (!alreadyInsertedMatch(match)) {
			StringBuffer sb = new StringBuffer();
			
			sb.append("INSERT INTO \"MATCH\"																					");
			sb.append("(\"DT_MATCH\", \"ID_COMPETITION\", \"ID_HOME\", \"NR_SCORE_HOME\", \"NR_SCORE_VISITOR\", \"ID_VISITOR\")	");
			sb.append("VALUES (?, ?, ?, ?, ?, ?)																	");
			
			try {
				PreparedStatement psmt = connection.prepareStatement(sb.toString());
				psmt.setDate(1, new Date(match.getDateMatch().getTime()));
				psmt.setInt(2, insertCompetition(match.getNameCompetition(), match.getYearCompetition()));
				psmt.setInt(3, insertTeam(match.getNameTeamA()));
				psmt.setInt(4, match.getScoreTeamA());
				psmt.setInt(5, match.getScoreTeamB());
				psmt.setInt(6, insertTeam(match.getNameTeamB()));
				
				psmt.execute();
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public Statistic getStatisticCompetition(String nameCompetition, String year) {
		StringBuffer sb = new StringBuffer();
		Statistic statistic = new Statistic();
		
		sb.append("SELECT COUNT(*) AS TOTAL_MATCH, SUM(M.\"NR_SCORE_HOME\") AS TOTAL_HOME, SUM(M.\"NR_SCORE_VISITOR\") AS TOTAL_VISITOR FROM \"MATCH\" M	");
		sb.append("INNER JOIN \"COMPETITION\" C ON C.\"ID_COMPETITION\" = M.\"ID_COMPETITION\"																");
		sb.append("WHERE C.\"NM_COMPETITION\" = ? AND C.\"NM_YEAR\" = ?																						");
		
		try {
			PreparedStatement psmt = connection.prepareStatement(sb.toString());
			psmt.setString(1, nameCompetition);
			psmt.setString(2, year);
			
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				statistic.setQtdMatches(rs.getInt("TOTAL_MATCH"));
				statistic.setQtdGoalsPro(rs.getInt("TOTAL_HOME"));
				statistic.setQtdGoalsContra(rs.getInt("TOTAL_VISITOR"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return statistic;
	}
	
	public Statistic getStatisticTeamHomeYears(String nameTeam, int date) {
		StringBuffer sb = new StringBuffer();
		Statistic statistic = new Statistic();
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, date);
		java.util.Date yearAgo = now.getTime();
		
		sb.append("SELECT COUNT(*) AS TOTAL_MATCH, SUM(\"NR_SCORE_HOME\") AS TOTAL_HOME, SUM(\"NR_SCORE_VISITOR\") AS TOTAL_VISITOR FROM \"MATCH\"  ");
		sb.append("WHERE \"ID_MATCH\" IN (SELECT M.\"ID_MATCH\" FROM \"MATCH\" M                                                                    ");
		sb.append("INNER JOIN \"TEAM\" T ON T.\"ID_TEAM\" = M.\"ID_HOME\"                                                                           ");
		sb.append("INNER JOIN \"COMPETITION\" C ON M.\"ID_COMPETITION\" = C.\"ID_COMPETITION\"                                                      ");
		sb.append("WHERE T.\"NM_TEAM\" = ?                                                                                                  	    ");
		sb.append("AND C.\"NM_COMPETITION\" NOT IN ('AMISTOSOS', 'HYBRID FRIENDLIES', 'COPA PAULISTA', 'COPA GOVERNADOR')                       	");
		sb.append("AND M.\"DT_MATCH\" >= ?)                                                                                         		");
		
		try {
			PreparedStatement psmt = connection.prepareStatement(sb.toString());
			psmt.setString(1, nameTeam);
			psmt.setDate(2, new Date(yearAgo.getTime()));
			
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				statistic.setQtdMatches(rs.getInt("TOTAL_MATCH"));
				statistic.setQtdGoalsPro(rs.getInt("TOTAL_HOME"));
				statistic.setQtdGoalsContra(rs.getInt("TOTAL_VISITOR"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return statistic;
	}
	
	public Statistic getStatisticTeamHomeCompetition(String nameTeam, String nameCompetition, String year) {
		StringBuffer sb = new StringBuffer();
		Statistic statistic = new Statistic();
		
		sb.append("SELECT COUNT(*) AS TOTAL_MATCH, SUM(M.\"NR_SCORE_HOME\") AS TOTAL_HOME, SUM(M.\"NR_SCORE_VISITOR\") AS TOTAL_VISITOR FROM \"MATCH\" M ");
		sb.append("INNER JOIN \"TEAM\" T ON T.\"ID_TEAM\" = M.\"ID_HOME\"                                                                                ");
		sb.append("INNER JOIN \"COMPETITION\" C ON M.\"ID_COMPETITION\" = C.\"ID_COMPETITION\"                                                           ");
		sb.append("WHERE T.\"NM_TEAM\" = ?                                                                                                               ");
		sb.append("AND C.\"NM_COMPETITION\" = ? AND C.\"NM_YEAR\" = ?                                                                                    ");
		
		try {
			PreparedStatement psmt = connection.prepareStatement(sb.toString());
			psmt.setString(1, nameTeam);
			psmt.setString(2, nameCompetition);
			psmt.setString(3, year);
			
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				statistic.setQtdMatches(rs.getInt("TOTAL_MATCH"));
				statistic.setQtdGoalsPro(rs.getInt("TOTAL_HOME"));
				statistic.setQtdGoalsContra(rs.getInt("TOTAL_VISITOR"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return statistic;
	}
	
	public Statistic getStatisticTeamHomeLastSixGames(String nameTeam) {
		StringBuffer sb = new StringBuffer();
		Statistic statistic = new Statistic();
		
		sb.append("SELECT COUNT(*) AS TOTAL_MATCH, SUM(\"NR_SCORE_HOME\") AS TOTAL_HOME, SUM(\"NR_SCORE_VISITOR\") AS TOTAL_VISITOR FROM \"MATCH\"  ");
		sb.append("WHERE \"ID_MATCH\" IN (SELECT M.\"ID_MATCH\" FROM \"MATCH\" M                                                                    ");
		sb.append("INNER JOIN \"TEAM\" T ON T.\"ID_TEAM\" = M.\"ID_HOME\"                                                                           ");
		sb.append("INNER JOIN \"COMPETITION\" C ON M.\"ID_COMPETITION\" = C.\"ID_COMPETITION\"                                                      ");
		sb.append("WHERE T.\"NM_TEAM\" = ?                                                                                                          ");
		sb.append("AND C.\"NM_COMPETITION\" NOT IN ('AMISTOSOS', 'HYBRID FRIENDLIES', 'COPA PAULISTA', 'COPA GOVERNADOR')                           ");
		sb.append("ORDER BY M.\"DT_MATCH\" DESC                                                                                                     ");
		sb.append("LIMIT 6)                                                                                                                         ");
			
		try {
			PreparedStatement psmt = connection.prepareStatement(sb.toString());
			psmt.setString(1, nameTeam);
			
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				statistic.setQtdMatches(rs.getInt("TOTAL_MATCH"));
				statistic.setQtdGoalsPro(rs.getInt("TOTAL_HOME"));
				statistic.setQtdGoalsContra(rs.getInt("TOTAL_VISITOR"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return statistic;
	}
	
	public Statistic getStatisticTeamVisitorYears(String nameTeam, int date) {
		StringBuffer sb = new StringBuffer();
		Statistic statistic = new Statistic();
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, date);
		java.util.Date yearAgo = now.getTime();
		
		sb.append("SELECT COUNT(*) AS TOTAL_MATCH, SUM(\"NR_SCORE_VISITOR\") AS TOTAL_HOME, SUM(\"NR_SCORE_HOME\") AS TOTAL_VISITOR FROM \"MATCH\"  ");
		sb.append("WHERE \"ID_MATCH\" IN (SELECT M.\"ID_MATCH\" FROM \"MATCH\" M                                                                    ");
		sb.append("INNER JOIN \"TEAM\" T ON T.\"ID_TEAM\" = M.\"ID_VISITOR\"                                                                           ");
		sb.append("INNER JOIN \"COMPETITION\" C ON M.\"ID_COMPETITION\" = C.\"ID_COMPETITION\"                                                      ");
		sb.append("WHERE T.\"NM_TEAM\" = ?                                                                                                  	    ");
		sb.append("AND C.\"NM_COMPETITION\" NOT IN ('AMISTOSOS', 'HYBRID FRIENDLIES', 'COPA PAULISTA', 'COPA GOVERNADOR')                       	");
		sb.append("AND M.\"DT_MATCH\" >= ?)                                                                                         		");
		
		try {
			PreparedStatement psmt = connection.prepareStatement(sb.toString());
			psmt.setString(1, nameTeam);
			psmt.setDate(2, new Date(yearAgo.getTime()));
			
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				statistic.setQtdMatches(rs.getInt("TOTAL_MATCH"));
				statistic.setQtdGoalsPro(rs.getInt("TOTAL_HOME"));
				statistic.setQtdGoalsContra(rs.getInt("TOTAL_VISITOR"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return statistic;
	}
	
	public Statistic getStatisticTeamVisitorCompetition(String nameTeam, String nameCompetition, String year) {
		StringBuffer sb = new StringBuffer();
		Statistic statistic = new Statistic();
		
		sb.append("SELECT COUNT(*) AS TOTAL_MATCH, SUM(M.\"NR_SCORE_VISITOR\") AS TOTAL_HOME, SUM(M.\"NR_SCORE_HOME\") AS TOTAL_VISITOR FROM \"MATCH\" M ");
		sb.append("INNER JOIN \"TEAM\" T ON T.\"ID_TEAM\" = M.\"ID_VISITOR\"                                                                                ");
		sb.append("INNER JOIN \"COMPETITION\" C ON M.\"ID_COMPETITION\" = C.\"ID_COMPETITION\"                                                           ");
		sb.append("WHERE T.\"NM_TEAM\" = ?                                                                                                               ");
		sb.append("AND C.\"NM_COMPETITION\" = ? AND C.\"NM_YEAR\" = ?                                                                                    ");
		
		try {
			PreparedStatement psmt = connection.prepareStatement(sb.toString());
			psmt.setString(1, nameTeam);
			psmt.setString(2, nameCompetition);
			psmt.setString(3, year);
			
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				statistic.setQtdMatches(rs.getInt("TOTAL_MATCH"));
				statistic.setQtdGoalsPro(rs.getInt("TOTAL_HOME"));
				statistic.setQtdGoalsContra(rs.getInt("TOTAL_VISITOR"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return statistic;
	}
	
	public Statistic getStatisticTeamVisitorLastSixGames(String nameTeam) {
		StringBuffer sb = new StringBuffer();
		Statistic statistic = new Statistic();
		
		sb.append("SELECT COUNT(*) AS TOTAL_MATCH, SUM(\"NR_SCORE_VISITOR\") AS TOTAL_HOME, SUM(\"NR_SCORE_HOME\") AS TOTAL_VISITOR FROM \"MATCH\"  ");
		sb.append("WHERE \"ID_MATCH\" IN (SELECT M.\"ID_MATCH\" FROM \"MATCH\" M                                                                    ");
		sb.append("INNER JOIN \"TEAM\" T ON T.\"ID_TEAM\" = M.\"ID_VISITOR\"                                                                           ");
		sb.append("INNER JOIN \"COMPETITION\" C ON M.\"ID_COMPETITION\" = C.\"ID_COMPETITION\"                                                      ");
		sb.append("WHERE T.\"NM_TEAM\" = ?                                                                                                          ");
		sb.append("AND C.\"NM_COMPETITION\" NOT IN ('AMISTOSOS', 'HYBRID FRIENDLIES', 'COPA PAULISTA', 'COPA GOVERNADOR')                           ");
		sb.append("ORDER BY M.\"DT_MATCH\" DESC                                                                                                     ");
		sb.append("LIMIT 6)                                                                                                                         ");
			
		try {
			PreparedStatement psmt = connection.prepareStatement(sb.toString());
			psmt.setString(1, nameTeam);
			
			ResultSet rs = psmt.executeQuery();
			
			while (rs.next()) {
				statistic.setQtdMatches(rs.getInt("TOTAL_MATCH"));
				statistic.setQtdGoalsPro(rs.getInt("TOTAL_HOME"));
				statistic.setQtdGoalsContra(rs.getInt("TOTAL_VISITOR"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return statistic;
	}
}
