
package edu.kit.aifb.TechnicianReportSimilarity;

import java.util.Arrays;
import java.util.List;


//import io.swagger.jaxrs.config.BeanConfig;
//import sample.hello.resources.HelloResource;

//import io.swagger.jaxrs.config.BeanConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import edu.kit.aifb.TechnicianReportSimilarity.distance.JenaDistance;
import edu.kit.aifb.TechnicianReportSimilarity.web.XDomainNlpClient;


/**
 * 
 * @author sba
 *
 */
public class App {
	
	

	private final Logger logger = LoggerFactory.getLogger(App.class);
	

	
	
	public static void main(String[] args) {

		App app = new App();
		app.run();
		

	}


	/**
	 * 
	 */
	private void run() {

		
		//TBD: Start variables with lowercase letters
		TechnicianReportParser parser = new TechnicianReportParser();
		List<Concept> proposalConcepts = parser.readInProposalLineByLineAndAnnotate("1_proposalFake");	
		List<Concept> proposalConceptsWoExampleOrg = parser.removeExampleEntities(proposalConcepts);
		
		
		List<Concept> reportConcepts = parser.readInReportLineByLineAndAnnotate("1_FakeReport");
		List<Concept> reportConceptsWoExampleOrg = parser.removeExampleEntities(reportConcepts);
		
//
//		XDomainNlpClient nlpService = new XDomainNlpClient();
//		JsonElement annotated = nlpService.executeGetStepPlatform("https://step-platform.usu-research.ml/step-platform-app/v1/api/marmotta/ldp/StackOverflow/StackoverflowQuestion", "aifbtech", "aifbtech");
//		logger.info(annotated.toString());
//		List<Concept> reportConceptsFromStackoverflow = parser.parseRelevantPartsString(annotated.toString());


		
		
		JenaDistance jenaDistance = new JenaDistance();
		int[][] distances = jenaDistance.calculateJenaDistances(reportConceptsWoExampleOrg,proposalConceptsWoExampleOrg);
		
		
		
		logger.info(Arrays.deepToString(distances));

		//		SimilarityCalculationDemo SimilarityCalculator = new SimilarityCalculationDemo();
		//		SimilarityCalculator.run("dog","pet");

		// String proposal = readInFile("7_proposal");
		
	}
	
}
