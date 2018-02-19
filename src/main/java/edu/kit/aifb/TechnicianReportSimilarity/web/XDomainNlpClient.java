package edu.kit.aifb.TechnicianReportSimilarity.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.glassfish.jersey.client.ClientRequest;
import org.glassfish.jersey.client.JerseyInvocation;
import org.glassfish.jersey.internal.util.Base64;

import org.semanticweb.yars.jaxrs.JsonLdMessageBodyReaderWriter;
import org.semanticweb.yars.nx.BNode;
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;
import org.semanticweb.yars.nx.namespace.RDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import edu.kit.aifb.TechnicianReportSimilarity.vocabs.STEP;

public class XDomainNlpClient {

	private final Logger logger = LoggerFactory.getLogger(XDomainNlpClient.class);


	public String executePostNer(String targetURL, String body) {
		HttpURLConnection connection = null;
		logger.info("POST Body: " + body);
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
			logger.info(message);
			if (status == HttpURLConnection.HTTP_CREATED) {
				String location = connection.getHeaderField("Location");
				//Location is needed to perform the GET afterwards
				return location;

			} else
				return "POST not successful " + status;
		} catch (Exception e) {
			logger.error("Can not talk to XdomainServer: ", e);
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
			logger.error("Exception at requesting " + targetURL + " :", e);
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	
	public JsonElement executeGetStepPlatform(String targetURL, String user, String password) {

		HttpURLConnection connection = null;
		StringBuilder result = new StringBuilder();
		try {
			// Create connection
			URI url = new URI(targetURL);
			HttpGet getLdpResource = new HttpGet();
			getLdpResource.setURI(url);
			getLdpResource.setHeader("Authorization", "Basic " + Base64.encodeAsString("aifbtech:aifbtech"));
			getLdpResource.setHeader("Accept", "application/ld+json");

			
			// JAX RS Client
			logger.debug("Sending request to STEP Platform: " + getLdpResource.toString());
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpResponse responseLDP = client.execute(getLdpResource);

			logger.info("Response from STEP Platform: " + responseLDP.toString());
			
//			Response mit Entities auslesen
			String entites = "";
			InputStream bodies = responseLDP.getEntity().getContent();
			BufferedReader rt = new BufferedReader(new InputStreamReader( bodies ) );
			String line = "";
			while ((line = rt.readLine()) != null) {
			    entites += line + " ";
			}
			rt.close();	
			
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonEntities = jsonParser.parse(entites);
			
			return jsonEntities;
		} catch (Exception e) {
			logger.error("Exception at requesting " + targetURL + " :", e);
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	

	//Not used right now, copied from another project of sebbader
	public void makeRdfCall(String targetURL) throws NoSuchMethodException, SecurityException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException {
		// Status: hier alle informationen über Java zugreifbar

		// JAX RS Client
		// Client client = ClientBuilder.newClient();
		Client client = ClientBuilder.newBuilder().register(JsonLdMessageBodyReaderWriter.class).build();

		// client macht seinen Call
		List<Node[]> graph = new ArrayList<Node[]>();
		// Here the outgoing RDF graph is built.
		// Blank Node - RDF:type - Whatver + AppDev
		graph.add(new Node[] { new BNode(""), RDF.TYPE, new Resource(STEP.NS + "AppDev") });

		// Here we construct the request builder.
		JerseyInvocation.Builder b = (org.glassfish.jersey.client.JerseyInvocation.Builder) client.target(targetURL)
				.request(JsonLdMessageBodyReaderWriter.JSONLD_MEDIATYPE);

		// The required method is not accessible (private/package)
		// We undermine Java Security by setting setAccessible to TRUE
		Method m = b.getClass().getDeclaredMethod("request");
		m.setAccessible(true);// Abracadabra
		ClientRequest cr = (ClientRequest) m.invoke(b);// now its OK

		// To be able to build bla afterwards
		cr.setEntityType(graph.getClass().getGenericSuperclass());
		// Request is built.
		// JerseyInvocation bla = (JerseyInvocation) b.buildPost(Entity.entity(new
		// GenericEntity<Iterable<Node[]>>( list ) {
		// },JsonLdMessageBodyReaderWriter.JSONLD_MEDIATYPE ));
		JerseyInvocation innvocation = (JerseyInvocation) b.buildGet();
		cr.accept(JsonLdMessageBodyReaderWriter.JSONLD_MEDIATYPE);

		// cr.setEntityType(list.getClass().getGenericSuperclass());
		// Request is sent
		Response response = innvocation.invoke();

		
		
		// Response object is built, Then we read the Response
		Iterable<Node[]> data = response.readEntity(new GenericType<Iterable<Node[]>>() { });
		for (Iterator<Node[]> iter = data.iterator(); iter.hasNext();) {
			Node[] node = iter.next();
			logger.info("Node: " + node[0] + node[1] + node[2]);
		}

		// responses werden gelesen

		// als rdf über return zurückgegeben
	}

	


}
