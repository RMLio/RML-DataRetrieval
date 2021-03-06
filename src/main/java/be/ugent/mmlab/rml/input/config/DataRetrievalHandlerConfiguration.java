package be.ugent.mmlab.rml.input.config;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RML Data Retrieval Handler : DataRetrievalHandlerConfiguration
 *
 * @author andimou
 */
public class DataRetrievalHandlerConfiguration {
    
    // Log
    private static final Logger log =
            LoggerFactory.getLogger(DataRetrievalHandlerConfiguration.class);
    private static final Options cliOptions = generateCLIOptions();
    
    public static CommandLine parseArguments(String[] args) throws ParseException {
        
        CommandLineParser cliParser = new GnuParser();
        return cliParser.parse(getCliOptions(), args);
    }

    private static Options generateCLIOptions() {
        Options cliOptions = new Options();
        
        cliOptions.addOption("h", 
                "help", false, "show this help message");
        cliOptions.addOption("m", 
                "mapping document", true, "the URI of the mapping file (required)");
        cliOptions.addOption("tm", 
                "Triples Map", true, "Triples Map to be processed.");
        cliOptions.addOption("f",
                "format", false, "gives the output format.");
        cliOptions.addOption("p", 
                "arguments to pass if the rml:source of the mapping document is a URI template "
                + "and requires parameters (they should be comma separated)", true, 
                "arguments for the source template");
        return cliOptions;
    }
    
    public static Options getCliOptions() {
        return cliOptions;
    }
    
    public static void displayHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("RML Data Retrieval Handler", getCliOptions());
        System.exit(1);
    }

}
