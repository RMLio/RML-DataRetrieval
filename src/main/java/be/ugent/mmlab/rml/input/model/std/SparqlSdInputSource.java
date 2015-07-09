package be.ugent.mmlab.rml.input.model.std;

import be.ugent.mmlab.rml.input.std.StdInputSource;

/**
 * RML - Data Retrieval Handler : SparqlSdInputSource
 *
 * @author andimou
 */
public class SparqlSdInputSource extends StdInputSource {
    
    private String template;
    
    /**
     *
     * @param name
     * @param template
     */
    public SparqlSdInputSource(String name, String template){
        super(name);
        setTemplate(template);
    }
    
    private void setTemplate(String template){
        this.template = template;
    }
    
    /**
     *
     * @return
     */
    public String setTemplate(){
        return this.template ;
    }
}
