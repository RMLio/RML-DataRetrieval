package be.ugent.mmlab.rml.input.extractor;

import be.ugent.mmlab.rml.mapdochandler.extraction.concrete.SourceExtractor;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RML - Data Retrieval Handler : AbstractInputExtractor
 *
 * @author andimou
 */
abstract public class AbstractSourceExtractor implements SourceExtractor {

    // Log
    private static final Logger log = LoggerFactory.getLogger(AbstractSourceExtractor.class);

    
    /**
     *
     * @param stringTemplate
     * @return
     */
    public Set<String> extractStringTemplate(String stringTemplate) {
        Set<String> result = new HashSet<String>();
        // Curly braces that do not enclose column names MUST be
        // escaped by a backslash character (“\”).
        stringTemplate = stringTemplate.replaceAll("\\\\\\{", "");
        stringTemplate = stringTemplate.replaceAll("\\\\\\}", "");

        if (stringTemplate != null) {
            StringTokenizer st = new StringTokenizer(stringTemplate, "{}", true);
            boolean keepNext = false;
            String next = null;
            while (st.hasMoreElements()) {
                String element = st.nextElement().toString();
                if (keepNext) {
                    next = element;
                }
                keepNext = element.equals("{");
                if (element.equals("}") && element != null) {
                    log.debug("Extracted variable name "
                            + next + " from string template " + stringTemplate);
                    result.add(next);
                    next = null;
                }
            }
        }
        return result;
    }

}
