package edu.kit.aifb.TechnicianReportSimilarity_;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
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

//Source: https://thusithamabotuwana.wordpress.com/2012/01/20/determining-shortest-distance-between-ontology-concepts-using-jena-onttools/
public class JenaDistance {

	public static void main(String[] args) {
        String fromSubClassURI = "http://people.aifb.kit.edu/mu2771/step#ink_train";
        String toSuperClassURI = "http://people.aifb.kit.edu/mu2771/step#inking_station";
		FindJenaDistance(fromSubClassURI, toSuperClassURI);

	}
	public static int FindJenaDistance(String fromSubClassURI, String toSuperClassURI) {

        // Jena implementation 

        long startTime = System.currentTimeMillis();
        
        // this file needs to be created by doing "Save As.." and "RDF/XML" for a 'normal' OWL file. Otherwise we get Jena parse errors
        String inputFileName = "./ontologies/step_classes_murks.owl";
        //Namespace
        String ns = "http://people.aifb.kit.edu/mu2771/step";

        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        InputStream in = FileManager.get().open(inputFileName);
        model.read(in, "");  
        
        System.out.format("Ontology load time: (%7.2f sec)%n%n", (System.currentTimeMillis() - startTime) / 1000.0);        
        

        
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
        	System.out.println(fromSubClass +" and " + toSuperClass + " are connected by a sameAs relation.");
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

            
            if (fromSubClassURI.equals(toSuperClassURI)){
            	System.out.println("Same node");
            	return 0;
            }
            else if (path != null){
                int superClasses = 0;
                for (Statement s: path) {
                    if (s.getObject().toString().startsWith(ns)) {
                        // filter out OWL Classes
                        superClasses++;
                        System.out.println(s.getObject());
                    }
                }
                System.out.println("Shortest distance from " + fromSubClass + " to " + toSuperClass + " = " + superClasses);
                return superClasses;
            }else {
                System.out.println("No path from " + fromSubClass + " to " + toSuperClass);
                return -1;
        }
        
        

//        if (path != null){
//            int superClasses = 0;
//            for (Statement s: path) {
//                if (s.getObject().toString().startsWith(ns)) {
//                    // filter out OWL Classes
//                    superClasses++;
//                    System.out.println(s.getObject());
//                }
//            }
//            System.out.println("Shortest distance from " + fromSubClass + " to " + toSuperClass + " = " + superClasses);
//        }else if (fromSubClass == toSuperClass){
//            System.out.println("Same node");
//        }else {
//            System.out.println("No path from " + fromSubClass + " to " + toSuperClass);
//        }   

        //System.out.format("\nProcessing time: (%7.2f sec)%n%n", (System.currentTimeMillis() - startTime) / 1000.0);
    }
	}

}
