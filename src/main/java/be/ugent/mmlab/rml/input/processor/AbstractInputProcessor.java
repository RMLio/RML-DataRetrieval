package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.model.LogicalSource;
import be.ugent.mmlab.rml.model.Source;
import be.ugent.mmlab.rml.model.TriplesMap;
import be.ugent.mmlab.rml.model.source.std.StdJdbcSource;
import java.io.InputStream;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * RML - Data Retrieval Handler : AbstractInputProcessor
 *
 * @author andimou
 */
public class AbstractInputProcessor implements SourceProcessor {
    
    // Log
    private static final Logger log = 
            LoggerFactory.getLogger(AbstractInputProcessor.class);
    
    public InputStream getInputStream(TriplesMap triplesMap) {
        
        //String source = triplesMap.getLogicalSource().getInputSource().getSource();
        
        //InputStream inputStream = getInputStream(
        //        triplesMap.getLogicalSource().getSource());
        //return inputStream;
        return null;
    }
    
    @Override
    public InputStream getInputStream(
            LogicalSource logicalSource, Map<String, String> parameters) {
        InputStream inputStream = null;
        SourceProcessor inputProcessor;
        Source source = logicalSource.getSource();
        log.debug("type of source " + source.getClass().getSimpleName());

        switch (source.getClass().getSimpleName()) {
            case ("StdLocalFileSource"):
                log.debug("Local File as Source");
                inputProcessor = new LocalFileProcessor();
                inputStream = inputProcessor.
                        getInputStream(logicalSource, parameters);
                break;
            case ("StdApiSource"):
                log.debug("API Data Source.");
                inputProcessor = new ApiProcessor();
                inputStream = inputProcessor.
                        getInputStream(logicalSource, parameters);
                break;
            case ("StdSparqlEndpointSource"):
                log.debug("SPARQL Endpoint as Data Source.");
                inputProcessor = new SparqlProcessor();
                inputStream = inputProcessor.
                        getInputStream(logicalSource, parameters);
                break;
            case ("StdStdJdbcSource"):
                log.debug("JDBC Data Source.");
                JdbcProcessor jdbcProcessor = new JdbcProcessor();
                //StdJdbcInputSource mapSource = null;
                JdbcTemplate jdcTemplate = jdbcProcessor.
                        getJdbcConnection((StdJdbcSource) source);
                break;
            default:
                log.error("Not identified input");
        }
        return inputStream;
    }
    
}
