package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.model.TriplesMap;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RML - Data Retrieval Handler : ApiProcessor
 *
 * @author andimou
 */
public class ApiProcessor extends AbstractInputProcessor {
    
    // Log
    private static final Logger log = LoggerFactory.getLogger(ApiProcessor.class);
    
    @Override
    public InputStream getInputStream(TriplesMap triplesMap, String source) {
        InputStream input = null;

        try {
            HttpURLConnection con = (HttpURLConnection) new URL(source).openConnection();
            con.setRequestMethod("HEAD");
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                input = new URL(source).openStream();
            }
        } catch (MalformedURLException ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex);
        } catch (IOException ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex);
        }

        return input;
    }
}
