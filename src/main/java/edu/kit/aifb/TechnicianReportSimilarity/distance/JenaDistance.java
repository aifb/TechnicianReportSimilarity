package edu.kit.aifb.TechnicianReportSimilarity.distance;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.ontology.OntTools;
import org.apache.jena.ontology.OntTools.Path;
import org.apache.jena.ontology.OntTools.PredicatesFilter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.kit.aifb.TechnicianReportSimilarity.Concept;

//Source: https://thusithamabotuwana.wordpress.com/2012/01/20/determining-shortest-distance-between-ontology-concepts-using-jena-onttools/
public class JenaDistance {

	private final Logger logger = LoggerFactory.getLogger(JenaDistance.class);

	public OntModel model;


	public JenaDistance() {
		// TODO Auto-generated constructor stub
	}


	public JenaDistance(String inputFileName, String namespace) {
		long startTime = System.currentTimeMillis();

		// the inputFileName file needs to be created by doing "Save As.." and "RDF/XML" for a 'normal' OWL file. Otherwise we get Jena parse errors
		if (model == null)
		{
			this.model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
			InputStream in = FileManager.get().open(inputFileName);
			model.read(in, "http://people.aifb.kit.edu/mu2771/step#"); 
			logger.info("Ontology load time: ({} sec)", (System.currentTimeMillis() - startTime) / 1000.0);

		} 
	}





	public double[][] calculateJenaDistances(List<Concept> reportConcepts,List<Concept> proposalConcepts)
	{
		JenaDistance jd = new JenaDistance("./ontologies/step_all_with_inverted_relations.owl","http://people.aifb.kit.edu/mu2771/step");
		double[][] distances = new double[reportConcepts.size()][proposalConcepts.size()];
		Iterator<Concept> reportIterator = reportConcepts.iterator();
		int reportCounter = 0;
		while (reportIterator.hasNext()){
			Concept reportConcept = reportIterator.next();
			String reportURL = reportConcept.getUrl();
			reportURL = reportURL.replace("step/", "step#");

			Iterator<Concept> proposalIterator = proposalConcepts.iterator();
			int proposalCounter =0;
			while(proposalIterator.hasNext())
			{
				Concept proposalConcept = proposalIterator.next();
				String proposalURL = proposalConcept.getUrl();
				proposalURL = proposalURL.replace("step/", "step#");
				distances[reportCounter][proposalCounter] = jd.FindJenaDistance(reportURL, proposalURL);
				//logger.info("Now comparing " + reportURL + " and " + proposalURL);
				proposalCounter++;
			}
			reportCounter++;

		}
		return distances;


	}



	public int FindJenaDistance(String fromSubClassURI, String toSuperClassURI) {

		// Jena implementation       

		OntResource start = model.getOntResource(fromSubClassURI);
		OntResource end = model.getOntResource(toSuperClassURI);




		Collection <Property> sameAsCollection = new ArrayList <Property>();
		sameAsCollection.add(new PropertyImpl("http://www.w3.org/2002/07/owl#sameAs"));
		PredicatesFilter sameAsPf = new PredicatesFilter(sameAsCollection);

		
		if (OntTools.findShortestPath(model, start, end, sameAsPf) != null) {
			
			logger.warn(start +" and " + end + " are connected by a sameAs relation.");
			return 0;
			
		} else if(fromSubClassURI.equals(toSuperClassURI)){

			logger.warn(start + " and " + end + " are the same node!");
			return 0;
			
		} else {
			
			Collection <Property> predicates = new ArrayList <Property>();
			
//			predicates.add(new PropertyImpl("http://www.w3.org/2000/01/rdf-schema#subClassOf"));
//			predicates.add(new PropertyImpl("http://www.example.org/subClassOfInverted"));
			predicates.addAll(StepOntologyHelper.getUsedProperties());
			predicates.addAll(StepOntologyHelper.getInvertefProperties());
			
			PredicatesFilter pf = new PredicatesFilter(predicates);
			
			// Start the shortest path algorithm
			Path path = OntTools.findShortestPath(model, start, end, pf);

			if (path != null){
				int superClasses = 0;
				for (Statement s: path) {
					superClasses++;
					logger.info(s.getObject().toString());
				}
				logger.info("Shortest distance from " + start + " to " + end + " = " + superClasses);
				return superClasses;
			} else {
				logger.warn("No path from " + start + " to " + end);
				return -1;
			}
		}
	}


}
