package be.ugent.mmlab.rml.input.extractor;

import be.ugent.mmlab.rml.model.InputSource;
import java.util.Set;
import org.openrdf.model.Value;
import org.openrdf.repository.Repository;

/**
 * RML - Data Retrieval Handler : InputExtractor
 *
 * @author andimou
 */
public interface SourceExtractor {

    /**
     *
     * @return
     */
    public Set<InputSource> extractSource(Repository repository, Value resource);
}
