package be.ugent.mmlab.rml.input.main;

import be.ugent.mmlab.rml.input.InputFactory;
import be.ugent.mmlab.rml.input.config.DataRetrievalHandlerConfiguration;
import be.ugent.mmlab.rml.model.RMLMapping;
import be.ugent.mmlab.rml.model.TriplesMap;
import be.ugent.mmlab.rml.sesame.RMLSesameDataSet;
import be.ugent.mmlab.rml.vocabulary.Vocab;
import java.util.Map;
import java.util.logging.Level;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openrdf.model.Resource;
import org.openrdf.rio.RDFFormat;

/**
 * RML Data Retrieval Handler : Main
 *
 * @author andimou
 */
public class Main {
    
    // Log
    Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        String map_doc;
        CommandLine commandLine ;
        
        try {
            commandLine = DataRetrievalHandlerConfiguration.parseArguments(args);

            if (commandLine.hasOption("m")) {
                map_doc = commandLine.getOptionValue("m", null);
            }
            
            InputFactory inputFactory ;

        } catch (ParseException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("RML Data Retrieval Handler");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("");

        System.out.println("RML Processor - Extracting Mapping Document.");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("");
    }
    
    
}
