package be.ugent.mmlab.rml.input.model.std;

import be.ugent.mmlab.rml.input.std.StdInputSource;

/**
 * RML - Data Retrieval
 *
 * @author andimou
 */
public class LocalFileSource extends StdInputSource  {
    
    /**
     *
     * @param name
     */
    public LocalFileSource(String name){
        super(name);
    }
    
    /**
     *
     * @param name
     * @param source
     */
    public LocalFileSource(String name, String source){
        super(name, source);
    }
}
