package be.ugent.mmlab.rml.input.extractor.concrete;

import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.input.extractor.AbstractSourceExtractor;
import be.ugent.mmlab.rml.input.std.StdJdbcInputSource;
import be.ugent.mmlab.rml.vocabulary.D2RQVocabulary;
import be.ugent.mmlab.rml.vocabulary.D2RQVocabulary.D2RQTerm;
import java.util.HashSet;
import java.util.Set;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.ValueFactory;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RML - Data Retrieval Handler : DbExtractor
 *
 * @author andimou
 */
public class JdbcExtractor extends AbstractSourceExtractor {

    // Log
    private static final Logger log = LoggerFactory.getLogger(ApiExtractor.class);

    @Override
    public Set<String> extractStringTemplate(String source) {

        return null;
    }
        
    
    @Override
    public Set<InputSource> extractSource(Repository repository, Value value) {
        Set<InputSource> inputSources = new HashSet<InputSource>();

        String jdbcDSN = extractJdbcTerm(repository, value, D2RQTerm.JDBCDSN);
        String jdbcDriver = extractJdbcTerm(repository, value, D2RQTerm.JDBCDRIVER);
        String username = extractJdbcTerm(repository, value, D2RQTerm.USERNAME);
        String password = extractJdbcTerm(repository, value, D2RQTerm.PASSWORD);
        InputSource source = new StdJdbcInputSource(jdbcDSN, jdbcDriver, username, password);
        inputSources.add(source);

        return inputSources;
    }
    
    private String extractJdbcTerm(Repository repository, Value resource, D2RQTerm term){
        String value = null;
        
        try {
            RepositoryConnection connection = repository.getConnection();
            ValueFactory vf = connection.getValueFactory();
            
            URI predicate = vf.createURI(
                        D2RQVocabulary.D2RQ_NAMESPACE + term);
            
            RepositoryResult<Statement> statements =
                    connection.getStatements((Resource) resource, predicate, null, true);
            
            while (statements.hasNext()) {
                value = statements.next().getObject().stringValue();
            }

            connection.close();
            
        } catch (RepositoryException ex) {
            log.error("Repository Exception " + ex);
        }
        return value;
    }
    
}
