package edu.kit.aifb.TechnicianReportSimilarity_;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.LinkedList;

//import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

//import io.swagger.jaxrs.config.BeanConfig;
//import sample.hello.resources.HelloResource;

//import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.JerseyInvocation;
//import org.apache.jena.datatypes.RDFDatatype;
//import org.apache.jena.rdf.model.Model;
//import org.apache.jena.rdf.model.ModelFactory;
//import org.apache.jena.rdf.model.Statement;
//import org.apache.jena.rdf.model.StmtIterator;
//import org.json.JSONObject;
import org.semanticweb.yars.jaxrs.JsonLdMessageBodyReaderWriter;
import org.semanticweb.yars.nx.BNode;
import org.semanticweb.yars.nx.Literal;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;
import org.semanticweb.yars.nx.namespace.RDF;
import org.semanticweb.yars.nx.namespace.RDFS;
import org.semanticweb.yars.nx.namespace.XSD;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class App {

	public static void main(String[] args) {

		App app = new App();
		List<Concept> ProposalConcepts = readInProposalLineByLineAndAnnotate("7_proposal");
		List<Concept> ReportConcepts = readInReportLineByLineAndAnnotate("7_FakeReport");
		
//		SimilarityCalculationDemo SimilarityCalculator = new SimilarityCalculationDemo();
//		SimilarityCalculator.run("dog","pet");
		
		// String proposal = readInFile("7_proposal");

	}
	//Not used right now, copied from another project of sebbader
	public void makeRDFCall(String targetURL) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// Status: hier alle informationen über Java zugreifbar

		// JAX RS Client
		// Client client = ClientBuilder.newClient();
		Client client = ClientBuilder.newBuilder().register(JsonLdMessageBodyReaderWriter.class).build();

		// client macht seinen Call
		Node[] nodes;
		Iterable<Node[]> list = new LinkedList<Node[]>() {
		};
		// Here the outgoing RDF graph is built.
		// Blank Node - RDF:type - Whatver + AppDev
		((LinkedList<Node[]>) list).add(new Node[] { new BNode(""), RDF.TYPE, new Resource(STEP.NS + "AppDev") });

		// Here we construct the request builder.
		JerseyInvocation.Builder b = (org.glassfish.jersey.client.JerseyInvocation.Builder) client.target(targetURL)
				.request(JsonLdMessageBodyReaderWriter.JSONLD_MEDIATYPE);

		// The required method is not accessible (private/package)
		// We undermine Java Security by setting setAccessible to TRUE
		Method m = b.getClass().getDeclaredMethod("request");
		m.setAccessible(true);// Abracadabra
		ClientRequest cr = (ClientRequest) m.invoke(b);// now its OK

		// To be able to build bla afterwards
		cr.setEntityType(list.getClass().getGenericSuperclass());
		// Request is built.
		// JerseyInvocation bla = (JerseyInvocation) b.buildPost(Entity.entity(new
		// GenericEntity<Iterable<Node[]>>( list ) {
		// },JsonLdMessageBodyReaderWriter.JSONLD_MEDIATYPE ));
		JerseyInvocation bla = (JerseyInvocation) b.buildGet();
		cr.accept(JsonLdMessageBodyReaderWriter.JSONLD_MEDIATYPE);

		// cr.setEntityType(list.getClass().getGenericSuperclass());
		// Request is sent
		Response response = bla.invoke();

		// Response object is built, Then we read the Response
		Iterable<Node[]> data = response.readEntity(new GenericType<Iterable<Node[]>>() {
		});
		for (Iterator<Node[]> iter = data.iterator(); iter.hasNext();) {
			Node[] node = iter.next();
			System.out.println("Node: " + node[0] + node[1] + node[2]);
		}

		// responses werden gelesen

		// als rdf über return zurückgegeben
	}


	//Proposal and Report have own methods since proposal has specific structure.
	public static List<Concept> readInProposalLineByLineAndAnnotate(String filename) {
		//For each file all Concepts are stored in the List.
		List<Concept> allConcepts = new ArrayList();
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

						message = App.replaceGermanSpecialLetters(message);
						//Send text to XDomainNLP Server
						String targetURL = "http://aifb-ls3-vm1.aifb.kit.edu:9085/nlp/stackoverflow/ner";
						String body = "{ " + "\"body\":\"" + message + "\"" + "," + "\"tags\":[]" + " }";
						//Get the annotated text from the XDomainNLP Server
						String location = executePostNER(targetURL, body);
						System.out.println(location);
						String annotated = executeGETNER(location);
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
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator iter = allConcepts.iterator();
		System.out.println("Iterating through proposal Concepts.");
		while (iter.hasNext())
		{
			Concept c = (Concept) iter.next();
			System.out.println(c.toString());
		}
		return allConcepts;
	}

	public static List<Concept> readInReportLineByLineAndAnnotate(String filename) {
		//For each file all Concepts are stored in the List.
		List<Concept> allConcepts = new ArrayList();
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

						message = App.replaceGermanSpecialLetters(message);
						//Send text to XDomainNLP Server
						String targetURL = "http://aifb-ls3-vm1.aifb.kit.edu:9085/nlp/stackoverflow/ner";
						String body = "{ " + "\"body\":\"" + message + "\"" + "," + "\"tags\":[]" + " }";
						//Get the annotated text from the XDomainNLP Server
						String location = executePostNER(targetURL, body);
						System.out.println(location);
						String annotated = executeGETNER(location);
						System.out.println(annotated);
						

						//writer.write(annotated + "\r");
						
						allConcepts.addAll(parseRelevantPartsString(annotated));

				}
			}			
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator iter = allConcepts.iterator();
		System.out.println("Iterating through Report Concepts.");
		while (iter.hasNext())
		{
			Concept c = (Concept) iter.next();
			System.out.println(c.toString());
		}
		return allConcepts;
	}

	public static List parseRelevantPartsString(String annotated)
	{
		List<Concept> concepts = new ArrayList();
		
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

	public static String replaceGermanSpecialLetters(String message) {

		String[][] UMLAUT_REPLACEMENTS = { { "Ä", "Ae" }, { "Ü", "Ue" }, { "Ö", "Oe" }, { "ä", "ae" }, { "ü", "ue" },
				{ "ö", "oe" }, { "ß", "ss" } };
		for (int i = 0; i < UMLAUT_REPLACEMENTS.length; i++) {
			message = message.replace(UMLAUT_REPLACEMENTS[i][0], UMLAUT_REPLACEMENTS[i][1]);
		}
		return message;

	}


	

	public static String executePostNER(String targetURL, String body) {
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

	public static String executeGETNER(String targetURL) {

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

	public static String executePostStanford(String targetURL, String body) {
		HttpURLConnection connection = null;
		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "XML");
			connection.setRequestProperty("Content-Type", "text");

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(body);
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
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
