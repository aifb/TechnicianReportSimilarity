import java.io.InputStream;


import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntTools;
import org.apache.jena.ontology.OntTools.Path;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
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
        String inputFileName = "./ontologies/step_all.owl";

        String ns = "http://people.aifb.kit.edu/mu2771/step";

        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        InputStream in = FileManager.get().open(inputFileName);
        model.read(in, "");  
        
        System.out.format("Ontology load time: (%7.2f sec)%n%n", (System.currentTimeMillis() - startTime) / 1000.0);        

        OntClass fromSubClass = model.getOntClass("http://people.aifb.kit.edu/mu2771/step#FinishedWorkOrder");

        OntClass toSuperClass = model.getOntClass("http://people.aifb.kit.edu/mu2771/step#UnplannedWorkOrder");

        Path path = OntTools.findShortestPath(model, fromSubClass, toSuperClass, Filter.any);

        if (path != null){
            int superClasses = 0;
            for (Statement s: path) {
                if (s.getObject().toString().startsWith(ns)) {
                    // filter out OWL Classes
                    superClasses++;
                    System.out.println(s.getObject());
                }
            }
            System.out.println("Shortest distance from " + fromSubClass + " to " + toSuperClass + " = " + superClasses);
        }else if (fromSubClass == toSuperClass){
            System.out.println("Same node");
        }else {
            System.out.println("No path from " + fromSubClass + " to " + toSuperClass);
        }   

        System.out.format("\nProcessing time: (%7.2f sec)%n%n", (System.currentTimeMillis() - startTime) / 1000.0);
        

    }
	

}
