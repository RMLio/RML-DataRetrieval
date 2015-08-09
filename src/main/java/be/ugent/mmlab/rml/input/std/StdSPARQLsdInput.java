package be.ugent.mmlab.rml.input.std;

import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.model.TriplesMap;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.SD;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;

/**
 * RML - Data Retrieval : SPARQLsdInput
 *
 * @author andimou
 */
public class StdSPARQLsdInput implements InputSource{
    // Log
    private static final Logger log = LoggerFactory.getLogger(StdSPARQLsdInput.class);
    // Value factory
    private static ValueFactory vf = new ValueFactoryImpl();
    
    private Object endpoint;
    private String supportedLanguage ; 
    private String resultFormat ;
    private String sparqlQueryTemplate ;
    
    public StdSPARQLsdInput(){};
    
    public StdSPARQLsdInput(Repository repository, Value source){ 
        Object endpoint = extractEndpoint(repository, (Resource) source);
        if(endpoint != null)
            this.endpoint = endpoint;
        
        String value = extractSupportedLanguage(repository, (Resource) source);
        if(value != null)
            this.supportedLanguage = value;
        
        value = extractResultFormat(repository, (Resource) source);
        if(value != null)
            this.resultFormat = value;
        
        value = extractSparqlQueryTemplate(repository, (Resource) source);
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
    public StdSPARQLsdInput(Object endpoint, String supportedLanguage, String resultFormat, String sparqlQueryTemplate){
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
    public void extract (Repository repository, Statement statement) {
        try {
            RepositoryConnection connection = repository.getConnection();

            //vf.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "type")
            RepositoryResult<Statement> source = 
                    connection.getStatements((Resource) statement.getObject(), RDF.TYPE, null, true);
            
            connection.close();

        } catch (RepositoryException ex) {
            log.error("RepositoryException " + ex);
        }
        
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
    public Object extractEndpoint(Repository repository, Resource source) {
        RepositoryResult<Statement> statements = null;
        try {
            RepositoryConnection connection = repository.getConnection();
            URI p = vf.createURI("http://www.w3.org/TR/sparql-service-description#endpoint");
            statements = connection.getStatements(source, p, null, true);
            
            connection.close();
        } catch (RepositoryException ex) {
            log.error("RepositoryException " + ex);
        }
        
        return statements;
    }
    
    /**
     *
     * @param rmlMappingGraph
     * @param source
     * @return
     */
    public String extractSupportedLanguage(Repository repository, Resource source) {
        String statement = null;
        try {
            RepositoryConnection connection = repository.getConnection();
            RepositoryResult<Statement> statements = 
                    connection.getStatements(source, SD.SUPPORTED_LANGUAGE, null, true);
            statement = statements.next().getObject().stringValue();
            connection.close();
        } catch (RepositoryException ex) {
            log.error("RepositoryException " + ex);
        }
        return statement;
    }
    
    /**
     *
     * @param rmlMappingGraph
     * @param source
     * @return
     */
    public String extractResultFormat(Repository repository, Resource source) {
        String statement = null ;
        try {
            RepositoryConnection connection = repository.getConnection();
            RepositoryResult<Statement> statements = 
                    connection.getStatements(source, SD.RESULT_FORMAT, null, true);
            statement = statements.next().getObject().stringValue();
            connection.close();
        } catch (RepositoryException ex) {
            log.error("RepositoryException " + ex);
        }
        return statement;
    }
    
    /**
     *
     * @param rmlMappingGraph
     * @param source
     * @return
     */
    public String extractSparqlQueryTemplate(Repository repository, Resource source) {
        RepositoryResult<Statement> statements;
        String stringStatement = null;
        try {
            RepositoryConnection connection = repository.getConnection();
            
            URI p = vf.createURI(SD.NAMESPACE + "sparqlQueryTemplate");
            statements = connection.getStatements(source, p, source, true);

            stringStatement = statements.next().getObject().stringValue();
            connection.close();
        } catch (RepositoryException ex) {
            log.error("RepositoryException " + ex);
        }
        return stringStatement;
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
