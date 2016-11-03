package br.edu.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import br.edu.model.Statistic;
import br.edu.repository.ConnectionJDBC;

public class Main {

	public static void main(String[] args) {
		ConnectionJDBC connectionJDBC = new ConnectionJDBC();

		/*List<String> teams = new ArrayList<String>();
		
		List<Match> list = new ArrayList<Match>();
		
		for (int i = 0; i < teams.size(); i++) {
			list = ExtractHTML.extractHTML(teams.get(i), 1);
			
			for (Match auxMatch : list) {
				connectionJDBC.insertMatch(auxMatch);
			}
		}*/
		
		Statistic competition = connectionJDBC.getStatisticCompetition("LIGA DOS CAMPEÕES DA UEFA", "16/17");
		System.out.println(competition.toString());
		
		Statistic teamHomeYears = connectionJDBC.getStatisticTeamHomeYears("BORUSSIA DORTMUND", -2);
		System.out.println(teamHomeYears.toString());
		Statistic teamHomeCompetition = connectionJDBC.getStatisticTeamHomeCompetition("BORUSSIA DORTMUND", "LIGA DOS CAMPEÕES DA UEFA", "16/17");
		System.out.println(teamHomeCompetition.toString());
		Statistic teamHomeLastSixGames = connectionJDBC.getStatisticTeamHomeLastSixGames("BORUSSIA DORTMUND");
		System.out.println(teamHomeLastSixGames.toString());
		
		Statistic teamVisitorYears = connectionJDBC.getStatisticTeamVisitorYears("SPORTING", -2);
		System.out.println(teamVisitorYears.toString());
		Statistic teamVisitorCompetition = connectionJDBC.getStatisticTeamVisitorCompetition("SPORTING", "LIGA DOS CAMPEÕES DA UEFA", "16/17");
		System.out.println(teamVisitorCompetition.toString());
		Statistic teamVisitorLastSixGames = connectionJDBC.getStatisticTeamVisitorLastSixGames("SPORTING");
		System.out.println(teamVisitorLastSixGames.toString());
		
		BigDecimal mediaHomeGoalsProYears = (new BigDecimal(teamHomeYears.getQtdGoalsPro()).divide(new BigDecimal(teamHomeYears.getQtdMatches()), 3, RoundingMode.HALF_EVEN));
		BigDecimal mediaHomeGoalsContraYears = (new BigDecimal(teamHomeYears.getQtdGoalsContra()).divide(new BigDecimal(teamHomeYears.getQtdMatches()), 3, RoundingMode.HALF_EVEN));
		
		BigDecimal mediaHomeGoalsProCompetition = (new BigDecimal(teamHomeCompetition.getQtdGoalsPro()).divide(new BigDecimal(teamHomeCompetition.getQtdMatches()), 3, RoundingMode.HALF_EVEN));
		BigDecimal mediaHomeGoalsContraCompetition = (new BigDecimal(teamHomeCompetition.getQtdGoalsContra()).divide(new BigDecimal(teamHomeCompetition.getQtdMatches()), 3, RoundingMode.HALF_EVEN));
		
		BigDecimal mediaHomeGoalsProLastSixGames = (new BigDecimal(teamHomeLastSixGames.getQtdGoalsPro()).divide(new BigDecimal(teamHomeLastSixGames.getQtdMatches()), 3, RoundingMode.HALF_EVEN));
		BigDecimal mediaHomeGoalsContraLastSixGames = (new BigDecimal(teamHomeLastSixGames.getQtdGoalsContra()).divide(new BigDecimal(teamHomeLastSixGames.getQtdMatches()), 3, RoundingMode.HALF_EVEN));
		
		BigDecimal mediaVisitorGoalsProYears = (new BigDecimal(teamVisitorYears.getQtdGoalsPro()).divide(new BigDecimal(teamVisitorYears.getQtdMatches()), 3, RoundingMode.HALF_EVEN));
		BigDecimal mediaVisitorGoalsContraYears = (new BigDecimal(teamVisitorYears.getQtdGoalsContra()).divide(new BigDecimal(teamVisitorYears.getQtdMatches()), 3, RoundingMode.HALF_EVEN));
		
		BigDecimal mediaVisitorGoalsProCompetition = (new BigDecimal(teamVisitorCompetition.getQtdGoalsPro()).divide(new BigDecimal(teamVisitorCompetition.getQtdMatches()), 3, RoundingMode.HALF_EVEN));
		BigDecimal mediaVisitorGoalsContraCompetition = (new BigDecimal(teamVisitorCompetition.getQtdGoalsContra()).divide(new BigDecimal(teamVisitorCompetition.getQtdMatches()), 3, RoundingMode.HALF_EVEN));
		
		BigDecimal mediaVisitorGoalsProLastSixGames = (new BigDecimal(teamVisitorLastSixGames.getQtdGoalsPro()).divide(new BigDecimal(teamVisitorLastSixGames.getQtdMatches()), 3, RoundingMode.HALF_EVEN));
		BigDecimal mediaVisitorGoalsContraLastSixGames = (new BigDecimal(teamVisitorLastSixGames.getQtdGoalsContra()).divide(new BigDecimal(teamVisitorLastSixGames.getQtdMatches()), 3, RoundingMode.HALF_EVEN));
		
				
		System.out.println(mediaHomeGoalsProYears.toString() + " - " + mediaHomeGoalsContraYears.toString());
		System.out.println(mediaHomeGoalsProCompetition.toString() + " - " + mediaHomeGoalsContraCompetition.toString());
		System.out.println(mediaHomeGoalsProLastSixGames.toString() + " - " + mediaHomeGoalsContraLastSixGames.toString());
		System.out.println(mediaVisitorGoalsProYears.toString() + " - " + mediaVisitorGoalsContraYears.toString());
		System.out.println(mediaVisitorGoalsProCompetition.toString() + " - " + mediaVisitorGoalsContraCompetition.toString());
		System.out.println(mediaVisitorGoalsProLastSixGames.toString() + " - " + mediaVisitorGoalsContraLastSixGames.toString());

		BigDecimal mediaTeamHome = ((((mediaHomeGoalsProYears.multiply(new BigDecimal("0.2")))
				.add(mediaHomeGoalsProCompetition.multiply(new BigDecimal("0.55")))
				.add(mediaHomeGoalsProLastSixGames.multiply(new BigDecimal("0.25")))).divide(
						new BigDecimal(competition.getQtdGoalsPro())
								.divide(new BigDecimal(competition.getQtdMatches()), 3, RoundingMode.HALF_EVEN),
						3, RoundingMode.HALF_EVEN))
								.multiply(((mediaVisitorGoalsContraYears.multiply(new BigDecimal("0.2")))
										.add(mediaVisitorGoalsContraCompetition.multiply(new BigDecimal("0.55")))
										.add(mediaVisitorGoalsContraLastSixGames.multiply(new BigDecimal("0.25"))))
												.divide(new BigDecimal(competition.getQtdGoalsPro()).divide(
														new BigDecimal(competition.getQtdMatches()), 3,
														RoundingMode.HALF_EVEN), 3, RoundingMode.HALF_EVEN))).multiply(
																new BigDecimal(competition.getQtdGoalsPro()).divide(
																		new BigDecimal(competition.getQtdMatches()), 3,
																		RoundingMode.HALF_EVEN))
																.setScale(3, RoundingMode.HALF_EVEN);

		BigDecimal mediaTeamVisitor = ((((mediaVisitorGoalsProYears.multiply(new BigDecimal("0.2")))
				.add(mediaVisitorGoalsProCompetition.multiply(new BigDecimal("0.55")))
				.add(mediaVisitorGoalsProLastSixGames.multiply(new BigDecimal("0.25")))).divide(
						new BigDecimal(competition.getQtdGoalsContra())
								.divide(new BigDecimal(competition.getQtdMatches()), 3, RoundingMode.HALF_EVEN),
						3,
						RoundingMode.HALF_EVEN)).multiply(((mediaHomeGoalsContraYears.multiply(new BigDecimal("0.2")))
								.add(mediaHomeGoalsContraCompetition.multiply(new BigDecimal("0.55")))
								.add(mediaHomeGoalsContraLastSixGames.multiply(new BigDecimal("0.25"))))
										.divide(new BigDecimal(competition.getQtdGoalsContra()).divide(
												new BigDecimal(competition.getQtdMatches()), 3, RoundingMode.HALF_EVEN),
												3, RoundingMode.HALF_EVEN)))
														.multiply(new BigDecimal(competition.getQtdGoalsContra())
																.divide(new BigDecimal(competition.getQtdMatches()), 3,
																		RoundingMode.HALF_EVEN))
														.setScale(3, RoundingMode.HALF_EVEN);

		System.out.println(mediaTeamHome.toString());
		System.out.println(mediaTeamVisitor.toString());
		
		Integer[] goals = {0, 1, 2, 3, 4};
		List<BigDecimal> oddsGoalsHome = new ArrayList<BigDecimal>();
		List<BigDecimal> oddsGoalsVisitor = new ArrayList<BigDecimal>();
		
		for (int i = 0; i < goals.length; i++) {
			oddsGoalsHome.add(new BigDecimal(poison(goals[i], mediaTeamHome)).setScale(3, RoundingMode.HALF_EVEN));
			oddsGoalsVisitor.add(new BigDecimal(poison(goals[i], mediaTeamVisitor)).setScale(3, RoundingMode.HALF_EVEN));
		}
		
		oddsGoalsHome.add(moreFourGoals(oddsGoalsHome));
		oddsGoalsVisitor.add(moreFourGoals(oddsGoalsVisitor));
		
		BigDecimal[][] scores = new BigDecimal[6][6];
		
		for (int i = 0; i < oddsGoalsHome.size(); i++) {
			for (int j = 0; j < oddsGoalsVisitor.size(); j++) {
				scores[i][j] = (oddsGoalsHome.get(i).multiply(oddsGoalsVisitor.get(j))).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_EVEN);
			}
		}
		
		BigDecimal oddsDraw = oddsDraw(scores);
		BigDecimal oddsWinnerHome = oddsWinnerHome(scores);
		BigDecimal oddsWinnerVisitor = oddsWinnerVisitor(scores);
		
		System.out.println("TERMINEI - " + oddsDraw.toString() + " - " + oddsWinnerHome.toString() + " - " + oddsWinnerVisitor.toString());
	}
	
	public static int fatorial(int number) {
		if (number <= 1) {
			return 1;
		} else {
			return fatorial(number - 1) * number;
		}
	}
	
	public static double poison(int k, BigDecimal lambda) {
		return ((Math.pow(Math.E, (-1 * lambda.doubleValue()))) * (Math.pow(lambda.doubleValue(), k))/(fatorial(k)));
	}
	
	public static BigDecimal moreFourGoals(List<BigDecimal> oddsGoals) {
		BigDecimal sum = new BigDecimal("0");
		
		for (BigDecimal bigDecimal : oddsGoals) {
			sum = sum.add(bigDecimal);
		}
		
		return new BigDecimal("1").subtract(sum);
	}
	
	public static BigDecimal oddsDraw(BigDecimal[][] scores) {
		BigDecimal odds = new BigDecimal("0");
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (i == j) {
					odds = odds.add(scores[i][j]);
				}
			}
		}
		
		return odds;
	}
	
	public static BigDecimal oddsWinnerHome(BigDecimal[][] scores) {
		BigDecimal odds = new BigDecimal("0");
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (i > j) {
					odds = odds.add(scores[i][j]);
				}
			}
		}
		
		return odds;
	}
	
	public static BigDecimal oddsWinnerVisitor(BigDecimal[][] scores) {
		BigDecimal odds = new BigDecimal("0");
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (i < j) {
					odds = odds.add(scores[i][j]);
				}
			}
		}
		
		return odds;
	}
}
