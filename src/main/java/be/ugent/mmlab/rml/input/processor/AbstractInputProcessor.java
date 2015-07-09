package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.model.TriplesMap;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * RML - Data Retrieval Handler : AbstractInputProcessor
 *
 * @author andimou
 */
public class AbstractInputProcessor implements InputProcessor {
    
    // Log
    private static final Logger log = LoggerFactory.getLogger(AbstractInputProcessor.class);
    
    @Override
    public InputStream getInputStream(TriplesMap triplesMap, String source) {
        InputStream inputStream = null;
        InputProcessor inputProcessor;
        
        switch (triplesMap.getLogicalSource().getInputSource().getClass().getSimpleName()) {
            case ("LocalFileSource"):
                log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " 
                        + "Local File");
                inputProcessor = new LocalFileProcessor();
                inputStream = inputProcessor.getInputStream(triplesMap, source);
                break;
            case ("ApiInputSource"):
                inputProcessor = new ApiProcessor();
                inputStream = inputProcessor.getInputStream(triplesMap, source);
                break;
            case ("SparqlSdInputSource"):
                inputProcessor = new SparqlProcessor();
                inputStream = inputProcessor.getInputStream(triplesMap, source);
                break;
            case ("DbInputSource"):
                inputProcessor = new DbProcessor();
                inputStream = inputProcessor.getInputStream(triplesMap, source);
                break;
            default:
                log.error("Not identified input");
        }
        return inputStream;
    }
    
}
