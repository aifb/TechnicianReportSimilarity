package edu.kit.aifb.TechnicianReportSimilarity.distance;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
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
			model.read(in, "http://people.aifb.kit.edu/mu2771/step/asd"); 
			logger.info("Ontology load time: ({} sec)", (System.currentTimeMillis() - startTime) / 1000.0);

		} 
	}
	




	public double[][] calculateJenaDistances(List<Concept> reportConcepts,List<Concept> proposalConcepts)
	{
		JenaDistance jd = new JenaDistance("./ontologies/step_classes_murks.owl","http://people.aifb.kit.edu/mu2771/step");
		double[][] distances = new double[reportConcepts.size()][proposalConcepts.size()];
		Iterator<Concept> reportIterator =reportConcepts.iterator();
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
		//        String fromSubClassURI = "http://people.aifb.kit.edu/mu2771/step#FinishedWorkOrder";
		//        String toSuperClassURI = "http://people.aifb.kit.edu/mu2771/step#UnplannedWorkOrder";


		OntClass fromSubClass = model.getOntClass(fromSubClassURI);
		OntClass toSuperClass = model.getOntClass(toSuperClassURI);
		//OntClass fromSubClass = model.getOntClass("http://people.aifb.kit.edu/mu2771/step#FinishedWorkOrder");
		//        OntClass fromSubClass = model.getOntClass("http://people.aifb.kit.edu/mu2771/step#powder_spray_device");
		//        OntClass toSuperClass = model.getOntClass("http://people.aifb.kit.edu/mu2771/step#fan");


		//		To be discussed with Sebastian: it works with Filter.any. Relation is rdfs:subclassOf. How to get that into PropertyImpl.
		//        Path path = OntTools.findShortestPath(model, fromSubClass, toSuperClass, Filter.any);

		Collection <Property> sameAsCollection = new ArrayList <Property>();
		PropertyImpl sameAs = new PropertyImpl("http://www.w3.org/2002/07/owl#sameAs");
		sameAsCollection.add(sameAs);
		PredicatesFilter sameAsPf = new PredicatesFilter(sameAsCollection);
		Path sameAsPath = OntTools.findShortestPath(model, fromSubClass, toSuperClass, sameAsPf);
		if (sameAsPath != null)
		{
			logger.info(fromSubClass +" and " + toSuperClass + " are connected by a sameAs relation.");
			return 0;
		}
		else if(fromSubClassURI.equals(toSuperClassURI)){
			logger.info("Same node");
			return 0;
		}
		else
		{
			Collection <Property> predicates = new ArrayList <Property>();
			PropertyImpl subClassOf = new PropertyImpl("http://www.w3.org/2000/01/rdf-schema#subClassOf");
			predicates.add(subClassOf);
			PropertyImpl subClassOfInverted = new PropertyImpl("http://www.example.org/subClassOfInverted");
			predicates.add(subClassOfInverted);
			PredicatesFilter pf = new PredicatesFilter(predicates);
			Path path = OntTools.findShortestPath(model, fromSubClass, toSuperClass, pf);

			if (path != null){
				int superClasses = 0;
				for (Statement s: path) {
					superClasses++;
					logger.info(s.getObject().toString());
				}
				logger.info("Shortest distance from " + fromSubClass + " to " + toSuperClass + " = " + superClasses);
				return superClasses;
			}else {
				logger.info("No path from " + fromSubClass + " to " + toSuperClass);
				return -1;
			}
		}
	}


}
