package edu.kit.aifb.TechnicianReportSimilarity;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.kit.aifb.TechnicianReportSimilarity.distance.JenaDistance;

public class JenaSimilarity {

	@Test
	public void test() {
		
		JenaDistance jd2 = new JenaDistance("ontologies/step_classes_murks.owl","http://people.aifb.kit.edu/mu2771/step");
		
		String fromSubClassURI = "http://people.aifb.kit.edu/mu2771/step#ink_train";
		String toSuperClassURI = "http://people.aifb.kit.edu/mu2771/step#inking_station";
		
		System.out.println("Jena Distance between 'ink_train' and 'inking_station':");
		System.out.println(jd2.FindJenaDistance(fromSubClassURI, toSuperClassURI));

	}

}
