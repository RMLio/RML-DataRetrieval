package be.ugent.mmlab.rml.input.main;

import be.ugent.mmlab.rml.logicalsourcehandler.configuration.LogicalSourceConfiguration;
import be.ugent.mmlab.rml.input.ConcreteLogicalSourceProcessorFactory;
import be.ugent.mmlab.rml.input.processor.SourceProcessor;
import be.ugent.mmlab.rml.mapdochandler.extraction.std.StdRMLMappingFactory;
import be.ugent.mmlab.rml.mapdochandler.retrieval.RMLDocRetrieval;
import be.ugent.mmlab.rml.model.RMLMapping;
import be.ugent.mmlab.rml.model.TriplesMap;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.IOUtils;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by andimou on 9/22/16.
 */
public class Main {

    // Log
    static Logger log = LoggerFactory.getLogger(
            Main_depr.class.getPackage().toString());

    public static void main(String[] args) {

        CommandLine commandLine;
        String outputFile = null;
        String map_doc = null;
        StdRMLMappingFactory mappingFactory = new StdRMLMappingFactory();

        log.info("=================================================");
        log.info("RML Mapping and Logical Source Handler");
        log.info("=================================================");
        log.info("");


        try {
            commandLine = LogicalSourceConfiguration.parseArguments(args);

            if (commandLine.hasOption("h")) {
                LogicalSourceConfiguration.displayHelp();
            }
            if (commandLine.hasOption("o")) {
                outputFile = commandLine.getOptionValue("o", null);
            }
            if (commandLine.hasOption("m")) {
                map_doc = commandLine.getOptionValue("m", null);
            }

            log.info("========================================");
            log.info("Retrieving the RML Mapping Document...");
            log.info("========================================");
            RMLDocRetrieval mapDocRetrieval = new RMLDocRetrieval();
            Repository repository =
                    mapDocRetrieval.getMappingDoc(map_doc, RDFFormat.TURTLE);

            if(repository == null){
                log.debug("Problem retrieving the RML Mapping Document");
                System.exit(1);
            }

            log.info("========================================");
            log.info("Extracting the RML Mapping Definitions..");
            log.info("========================================");
            RMLMapping mapping = mappingFactory.extractRMLMapping(repository);

            TriplesMap triplesMap = mapping.getTriplesMaps().iterator().next();

            ConcreteLogicalSourceProcessorFactory logicalSourceProcessorFactory =
                    new ConcreteLogicalSourceProcessorFactory();
            SourceProcessor inputProcessor =
                    logicalSourceProcessorFactory.
                            createSourceProcessor(triplesMap.getLogicalSource().getSource());
            Map<String, String> parameters = null;
            InputStream input = inputProcessor.getInputStream(
                    triplesMap.getLogicalSource(), parameters);

            OutputStream output = new FileOutputStream(outputFile);
            IOUtils.copy(input,output);
            output.close();

        } catch (ParseException e) {
            log.error("Parse Exception " + e);
        } catch (IOException e) {
            log.error("IO Exception " + e);
        }


    }
}
