package be.ugent.mmlab.rml.input.extractor.concrete;

import be.ugent.mmlab.rml.input.InputFactory;
import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.input.extractor.InputExtractor;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;

/**
 * RML - Data Retrieval Handler : ConcreteInputFactory
 *
 * @author andimou
 */
public class ConcreteInputFactory implements InputFactory {
    
    // Log
    private static final Logger log = LoggerFactory.getLogger(ConcreteInputFactory.class);
    
    public Set<InputSource> chooseInput(Repository repository, Resource resource){
        Set<InputSource> inputSources = null;
        try {
            InputExtractor input ;
            RepositoryConnection connection = repository.getConnection();
            RepositoryResult<Statement> inputStatements = 
                    connection.getStatements(resource, RDF.TYPE, null, true);
            
            switch(inputStatements.next().getObject().stringValue().toString()){
                case ("http://www.w3.org/ns/hydra/core#APIDocumentation"):
                    input = new ApiExtractor();
                    inputSources = input.extractInput(repository, resource);
                    break;
                case ("http://www.w3.org/ns/sparql-service-description#Service"):
                    input = new SparqlExtractor();
                    inputSources = input.extractInput(repository, resource);
                    break;
                case("http://www.wiwiss.fu-berlin.de/suhl/bizer/D2RQ/0.1#Database"):
                    input = new JdbcExtractor();
                    inputSources = input.extractInput(repository, resource);
                    break;
                default:
                    log.error("Not identified input");
            connection.close();
            } 
        } catch (RepositoryException ex) {
            log.error("RepositoryException " + ex);
        }
        return inputSources;
    }
    
}
