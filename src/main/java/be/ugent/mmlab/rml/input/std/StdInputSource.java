package be.ugent.mmlab.rml.input.std;

import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.model.TriplesMap;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RML - Data Retrieval : StdInputSource
 *
 * @author andimou
 */
public class StdInputSource implements InputSource{
    
    // Log
    private static final Logger log = LoggerFactory.getLogger(StdInputSource.class);
    
    private String name;
    private String source;
    private Set<TriplesMap> triplesMaps;

    /**
     *
     * @param name
     */
    public StdInputSource(String name) {
        setName(name);
    }

    /**
     *
     * @param name
     * @param source
     */
    public StdInputSource(String name, String source) {
        setSource(source);
        setName(name);
    }

    /**
     *
     * @param name
     */
    private void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }
    
    /**
     *
     * @return
     */
    public String getName(){
        return this.name;
    }
    
    @Override
    public void setSource(String source) {
        if (source != null) {
            this.source = source;
        }
    }
    
    @Override
    public String getSource(){
        return this.source;
    }

    /**
     *
     * @param triplesMap
     */
    @Override
    public void setTriplesMap(TriplesMap triplesMap) {
        if (triplesMap != null) {
            triplesMaps.add(triplesMap);
        }
    }

    public Set<TriplesMap> getTriplesMap() {
        return this.triplesMaps;
    }

}
