package be.ugent.mmlab.rml.input.std;

import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.model.TriplesMap;
import be.ugent.mmlab.rml.sesame.RMLSesameDataSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.SD;

/**
 * RML - Data Retrieval : SPARQLsdInput
 *
 * @author andimou
 */
public class SPARQLsdInput implements InputSource{
    // Log
    private static final Logger log = LogManager.getLogger(SPARQLsdInput.class);
    // Value factory
    private static ValueFactory vf = new ValueFactoryImpl();
    
    private Object endpoint;
    private String supportedLanguage ; 
    private String resultFormat ;
    private String sparqlQueryTemplate ;
    
    public SPARQLsdInput(){};
    
    public SPARQLsdInput(RMLSesameDataSet rmlMappingGraph, Value source){ 
        Object endpoint = extractEndpoint(rmlMappingGraph, (Resource) source);
        if(endpoint != null)
            this.endpoint = endpoint;
        
        String value = extractSupportedLanguage(rmlMappingGraph, (Resource) source);
        if(value != null)
            this.supportedLanguage = value;
        
        value = extractResultFormat(rmlMappingGraph, (Resource) source);
        if(value != null)
            this.resultFormat = value;
        
        value = extractSparqlQueryTemplate(rmlMappingGraph, (Resource) source);
        if(value != null)
            this.supportedLanguage = value;
    };
    
    /**
     *
     * @param endpoint
     * @param supportedLanguage
     * @param resultFormat
     * @param sparqlQueryTemplate
     */
    public SPARQLsdInput(Object endpoint, String supportedLanguage, String resultFormat, String sparqlQueryTemplate){
        this.endpoint = endpoint;
        this.supportedLanguage = supportedLanguage ;
        this.resultFormat = resultFormat ;
        this.sparqlQueryTemplate = sparqlQueryTemplate ;
    };
    
    /**
     *
     * @param rmlMappingGraph
     * @param statement
     */
    public void extract (RMLSesameDataSet rmlMappingGraph, Statement statement) {
        
        List<Statement> source = rmlMappingGraph.tuplePattern(
                    (Resource) statement.getObject(), vf.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "type"), null);
        
    }
    
    /**
     *
     * @return
     */
    public Object getEndpoint() {
        return this.endpoint;
    }
    
    /**
     *
     * @return
     */
    public String getsupportedLanguage() {
        return this.supportedLanguage;
    }
    
    /**
     *
     * @return
     */
    public String getResultFormat() {
        return this.resultFormat;
    }
    
    /**
     *
     * @return
     */
    public String getSparqlQueryTemplate() {
        return this.sparqlQueryTemplate;
    }
    
    /**
     *
     * @param rmlMappingGraph
     * @param source
     * @return
     */
    public Object extractEndpoint(RMLSesameDataSet rmlMappingGraph, Resource source) {
        List<Statement> statements = rmlMappingGraph.tuplePattern(
                (Resource) source, vf.createURI("http://www.w3.org/TR/sparql-service-description#endpoint"), null);
        return statements;
    }
    
    /**
     *
     * @param rmlMappingGraph
     * @param source
     * @return
     */
    public String extractSupportedLanguage(RMLSesameDataSet rmlMappingGraph, Resource source) {
        List<Statement> statements = rmlMappingGraph.tuplePattern(source, SD.SUPPORTED_LANGUAGE, null);
        return statements.get(0).getObject().stringValue();
    }
    
    /**
     *
     * @param rmlMappingGraph
     * @param source
     * @return
     */
    public String extractResultFormat(RMLSesameDataSet rmlMappingGraph, Resource source) {
        List<Statement> statements = rmlMappingGraph.tuplePattern(source, SD.RESULT_FORMAT, null);
        return statements.get(0).getObject().stringValue();
    }
    
    /**
     *
     * @param rmlMappingGraph
     * @param source
     * @return
     */
    public String extractSparqlQueryTemplate(RMLSesameDataSet rmlMappingGraph, Resource source) {
        List<Statement> statements = rmlMappingGraph.tuplePattern(source, vf.createURI(SD.NAMESPACE + "sparqlQueryTemplate"), null);
        return statements.get(0).getObject().stringValue();
    }

    @Override
    public void setTriplesMap(TriplesMap triplesMap) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public Set<TriplesMap> getTriplesMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSource() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSource(String source) {
        
    }

}
