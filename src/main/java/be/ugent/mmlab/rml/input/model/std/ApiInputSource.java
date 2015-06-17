package be.ugent.mmlab.rml.input.model.std;

import be.ugent.mmlab.rml.input.std.StdInputSource;

/**
 * RML - Data Retrieval
 *
 * @author andimou
 */
public class ApiInputSource extends StdInputSource {
    private String template;
    
    /**
     *
     * @param name
     */
    public ApiInputSource(String name){
        super(name);
    }
    
    /**
     *
     * @param name
     * @param template
     */
    public ApiInputSource(String name, String template){
        super(name, template);
        setTemplate(template);
    }
    
    private void setTemplate(String template){
        this.template = template;
    }
    
    /**
     *
     * @return
     */
    public String getTemplate(){
        return this.template ;
    }
    
}
