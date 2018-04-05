
package edu.kit.aifb.TechnicianReportSimilarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
		//app.runAllJenaAndWriteToExcel();
//		for (int i = 10; i<28;i++)
//		{
//			try {
//				app.runWS4J(i);
//			} catch(NullPointerException npe) {;
//				continue;
//			}
//		}
//		app.writeAllLabelsToExcel();
		for (int i = 10; i<28;i++) {
			app.writeLabelsForIDFScores(i);
		}
		
		


	}


	/**
	 * 
	 */
	private void runAllJenaAndWriteToExcel() {
		ExcelWriter ew = new ExcelWriter();

			for (int i = 10; i<28;i++)
			{
				try {
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

				//logger.info(Arrays.deepToString(distances));

				ew.writeToExcel(Integer.toString(i), reportConceptsWoExampleOrg, proposalConceptsWoExampleOrg, distances);
			}catch(NullPointerException npe)
				{
				continue;
			}
		}



		//		SimilarityCalculationDemo SimilarityCalculator = new SimilarityCalculationDemo();
		//		SimilarityCalculator.run("dog","pet");

		ew.writeWorkbookToFile("JenaResults");
	}


	private void runWS4J(int number)  {

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

	public void writeAllLabelsToExcel() {
		ExcelWriter ew = new ExcelWriter();
		List<String> allLabels = new ArrayList<String>();
		for (int i = 10; i<28;i++) {
			try {
				TechnicianReportParser parser = new TechnicianReportParser();
				List<Concept> proposalConcepts = parser.readInProposalLineByLineAndAnnotate(i+"_proposal");	
				List<Concept> proposalConceptsWoExampleOrg = parser.removeExampleEntities(proposalConcepts);


				List<Concept> reportConcepts = parser.readInReportLineByLineAndAnnotate(i+"_report");
				List<Concept> reportConceptsWoExampleOrg = parser.removeExampleEntities(reportConcepts);
				
				Iterator<Concept> reportIterator = reportConceptsWoExampleOrg.iterator();
				List <String> reportLabels = new ArrayList<String>();
				while(reportIterator.hasNext()) {
					reportLabels.add(reportIterator.next().getLabel());
				}

				Iterator<Concept> proposalIterator = proposalConceptsWoExampleOrg.iterator();
				List <String> proposalLabels = new ArrayList<String>();
				while(proposalIterator.hasNext()) {
					proposalLabels.add(proposalIterator.next().getLabel());
				}
				
				allLabels.addAll(proposalLabels);
				allLabels.addAll(reportLabels);
				
				
				
				}catch(NullPointerException npe) {
					continue;
				}	
		}
		ew.writeLabelsInRows(allLabels);	
	}
	public void writeLabelsForIDFScores(int number) {
		ExcelWriter ew = new ExcelWriter();
		try {
			TechnicianReportParser parser = new TechnicianReportParser();
			List<Concept> proposalConcepts = parser.readInProposalLineByLineAndAnnotate(number+"_proposal");	
			List<Concept> proposalConceptsWoExampleOrg = parser.removeExampleEntities(proposalConcepts);


			List<Concept> reportConcepts = parser.readInReportLineByLineAndAnnotate(number+"_report");
			List<Concept> reportConceptsWoExampleOrg = parser.removeExampleEntities(reportConcepts);
			
			Iterator<Concept> reportIterator = reportConceptsWoExampleOrg.iterator();
			List <String> reportLabels = new ArrayList<String>();
			while(reportIterator.hasNext()) {
				reportLabels.add(reportIterator.next().getLabel());
			}

			Iterator<Concept> proposalIterator = proposalConceptsWoExampleOrg.iterator();
			List <String> proposalLabels = new ArrayList<String>();
			while(proposalIterator.hasNext()) {
				proposalLabels.add(proposalIterator.next().getLabel());
			
			}
			ew.writeLabelsForIDFScores("Labels", reportLabels, proposalLabels, number);
	}catch(NullPointerException npe) {
		System.out.println("XDomain Error");
	}	
		
	}
}
