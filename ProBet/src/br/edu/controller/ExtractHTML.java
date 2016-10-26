package br.edu.controller;

import java.io.BufferedReader;
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
import br.edu.util.Constantes;

@SuppressWarnings("deprecation")
public class ExtractHTML {

	@SuppressWarnings("resource")
	public static String extractHTML(String url) {
		StringBuffer sb = new StringBuffer();
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			InputStream is;
			is = entity.getContent();
			
			Match match;
			List<Match> listMatchs = new ArrayList<Match>();

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, Constantes.UTF_8), 8);
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				if (line.contains(Constantes.NEXT_GAMES)) {
					while (((line = reader.readLine()) != null) && (!line.trim().equals(Constantes.FECHA_DIV))) {
						if ((line.contains(Constantes.EVEN)) || (line.contains(Constantes.ODD))) {
							match = new Match();
							while (((line = reader.readLine()) != null) && (!line.trim().equals(Constantes.FECHA_TR))) {
								if ((line.contains(Constantes.TD)) && (line.contains(Constantes.FECHA_TD)) && (match.getDateMatch() == null)) {
									line = line.replace(Constantes.TD, Constantes.VAZIO).replace(Constantes.FECHA_TD, Constantes.VAZIO);
									match.setDateMatch(line);
								} else if ((line.contains(Constantes.FECHA_UL)) && (line.contains(Constantes.FECHA_A))) {
									line = line.replace(Constantes.FECHA_UL, Constantes.VAZIO).replace(Constantes.FECHA_A, Constantes.VAZIO);
									match.setNameCompetition(line.replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO).replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO));
								} else if ((line.contains(Constantes.HREF)) && (line.contains(Constantes.TARGET_SELF)) && (match.getNameTeamA() == null)) {
									line = reader.readLine();
									if ((line.contains(Constantes.FECHA_A)) && (!line.contains(Constantes.UL_CLASS))) {
										match.setNameTeamA(line.replace(Constantes.FECHA_A, Constantes.VAZIO).replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO).replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO));
									}
								} else if (line.contains(Constantes.FECHA_A) && (line.contains(Constantes.HIFEN)) && (!line.contains(Constantes.TARGET_SELF)) && (!line.contains(Constantes.HREF)) && (!line.contains(Constantes.MATCH_PREVIEW))) {
									line = line.replace(Constantes.FECHA_A, Constantes.VAZIO);
									String[] resultSplit = line.trim().split(Constantes.HIFEN);
									match.setScoreTeamA(Integer.parseInt((resultSplit[0]).trim()));
									match.setScoreTeamB(Integer.parseInt((resultSplit[1]).trim()));
								} else if ((line.contains(Constantes.HREF)) && (line.contains(Constantes.TARGET_SELF))) {
									line = reader.readLine();
									if ((line.contains(Constantes.FECHA_A)) && (!line.contains(Constantes.UL_CLASS))) {
										match.setNameTeamB(line.replace(Constantes.FECHA_A, Constantes.VAZIO).replaceAll(Constantes.REGEX_ESPACO_INICIO, Constantes.VAZIO).replaceAll(Constantes.REGEX_ESPACO_FIM, Constantes.VAZIO));
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

			for (Match aux : listMatchs) {
				sb.append(aux.toString());
				sb.append(Constantes.PULA_LINHA);
			}

			is.close(); 
			reader.close();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
}
