package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.model.Source;
import java.io.InputStream;

/**
 * RML - Data Retrieval Handler : InputProcessor
 *
 * @author andimou
 */
public interface SourceProcessor {
    /**
     *
     * @param triplesMap
     * @param source
     * @param triplesMap
     * @return
     */
    public InputStream getInputStream(Source source);

}
