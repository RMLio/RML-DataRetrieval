package be.ugent.mmlab.rml.input.extractor.concrete;

import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.input.extractor.AbstractInputExtractor;
import java.util.Set;
import org.openrdf.model.Resource;
import org.openrdf.repository.Repository;

/**
 * RML - Data Retrieval Handler : DbExtractor
 *
 * @author andimou
 */
public class DbExtractor extends AbstractInputExtractor {

        @Override
        public Set<String> extractStringTemplate(String source) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

        @Override
        public Set<InputSource> extractInput(Repository repository, Resource resource) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
