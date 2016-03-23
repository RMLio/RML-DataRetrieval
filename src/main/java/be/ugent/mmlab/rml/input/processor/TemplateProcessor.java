package be.ugent.mmlab.rml.input.processor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RML - Data Retrieval: Template Processor
 *
 * @author andimou
 */
public class TemplateProcessor {
    
    // Log
    private static final Logger log = 
            LoggerFactory.getLogger(TemplateProcessor.class.getSimpleName());
    
    public String processUriTemplate(String template, Map<String, String> parameters) {
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            String expression = parameter.getKey();
            String replacement = parameter.getValue();
            
            log.debug("Template processing...");
            
            template = template.replaceAll("\\n", "").replaceAll(" +", " ");

            /*if (expression.contains("[")) {
                expression = expression.replaceAll("\\[", "").replaceAll("\\]", "");
                template = template.replaceAll("\\[", "").replaceAll("\\]", "");
            }*/
            try {
                if (replacement.trim().startsWith("http://")) {
                    template = template.replaceAll(
                            "\\{" + Pattern.quote(expression) + "\\}", replacement.trim());
                } else {
                    //TODO: replace the following with URIbuilder
                    template = template.replaceAll(
                            "\\{" + Pattern.quote(expression) + "\\}",
                            URLEncoder.encode(replacement, "UTF-8")
                            .replaceAll("\\+", "%20")
                            .replaceAll("\\%21", "!")
                            .replaceAll("\\%27", "'")
                            .replaceAll("\\%28", "(")
                            .replaceAll("\\%29", ")")
                            .replaceAll("\\%7E", "~"));
                    template = template.replaceAll(
                            "%%" + Pattern.quote(expression) + "%%",
                            URLEncoder.encode(replacement, "UTF-8")
                            .replaceAll("\\+", "%20")
                            .replaceAll("\\%21", "!")
                            .replaceAll("\\%27", "'")
                            .replaceAll("\\%28", "(")
                            .replaceAll("\\%29", ")")
                            .replaceAll("\\%7E", "~"));
                }
            } catch (UnsupportedEncodingException ex) {
                log.error("UnsupportedEncodingException " + ex);
            }
        }
        return template.toString();
    }
    
    public String processTemplate(String template, Map<String, String> parameters) {
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            String expression = parameter.getKey();
            String replacement = parameter.getValue();

            log.debug("Template processing...");

            template = template.replaceAll("\\n", "").replaceAll(" +", " ");

            template = template.replaceAll(
                    "\\{" + Pattern.quote(expression) + "\\}",
                    replacement);
            template = template.replaceAll(
                    "%%" + Pattern.quote(expression) + "%%",
                    replacement);

        }
        return template.toString();
    }

}
