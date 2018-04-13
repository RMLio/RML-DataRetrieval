package be.ugent.mmlab.rml.input.std;

import be.ugent.mmlab.rml.model.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.SD;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.repository.RepositoryResult;

/**
 * RML - Data Retrieval : SPARQLsdInput
 *
 * @author andimou
 */
public class StdSparqlSdSource implements Source{
    // Log
    private static final Logger log = 
            LoggerFactory.getLogger(StdSparqlSdSource.class);
    // Value factory
    private static ValueFactory vf = SimpleValueFactory.getInstance();
    
    private Object endpoint;
    private String supportedLanguage ; 
    private String resultFormat ;
    private String sparqlQueryTemplate ;
    
    public StdSparqlSdSource(){};
    
    public StdSparqlSdSource(Repository repository, Value source){ 
        this.endpoint = extractEndpoint(repository, (Resource) source);
        this.supportedLanguage = extractSupportedLanguage(repository, (Resource) source);
        this.resultFormat = extractResultFormat(repository, (Resource) source);
        this.sparqlQueryTemplate = extractSparqlQueryTemplate(repository, (Resource) source);
    };
    
    /**
     *
     * @param endpoint
     * @param supportedLanguage
     * @param resultFormat
     * @param sparqlQueryTemplate
     */
    public StdSparqlSdSource(Object endpoint, String supportedLanguage, 
            String resultFormat, String sparqlQueryTemplate){
        this.endpoint = endpoint;
        this.supportedLanguage = supportedLanguage ;
        this.resultFormat = resultFormat ;
        this.sparqlQueryTemplate = sparqlQueryTemplate ;
    };
    
    /**
     *
     * @param statement
     */
    public void extract (Repository repository, Statement statement) {
        try {
            RepositoryConnection connection = repository.getConnection();

            RepositoryResult<Statement> source = 
                    connection.getStatements(
                    (Resource) statement.getObject(), RDF.TYPE, null, true);
            
            connection.close();

        } catch (RepositoryException ex) {
            log.error("Repository Exception " + ex);
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
     * @param source
     * @return
     */
    public Object extractEndpoint(Repository repository, Resource source) {
        RepositoryResult<Statement> statements = null;
        try {
            RepositoryConnection connection = repository.getConnection();
            IRI p = vf.createIRI("http://www.w3.org/TR/sparql-service-description#endpoint");
            statements = connection.getStatements(source, p, null, true);
            
            connection.close();
        } catch (RepositoryException ex) {
            log.error("RepositoryException " + ex);
        }
        
        return statements;
    }
    
    /**
     *
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
     * @param source
     * @return
     */
    public String extractSparqlQueryTemplate(Repository repository, Resource source) {
        RepositoryResult<Statement> statements;
        String stringStatement = null;
        try {
            RepositoryConnection connection = repository.getConnection();
            
            IRI p = vf.createIRI(SD.NAMESPACE + "sparqlQueryTemplate");
            statements = connection.getStatements(source, p, source, true);

            stringStatement = statements.next().getObject().stringValue();
            connection.close();
        } catch (RepositoryException ex) {
            log.error("RepositoryException " + ex);
        }
        return stringStatement;
    }

    @Override
    public void setTemplate(String template) {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getTemplate() {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }
}
