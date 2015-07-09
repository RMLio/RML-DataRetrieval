package be.ugent.mmlab.rml.input.extractor.concrete;

import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.input.extractor.AbstractInputExtractor;
import be.ugent.mmlab.rml.input.model.std.LocalFileSource;
import be.ugent.mmlab.rml.sesame.RMLSesameDataSet;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openrdf.model.Resource;

/**
 * RML - Data Retrieval Handler : LocalFileExtractor
 *
 * @author andimou
 */
public class LocalFileExtractor extends AbstractInputExtractor {
    
    // Log
    private static final Logger log = LoggerFactory.getLogger(LocalFileExtractor.class);
    
    public Set<InputSource> extractInput(RMLSesameDataSet rmlMappingGraph, String source) {
        Set<InputSource> inputSources = new HashSet<InputSource>();
        
        inputSources.add(new LocalFileSource(source,source));
        
        return inputSources;
    }

    @Override
    public Set<InputSource> extractInput(RMLSesameDataSet rmlMappingGraph, Resource resource) {
        return null;
    }
     
}
