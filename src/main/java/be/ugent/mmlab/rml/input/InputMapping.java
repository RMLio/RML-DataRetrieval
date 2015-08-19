package be.ugent.mmlab.rml.input;

import be.ugent.mmlab.rml.model.Source;
import java.util.Collection;
import java.util.HashSet;

/**
 * RML - Data Retrieval Handler : InputMapping
 *
 * @author andimou
 */
public class InputMapping {
    private Collection<Source> inputSources;

    /**
     *
     * @param inputSources
     */
    public InputMapping(Collection<Source> inputSources) {
        this.inputSources = new HashSet<Source>();
        this.inputSources.addAll(inputSources);
    }
    
    /**
     *
     * @return
     */
    public Collection<Source> getInputSources() {
        return inputSources;
    }
    
}
