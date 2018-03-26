package edu.kit.aifb.TechnicianReportSimilarity;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.kit.aifb.TechnicianReportSimilarity.distance.JenaDistance;

public class JenaSimilarityTest {

	@Test
	public void test() {
		
		JenaDistance jd2 = new JenaDistance("ontologies/step_all_with_inverted_relations.owl","http://people.aifb.kit.edu/mu2771/step#");
		
		String fromSubClassURI = "http://people.aifb.kit.edu/mu2771/step#ink_train";
//		String toSuperClassURI = "http://people.aifb.kit.edu/mu2771/step#inking_station";
//		String fromSubClassURI = "http://people.aifb.kit.edu/mu2771/step#printing_press";
//		String fromSubClassURI = "http://purl.oclc.org/NET/ssnx/ssn#System";
		String toSuperClassURI = "http://purl.oclc.org/NET/ssnx/ssn#Device";
//		String toSuperClassURI = "http://people.aifb.kit.edu/mu2771/step#powder_spray";

//		String fromSubClassURI = "http://purl.oclc.org/NET/ssnx/ssn#Device";
//		String toSuperClassURI = "http://purl.oclc.org/NET/ssnx/ssn#System";
		
		System.out.println("Jena Distance between " + fromSubClassURI.split("#")[1] + " and " + toSuperClassURI.split("#")[1] + ":");
		System.out.println(jd2.FindJenaDistance(fromSubClassURI, toSuperClassURI));

	}

}
