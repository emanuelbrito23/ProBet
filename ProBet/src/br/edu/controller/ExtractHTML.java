package br.edu.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import br.edu.model.Match;

public class ExtractHTML {

	public static void main(String[] args) {
		try {
			HttpClient httpclient = new DefaultHttpClient(); // Create HTTP
																// Client
			HttpGet httpget = new HttpGet("https://www.academiadasapostas.com/stats/team/alemanha/bayern-munique/961"); // Set
																												// the
			// action
			// you want
			// to do
			HttpResponse response = httpclient.execute(httpget); // Executeit
			HttpEntity entity = response.getEntity();
			InputStream is;
			is = entity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
			String line = null;
			Match match;
			List<Match> listMatchs = new ArrayList<Match>();
			while ((line = reader.readLine()) != null) { // Read line by line
				if (line.contains("<table class=\"next-games\"")) {
					while (((line = reader.readLine()) != null) && (!line.trim().equals("</div>"))) {
						if ((line.contains("<tr class=\"even\">")) || (line.contains("<tr class=\"odd\">"))) {
							match = new Match();
							while (((line = reader.readLine()) != null) && (!line.trim().equals("</tr>"))) {
								if ((line.contains("<td>")) && (line.contains("</td>")) && (match.getDateMatch() == null)) {
									line = line.replace("<td>", "").replace("</td>", "");
									match.setDateMatch(line);
								} else if ((line.contains("</ul>")) && (line.contains("</a>"))) {
									line = line.replace("</ul>", "").replace("</a>", "");
									match.setNameCompetition(line.replaceAll("\\s+$", "").replaceAll("^\\s+", ""));
								} else if ((line.contains("a href=\"")) && (line.contains("target=\"_self\">")) && (match.getNameTeamA() == null)) {
									line = reader.readLine();
									if ((line.contains("</a>")) && (!line.contains("<ul class="))) {
										match.setNameTeamA(line.replace("</a>", "").replaceAll("\\s+$", "").replaceAll("^\\s+", ""));
									}
								} else if (line.contains("</a>") && (line.contains("-")) && (!line.contains("target=\"_self")) && (!line.contains("<a href=\"")) && (!line.contains("<a class=\"match_preview\" href="))) {
									line = line.replace("</a>", "");
									String[] resultSplit = line.trim().split("-");
									match.setScoreTeamA(Integer.parseInt((resultSplit[0]).trim()));
									match.setScoreTeamB(Integer.parseInt((resultSplit[1]).trim()));
								} else if ((line.contains("a href=\"")) && (line.contains("target=\"_self\">"))) {
									line = reader.readLine();
									if ((line.contains("</a>")) && (!line.contains("<ul class="))) {
										match.setNameTeamB(line.replace("</a>", "").replaceAll("\\s+$", "").replaceAll("^\\s+", ""));
									}
								}
							}
							if ((match.getNameCompetition() != null) && (match.getScoreTeamA() != null)
									&& (match.getScoreTeamA() != null)) {
								listMatchs.add(match);
							}
						}
					}
				}
			}

			BufferedWriter buffWrite = new BufferedWriter(new FileWriter("C:/Users/Emanuel Guimarães/Desktop/AngularJS/TESTE_EMANUEL/SAIDA.txt"));
			for (Match aux : listMatchs) {
				buffWrite.append(aux.toString());
				buffWrite.newLine();
			}

			is.close(); // Close the stream
			reader.close();
			buffWrite.close();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getDateLine(String line) {
		String textCompare = "data-format='dd/mm/yy'>";

		int beginText = (line.length() - 1) - textCompare.length();
		for (int endText = (line.length() - 1); endText >= (textCompare.length() - 1); endText--) {
			if (line.substring(beginText, endText).equals(textCompare)) {
				return line.substring(endText, endText + 8);
			}
			beginText--;
		}

		return null;
	}

	public static String getCompetition(String line) {
		String textCompare = "title=\"";

		int beginText = (line.length() - 1) - textCompare.length();
		for (int endText = (line.length() - 1); endText >= (textCompare.length() - 1); endText--) {
			if (line.substring(beginText, endText).equals(textCompare)) {
				String[] resultSplit = line.substring(endText, (line.length() - 1)).split("\"");
				return resultSplit[0];
			}
			beginText--;
		}

		return null;
	}

	public static String getTeam(String line) {
		String textCompare = "title=\"";

		int beginText = (line.length() - 1) - textCompare.length();
		for (int endText = (line.length() - 1); endText >= (textCompare.length() - 1); endText--) {
			if (line.substring(beginText, endText).equals(textCompare)) {
				String[] resultSplit = line.substring(endText, (line.length() - 1)).split("\"");
				return resultSplit[0];
			}
			beginText--;
		}

		return null;
	}
}
