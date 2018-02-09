
package edu.kit.aifb.TechnicianReportSimilarity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


//import io.swagger.jaxrs.config.BeanConfig;
//import sample.hello.resources.HelloResource;

//import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
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
import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.Resource;
import org.semanticweb.yars.nx.namespace.RDF;
import edu.kit.aifb.TechnicianReportSimilarity.distance.JenaDistance;
import edu.kit.aifb.TechnicianReportSimilarity.vocabs.STEP;

public class App {
	
	
	
	

	public static void main(String[] args) {


		//TBD: Start variables with lowercase letters
		TechnicianReportParser parser = new TechnicianReportParser();
		List<Concept> proposalConcepts = parser.readInProposalLineByLineAndAnnotate("1_proposalFake");	
		List<Concept> proposalConceptsWoExampleOrg = parser.removeExampleEntities(proposalConcepts);
		
		
		List<Concept> reportConcepts = parser.readInReportLineByLineAndAnnotate("1_FakeReport");
		List<Concept> reportConceptsWoExampleOrg = parser.removeExampleEntities(reportConcepts);
		
		
		
		JenaDistance jenaDistance = new JenaDistance();
		int[][] distances = jenaDistance.calculateJenaDistances(reportConceptsWoExampleOrg,proposalConceptsWoExampleOrg);
		
		
		
		System.out.println(Arrays.deepToString(distances));

		//		SimilarityCalculationDemo SimilarityCalculator = new SimilarityCalculationDemo();
		//		SimilarityCalculator.run("dog","pet");

		// String proposal = readInFile("7_proposal");

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
			System.out.println("Node: " + node[0] + node[1] + node[2]);
		}

		// responses werden gelesen

		// als rdf über return zurückgegeben
	}

	

	
	
}
