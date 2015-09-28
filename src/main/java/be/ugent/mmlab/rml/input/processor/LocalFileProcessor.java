package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.model.LogicalSource;
import be.ugent.mmlab.rml.model.Source;
import be.ugent.mmlab.rml.model.TriplesMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RML - Data Retrieval Handler : LocalFileProcessor
 *
 * @author andimou
 */
public class LocalFileProcessor extends AbstractInputProcessor {
    
    // Log
    private static final Logger log = 
            LoggerFactory.getLogger(LocalFileProcessor.class);
    
    public InputStream getInputStream(String source) {
        InputStream input = null;
        try {
            File file = new File(new File(source).getAbsolutePath());

            if (!file.exists()) {
                file = new File(new File(source).getCanonicalPath());

                if (!file.exists()) {
                    log.error("Input file not found. ");
                }
            }
            input = new FileInputStream(file);
        } catch (IOException ex) {
            log.error("IO Exception: " + ex);
        }
        return input;
    }
    
    @Override
    public InputStream getInputStream(
            LogicalSource logicalSource, Map<String, String> parameters) {
        InputStream input = null;
        Source source = logicalSource.getSource();
        String template ;
        try {
            if (parameters != null) {
                TemplateProcessor templateProcessor = new TemplateProcessor();
                template = templateProcessor.
                        processTemplate(source.getTemplate(), parameters);
            } else {
                template = source.getTemplate();
            }
            
            File file = new File(new File(template).getAbsolutePath());

            if (!file.exists()) {
                file = new File(new File(template).getCanonicalPath());

                if (!file.exists()) {
                    log.error("Input file not found. ");
                }
            }
            input = new FileInputStream(file);
        } catch (IOException ex) {
            log.error("IO Exception: " + ex);
        }
        return input;
    }
     
     
     /**
     *
     * @param source
     * @param triplesMap
     * @return
     */
    public String getInputSource(String source, TriplesMap triplesMap) {
        
        //string input source
        if (isLocalFile(source)) {
            try {
                File file = new File(new File(source).getAbsolutePath());

                if (!file.exists()) {
                    if (LocalFileProcessor.class.
                            getResource(triplesMap.getLogicalSource().getSource().getTemplate()) == null) {
                        source = triplesMap.getLogicalSource().getSource().getTemplate();
                        file = new File(new File(source).getAbsolutePath());
                    } else {
                        source = LocalFileProcessor.class.
                                getResource(triplesMap.getLogicalSource().getSource().getTemplate()).getFile();
                        file = new File(new File(source).getCanonicalPath());
                    }
                    if (!file.exists()) {
                        log.error("Input file not found. ");
                    }
                }
                source = file.toString();
                //input = new FileInputStream(file);
            } catch (IOException ex) {
                log.error("IO Exception: " + ex);
            }
        } else {
            log.error("Input stream was not possible.");
            return null;
        }
        return source;
    }
    
    /**
     *
     * @param source
     * @return
     */
    public static boolean isLocalFile(String source) {
        try {
            new URL(source);
            return false;
        } catch (MalformedURLException ex) {
            log.error("Malformed URL Exception: " + ex);
            return true;
        }
    }
}
