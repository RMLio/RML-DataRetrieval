package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.model.Source;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RML - Data Retrieval Handler : ApiProcessor
 *
 * @author andimou
 */
public class ApiProcessor extends AbstractInputProcessor implements SourceProcessor {
    
    // Log
    private static final Logger log = LoggerFactory.getLogger(ApiProcessor.class);
    
    @Override
    public InputStream getInputStream(Source source, Map<String, String> parameters) {
        InputStream input = null;
        String sourceTemplate;

        TemplateProcessor templateProcessor = new TemplateProcessor();
        if (parameters != null) {
            sourceTemplate = templateProcessor.
                    processTemplate(source.getTemplate(), parameters);
        } else {
            sourceTemplate = source.getTemplate();
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
    
    public InputStream getInputStream(String source) {
        InputStream input = null;

        try {
            HttpURLConnection con = (HttpURLConnection) new URL(source).openConnection();
            con.setRequestMethod("HEAD");
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                input = new URL(source).openStream();
            }
        } catch (MalformedURLException ex) {
            log.error("Malformed URL Exception: " + ex);
        } catch (IOException ex) {
            log.error("Malformed URL Exception: " + ex);
        }

        return input;
    }
}
