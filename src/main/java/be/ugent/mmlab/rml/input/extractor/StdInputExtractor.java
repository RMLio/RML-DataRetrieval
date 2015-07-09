package be.ugent.mmlab.rml.input.extractor;

import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.input.std.StdInputSource;
import be.ugent.mmlab.rml.model.TriplesMap;
import be.ugent.mmlab.rml.model.std.StdTriplesMap;
import be.ugent.mmlab.rml.sesame.RMLSesameDataSet;
import be.ugent.mmlab.rml.vocabulary.R2RMLVocabulary;
import be.ugent.mmlab.rml.vocabulary.RMLVocabulary;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;

/**
 * RML - Data Retrieval Handler : StdInputExtractor
 *
 * @author andimou
 */
public class StdInputExtractor implements InputExtractor {
    
    // Log
    private static final Logger log = LoggerFactory.getLogger(StdInputExtractor.class);
    
    /**
     *
     * @param rmlMappingGraph
     * @return
     */
    public Map<Resource, InputSource> extractInputResources(RMLSesameDataSet rmlMappingGraph) {
        Map<Resource, InputSource> inputResources = new HashMap<Resource, InputSource>();
        
        List<Statement> statements = getInputResources(rmlMappingGraph);

        inputResources = putInputResources(rmlMappingGraph, statements, inputResources);

        return inputResources;
    }
    
    /**
     *
     * @param rmlMappingGraph
     * @return
     */
    protected List<Statement> getInputResources(RMLSesameDataSet rmlMappingGraph) {
        
        List<Statement> inputStatements = new ArrayList<Statement>();
        
        URI p = rmlMappingGraph.URIref(RMLVocabulary.RML_NAMESPACE
                + RMLVocabulary.RMLTerm.SOURCE);

        inputStatements = rmlMappingGraph.tuplePattern(null, p, null);
        
        return inputStatements;
    }
    
    /**
     *
     * @param rmlMappingGraph
     * @param statements
     * @param inputResources
     * @return
     */
    protected Map<Resource, InputSource> putInputResources(RMLSesameDataSet rmlMappingGraph,
            List<Statement> statements, Map<Resource, InputSource> inputResources) {
        URI p = rmlMappingGraph.URIref(RMLVocabulary.RML_NAMESPACE
                + RMLVocabulary.RMLTerm.LOGICAL_SOURCE);       
        
        for (Statement statement : statements) {
            List<Statement> triplesMapsStatements = 
                    rmlMappingGraph.tuplePattern(null, p, statement.getSubject());
            for(Statement triplesMapsStatement : triplesMapsStatements){
                try {
                    inputResources.put(
                            //triplesMap resource
                            triplesMapsStatement.getSubject(),
                            //input source
                            new StdInputSource(statement.getObject().stringValue()));
                } catch (Exception ex) {
                    log.error(StdInputExtractor.class.getName() + ex);
                }
            }
        }
        return inputResources;
    }
    
    /**
     *
     * @param rmlMappingGraph
     * @param inputResource
     * @param inputResources
     */
    public void extractInputSource(
            RMLSesameDataSet rmlMappingGraph, Resource inputResource,
            Map<Resource, InputSource> inputResources) {
        log.debug(Thread.currentThread().getStackTrace()[1].getMethodName() + ": "
                + "Extract Input Resource : "
                + inputResource.stringValue());
        
        InputSource result = inputResources.get(inputResource);

        // Extract TriplesMap properties
        Set<TriplesMap> triplesMaps =
                extractTriplesMaps(rmlMappingGraph, inputResource);
        
        // Add triples maps
        for (TriplesMap triplesMap : triplesMaps) {
            result.setTriplesMap(triplesMap);
        }      

        log.info(Thread.currentThread().getStackTrace()[1].getMethodName() + ": "
                + "Extract of Input source : "
                + inputResource.stringValue() + " done.");
    }
    
    
    /**
     *
     * @param rmlMappingGraph
     * @param inputResource
     * @return
     */
    protected Set<TriplesMap> extractTriplesMaps(
            RMLSesameDataSet rmlMappingGraph, Resource inputResource) {
        Set<TriplesMap> triplesMaps = new HashSet<TriplesMap>();
        TriplesMap triplesMap ;
        
        URI p = getTermURI(rmlMappingGraph, RMLVocabulary.RMLTerm.LOGICAL_SOURCE);
        
        List<Statement> triplesMapStatements = rmlMappingGraph.tuplePattern(null, p, inputResource);
        
        for(Statement triplesMapStatement : triplesMapStatements){
            triplesMap = new StdTriplesMap(
                    null, null, null, triplesMapStatement.getSubject().stringValue());
            triplesMaps.add(triplesMap);
        }
        
        return triplesMaps;
    }
    
    /**
     *
     * @param rmlMappingGraph
     * @param term
     * @param resource
     * @param triplesMap
     * @return
     */
    protected List<Statement> getStatements(
            RMLSesameDataSet rmlMappingGraph, Enum term,  Resource resource, TriplesMap triplesMap){
        URI p = getTermURI(rmlMappingGraph, term);

        List<Statement> statements = rmlMappingGraph.tuplePattern(resource,
                p, null);
        
        return statements;
    }
    
    /**
     *
     * @param rmlMappingGraph
     * @param term
     * @return
     */
    protected static URI getTermURI(RMLSesameDataSet rmlMappingGraph, Enum term) {
        String namespace = R2RMLVocabulary.R2RML_NAMESPACE;

        if (term instanceof RMLVocabulary.RMLTerm) {
            namespace = RMLVocabulary.RML_NAMESPACE;
        } else if ((term instanceof R2RMLVocabulary.R2RMLTerm)) 
            namespace = R2RMLVocabulary.R2RML_NAMESPACE;
        else
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + ": "
                    + term + " is not valid.");

        return rmlMappingGraph
                .URIref(namespace + term);
    }  

    @Override
    public Set<InputSource> extractInput(RMLSesameDataSet rmlMappingGraph, Resource resource) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    /*public Map<Resource, InputSource> extractInputResources(SesameDataSet dataset) {
        Map<Resource, InputSource> inputResources = new HashMap<Resource, InputSource>();
        
       

        return inputResources;
    }*/

}
