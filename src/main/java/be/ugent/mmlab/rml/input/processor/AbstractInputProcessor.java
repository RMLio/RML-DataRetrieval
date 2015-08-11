package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.input.std.StdJdbcInputSource;
import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.model.TriplesMap;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * RML - Data Retrieval Handler : AbstractInputProcessor
 *
 * @author andimou
 */
public class AbstractInputProcessor implements InputProcessor {
    
    // Log
    private static final Logger log = LoggerFactory.getLogger(AbstractInputProcessor.class);
    
    public InputStream getInputStream(TriplesMap triplesMap) {
        
        //String source = triplesMap.getLogicalSource().getInputSource().getSource();
        
        InputStream inputStream = getInputStream(
                triplesMap.getLogicalSource().getSource());
        return inputStream;
    }
    
    /*@Override
    public InputStream getInputStream(TriplesMap triplesMap, String source) {
        InputStream inputStream = null;
        InputProcessor inputProcessor;
        
        switch (triplesMap.getLogicalSource().getInputSource().getClass().getSimpleName()) {
            case ("LocalFileSource"):
                log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " 
                        + "Local File");
                inputProcessor = new LocalFileProcessor();
                inputStream = inputProcessor.getInputStream(source);
                break;
            case ("ApiInputSource"):
                inputProcessor = new ApiProcessor();
                inputStream = inputProcessor.getInputStream(source);
                break;
            case ("SparqlSdInputSource"):
                inputProcessor = new SparqlProcessor();
                inputStream = inputProcessor.getInputStream(source);
                break;
            case ("JdbcInputSource"):
                JdbcProcessor jdbcProcessor = new JdbcProcessor();
                StdJdbcInputSource mapSource = null;
                JdbcTemplate jdcTemplate = jdbcProcessor.getJdbcConnection(mapSource);
                //inputStream = inputProcessor.getInputStream(triplesMap, source);
                break;
            default:
                log.error("Not identified input");
        }
        return inputStream;
    }*/
    
    @Override
    public InputStream getInputStream(InputSource source) {
        InputStream inputStream = null;
        InputProcessor inputProcessor;
        //String source = inputSource.getSource();
        
        //switch (triplesMap.getLogicalSource().getInputSource().getClass().getSimpleName()) {
        switch (source.getClass().getSimpleName()) {
            case ("LocalFileSource"):
                log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " 
                        + "Local File");
                inputProcessor = new LocalFileProcessor();
                inputStream = inputProcessor.getInputStream(source);
                break;
            case ("ApiInputSource"):
                log.debug("API Input Source.");
                inputProcessor = new ApiProcessor();
                inputStream = inputProcessor.getInputStream(source);
                break;
            case ("SparqlSdInputSource"):
                log.debug("SPARQL-SD Input Source.");
                inputProcessor = new SparqlProcessor();
                inputStream = inputProcessor.getInputStream(source);
                break;
            case ("StdJdbcInputSource"):
                log.debug("JDBC Input Source.");
                JdbcProcessor jdbcProcessor = new JdbcProcessor();
                //StdJdbcInputSource mapSource = null;
                JdbcTemplate jdcTemplate = jdbcProcessor.
                        getJdbcConnection((StdJdbcInputSource) source);
                break;
            default:
                log.error("Not identified input");
        }
        return inputStream;
    }
    
}
