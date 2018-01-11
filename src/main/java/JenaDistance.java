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
		FindJenaDistance();

	}
	public static void FindJenaDistance() {

        // Jena implementation 

        long startTime = System.currentTimeMillis();
        
        // this file needs to be created by doing "Save As.." and "RDF/XML" for a 'normal' OWL file. Otherwise we get Jena parse errors
        String inputFileName = "./ontologies/step_all_classes.owl";
        //Namespace
        String ns = "http://people.aifb.kit.edu/mu2771/step";

        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        InputStream in = FileManager.get().open(inputFileName);
        model.read(in, "");  
        
        System.out.format("Ontology load time: (%7.2f sec)%n%n", (System.currentTimeMillis() - startTime) / 1000.0);        
        
        String fromSubClassURI = "http://people.aifb.kit.edu/mu2771/step#UnplannedWorkOrder";
        String toSuperClassURI = "http://people.aifb.kit.edu/mu2771/step#WorkOrder";
        OntClass fromSubClass = model.getOntClass(fromSubClassURI);
        OntClass toSuperClass = model.getOntClass(toSuperClassURI);
        //OntClass fromSubClass = model.getOntClass("http://people.aifb.kit.edu/mu2771/step#FinishedWorkOrder");
//        OntClass fromSubClass = model.getOntClass("http://people.aifb.kit.edu/mu2771/step#powder_spray_device");
//        OntClass toSuperClass = model.getOntClass("http://people.aifb.kit.edu/mu2771/step#fan");
        
        
//		To be discussed with Sebastian: it works with Filter.any. Relation is rdfs:subclassOf. How to get that into PropertyImpl.
//        Path path = OntTools.findShortestPath(model, fromSubClass, toSuperClass, Filter.any);
        Collection <Property> predicates = new ArrayList <Property>();
        //How to get the predicate subclassOf in? Constructor takes a String uri
        // Subsequent and rdfs:subClassOf does not work.
        PropertyImpl subClassOf = new PropertyImpl("http://www.w3.org/2000/01/rdf-schema#subClassOf");
        PropertyImpl symmetric = new PropertyImpl("http://people.aifb.kit.edu/mu2771/step/symmetricProperty");
        predicates.add(subClassOf);
        PredicatesFilter pf = new PredicatesFilter(predicates);
        Path path = OntTools.findShortestPath(model, fromSubClass, toSuperClass, pf);

        
        if (fromSubClassURI.equals(toSuperClassURI)){
        	System.out.println("Same node");
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
        }else {
            System.out.println("No path from " + fromSubClass + " to " + toSuperClass);
        
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

        System.out.format("\nProcessing time: (%7.2f sec)%n%n", (System.currentTimeMillis() - startTime) / 1000.0);
    }
	}

}
