package be.ugent.mmlab.rml.input;

import be.ugent.mmlab.rml.input.processor.ApiProcessor;
import be.ugent.mmlab.rml.input.processor.JdbcProcessor;
import be.ugent.mmlab.rml.input.processor.LocalFileProcessor;
import be.ugent.mmlab.rml.input.processor.SourceProcessor;
import be.ugent.mmlab.rml.input.processor.SparqlProcessor;
import be.ugent.mmlab.rml.mapdochandler.extraction.concrete.SourceExtractor;
import be.ugent.mmlab.rml.mapdochandler.extraction.source.concrete.DcatExtractor;
import be.ugent.mmlab.rml.mapdochandler.extraction.source.concrete.HydraExtractor;
import be.ugent.mmlab.rml.mapdochandler.extraction.source.concrete.JdbcExtractor;
import be.ugent.mmlab.rml.mapdochandler.extraction.source.concrete.LocalFileExtractor;
import be.ugent.mmlab.rml.mapdochandler.extraction.source.concrete.SparqlExtractor;
import be.ugent.mmlab.rml.model.Source;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;

/**
 * RML - Data Retrieval Handler : ConcreteSourceProcessorFactory
 *
 * @author andimou
 */
public class ConcreteLogicalSourceProcessorFactory implements SourceProcessorFactory {
    
    // Log
    private static final Logger log = LoggerFactory.getLogger(ConcreteLogicalSourceProcessorFactory.class);
    
    public SourceProcessor createSourceProcessor(Repository repository, Source source) {
        SourceProcessor sourceProcessor = null;
        try {
            
            RepositoryConnection connection = repository.getConnection();
            
            //TODO: Normally source won't work, it's of another class
            if (source.getClass().getSimpleName().equals("MemLiteral")) {
                log.debug("Literal-valued Input Source");
                sourceProcessor = new LocalFileProcessor();
            } else {
                log.debug("Resource-valued Input Source");
                //RepositoryResult<Statement> inputStatements =
                //        connection.getStatements((Resource) value, RDF.TYPE, null, true);
                //String sourceType = inputStatements.next().getObject().stringValue().toString();
                
                //TODO Normally the following switch case won't work.
                String sourceType = source.getClass().getSimpleName();
                log.debug("source type " + sourceType);

                switch (sourceType) {
                    case ("StdApiSource"):
                        log.debug("Processor for API Source.");
                        sourceProcessor = new ApiProcessor();
                        break;
                    case ("StdLocalFileSource"):
                        log.debug("Processor for Local File Source.");
                        sourceProcessor = new LocalFileProcessor();
                        break;
                    case ("StdSparqlEndpointSource"):
                        log.debug("Processor for SPARQL Endpoint source.");
                        sourceProcessor = new SparqlProcessor();
                        break;
                    case ("StdJdbcSource"):
                        log.debug("Processor for JDBC Source.");
                        sourceProcessor = new JdbcProcessor();
                        break;
                    default:
                        log.error("Not identified source type");
                }
                connection.close();
            }
        } catch (RepositoryException ex) {
            log.error("Repository Exception " + ex);
        }
        return sourceProcessor;
    }
    
    public SourceProcessor createSourceProcessor(Source source) {
        SourceProcessor sourceProcessor = null;

        //TODO: Normally source won't work, it's of another class
        if (source.getClass().getSimpleName().equals("MemLiteral")) {
            log.debug("Literal-valued Input Source");
            sourceProcessor = new LocalFileProcessor();
        } else {
            log.debug("Resource-valued Input Source");
            //RepositoryResult<Statement> inputStatements =
            //        connection.getStatements((Resource) value, RDF.TYPE, null, true);
            //String sourceType = inputStatements.next().getObject().stringValue().toString();

            //TODO Normally the following switch case won't work.
            String sourceType = source.getClass().getSimpleName();
            log.debug("source type " + sourceType);

            switch (sourceType) {
                case ("StdApiSource"):
                    log.debug("Processor for API Source.");
                    sourceProcessor = new ApiProcessor();
                    break;
                case ("StdLocalFileSource"):
                    log.debug("Processor for Local File Source.");
                    sourceProcessor = new LocalFileProcessor();
                    break;
                case ("StdSparqlEndpointSource"):
                    log.debug("Processor for SPARQL Endpoint source.");
                    sourceProcessor = new SparqlProcessor();
                    break;
                case ("StdJdbcSource"):
                    log.debug("Processor for JDBC Source.");
                    sourceProcessor = new JdbcProcessor();
                    break;
                default:
                    log.error("Not identified source type");
            }

        }

        return sourceProcessor;
    }
    
    public Set<Source> chooseSource(Repository repository, Value value) {
        Set<Source> inputSources = null;
        try {
            SourceExtractor input;
            RepositoryConnection connection = repository.getConnection();
            if (value.getClass().getSimpleName().equals("MemLiteral")) {
                log.debug("Literal-valued Input Source");
                //TODO: Change extractInput to Value instead of Resource
                LocalFileExtractor localFileExtractor = new LocalFileExtractor();
                inputSources = localFileExtractor.extractInput(repository, value.stringValue());
                
            } else {
                log.debug("Resource-valued Input Source");
                
                RepositoryResult<Statement> inputStatements =
                        connection.getStatements((Resource) value, RDF.TYPE, null, true);
                
                String sourceType = inputStatements.next().getObject().stringValue().toString();
                log.debug("source type " + sourceType);

                //TODO:Change the followings not to compare with String
                switch (sourceType) {
                    case ("http://www.w3.org/ns/hydra/core#APIDocumentation"):
                        log.debug("Source described with Hydra Core vocabulary.");
                        input = new HydraExtractor();
                        inputSources = input.extractSources(repository, (Resource) value);
                        break;
                    case ("http://www.w3.org/ns/dcat#Distribution"):
                        log.debug("Source described with DCAT vocabulary.");
                        input = new DcatExtractor();
                        inputSources = input.extractSources(repository, (Resource) value);
                        break;
                    case ("http://www.w3.org/ns/sparql-service-description#Service"):
                        log.debug("Source described with SPARQL-SD vocabulary.");
                        input = new SparqlExtractor();
                        inputSources = input.extractSources(repository, (Resource) value);
                        break;
                    case ("http://www.wiwiss.fu-berlin.de/suhl/bizer/D2RQ/0.1#Database"):
                        log.debug("Source described with D2RQ vocabulary.");
                        input = new JdbcExtractor();
                        inputSources = input.extractSources(repository, (Resource) value);
                        break;
                    default:
                        log.error("Not identified source type");
                }
                connection.close();
            }
        } catch (RepositoryException ex) {
            log.error("RepositoryException " + ex);
        }
        return inputSources;
    }
    
}
