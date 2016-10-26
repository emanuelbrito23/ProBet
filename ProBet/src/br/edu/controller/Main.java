package br.edu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import br.edu.model.Match;
import br.edu.util.Constantes;

public class Main {

	public static void main(String[] args) {
		//System.out.println(ExtractHTML.extractHTML("https://www.academiadasapostas.com/stats/team/portugal/vizela/1755#team_id=1755&competition_id=0&page=4"));
		
		try {
			WebClient webClient = new WebClient();
			HtmlPage html = webClient.getPage("https://www.academiadasapostasbrasil.com/stats/team/brasil/chapecoense/6223#team_id=6223&competition_id=0&page=4");
			
			String variable = html.asText();
			
			variable = variable.replace("\r", "\n");
			variable = variable.replace("\t", "\n");
			String[] resultSplit = variable.split("\n");
			
			Match match;
			List<Match> listMatchs = new ArrayList<Match>();
			
			StringBuffer sb = new StringBuffer();
			boolean condition = false;
			
			for (int i = 0; i < resultSplit.length; i++) {
				resultSplit[i] = resultSplit[i].replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO).replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO);
				if (resultSplit[i].equals("Ver")) {
					i++;
					while (!(resultSplit[i].trim()).equals("Anterior Seguinte")) {
						resultSplit[i] = resultSplit[i].replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO).replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO);
						if (!(resultSplit[i].replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO).replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO)).equals("") && !(resultSplit[i].replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO).replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO)).equals("P")) {
							if ((!resultSplit[i+6].trim().equals("vs")) && (resultSplit[i+6].contains("-"))) {
								match = new Match();
								match.setDateMatch(resultSplit[i]);
								match.setNameCompetition(resultSplit[i+4]);
								match.setNameTeamA(resultSplit[i+5]);
								String[] scores = resultSplit[i+6].trim().split("-");
								match.setScoreTeamA(Integer.parseInt(scores[0]));
								match.setScoreTeamB(Integer.parseInt(scores[1]));
								match.setNameTeamB(resultSplit[i+7]);
								
								listMatchs.add(match);
							}
							i = i + 16;
						} else {
							i = i + 1;
						}
					}
					break;
				}
			}
			
			for (Match aux : listMatchs) {
				sb.append(aux.toString());
				sb.append(Constantes.PULA_LINHA);
			}
			
			System.out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
