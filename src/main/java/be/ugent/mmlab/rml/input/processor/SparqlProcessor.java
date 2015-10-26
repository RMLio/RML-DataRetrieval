package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.model.LogicalSource;
import be.ugent.mmlab.rml.model.Source;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RML - Data Retrieval Handler : SparqlProcessor
 *
 * @author andimou
 */
public class SparqlProcessor extends AbstractInputProcessor {
    // Log
    private static final Logger log = 
            LoggerFactory.getLogger(SparqlProcessor.class);
    
    //TODO:change or move function to AbstractInputProcessor
    
    public InputStream getInputStream(String source) {
        InputStream input = null;

        try {
            HttpURLConnection con = 
                    (HttpURLConnection) new URL(source).openConnection();
            con.setRequestMethod("HEAD");
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                input = new URL(source).openStream();
            }
        } catch (MalformedURLException ex) {
            log.error("Malformed URL Exception: " + ex);
        } catch (IOException ex) {
            log.error("IO Exception: " + ex);
        }

        return input;
    }
    
    @Override
    public InputStream getInputStream(
            LogicalSource logicalSource, Map<String, String> parameters) {
        InputStream input = null;
        String sourceTemplate;
        
        Source source = logicalSource.getSource();
        String query = logicalSource.getQuery();
        TemplateProcessor templateProcessor = new TemplateProcessor();
        
        if (parameters != null) {
            sourceTemplate = templateProcessor.
                    processUriTemplate(source.getTemplate(), parameters);
            query = templateProcessor.
                    processUriTemplate(query, parameters);
        } else {
            sourceTemplate = source.getTemplate();
        }

        //TODO: Change format to be based on the reference formulation
        try {
            query = URLEncoder.encode(query.trim(), "UTF-8");
            sourceTemplate = sourceTemplate 
                    + "?query=" + query 
                    + "&format=application%2Fsparql-results%2Bjson";
        } catch (UnsupportedEncodingException ex) {
            log.error("Unsupported Encoding Exception " + ex);
        }

        //TODO: Change the following with Spring
        try {
            URL url = new URL(sourceTemplate);
            input = url.openStream();

        } catch (MalformedURLException ex) {
            log.error("Malformed URL Exception: " + ex);
        } catch (IOException ex) {
            log.error("IO Exception : " + ex);
        }
        return input;
    }
    
    @Override
    public boolean hasNextInputStream(){
        return false;
    }
}
