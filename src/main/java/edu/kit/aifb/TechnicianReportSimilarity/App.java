
package edu.kit.aifb.TechnicianReportSimilarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
		app.runAllJenaAndWriteToExcel();
		//app.runWS4J();


	}


	/**
	 * 
	 */
	private void runAllJenaAndWriteToExcel() {
		ExcelWriter ew = new ExcelWriter();
		for (int i = 25; i<26;i++)
		{	//These are the reports/proposals which are not going through.
			if(i == 12 || i == 23 || i == 24) {
				continue;
			}
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
			double[][] distances = jenaDistance.calculateJenaDistances(reportConceptsWoExampleOrg,proposalConceptsWoExampleOrg);

			logger.info(Arrays.deepToString(distances));

			ew.writeToExcel(Integer.toString(i), reportConceptsWoExampleOrg, proposalConceptsWoExampleOrg, distances);
		}


		//		SimilarityCalculationDemo SimilarityCalculator = new SimilarityCalculationDemo();
		//		SimilarityCalculator.run("dog","pet");

		ew.writeWorkbookToFile("JenaResults");
	}


	private void runWS4J() {

		int number = 25;
		TechnicianReportParser parser = new TechnicianReportParser();
		List<Concept> proposalConcepts = parser.readInProposalLineByLineAndAnnotate(number + "_proposal");	
		List<Concept> proposalConceptsWoExampleOrg = parser.removeExampleEntities(proposalConcepts);


		List<Concept> reportConcepts = parser.readInReportLineByLineAndAnnotate(number + "_report");
		List<Concept> reportConceptsWoExampleOrg = parser.removeExampleEntities(reportConcepts);


		//
		//		XDomainNlpClient nlpService = new XDomainNlpClient();
		//		JsonElement annotated = nlpService.executeGetStepPlatform("https://step-platform.usu-research.ml/step-platform-app/v1/api/marmotta/ldp/StackOverflow/StackoverflowQuestion", "aifbtech", "aifbtech");
		//		logger.info(annotated.toString());
		//		List<Concept> reportConceptsFromStackoverflow = parser.parseRelevantPartsString(annotated.toString());




		Iterator<Concept> reportIterator = reportConceptsWoExampleOrg.iterator();
		List <String> reportLabels = new ArrayList<String>();
		while(reportIterator.hasNext()) {
			reportLabels.add(reportIterator.next().getLabel());
		}
		String [] reportLabelsArray = reportLabels.toArray(new String[0]);

		Iterator<Concept> proposalIterator = proposalConceptsWoExampleOrg.iterator();
		List <String> proposalLabels = new ArrayList<String>();
		while(proposalIterator.hasNext()) {
			proposalLabels.add(proposalIterator.next().getLabel());
		}
		String [] proposalLabelsArray = proposalLabels.toArray(new String[0]);

		ExcelWriter ew = new ExcelWriter();
		ew.writeAllToExcel(reportConcepts, reportLabelsArray, proposalConcepts, proposalLabelsArray, number);


	}

}
