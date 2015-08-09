package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.model.TriplesMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RML - Data Retrieval Handler : LocalFileProcessor
 *
 * @author andimou
 */
public class LocalFileProcessor extends AbstractInputProcessor {
    
    // Log
    private static final Logger log = LoggerFactory.getLogger(LocalFileProcessor.class);
    
    public InputStream getInputStream(String source) {
        InputStream input = null;
        try {
            File file = new File(new File(source).getAbsolutePath());

            if (!file.exists()) {
                file = new File(new File(source).getCanonicalPath());

                if (!file.exists()) {
                    log.error(
                            Thread.currentThread().getStackTrace()[1].getMethodName() + ": "
                            + "Input file not found. ");
                }
            }
            input = new FileInputStream(file);
        } catch (IOException ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex);
        }
        return input;
    }
    
    @Override
    public InputStream getInputStream(InputSource inputSource) {
        InputStream input = null;
        String source = inputSource.getSource();
        try {
            File file = new File(new File(source).getAbsolutePath());

            if (!file.exists()) {
                file = new File(new File(source).getCanonicalPath());

                if (!file.exists()) {
                    log.error(
                            Thread.currentThread().getStackTrace()[1].getMethodName() + ": "
                            + "Input file not found. ");
                }
            }
            input = new FileInputStream(file);
        } catch (IOException ex) {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex);
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
                    if (LocalFileProcessor.class.getResource(triplesMap.getLogicalSource().getSource()) == null) {
                        source = triplesMap.getLogicalSource().getSource();
                        file = new File(new File(source).getAbsolutePath());
                    } else {
                        source = LocalFileProcessor.class.getResource(triplesMap.getLogicalSource().getSource()).getFile();
                        file = new File(new File(source).getCanonicalPath());
                    }
                    if (!file.exists()) {
                        log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + ": "
                                + "Input file not found. ");
                    }
                }
                source = file.toString();
                //input = new FileInputStream(file);
            } catch (IOException ex) {
                log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex);
            }
        } else {
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " 
                    + "Input stream was not possible.");
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
            log.error(Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + ex);
            return true;
        }
    }
}
