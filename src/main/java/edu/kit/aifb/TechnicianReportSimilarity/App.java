
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
		ExcelWriter ew = new ExcelWriter();
		for (int i = 10; i<28;i++)
		{
			if(i == 12 || i == 23 || i == 24) {
				continue;
			}
			//TBD: Start variables with lowercase letters
			TechnicianReportParser parser = new TechnicianReportParser();
			List<Concept> proposalConcepts = parser.readInProposalLineByLineAndAnnotate(i+"_proposal");	
			List<Concept> proposalConceptsWoExampleOrg = parser.removeExampleEntities(proposalConcepts);
			
			
			List<Concept> reportConcepts = parser.readInReportLineByLineAndAnnotate(i+"_report");
			List<Concept> reportConceptsWoExampleOrg = parser.removeExampleEntities(reportConcepts);
			
	//
//			XDomainNlpClient nlpService = new XDomainNlpClient();
//			JsonElement annotated = nlpService.executeGetStepPlatform("https://step-platform.usu-research.ml/step-platform-app/v1/api/marmotta/ldp/StackOverflow/StackoverflowQuestion", "aifbtech", "aifbtech");
//			logger.info(annotated.toString());
//			List<Concept> reportConceptsFromStackoverflow = parser.parseRelevantPartsString(annotated.toString());


			
			
			JenaDistance jenaDistance = new JenaDistance();
			int[][] distances = jenaDistance.calculateJenaDistances(reportConceptsWoExampleOrg,proposalConceptsWoExampleOrg);
//			System.out.println("Testmatrix");
//			for (int a = 0; i < distances.length;i++)
//			{
//				for (int j = 0; j<distances[a].length;j++) {
//					System.out.print(distances[a][j] + " ");
//				}
//					System.out.println();
//			}
			
			logger.info(Arrays.deepToString(distances));
			
			ew.writeToExcel(i, reportConceptsWoExampleOrg, proposalConceptsWoExampleOrg, distances);
		}


		//		SimilarityCalculationDemo SimilarityCalculator = new SimilarityCalculationDemo();
		//		SimilarityCalculator.run("dog","pet");

		// String proposal = readInFile("7_proposal");
		ew.writeWorkbookToFile();
	}
	
	
}
