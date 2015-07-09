package be.ugent.mmlab.rml.input.extractor.concrete;

import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.input.extractor.AbstractInputExtractor;
import be.ugent.mmlab.rml.sesame.RMLSesameDataSet;
import java.util.Set;
import org.openrdf.model.Resource;

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
        public Set<InputSource> extractInput(RMLSesameDataSet rmlMappingGraph, Resource resource) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
