package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.input.std.StdJdbcInputSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * RML - Data Retrieval Handler : DbProcessor
 *
 * @author andimou
 */
public class JdbcProcessor extends AbstractInputProcessor{
    // Log
    private static final Logger log = LoggerFactory.getLogger(JdbcProcessor.class);
    
    public JdbcTemplate getJdbcConnection(StdJdbcInputSource mapSource) {        
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        log.info("map source driver " + mapSource.getJdbcDriver());
        dataSource.setDriverClassName(mapSource.getJdbcDriver());
        dataSource.setUrl(mapSource.getJdbcDSN());
        dataSource.setUsername(mapSource.getUsername());
        dataSource.setPassword(mapSource.getPassword());
        
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        
        return jdbcTemplate;

    }
}
