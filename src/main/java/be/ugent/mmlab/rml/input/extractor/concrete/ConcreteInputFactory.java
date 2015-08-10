package be.ugent.mmlab.rml.input.extractor.concrete;

import be.ugent.mmlab.rml.input.InputFactory;
import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.input.extractor.InputExtractor;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
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
    
    public Set<InputSource> chooseSource(Repository repository, Value value) {
        Set<InputSource> inputSources = null;
        try {
            InputExtractor input;
            RepositoryConnection connection = repository.getConnection();
            if (value.getClass().getSimpleName().equals("MemLiteral")) {
                log.debug("Literal-valued Input Source");
                //TODO: Change extractInput to Value instead of Resource
                LocalFileExtractor localFileExtractor = new LocalFileExtractor();
                inputSources = localFileExtractor.extractInput(repository, value.stringValue());
                
            } else {
                log.debug("Resource-valued Input Source");
                
                RepositoryResult<Statement> inputStatements =
                        connection.getStatements((Resource) value, RDF.TYPE, null, true);
                
                //log.debug("type " + inputStatements.next().getObject().stringValue().toString());
                
                String sourceType = inputStatements.next().getObject().stringValue().toString();
                log.debug("source type " + sourceType);

                //TODO:Change the followings not to compare with String
                switch (sourceType) {
                    case ("http://www.w3.org/ns/hydra/core#APIDocumentation"):
                        log.debug("Source described with Hydra Core vocabulary.");
                        input = new ApiExtractor();
                        inputSources = input.extractInput(repository, (Resource) value);
                        break;
                    case ("http://www.w3.org/ns/sparql-service-description#Service"):
                        log.debug("Source described with SPARQL-SD vocabulary.");
                        input = new SparqlExtractor();
                        inputSources = input.extractInput(repository, (Resource) value);
                        break;
                    case ("http://www.wiwiss.fu-berlin.de/suhl/bizer/D2RQ/0.1#Database"):
                        log.debug("Source described with D2RQ vocabulary.");
                        input = new JdbcExtractor();
                        inputSources = input.extractInput(repository, (Resource) value);
                        break;
                    default:
                        log.error("Not identified source type");
                }
                connection.close();
            }
        } catch (RepositoryException ex) {
            log.error("RepositoryException " + ex);
        }
        return inputSources;
    }
    
}
