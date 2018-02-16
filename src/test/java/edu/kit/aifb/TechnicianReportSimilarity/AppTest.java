package edu.kit.aifb.TechnicianReportSimilarity;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.kit.aifb.TechnicianReportSimilarity.web.XDomainNlpClient;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	

	private final Logger logger = LoggerFactory.getLogger(AppTest.class);
	
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    
    public void testStepPlatformCall() {
    	

		XDomainNlpClient nlpService = new XDomainNlpClient();
		JsonElement annotated = nlpService.executeGetStepPlatform("https://step-platform.usu-research.ml/step-platform-app/v1/api/marmotta/ldp/StackOverflow/StackoverflowQuestion", "aifbtech", "aifbtech");
		
		logger.info(annotated.toString());
		
		TechnicianReportParser parser = new TechnicianReportParser();
		List<Concept> reportConceptsFromStackoverflow = parser.parseRelevantPartsString(annotated.toString());
		

		logger.info(reportConceptsFromStackoverflow.toString());
		
    }
}
