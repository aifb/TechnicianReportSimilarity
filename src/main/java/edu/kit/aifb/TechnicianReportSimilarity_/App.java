package edu.kit.aifb.TechnicianReportSimilarity_;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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

public class App {

	public static void main(String[] args) {
		//Recommendation of https://stackoverflow.com/questions/11648706/spring-resttemplate-client-connection-refused-exception
//	    System.setProperty("http.proxyHost", "yourproxy.server.com");
//	    System.setProperty("http.proxyPort", "8080");
//	    System.setProperty("https.proxyHost", "yourproxy.server.com");
//	    System.setProperty("https.proxyPort", "8080");
		
		App app = new App();
		String report = readInFile("2_report");
//		String proposal = readInFile("1_proposal");
//		
//		System.out.println(report);
//		System.out.println(proposal);

//POST against Marmotta LDP Server		
//		String targetURL = "http://aifb-ls3-vm1.aifb.kit.edu:8080/marmotta/ldp/";
//		String body = "@prefix aifb: <http://www.aifb.kit.edu/id/> .\r\n" + 
//				"@prefix foaf: <http://xmlns.com/foaf/0.1/> .\r\n" + 
//				"aifb:Rudi_Studer foaf:name \"RudiStuder2\" .";
//		String response = executePostMarmotta(targetURL, body);
//		System.out.println(response);
		
//POST against xDomainNLP Server with sample POST from Marcel. This works.	
//		String targetURL2 = "http://aifb-ls3-vm1.aifb.kit.edu:9080/nlp/stackoverflow/ner";
//		String body2 = "{\r\n" + 
//				"\r\n" + 
//				"\"id\":\"123456\",\r\n" + 
//				"\r\n" + 
//				"\"title\":\"test1\",\r\n" + 
//				"\r\n" + 
//				"\"body\":\"I am new to Docker and I have a question regarding possibility to extend docer image after pulling goods from a Printing Press 123456.\",\r\n" + 
//				"\r\n" + 
//				"\"tags\":[\"java\",\"docker\"]\r\n" + 
//				"\r\n" + 
//				"}";
//		String location = executePostNER(targetURL2,body2);
//		System.out.println(location);
//		//Get the annotated response.
//		String annotated = executeGETNER(location);
//		System.out.println(annotated);
		
//		try {
//			app.makeRDFCall(location);
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
		String targetURL2 = "http://aifb-ls3-vm1.aifb.kit.edu:9080/nlp/stackoverflow/ner";
		String body2 = "{ " + 
				"\"id\":\"123456\"," + 
				"" + 
				"\"title\":\"test1\"," + 
				"" + 
				"\"body\":\"" + report + "\"," + 
				"\"tags\":[\"java\",\"docker\"]" + 
				"" + 
				"}";
		String location = executePostNER(targetURL2,body2);
		System.out.println(location);
		
		String annotated = executeGETNER(location);
		System.out.println(annotated);
//		try {
//			app.makeRDFCall(location);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
		
//		String targetURL2 = "http://aifb-ls3-vm1.aifb.kit.edu:9080/nlp/stackoverflow/ner";
//		String body2 = "{ \"id\":\"123456\",\"title\":\"test1\",\"body\":\"1. What have been the start conditions of the job on-site? start conditions 2. What has been done to solve the problem? Zum Thema IRIS: hierzu bitte gesonderten Bericht des Kollegen M. Feuerstein lesen. Zum Thema Anlage wurde -die Variantenstelle geprüft -der Anleger wurde erneut mit dem Saugkopf abgesteckt -die Schuppenlänge wurde überprüft -an der Bandantriebswalze wurde der Durchmesser geprüft. -der Einbau der richtigen Steuerkurve im Saugkopf wurde geprüft -das Bremskupplungsmodul (BKM) wurde auf Funktion geprüft Alle Teile waren i.O. bzw. in der Toleranz. -außerdem wurde trotz Folienpaketes das Saugband von Grau auf Grün getauscht. Mit diesem Tausch funktionierte das Stapelmanagement und auch der Bogenlauf/Bogenankunftsregelung verlief erfolgreich! 3. Which parts of the machine have been affected? machine parts 4. Which machine parts have been exchanged? Saugband 5. Which components have been adjusted? components adjusted 6. Additional information about software versions etc. additional info 7. Has the final result been tested? How? Der Papierlauf incl. Stapelmanagement erfolgte zur Zufriedenheit des Kunden. Während der Produktionsbegleitung gab es keinerlei Beanstandungen. 8. Summary of the result Thema Stapelmanagement im Anleger ist behoben. Thema IRIS siehe Bericht vom Kollegen. 9. Safety relevant issues safety issues\",\"tags\":[\"java\",\"docker\"]}";
//		String location = executePostNER(targetURL2,body2);
//		System.out.println(location);
//		
//		String annotated = executeGETNER(location);
//		System.out.println(annotated);
		
//		String targetURL3 = "http://aifb-ls3-vm1.aifb.kit.edu:8090/";
//		String body3 = "'text = Hello World!'";
//		String response = executePostStanford(targetURL3,body3);
//		System.out.println(response);
		
		
		
	}
	
	public void makeRDFCall(String targetURL) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// Status: hier alle informationen über Java zugreifbar

				// JAX RS Client
				//		Client client = ClientBuilder.newClient();
				Client client = ClientBuilder.newBuilder().register(JsonLdMessageBodyReaderWriter.class).build();

				// client macht seinen Call
				Node[] nodes;
				Iterable<Node[]> list = new LinkedList<Node[]>() {};
				//Here the outgoing RDF graph is built.
				//Blank Node - RDF:type - Whatver + AppDev
				((LinkedList<Node[]>) list).add(new Node[] { new BNode(""), RDF.TYPE, new Resource(STEP.NS + "AppDev" ) });

				//Here we construct the request builder.
				JerseyInvocation.Builder b = (org.glassfish.jersey.client.JerseyInvocation.Builder) client
						.target(targetURL)
						.request( JsonLdMessageBodyReaderWriter.JSONLD_MEDIATYPE );
				
				//The required method is not accessible (private/package)
				//We undermine Java Security by setting setAccessible to TRUE
				Method m = b.getClass().getDeclaredMethod("request");
				m.setAccessible(true);// Abracadabra 
				ClientRequest cr = (ClientRequest) m.invoke(b);// now its OK
				
				
				//To be able to build bla afterwards
				cr.setEntityType(list.getClass().getGenericSuperclass());
				//Request is built.
				//JerseyInvocation bla = (JerseyInvocation) b.buildPost(Entity.entity(new GenericEntity<Iterable<Node[]>>( list ) { },JsonLdMessageBodyReaderWriter.JSONLD_MEDIATYPE ));
				JerseyInvocation bla = (JerseyInvocation) b.buildGet();
				cr.accept( JsonLdMessageBodyReaderWriter.JSONLD_MEDIATYPE );
				
				//cr.setEntityType(list.getClass().getGenericSuperclass());
				//Request is sent
				Response response = bla.invoke();
				
				//Response object is built, Then we read the Response
				Iterable<Node[]> data = response.readEntity(  new GenericType<Iterable<Node[]>>(  ) { } ) ;		
				for (Iterator<Node[]> iter = data.iterator(); iter.hasNext() ; ) {
					Node[] node = iter.next();
					System.out.println("Node: " + node[0] + node[1] + node[2]);
				}

				// responses werden gelesen

				// als rdf über return zurückgegeben
	}
	
	public static String readInFile(String filename)
	{
		String text = "";
		
		try { 
			File f = new File("./data/"+filename + ".txt");
			  
			//So many different Readers to be able to specify encoding which is necessary for mutated vowels(Umlaute).
			BufferedReader b = new BufferedReader(new InputStreamReader( new FileInputStream(f) , "UTF-8"));

	           String readLine = "";
	           

	           System.out.println("Reading " +  filename + " using Buffered Reader");

	           while ((readLine = b.readLine()) != null) {
	               text =text + readLine;
	           }

	       } catch (IOException e) {
	           e.printStackTrace();

		}
		return text;
	}
	
	//TBD: Wie iterierte ich über alle Reports und Proposals zum Einlesen? 
	//TBD: Wie POST absetzen?
	//TBD: Wie bekomme ich Response, wie sieht die aus und was mache ich dann mit der?
	//TBD: Text must be posted to Stanford CoreNLP
	public static String executePostMarmotta(String targetURL, String body) {
		  HttpURLConnection connection = null;

		  try {
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Content-Type", 
		        "text/turtle");
		    connection.setRequestProperty("SLUG", "RudiStuder2");

//		    connection.setRequestProperty("Content-Length", 
//		        Integer.toString(urlParameters.getBytes().length));
//		    connection.setRequestProperty("Content-Language", "en-US");  
//
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);

		    //Send request
		    DataOutputStream wr = new DataOutputStream (
		        connection.getOutputStream());
		    wr.writeBytes(body);
		    wr.close();

		    //Get Response  
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
	
	public static String executePostNER(String targetURL, String body) {
		  HttpURLConnection connection = null;
		  System.out.println(body);
		  try
		  {
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Accept", 
		        "application/json");
		    connection.setRequestProperty("Content-Type", "application/json");
		    //Recommendation from https://stackoverflow.com/questions/11648706/spring-resttemplate-client-connection-refused-exception
		    connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		    
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);

		    //Send request
		    DataOutputStream wr = new DataOutputStream (
		        connection.getOutputStream());
		    wr.writeBytes(body);
		    wr.close();

		    //Get Response 
		    int status = connection.getResponseCode();
		    String message = connection.getResponseMessage();
		    System.out.println(message);
		    if (status == HttpURLConnection.HTTP_CREATED) {
		              String location = connection.getHeaderField("Location");
		              return location;

		        }
		    else
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
		    //Create connection
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
	
	public static String executePostStanford(String targetURL, String body)
	{
		 HttpURLConnection connection = null;
		  try
		  {
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Accept", 
		        "XML");
		    connection.setRequestProperty("Content-Type", "text");

		    connection.setUseCaches(false);
		    connection.setDoOutput(true);

		    //Send request
		    DataOutputStream wr = new DataOutputStream (
		        connection.getOutputStream());
		    wr.writeBytes(body);
		    wr.close();

		    //Get Response  
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

