package edu.kit.aifb.TechnicianReportSimilarity.distance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.util.FileManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StepOntologyHelper {


	private static Logger logger = LoggerFactory.getLogger(StepOntologyHelper.class);

	public static List<PropertyImpl> getUsedProperties() {

		List<PropertyImpl> properties = new ArrayList<PropertyImpl>();

		properties.add(new PropertyImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"));
		properties.add(new PropertyImpl("http://www.w3.org/2000/01/rdf-schema#subClassOf"));	

		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#requires"));	
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#implements"));	
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#endTime"));	
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#isSettingFor"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#derivedUnitOfSystem"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#memberDuring"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#hasDeployment"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemDimension"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#qualityOfObservation"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasPostalCode"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#remuneration"));
		properties.add(new PropertyImpl("http://xmlns.com/foaf/0.1/maker"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#classification"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#coherentUnitSystem"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasRelated"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#basisElement"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#url"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#hasRegion"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#hasSite"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#hasWorktimeAt"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#reifiableBy"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasLanguage"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasCalendarLink"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#resultedFrom"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#isExecutedBy"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/time#hasBeginning"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#has-author"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#observationResultTime"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#owner"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#allowedUnitOfSystem"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#ownedBy"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#isAvailableAt"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#hasValue"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#has-first-child"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#wasNewWorkOrder"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#hasParticipant"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#role"));
		properties.add(new PropertyImpl("http://www.w3.org/2001/sw/BestPractices/OEP/SimplePartWhole/part.owl#partOf"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#madeObservation"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasOrganizationUnit"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#valueQuantity"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemBaseUnit"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasGeo"));
		properties.add(new PropertyImpl("http://purl.org/dc/elements/1.1/subject"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#logo"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#observedProperty"));
		properties.add(new PropertyImpl("http://www.w3.org/2003/01/geo/wgs84_pos#location"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#hasSubOrganization"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasTelephone"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#relates-to-product-metadata"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#linkedTo"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasTitle"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/time#timeZone"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#requires-supply"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#hasMembership"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#hasInput"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#subOrganizationOf"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemBaseQuantityKind"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#email"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasLogo"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#siteOf"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#inDeployment"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#references-information-unit"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/dtype#defaultValue"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#memberOf"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#generalization"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/time#intervalAfter"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#usedModel"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemDerivedQuantityKind"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#detects"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#hasProperty"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasNickname"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#includesEvent"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#has-version"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#specialization"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasGivenName"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#has-document-type"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#relatedTo"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasRole"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/time#inside"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/time#intervalMeets"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasCountryName"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#includesObject"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#hasSurvivalRange"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#isObjectIncludedIn"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#unitOfSystem"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#has-topic-type"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#ofFeature"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step/0.2.0/moduleC#date"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemPrefixUnit"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#hasMeasurementProperty"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#geo"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#requires-time"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/dtype#refersTo"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/time#intervalOverlaps"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#coherentDerivedUnitOfSystem"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasEmail"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#coherentUnitOfSystem"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#deployedOnPlatform"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#hasSubSystem"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#hasQuality"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasAdditionalName"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#hasSurvivalProperty"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemDefinedUnit"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#observes"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasUID"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemCoherentUnit"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#photo"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#hasOperatingRange"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#hasPrediction"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#elementKind"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasURL"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#exactMatch"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#heldBy"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#memberOf"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#baseDimensionEnumeration"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#hasRegisteredSite"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/dtype#derivedFrom"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/time#intervalEquals"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#isProxyFor"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#sensingMethodUsed"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/prov#wasDerivedFrom"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#attachedSystem"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasMember"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasCategory"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#observationSamplingTime"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#postIn"));
		properties.add(new PropertyImpl("http://xmlns.com/foaf/0.1/made"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#adr"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#hasMember"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#hasMeasurementCapability"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasFN"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#resultingOrganization"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#holds"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#hasWorkOrder"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/dtype#hasMember"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasAddress"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#isRegionFor"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemUnit"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#inCondition"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasNote"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasPhoto"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#has-next-sibling"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemDerivedUnit"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#hasPart"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#tel"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#isProducedBy"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#sound"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#changedBy"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#hasCatalogEntry"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemCoherentDerivedUnit"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#hasOutput"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasInstantMessage"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#has-information-type"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#isParticipantIn"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#quantityValue"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#unitOf"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#has-subject"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#key"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasCalendarBusy"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasName"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#siteAddress"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#requires-qualification"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#originalOrganization"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#baseUnitOfSystem"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasSource"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#hasGraphRole"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#onPlatform"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#hasPriority"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#startTime"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#isBusyAt"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#deployedSystem"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#is-version-of"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#withAttributionTo"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#transitiveSubOrganizationOf"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasRegion"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#hasRoute"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasSound"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/dtype#compositeOf"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasHonorificSuffix"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#date"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#runBy"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/time#before"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#hasLocation"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#describes"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#baseQuantityKindOfSystem"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#wasScheduledWorkOrder"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/prov#wasGeneratedBy"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/time#after"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#iirdsRelationConcept"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#hasOperatingProperty"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#default"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#hasPrimarySite"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/prov#used"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#hasPost"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasGender"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#member"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasValue"));
		properties.add(new PropertyImpl("http://xmlns.com/foaf/0.1/based_near"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#quantityKind"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemAllowedUnit"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#hasExpertise"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#n"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#unitSystem"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#deploymentProcessPart"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#featureOfInterest"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#unit"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/dtype#value"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#hasType"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#element"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#isLocationOf"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasOrganizationName"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#org"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#isPropertyOf"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#quantity"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#headOf"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#wasFinishedWorkOrder"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#wasUnplanedWorkOrder"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/time#intervalBefore"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#hasLicenseType"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#hasUnit"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#implementedBy"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/time#intervalDuring"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#relates-to"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#quantityKindOfSystem"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasLocality"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#isQualityOf"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#expertiseOfSystem"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#agent"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#referenceThing"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#relates-to-event"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#organization"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#forProperty"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#observationResult"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemQuantityKind"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasStreetAddress"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasCalendarRequest"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasFamilyName"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#referenceUnit"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasKey"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#prefixUnitOfSystem"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#owningParty"));
		properties.add(new PropertyImpl("http://iirds.tekom.de/iirds#has-contributor"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#satisfies"));
		properties.add(new PropertyImpl("http://people.aifb.kit.edu/mu2771/step#requieredExpertise"));
		properties.add(new PropertyImpl("http://www.w3.org/2006/vcard/ns#hasHonorificPrefix"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#basedAt"));
		properties.add(new PropertyImpl("http://www.loa-cnr.it/ontologies/DUL.owl#isDescribedBy"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#dimensionInverse"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#reportsTo"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#unitFor"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#value"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#definedUnitOfSystem"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#derivedQuantityKindOfSystem"));
		properties.add(new PropertyImpl("http://purl.oclc.org/NET/ssnx/ssn#observedBy"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#usesNonImportedResource"));

		return properties;

	}


	public static List<PropertyImpl> getInvertefProperties() {

		List<PropertyImpl> properties = new ArrayList<PropertyImpl>();

		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#quantityKind_invert"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#dimensionVector_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#type_invert"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#referenceQuantity_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2002/07/owl#allValuesFrom_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2002/07/owl#onProperty_invert"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#basisElement_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2000/01/rdf-schema#subClassOf_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2000/01/rdf-schema#domain_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2000/01/rdf-schema#range_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/1999/02/22-rdf-syntax-ns#first_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2001/sw/BestPractices/OEP/SimplePartWhole/part.owl#partOf_invert"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#generalization_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2002/07/owl#onClass_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2000/01/rdf-schema#subPropertyOf_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2002/07/owl#disjointWith_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2002/07/owl#inverseOf_invert"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemDimension_invert"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemDerivedQuantityKind_invert"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#systemBaseQuantityKind_invert"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#unitSystem_invert"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#baseDimensionEnumeration_invert"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#usesNonImportedResource_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2002/07/owl#equivalentProperty_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2002/07/owl#equivalentClass_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2002/07/owl#someValuesFrom_invert"));
		properties.add(new PropertyImpl("http://qudt.org/schema/qudt#element_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2000/01/rdf-schema#seeAlso_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/2002/07/owl#propertyDisjointWith_invert"));
		properties.add(new PropertyImpl("http://voag.linkedmodel.org/schema/voag#hasCatalogEntry_invert"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#hasRole_invert"));
		properties.add(new PropertyImpl("http://voag.linkedmodel.org/schema/voag#withAttributionTo_invert"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#hasCatalogEntry_invert"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#withAttributionTo_invert"));
		properties.add(new PropertyImpl("http://www.linkedmodel.org/schema/vaem#hasGraphRole_invert"));
		properties.add(new PropertyImpl("http://www.w3.org/ns/org#roleProperty_invert"));

		return properties;
	}

	public static void main(String[] args) throws IOException {

		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		InputStream in = FileManager.get().open("ontologies/step_all.owl");
		model.read(in, "http://people.aifb.kit.edu/mu2771/step#"); 

		File out = new File("ontologies/step_all_inverted_only.nt");
		FileWriter writer = new FileWriter(out);

		//specify the new, inverted relations
		Query query1 = QueryFactory.create("select distinct (CONCAT(STR(?rel), '_invert') AS ?relation) "
				+ "where {  ?Concept ?rel ?obj ."
				+ " ?obj a ?class ."
				+ "}" );
		try {
			QueryExecution qexec = QueryExecutionFactory.create(query1, model);

			ResultSet results = qexec.execSelect() ;

			while (results.hasNext()) {
				QuerySolution solution = results.next();
				String triples = "<" + solution.get("relation").toString() + ">"
						+ " a"
						+ " <http://www.w3.org/2002/07/owl#ObjectProperty> .\n";
				logger.info(triples);
				writer.append(triples);
			}
		} catch (Exception e) {
			logger.warn("", e);
		}





		// invert all subj rel resource triples
		Query query2 = QueryFactory.create("select ?obj (CONCAT(STR(?rel), '_invert') AS ?relation) ?Concept "
				+ "where {  ?Concept ?rel ?obj ."
				+ " ?obj a ?class ."
				+ "}" );

		try {
			QueryExecution qexec = QueryExecutionFactory.create(query2, model);

			ResultSet results = qexec.execSelect() ;

			while (results.hasNext()) {
				QuerySolution solution = results.next();
				String triples = "<" + solution.get("obj").toString() + ">"
						+ " <" + solution.get("relation").toString() + ">"
						+ " <" + solution.get("Concept").toString() + "> .\n";
				logger.info(triples);
				writer.append(triples);
			}
		} catch (Exception e) {
			logger.warn("", e);
		}


		writer.close();
	}
}
