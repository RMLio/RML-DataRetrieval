package be.ugent.mmlab.rml.input;

import be.ugent.mmlab.rml.mapdochandler.extraction.concrete.LogicalSourceExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openrdf.model.Value;
import org.openrdf.repository.Repository;

/**
 * RML - Data Retrieval Handler : ConcreteLogicalSourceFactory
 *
 * @author andimou
 */
public class ConcreteLogicalSourceFactory implements LogicalSourceFactory {

    // Log
    private static final Logger log = 
            LoggerFactory.getLogger(ConcreteLogicalSourceFactory.class);

    public LogicalSourceExtractor createLogicalSourceExtractor(
            Repository repository, Value value) {
        LogicalSourceExtractor logicalSourceExtractor = 
                new LogicalSourceExtractor();

        return logicalSourceExtractor;
    }
}
