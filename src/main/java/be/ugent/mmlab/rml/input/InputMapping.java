package be.ugent.mmlab.rml.input;

import be.ugent.mmlab.rml.model.InputSource;
import java.util.Collection;
import java.util.HashSet;

/**
 * RML - Data Retrieval Handler : InputMapping
 *
 * @author andimou
 */
public class InputMapping {
    private Collection<InputSource> inputSources;

    /**
     *
     * @param inputSources
     */
    public InputMapping(Collection<InputSource> inputSources) {
        this.inputSources = new HashSet<InputSource>();
        this.inputSources.addAll(inputSources);
    }
    
    /**
     *
     * @return
     */
    public Collection<InputSource> getInputSources() {
        return inputSources;
    }
    
}
