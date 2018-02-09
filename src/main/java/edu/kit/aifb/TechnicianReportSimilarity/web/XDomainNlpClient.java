package edu.kit.aifb.TechnicianReportSimilarity.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class XDomainNlpClient {
	


	public String executePostNer(String targetURL, String body) {
		HttpURLConnection connection = null;
		System.out.println("POST Body: " + body);
		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(body);
			wr.close();

			// Get Response
			int status = connection.getResponseCode();
			String message = connection.getResponseMessage();
			System.out.println(message);
			if (status == HttpURLConnection.HTTP_CREATED) {
				String location = connection.getHeaderField("Location");
				//Location is needed to perform the GET afterwards
				return location;

			} else
				return "POST not successful " + status;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	
	
	
	public String executeGetNer(String targetURL) {

		HttpURLConnection connection = null;
		StringBuilder result = new StringBuilder();
		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
