package be.ugent.mmlab.rml.input.extractor.concrete;

import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.input.extractor.AbstractInputExtractor;
import be.ugent.mmlab.rml.input.model.std.ApiInputSource;
import be.ugent.mmlab.rml.vocabulary.HydraVocabulary;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;

/**
 * RML - Data Retrieval Handler : ApiExtractor
 *
 * @author andimou
 */
public class ApiExtractor extends AbstractInputExtractor {
    
    // Log
    private static final Logger log = LoggerFactory.getLogger(ApiExtractor.class);
    // Value factory
    private static ValueFactory vf = new ValueFactoryImpl();


    @Override
    public Set<InputSource> extractInput(Repository repository, Resource resource) {
        Set<InputSource> inputSources = new HashSet<InputSource>();
        try {
            RepositoryConnection connection = repository.getConnection();
            ValueFactory vf = connection.getValueFactory();
            URI predicate = vf.createURI(
                    HydraVocabulary.HYDRA_NAMESPACE + HydraVocabulary.HydraTerm.TEMPLATE);
            RepositoryResult<Statement> statements =
                    connection.getStatements(resource, predicate, resource, true);

            while (statements.hasNext()) {
                inputSources.add(
                        new ApiInputSource(
                        resource.stringValue(), statements.next().getObject().stringValue()));
            }
            connection.close();
        } catch (RepositoryException ex) {
            log.error("RepositoryException " + ex);
        }
        return inputSources;
    }
    
}
