package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.model.LogicalSource;
import be.ugent.mmlab.rml.model.Source;
import java.io.FileInputStream;
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
    private static final Logger log = 
            LoggerFactory.getLogger(ApiProcessor.class);
    private String nextPage = null;
    
    private String getInputSource(
            LogicalSource logicalSource, Map<String, String> parameters) {
        String sourceTemplate;
        Source source ;
        
        if (nextPage == null) {
            source = logicalSource.getSource();

            TemplateProcessor templateProcessor = new TemplateProcessor();
            if (parameters != null) {
                sourceTemplate = templateProcessor.
                        processUriTemplate(source.getTemplate(), parameters);
            } else {
                sourceTemplate = source.getTemplate();
            }
        } else {
            sourceTemplate = nextPage;
        }
        return sourceTemplate;
    }
    
    @Override
    public InputStream getInputStream(
            LogicalSource logicalSource, Map<String, String> parameters){
        InputStream input = null;
        boolean flag = false;
        
        log.debug("Getting Input source..");
        String sourceTemplate = getInputSource(logicalSource, parameters);
        log.info("Mapping " + sourceTemplate + " input source.");
        HttpURLConnection con;

        //TODO: Spring it
        try {
            if (sourceTemplate.startsWith("/")) {
                ///upnormal but mainly to catch CSVW descriptions of local files
                input = new FileInputStream(sourceTemplate);
                return input;
            } else {
                con = (HttpURLConnection) new URL(sourceTemplate).openConnection();
                con.setRequestMethod("GET");
                if (logicalSource.getReferenceFormulation() != null) {
                    switch (logicalSource.getReferenceFormulation().toString()) {
                        case "JSONPath":
                            con.addRequestProperty("Accept", "application/json");
                            con.setRequestProperty("Content-Type", "application/json");
                            break;
                        case "XPath":
                            con.addRequestProperty("Accept", "application/xml");
                            con.setRequestProperty("Content-Type", "application/xml");
                            break;
                    }
                }

                String rel = con.getHeaderField("Link");
                if (rel != null) {
                    String[] smths = rel.split(",");

                    for (String smth : smths) {
                        if (smth.contains("http://www.w3.org/ns/hydra/core#nextPage")) {
                            flag = true;
                            String[] nextPages = smth.split(";");
                            for (String np : nextPages) {
                                if (!np.contains("rel=")) {
                                    nextPage = np;
                                }
                            }
                        }
                    }

                    if (flag == false) {
                        nextPage = null;
                    }
                }

                if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    input = con.getInputStream();
                } else {
                    log.debug("Response Code " + con.getResponseCode());
                }
            }

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
    public boolean hasNextInputStream(){
        if(nextPage != null)
            return true;
        else
            return false;
    }
}
