package be.ugent.mmlab.rml.input.model;

import be.ugent.mmlab.rml.model.TriplesMap;

/**
 * RML - Data Retrieval
 *
 * @author andimou
 */
public interface InputSource {
    
    /**
     *
     * @param triplesMap
     */
    public void addTriplesMap(TriplesMap triplesMap);
    
    /**
     *
     * @return
     */
    public String getSource();
    
}
