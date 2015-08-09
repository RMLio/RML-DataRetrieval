package be.ugent.mmlab.rml.input.extractor.concrete;

import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.input.extractor.AbstractInputExtractor;
import be.ugent.mmlab.rml.input.model.std.LocalFileSource;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openrdf.model.Resource;
import org.openrdf.repository.Repository;

/**
 * RML - Data Retrieval Handler : LocalFileExtractor
 *
 * @author andimou
 */
public class LocalFileExtractor extends AbstractInputExtractor {
    
    // Log
    private static final Logger log = LoggerFactory.getLogger(LocalFileExtractor.class);
    
    //TODO: Change extractInput to Value instead of Resource
    public Set<InputSource> extractInput(Repository repository, String source) {
        Set<InputSource> inputSources = new HashSet<InputSource>();
        
        inputSources.add(new LocalFileSource(source,source));
        
        return inputSources;
    }

    @Override
    public Set<InputSource> extractInput(Repository repository, Resource resource) {
        return null;
    }
     
}
