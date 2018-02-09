package edu.kit.aifb.TechnicianReportSimilarity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.kit.aifb.TechnicianReportSimilarity.web.XDomainNlpClient;

public class TechnicianReportParser {
	
	
	private XDomainNlpClient nlpService;
	
	
	public TechnicianReportParser() {
		this.nlpService = new XDomainNlpClient();
	}
	


	//Proposal and Report have own methods since proposal has specific structure.
	public List<Concept> readInProposalLineByLineAndAnnotate(String filename) {
		//For each file all Concepts are stored in the List.
		List<Concept> allConcepts = new ArrayList<Concept>();
		try {
			File f = new File("./data/" + filename + ".txt");

			// So many different Readers to be able to specify encoding which is necessary
			// for mutated vowels(Umlaute).
			Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("./data/"+filename+"_annotated.txt"), "utf-8"));
			BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));

			String readLine = "";

			System.out.println("Reading " + filename + " using Buffered Reader");

			while ((readLine = b.readLine()) != null) {
				if (!readLine.trim().isEmpty()) {
					// System.out.println(readLine);

					//Now text is split up according to it's structure. 
					//Quotation mark indicates that relevant text follows.
					String[] parts = readLine.split("\"");
					try {
						//We are interested in the part behind the quotation mark
						String message = parts[1];
						//Strings containing only numbers are not relevant
						String regex = "[0-9]+";
						if (message.matches(regex)) {
							continue;
						}
						// System.out.println(message);

						message = replaceGermanSpecialLetters(message);
						//Send text to XDomainNLP Server
						String targetURL = "http://aifb-ls3-vm1.aifb.kit.edu:9085/nlp/stackoverflow/ner";
						String body = "{ " + "\"body\":\"" + message + "\"" + "," + "\"tags\":[]" + " }";
						//Get the annotated text from the XDomainNLP Server
						String location = nlpService.executePostNer(targetURL, body);
						System.out.println(location);
						String annotated = nlpService.executeGetNer(location);
						System.out.println(annotated);


						//writer.write(annotated + "\r");

						allConcepts.addAll(parseRelevantPartsString(annotated));


					}
					//In case a line does not contain a quotation mark the parts array consists of one element
					catch (ArrayIndexOutOfBoundsException e) {
						continue;
					}
				}
			}
			
			b.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<Concept> iter = allConcepts.iterator();
		System.out.println("Iterating through proposal Concepts.");
		while (iter.hasNext())
		{
			Concept c = (Concept) iter.next();
			System.out.println(c.toString());
		}
		return allConcepts;
	}

	
	
	
	public List<Concept> readInReportLineByLineAndAnnotate(String filename) {
		//For each file all Concepts are stored in the List.
		List<Concept> allConcepts = new ArrayList<Concept>();
		try {
			File f = new File("./data/" + filename + ".txt");

			// So many different Readers to be able to specify encoding which is necessary
			// for mutated vowels(Umlaute).
			Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("./data/"+filename+"_annotated.txt"), "utf-8"));
			BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));

			String readLine = "";

			System.out.println("Reading " + filename + " using Buffered Reader");

			while ((readLine = b.readLine()) != null) {
				if (!readLine.trim().isEmpty()) {
					String message = readLine;

					message = replaceGermanSpecialLetters(message);
					//Send text to XDomainNLP Server
					String targetURL = "http://aifb-ls3-vm1.aifb.kit.edu:9085/nlp/stackoverflow/ner";
					String body = "{ " + "\"body\":\"" + message + "\"" + "," + "\"tags\":[]" + " }";
					//Get the annotated text from the XDomainNLP Server
					String location = nlpService.executePostNer(targetURL, body);
					System.out.println(location);
					String annotated = nlpService.executeGetNer(location);
					System.out.println(annotated);


					//writer.write(annotated + "\r");

					allConcepts.addAll(parseRelevantPartsString(annotated));

				}
			}			

			b.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<Concept> iter = allConcepts.iterator();
		System.out.println("Iterating through Report Concepts.");
		while (iter.hasNext())
		{
			Concept c = (Concept) iter.next();
			System.out.println(c.toString());
		}
		return allConcepts;
	}

	
	
	
	
	public List<Concept> parseRelevantPartsString(String annotated)
	{
		List<Concept> concepts = new ArrayList<Concept>();

		JsonElement jelement = new JsonParser().parse(annotated);
		JsonObject jsonObject = jelement.getAsJsonObject();
		JsonArray graphArray = jsonObject.get("@graph").getAsJsonArray();
		System.out.println(graphArray);
		Iterator<JsonElement> iter = graphArray.iterator();
		while (iter.hasNext())
		{
			//System.out.println(iter.next());
			JsonObject jo = iter.next().getAsJsonObject();


			try
			{
				JsonObject taIdentRef = jo.get("itsrdf:taIdentRef").getAsJsonObject();
				//System.out.println(taIdentRef);
				String anchorOf = jo.get("nif:anchorOf").getAsString();
				System.out.println(anchorOf);
				String url = taIdentRef.get("@id").getAsString();
				System.out.println(url);


				Concept c = new Concept(url,anchorOf);
				concepts.add(c);
			}
			catch(NullPointerException e)
			{
				//e.printStackTrace();
			}
		}
		return concepts;
	}

	
	
	public String replaceGermanSpecialLetters(String message) {

		String[][] UMLAUT_REPLACEMENTS = { { "Ä", "Ae" }, { "Ü", "Ue" }, { "Ö", "Oe" }, { "ä", "ae" }, { "ü", "ue" },
				{ "ö", "oe" }, { "ß", "ss" } };
		for (int i = 0; i < UMLAUT_REPLACEMENTS.length; i++) {
			message = message.replace(UMLAUT_REPLACEMENTS[i][0], UMLAUT_REPLACEMENTS[i][1]);
		}
		return message;

	}
	
	
	

	
	
	
	public List<Concept> removeExampleEntities(List<Concept> list)
	{
		Iterator<Concept> iter = list.iterator();
		while (iter.hasNext()) {
			Concept c = iter.next();
			if (c.getUrl().startsWith("http://example.org/")) iter.remove();
		}
		return list;
	}
	
	



}
