package br.edu.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import br.edu.model.Match;
import br.edu.util.Constantes;

public class ExtractHTML {

	@SuppressWarnings("resource")
	public static List<Match> extractHTML(String url, int page) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, -2);

		Date oneYearAgo = now.getTime();
		boolean continueSearch = true;

		Match match;
		List<Match> listMatchs = new ArrayList<Match>();

		while ((continueSearch) && ((listMatchs.isEmpty())
				|| (listMatchs.get(listMatchs.size() - 1).getDateMatch().after(oneYearAgo)))) {
			try {
				WebClient webClient = new WebClient();
				HtmlPage html = webClient.getPage(url + page);

				String variable = html.asText();

				variable = variable.replace(Constantes.TAG_R, Constantes.TAG_N);
				variable = variable.replace(Constantes.TAG_T, Constantes.TAG_N);
				String[] resultSplit = variable.split(Constantes.TAG_N);

				for (int i = 0; i < resultSplit.length; i++) {
					resultSplit[i] = resultSplit[i].replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO)
							.replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO);
					if (resultSplit[i].equals(Constantes.VER)) {
						i++;
						while (!(resultSplit[i].trim()).equals(Constantes.ANTERIOR_PROXIMO)) {
							resultSplit[i] = resultSplit[i].replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO)
									.replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO);
							if (!(resultSplit[i].replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO)
									.replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO)).equals(Constantes.VAZIO)
									&& !(resultSplit[i].replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO)
											.replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO))
													.equals(Constantes.P)) {
								int auxScore = 6;
								while (((i + auxScore) < resultSplit.length)
										&& ((resultSplit[i + auxScore].trim().equals(""))
												|| (!resultSplit[i + 6].contains(Constantes.HIFEN))
												|| (!resultSplit[i + auxScore].trim().equals(Constantes.VS)))) {
									if (!resultSplit[i + auxScore].contains(Constantes.HIFEN)) {
										if ((!resultSplit[i + auxScore].trim().equals(Constantes.VS))
												&& (!resultSplit[i + auxScore].contains(Constantes.CANCELLED))
												&& (!resultSplit[i + auxScore].contains(Constantes.ADIADO_EN))
												&& (!resultSplit[i + auxScore].contains(Constantes.ADIADO_PT))) {
											auxScore++;
										} else {
											break;
										}
									} else {
										if (resultSplit[i + auxScore].trim().matches(Constantes.REGEX_PLACAR)) {
											break;
										}
										auxScore++;
									}
								}
								if (((i + auxScore) < resultSplit.length)
										&& (!resultSplit[i + auxScore].trim().equals(Constantes.VS))
										&& (resultSplit[i + auxScore].contains(Constantes.HIFEN))
										&& (!resultSplit[i + auxScore].contains(Constantes.CANCELLED))) {
									if (resultSplit[i + auxScore].trim().matches(Constantes.REGEX_PLACAR)) {
										match = new Match();
										DateFormat dateFormat = new SimpleDateFormat(Constantes.FORMAT_DATE);
										Date date = dateFormat.parse(resultSplit[i]);
										if (date.after(oneYearAgo)) {
											match.setDateMatch(date);
											match.setNameCompetition(resultSplit[i + auxScore - 2]);
											match.setYearCompetition(resultSplit[i + auxScore - 2]);
											match.setNameTeamA(resultSplit[i + auxScore - 1]);
											String[] scores = resultSplit[i + auxScore].trim().split(Constantes.HIFEN);
											match.setScoreTeamA(Integer.parseInt(scores[0]));
											match.setScoreTeamB(Integer.parseInt(scores[1]));

											int auxIndiceTeamB = auxScore + 1;
											while ((resultSplit[i + auxIndiceTeamB].trim().equals(Constantes.VAZIO))
													|| (resultSplit[i + auxIndiceTeamB].trim()
															.contains(Constantes.ABRE_PARENTESES))
													|| (resultSplit[i + auxIndiceTeamB].trim()
															.contains(Constantes.FECHA_PARENTESES))) {
												auxIndiceTeamB++;
											}
											match.setNameTeamB(resultSplit[i + auxIndiceTeamB]);

											listMatchs.add(match);
										} else {
											continueSearch = false;
											break;
										}
									}
								}
								if (((i + auxScore) < resultSplit.length)) {
									i = i + 16;
								} else {
									break;
								}
							} else {
								i = i + 1;
							}
						}
						break;
					}
				}

				if ((listMatchs.size() > 0)
						&& (listMatchs.get(listMatchs.size() - 1).getDateMatch().after(oneYearAgo))) {
					page++;
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return listMatchs;
	}
}
